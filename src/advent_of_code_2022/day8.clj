(ns advent-of-code-2022.day8
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]))

;(defn visible-trees [file]
;  (let [tree-rows (vec (get-resource-file-by-line file)]
;    ))

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

