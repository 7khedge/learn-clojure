(ns learn-clojure.datastructures.vectorx)

;; Vector time!
;; 0 index array
[3 2 1]

;; the same get function used for map works for vector
(get [3 2 1] 0)
; => 3

;; vector can contain multiple types
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
; => {:name "Pugsley Winterbottom"}

;; create a vector with the vector function
(vector "creepy" "full" "moon")
; => ["creepy" "full" "moon"]

;;conj add an enrty to add of the vector
(conj [1 2 3] 4)
; => [1 2 3 4]