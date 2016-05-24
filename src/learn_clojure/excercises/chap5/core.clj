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

(def tri (tri*))
;=> #'learn-clojure.excercises.chap5/tri
;(take 5 tri)
;=> (1 3 6 10 15)

(defn triangular?
  "Is the number triangular? e.g. 1, 3, 6, 10, 15, etc"
  [n]
  (= n (last (take-while #(>= n %) tri))))

;(triangular?  5)
;=> false
;(triangular? 6)
;=> true

(defn row-tri
  "The triangular number at the end of row n"
  [n]
  (last (take n tri)))

; (row-tri 1)
; => 1

; (row-tri 2)
; => 3

; (row-tri 3)
; => 6

(defn row-num
  "Returns row number the position belongs to: pos 1 in row 1,
  positions 2 and 3 in row 2, etc"
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(defn in-bounds?
  "Is every position less than or equal the max position?"
  [max-pos & positions]
  ; (println max-pos positions)
  (= max-pos (apply max max-pos positions)))

;(apply max 15 [ 2 4])
;=> 15

(defn connect
  "Form a mutual connection between two positions"
  [board max-pos pos neighbor destination]
  (if (in-bounds? max-pos neighbor destination)
    (reduce (fn [new-board [p1 p2]]
              ; (println new-board p1 p2)
              (assoc-in new-board [p1 :connections p2] neighbor))
            board
            [[pos destination] [destination pos]])
    board))

; Example of how assoic-in creates a nested map structure for the keys in the vector and the value 2
;(assoc-in {} [1 :a :b :c] 2)
;=> {1 {:a {:b {:c 2}}}}

;Therefore
;(assoc-in {} [1 :connections 4] 2)
;=> {1 {:connections {4 2}}}


;(connect {} 15 1 2 4)
;{} 1 4
;{1 {:connections {4 2}}} 4 1
;=> {1 {:connections {4 2}}, 4 {:connections {1 2}}}

          1
        2   3
      4   5   6
    7   8   9   10
  11  12  13  14  15


;{1 {:connections {4 2}},  ; to get to position  1 a peg at 4 can jump over to 2 and land on 1
; 4 {:connections {1 2}}}  ; to get to position  4 a peg at 1 can jump over to 2 and land on 4

(defn connect-right
  [board max-pos pos]
  (let [neighbor (inc pos)
        destination (inc neighbor)]
    (if-not (or (triangular? neighbor) (triangular? pos))
      (connect board max-pos pos neighbor destination)
      board)))

(defn connect-down-left
  [board max-pos pos]
  (let [row (row-num pos)
        neighbor (+ row pos)
        destination (+ 1 row neighbor)]
    (connect board max-pos pos neighbor destination)))

(defn connect-down-right
  [board max-pos pos]
  (let [row (row-num pos)
        neighbor (+ 1 row pos)
        destination (+ 2 row neighbor)]
    (connect board max-pos pos neighbor destination)))

;(connect-down-left {} 15 3)
;=> { 3 {:connections {8 5}},
;     8 {:connections {3 5}}}

;(connect-down-right {} 15 3)
;=> { 3 {:connections {10 6}},
;     10 {:connections {3 6}}}

; Actually up right
;(connect-right {} 15 8)
;=> { 8 {:connections {10 9}},
;     10 {:connections {8 9}}}

        1
      2   3
    4   5   6
  7   8   9   10
11  12  13  14  15