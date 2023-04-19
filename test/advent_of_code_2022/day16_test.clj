(ns advent-of-code-2022.day16-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day16 :refer :all]))

(deftest day16-example-test
  (testing "day16-example-test"
    (is (= (most-possible-pressure "day16-example-data.txt" "AA" 30) 1651))))

(deftest day16-full-test
  (testing "day16-full-test"
    (is (= (most-possible-pressure "day16-full-data.txt" "AA" 30) 1716))))
