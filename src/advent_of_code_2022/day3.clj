(ns advent-of-code-2022.day3
  (:require [advent-of-code-2022.util :refer :all]
    [taoensso.timbre :as log]))

(defn in-all-vectors? [ch vectors]
  (every? #(some #{ch} %) vectors))

(defn find-common-element [vectors]
  (some #(when (in-all-vectors? % (rest vectors)) %) (first vectors)))

(defn get-priority [ch]
  (let [ch-dec (int ch)]
    (if (>= ch-dec 97)
        (- ch-dec 96)
        (- ch-dec 38))))

(defn sum-priorities [file]
  (let [contents (vec (get-resource-file-by-line file))]
    (->> contents
         (map #(split-at (/ (count %) 2) %))
         (map find-common-element)
         (map get-priority)
         (reduce +))))

(defn- group-by-threes-reducer [items state]
  (if (<= (count items) 3)
      (conj state items)
      (group-by-threes-reducer (drop 3 items) (conj state (vec (take 3 items))))))

(defn group-by-threes [items]
  (group-by-threes-reducer items []))

(defn sum-priorities-of-group-badges [file]
  (let [contents (vec (get-resource-file-by-line file))]
    (->> contents
         (group-by-threes)
         (map find-common-element)
         (map get-priority)
         (reduce +))))
