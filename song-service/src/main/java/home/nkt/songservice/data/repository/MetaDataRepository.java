package home.nkt.songservice.data.repository;

import home.nkt.songservice.data.MetaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaDataRepository extends JpaRepository<MetaData, Long> {

    Optional<MetaData> findByResourceId(Long resourceId);


}
