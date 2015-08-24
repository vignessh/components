(ns components.mongo
  (:require [com.stuartsierra.component :as component]
            [monger.core :as mg]
            [schema.core :as s])
  (:import [com.mongodb DB MongoClient MongoOptions ServerAddress]))

(defrecord Mongo [host port database options connection]
  component/Lifecycle

  (start [this]
    (println ";; Starting Mongo")
    (if connection
      this
      (let [^ServerAddress address (mg/server-address host port)
            ^MongoClient client (mg/connect address (mg/mongo-options options))
            ^DB db (mg/get-db client database)]
        (assoc this :connection db)))) 
  
  (stop [this]
    (println ";; Stopping Mongo")
    (if-not connection
      this
      (do (.close connection)
          (dissoc this :connection)))))

(s/defn new-mongo-server :- Mongo 
  [{:keys [host 
           port 
           database 
           options]
    :or {host "localhost"
         mongo-port 27017
         options {:connections-per-host 30}}}]
  (map->MongoServer {:host host 
                     :port port
                     :database database
                     :options options}))
