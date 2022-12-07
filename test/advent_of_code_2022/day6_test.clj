(ns advent-of-code-2022.day6-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day6 :refer :all]
            [advent-of-code-2022.util :refer :all]
            ))

(deftest day6-sample-test
  (testing "day6-sample-test"
    (is (= (find-marker-position "mjqjpqmgbljsphdztnvjfqwrcgsmlb") 7))
    (is (= (find-marker-position "bvwbjplbgvbhsrlpgdmjqwftvncz") 5))
    (is (= (find-marker-position "nppdvjthqldpwncqszvftbrmjlhg") 6))
    (is (= (find-marker-position "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") 10))
    (is (= (find-marker-position "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") 11))
    ))

(deftest day6-full-test
  (testing "day6-full-test"
    (is (= (find-marker-position (first (get-resource-file-by-line "day6-full-data.txt"))) 1804))))

(deftest day6-sample-test-part2
  (testing "day6-sample-test-part2"
    (is (= (find-marker-position "mjqjpqmgbljsphdztnvjfqwrcgsmlb") 19))
    (is (= (find-marker-position "bvwbjplbgvbhsrlpgdmjqwftvncz") 23))
    (is (= (find-marker-position "nppdvjthqldpwncqszvftbrmjlhg") 23))
    (is (= (find-marker-position "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") 29))
    (is (= (find-marker-position "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") 26))
    ))
