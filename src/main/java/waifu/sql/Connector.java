package waifu.sql;

import de.kraml.Main;
import de.kraml.Terminal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public final class Connector {

    private final static String DATABASE = "KRAMLSBOT";
    private final static String USERNAME = Main.getUserName();
    private final static String PASSWORD = Main.getPassword();
    private final static String URL = "jdbc:mysql://" + Main.getIp() + "/" + DATABASE + "?serverTimezone=Europe/Berlin&useSSL=false&allowPublicKeyRetrieval=true";


    private static Connector connector;
    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections;

    public synchronized static Connector getInstance() {
        if (connector == null) {
            connector = new Connector();
        }
        return connector;
    }

    private Connector() {
        this.availableConnections = Collections.synchronizedList(new ArrayList<>());
        this.usedConnections = Collections.synchronizedList(new ArrayList<>());

    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public Connection getConnection() throws SQLException {

        Connection connection;

        if (!this.availableConnections.isEmpty()) {
            connection = this.availableConnections.get(0);
        } else {
            connection = this.createConnection();
            Terminal.printLine("Created new Connection | Used: " + this.usedConnections.size() + " | Available: " + this.availableConnections.size());
        }

        if (connection.isClosed()) {
            Terminal.printLine("Connection was opened");
            connection = this.createConnection();
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
