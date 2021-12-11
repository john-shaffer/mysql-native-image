# mysql-native-image

A minimal repo for getting mysql-connector-java working with Graal Native Image

## Build and test

- [Install Nix](https://nixos.org/guides/install-nix.html)
- Build: `nix-shell --pure --run ./uberjar.sh`
- Run MariaDB: `podman-compose up -d` or `sudo docker-compose up -d`
- Test: `nix-shell --pure --run "java -cp target/mysql-native-image.jar clojure.main -m mysql-native-image"`
