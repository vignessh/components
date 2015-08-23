(ns components.http-kit
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as http-kit]))

(defrecord HttpServer [port handler http-kit]
  component/Lifecycle

  (start [this]
    (println ";; Starting Httpkit server")
    (if http-kit
      this
      (assoc this :http-kit (http-kit/run-server (handler this) {:port port}))))

  (stop [this]
    (println ";; Stopping Httpkit server")
    (if (not http-kit)
      this
      (do (http-kit :timeout 1000)
          (dissoc this :http-kit)))))

(defn new-http-server [{:keys [port handler]}]
  (map->HttpServer {:port port :handler handler}))
