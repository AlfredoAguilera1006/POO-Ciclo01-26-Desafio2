package sv.edu.udb.beans;

public class LibroBeans {
    private int id_libro;
    private String titulo;
    private int año_publicacion;
    private int id_autor;
    private int id_categoria;

    public LibroBeans() {}

    public LibroBeans(int id_libro, String titulo, int año_publicacion, int id_autor, int id_categoria) {
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.año_publicacion = año_publicacion;
        this.id_autor = id_autor;
        this.id_categoria = id_categoria;
    }

    public int getId_libro() { return id_libro; }
    public void setId_libro(int id_libro) { this.id_libro = id_libro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAño_publicacion() { return año_publicacion; }
    public void setAño_publicacion(int año_publicacion) { this.año_publicacion = año_publicacion; }

    public int getId_autor() { return id_autor; }
    public void setId_autor(int id_autor) { this.id_autor = id_autor; }

    public int getId_categoria() { return id_categoria; }
    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }
}