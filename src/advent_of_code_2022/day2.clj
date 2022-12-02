(ns advent-of-code-2022.day2
  (:require [advent-of-code-2022.util :refer :all]
    [taoensso.timbre :as log]))

(defn- result-score [their-move my-move]
  (cond
    (and (= their-move \A) (= my-move \X)) 3
    (and (= their-move \A) (= my-move \Y)) 6
    (and (= their-move \A) (= my-move \Z)) 0
    (and (= their-move \B) (= my-move \X)) 0
    (and (= their-move \B) (= my-move \Y)) 3
    (and (= their-move \B) (= my-move \Z)) 6
    (and (= their-move \C) (= my-move \X)) 6
    (and (= their-move \C) (= my-move \Y)) 0
    (and (= their-move \C) (= my-move \Z)) 3
    :else 999
  ))

(defn- move-score [my-move]
  (cond
    (= my-move \X) 1
    (= my-move \Y) 2
    (= my-move \Z) 3
    :else 888
  ))

(defn- play-round [round]
  (let [their-move (first round)
        my-move (last round)]
    ;(log/info (str their-move " <> " my-move))
    ;(log/info (result-score their-move my-move))
    ;(log/info (move-score my-move))
    (+ (result-score their-move my-move) (move-score my-move))))

(defn calculate-score [file]
  (let [guide (vec (get-resource-file-by-line file))]
    ;(log/info guide)
    (reduce + (map play-round guide))))

