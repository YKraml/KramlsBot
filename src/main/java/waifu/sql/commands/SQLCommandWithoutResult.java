package waifu.sql.commands;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotExecuteMySQLQuery;
import waifu.sql.Connector;
import de.kraml.Terminal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLCommandWithoutResult extends SQLCommand {

    public void executeCommand() throws MyOwnException {

        Connection connection = null;
        try {
            connection = Connector.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(getCommand());
            statement.close();
        } catch (SQLException e) {
            Terminal.printError("Could not execute \"" + this.getCommand() + "\"");
            Connector.getInstance().giveConnection(connection);
            throw new MyOwnException(new CouldNotExecuteMySQLQuery(this.getCommand()), e);
        }

        Connector.getInstance().giveConnection(connection);

    }

}
