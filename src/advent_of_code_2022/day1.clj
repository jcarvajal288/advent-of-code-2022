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

(defn most-carried-calories [file]
  (let [all-calorie-lists (vec (get-resource-file-by-line file))]
    (->> all-calorie-lists
         (split-at-blanks)
         (map (partial reduce +))
         (reduce max))))

