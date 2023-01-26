(ns advent-of-code-2022.day8
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.math.combinatorics :as cm]
            [taoensso.timbre :as log]))

(defn read-forest [file]
  (let [rows (vec (get-resource-file-by-line file))]
    (vec (map #(vec (map char-to-int %)) rows))))

(defn is-tree-visible-in-row [tree-row]
  (if (= (count tree-row) 1)
    true
    (> (first tree-row) (reduce max (rest tree-row)))))

(defn get-tree [forest x y]
  (get (get forest y) x))

(defn row-up [forest x y]
  (if (= y 0)
    [(get-tree forest x y)]
    (conj (row-up forest x (- y 1)) (get-tree forest x y))))

(defn row-right [forest x y]
  (if (= x (- (count (get forest 0)) 1))
    [(get-tree forest x y)]
    (conj (row-right forest (+ x 1) y) (get-tree forest x y))))

(defn row-down [forest x y]
  (if (= y (- (count forest) 1))
    [(get-tree forest x y)]
    (conj (row-down forest x (+ y 1)) (get-tree forest x y))))

(defn row-left [forest x y]
  (if (= x 0)
    [(get-tree forest x y)]
    (conj (row-left forest (- x 1) y) (get-tree forest x y))))

(defn get-rows-for-tree [forest x y]
  [(reverse (row-up forest x y))
   (reverse (row-right forest x y))
   (reverse (row-down forest x y))
   (reverse (row-left forest x y))])

(defn is-tree-visible [forest x y]
  (let [sight-lines (get-rows-for-tree forest x y)]
    (some true? (map is-tree-visible-in-row sight-lines))))

(defn trees-seen [tree-row]
  (let [tree (first tree-row)
        reducer (fn [sum trees]
                  (if (or (<= tree (first trees)) (= (count trees) 1))
                    (+ sum 1)
                    (recur (+ sum 1) (rest trees))))]
    (if (= (count tree-row) 1)
      0
      (reducer 0 (rest tree-row)))))

(defn tree-scenery-score [forest x y]
  (let [sight-lines (get-rows-for-tree forest x y)]
    (->> sight-lines
         (map trees-seen)
         (reduce *))))

(defn visible-trees [file]
  (let [forest (read-forest file)
        x-axis (range 0 (count (get forest 0)))
        y-axis (range 0 (count forest))]
    (->> (cm/cartesian-product x-axis y-axis)
         (map #(is-tree-visible forest (first %) (last %)))
         (filter true?)
         (count))))

(defn highest-scenery-score [file]
  (let [forest (read-forest file)
        x-axis (range 0 (count (get forest 0)))
        y-axis (range 0 (count forest))]
    (->> (cm/cartesian-product x-axis y-axis)
         (map #(tree-scenery-score forest (first %) (last %)))
         (reduce max))))

