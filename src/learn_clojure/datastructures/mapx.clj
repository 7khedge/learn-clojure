(ns learn-clojure.datastructures.mapx)

;; Maps now
{}

{:first-name "Charlie" :last-name "McFishwich"}

{"string-key" +}

;; you can have nested maps
{:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}

;; generate a map using hash-map function
(hash-map :a 1 :b 2)
; => {:a 1 :b 2}

(get {:a 0 :b 1} :b)
; => 1

;; retrieving a nested map entry
(get {:a 0 :b {:c "ho hum"}} :b)
; => {:c "ho hum"}

;; nil returned when map entry not found
(get {:a 0 :b 1} :c)
; => nil

;; default returned for non existing map entry when specified
(get {:a 0 :b 1} :c "unicorns?")
; => "unicorns?"

;; retrieving a nested map value by specifying the keyword path to the get-in function
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
; => "ho hum"


;; Use map as a function and supply a keyword as the parameter
;; same as get above
({:name "The Human Coffeepot"} :name)
; => "The Human Coffeepot"


;; Here we're looking at keywords
:a
:rumplestiltsken
:34
:_?


;;Using the keyword as a function and supplying the map as the parameter
(:a {:a 1 :b 2 :c 3})
; => 1

;; the above is the same as
(get {:a 1 :b 2 :c 3} :a)
; => 1

;; you can also supply a default value.
(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
; => "No gnome knows homes like Noah knows"


;;map is implemented using the sequence (seq) abstraction methods first,rest and cons

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

;; map on a vector
(map titleize ["Hamsters" "Ragnarok"])
; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")

;;map on a list
(map titleize '("Empathy" "Decorating"))
; => ("Empathy for the Brave and True" "Decorating for the Brave and True")

;;map on a set
(map titleize #{"Elbows" "Soap Carving"})
; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")


;;map on a map
;; why is second used
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})
; => ("Winking for the Brave and True")

;;map take the function and apply it to each element of the vector
(map inc [1 2 3])
; => (2 3 4)

;;map take the function str and apply its to corresponding element of the both vectors
;; effectivley doing this:-
;; (list (str "a" "A") (str "b" "B") (str "c" "C"))
(map str ["a" "b" "c"] ["A" "B" "C"])
; => ("aA" "bB" "cC")


;;using the above behaviour we can do the following
(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0}
; => {:human 7.3, :critter 0.2}
; => {:human 6.6, :critter 0.3}
; => {:human 5.0, :critter 1.8})

;;We can map a list of functions to each element of a sequence
;; in this case vector is being used
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)


(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")
