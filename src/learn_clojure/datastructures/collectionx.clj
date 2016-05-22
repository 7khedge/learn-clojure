(ns learn-clojure.datastructures.collectionx)

;; Collection abstraction operates on the whole datastucture where as sequence abstraction operates on the elements of
;; the datastructure. Vectors, maps, lists and sets take part in both abstractions

(empty? [])                                                 ;vector
(empty? '())                                                ;list
(empty? {})                                                 ;map
(empty? #{})                                                ;set
; => true

(empty? ["no!"])                                            ;vector
(empty? '(1))                                               ;list
(empty? {:b 1})                                             ;map
(empty? {1 2})                                              ;set
; => false


(type #{})
;=> clojure.lang.PersistentHashSet
(type {})
;=> clojure.lang.PersistentArrayMap
(type '())
;=> clojure.lang.PersistentList$EmptyList
(type '(2))
;=> clojure.lang.PersistentList
(type [])
;=> clojure.lang.PersistentVector


; map returns a sequence rather than the original data structure
(map identity {:sunlight-reaction "Glitter!"})
; => ([:sunlight-reaction "Glitter!"])

;into allows the sequence returned by map being converted back into a datastructure
; below shows how using into the result from map can be converted to a map collection
(into {} (map identity {:sunlight-reaction "Glitter!"}))
; => {:sunlight-reaction "Glitter!"}

; identity simply returns its argument
(map identity [:garlic :sesame-oil :fried-eggs])
; => (:garlic :sesame-oil :fried-eggs)

(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
; => [:garlic :sesame-oil :fried-eggs]

(map identity [:garlic-clove :garlic-clove])
; => (:garlic-clove :garlic-clove)

; below converts the results from map into a set
(into #{} (map identity [:garlic-clove :garlic-clove]))
; => #{:garlic-clove}

; into can also append to a collection already with elements
(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
; => {:favorite-emotion "gloomy" :sunlight-reaction "Glitter!"}


; into can be used to add elements from one collection to aother collection
; into arguments to into can be of the same type or different types
(into ["cherry"] '("pine" "spruce"))
; => ["cherry" "pine" "spruce"]

; into appending entries from a map into a map
(into {:favorite-animal "kitty"} {:least-favorite-smell "dog"
                                  :relationship-with-teenager "creepy"})
; => {:favorite-animal "kitty"
; =>  :relationship-with-teenager "creepy"
; =>  :least-favorite-smell "dog"}


(conj [0] [1])
; => [0 [1]]

(into [0] [1])
; => [0 1]

(conj [0] 1)
; => [0 1]

; conj takes a collection as the first parameter x and adds the rest of the arguments xs to the collection
(conj [0] 1 2 3 4)
; => [0 1 2 3 4]

; conj can even add elements from another collection
(conj {:time "midnight"} [:place "ye olde cemetarium"])
; => {:place "ye olde cemetarium" :time "midnight"}


; conj and into are similar, conj uses the rest parameter whereas into uses a sequable data structure
(defn my-conj
  [target & additions]
  (into target additions))

(my-conj [0] 1 2 3)
; => [0 1 2 3]
