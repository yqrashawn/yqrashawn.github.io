(ns home
  (:require
   ;; [reagent.dom :as rdom]
   [reagent.dom.server]))

(defn nav []
  [:nav
   [:a {:href "/"} "Home"]
   [:a {:href "/"} "About"]
   [:a {:href "/"} "RSS"]])

(defn header []
  [:header [nav]])

(defn footer []
  [:footer])

(defn post-entry [{:keys [href title date-str]}]
  [:li
   [:pre>time {:datetime date-str} date-str]
   [:a {:href href} title]])

(defn posts []
  (into [:ul.post-entries] (mapv post-entry "{{posts}}")))

(defn body []
  [:body
   [:link {:rel "stylesheet" :href "https://cdn.jsdelivr.net/npm/water.css@2/out/water.min.css"}]
   [:style {:type "text/css"} "ul.post-entries li pre {display: inline;}"]
   [:main
    [:section.posts-section
     [:h3 "Posts"] (posts)]]])

(defn head []
  [:head
   [:title "Rashawn Zhang"]
   [:meta {:name "author" :content "Rashawn Zhang"}]
   [:meta {:property "og:locale" :content "en_US"}]
   [:meta {:property "og:url" :content "https://yqrashawn.com"}]
   [:meta {:property "og:site_name" :content "Rashawn Zhang"}]
   [:meta {:property "og:type" :content "website"}]
   [:meta {:property "twitter:site" :content "@yqrashawn"}]
   [:meta {:property "twitter:card" :content "summary_large_image"}]
   [:meta {:property "twitter:title" :content "Rashawn Zhang's personal site"}]
   [:link {:rel "canonical" :href "https://yqrashawn.com"}]])

(defn html []
  [:html
   [head]
   [header]
   [body]
   ;; [footer]
   ])

;; (defn init []
;;   (rdom/render [article] (.getElementById js/document "main")))

(defn srender []
  (reagent.dom.server/render-to-string [html]))

(js/console.log (srender))
