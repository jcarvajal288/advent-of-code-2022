(ns advent-of-code-2022.day9-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day9 :refer :all]))

(deftest day8-sample-test
  (testing "day8-sample-test"
    (is (= (get-unique-positions "day9-example-data.txt") 13))))

(deftest day8-full-test
  (testing "day8-full-test"
    (is (= (get-unique-positions "day9-full-data.txt") 13))))

(deftest test-update-tail
  (testing "update-tail"
    (is (= (update-tail '(0 0) '(0 0)) '(0 0)))
    (is (= (update-tail '(1 0) '(0 0)) '(0 0)))
    (is (= (update-tail '(0 1) '(0 0)) '(0 0)))
    (is (= (update-tail '(2 0) '(0 0)) '(1 0)))
    (is (= (update-tail '(0 2) '(0 1)) '(0 1)))
    (is (= (update-tail '(0 0) '(2 0)) '(1 0)))
    (is (= (update-tail '(0 0) '(0 2)) '(0 1)))
    (is (= (update-tail '(2 3) '(1 1)) '(2 2)))
    (is (= (update-tail '(3 2) '(1 1)) '(2 2)))
    (is (= (update-tail '(4 1) '(3 0)) '(3 0)))))
