package sv.edu.udb.beans;

public class AutorBeans {
    private int id_autor;
    private String nombre;
    private String nacionalidad;

    public AutorBeans() {}

    public AutorBeans(int id_autor, String nombre, String nacionalidad) {
        this.id_autor = id_autor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public int getId_autor() { return id_autor; }
    public void setId_autor(int id_autor) { this.id_autor = id_autor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    @Override
    public String toString() {
        return nombre;
    }
}