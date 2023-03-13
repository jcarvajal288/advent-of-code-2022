(ns advent-of-code-2022.day11
  (:require [taoensso.timbre :as log]))

(declare give-monkey-item)
(defrecord Monkey [items operation divisor throw-item num-inspections])

(defn create-monkey [items operation divisor monkey-true monkey-false]
  (->Monkey
    items
    operation
    divisor
    (fn [item monkeys]
      (if (= (mod item divisor) 0)
        (give-monkey-item item monkeys monkey-true)
        (give-monkey-item item monkeys monkey-false)))
    0))

(defn give-monkey-item [item monkeys catcher]
  (let [mod-cap (reduce * (map #(.-divisor %) monkeys))]
    (update-in monkeys [catcher :items] #(conj % (mod item mod-cap)))))

(defn throw-items [monkeys current-monkey worry-divisor]
  (reduce (fn [monkeys item]
            (-> item
                ((.operation current-monkey))
                ;(/ worry-divisor)
                ;(biginteger)
                ;(Math/round)
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

(defn play-turn [monkeys current-monkey-index worry-divisor]
  (let [current-monkey (get monkeys current-monkey-index)]
    (-> monkeys
        (throw-items current-monkey worry-divisor)
        (adjust-current-monkeys-inspections current-monkey-index)
        (clear-current-monkeys-items current-monkey-index))
    ))

(defn play-round [monkeys worry-divisor]
  (reduce (fn [monkeys monkey-index]
            (play-turn monkeys monkey-index worry-divisor))
          monkeys
          (range (count monkeys))))

(defn play-rounds [monkeys num-rounds worry-divisor]
  (reduce (fn [rounds round-number]
            ;(log/info round-number)
            (let [latest-monkeys (last rounds)]
              (conj rounds (play-round latest-monkeys worry-divisor))))
          [monkeys]
          (range num-rounds)))

(defn calculate-monkey-business [monkeys num-rounds worry-divisor]
  (let [rounds (play-rounds monkeys num-rounds, worry-divisor)]
    (->> (last rounds)
         (map #(.-num_inspections %))
         (sort)
         (reverse)
         (take 2)
         (reduce *)
         )))