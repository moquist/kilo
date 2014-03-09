(ns kilo.data.user-sql
  (:use [korma.core]
        [kilo.sqldb])
  )

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
  (let [id (Long/parseLong id)]
    (update user
            (set-fields fields)
            (where {:id id})))
  )
 

