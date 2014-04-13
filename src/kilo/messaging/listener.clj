(ns kilo.messaging.listener
  (:require [clojure.data.json :as json]
            [immutant.messaging :as msg]
            [kilo.data.resource-accessor :as k-resource]))

(def entity-functions
  "Maps incoming 'operation' value with a function that implements
   analagous operation via Korma db functions."
  {:assert k-resource/set-data
   :get k-resource/get-data})

(def sample-message
  "{\"header\":{\"operation\":\"assert\",\"qitem-id\":1234},
\"payload\":[{\"entity-id\":{\"user-id\":33209},
\"entity\":{\"lastname\":\"Mobydoby\"},\"entity-type\":\"user\"}]}")

(defn process-payload
 "Maps a call to the function defined by the 'operation' over the collection of entries in the given payload."
  [operation payload]
  (prn "in process-payload ...")
  (let [entity-function (operation entity-functions)]
    
    (map (fn [payload-entry]
           (let [entity (keyword (:entity-type payload-entry)) 
                 id-map (:entity-id payload-entry)
                 data (:entity payload-entry)]
             (prn "entity is: " entity) 
             (entity-function entity id-map data))) payload)))

(defn process-message
  "Receives message from queue and assembles call to the function that will
   either retrieve data from the db or update data in the db (depending on
   'operation' value in message."
  [message]
  (prn "In process-message ...")
  (let [parsed-message (json/read-str message :key-fn keyword)
        header (:header parsed-message)
        payload (:payload parsed-message)
        operation (keyword (:operation header))]
    (process-payload operation payload)))

