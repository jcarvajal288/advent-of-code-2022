(ns advent-of-code-2022.day11
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(declare give-monkey-item)
(defrecord Monkey [items operation throw-item num-inspections])

(defn create-monkey [items operation divisor monkey-true monkey-false]
  (->Monkey
    items
    operation
    (fn [item monkeys]
      (if (= (mod item divisor) 0)
        (give-monkey-item item monkeys monkey-true)
        (give-monkey-item item monkeys monkey-false)))
    0))


(defn give-monkey-item [item monkeys catcher]
  ;(log/info (format "    Item with worry level %d is thrown to monkey %d" item catcher))
  (update-in monkeys [catcher :items] #(conj % item)))

(defn throw-items [monkeys current-monkey]
  (reduce (fn [monkeys item]
        ;(log/info (format "  Monkey inspects an item with worry level of %d" item))
        (-> item
            ((.operation current-monkey))
            ;(/ 3)
            (biginteger)
            ((.-throw_item current-monkey) monkeys)))
      monkeys
      (.items current-monkey)))

(defn adjust-current-monkeys-inspections [monkeys current-monkey-index]
  (let [current-monkey (get monkeys current-monkey-index)
        num-items (count (.items current-monkey))]
    (update-in monkeys [current-monkey-index :num-inspections] #(+ % num-items))))

(defn clear-current-monkeys-items [monkeys current-monkey-index]
  (let [current-monkey (get monkeys current-monkey-index)
        itemless-monkey (assoc current-monkey :items [])]
    (assoc monkeys current-monkey-index itemless-monkey)))

(defn play-turn [monkeys current-monkey-index]
  ;(log/info (format "Monkey %d:" current-monkey-index))
  (let [current-monkey (get monkeys current-monkey-index)]
    (-> monkeys
        (throw-items current-monkey)
        (adjust-current-monkeys-inspections current-monkey-index)
        (clear-current-monkeys-items current-monkey-index))
    ))

(defn play-round [monkeys]
  (reduce (fn [monkeys monkey-index]
            (play-turn monkeys monkey-index))
          monkeys
          (range (count monkeys))))

(defn play-rounds [monkeys num-rounds]
  (reduce (fn [rounds round-number]
            (log/info round-number)
            (let [latest-monkeys (last rounds)]
              (conj rounds (play-round latest-monkeys))))
          [monkeys]
          (range num-rounds)))

(defn calculate-monkey-business [monkeys num-rounds]
  (let [rounds (play-rounds monkeys num-rounds)]
    (->> (last rounds)
         (map #(.-num_inspections %))
         (sort)
         (reverse)
         (take 2)
         (reduce *))))