(ns advent-of-code-2022.util
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))

(defn get-resource-file-by-line [file]
  (str/split-lines (slurp (.getPath (clojure.java.io/resource file)))))

(defn str-to-int [s]
  (let [value (Integer/parseInt (re-find #"\d+" s))]
    (if (str/starts-with? s "-")
      (- value)
      value)))

(defn char-to-int [c]
  (Character/digit ^Character c 10))

(defn pad [n val coll]
  (take n (concat coll (repeat val))))

(defn is-number? [s]
  (every? #(Character/isDigit ^char %) s))

(defn assoc-if-new [coll k v]
  (merge {k v} coll))

(defn not-neg? [n] (not (neg? n)))

(defn not-nil? [n] (not (nil? n)))

(defn zip [v1 v2] (map vector v1 v2))

(defn coll-contains? [n coll]
  (not-nil? (some #{n} coll)))