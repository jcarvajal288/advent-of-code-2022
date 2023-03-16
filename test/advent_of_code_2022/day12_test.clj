(ns advent-of-code-2022.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day12 :refer :all]))

(deftest day12-sample-test
  (testing "day12-sample-test"
    (is (= (shortest-path-to-signal "day12-example-data.txt") 31))))

(deftest day12-full-test
  (testing "day12-full-test"
    (is (= (shortest-path-to-signal "day12-full-data.txt") 490))))
