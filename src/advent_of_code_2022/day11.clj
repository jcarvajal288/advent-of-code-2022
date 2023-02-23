(ns advent-of-code-2022.day11
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(declare throw-to-monkey)
(deftype Monkey [items operation throw-item num-inspections])

(defn create-monkey [items operation divisor monkey-true monkey-false]
  (->Monkey
    (.items items)
    (.operation operation)
    (.throw-item (fn [item monkeys]
                   (if (= (mod item divisor) 0)
                     (throw-to-monkey item monkeys monkey-true)
                     (throw-to-monkey item monkeys monkey-false))))
    0))

(defn throw-to-monkey [item monkeys catcher]
  )

(defn play-turn [monkey monkeys]
  (do
    (for [item (.items monkey)]
      (-> item
          ((.operation monkey))
          (/ 3)
          ((.-throw_item monkey) monkeys)))
    (assoc monkey .items [])))

(defn play-round [monkeys]
  (for [monkey monkeys]
    (play-turn monkey monkeys)))

(defn calculate-monkey-business [monkeys num-rounds]
  (let [last-round (nth (iterate (play-round monkeys) num-rounds) num-rounds)]
    (->> last-round
         (map #(.num-inspections %))
         (sort)
         (reverse)
         (take 2)
         (reduce *))))