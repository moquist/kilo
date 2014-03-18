(ns kilo.db-config
  (:require [kilo.conf :as k-conf]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]
            [kilo.sqldb-ddl :as k-sqldb-ddl]
            [kilo.sqldb :as k-sqldb]
            [clojure.edn :as edn])
  (:use clojure.java.io ))

(def home-dir (System/getProperty "user.home") )

(def test-schema (edn/read-string (slurp "schema.edn")))

(def test-db
  (let [conf (k-conf/load-configs (apply str home-dir
                                         "/" ".kilo-test" ))
        db (:conf-sql-db conf)]
    ;; TODO: really need to substring (?) and supply 'kilo-test' as db-name
    (assoc db :subname "//localhost:5432/kilo_test")
    ))

(defn set-korma-connection!
  []
  (k-sqldb/default-connection! test-db))

(defn get-lines
  "Returns a seq referencing the lines read from the file name passed."
  [file-name]
  (with-open [r (reader file-name)]
    (doall (line-seq r))))

(defn populate-test-db!
  "Runs the db script against an existing database to populate the tables, create indexes, etc."
  [db db-setup-script]
  (apply sql/db-do-commands db (get-lines db-setup-script) ))

(defn setup-test-db!
  "Utility method to be called from tests to create tables in a test database and populate them with test data.
   If no args specified, the default db config and test data file defined here will be used."
  [& {:keys [db db-setup-file]
      :or {db test-db db-setup-file "test.sql"}}]
  (k-sqldb-ddl/init! db)
  (populate-test-db! db db-setup-file))

(defn teardown-test-db!
  "Utility method to be called from tests to drop all test tables based on either the default schema or the one passed
   as an arg to this function."
  [& {:keys [db schema]
      :or {db test-db schema test-schema}}]
  (doall (for [table (keys (schema :tables))
                  :let [drop-statement (format "DROP TABLE %s CASCADE;"
                                        (name table))]]
                  (apply sql/db-do-commands db [drop-statement] ) )))
