package gg.pixelgruene.oergpbackend.minecraft;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RankService {

    private final LuckPerms luckPerms;

    public RankService() {
        this.luckPerms = LuckPermsProvider.get();
    }

    /**
     * Holt die Primärgruppe eines Spielers anhand seiner UUID.
     *
     * @param uuid Die UUID des Spielers.
     * @return Die Primärgruppe des Spielers oder null, wenn der Spieler nicht gefunden wird.
     */
    public String getPrimaryGroup(UUID uuid) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user != null) {
            return user.getPrimaryGroup();
        }
        return null; // Oder eine Standardgruppe zurückgeben, wenn der Benutzer nicht gefunden wird
    }
}
