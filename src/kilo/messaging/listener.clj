(ns kilo.messaging.listener
  (:require
    [clojure.data.json :as json]
    [immutant.messaging :as msg]
    [kilo.data.resource-accessor :as k-resource]))

;; {"header":{"operation":"assert","qitem-id":1234},
;; "payload":[{"entity-id":{"user-id":1234},
;;             "entity":{"lastname":"Williams"},"entity-type":"user"}]}

(def entity-functions
  "Maps incoming 'operation' value with a function that implements
   analagous operation via Korma db functions."
  {
   :assert #'k-resource/set-data
   :get #'k-resource/get-data})

(defn process-payload
  [operation payload]
  (let [entity-function (operation entity-functions)]
    (map (fn [payload-entry]
           (let [entity (:entity-type payload-entry)
                 id-map (:entity-id payload-entry)
                 data (:entity payload-entry)]
             (entity-function entity id-map data)))
         payload)))

(defn process-message
  "Receives message from queue and assembles call to the function that will
   either retrieve data from the db or update data in the db (depending on
   'operation' value in message."
  ;;Q: Can payloads contain data for multiple entity types?
  ;;Q: Move entity-type to header?
  [message]
  (let [parsed-message (json/read-str message :key-fn keyword)
        header (:header parsed-message)
        payload (:payload parsed-message)
        entity (keyword (:entity-type (first payload)))
        operation (keyword (:operation header))]
    (process-payload operation payload)))

