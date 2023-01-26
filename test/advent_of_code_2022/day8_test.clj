(ns advent-of-code-2022.day8-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day8 :refer :all]
            [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]))

(deftest day8-sample-test
  (testing "day8-sample-test"
    (is (= (visible-trees "day8-example-data.txt") 21))))

(deftest day8-full-test
  (testing "day8-full-test"
    (is (= (visible-trees "day8-full-data.txt") 1681))))

(def forest (read-forest "day8-example-data.txt"))

(deftest test-is-tree-visible-in-row
  (testing "is-tree-visible-in-row"
    (is (= (is-tree-visible-in-row [3]) true))
    (is (= (is-tree-visible-in-row [3 1 7]) false))
    (is (= (is-tree-visible-in-row [3 3 1]) false))
    (is (= (is-tree-visible-in-row [3 2 1]) true))))

(deftest test-get-tree
  (testing "get-tree"
    (log/info (vec forest))
    (is (= (get-tree forest 0 0) 3))
    (is (= (get-tree forest 1 0) 0))
    (is (= (get-tree forest 0 1) 2))
    (is (= (get-tree forest 2 2) 3))
    (is (= (get-tree forest 4 3) 9))
    (is (= (get-tree forest 1 4) 5))))

(deftest test-row-up
  (testing "row-up"
    (is (= (row-up forest 0 0) [3]))
    (is (= (row-up forest 2 2) [3 5 3]))
    (is (= (row-up forest 3 2) [7 1 3]))))

(deftest test-row-right
  (testing "row-right"
    (is (= (row-right forest 0 0) [3 7 3 0 3]))
    (is (= (row-right forest 4 0) [3]))))

(deftest test-row-down
  (testing "row-down"
    (is (= (row-down forest 0 0) [3 3 6 2 3]))
    (is (= (row-down forest 0 4) [3]))))

(deftest test-row-left
  (testing "row-left"
    (is (= (row-left forest 0 0) [3]))
    (is (= (row-left forest 4 0) [3 0 3 7 3]))))

(deftest test-get-rows-for-tree
  (testing "get-rows-for-tree"
    (is (= (get-rows-for-tree forest 0 0) [[3] [3 0 3 7 3] [3 2 6 3 3] [3]]))
    (is (= (get-rows-for-tree forest 2 2) [[3 5 3] [3 3 2] [3 5 3] [3 5 6]]))))

(deftest test-is-tree-visible
  (testing "is-tree-visible"
    (is (= (is-tree-visible forest 1 1) true))
    (is (= (is-tree-visible forest 2 1) true))
    (is (= (is-tree-visible forest 3 1) nil))
    (is (= (is-tree-visible forest 1 2) true))
    (is (= (is-tree-visible forest 2 2) nil))
    (is (= (is-tree-visible forest 3 2) true))
    (is (= (is-tree-visible forest 2 3) true))
    (is (= (is-tree-visible forest 1 3) nil))
    (is (= (is-tree-visible forest 3 3) nil))))