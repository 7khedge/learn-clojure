(ns learn-clojure.helloworld.helloworld
  ;; :gen-class required create helloworld.classfor java to find helloworld.class
  (:gen-class))

(def message
  "Simple Message")

(def helloMessage
  "Hello, World!")

;;'-' static method
;;entry point a function called main
(defn -main
  "Entry point to the application "
  [& args]
  (println helloMessage))


(comment
  )