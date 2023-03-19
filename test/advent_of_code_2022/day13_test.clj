(ns advent-of-code-2022.day13-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2022.day13 :refer :all]
            ))

(deftest packet-in-right-order?-test
  (testing "packet-in-right-order"
    ;(is (= (packet-in-right-order? ["[1,1,3,1,1]" "[1,1,5,1,1]"]) true))
    ;(is (= (packet-in-right-order? ["[[1],[2,3,4]]" "[[1],4]"]) true))
    ;(is (= (packet-in-right-order? ["[[1],4]" "[[1],[2,3,4]]"]) false))
    ;(is (= (packet-in-right-order? ["[9]" "[[8,7,6]]"]) false))
    ;(is (= (packet-in-right-order? ["[[4,4],4,4]" "[[4,4],4,4,4]"]) true))
    ;(is (= (packet-in-right-order? ["[7,7,7,7]" "[7,7,7]"]) false))
    ;(is (= (packet-in-right-order? ["[]" "[3]"]) true))
    ;(is (= (packet-in-right-order? ["[5,6,6,7,3]" "[5,6,6,7]"]) false))
    ;(is (= (packet-in-right-order? ["[[7, 6, 5], [9, 1]]" "[[[3, 2, [1, 0, 9, 2, 7], 4, 2], [[4, 10, 3, 4], 6, [0, 4]], [], [9, [1, 0], []]], [], [5, [4, [4, 10, 9, 6, 3], 3], [[8, 2, 8], [10, 7, 7, 1], 10, [], 5], [9], 9], [0, 7, 3, 5, 10]]"]) false))
    ;(is (= (packet-in-right-order? ["[[[0, 4], [[10, 8, 6, 3], 7], 3], [], [[], [[2, 5, 3], [], [6, 7, 10, 7], 5, 7]], [5, 9, [[], [4, 3], 3, [1, 4, 3]], 3], [10, 5, 3, 5, [9, 2, [9, 9], 10, [3, 8, 9, 8, 5]]]]" "[[[6, 2, [8, 2, 4, 6]], [9, 6, [], []], [2, [9, 7, 8], [8, 8, 5, 7], [6, 7, 0, 10]], [[0, 7]], 8], [[[6], [9], 1], [[], 5]], [[7, 0, [4, 8], 10], 5, 7, 8]]"]) true))
    ;(is (= (packet-in-right-order? ["[[[[6, 10, 2, 9, 8], 9], [[9, 7, 6], 4, [2, 10, 5], []], 8, 4, [[7, 1, 8], [0], [5]]]]" "[[8, [2, [2], 8, []]], [[3, [10, 0, 8, 3, 9]]], [[4], [], [[7, 6, 8, 10], 3, [7, 6], [0, 8]]]]"]) true))

    ;(is (= (packet-in-right-order? ["[[3, [7], [0], 7]]" "[[5, 2]]"]) true))
    ;(is (= (packet-in-right-order? ["[[1, [2, [10, 8, 2, 1, 1]], 0]]" "[[[1]], [[[2, 4, 10, 2], []], 3, 8], [9, 3, [5, [3, 0], [0], [4]], 6, [[9, 8, 3, 7], 4, [10, 10, 8], 10, [6, 6]]], [[[3], 7, [], [10, 5]], 0], [5, [[3, 9, 0, 2, 1], 0, [4, 5, 2], [6]]]]"]) false))

    ;(is (= (packet-in-right-order? ["[]" "[]"]) true))
    ;(is (= (packet-in-right-order? ["[[[[7], [2, 5], [4, 1, 10, 9]], [[], [6, 0, 2, 1], [0], [7, 0], 9], 8, [6], 9], [4, [], []], [2]]" "[[7], [[6, 6]]]"]) false))
    ;(is (= (packet-in-right-order? ["[[1],[2,3,4]]" "[[1],2,3,4]"]) false))

    ;(is (= (packet-in-right-order? ["[[[10,3,6,[4,5]]],[2]]" "[[10,[8,1,1,5,2]]]"]) false))
    (is (= (packet-in-right-order? ["[[2],[7]]" "[2,6]"]) true))
    ;(is (= (packet-in-right-order? ["[[], [2,7]]" "[[2],[6]]"]) true))
    ;(is (= (packet-in-right-order? ["[2,7]" "[2,[6]]"]) false))
    ;(is (= (packet-in-right-order? ["[[[]]]" "[[]]"]) false))
    ;(is (= (packet-in-right-order? ["[1,[2,[3,[4,[5,6,7]]]],8,9]" "[1,[2,[3,[4,[5,6,0]]]],8,9]"]) false))
    ))

(deftest day13-example-test
  (testing "day13-example-test"
    (is (= (sum-of-indices-of-right-order-pairs "day13-example-data.txt") 13))))

(deftest day13-full-test
  (testing "day13-full-test"
    (is (= (sum-of-indices-of-right-order-pairs "day13-full-data.txt") 5882))))

(deftest day13-example-test-part2
  (testing "day13-example-test-part2"
    (is (= (product-of-indices "day13-example-data.txt") 140))))

(deftest day13-full-test-part2
  (testing "day13-full-test-part2"
    (is (= (product-of-indices "day13-full-data.txt") 140))))
