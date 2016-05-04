(ns learn-clojure.datastructures.intox)

;; into copies the elements from one collection to another one
(into [ 1 2 ] '( 3 4))
; => [1 2 3 4]

;;
(into () '(1 2 3))
; => (3 2 1)