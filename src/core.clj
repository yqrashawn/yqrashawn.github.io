(ns core
  (:require
   ;; [clj-time.core :as t]
   [selmer.parser :as selmer]
   [selmer.util]
   [clojure.string :as str]
   [hashp.core]
   [babashka.process :as p]
   [cybermonday.core :refer [parse-md]]
   [babashka.fs :as fs]))

(defonce blog-f (atom nil))

(defn- get-pwd []
  (System/getProperty "user.dir"))

(defn- is-mac []
  (let [n (System/getProperty "os.name")]
    (or (.matches n ".*mac.*")
        (.matches n ".*Mac.*"))))

(defn- paint-lang-map [lang]
  (case lang
    "zsh"        "shell"
    "emacs-lisp" "lisp"
    "elisp"      "lisp"
    lang))

(defn paint [lang code]
  (let [lang       (paint-lang-map lang)
        user-dir   (get-pwd)
        file-name  (if is-mac "./paint-macos" "./paint-linux")
        paint-path (-> user-dir
                       (fs/file file-name)
                       fs/normalize)]
    (:out (p/process
           [paint-path
            (if lang (str "--filetype=" lang) "")
            ;; "--html-only"
            "--embed"
            "--css-inline"]
           {:in code}))))

(comment
  (slurp (paint "javascript" "const a = 100")))

(defn -main
  ([] (-main (str (get-pwd) "/posts")))
  ([path]
   (let [path (fs/expand-home path)]
     (assert (fs/readable? path) (str "Non-readable path " path))
     (reset! blog-f path))))

(defn- get-all-md-file []
  (let [dir-stack (atom [])
        md-files  (atom [])]
    (fs/walk-file-tree
     @blog-f
     {:post-visit-dir (fn [f _] (swap! dir-stack pop) :continue)
      :pre-visit-dir  (fn [f _]
                        (if (str/ends-with? (.toString f) "node_modules")
                          :skip-subtree
                          (do (swap! dir-stack conj f) :continue)))
      :visit-file
      (fn [f _]
        (when (-> f fs/extension (= "md")) (swap! md-files conj [f @dir-stack])) :continue)})
    @md-files))

;; (defn- get-body [hiccup-vector]
;;   (-> hiccup-vector first rest first rest))

