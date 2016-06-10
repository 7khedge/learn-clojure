(ns learn-clojure.excercises.chap5.core
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

;          1
;        2   3
;      4   5   6
;    7   8   9   10
;  11  12  13  14  15


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

;        1
;      2   3
;    4   5   6
;  7   8   9   10
; 11  12  13  14  15

(defn add-pos
  "Pegs the position and performs connections"
  [board max-pos pos]
  (let [pegged-board (assoc-in board [pos :pegged] true)]
    (reduce (fn [new-board connector] (connector new-board max-pos pos))
            pegged-board
            [connect-right connect-down-left connect-down-right])))

;(add-pos {} 15 1)
; => {1 {:pegged true, :connections {4 2, 6 3}},
;     4 {:connections {1 2}},
;     6 {:connections {1 3}}}

(defn new-board
  [rows]
  (let [initial-board {:rows rows}
        max-pos (row-tri rows)]
    (reduce (fn [board pos] (add-pos board max-pos pos))
            initial-board
            (range 1 (inc max-pos)))))


;(new-board 5)
;
;{7 {:connections {2 4, 9 8}, :pegged true},
; 1 {:pegged true, :connections {4 2, 6 3}},
; 4 {:connections {1 2, 6 5, 11 7, 13 8}, :pegged true},
; 15 {:connections {6 10, 13 14}, :pegged true},
; 13 {:connections {4 8, 6 9, 11 12, 15 14}, :pegged true}, :rows 5,
; 6 {:connections {1 3, 4 5, 13 9, 15 10}, :pegged true},
; 3 {:pegged true, :connections {8 5, 10 6}},
; 12 {:connections {5 8, 14 13}, :pegged true},
; 2 {:pegged true, :connections {7 4, 9 5}},
; 11 {:connections {4 7, 13 12}, :pegged true},
; 9 {:connections {2 5, 7 8}, :pegged true},
; 5 {:pegged true, :connections {12 8, 14 9}},
; 14 {:connections {5 9, 12 13}, :pegged true},
; 10 {:connections {3 6, 8 9}, :pegged true},
; 8 {:connections {3 5, 10 9}, :pegged true}}

;;;;
;; Move pegs
;;;;

(defn pegged?
  "Does the position have a peg in it?"
  [board pos]
  (get-in board [pos :pegged]))

(defn valid-moves
  "Return a map of all valid moves for pos, where the key is the
  destination and the value is the jumped position"
  [board pos]
  (into {}
        (filter (fn [[destination jumped]]
                  (and (not (pegged? board destination))
                       (pegged? board jumped)))
                (get-in board [pos :connections]))))


; create a board of 5 rows with no peg in posistion 4
(def my-board (assoc-in (new-board 5) [4 :pegged] false))

(defn valid-move?
  "Return jumped position if the move from p1 to p2 is valid, nil
  otherwise"
  [board p1 p2]
  (get (valid-moves board p1) p2))

;        1
;      2   3
;    4*  5   6
;  7   8   9   10
;11  12  13  14  15

;(valid-move? my-board 11 4)
; => 7
;(valid-move? my-board 1 4)
; => 2

; this method sets pegged to false for the pos and returns the new board
(defn remove-peg
  "Take the peg at given position out of the board"
  [board pos]
  (assoc-in board [pos :pegged] false))

; this method sets pegged to true for the pos and returns the new board ;
(defn place-peg
  "Put a peg in the board at given position"
  [board pos]
  (assoc-in board [pos :pegged] true))

; this removed peg from p1 and place peg in p2 and returns the new board
(defn move-peg
  "Take peg out of p1 and place it in p2"
  [board p1 p2]
  (place-peg (remove-peg board p1) p2))

; this removes the peg from jumped pos
; moves peg from p1 to p2
; returns the new board
(defn make-move
  "Move peg from p1 to p2, removing jumped peg"
  [board p1 p2]
  (if-let [jumped (valid-move? board p1 p2)]
    (move-peg (remove-peg board jumped) p1 p2)))

(defn can-move?
  "Do any of the pegged positions have valid moves?"
  [board]
  (some (comp not-empty (partial valid-moves board))
        (map first (filter #(get (second %) :pegged) board))))

;(map first (filter #(get (second %) :pegged) board))
;filter returns a lazy sequence for which the function is used to filter out elements
;

;;;;
;; Represent board textually and print it
;;;;

(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))
(def pos-chars 3)

(def ansi-styles
  {:red   "[31m"
   :green "[32m"
   :blue  "[34m"
   :reset "[0m"})

(defn ansi
  "Produce a string which will apply an ansi style"
  [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  "Apply ansi color to text"
  [text color]
  (str (ansi color) text (ansi :reset)))

(defn render-pos
  [board pos]
  (str (nth letters (dec pos))
       (if (get-in board [pos :pegged])
         (colorize "0" :blue)
         (colorize "-" :red))))

(defn row-positions
  "Return all positions in the given row"
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))

(defn row-padding
  "String of spaces to add to the beginning of a row to center it"
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) pos-chars) 2)]
    (apply str (take pad-length (repeat " ")))))

(defn render-row
  [board row-num]
  (str (row-padding row-num (:rows board))
       (clojure.string/join " " (map (partial render-pos board) (row-positions row-num)))))

(defn print-board
  [board]
  (doseq [row-num (range 1 (inc (:rows board)))]
    (println (render-row board row-num))))