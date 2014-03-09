(ns kilo.resources.user
  (:require
            [clojure.data.json :as json]
            [liberator.core :refer [resource defresource]]
            [kilo.sqldb :as k-sqldb]
            [kilo.data.user-sql :as k-usersql] 
            [clojure.pprint :refer (pprint)]))

(defresource
  ^{:doc "This resource provides data for a given user. Utilizing Liberator pattern of
          getting data in function that handles the exists? check and assoc'ing that data
          into the Liberator context."
    :example "<code>curl --header 'Accept:application/edn' http://localhost:4001/kilo/user/36590</code>"} 

  get-user [id]
  :allowed-methods [:get] 
  :available-media-types ["application/json" "application/edn"]
  :exists? (fn [ctx]
             (if-let [user (k-usersql/get-user-data id)]
               {::user user}))
  :handle-ok (fn [ctx]
               (::user ctx)))

(defresource
  set-user [id]
  :allowed-methods [:put]
  :available-media-types ["application/json" "application/edn"]
  :put! (fn [ctx]
          (k-usersql/set-user-data id (json/read-str  (slurp (get-in ctx [:request :body])) :key-fn keyword) ) )
  :respond-with-entity? true
  :handle-ok (fn [ctx]
               (prn "in handle-ok"))
  )




