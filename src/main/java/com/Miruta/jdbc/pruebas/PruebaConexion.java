package com.Miruta.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.com.Miruta.jdbc.factory.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

        System.out.println("Cerrando la conexion");

        con.close();
    }

}
