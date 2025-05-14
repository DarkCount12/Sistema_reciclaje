package Backend.Modelos;

public class Usuario {
    public int id;
    public String nombre, apellido, correo, contrasena, telefono;

    public Usuario(int id, String nombre, String apellido, String correo, String contrasena, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
}
