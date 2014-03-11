(ns kilo.resources.group
  (:require
            [clojure.data.json :as json]
            [liberator.core :refer [resource defresource]]
            [kilo.data.group :as k-group] 
            ))
 

(defresource
  ^{:doc "This resource provides data for a given user. Utilizing Liberator pattern of
          getting data in function that handles the exists? check and assoc'ing that data
          into the Liberator context."
    :example "<code>curl --header 'Accept:application/edn' http://localhost:4001/kilo/user/36590</code>"} 

  get-group [id]
  :allowed-methods [:get] 
  :available-media-types ["application/json" "application/edn"]
  :exists? (fn [ctx]
             (if-let [group (k-group/get-group-data id)]
               {::group group}))
  :handle-ok (fn [ctx]
               (::group ctx)))

(defresource
  set-group [id]
  :allowed-methods [:put]
  :available-media-types ["application/json" "application/edn"]
  :put! (fn [ctx]
          ;;(k-usersql/set-user-data id (json/read-str  (slurp (get-in
          ;ctx [:request :body])) :key-fn keyword) )
          )
  :respond-with-entity? true
  :handle-ok (fn [ctx]
               (prn "in handle-ok"))
  )
