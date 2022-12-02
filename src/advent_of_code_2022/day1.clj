(ns advent-of-code-2022.day1
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defn- split-at-blanks [all-calorie-lists]
  (reduce
    (fn [accumulator next-value]
      (if (str/blank? next-value)
        (conj accumulator [])
        (conj (pop accumulator) (conj (peek accumulator) (str-to-int next-value)))))
    [[(str-to-int (first all-calorie-lists))]]
    (rest all-calorie-lists)))

(defn- summed-calories [all-calorie-lists]
    (->> all-calorie-lists
         (split-at-blanks)
         (map (partial reduce +))))

(defn most-carried-calories [file]
  (let [all-calorie-lists (vec (get-resource-file-by-line file))]
    (reduce max (summed-calories all-calorie-lists))))

(defn sum-top3-carried-calories [file]
  (let [all-calorie-lists (vec (get-resource-file-by-line file))]
    (->> (summed-calories all-calorie-lists)
         (sort)
         (reverse)
         (take 3)
         (reduce +))))

