package Backend.Modelos;

public class Recompensa {
    public int id_recompensa;
    public String nombre, descripcion;
    public int puntos_necesarios;

    public Recompensa(int id_recompensa, String nombre, String descripcion, int puntos_necesarios) {
        this.id_recompensa = id_recompensa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos_necesarios = puntos_necesarios;
    }


    // Getters
    public int getId_recompensa() {
        return id_recompensa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntos_necesarios() {
        return puntos_necesarios;
    }

    // Setters
    public void setId_recompensa(int id_recompensa) {
        this.id_recompensa = id_recompensa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntos_necesarios(int puntos_necesarios) {
        this.puntos_necesarios = puntos_necesarios;
    }

@Override
public String toString() {
    return nombre; // Puedes mostrar solo el nombre o lo que tú prefieras
}


}
