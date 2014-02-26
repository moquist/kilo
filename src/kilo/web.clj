(ns kilo.web
  (:require
   [kilo.resources.user :as k-user]
   [liberator.core :refer [resource defresource]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.format :as ring-format]
   [compojure.core :refer [defroutes PUT GET]]
   [clojure.java.jdbc :as sql]))

(defroutes app
  ;;curl -H "Accept:application/json" http://localhost:3000/users

  (GET "/kilo/user/:id" [id] (k-user/get-user id)))

(def handler
  (-> app
      ;;(wrap-trace :header :ui)
      (wrap-params)
      (ring-format/wrap-restful-format)))

(defn run!
  [& args]
  (apply jetty/run-jetty #'app args))

#_(def server (run-jetty #'app {:port 3000 :join? false}))



