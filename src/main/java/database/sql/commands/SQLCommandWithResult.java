package database.sql.commands;

import database.sql.ConnectionPool;
import database.sql.entry.AbstractEntrySet;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotExecuteMySQLQuery;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLCommandWithResult<EntrySetType extends AbstractEntrySet<? extends AbstractEntrySet.AbstractEntry>> extends
    SQLCommand {

  protected final EntrySetType entrySet;

  protected SQLCommandWithResult(EntrySetType abstractEntrySet) {
    this.entrySet = abstractEntrySet;
  }

  public EntrySetType executeCommand(ConnectionPool connectionPool) throws MyOwnException {

    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(getCommand());
      while (resultSet.next()) {
        entrySet.addSingleResult(resultSet);
      }
      statement.close();
      connectionPool.giveConnection(connection);
    } catch (SQLException e) {
      connectionPool.giveConnection(connection);
      throw new MyOwnException(new CouldNotExecuteMySQLQuery(this.getCommand()), e);
    }

    return this.entrySet;
  }

}
