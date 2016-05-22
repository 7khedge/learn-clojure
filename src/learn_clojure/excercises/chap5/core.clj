(ns learn-clojure.excercises.chap5
  ;; import functions to be used
  (require [clojure.set :as set])
  ;;Allows namespace to be run from the command line
  (:gen-class))

;;allows functions to refer to those names before they're defined
(declare successful-move prompt-move game-over query-rows)

(defn tri*
  "Generates lazy sequence of triangular numbers"
  ([] (tri* 0 1))
  ([sum n]
    (let [new-sum (+ sum n)]
      (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))










