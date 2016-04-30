(ns learn-clojure.helloworld.helloworld_test
  (:require [clojure.test :refer :all]
            [learn-clojure.helloworld.helloworld :refer :all]))


(deftest shouldReturnHelloMessage
  (testing
    "simple assert on the return of message"
    (is (= helloMessage "Hello, World!"))))

(deftest shouldReturnSimpleMessage
  (testing
    "simple assert on the return of message"
    (is (= message "Simple Message"))))
