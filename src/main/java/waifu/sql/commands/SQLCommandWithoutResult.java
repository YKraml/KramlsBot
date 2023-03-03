package waifu.sql.commands;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotExecuteMySQLQuery;
import waifu.sql.ConnectionPool;
import de.kraml.Terminal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLCommandWithoutResult extends SQLCommand {

  public void executeCommand(ConnectionPool connectionPool) throws MyOwnException {

    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      Statement statement = connection.createStatement();
      statement.execute(getCommand());
      statement.close();
    } catch (SQLException e) {
      Terminal.printError("Could not execute '%s'. Grund: '%s'".formatted(this.getCommand(), e.getMessage()));
      connectionPool.giveConnection(connection);
      throw new MyOwnException(new CouldNotExecuteMySQLQuery(this.getCommand()), e);
    }

    connectionPool.giveConnection(connection);

  }

}
