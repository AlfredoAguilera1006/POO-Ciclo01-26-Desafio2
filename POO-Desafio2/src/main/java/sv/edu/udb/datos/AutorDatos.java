package sv.edu.udb.datos;

import sv.edu.udb.beans.AutorBeans;
import sv.edu.udb.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AutorDatos {

    public ArrayList<AutorBeans> obtenerAutores() {
        ArrayList<AutorBeans> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor";
        try {
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                AutorBeans autor = new AutorBeans();
                autor.setId_autor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                lista.add(autor);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener autores: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
}
