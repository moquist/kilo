(ns kilo.api-test
  (:require [kilo.api-test-config :refer :all]
            [kilo.web :as k-web]
            [clojure.test :refer :all]
            [clojure.data.json :as json]
            [ring.mock.request :as rmr]
            ))

;; api-test-config makes the mock ring requests to the resources
;; defined in web.clj. The resulting vars are available for testing here.
(use-fixtures :once api-test-config) 

(deftest get-user-test
  (is (= (:status get-user) 200)))

(deftest verify-user-content-type
  ;; keyed-headers seems like it should be Ring middleware
  (def keyed-headers
    (into {}
      (for [[k v] (:headers get-user)]
        [(keyword k) v])))
  (is (= (:Content-Type keyed-headers) "application/json;charset=UTF-8")))



(deftest get-group-test
  (is (= (:status get-group) 200)))
