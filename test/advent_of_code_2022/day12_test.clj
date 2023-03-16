(ns advent-of-code-2022.day12-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day12 :refer :all]))

(deftest day12-sample-test
  (testing "day12-sample-test"
    (is (= (shortest-path-to-signal "day12-example-data.txt") 31))))
