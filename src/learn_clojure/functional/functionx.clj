(ns learn-clojure.functional.functionx)

;; Just some more example function calls
(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])

;; You can return functions as values
;; as + is the first truthy value the + function is returned
(or + -)
; => #<core$_PLUS_ clojure.core$_PLUS_@76dace31>

;; Some neat tricks
;; Given the above the + can be returned to used on '1 2 3)
((or + -) 1 2 3)
; => 6

;; and returns the first falsely or last truthy value
;; hence + is returned
((and (= 1 1) +) 1 2 3)
; => 6

;; vector can also hold functions
;; the first returns + to operation on '1 2 3)
((first [+ 0]) 1 2 3)
; => 6

;; The above shows
;;  functions can be treated as values
;;  funciions can be returned
;;  functions can be passed as parameters
;; Clojure treats functions for firs-class citzens


;; Higher-order function examples
(inc 1.1)
; => 2.1

;; map applies the function inc across elements in the vector
(map inc [0 1 2 3])
; => (1 2 3 4)


;; Demonstrating recursive evaluation
(+ (inc 199) (/ 100 (- 7 2)))
(+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
(+ 200 (/ 100 5)) ; evaluated (- 7 2)
(+ 200 20) ; evaluated (/ 100 5)
220 ; final evaluation

;; Arity examples
(defn no-params
  []
  "I take no parameters!")
(defn one-param
  [x]
  (str "I take one parameter: " x))
(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
       "together to spite you! " x y))

(defn multi-arity
  ;; 3-arity arguments and body
  ([first-arg second-arg third-arg]
   (do-things first-arg second-arg third-arg))
  ;; 2-arity arguments and body
  ([first-arg second-arg]
   (do-things first-arg second-arg))
  ;; 1-arity arguments and body
  ([first-arg]
   (do-things first-arg)))


;; Using arity to provide a default value for an argument
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(x-chop "Kanye West" "slap")
; => "I slap chop Kanye West! Take that!"

(x-chop "Kanye East")
; => "I karate chop Kanye East! Take that!"


;; Rest parameters
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Billy" "Anne-Marie" "The Incredible Bulk")
; => ("Get off my lawn, Billy!!!"
; =>  "Get off my lawn, Anne-Marie!!!"
; =>  "Get off my lawn, The Incredible Bulk!!!")


(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")
; => "Hi, Doreen, here are my favorite things: gum, shoes, kara-te"


;; Destructuring
;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)

(my-first ["oven" "bike" "war-axe"])
; => "oven"

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])
; => Your first choice is: Marmalade
; => Your second choice is: Handsome Jack
; => We're ignoring the rest of your choices. Here they are in case \
; => you need to cry over them: Pigpen, Aquaman

;; Destructuring maps
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})
; => Treasure lat: 100
; => Treasure lng: 50

(defn announce-treasure-location
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

;; destruct the a map into keywords into lat and lng and also keep reference to the map
;; using the  :as keyword
(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

  ;; Clojure automatically the value of the last form evaluatedFunction bodies return the last value
  (defn illustrative-function
    []
    (+ 1 304)
    30
    "joe")

  (illustrative-function)
  ; => "joe"

  (defn number-comment
    [x]
    (if (> x 6)
      "Oh my gosh! What a big number!"
      "That number's OK, I guess"))

  (number-comment 5)
  ; => "That number's OK, I guess"

  (number-comment 7)
  ; => "Oh my gosh! What a big number!"


;; Anonymous functions
(fn [param-list]
  function body)

(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])
; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

((fn [x] (* x 3)) 8)
; => 24


(def my-special-multiplier (fn [x] (* x 3)))
(my-special-multiplier 12)
; => 36


;; Compact anonymous function
;; % represents the first input parameter to the function
#(* % 3)

(#(* % 3) 8)
; => 24

(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])
; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

;; Function call
(* 8 3)

;; Anonymous function
#(* % 3)


;;if Anonymous function take more than one parameter
;; then use %1,%2 etc..
(#(str %1 " and " %2) "cornbread" "butter beans")
; => "cornbread and butter beans"

;; %& means the rest of parameters
;;identity returns its argument
(#(identity %&) 1 "blarg" :yip)
; => (1 "blarg" :yip)

;; Returning functions
;; function which returns a function
;; allows for the function returned to be configured
;; The returned functions are closure, which menas the function has access to all variables in scope when it was created
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

;; configuring the return function
(def inc3 (inc-maker 3))

;; then using the configured function
(inc3 7)
; => 10
