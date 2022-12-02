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
    (+ (result-score their-move my-move) (move-score my-move))))

(defn calculate-score [file]
  (let [guide (vec (get-resource-file-by-line file))]
    (reduce + (map play-round guide))))


(defn- result-score-2 [needed-result]
  (cond
    (= needed-result \X) 0
    (= needed-result \Y) 3
    (= needed-result \Z) 6
    :else 888
  ))

; A - rock       X - lose
; B - paper      Y - draw
; C - scissors   Z - win

; rock - 1
; paper - 2
; scissors - 3

(defn- move-score-2 [their-move needed-result]
  (cond
    (and (= their-move \A) (= needed-result \X)) 3
    (and (= their-move \A) (= needed-result \Y)) 1
    (and (= their-move \A) (= needed-result \Z)) 2
    (and (= their-move \B) (= needed-result \X)) 1
    (and (= their-move \B) (= needed-result \Y)) 2
    (and (= their-move \B) (= needed-result \Z)) 3
    (and (= their-move \C) (= needed-result \X)) 2
    (and (= their-move \C) (= needed-result \Y)) 3
    (and (= their-move \C) (= needed-result \Z)) 1
    :else 999
    ))

(defn- play-round-2 [round]
  (let [their-move (first round)
        needed-result (last round)]
    (+ (result-score-2 needed-result) (move-score-2 their-move needed-result))))

(defn calculate-score-2 [file]
  (let [guide (vec (get-resource-file-by-line file))]
    (reduce + (map play-round-2 guide))))

