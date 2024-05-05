build_resource_service() {
  ./gradlew resource-service:build
}

build_song_service() {
  ./gradlew song-service:build
}

run_docker_compose() {
  docker-compose up
}

build_resource_service
build_song_service
run_docker_compose