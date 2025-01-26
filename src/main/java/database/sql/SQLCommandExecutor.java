package database.sql;

import database.sql.commands.SQLCommandCheckExistence;
import database.sql.commands.SQLCommandWithResult;
import database.sql.commands.SQLCommandWithoutResult;
import database.sql.entry.AbstractEntrySet;
import domain.exceptions.MyOwnException;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQLCommandExecutor {

  private final ConnectionPool connectionPool;


  @Inject
  public SQLCommandExecutor(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  public <T extends AbstractEntrySet<? extends AbstractEntrySet.AbstractEntry>, K extends SQLCommandWithResult<T>> T execute(
      K command)
      throws MyOwnException {
    return command.executeCommand(connectionPool);
  }

  public boolean execute(SQLCommandCheckExistence command) throws MyOwnException {
    return command.executeCommand(connectionPool);
  }

  public void execute(SQLCommandWithoutResult command) throws MyOwnException {
    command.executeCommand(connectionPool);
  }

}
