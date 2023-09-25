package co.com.Miruta.jdbc.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.com.Miruta.jdbc.controller.CategoriaController;
import co.com.Miruta.jdbc.controller.ProductoController;

public class ControlDeRegistros extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombres, labelApellidos, labelDocumento, labelCorreo_electronico, labelTarjeta_sitp, labelTelefono;
    private JTextField textoNombres, textoApellidos, textoDocumento, textoCorreo, textoTarjeta, textoTelefono;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ProductoController productoController;
    private CategoriaController categoriaController;

	

    public ControlDeRegistros() {
        super("Registro");

        this.categoriaController = new CategoriaController();
        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);

        configurarAccionesDelFormulario();
    }

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
     
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Documento");
        modelo.addColumn("Correo electronico");
        modelo.addColumn("tarjeta SITP");
        modelo.addColumn("Telefono");

        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonReporte = new JButton("Ver Reporte");
        botonEliminar.setBounds(10, 500, 80, 20);
        botonModificar.setBounds(100, 500, 80, 20);
        botonReporte.setBounds(190, 500, 80, 20);

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonReporte);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void configurarCamposDelFormulario(Container container) {
        labelNombres = new JLabel("Nombres");
        labelApellidos = new JLabel("Apellidos");
        labelDocumento = new JLabel("Documento");
        labelCorreo_electronico = new JLabel("Correo electronico");
        labelTarjeta_sitp = new JLabel("Tarjeta sitp");
        labelTelefono = new JLabel("Numero de telofono");

        labelNombres.setBounds(10, 10, 240, 15);
        labelApellidos.setBounds(10, 50, 240, 15);
        labelDocumento.setBounds(10, 90, 240, 15);
        labelCorreo_electronico.setBounds(300, 10, 240, 15);
        labelTarjeta_sitp.setBounds(300, 50, 240, 15);
        labelTelefono.setBounds(300, 90, 240, 15);        

        labelNombres.setForeground(Color.BLACK);
        labelApellidos.setForeground(Color.BLACK);
        labelDocumento.setForeground(Color.BLACK);
        labelCorreo_electronico.setForeground(Color.BLACK);
        labelTarjeta_sitp.setForeground(Color.BLACK);
        labelTelefono.setForeground(Color.BLACK);
       
        

        textoNombres = new JTextField();
        textoApellidos = new JTextField();
        textoDocumento = new JTextField();
        textoCorreo = new JTextField();
        textoTarjeta = new JTextField();
        textoTelefono = new JTextField();
        

        // TODO
        var categorias = this.categoriaController.listar();
        // categorias.forEach(categoria -> comboCategoria.addItem(categoria));

        textoNombres.setBounds(10, 30, 200, 20);
        textoApellidos.setBounds(10, 70, 200, 20);
        textoDocumento.setBounds(10, 110, 200, 20);
        textoCorreo.setBounds(300, 30, 200, 20);
        textoTarjeta.setBounds(300, 70, 200, 20);
        textoTelefono.setBounds(300, 110, 200, 20);
       

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(10, 175, 80, 20);
        botonLimpiar.setBounds(100, 175, 80, 20);
        
        container.add(labelNombres);
        container.add(labelApellidos);
        container.add(labelDocumento);
        container.add(labelCorreo_electronico);
        container.add(labelTarjeta_sitp);
        container.add(labelTelefono);

        container.add(textoNombres);
        container.add(textoApellidos);
        container.add(textoDocumento);
        container.add(textoCorreo);
        container.add(textoTarjeta);
        container.add(textoTelefono);
        
       
        container.add(botonGuardar);
        container.add(botonLimpiar);
    }

    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificar();
                limpiarTabla();
                cargarTabla();
            }
        });
    }

    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    private void modificar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un campo");
            return;
        }

    Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                	.ifPresentOrElse(fila -> {
                
                    String NOMBRES = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
                    String APELLIDOS = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
                    String DOCUMENTO = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
                    String CORREO_ELECTRONICO = (String) modelo.getValueAt(tabla.getSelectedRow(), 3);
                    String TARJETA_SITP = (String) modelo.getValueAt(tabla.getSelectedRow(), 4);
                    String TELEFONO = (String) modelo.getValueAt(tabla.getSelectedRow(), 5); 
                    
                    int filasModificadas;

                    try {
						filasModificadas = this.productoController.modificar(NOMBRES, APELLIDOS, DOCUMENTO, CORREO_ELECTRONICO, TARJETA_SITP, TELEFONO);
					} catch (SQLException e) {
						 e.printStackTrace();
						new RuntimeException(e);
					}
                    JOptionPane.showMessageDialog(this, String.format("registro modificado con éxito!"));
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un campo"));
    }

    private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un campo");
            return;
        }

   Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                	.ifPresentOrElse(fila -> {
                	
                	String DOCUMENTO = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
                 
                    int cantidadEliminada;
                    
                    try {
						cantidadEliminada = this.productoController.eliminar(DOCUMENTO);
					} catch (SQLException e) {
						new RuntimeException(e);
					}

                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this,"Registro eliminado con exito!");
                },  () -> JOptionPane.showMessageDialog(this, "Por favor, elije un regisro"));
    }

    private void cargarTabla() {
    	try {
        var Lista = this.productoController.listar();
        
        try {
        	 Lista.forEach(datos -> modelo.addRow(new Object[] { 
        	 datos.get("NOMBRES"),
        	 datos.get("APELLIDOS"),
        	 datos.get("DOCUMENTO"),
        	 datos.get("CORREO_ELECTRONICO"),
             datos.get("TARJETA_SITP"),
             datos.get("TELEFONO")  			 
              }));
        } catch (Exception e) {
            throw e;
        }
    	}catch(SQLException e) {
    		throw new RuntimeException(e);
    	}
    }
     

    private void guardar() {
        if (textoNombres.getText().isBlank() || textoApellidos.getText().isBlank() || textoDocumento.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombres, apellidos y documento son requeridos.");
            return;
        }

        var nuevosRegistros = new HashMap<String, String>();
        nuevosRegistros.put("NOMBRES", textoNombres.getText());
        nuevosRegistros.put("APELLIDOS", textoApellidos.getText());
        nuevosRegistros.put("DOCUMENTO", textoDocumento.getText());
        nuevosRegistros.put("TARJETA_SITP", textoTarjeta.getText());
        nuevosRegistros.put("TELEFONO", textoTelefono.getText());
        
        try {
			this.productoController.guardar(nuevosRegistros);
		} catch (SQLException e) {
			new RuntimeException(e);
		}

        JOptionPane.showMessageDialog(this, "Registrado con Exito!");

        this.limpiarFormulario();
    }

    private void limpiarFormulario() {
        this.textoNombres.setText("");
        this.textoApellidos.setText("");
        this.textoDocumento.setText("");
        this.textoCorreo.setText("");
        this.textoTarjeta.setText("");
        this.textoTelefono.setText("");
    }

}
