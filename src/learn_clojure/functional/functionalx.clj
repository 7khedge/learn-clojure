(ns learn-clojure.functional.functionalx)

; max functions returns the maximum from the list arguments presented to it
(max 1 2 3)
; => 3
; in the case where a collection is supplied the collection is returned
(max [1 2 3])
; => [1 2 3]
; using apply allow a function to be applied to each element in a collection
(apply max [1 2 3])
; => 3

; using apply and conj to write a into function
; see collectionx for more detail on conj
(defn my-into
  [target additions]
  (apply conj target additions))

(my-into [0] [1 2 3])
; => [0 1 2 3]

;; partial

; partial takes a function and arguments and returns a new function
; when the new function is called the orignal function with orignal arguments and the ones supplied to the new function
(def add10 (partial + 10))
(add10 3)
; => 13
(add10 5)
; => 15

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtainium" "adamantium")
; => ["water" "earth" "air" "unobtainium" "adamantium"]

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))
(add20 3)
; => 23

(fn [& more-args]
  (apply + (into [20] more-args)))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "Red light ahead")
; => "red light ahead"

; complement function

(defn identify-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

(def not-vampire? (complement vampire?))
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (complement neg?))
(my-pos? 1)
; => true

(my-pos? -1)
; => false

; function showing the benefit of immutability with function composition
(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

;(clean "My boa constrictor is so sassy lol!  ")
; => "My boa constrictor is so sassy LOL!"

