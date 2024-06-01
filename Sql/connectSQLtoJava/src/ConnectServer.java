import java.sql.*;

public class ConnectServer {
        Connection connection = null;
        public Connection connectDB(){
            try {
                System.setProperty("java.library.path", "C:\\Program Files\\sqljdbc_12.6\\enu\\auth\\x64");

                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver()); // Register JDBC driver

                String jdbcURL = "jdbc:sqlserver://";
                String servername = "RAYNER\\SQLEXPRESS"; // Servername beserta instance name
                String portNumber = ":1433;"; // Port number yang digunakan adalah 1433
                String dbName = "databaseName=Furnitur;"; // Database yang digunakan bernama Furnitur
                String security = "encrypt=true;integratedSecurity=true;trustServerCertificate=true"; // karena menggunakan
                                                                                                    // windows
                                                                                                    // authenticataion,
                                                                                                    // maka
                                                                                                    // integratedSecurity
                                                                                                    // = true
                String cURL = jdbcURL + servername + portNumber + dbName + security; // Syntax full dari connection URL

                connection = DriverManager.getConnection(cURL);
                stmt = connection.createStatement();
                System.out.println("Connection Established.");

                return connection;
            } catch (Exception e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            
}
