package sv.edu.udb.vista;


import sv.edu.udb.beans.AutorBeans;
import sv.edu.udb.beans.CategoriaBeans;
import sv.edu.udb.beans.LibroBeans;
import sv.edu.udb.datos.AutorDatos;
import sv.edu.udb.datos.CategoriaDatos;
import sv.edu.udb.datos.LibroDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frmBiblioteca extends JFrame {
    // Estos nombres DEBEN coincidir exactamente con los "field name" que pusiste en el diseñador visual
    private JPanel panelPrincipal;
    private JTextField txtTitulo;
    private JTextField txtAño;
    private JComboBox<AutorBeans> cbAutor;
    private JComboBox<CategoriaBeans> cbCategoria;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaLibros;
    private JLabel lblImagen;

    // Variables de apoyo para el CRUD
    private int idLibroSeleccionado = -1;
    private LibroDatos libroDatos = new LibroDatos();
    private AutorDatos autorDatos = new AutorDatos();
    private CategoriaDatos categoriaDatos = new CategoriaDatos();

    public frmBiblioteca() {

        setContentPane(panelPrincipal);
        setTitle("Administración de Biblioteca Digital");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/LibrosIcono.png"));

        java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

        lblImagen.setIcon(new ImageIcon(imagenEscalada));

        cargarCombos();
        actualizarTabla();

        // BOTÓN LIMPIAR
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        // BOTÓN GUARDAR
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    LibroBeans libro = capturarDatosFormulario();
                    if (libroDatos.guardar(libro)) {
                        JOptionPane.showMessageDialog(null, "Libro guardado exitosamente.");
                        actualizarTabla();
                        limpiarFormulario();
                    }
                }
            }
        });

        // BOTÓN EDITAR
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idLibroSeleccionado == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un libro de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (validarCampos()) {
                    LibroBeans libro = capturarDatosFormulario();
                    libro.setId_libro(idLibroSeleccionado);
                    if (libroDatos.editar(libro)) {
                        JOptionPane.showMessageDialog(null, "Libro actualizado exitosamente.");
                        actualizarTabla();
                        limpiarFormulario();
                    }
                }
            }
        });

        // BOTÓN ELIMINAR
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idLibroSeleccionado == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un libro de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este libro?");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (libroDatos.eliminar(idLibroSeleccionado)) {
                        JOptionPane.showMessageDialog(null, "Libro eliminado exitosamente.");
                        actualizarTabla();
                        limpiarFormulario();
                    }
                }
            }
        });

        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaLibros.getSelectedRow();
                if (fila >= 0) {
                    // Tomar datos de la fila
                    idLibroSeleccionado = Integer.parseInt(tablaLibros.getValueAt(fila, 0).toString());
                    txtTitulo.setText(tablaLibros.getValueAt(fila, 1).toString());
                    txtAño.setText(tablaLibros.getValueAt(fila, 2).toString());

                    // Que cuadre con el Autor con el ComboBox
                    String nombreAutor = tablaLibros.getValueAt(fila, 3).toString();
                    for (int i = 0; i < cbAutor.getItemCount(); i++) {
                        if (cbAutor.getItemAt(i).getNombre().equals(nombreAutor)) {
                            cbAutor.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Igual que cuadre la Categoria con el ComboBox
                    String nombreCat = tablaLibros.getValueAt(fila, 4).toString();
                    for (int i = 0; i < cbCategoria.getItemCount(); i++) {
                        if (cbCategoria.getItemAt(i).getNombre_categoria().equals(nombreCat)) {
                            cbCategoria.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void cargarCombos() {

        DefaultComboBoxModel<AutorBeans> modeloAutor = new DefaultComboBoxModel<>();
        for (AutorBeans a : autorDatos.obtenerAutores()) {
            modeloAutor.addElement(a);
        }
        cbAutor.setModel(modeloAutor);

        DefaultComboBoxModel<CategoriaBeans> modeloCat = new DefaultComboBoxModel<>();
        for (CategoriaBeans c : categoriaDatos.obtenerCategorias()) {
            modeloCat.addElement(c);
        }
        cbCategoria.setModel(modeloCat);
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = libroDatos.consultarLibros();
        tablaLibros.setModel(modelo);
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAño.setText("");
        if (cbAutor.getItemCount() > 0) cbAutor.setSelectedIndex(0);
        if (cbCategoria.getItemCount() > 0) cbCategoria.setSelectedIndex(0);
        idLibroSeleccionado = -1;
    }

    private LibroBeans capturarDatosFormulario() {
        LibroBeans libro = new LibroBeans();
        libro.setTitulo(txtTitulo.getText());
        libro.setAño_publicacion(Integer.parseInt(txtAño.getText()));

        AutorBeans autor = (AutorBeans) cbAutor.getSelectedItem();
        libro.setId_autor(autor.getId_autor());

        CategoriaBeans categoria = (CategoriaBeans) cbCategoria.getSelectedItem();
        libro.setId_categoria(categoria.getId_categoria());

        return libro;
    }

    private boolean validarCampos() {
        String titulo = txtTitulo.getText().trim();
        String anioTexto = txtAño.getText().trim();

        // No haya nada en blanco
        if (titulo.isEmpty() || anioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos de texto.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Que tantos caracteres hay, no más de 150
        if (titulo.length() > 150) {
            JOptionPane.showMessageDialog(null, "El título es demasiado largo. El máximo permitido es de 150 caracteres.", "Límite Excedido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Que el año se un numero y no negativo
        try {
            int anio = Integer.parseInt(anioTexto);

            // Año del sistema
            int anioActual = java.time.Year.now().getValue();

            // Que no sea negativo o muy a futuro
            if (anio <= 0 || anio > anioActual) {
                JOptionPane.showMessageDialog(null, "Ingrese un año de publicación lógico (entre el año 1 y el " + anioActual + ").", "Año Inválido", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El año debe ser un número entero válido (sin letras, espacios ni símbolos).", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Que tenga seleccionado el ComboBox
        if (cbAutor.getSelectedItem() == null || cbCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Autor y una Categoría válidos.", "Faltan Selecciones", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new frmBiblioteca().setVisible(true);
            }
        });
    }
}