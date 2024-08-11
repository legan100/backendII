package gg.pixelgruene.oergpbackend.restapi;

import gg.pixelgruene.oergpbackend.service.GroupService;
import gg.pixelgruene.oergpbackend.user.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{groupname}")
    public Long getGroupID(@PathVariable String groupname) throws SQLException {
        return groupService.getGroupID(groupname);
    }

    @GetMapping("/id/{groupid}")
    public String getGroupNameById(@PathVariable Long groupid) throws SQLException {
        return groupService.getGroupNameById(groupid);
    }

    @GetMapping("/all")
    public List<Group> getAllGroups() throws SQLException {
        return groupService.getAllGroups();
    }

    // Weitere Endpunkte, falls erforderlich (z.B. Create, Update, Delete).
}
