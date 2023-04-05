(ns advent-of-code-2022.day15-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day15 :refer :all]))

(deftest day15-example-test
  (testing "day15-example-test"
    (is (= (count-beaconless-positions-in-row 10 "day15-example-data.txt") 26))))

(deftest day15-full-test
  (testing "day15-full-test"
    (is (= (count-beaconless-positions-in-row 2000000 "day15-full-data.txt") 4724228))))
