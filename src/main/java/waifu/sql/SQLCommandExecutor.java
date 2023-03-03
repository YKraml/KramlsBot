package waifu.sql;

import exceptions.MyOwnException;
import javax.inject.Inject;
import waifu.sql.ConnectionPool;
import waifu.sql.commands.SQLCommandCheckExistence;
import waifu.sql.commands.SQLCommandWithResult;
import waifu.sql.commands.SQLCommandWithoutResult;
import waifu.sql.commands.group.SelectGroupsByOwnerId;
import waifu.sql.entry.AbstractEntrySet;
import waifu.sql.entry.AbstractEntrySet.AbstractEntry;
import waifu.sql.entry.GroupEntrySet;

public class SQLCommandExecutor {

  private final ConnectionPool connectionPool;


  @Inject
  public SQLCommandExecutor(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  public <T extends AbstractEntrySet<? extends AbstractEntry>, K extends SQLCommandWithResult<T>> T execute(
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
