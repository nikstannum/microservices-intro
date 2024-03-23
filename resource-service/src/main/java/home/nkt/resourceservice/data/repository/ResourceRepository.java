package home.nkt.resourceservice.data.repository;

import home.nkt.resourceservice.data.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Mp3File, Long> {
}
