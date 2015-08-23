(ns components.aleph
  (:require [aleph.http :as http]
            [com.stuartsierra.component :as component]))

(defrecord AlephWebServer [port handler aleph]
  component/Lifecycle
  
  (start [this]
    (println ";; Starting Aleph HTTP server")
    (if aleph
      this
      (assoc this :aleph (http/start-server (handler this) {:port port}))))

  (stop [this]
    (println ";; Stopping Aleph HTTP server")
    (if (not aleph)
      this
      (do (.close aleph) 
          (dissoc this :aleph)))))

(defn new-aleph-server [{:keys [port handler]}]
  (map->AlephWebServer {:port port :handler handler}))
