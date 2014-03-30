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
