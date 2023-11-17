package domain;

import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;

import java.util.List;

public interface GroupLoader {
    void saveGroup(Group group, Player owner) throws MyOwnException;

    void deleteGroup(Group group) throws MyOwnException;

    void deleteWaifuFromGroup(Group group, Waifu waifu) throws MyOwnException;

    List<Group> getGroupsFromPlayer(Player player) throws MyOwnException;
}
