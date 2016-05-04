(ns learn-clojure.functional.reducex)

(reduce + [1 2 3 4])
;=> 10
(reduce + 15 [1 2 3 4])
;=> 25

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; => {:max 31, :min 11}

(assoc (assoc {} :max (inc 30))
  :min (inc 10))

;example of destructing
(reduce (fn [new-map [key val]]
          (println new-map key val "\n" )
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9})
; => {:human 4.1}

;; For further example on how to use reduce and a implementation
;; see chap3 execrcise.
