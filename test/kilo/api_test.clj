(ns kilo.api-test
  (:require [kilo.web :as k-web]
            [clojure.test :refer :all]
            [ring.mock.request :as rmr]
            [kilo.api-test-config :refer :all]))

;; api-test-config makes the mock ring requests to the resources
;; defined in web.clj. The resulting vars are available for testing here.
(use-fixtures :once api-test-config) 

#_(deftest resource-api-test
  (def keyed-headers
    (into {}
      (for [[k v] (:headers api-response)]
        [(keyword k) v])))
  (is (= (:status api-response) 200))
  (is (= (:Content-Type keyed-headers) "application/edn;charset=UTF-8" )))

;; disabling get-user-test for now. Need to allow for db connection in
;; context of test.
(deftest get-user-test
  (is (= (:status get-user) 200)))

