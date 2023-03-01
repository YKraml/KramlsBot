package waifu.sql.commands;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotExecuteMySQLQuery;
import de.kraml.Terminal;
import waifu.sql.Connector;
import waifu.sql.entry.AbstractEntrySet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLCommandWithResult<EntrySetType extends AbstractEntrySet<? extends AbstractEntrySet.AbstractEntry>> extends SQLCommand {

    protected final EntrySetType entrySet;

    protected SQLCommandWithResult(EntrySetType abstractEntrySet) {
        this.entrySet = abstractEntrySet;
    }

    public EntrySetType executeCommand() throws MyOwnException {

        Connection connection = null;
        try {
            connection = Connector.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getCommand());
            while (resultSet.next()) {
                entrySet.addSingleResult(resultSet);
            }
            statement.close();
            Connector.getInstance().giveConnection(connection);
        } catch (SQLException e) {
            Terminal.printError("Could not execute \"" + this.getCommand() + "\"");
            Connector.getInstance().giveConnection(connection);
            throw new MyOwnException(new CouldNotExecuteMySQLQuery(this.getCommand()), e);
        }

        return this.entrySet;
    }

}
