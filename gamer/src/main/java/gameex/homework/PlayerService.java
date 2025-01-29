package gameex.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private PlayerValidator playerValidator;


    public void addPlayer(PlayerProfile playerProfile) throws PlayerExistsException {
        playerValidator.isValid(playerProfile);
        if (playerRepo.findByNameIgnoreCaseAndAge(playerProfile.getName(), playerProfile.getAge()).isPresent()) {
            throw new PlayerExistsException();
        }
        playerRepo.save(playerProfile);
    }

    public List<PlayerProfile> findByNameContainsIgnoreCase(String partialName){
        return playerRepo.findByNameContainsIgnoreCase(partialName);
    }
}
