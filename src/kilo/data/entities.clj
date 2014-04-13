(ns kilo.data.entities
  (:require [korma.core :refer [defentity table]]))

(defentity group
  (table :mdl_groups))

(defentity group_member
  (table :mdl_groups_members))

(defentity notification
  (table :unknown))

(defentity user
  (table :mdl_sis_user))

(defentity enrollment)

(defentity section)

(defentity competency)

(defentity task)

(defentity task-progress)

(defentity notification)

(def entity-keys
  ;; Mapping of incoming entity ids with db table column names
  {:user-id (keyword "id")
   :id (keyword "id")})

(def resources [user])

(defn get-where-fields
  [where-data]
  ;;create new map
  ;;for each k,v in where-data
  ;;lookup db key from entity-keys based on k
  ;;create new map entry with db key and v
  (into {}
        (for [[k v] where-data] [(k entity-keys) v])))

  
