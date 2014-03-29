(ns kilo.messaging.publisher
  (:require [immutant.messaging :as msg])) 

(defn publish
  [id message]
  (msg/publish "queue.kilo" message))
