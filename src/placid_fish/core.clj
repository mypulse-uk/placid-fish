(ns placid-fish.core
  (:require
    [clojure.string :as str]
    [org.bovinegenius.exploding-fish :as ef]))

(defn absolute?
  [uri]
  (try (ef/absolute? uri)
       (catch Exception _)))

(defn ends-with?
  [^CharSequence s ^String substr]
  (try (str/ends-with? s substr)
       (catch Exception _)))

(defn path
  [uri]
  (try (ef/path uri)
       (catch Exception _)))

(defn query-map
  [uri]
  (try (ef/query-map uri)
       (catch Exception _)))
