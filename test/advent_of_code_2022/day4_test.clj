(ns advent-of-code-2022.day4-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day4 :refer :all]
            ))


(deftest day4-sample-test
  (testing "day4-sample-test"
    (is (= (count-fully-contained-ranges "day4-example-data.txt") 2))))

(deftest day4-full-test
  (testing "day4-full-test"
    (is (= (count-fully-contained-ranges "day4-full-data.txt") 2))))
