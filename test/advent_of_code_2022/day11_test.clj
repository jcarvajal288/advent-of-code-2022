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

(def actual-monkeys
  [(create-monkey
     [54 98 50 94 69 62 53 85]
     (fn [old] (* old 13))
     3 2 1)
   (create-monkey
     [71 55 82]
     (fn [old] (+ old 2))
     13 7 2)
   (create-monkey
     [77 73 86 72 87]
     (fn [old] (+ old 8))
     19 4 7)
   (create-monkey
     [97 91]
     (fn [old] (+ old 1))
     17 6 5)
   (create-monkey
     [78 97 51 85 66 63 62]
     (fn [old] (* old 17))
     5 6 3)
   (create-monkey
     [88]
     (fn [old] (+ old 3))
     7 1 0)
   (create-monkey
     [87 57 63 86 87 53]
     (fn [old] (* old old))
     11 5 0)
   (create-monkey
     [73 59 82 65]
     (fn [old] (+ old 6))
     2 4 3)
   ])


(deftest day11-example-test
  (testing "day11-example-test"
    (is (= (calculate-monkey-business example-monkeys 20) 10605))))

(deftest day11-full-test
  (testing "day11-full-test"
    (is (= (calculate-monkey-business actual-monkeys 20) 10605))))

(deftest day11-full-test-part2
  (testing "day11-full-test-part2"
    (is (= (calculate-monkey-business actual-monkeys 10000) 10605))))
