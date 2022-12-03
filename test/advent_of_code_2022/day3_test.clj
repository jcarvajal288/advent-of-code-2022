(ns advent-of-code-2022.day3-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day3 :refer :all]
            ))

(deftest day3-sample-test
  (testing "day3-sample-test"
    (is (= (sum-priorities "day3-example-data.txt") 157))))

(deftest day3-full-test
  (testing "day3-full-test"
    (is (= (sum-priorities "day3-full-data.txt") 7763))))

(deftest day3-sample-test-part2
  (testing "day3-sample-test-part2"
    (is (= (sum-priorities-of-group-badges "day3-example-data.txt") 70))))

(deftest day3-full-test-part2
  (testing "day3-full-test-part2"
    (is (= (sum-priorities-of-group-badges "day3-full-data.txt") 2569))))


(deftest find-common-element-test
  (testing "find-common-element"
    (is (= (find-common-element [[\a \b \c] [\d \c \e]]) \c))))

(deftest find-common-element-test-many
  (testing "find-common-element-many"
    (is (= (find-common-element [[\a \b \c] [\d \c \e] [ \f \g \c]]) \c))))

(deftest group-by-threes-test
  (testing "group-by-threes"
    (is (= (group-by-threes [1 2 3 4 5 6 7 8 9]) [[1 2 3] [4 5 6] [7 8 9]]))))

(deftest get-priority-test
  (testing "get-priority-test"
    (is (= (get-priority \a) 1))
    (is (= (get-priority \z) 26))
    (is (= (get-priority \A) 27))
    (is (= (get-priority \Z) 52))))
