(ns advent-of-code-2022.day1-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day1 :refer :all]
            [taoensso.timbre :as log]))

(deftest day1-sample-test
  (testing "day1-sample-test"
    (is (= (most-carried-calories "day1-example-data.txt") 24000))))

(deftest day1-full-test
  (testing "day1-full-test"
    (is (= (most-carried-calories "day1-full-data.txt") 71780))))

(deftest day1-part2-sample-test
  (testing "day1-part2-sample-test"
    (is (= (sum-top3-carried-calories "day1-example-data.txt") 45000))))

(deftest day1-part2-full-test
  (testing "day1-part2-full-test"
    (is (= (sum-top3-carried-calories "day1-full-data.txt") 212489))))

