(ns advent-of-code-2022.day5-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day5 :refer :all]
            [advent-of-code-2022.util :refer :all]
            ))

(deftest parse-stacks-test
  (testing "parse stacks"
    (is (= (parse-stacks (get-resource-file-by-line "day5-example-data.txt"))
           {1 [\Z \N]
            2 [\M \C \D]
            3 [\P]
            }))))

(deftest parse-instructions-test
  (testing "parse instructions"
    (is (= (parse-instructions (get-resource-file-by-line "day5-example-data.txt") 5)
           '((1 2 1)
             (3 1 3)
             (2 2 1)
             (1 1 2))))))

(deftest move-crates-test
  (testing "move crates"
    (is (= (move-crates {1 [\Z \N]
                         2 [\M \C \D]
                         3 [\P]
                         }
                        '((1 2 1)
                          (3 1 3)
                          (2 2 1)
                          (1 1 2)))
             {1 [\C]
              2 [\M]
              3 [\P \D \N \Z]}))))

(deftest day5-sample-test
  (testing "day5-sample-test"
    (is (= (find-stack-tops "day5-example-data.txt") "CMZ"))))

(deftest day5-full-test
  (testing "day5-full-test"
    (is (= (find-stack-tops "day5-full-data.txt") "JRVNHHCSJ"))))
