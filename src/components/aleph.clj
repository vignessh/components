(ns components.aleph
  (:require [aleph.http :as http]
            [com.stuartsierra.component :as component]
            [schema.core :as s]))

(defrecord AlephWebServer [port handler aleph]
  component/Lifecycle
  
  (start [this]
    (println ";; Starting Aleph HTTP server")
    (if aleph
      this
      (assoc this :aleph (http/start-server (handler this) {:port port}))))

  (stop [this]
    (println ";; Stopping Aleph HTTP server")
    (if-not aleph
      this
      (do (.close aleph) 
          (dissoc this :aleph)))))

; handler is a function that can take in the component map and initialize itself.
; i.e a Compojure route that can be initialized with components.
(s/defn new-aleph-server :- AlephWebServer [{:keys [port handler]}]
  (map->AlephWebServer {:port port :handler handler}))
