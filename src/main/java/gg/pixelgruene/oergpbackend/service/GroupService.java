package gg.pixelgruene.oergpbackend.service;

import gg.pixelgruene.oergpbackend.user.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class GroupService {

    private GroupController groupRepository;

    public Long getGroupID(String groupname) throws SQLException {
        return groupRepository.getGroupID(groupname);
    }

    public String getGroupNameById(Long groupid) throws SQLException {
        return groupRepository.getGroupNameById(groupid);
    }

    public List<Group> getAllGroups() throws SQLException {
        return groupRepository.getAllGroups();
    }

}
