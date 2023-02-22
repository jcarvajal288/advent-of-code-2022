(ns advent-of-code-2022.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.util :refer :all]
            [advent-of-code-2022.day10 :refer :all]))

(def program1 (get-resource-file-by-line "day10-example-data.txt"))

(deftest day10-example-test
  (testing "day10-example-test"
    (is (= (x-register-at-cycle (run-program program1) 5) -1))))

(deftest day10-example-test2
  (testing "day10-example-test2"
    (is (= (sum-signal-strengths 20 40 "day10-example-data2.txt") 13140))))

(deftest day10-full-test2
  (testing "day10-full-test2"
    (is (= (sum-signal-strengths 20 40 "day10-full-data.txt") 13480))))
