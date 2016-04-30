(ns learn-clojure.datastructures.seqx)

;;Cloujure can treats all data structures as sequences when the
;; use of the sequence abstraction is required
;; cons,rest,first

;;list treated as list
(seq '(1 2 3))
; => (1 2 3)

;;vector treated as list
(seq [1 2 3])
; => (1 2 3)

;;set treated as list
(seq #{1 2 3})
; => (1 2 3)

;;map treated as list of key-value vectors
(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
; => ([:name "Bill Compton"] [:occupation "Dead mopey guy"])

;; converting a (seq { .. } ) back into {}
;; {} = map
(into {} (seq {:a 1 :b 2 :c 3}))
; => {:a 1, :c 3, :b 2}
