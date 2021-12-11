(ns mysql-native-image
  (:require [next.jdbc :as jdbc])
  (:gen-class))

(def db
  {:dbname "native_image"
   :dbtype "mysql"
   :password "local"
   :user "root"})

(defn test-with-transaction!
  "Test for https://github.com/babashka/babashka-sql-pods/issues/47

  No issues here, but it fails in babashka."
  [datasource]
  (let [con (jdbc/get-connection datasource)]
    (prn (jdbc/execute! con ["drop table if exists foo"]))
    (prn (jdbc/execute! con ["create table foo (a int)"]))
    (prn (jdbc/execute! con ["insert into foo values (1), (2), (3)"]))
    (prn (jdbc/execute! con ["select * from foo"]))
    (jdbc/with-transaction [tx datasource]
      (Thread/sleep 200)
      (prn (jdbc/execute! tx ["select * from foo"]))
      (assert (= (jdbc/execute! con ["select * from foo"])
                (jdbc/execute! tx ["select * from foo"]))))))

(defn -main [& _]
  (let [ds (jdbc/get-datasource db)]
    (prn
      (jdbc/execute! ds ["select now()"]))
    (test-with-transaction! ds)))
