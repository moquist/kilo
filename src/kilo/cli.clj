(ns kilo.cli
  (:require [clojure.tools.cli :as cli]
            [kilo.util :as k-util]))

(def cli-options
  [["-p" "--config-path" "Specify a non-default config directory"]
   ["-h" "--help" "Print this help message" :flag true]
   ["-v" "--verbose" "Print info to stdout while running" :flag true]])

(defn get-opts
  "Get and process all the CLI arguments, handle --help and --verbose. Return ONLY the options."
  [args]
  (let [[opts args usage] (apply cli/cli args cli-options)]
    (when (:help opts)
      (println usage)
      (System/exit 0)) 
    opts))
