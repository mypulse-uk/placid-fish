(ns placid-fish.core-test
  (:require
    [clojure.test :refer [deftest is testing]]
    [placid-fish.core :refer [absolute? path ends-with? query-map]]))

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

(deftest path-should-not-throw
  (testing "path should not throw"
    (testing "with valid data"
      (is (path "https://example.com/foo")))
    (testing "with nil"
      (is (nil? (path nil))))))

(deftest query-map-should-not-throw
  (testing "query-map should not throw"
    (testing "with valid data"
      (is (query-map "https://example.com"))
      (is (query-map "https://example.com?foo=bar")))
    (testing "with nil"
      (is (nil? (query-map nil))))))
