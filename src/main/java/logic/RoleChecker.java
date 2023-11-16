package logic;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotUpdateRoles;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoleChecker {

    private final Map<Integer, String> moneyThresholds;
    private final Map<Integer, String> timeThresholds;
    private final PlayerLoader playerLoader;
    private final RoleFinder roleFinder;


    @Inject
    public RoleChecker(PlayerLoader playerLoader, RoleFinder roleFinder) {
        this.playerLoader = playerLoader;
        this.roleFinder = roleFinder;
        moneyThresholds = Collections.synchronizedMap(new LinkedHashMap<>());
        timeThresholds = Collections.synchronizedMap(new LinkedHashMap<>());

        moneyThresholds.put((int) Math.pow(10, 0), "Slumdog");
        moneyThresholds.put((int) Math.pow(10, 3), "Normie");
        moneyThresholds.put((int) Math.pow(10, 4.5), "RichBoy");
        moneyThresholds.put((int) Math.pow(10, 6), "Millionär");
        moneyThresholds.put((int) Math.pow(10, 9), "Milliardär");

        timeThresholds.put(0, "Bauer");
        timeThresholds.put(15, "Ritter");
        timeThresholds.put(50, "Baron");
        timeThresholds.put(100, "Graf");
        timeThresholds.put(200, "Fürst");
        timeThresholds.put(350, "Herzog");
        timeThresholds.put(500, "König");
        timeThresholds.put(1000, "Kaiser");
    }

    public void updateRoles(Server server, User user) throws MyOwnException {

        try {
            Player player = playerLoader.getPlayerById(user.getIdAsString());
            deleteRoles(server, user);

            long money = player.getInventory().getMoney();
            long timeInHours = player.getTimeOnServer(server.getIdAsString()) / 60;

            String moneyRoleName = findRole(money, moneyThresholds);
            String timeRoleName = findRole(timeInHours, timeThresholds);

            roleFinder.getRoleByName(server, moneyRoleName).ifPresent(user::addRole);
            roleFinder.getRoleByName(server, timeRoleName).ifPresent(user::addRole);

        } catch (MyOwnException e) {
            throw new MyOwnException(new CouldNotUpdateRoles(user.getIdAsString()), e);
        }
    }

    private String findRole(long value, Map<Integer, String> thresholdMap) {
        String roleName = "";
        for (Map.Entry<Integer, String> e : thresholdMap.entrySet()) {
            if (value > e.getKey()) {
                roleName = e.getValue();
            }
        }
        return roleName;
    }

    private void deleteRoles(Server server, User user) {
        deleteRolesFromMap(server, user, timeThresholds);
        deleteRolesFromMap(server, user, moneyThresholds);
    }

    private void deleteRolesFromMap(Server server, User user, Map<Integer, String> thresholdMap) {
        thresholdMap.forEach((threshold, roleName) -> roleFinder.getRoleByName(server, roleName).ifPresent(role -> {
            if (user.getRoles(server).contains(role)) {
                user.removeRole(role);
            }
        }));
    }


}
