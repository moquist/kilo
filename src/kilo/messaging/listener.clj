(ns kilo.messaging.listener
  (:require
    [clojure.data.json :as json]
    [immutant.messaging :as msg]
    [kilo.data.user :as k-user]))

(def sample-request
  "{\"header\":{\"entity-id\":{\"notification-id\":7767},\"operation\":\"put\",\"entity-type\":\"notification\"},\"payload\":{\"user-id\":1234,\"type\":\"user\",\"message\":\"<a href=\\\"http:\\/\\/gamestarmechanic.com\\\">Your task has been evaluated!<\\/a>\"}}")

(def sample-user-message
  "{\"payload\":{\"lastname\":\"Moby\"},\"header\":{\"entity-id\":{\"user-id\":33209},\"operation\":\"put\",\"entity-type\":\"user\"}}")

(def entity-update-functions
  {:user #'k-user/set-user-data}
  {:group #'k-group/set-group-data}
  )

(def entity-ids
  {:user user-id})

(defn process-message
  [message]
  (let [parsed-message (json/read-str message :key-fn keyword)
        header (:header parsed-message)
        payload (:payload parsed-message)
        entity (keyword (:entity-type header))]
    ((entity entity-update-functions) (get-in header [:entity-id :user-id]) payload)))

