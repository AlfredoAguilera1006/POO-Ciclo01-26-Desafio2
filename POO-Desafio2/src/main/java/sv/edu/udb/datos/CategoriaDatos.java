package sv.edu.udb.datos;

import sv.edu.udb.beans.CategoriaBeans;
import sv.edu.udb.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CategoriaDatos {

    public ArrayList<CategoriaBeans> obtenerCategorias() {
        ArrayList<CategoriaBeans> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                CategoriaBeans cat = new CategoriaBeans();
                cat.setId_categoria(rs.getInt("id_categoria"));
                cat.setNombre_categoria(rs.getString("nombre_categoria"));
                lista.add(cat);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener categorías: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
}