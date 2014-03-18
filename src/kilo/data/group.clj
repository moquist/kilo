(ns kilo.data.group
  (:require [korma.core :refer [defentity table where select update set-fields]]))

(defentity group
  (table :mdl_groups))

(defn get-group-data
  [id]
  ;; need to convert string id to long
  (let [id (Long/parseLong id)]
    (select group
          (where {:id id})))
   )

(defn set-group-data
  [id fields]
  (let [id (Long/parseLong id)
        fields (assoc fields :timemodified (quot (System/currentTimeMillis) 1000))]
    (update group
            (set-fields fields)
            (where {:id id})))
  )

