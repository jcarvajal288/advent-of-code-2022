(ns advent-of-code-2022.day13
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]))

(defn compare-integers [pair]
  (cond
    (< (first pair) (last pair)) :in-order
    (> (first pair) (last pair)) :out-of-order
    :else :equal))

(defn unbox [value]
  (if (and (list? value) (= (count value) 1))
    (unbox (first value))
    value))

(defn compare-lengths [list-left list-right]
  (cond
    (< (count list-left) (count list-right)) :in-order
    (> (count list-left) (count list-right)) :out-of-order
    :else :equal))

(defn compare-lists [lists]
  (let [left (first lists)
        right (last lists)
        list-left (if (integer? left) [left] left)
        list-right (if (integer? right) [right] right)
        zipped (zip list-left list-right)
        first-unequal-pair (first (filter #(not (= (unbox (first %)) (unbox (last %)))) zipped))]
    (cond
      (empty? first-unequal-pair) (compare-lengths list-left list-right)
      (every? integer? first-unequal-pair) (compare-integers first-unequal-pair)
      :else (let [result (compare-lists first-unequal-pair)]
              (if (and result (= result :equal))
                (compare-lengths list-left list-right)
                result)))))

(defn packet-in-right-order? [packet-strings]
  (let [packet1 (load-string (first packet-strings))
        packet2 (load-string (last packet-strings))
        result (compare-lists [packet1 packet2])]
    (or (= result :in-order) (= result :equal))))

(defn compare-packets [packet1 packet2]
  (packet-in-right-order? [packet1 packet2]))

(defn get-packet-pairs [filename]
  (->> (get-resource-file-by-line filename)
       (filter #(not (empty? %)))
       (partition 2)))

(defn sum-of-indices-of-right-order-pairs [filename]
  (->> (get-packet-pairs filename)
       (map packet-in-right-order?)
       (map-indexed vector)
       (filter last)
       (map first)
       (map inc)
       (reduce +)))

(defn sort-packets [filename]
  (->> (get-resource-file-by-line filename)
       (filter #(not (empty? %)))
       (concat ["[[2]]" "[[6]]"])
       (sort compare-packets)))

(defn product-of-indices [filename]
  (let [sorted-packets (sort-packets filename)
        two-index (inc (.indexOf sorted-packets "[[2]]"))
        six-index (inc (.indexOf sorted-packets "[[6]]"))]
    (* two-index six-index)))