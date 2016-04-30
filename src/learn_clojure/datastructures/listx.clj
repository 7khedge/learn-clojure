(ns learn-clojure.datastructures.listx)

;; List time!
;; create a list using quote in front of a brackets
'(1 2 3 4)
; => (1 2 3 4)

;; extract a valye of a list using nth
;; however slower than vector index based reading compared literating over each of the list
;; until the nth variable is reached
(nth '(:a :b :c) 0)
; => :a

(nth '(:a :b :c) 2)
; => :c

;;a list can be created with the list function and a list can contain any type
(list 1 "two" {3 4})
; => (1 "two" {3 4})


;; conj add a value to the begining of the list unlike on a vector where it is added to the end
(conj '(1 2 3) 4)
; => (4 1 2 3)