(ns advent-of-code-2022.day11-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day11 :refer :all]))

(def example-monkeys
  [(create-monkey
     [79 98]
     (fn [old] (* old 19))
     23 2 3)
   (create-monkey
     [54 65 75 74]
     (fn [old] (+ old 6))
     19 2 0)
   (create-monkey
     [79 60 97]
     (fn [old] (* old old))
     13 1 3)
   (create-monkey
     [74]
     (fn [old] (+ old 3))
     17 0 1)])

(deftest day11-example-test
  (testing "day11-example-test"
    (is (= (calculate-monkey-business example-monkeys 20) 10605))))

