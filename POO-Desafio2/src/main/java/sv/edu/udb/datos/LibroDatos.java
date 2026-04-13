package sv.edu.udb.datos;

import sv.edu.udb.beans.LibroBeans;
import sv.edu.udb.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LibroDatos {

    public boolean guardar(LibroBeans libro) {
        boolean ejecutado = false;
        String sql = "INSERT INTO libro (titulo, año_publicacion, id_autor, id_categoria) VALUES (?, ?, ?, ?)";
        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, libro.getTitulo());
            pst.setInt(2, libro.getAño_publicacion());
            pst.setInt(3, libro.getId_autor());
            pst.setInt(4, libro.getId_categoria());

            int res = pst.executeUpdate();
            if (res > 0) ejecutado = true;
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar libro: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return ejecutado;
    }

    public boolean editar(LibroBeans libro) {
        boolean ejecutado = false;
        String sql = "UPDATE libro SET titulo = ?, año_publicacion = ?, id_autor = ?, id_categoria = ? WHERE id_libro = ?";
        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, libro.getTitulo());
            pst.setInt(2, libro.getAño_publicacion());
            pst.setInt(3, libro.getId_autor());
            pst.setInt(4, libro.getId_categoria());
            pst.setInt(5, libro.getId_libro());

            int res = pst.executeUpdate();
            if (res > 0) ejecutado = true;
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar libro: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return ejecutado;
    }

    public boolean eliminar(int id_libro) {
        boolean ejecutado = false;
        String sql = "DELETE FROM libro WHERE id_libro = ?";
        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, id_libro);

            int res = pst.executeUpdate();
            if (res > 0) ejecutado = true;
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar libro: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return ejecutado;
    }

    // Metodo que configurar el default de la tabla
    public DefaultTableModel consultarLibros() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Año");
        modelo.addColumn("Autor");
        modelo.addColumn("Categoría");

        // Usamos INNER JOIN para que en la tabla se vean los nombres y no los IDs
        String sql = "SELECT l.id_libro, l.titulo, l.año_publicacion, a.nombre, c.nombre_categoria " +
                "FROM libro l " +
                "INNER JOIN autor a ON l.id_autor = a.id_autor " +
                "INNER JOIN categoria c ON l.id_categoria = c.id_categoria";

        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_libro");
                fila[1] = rs.getString("titulo");
                fila[2] = rs.getInt("año_publicacion");
                fila[3] = rs.getString("nombre");
                fila[4] = rs.getString("nombre_categoria");
                modelo.addRow(fila);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar libros: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
}
