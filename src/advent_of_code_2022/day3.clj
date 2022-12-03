(ns advent-of-code-2022.day3
  (:require [advent-of-code-2022.util :refer :all]
    [taoensso.timbre :as log]))

(defn find-common-element [vectors]
  (some #(some #{%} (last vectors)) (first vectors)))

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
