package gameex.homework;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepo extends JpaRepository<PlayerProfile, Long> {

    Optional<PlayerProfile> findByNameIgnoreCaseAndAge(String name, int age );

    List<PlayerProfile> findByNameContainsIgnoreCase(String partialName);
}
