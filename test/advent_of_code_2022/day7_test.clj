(ns advent-of-code-2022.day7-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day7 :refer :all]
            [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]
            ))

(def dir-tree (construct-dir-tree (get-resource-file-by-line "day7-sample-data.txt")))

(deftest construct-dir-tree-test
  (testing "construct dir tree"
    (is (= (dir-size "e" dir-tree) 584))
    (is (= (dir-size "a" dir-tree) 94853))
    (is (= (dir-size "d" dir-tree) 24933642))
    (is (= (dir-size "/" dir-tree) 48381165))))

(deftest day7-sample-test
  (testing "day7-sample-test"
    (is (= (total-dir-size 100000 (get-resource-file-by-line "day7-sample-data.txt")) 95437))))

(deftest day7-full-test
  (testing "day7-full-test"
    (is (= (total-dir-size 100000 (get-resource-file-by-line "day7-full-data.txt")) 95437))))
