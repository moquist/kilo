(ns kilo.data.resource-accessor
  
  (:require
   [kilo.data.entities :as entities]
   [korma.core :refer [where select update set-fields]]))

(def resource-entities
  {
   :user         entities/user
   :group        entities/group
   :group-member entities/group_member
   ;;:notification entities/notification
   })

(defn get-data
  [entity id]
  (select (entity resource-entities)
          (where {:id id})))

(defn set-data
  [entity entity-id data]
  (let [fields (assoc data :timemodified (quot (System/currentTimeMillis) 1000))]
    (update (entity resource-entities)
              (set-fields data)
              (where (entities/get-where-fields entity-id)))))
