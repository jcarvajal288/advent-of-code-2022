(ns advent-of-code-2022.day10-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.util :refer :all]
            [advent-of-code-2022.day10 :refer :all]))

(deftest day10-example-test
  (testing "day10-example-test"
    (is (= (sum-signal-strengths 20 40 "day10-example-data2.txt") 13140))))

(deftest day10-full-test
  (testing "day10-full-test"
    (is (= (sum-signal-strengths 20 40 "day10-full-data.txt") 13480))))

(deftest day10-full-test-part2
  (testing "day10-full-test2-part2"
    (is (= (draw-screen "day10-full-data.txt") nil))))


