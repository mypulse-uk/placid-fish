(ns placid-fish.core-test
  (:require [clojure.test :refer :all]
            [placid-fish.core :refer :all]))

(deftest absolute-should-not-throw
  (testing "absolute? should not throw"
    (testing "with valid data"
      (is (absolute? "https://example.com")))
    (testing "with nil"
      (is (nil? (absolute? nil))))
    (testing "with other data types"
      (is (nil? (absolute? 123)))
      (is (nil? (absolute? [])))
      (is (nil? (absolute? {}))))))

(deftest ends-with-should-not-throw
  (testing "ends-with? should not throw"
    (testing "with valid data"
      (is (ends-with? "https://example.com" "com")))
    (testing "with nil"
      (is (nil? (absolute? nil))))))
