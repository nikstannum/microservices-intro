package nkt.home.songservice.data.repository;

import java.util.Optional;
import nkt.home.songservice.data.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaDataRepository extends JpaRepository<MetaData, Long> {

    Optional<MetaData> findByResourceId(Long resourceId);


}
