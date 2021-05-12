/usr/lib/jvm/graalvm/bin/java -agentlib:native-image-agent=config-output-dir=agent -cp target/mysql-native-image.jar clojure.main -m mysql-native-image