(defn- process-for-metadata [files-vector]
  (reduce
   (fn [acc [f parents]]
     (let [parents            (mapv fs/file parents)
           [_ year month day] parents
           file-name          (fs/file-name f)

           rst
           {:file f
            :js-file
            (-> f fs/parent (fs/file (str/replace file-name #"\.md$" ".js")))
            :cljs-file
            (-> f fs/parent (fs/file (str/replace file-name #"\.md$" ".cljs")))
            :html-file
            (-> f fs/parent (fs/file (str/replace file-name #"\.md$" ".html")))

            :file-name          file-name
            :creation-time      (fs/get-attribute f "basic:creationTime")
            :last-modified-time (fs/get-attribute f "lastModifiedTime")
            :size               (fs/get-attribute f "size")
            :year               (Integer/parseInt (-> year .getName))
            :month              (Integer/parseInt (-> month .getName))
            :day                (Integer/parseInt (-> day .getName))}]

       (conj acc rst)))
   []
   files-vector))

(defn md->hiccup [code-block-cache md-file-path]
  (let [md-str (-> md-file-path slurp)
        lines  (str/split-lines md-str)
        lines
        (if (= "---" (first lines))
          (loop [l   lines
                 rst []
                 c   0]
            (if (seq l)
              (cond
                (and (< c 2) (= (first l) "---"))
                (recur (rest l) (conj rst (first l)) (inc c))
                (and (>= c 2) (= (first l) "---"))
                (recur (rest l) (conj rst "___") c)
                :else
                (recur (rest l) (conj rst (first l)) c))
              rst))
          lines)
        md-str (str/join "\n" lines)]
    (-> md-str
        (parse-md
         {:lower-fns
          {:markdown/fenced-code-block
           (fn [[_ {:keys [language]} code]]
             (let [js-file-name (str "code-" (.toString (java.util.UUID/randomUUID)) ".js")
                   js-str       (slurp (paint language code))]
               (swap! code-block-cache #(assoc % js-file-name js-str))
               [:div.embedded>script {:src (str "./" js-file-name)}]
               ;; (first (get-body (hicv/html->hiccup (slurp (paint language code)))))
               ))
           :markdown/image-ref
           (fn [[_ {:keys [reference]}]]
             (when reference
               [:img {:src   (:url (second reference))
                      :title (:title (second reference))}]))}}))))

(defn process-files-for-hicuup [files]
  (mapv (fn [{:keys [file] :as data}]
          (let [code-block-cache (atom nil)
                data
                (merge data (md->hiccup code-block-cache (fs/file file)))

                data
                (assoc data :code-block-js @code-block-cache)

                data
                (assoc data :title (-> data :body (nth 2) (nth 2)))]
            data))
        files))

(defonce templates-cache (atom nil))

(defn get-templates [template-key]
  (comment
    (reset! templates-cache nil))
  (if @templates-cache (get @templates-cache template-key)
      (let [pwd (get-pwd)]
        (swap! templates-cache
               #(assoc % :article
                       (-> pwd
                           (fs/file "./src/templates/article.cljs")
                           slurp
                           (str/replace "\"{{article}}\"" "{{article}}"))))
        (swap! templates-cache
               #(assoc % :home
                       (-> pwd
                           (fs/file "./src/templates/home.cljs")
                           slurp
                           (str/replace "\"{{posts}}\"" "{{posts}}"))))
        (get @templates-cache template-key))))

(defn process-files-for-reagent-template [files]
  (mapv
   (fn [{:keys [body title] :as f}]
     (assoc f :reagent
            (selmer.util/without-escaping
             (selmer/render
              (get-templates :article)
              {:article body :title title}))))
   files))

(defn spit-cljs-files [files]
  (doseq [{:keys [cljs-file reagent]} files]
    (spit cljs-file reagent)))

(defn get-html [file]
  (p/process ["nbb" file] {:dir (fs/file (fs/parent file))}))

(defn process-files-for-html [files]
  (mapv
   (fn [{:keys [cljs-file] :as f}]
     (assoc f :html-str (slurp (:out (get-html cljs-file)))))
   files))

(defn spit-html-files [files]
  (doseq [{:keys [html-file code-block-js html-str]} files]
    (doseq [[js-name js-str] code-block-js]
      (let [js-file (-> html-file fs/file fs/parent (fs/file js-name))]
        (spit js-file js-str)))
    (spit html-file html-str)))

(defn process-files-for-post-list [files]
  (mapv
   (fn [{:keys [year month day file-name title]}]
     {:href     (format
                 "/posts/%04d/%02d/%02d/%s"
                 year month day
                 (str/replace
                  file-name
                  #"\.(md|org)$"
                  ".html"))
      :date     [year month day]
      :date-str (format "%04d-%02d-%02d" year month day)
      :title    title})
   files))

(defn process-for-home-cljs [{:keys [posts] :as data}]
  (let [home-cljs-str
        (selmer.util/without-escaping
         (selmer/render
          (get-templates :home)
          {:posts posts}))]
    (assoc data :home-cljs-str home-cljs-str)))

(defn spit-home-cljs [{:keys [home-cljs-str]}]
  (let [pwd (get-pwd)]
    (spit (fs/file pwd "./index.cljs") home-cljs-str)))

(defn process-home-html [data]
  (let [pwd (get-pwd)
        f   (fs/file pwd "./index.cljs")

        rst (get-html f)
        home-html-str (-> rst :out slurp)]
    (when (= home-html-str "") (println (-> rst :err slurp)))
    (assoc data :home-html-str home-html-str)))

(defn spit-home-html [{:keys [home-html-str]}]
  (spit (fs/file (get-pwd) "./index.html") home-html-str))

#_{:clj-kondo/ignore [:invalid-arity]}
(comment
  (reset! templates-cache nil)
  (-main)
  (do
    (def ffff (get-all-md-file))
    (def ff (process-files-for-hicuup (sort-by :creation-time (process-for-metadata ffff))))
    (def ss (process-files-for-reagent-template ff))
    (spit-cljs-files ss)
    (def sss (process-files-for-html ss))
    (spit-html-files sss)
    (reset! templates-cache nil)
    (do
      (def ll {:posts (process-files-for-post-list ff)
               :files ff})
      (def lll (process-for-home-cljs ll))
      (spit-home-cljs lll)
      (def llll (process-home-html lll))
      (spit-home-html llll)))
  (first (first ffff))
  (first ff)
  (map :creation-time ff)
  (process-for-metadata ffff)
  (def ff (process-files-for-hicuup (process-for-metadata ffff)))
  (def ss (process-files-for-reagent-template ff))
  (fs/file (fs/parent (:file (first ss))))
  (def sss (process-files-for-html ss))
  (slurp (:out (get-html (:cljs-file (nth ss 4))))))

(comment
  (doseq [{:keys [file creation-time year month day]} ff]
    (slurp (:out (p/process ["setfile" "-d" (str month "/" day "/" year " 00:00:00") (.toString file)])))
    [file creation-time year month day]))
