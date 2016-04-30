(ns learn-clojure.excercises.chap3)



;; Hobbit violence!
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

;; Into

;;add a unique vector of elements to vector
(into [] (set [:a :a]))
; => [:a]

;;add elements to a vector
(into [:a] [:b :a])
; => [:a :b :a]

;; add elements of list from a nother list.
(into '(1 2) '(3 4))
;=> (4 3 1 2)

;; Loop

;;internal recursion
(defn showloop [count]
  (println "Starting Iteration")
  (loop [iteration count]                                   ;; loop acts as a recursion target
    (println (str "Iteration " iteration))
    (if (> iteration 3)
      (println "Goodbye!")
      (recur (inc iteration)))))

; (showloop 3)
; Starting Iteration
; Iteration 3
; Iteration 4
; Goodbye!
; => nil

;;recuring to the function
(defn showloopx [count]
  (println "Starting Iteration")
  (loop [iteration count]
    (println (str "Iteration " iteration))
    (if (> iteration 3)
      (println "Goodbye!")
      (showloopx (inc iteration)))))
; (showloop 3)
; Starting Iteration
; Iteration 3
; Starting Iteration
; Iteration 4
; Goodbye!
; => nil

;; Regular expressions
#"regular-expression"

(re-find #"^left-" "left-eye")
; => "left-"

(re-find #"^left-" "cleft-chin")
; => nil

(re-find #"^left-" "wongleblart")
; => nil

;; More hobbit violence
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

;; successfull match
(matching-part {:name "left-eye" :size 1})
; => {:name "right-eye" :size 1}]

;; successfull non-match
(matching-part {:name "head" :size 3})
; => {:name "head" :size 3}]


;; reduce process each element in a sequence and produce a results
;; sum with reduce
(reduce + [1 2 3 4])
; => 10

(+ (+ (+ 1 2) 3) 4)

(reduce + 15 [1 2 3 4])

;; build your own reduce function
(defn my-reduce
  ;; This is used only when initial is passed
  ([f initial coll]
   (println "3 arity" "function" initial coll)
   (loop [result initial                                    ;; target for recur function
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ;; This is used only when the initial value is not passed
  ([f [head & tail]]
   (println "2 arity" "function" head tail)
   (my-reduce f head tail)))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (my-reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))
