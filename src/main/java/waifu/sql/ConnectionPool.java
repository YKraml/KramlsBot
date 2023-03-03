package waifu.sql;

import de.kraml.Terminal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import javax.inject.Singleton;

@Singleton
public final class ConnectionPool {

  private final List<Connection> availableConnections;
  private final List<Connection> usedConnections;
  private final String url;
  private final String userName;
  private final String password;

  public ConnectionPool(String url, String userName, String password) {
    this.url = url;
    this.userName = userName;
    this.password = password;
    this.availableConnections = Collections.synchronizedList(new ArrayList<>());
    this.usedConnections = Collections.synchronizedList(new ArrayList<>());
  }

  private Connection createConnection() throws SQLException {
    return DriverManager.getConnection(url, userName, password);
  }


  public Connection getConnection() throws SQLException {

    Connection connection = availableConnections.stream().findFirst().orElse(createConnection());

    if (connection.isClosed()) {
      connection = createConnection();
      Terminal.printLine("Connection was opened, after it was closed.");
    }

    availableConnections.remove(connection);
    usedConnections.add(connection);
    return connection;
  }

  public void giveConnection(Connection connection) {
    if (connection == null) {
      return;
    }

    this.usedConnections.remove(connection);
    this.availableConnections.add(connection);
  }

}
