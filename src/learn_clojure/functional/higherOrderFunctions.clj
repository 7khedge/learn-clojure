(ns learn-clojure.functional.higherOrderFunctions)


;; Compose function
; comp combines inc and * , calling * first on two parameters and then calliing the inc on the result.
; (* 2 3) => 6 ( inc 6 ) => 7
((comp inc *) 2 3)
; => 7


(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
; using comp to chain functions and apply them to a dataset and passing the result from one to function to the next
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
; => 10

(c-str character)
; => 4

(c-dex character)
; => 5


(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(spell-slots character)
; => 6

; combines four functions
(def spell-slots-comp (comp int inc #(/ % 2) c-int))

;(c-int character)
;=> 10
;(#(/ % 2) 10)
;=> 5
;(inc 5)
;=> 6
;(int 6)
;=> 6