(ns learn-clojure.functional.recurx)


(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (println vals accumulating-total)
   (if (empty? vals)                                        ;recursive base condition
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

;(sum [39 5 1]) ; single-arity body calls two-arity body
;(sum [39 5 1] 0)
;(sum [5 1] 39)
;(sum [1] 44)
;(sum [] 45) ; base case is reached, so return accumulating-total
; => 45

;actual output
;(sum [39 5 1])
;[39 5 1] 0
;(5 1) 39
;(1) 44
;() 45
;=> 45

