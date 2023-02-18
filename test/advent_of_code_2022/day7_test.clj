(ns advent-of-code-2022.day7-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day7 :refer :all]
            [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]
            ))

(deftest day7-sample-test
  (testing "day7-sample-test"
    (is (= (total-dir-size 100000 (get-resource-file-by-line "day7-sample-data.txt")) 95437))))

(deftest day7-full-test
  (testing "day7-full-test"
    (is (= (total-dir-size 100000 (get-resource-file-by-line "day7-full-data.txt")) 1367870))))
