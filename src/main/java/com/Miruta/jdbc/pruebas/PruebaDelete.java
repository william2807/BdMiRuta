package com.Miruta.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import co.com.Miruta.jdbc.factory.ConnectionFactory;

public class PruebaDelete {

	public static void main(String[] args) throws SQLException {
Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM registro WHERE documento = ");
		
		System.out.println(statement.getUpdateCount()); 

	}

}
