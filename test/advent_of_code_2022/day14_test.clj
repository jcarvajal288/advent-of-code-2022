(ns advent-of-code-2022.day14-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day14 :refer :all]))

(deftest day14-example-test
  (testing "day14-example-test"
    (is (= (run-simulation [500 0] "day14-example-data.txt") 24))))

(deftest day14-full-test
  (testing "day14-full-test"
    (is (= (run-simulation [500 0] "day14-full-data.txt") 1406))))
