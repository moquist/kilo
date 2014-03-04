(ns kilo.resources.user
  (:require [liberator.core :refer [resource defresource]]
            [kilo.sqldb :as k-sqldb]
            [clojure.pprint :refer (pprint)]))

(defn get-user-data
  [id]
  (k-sqldb/select-one!
   {:select [:*]
        :from [:mdl_sis_user]
        :where [:= :id (bigint id)]}  ))

(defresource
  ^{:doc "This resource provides data for a given user. Utilizing Liberator pattern of
          getting data in function that handles the exists? check and assoc'ing that data
          into the Liberator context."
    :example "<code>curl --header 'Accept:application/edn' http://localhost:4001/kilo/user/36590</code>"} 

  get-user [id]
   
  :available-media-types ["application/json" "application/edn"]
  :exists? (fn [ctx]
             (if-let [user (get-user-data id)]
               {::user user}))
  :handle-ok (fn [ctx]
                 (::user ctx)))




