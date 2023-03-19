(ns advent-of-code-2022.day13
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]
            ))

(defn compare-integers [pair]
  (format "  Comparing %d vs %d" (first pair) (last pair))
  (<= (first pair) (last pair)))

(defn unbox [value]
  (log/info (str "  Unboxing " value))
    (if (and (list? value) (= (count value) 1))
      (unbox (first value))
      value))

(defn compare-lists [lists]
  (let [left (first lists)
        right (last lists)
        list-left (if (integer? left) [left] left)
        list-right (if (integer? right) [right] right)
        zipped (zip list-left list-right)
        first-unequal-pair (first (filter #(not (= (unbox (first %)) (unbox (last %)))) zipped))]
    (log/info (format "  Comparing %s vs %s" list-left list-right))
    (log/info (str "first unequal pair: " first-unequal-pair))
    (cond
      ;(and (= (first list-left) list-right) (> (count list-left) (count list-right))) false
      ;(and (= [(first list-left)] (first list-right)) (> (count list-left) 1) (= (count list-right) 1)) false
      ;(and (= [(first list-left)] (first list-right)) (> (count list-left) (count list-right))) false
      (empty? first-unequal-pair) (<= (count list-left) (count list-right))
      (every? integer? first-unequal-pair) (compare-integers first-unequal-pair)
      :else (recur first-unequal-pair))))

(defn packet-in-right-order? [packet-strings]
  (let [packet1 (load-string (first packet-strings))
        packet2 (load-string (last packet-strings))
        result (compare-lists [packet1 packet2])]
    (log/info (str "    Result: " result))
    result))

(defn compare-packets [packet1 packet2]
  (log/info (format "Comparing packets %s vs %s" packet1 packet2))
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