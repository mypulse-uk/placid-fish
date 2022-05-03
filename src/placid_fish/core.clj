(ns placid-fish.core
  (:require [org.bovinegenius.exploding-fish :as ef]
            [clojure.string :as str]))

(defn absolute?
  [uri]
  (try (ef/absolute? uri)
       (catch Exception _)))

(defn ends-with?
  [^CharSequence s ^String substr]
  (try (str/ends-with? s substr)
       (catch Exception _)))