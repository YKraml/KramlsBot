package logic.discord;

import java.util.Optional;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;

public class RoleFinder {

  public Optional<Role> getRoleByName(Server server, String roleName) {
    for (Role role : server.getRoles()) {
      if (role.getName().contains(roleName)) {
        return Optional.of(role);
      }
    }
    return Optional.empty();
  }

}
