(ns advent-of-code-2022.day13
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]
            ))

(defn compare-integers [pair]
  (<= (first pair) (last pair)))

(defn compare-lists [lists]
  (let [left (first lists)
        right (last lists)
        list-left (if (integer? left) [left] left)
        list-right (if (integer? right) [right] right)
        zipped (zip list-left list-right)
        first-unequal-pair (first (filter #(not (= (first %) (last %))) zipped))]
    (cond
      (and (= (first list-left) list-right) (> (count list-left) (count list-right))) false
      (and (= [(first list-left)] (first list-right)) (> (count list-left) (count list-right))) false
      (empty? first-unequal-pair) (<= (count list-left) (count list-right))
      (every? integer? first-unequal-pair) (compare-integers first-unequal-pair)
      :else (recur first-unequal-pair))))

(defn packet-in-right-order? [packet-strings]
  (let [packet1 (load-string (first packet-strings))
        packet2 (load-string (last packet-strings))]
    (compare-lists [packet1 packet2])))

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
