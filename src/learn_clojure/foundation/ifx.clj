(ns learn-clojure.foundation.ifx)

;; General structure of `if`
(if boolean-form
  then-form
  optional-else-form)

;; if examples
(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")
; => "By Zeus's hammer!"

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")
; => "By Aquaman's trident!"


(if false
  "By Odin's Elbow!")
; => nil


;;do operator
;; do lets wrap multiple expressions hence allows mutiple expressions to be executed
;; for each if forms
(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))
; => Success!
; => "By Zeus's hammer!"


;; More boolean operators
(when true
  (println "Success!")
  "abra cadabra")
; => Success!
; => "abra cadabra"


(nil? 1)
; => false

(nil? nil)
; => true

(if "bears eat beets"
  "bears beets Battlestar Galactica")
; => "bears beets Battlestar Galactica"

(if nil
  "This won't be the result because nil is falsey"
  "nil is falsey")
; => "nil is falsey"

(= 1 1)
; => true

(= nil nil)
; => true

(= 1 2)
; => false

;;the first truthy value is returned
(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
; => :large_I_mean_venti

;; as no truthy values exist the last value is returned
(or (= 0 1) (= "yes" "no"))
; => false

;; again no truthy values exist so the last value is returned
(or nil)
; => nil

;; no false value found so that last value returned
(and :free_wifi :hot_coffee)
; => :hot_coffee

;; first false value returned
(and :feelin_super_cool nil false)
; => nil