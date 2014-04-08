(ns kilo.messaging.listener
  (:require [clojure.data.json :as json]
            [immutant.messaging :as msg]
            [kilo.data.resource-accessor :as k-resource]))

(def entity-functions
  "Maps incoming 'operation' value with a function that implements
   analagous operation via Korma db functions."
  {:put k-resource/set-data
   :get k-resource/get-data})

(defn process-message
  "Receives message from queue and assembles call to the function that will
   either retrieve data from the db or update data in the db (depending on
   'operation' value in message.
"
  [message]
  (let [parsed-message (json/read-str message :key-fn keyword)
        header (:header parsed-message)
        payload (:payload parsed-message)
        entity (keyword (:entity-type header))
        operation (keyword (:operation header))]
    ((operation entity-functions) entity (get-in header [:entity-id :user-id]) payload)))

