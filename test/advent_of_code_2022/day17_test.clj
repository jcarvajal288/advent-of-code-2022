(ns advent-of-code-2022.day17-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day17 :refer :all]))

(deftest day17-example-test
  (testing "day17-example-test"
    (is (= (height-of-rock-tower "day17-example-data.txt") 3068))))
