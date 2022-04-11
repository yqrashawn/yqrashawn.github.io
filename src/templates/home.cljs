(ns home
  (:require
   ;; [reagent.dom :as rdom]
   [reagent.dom.server]))

(defn nav []
  [:nav
   [:a {:href "/"} "Home"]])

(defn header []
  [:header [nav]])

(defn footer []
  [:footer])

(defn post-entry [{:keys [href title]}]
  [:a {:href href} title])

(defn posts []
  (mapv
   post-entry
   "{{posts}}"))

(defn main []
  [:main
   [:section.posts-section
    [:h2 "Posts"]
    (posts)]])

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
   [header]
   [main]
   ;; [footer]
   ])
