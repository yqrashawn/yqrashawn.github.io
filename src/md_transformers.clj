(ns md-transformers
  (:use markdown.transformers)
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [markdown.links
             :refer [link
                     image
                     reference-link
                     image-reference-link
                     implicit-reference-link
                     footnote-link]]
            [markdown.lists :refer [li]]
            [markdown.tables :refer [table]]
            [markdown.common
             :refer
             [escape-code
              escaped-chars
              freeze-string
              thaw-strings
              strong
              bold
              bold-italic
              em
              italics
              strikethrough
              inline-code
              escape-inhibit-separator
              inhibit
              make-heading
              dashes]]
            [clj-yaml.core :as yaml]))

(require '[hashp.core])
