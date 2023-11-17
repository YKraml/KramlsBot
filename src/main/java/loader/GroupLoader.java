package loader;

import com.google.inject.Inject;
import database.sql.SQLCommandExecutor;
import database.sql.commands.group.DeleteGroup;
import database.sql.commands.group.GroupExistst;
import database.sql.commands.group.InsertGroup;
import database.sql.commands.group.SelectGroupsByOwnerId;
import database.sql.commands.group_waifu.*;
import database.sql.entry.GroupEntrySet;
import database.sql.entry.GroupWaifuEntrySet;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.WaifuLoader;

import javax.inject.Singleton;
import java.util.*;


@Singleton
public final class GroupLoader {

    private final Collection<Group> groupCache;
    private final WaifuLoader waifuLoader;
    private final SQLCommandExecutor sqlCommandExecutor;


    @Inject
    public GroupLoader(WaifuLoader waifuLoader, SQLCommandExecutor sqlCommandExecutor) {
        this.waifuLoader = waifuLoader;
        this.sqlCommandExecutor = sqlCommandExecutor;
        groupCache = Collections.synchronizedSet(new HashSet<>());
    }

    public void saveGroup(Group group, Player owner) throws MyOwnException {

        if (!sqlCommandExecutor.execute(new GroupExistst(group))) {
            sqlCommandExecutor.execute(new InsertGroup(group, owner));
        }

        Set<Waifu> waifuSetCopy = new LinkedHashSet<>(group.getWaifuList());
        for (Waifu waifu : waifuSetCopy) {
            if (!sqlCommandExecutor.execute(new GroupWaifuExists(group, waifu))) {
                sqlCommandExecutor.execute(new InsertGroupWaifu(group, waifu));
            }
        }

    }

    public void deleteGroup(Group group) throws MyOwnException {
        this.groupCache.remove(group);
        sqlCommandExecutor.execute(new DeleteAllWaifusFromGroup(group));
        sqlCommandExecutor.execute(new DeleteGroup(group));
    }

    public void deleteWaifuFromGroup(Group group, Waifu waifu) throws MyOwnException {
        sqlCommandExecutor.execute(new DeleteWaifuFromGroup(group, waifu));
    }

    public List<Group> getGroupsFromPlayer(Player player) throws MyOwnException {
        List<Group> groupList = new ArrayList<>();

        GroupEntrySet groupEntrySet = sqlCommandExecutor.execute(new SelectGroupsByOwnerId(player));
        for (GroupEntrySet.GroupEntry groupEntry : groupEntrySet) {

            Group group = new Group(groupEntry.getId(), groupEntry.getName());
            groupList.add(group);

            GroupWaifuEntrySet groupWaifuEntrySet = sqlCommandExecutor.execute(
                    new SelectWaifusFromGroup(group));
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