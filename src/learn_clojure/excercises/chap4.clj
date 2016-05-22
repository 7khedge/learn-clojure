(ns learn-clojure.excercises.chap4)

(def filename "resources/suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name          identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

;(clojure.string/split "Edward Cullen,10" #",")
;=> ["Edward Cullen" "10"]


(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

;(parse (slurp filename))
;=> (["Edward Cullen" "10"] ["Bella Swan" "0"] ["Charlie Swan" "0"] ["Jacob Black" "3"] ["Carlisle Cullen" "6"])

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         ; unmapped-row = ["Edward Cullen" "10"]
         (reduce (fn [row-map [vamp-key value]]
                   ;iteration 1 : row-map = {} vamp-key = :name value "Edward Cullen" => {:name "Edward Cullen"}
                   ;iteration 2 : row-map = row-map {:name Edward Cullen} vamp-key :glitter-index value 10 => {:name "Edward Cullen", :glitter-index 10}
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 ; returns {:name "Edward Cullen", :glitter-index 10} for the first row
                 (map vector vamp-keys unmapped-row)))
       rows))



(mapify (parse (slurp filename)))
;=>
;({:name "Edward Cullen", :glitter-index 10}
;  {:name "Bella Swan", :glitter-index 0}
;  {:name "Charlie Swan", :glitter-index 0}
;  {:name "Jacob Black", :glitter-index 3}
;  {:name "Carlisle Cullen", :glitter-index 6})

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;(glitter-filter 3 (mapify (parse (slurp filename))))
;=>
;({:name "Edward Cullen", :glitter-index 10}
;  {:name "Jacob Black", :glitter-index 3}
;  {:name "Carlisle Cullen", :glitter-index 6})
