(ns native
  (:require [cheshire.core :as json]
            [clj.native-image :as ni]))

(def exception-class-names
  ["com.mysql.cj.exceptions.AssertionFailedException"
   "com.mysql.cj.exceptions.CJCommunicationsException"
   "com.mysql.cj.exceptions.CJException"
   "com.mysql.cj.exceptions.CJOperationNotSupportedException"
   "com.mysql.cj.exceptions.CJPacketTooBigException"
   "com.mysql.cj.exceptions.CJTimeoutException"
   "com.mysql.cj.exceptions.ClosedOnExpiredPasswordException"
   "com.mysql.cj.exceptions.ConnectionIsClosedException"
   "com.mysql.cj.exceptions.DataConversionException"
   "com.mysql.cj.exceptions.DataReadException"
   "com.mysql.cj.exceptions.DataTruncationException"
   "com.mysql.cj.exceptions.FeatureNotAvailableException"
   "com.mysql.cj.exceptions.InvalidConnectionAttributeException"
   "com.mysql.cj.exceptions.NumberOutOfRange"
   "com.mysql.cj.exceptions.OperationCancelledException"
   "com.mysql.cj.exceptions.PasswordExpiredException"
   "com.mysql.cj.exceptions.PropertyNotModifiableException"
   "com.mysql.cj.exceptions.RSAException"
   "com.mysql.cj.exceptions.SSLParamsException"
   "com.mysql.cj.exceptions.StatementIsClosedException"
   "com.mysql.cj.exceptions.UnableToConnectException"
   "com.mysql.cj.exceptions.UnsupportedConnectionStringException"
   "com.mysql.cj.exceptions.WrongArgumentException"])

(def reflections
  {"com.mysql.cj.conf.url.SingleConnectionUrl"
   {:methods [{:name "<init>"
               :parameterTypes ["com.mysql.cj.conf.ConnectionUrlParser"
                                "java.util.Properties"]}]}
   "com.mysql.cj.jdbc.AbandonedConnectionCleanupThread" nil
   "com.mysql.cj.jdbc.Driver" nil
   "com.mysql.cj.log.StandardLogger"
   {:methods [{:name "<init>"
               :parameterTypes ["java.lang.String"]}]}
   "com.mysql.cj.protocol.StandardSocketFactory"
   {:methods [{:name "<init>"
               :parameterTypes []}]}
   "java.beans.BeanInfo" {:allPublicMethods true}
   "java.beans.Introspector" {:allPublicMethods true}
   "java.beans.PropertyDescriptor" {:allPublicMethods true} })

(defn reflections->config [reflections]
  (map
    (fn [[name options]]
      (assoc options :name name))
    reflections))

(defn write-reflect-config []
  (let [reflections (->> exception-class-names
                      (map #(-> {:name %
                                 :methods [{:name "<init>"
                                            :parameterTypes ["java.lang.String"]}]}))
                      (concat (reflections->config reflections))
                      (sort-by :name))]
    (spit "reflect-config.json"
      (json/generate-string reflections {:pretty true}))))

(defn build [{:keys [main-ns opts]}]
  (write-reflect-config)
  (apply ni/-main main-ns opts))
