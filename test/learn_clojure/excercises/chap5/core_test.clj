(ns learn-clojure.excercises.chap5.core-test
  (:require [clojure.test :refer :all]
            [learn-clojure.excercises.chap5.core :refer :all]))

(deftest shouldReturnTriangularNumbers
  (testing
    "tri should return triangular numbers"
    (is (= '(1 3 6 10 15) (take 5 tri)) "did not get 5 triangular numbers")
    (is (= '(1 3 6 10) (take 4 tri)) "did not get 4 triangular numbers")))

(def board-with-peg-removed (remove-peg (new-board 2) 2))

(deftest validMoveExists
  (testing
    "peg has been removed"
    (is (= (:pegged (get board-with-peg-removed 2)) false) "should be false")))


(def board-3 (remove-peg (new-board 3) 1))


(deftest validMovesTest
  (testing
    "Understand how can-move? works"
    (is (= (filter #(get (second %) :pegged) board-with-peg-removed) '([1 {:pegged true}] [3 {:pegged true}]) ))
    (is (= (map first (filter #(get (second %) :pegged) board-with-peg-removed)) '(1 3)))
    (is (= (map first (filter #(get (second %) :pegged) board-3)) '(4 6 2 3 5)))))
