(ns kilo.api-test-config
  (:require [kilo.web :as k-web]
            [clojure.test :refer :all]
            [ring.mock.request :as rmr]
            [kilo.db-config :as k-testdb]))

(defn set-up-test []
  ;; this is where we would also initialize and populate test db

  ;; the following vars containing the various responses are available to the
  ;; tests defined in web_test.clj

  (k-testdb/setup-test-db!)
  (k-testdb/set-korma-connection!)
  ;;(def onboarding-response (a-web/onboarding (rmr/request :get "/")))
  (def get-user (k-web/app (rmr/request :get "/kilo/user/99999")))
  (def get-group (k-web/app (rmr/request :get "/kilo/group/33333"))))
(defn teardown-test []
  (k-testdb/teardown-test-db!)
  )

(defn api-test-config
  [api-tests]
  (set-up-test)
  (api-tests)
  
  (try
    (teardown-test)
    (finally (println "Kilo API test complete"))))


