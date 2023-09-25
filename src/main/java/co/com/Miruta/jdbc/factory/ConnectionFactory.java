package co.com.Miruta.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection recuperaConexion() throws SQLException {
		return  DriverManager.getConnection(
                "jdbc:mysql://localhost/miruta?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "Miruta012");
	}
	
	
	
}
