(ns advent-of-code-2022.day2-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day2 :refer :all]
            ))

(deftest day2-sample-test
  (testing "day2-sample-test"
    (is (= (calculate-score "day2-example-data.txt") 15))))

(deftest day2-full-test
  (testing "day2-full-test"
    (is (= (calculate-score "day2-full-data.txt") 17189))))

(deftest day2-part2-sample-test
  (testing "day2-part2-sample-test"
    (is (= (calculate-score-2 "day2-example-data.txt") 12))))

(deftest day2-part2-full-test
  (testing "day2-part2-full-test"
    (is (= (calculate-score-2 "day2-full-data.txt") 13490))))
