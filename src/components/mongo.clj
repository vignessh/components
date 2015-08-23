(ns components.mongo
  (:require [com.stuartsierra.component :as component]
            [monger.core :as mg])
  (:import [com.mongodb DB MongoClient MongoOptions ServerAddress]))

(defrecord MongoServer [host port database options connection]
  component/Lifecycle

  (start [this]
    (println ";; Starting MongoServer")
    (if connection
      this
      (let [^ServerAddress address (mg/server-address host port)
            ^MongoClient client (mg/connect address (mg/mongo-options options))
            ^DB db (mg/get-db client database)]
        (assoc this :connection db)))) 
  
  (stop [this]
    (println ";; Stopping MongoServer")
    (if-not connection
      this
      (do (.close connection)
          (dissoc this :connection)))))

(defn new-mongo-server 
  [{:keys [host 
           mongo-port 
           database 
           options]
    :or {host "localhost"
         mongo-port 27017
         options {:connections-per-host 30}}}]
  (map->MongoServer {:host host 
                     :port mongo-port
                     :database database
                     :options options}))
