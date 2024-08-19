package gg.pixelgruene.oergpbackend.restapi.minecraft;

import gg.pixelgruene.oergpbackend.minecraft.RankService;
import gg.pixelgruene.oergpbackend.minecraft.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/minecraft")
public class UserMCController {

    @Autowired
    private User userService;
    private final RankService luckPermsService;

    @Autowired
    public UserMCController(RankService luckPermsService) {
        this.luckPermsService = luckPermsService;
    }

    // GET-Endpoint zum Abrufen des Guthabens eines Spielers anhand des Benutzernamens
    @GetMapping("/money/username/{username}")
    public int getPlayerMoneyByUsername(@PathVariable String username) {
        return userService.getPlayerMoneyByUsername(username);
    }

    // GET-Endpoint zum Abrufen des Guthabens eines Spielers anhand der UUID
    @GetMapping("/money/uuid/{uuid}")
    public int getPlayerMoneyByUUID(@PathVariable String uuid) {
        return userService.getPlayerMoneyByUUID(uuid);
    }

    @GetMapping("/others/{uuid}/first-join")
    public ResponseEntity<String> getFirstJoin(@PathVariable String uuid) {
        String firstJoin = userService.getFirstJoin(uuid);
        if (firstJoin != null) {
            return ResponseEntity.ok(firstJoin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uuid}/primarygroup")
    public ResponseEntity<String> getPrimaryGroup(@PathVariable String uuid) {
        try {
            UUID playerUUID = UUID.fromString(uuid);
            String primaryGroup = luckPermsService.getPrimaryGroup(playerUUID);

            if (primaryGroup != null) {
                return ResponseEntity.ok(primaryGroup);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format");
        }
    }
}