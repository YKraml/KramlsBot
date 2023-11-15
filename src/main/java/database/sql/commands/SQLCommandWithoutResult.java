package database.sql.commands;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotExecuteMySQLQuery;
import database.sql.ConnectionPool;
import util.Terminal;

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
