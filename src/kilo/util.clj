(ns kilo.util
  (:require [clojure.string :as str]
            ;;[kilo.sqldb :as k-sql]
            ))

(defn output!
  "Call output! instead of using prn. Respects --verbose. Returns nil."
  [verbose? & msgs] (when verbose? (apply prn msgs)))

(defn keyword-in-ns
  [target-ns keyword-in]
  (keyword (str target-ns "/" (name keyword-in))))

(defn keywords->ns
  [target-ns & keywords]
  (let [transform-fn (partial keyword-in-ns target-ns)]
    (map transform-fn (flatten keywords))))

#_(defn get-config!
  "Get the specified config key from the DB."
  [key]
  (:value
   (first
    (k-sql/select! {:select [:value]
                    :from [:config]
                    :where [:= :key key]}))))

#_(defn set-config!
  "Set the value of the specified config.key in the DB, inserting a new row or updating as appropriate."
  [key value]
  (if-let [_ (get-config! key)]
    (k-sql/update! "UPDATE config SET value = ? WHERE key = ?" [value key])
    (k-sql/update! "INSERT INTO config (value, key) VALUES (?, ?)" [value key])))

(defn split-paragraphs
  "Split the given text into a vector of paragraphs."
  [text]
  (filter #(not (str/blank? %))
          (str/split text #"[\n\r]")))

