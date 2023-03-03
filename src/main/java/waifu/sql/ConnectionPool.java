package waifu.sql;

import de.kraml.Terminal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ConnectionPool {

  private final List<Connection> availableConnections;
  private final List<Connection> usedConnections;
  private final String url;
  private final String userName;
  private final String password;

  @Inject
  public ConnectionPool(String url, String userName, String password) {
    this.url = url;
    this.userName = userName;
    this.password = password;
    availableConnections = Collections.synchronizedList(new ArrayList<>());
    usedConnections = Collections.synchronizedList(new ArrayList<>());
  }

  private Connection createConnection() throws SQLException {
    Terminal.printLine("Connection was created. There are %d connections. ".formatted(availableConnections.size() + usedConnections.size()));
    return DriverManager.getConnection(url, userName, password);
  }


  public Connection getConnection() throws SQLException {

    synchronized (availableConnections) {
      Connection connection =
          availableConnections.size() > 0 ? availableConnections.get(0) : createConnection();

      if (connection.isClosed()) {
        connection = createConnection();
      }

      availableConnections.remove(connection);
      usedConnections.add(connection);
      return connection;
    }
  }

  public void giveConnection(Connection connection) {
    if (connection == null) {
      return;
    }
    usedConnections.remove(connection);
    availableConnections.add(connection);
  }

}
