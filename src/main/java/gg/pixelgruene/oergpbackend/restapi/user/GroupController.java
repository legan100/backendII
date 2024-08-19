package gg.pixelgruene.oergpbackend.restapi.user;

import gg.pixelgruene.oergpbackend.service.GroupService;
import gg.pixelgruene.oergpbackend.user.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getGroupNameById(@PathVariable Long id) {
        String groupName = groupService.getGroupNameById(id);
        return ResponseEntity.ok(groupName);
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> isGroupNameAvailable(@RequestParam String groupName) {
        boolean available = groupService.isGroupNameAvailable(groupName);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/id")
    public ResponseEntity<Long> getGroupID(@RequestParam String groupName) {
        long groupId = groupService.getGroupID(groupName);
        return ResponseEntity.ok(groupId);
    }

}