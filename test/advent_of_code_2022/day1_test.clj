(ns advent-of-code-2022.day1-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day1 :refer :all]
            [taoensso.timbre :as log]))

(deftest day1-sample-test
  (testing "day1-sample-test"
    ;(log/info (most-carried-calories "day1-example-data.txt"))
    (is (= (most-carried-calories "day1-example-data.txt") 24000))))

(deftest day1-full-test
  (testing "day1-full-test"
    ;(log/info (most-carried-calories "day1-example-data.txt"))
    (is (= (most-carried-calories "day1-full-data.txt") 71780))))
