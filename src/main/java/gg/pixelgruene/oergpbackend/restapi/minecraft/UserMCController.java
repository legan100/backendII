package gg.pixelgruene.oergpbackend.restapi.minecraft;

import gg.pixelgruene.oergpbackend.minecraft.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/minecraft")
public class UserMCController {

    @Autowired
    private User userService;

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
}