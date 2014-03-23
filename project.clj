(defproject kilo "0.1.0-SNAPSHOT"
  :description "RESTful interface to Postgres Data"
  :url "http://"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.2.4"]
                 [liberator "0.10.0"]
                 [compojure "1.1.3"]
		 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring-middleware-format "0.3.2"]
                 
                 ;;[org.clojure/java.jdbc "0.3.3"]
                 ;; using org.clojure/java.jdbc 0.3.3 results in this
                 ;; error when executing lein:
                 ;; No such var: jdbc/set-rollback-only,
                 ;; compiling:(korma/db.clj:195:3)
                 
                 ;; korma depends on java.jdbc 0.2.3, but java.jdbc
                 ;; 0.3.0-beta1 does work
                 [org.clojure/java.jdbc "0.3.0-beta1"]

                 ;; jdbc-pg-init "here for now" as we may need it for
                 ;; test db creation / population.
                 [jdbc-pg-init "0.1.2"]
                 [honeysql "0.4.2"]
                 [korma "0.3.0-RC6" :exclusions [org.clojure/java.jdbc]]
                 [org.clojure/data.json "0.2.4"]]
  
  :plugins [[lein-ring "0.8.8" :exclusions [org.clojure/clojure]]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/tools.nrepl "0.2.3"]
                                  [org.clojure/java.classpath "0.2.0"]
                                  [ring-mock "0.1.5"]
                                  [org.immutant/immutant-messaging "1.1.0"]]
                                  }}

  :immutant {:context-path "/"}
  
  :ring {:handler kilo.web/app}

  :main ^{:skip-aot true} kilo.core

  :repl-options {:init-ns user 
                 :welcome (println "I am kilo.")}) 

 
  




