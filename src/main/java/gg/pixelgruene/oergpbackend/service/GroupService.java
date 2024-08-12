package gg.pixelgruene.oergpbackend.service;

import gg.pixelgruene.oergpbackend.user.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    /**
     * Ruft alle Gruppen aus der Datenbank ab.
     *
     * @return Eine Liste aller Gruppen.
     */
    public List<Group> getAllGroups() {
        return Group.getAllGroups();
    }

    /**
     * Überprüft, ob ein Gruppenname verfügbar ist.
     *
     * @param groupName Der zu überprüfende Gruppenname.
     * @return true, wenn der Gruppenname verfügbar ist, ansonsten false.
     */
    public boolean isGroupNameAvailable(String groupName) {
        Group group = new Group();
        return group.isGroupNameAvailable(groupName);
    }

    /**
     * Gibt den Gruppennamen anhand der Gruppen-ID zurück.
     *
     * @param groupId Die ID der Gruppe.
     * @return Der Name der Gruppe.
     */
    public String getGroupNameById(Long groupId) {
        return Group.getGroupNameById(groupId);
    }

    /**
     * Gibt die Gruppen-ID anhand des Gruppennamens zurück.
     *
     * @param groupName Der Name der Gruppe.
     * @return Die ID der Gruppe.
     */
    public long getGroupID(String groupName) {
        Group group = new Group();
        return group.getGroupID(groupName);
    }

    /**
     * Aktualisiert den Gruppennamen einer bestehenden Gruppe.
     *
     * @param groupId Die ID der zu aktualisierenden Gruppe.
     * @param newName Der neue Name der Gruppe.
     */
    public void updateGroupName(int groupId, String newName) {
        GroupController group = new GroupController();
        group.updateGroupName(groupId, newName);
    }

}
