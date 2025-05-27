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
}
