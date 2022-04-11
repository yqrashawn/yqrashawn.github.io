(ns article
  (:require
   ;; [reagent.dom :as rdom]
   [reagent.dom.server]))

(defn article []
  [:article "{{article}}"])

(defn body []
  [:body
   [:link
    {:rel "stylesheet" :href "https://cdn.jsdelivr.net/npm/water.css@2/out/water.min.css"}]
   [:link {:rel "stylesheet" :href "/public/index.css"}]
   [:style {:type "text/css"}
    "div.embedded tbody tr:nth-child(2n) {background-color: unset;}"]
   [:main [article]]
   ;; [:link {:rel "stylesheet" :href "/main.css" :type "text/css"}]
   ])

(defn head []
  [:head
   [:title "{{title}}"]
   [:meta {:charset "utf-8"}]
   [:meta {:name "author" :content "Rashawn Zhang"}]
   [:meta {:property "og:locale" :content "en_US"}]
   [:meta {:property "og:url" :content "https://yqrashawn.com/{{href}}"}]
   [:meta {:property "og:site_name" :content "Rashawn Zhang"}]
   [:meta {:property "og:type" :content "website"}]
   [:meta {:property "twitter:site" :content "@yqrashawn"}]
   [:meta {:property "twitter:card" :content "summary_large_image"}]
   [:meta {:property "twitter:title" :content "{{title}} - Rashawn Zhang"}]
   [:link {:rel "canonical" :href "https://yqrashawn.com"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]])

(defn html []
  [:html {:lang "en"}
   [head]
   [body]])

;; (defn init []
;;   (rdom/render [article] (.getElementById js/document "main")))

(defn srender []
  (reagent.dom.server/render-to-string [html]))

(js/console.log (srender))
