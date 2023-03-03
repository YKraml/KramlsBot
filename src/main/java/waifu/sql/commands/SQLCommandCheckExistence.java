package waifu.sql.commands;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotExecuteMySQLQuery;
import de.kraml.Terminal;
import waifu.sql.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLCommandCheckExistence extends SQLCommand {


  public boolean executeCommand(ConnectionPool connectionPool) throws MyOwnException {

    boolean resultExists = false;
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(this.getCommand());
      if (resultSet.next()) {
        resultExists = true;
      }
      statement.close();
    } catch (SQLException e) {
      Terminal.printError("Could not execute \"" + this.getCommand() + "\"");
      connectionPool.giveConnection(connection);
      throw new MyOwnException(new CouldNotExecuteMySQLQuery(this.getCommand()), e);
    }

    connectionPool.giveConnection(connection);

    return resultExists;
  }
}
