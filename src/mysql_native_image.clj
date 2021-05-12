(ns mysql-native-image
  (:require [next.jdbc :as jdbc])
  (:gen-class))

(def db
  {:dbname "native_image"
   :dbtype "mysql"
   :password "local"
   :user "root"})

(defn -main [& _]
  (let [ds (jdbc/get-datasource db)]
    (prn
      (jdbc/execute! ds ["select now()"]))))
