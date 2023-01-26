(ns advent-of-code-2022.day7-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day7 :refer :all]
            [advent-of-code-2022.util :refer :all]
            ))

(deftest construct-dir-tree-test
  (testing "construct dir tree"
    (let [dir-tree (construct-dir-tree (get-resource-file-by-line "day7-sample-data.txt"))]
      (is (= (dir-size (get dir-tree "e") 584)))
      (is (= (dir-size (get dir-tree "a") 94853)))
      (is (= (dir-size (get dir-tree "d") 24933642)))
      (is (= (dir-size (get dir-tree "/") 48381165))))))

(deftest day7-sample-test
  (testing "day7-sample-test"
    (is (= (total-dir-size 100000 (get-resource-file-by-line "day7-sample-data.txt")) 95437))))
