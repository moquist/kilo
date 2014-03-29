(ns immutant.init
  (:require
   [clojure.edn :as edn]
   [kilo.web           :as k-web]
   [kilo.sqldb         :as k-sqldb]
   [kilo.data.user     :as k-user]
   [kilo.messaging.listener :as k-listener]
   [immutant.web       :as web]
   [immutant.messaging :as msg]
   ))

(defn initialize!
  []
  ;; start the kilo api  endpoint
  (web/start "/" k-web/app)
  
  ;; start the message queue
  (msg/start "queue.kilo")
  (msg/listen "queue.kilo" k-listener/process-message)
  
  ;; initialize db connection
  (def db (edn/read-string (slurp (format "%s/.kilo/kilo-conf-sql-db.edn" (System/getProperty "user.home")))))
  (k-sqldb/default-connection! db))

  ;; in the context of immutant starting up, this function gets called
  (initialize!)










