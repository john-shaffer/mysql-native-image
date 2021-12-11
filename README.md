# mysql-native-image

A minimal repo for getting mysql-connector-java working with Graal Native Image

## Prerequisites

- [Install Nix](https://nixos.org/guides/install-nix.html)
- Run MariaDB: `podman-compose up -d` or `sudo docker-compose up -d`

## Build and test native-image
- Build: `nix-shell --pure --run "clj -X:native-image"`
- Test: `nix-shell --pure --run target/mysql-native-image`

## Build and test uberjar
- Build: `nix-shell --pure --run ./uberjar.sh`
- Test: `nix-shell --pure --run "java -cp target/mysql-native-image.jar clojure.main -m mysql-native-image"`
