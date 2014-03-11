(ns kilo.web
  (:require
   [kilo.resources.user :as k-user]
   [kilo.resources.group :as k-group]
   [liberator.core :refer [resource defresource]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.format :as ring-format]
   [compojure.core :refer [defroutes PUT GET]]
   [compojure.handler :as handler]
   [clojure.java.jdbc :as sql]))

(defroutes app
  ;;curl -H "Accept:application/json" http://localhost:3000/users/36590
  (GET "/kilo/user/:id" [id] (k-user/get-user id))
  (PUT "/kilo/user/:id" [id] (k-user/set-user id))

  (GET "/kilo/group/:id" [id] (k-group/get-group id))
  (PU
   T "/kilo/group/:id" [id] (k-group/set-group id))
  )

(def handler
  (-> app
      ;;(wrap-trace :header :ui)
      (handler/api)
      (ring-format/wrap-restful-format)))

(defn run!
  [& args]
  (apply jetty/run-jetty #'app args))

#_(def server (run-jetty #'app {:port 3000 :join? false}))



