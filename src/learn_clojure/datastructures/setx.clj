(ns learn-clojure.datastructures.setx)

;; Sets are super cool. Here's some set usage
;; create list of unique values
;; set can be created using #{ ..  }
#{"kurt vonnegut" 20 :icicle}
; => #{20 :icicle "kurt vonnegut"}

;;also a create a set using hash-set or sorted-set
(hash-set 1 1 2 2)
; => #{1 2}

(sorted-set 3 4 3 5 6 7 2)
; => #{2 3 4 5 6 7}

;; use conj to add a entry to a set
(conj #{1 2 } 3)
; => #{1 3 2}

(conj #{:a :b} :b)
; => #{:a :b}

; create a set from a vector
(set [3 3 3 4 4])
; => #{3 4}

; check membership using contains?
(contains? #{:a :b} :a)
; => true

(contains? #{:a :b} 3)
; => false

(contains? #{nil} nil)
; => true

(:a #{:a :b})
; => :a

;; using get to check the memberhsip of a set simply return the value if it is a member
(get #{:a :b} :a)
; => :a

;; better to use contains to check for membership of a set as get will return nill
(get #{:a nil} nil)
; => nil

(get #{:a :b} "kurt vonnegut")
; => nil