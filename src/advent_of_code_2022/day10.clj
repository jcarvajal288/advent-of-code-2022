(ns advent-of-code-2022.day10
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]
            ))


(deftype Program-State [cycle x-reg])

(defn execute-instruction [current-state next-instruction]
  (cond
    (= next-instruction "noop") [(->Program-State (+ (.cycle current-state) 1)
                                                  (.x-reg current-state))]
    (str/starts-with? next-instruction "addx") (let [x-value (str-to-int (last (str/split next-instruction #" ")))]
                                                 [(->Program-State (+ (.cycle current-state) 1)
                                                                   (.x-reg current-state))
                                                  (->Program-State (+ (.cycle current-state) 2)
                                                                   (+ (.x-reg current-state) x-value))])))

(defn run-program [program]
  (let [initial-state (->Program-State 1 1)]
    (reduce (fn [state-list next-instruction]
              (concat state-list (execute-instruction (last state-list) next-instruction)))
            [initial-state]
            program)))

(defn x-register-at-cycle [state-list cycle]
  (.x-reg (nth state-list (- cycle 1))))

(defn sum-signal-strengths [start-cycle cycle-interval filename]
  (let [state-list (run-program (get-resource-file-by-line filename))
        interesting-cycles (range start-cycle (count state-list) cycle-interval)]
    (->> interesting-cycles
         (map #(* % (x-register-at-cycle state-list %)))
         (reduce +))))

(defn draw-pixel [indexed-state]
  (let [index (first indexed-state)
        state (last indexed-state)]
    (if (<= (abs (- index (.x-reg state))) 1)
      \#
      \.)))

(defn render-screen [state-list]
  (let [states-by-rows (partition 40 state-list)
        draw-pixel-row (fn [state-row]
                         (->> state-row
                              (map-indexed vector)
                              (map draw-pixel)))]
    (map draw-pixel-row states-by-rows)))

(defn draw-screen [filename]
  (let [state-list (run-program (get-resource-file-by-line filename))
        pixel-rows (render-screen state-list)]
    (doseq [row pixel-rows] (println row))))
