package aeterraes.dataaccess;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseConfig {

    private static final Properties properties = new Properties();

    public LiquibaseConfig() {
    }

    public static void runLiquibase() throws Exception {
        System.out.println("Running Liquibase...");

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");
            properties.load(LiquibaseConfig.class.getClassLoader().getResourceAsStream("db/liquibase.properties"));

            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String changeLogFile = properties.getProperty("changeLogFile");

            update.addArgumentValue("changelogFile", changeLogFile);
            update.addArgumentValue("url", url);
            update.addArgumentValue("username", username);
            update.addArgumentValue("password", password);

            update.execute();
        });

        System.out.println("Running Liquibase...DONE");
    }

    public static Connection getConnection() throws SQLException, IOException {
        properties.load(LiquibaseConfig.class.getClassLoader().getResourceAsStream("db/liquibase.properties"));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}