(ns kilo.resources.user
  (:require [liberator.core :refer [resource defresource]]
            [kilo.sqldb :as k-sqldb]))

(defn get-user-data
  [id]
  (k-sqldb/select-one!
   {:select [:*]
        :from [:mdl_sis_user]
        :where [:= :id (bigint id)]}  ))

(defresource
  ^{:doc "This resource provides data for a given user."
    :example "<code>curl --header 'Accept:application/edn' http://localhost:4001/kilo/user/36590</code>"} 

  get-user [id]
    :available-media-types [ "application/json" "application/edn"]
    :handle-ok (get-user-data id) )

