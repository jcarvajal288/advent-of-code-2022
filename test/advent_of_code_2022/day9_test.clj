(ns advent-of-code-2022.day9-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day9 :refer :all]))

(deftest day8-sample-test
  (testing "day8-sample-test"
    (is (= (get-unique-positions-N-knots "day9-example-data.txt" 2) 13))))

(deftest day8-full-test
  (testing "day8-full-test"
    (is (= (get-unique-positions-N-knots "day9-full-data.txt" 2) 6067))))

(deftest day8-sample-test-part2
  (testing "day8-sample-test-part2"
    (is (= (get-unique-positions-N-knots "day9-example-data.txt" 10) 1))))

(deftest day8-sample-test-part2-data2
  (testing "day8-sample-test-part2-data2"
    (is (= (get-unique-positions-N-knots "day9-example-data2.txt" 10) 36))))

(deftest day8-full-test-part2
  (testing "day8-full-test-part2"
    (is (= (get-unique-positions-N-knots "day9-full-data.txt" 10) 2471))))

(deftest test-update-knot
  (testing "update-knot"
    (is (= (update-knot '(0 0) '(0 0)) '(0 0)))
    (is (= (update-knot '(1 0) '(0 0)) '(0 0)))
    (is (= (update-knot '(0 1) '(0 0)) '(0 0)))
    (is (= (update-knot '(2 0) '(0 0)) '(1 0)))
    (is (= (update-knot '(0 2) '(0 1)) '(0 1)))
    (is (= (update-knot '(0 0) '(2 0)) '(1 0)))
    (is (= (update-knot '(0 0) '(0 2)) '(0 1)))
    (is (= (update-knot '(2 3) '(1 1)) '(2 2)))
    (is (= (update-knot '(3 2) '(1 1)) '(2 2)))
    (is (= (update-knot '(4 1) '(3 0)) '(3 0)))))

(deftest test-update-rope
  (testing "update-rope"
    (is (= (update-rope [6 0] [[4 0] [3 0] [2 0] [1 0] [0 0]])
           [[6 0] [5 0] [4 0] [3 0] [2 0] [1 0]]))))