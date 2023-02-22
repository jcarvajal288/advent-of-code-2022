(ns advent-of-code-2022.day9
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))


(defn clamp [n diff dist]
  (cond
    (>= diff dist) (+ n 1)
    (<= diff (- dist)) (- n 1)
    :else n))

(defn update-knot [head tail]
  (let [x-diff (- (first head) (first tail))
        y-diff (- (last head) (last tail))]
    (cond
      (= head tail) tail
      (= (first head) (first tail)) (list
                                      (first tail)
                                      (clamp (last tail) y-diff 2))
      (= (last head) (last tail)) (list
                                    (clamp (first tail) x-diff 2)
                                    (last tail))
      (or (>= (abs x-diff) 2) (>= (abs y-diff) 2)) (list
                                                     (clamp (first tail) x-diff 1)
                                                     (clamp (last tail) y-diff 1))
      :else tail)))

(defn move-head [head instruction]
  (cond
    (= (first instruction) "U") [(first head) (+ (last head) (last instruction))]
    (= (first instruction) "D") [(first head) (- (last head) (last instruction))]
    (= (first instruction) "L") [(- (first head) (last instruction)) (last head)]
    (= (first instruction) "R") [(+ (first head) (last instruction)) (last head)]))

(defn expand-instruction [instruction]
  (for [_ (range (last instruction))]
    [(first instruction) 1]))

(defn expand-instructions [instructions]
  (->> instructions
       (map #(expand-instruction %))
       (reduce concat)))

(defn update-rope [new-head rest-rope]
  (reduce (fn [new-rope next-knot]
            (conj new-rope (update-knot (last new-rope) next-knot)))
          [new-head]
          rest-rope))

(defn get-positions-N-knots [origin rope-length instructions]
  (let [rope (for [_ (range rope-length)] origin)]
    (last (reduce (fn [rope-and-tail-list next-instruction]
                    (let [rope (first rope-and-tail-list)
                          head (first rope)
                          tail-list (last rope-and-tail-list)
                          new-head (move-head head next-instruction)
                          new-rope (update-rope new-head (rest rope))]
                      [new-rope (conj tail-list (last new-rope))]))
                  [rope [origin]]
                  instructions))))

(defn prepare-instructions [filename]
  (->> (get-resource-file-by-line filename)
       (map #(str/split % #" "))
       (map #(list (first %) (str-to-int (last %))))
       (expand-instructions)))

(defn get-unique-positions-N-knots [filename rope-length]
  (->> (prepare-instructions filename)
       (get-positions-N-knots [0 0] rope-length)
       (distinct)
       (count)))