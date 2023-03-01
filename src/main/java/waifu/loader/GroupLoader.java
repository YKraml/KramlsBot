package waifu.loader;

import exceptions.MyOwnException;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;
import waifu.sql.commands.group.DeleteGroup;
import waifu.sql.commands.group.GroupExistst;
import waifu.sql.commands.group.InsertGroup;
import waifu.sql.commands.group.SelectGroupsByOwnerId;
import waifu.sql.commands.group_waifu.*;
import waifu.sql.entry.GroupEntrySet;
import waifu.sql.entry.GroupWaifuEntrySet;

import java.util.*;

public final class GroupLoader {

  private final Collection<Group> groupCache;
  private final WaifuLoader waifuLoader;


  public GroupLoader(WaifuLoader waifuLoader) {
    this.waifuLoader = waifuLoader;
    groupCache = Collections.synchronizedSet(new HashSet<>());
  }

  public void saveGroup(Group group, Player owner) throws MyOwnException {

    if (!new GroupExistst(group).executeCommand()) {
      new InsertGroup(group, owner).executeCommand();
    }

    Set<Waifu> waifuSetCopy = new LinkedHashSet<>(group.getWaifuSet());
    for (Waifu waifu : waifuSetCopy) {
      if (!new GroupWaifuExists(group, waifu).executeCommand()) {
        new InsertGroupWaifu(group, waifu).executeCommand();
      }
    }

  }

  public void deleteGroup(Group group) throws MyOwnException {
    this.groupCache.remove(group);
    new DeleteAllWaifusFromGroup(group).executeCommand();
    new DeleteGroup(group).executeCommand();
  }

  public void deleteWaifuFromGroup(Group group, Waifu waifu) throws MyOwnException {
    new DeleteWaifuFromGroup(group, waifu).executeCommand();
  }

  public List<Group> getGroupsFromPlayer(Player player) throws MyOwnException {
    List<Group> groupList = new ArrayList<>();

    GroupEntrySet groupEntrySet = new SelectGroupsByOwnerId(player).executeCommand();
    for (GroupEntrySet.GroupEntry groupEntry : groupEntrySet) {

      Group group = new Group(groupEntry.getId(), groupEntry.getName());
      groupList.add(group);

      GroupWaifuEntrySet groupWaifuEntrySet = new SelectWaifusFromGroup(group).executeCommand();
      for (GroupWaifuEntrySet.GroupWaifuEntry entry : groupWaifuEntrySet) {
        Optional<Waifu> waifu = waifuLoader.getWaifuById(entry.getIdWaifu());
        if (waifu.isPresent()) {
          group.addWaifu(waifu.get());
        }
      }

    }

    groupCache.addAll(groupList);
    return groupList;
  }
}