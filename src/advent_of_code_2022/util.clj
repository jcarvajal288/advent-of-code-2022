(ns advent-of-code-2022.util
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))

(defn get-resource-file-by-line [file]
  (str/split-lines (slurp (.getPath (clojure.java.io/resource file)))))

(defn str-to-int [s]
  (Integer/parseInt (re-find #"\d+" s)))

(defn char-to-int [c]
  (Character/digit ^Character c 10))

(defn pad [n val coll]
  (take n (concat coll (repeat val))))

(defn is-number? [s]
  (every? #(Character/isDigit ^char %) s))
