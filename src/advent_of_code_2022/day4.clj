(ns advent-of-code-2022.day4
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defn- is-fully-contained? [ranges]
  (let [left-range (str/split (first ranges) #"-")
        right-range (str/split (last ranges) #"-")
        left [(str-to-int (first left-range)) (str-to-int (last left-range))]
        right [(str-to-int (first right-range)) (str-to-int (last right-range))]]
    (or (and (>= (first left) (first right))
             (<= (last left) (last right)))
        (and (<= (first left) (first right))
             (>= (last left) (last right))))))

(defn- is-overlapping? [ranges]
  (let [left-range (str/split (first ranges) #"-")
        right-range (str/split (last ranges) #"-")
        left [(str-to-int (first left-range)) (str-to-int (last left-range))]
        right [(str-to-int (first right-range)) (str-to-int (last right-range))]]
    (or (and (>= (first left) (first right))
             (<= (first left) (last right)))
        (and (<= (last left) (last right))
             (>= (last left) (first right)))
        (is-fully-contained? ranges))))

(defn count-fully-contained-ranges [file]
  (->> (get-resource-file-by-line file)
       (map #(str/split % #","))
       (filter is-fully-contained?)
       (count)))

(defn count-overlapping-ranges [file]
  (->> (get-resource-file-by-line file)
       (map #(str/split % #","))
       (filter is-overlapping?)
       (count)))
