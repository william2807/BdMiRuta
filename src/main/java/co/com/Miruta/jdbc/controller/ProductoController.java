package co.com.Miruta.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.Miruta.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String NOMBRES, String APELLIDOS, String DOCUMENTO, String CORREO_ELECTRONICO, String TARJETA_SITP, String TELEFONO ) throws SQLException {
		   
			Connection con = new ConnectionFactory().recuperaConexion();
			
		    PreparedStatement stmt = con.prepareStatement("UPDATE registro SET NOMBRES = ?, APELLIDOS = ?, DOCUMENTO = ?, CORREO_ELECTRONICO = ?, TARJETA_SITP = ?, TELEFONO = ?"
		    		+ "  WHERE DOCUMENTO = ");
		    stmt.setString(1, NOMBRES);
		    stmt.setString(2, APELLIDOS);
		    stmt.setString(2, DOCUMENTO);
		    stmt.setString(3, CORREO_ELECTRONICO);
		    stmt.setString(4, TARJETA_SITP);
		    stmt.setString(5, TELEFONO);
		    
		    int updateCount = stmt.executeUpdate();
		    
		    con.close();
		    
		return updateCount;
	}

	public int eliminar(String documento) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM registro WHERE DOCUMENTO = ");
		
		con.close();
		
		return statement.getUpdateCount();
		}

	public List<Map<String,String>> listar() throws SQLException{
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement =con.createStatement();
	
		statement.execute("SELECT NOMBRES, APELLIDOS, DOCUMENTO, CORREO_ELECTRONICO, TARJETA_SITP, TELEFONO FROM registro");
	
		ResultSet resultset = statement.getResultSet();
	
		List<Map<String,String>> resultado = new ArrayList();
	
		while(resultset.next()) {
			Map<String, String> fila = new HashMap<>(); 
		
			fila.put("NOMBRES", resultset.getString("NOMBRES"));
			fila.put("APELLIDOS", resultset.getString("APELLIDOS"));
			fila.put("DOCUMENTO", resultset.getString("DOCUMENTO"));
			fila.put("CORREO_ELECTRONICO", resultset.getString("CORREO_ELECTRONICO"));
			fila.put("TARJETA_SITP", resultset.getString("TARJETA_SITP"));
			fila.put("TELEFONO", resultset.getString("TELEFONO"));
		
			resultado.add(fila);
		
	}

	con.close();
	
	return resultado;
	
		
	}

    public void guardar(Map<String, String> registro) throws SQLException  {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		
		statement.execute("INSERT INTO registro (NOMBRES,APELLIDOS,DOCUMENTO,CORREO_ELECTRONICO,"
				+ "TARJETA_SITP,TELEFONO) "
				+ "VALUES('" + registro.get("NOMBRES") + "','" 
				+ registro.get("APELLIDOS") + "','" 
				+ registro.get("DOCUMENTO") + "','" 
				+ registro.get("CORREO_ELECTRONICO") + "','"   
				+ registro.get("TARJETA_SITP") + "','" 
				+ registro.get("TELEFONO") + "')");
		
		ResultSet resulset = statement.getGeneratedKeys();
		
		while (resulset.next()) {
			System.out.println(
					String.format(
							"fue insertado el nuevo registro", 
							resulset.getInt(1)));
		
	}
}

}
