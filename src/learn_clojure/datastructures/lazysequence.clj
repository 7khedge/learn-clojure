(ns learn-clojure.datastructures.lazysequence)
;;(:require [clojure.core :refer :all])

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

; Takes too long
;(time (vampire-related-details 0))
; => "Elapsed time: 1001.042 msecs"
; => {:name "McFishwich", :makes-blood-puns? false, :has-pulse? true}

(time (def mapped-details (map vampire-related-details (range 0 1000000))))
; => "Elapsed time: 0.049 msecs"
; => #'user/mapped-details

;; Take too long
;;(time (first mapped-details))
; => "Elapsed time: 32030.767 msecs"
; => {:name "McFishwich", :makes-blood-puns? false, :has-pulse? true}

(time (first mapped-details))
; => "Elapsed time: 0.022 msecs"
; => {:name "McFishwich", :makes-blood-puns? false, :has-pulse? true}

(time (identify-vampire (range 0 1000000)))
"Elapsed time: 32019.912 msecs"
; => {:name "Damon Salvatore", :makes-blood-puns? true, :has-pulse? false}

; Infinite Sequences

;; repeat will generate an infinite sequence if the n parameter is not supplied
(concat (take 8 (repeat "na")) ["Batman!"])
; => ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")

;;
(take 3 (repeatedly (fn [] (rand-int 10))))
; => (1 4 0)

;; Constructing an infinite sequence

;;Call Stack for (take 2 (even-numbers))
;;(even-numbers)->(even-numbers 0)-> (cons 0 (lazy-seq (even-numbers(2))))))
;;(even-numbers 2)->(cons 2 (lazy-seq (even-numbers(4)))))))
;;

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 2 (even-numbers))
; => (0 2)

(take 3 (even-numbers))
; => (0 2 4)

(take 10 (even-numbers))
; => (0 2 4 6 8 10 12 14 16 18)

;; cons returns a new list with element appends to the begininng of a list
;; This is called cosing to a fully realised list
;; even numbers cosines to a lazy sequence , which a receipe for the next element
(cons 0 '(2 4 6))
; => (0 2 4 6)