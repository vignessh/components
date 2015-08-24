(defproject components "0.1.0-SNAPSHOT"
  :description "Components for popular libraries"
  :url "https://github.com/vignessh/components"
  :scm {:name "git"
        :url "https://github.com/vignessh/components"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :signing {:gpg-key "190CBD05"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :pom-addition [:developers [:developer
                              [:name "Vignessh Vaidyanathan"]
                              [:url "https://vignessh.github.io"]]]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.2.3"]
                 [prismatic/schema "0.4.3"]
                 [http-kit "2.1.18"]
                 [aleph "0.4.0"]
                 [com.novemberain/monger "3.0.0-rc1"]])
