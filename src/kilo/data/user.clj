(ns kilo.data.user
  
  (:require [korma.core :refer [defentity table where select update set-fields]]))

(defentity user
  (table :mdl_sis_user)
  )

(defn get-user-data
  [id]
  ;; need to convert string id to long
  (let [id (Long/parseLong id)]
    (select user
          (where {:id id})))
   )

(defn set-user-data
  [id fields]
  (let [
        fields (assoc fields :timemodified (quot (System/currentTimeMillis) 1000))]
    (update user
            (set-fields fields)
            (where {:id id}))
    (prn fields))
  )
 

