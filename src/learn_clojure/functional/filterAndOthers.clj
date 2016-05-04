(ns learn-clojure.functional.filterAndOthers)

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
{:month 1 :day 2 :human 5.1 :critter 2.0}
{:month 2 :day 1 :human 4.9 :critter 2.1}
{:month 2 :day 2 :human 5.0 :critter 2.5}

(drop-while #(< (:month %) 3) food-journal)
; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
{:month 3 :day 2 :human 4.0 :critter 3.8}
{:month 4 :day 1 :human 3.7 :critter 3.9}
{:month 4 :day 2 :human 3.7 :critter 3.6}

(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
{:month 2 :day 2 :human 5.0 :critter 2.5}
{:month 3 :day 1 :human 4.2 :critter 3.3}
{:month 3 :day 2 :human 4.0 :critter 3.8}

(filter #(< (:human %) 5) food-journal)
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
{:month 3 :day 1 :human 4.2 :critter 3.3}
{:month 3 :day 2 :human 4.0 :critter 3.8}
{:month 4 :day 1 :human 3.7 :critter 3.9}
{:month 4 :day 2 :human 3.7 :critter 3.6}

(filter #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
{:month 1 :day 2 :human 5.1 :critter 2.0}
{:month 2 :day 1 :human 4.9 :critter 2.1}
{:month 2 :day 2 :human 5.0 :critter 2.5}

(some #(> (:critter %) 5) food-journal)
; => nil

(some #(> (:critter %) 3) food-journal)
; => true

;; some returns on the first truthy value
(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}

(sort [3 1 2])
; => (1 2 3)

(sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")

(concat [1 2] [3 4])
; => (1 2 3 4)
