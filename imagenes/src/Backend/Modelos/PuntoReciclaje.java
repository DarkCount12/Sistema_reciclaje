package Backend.Modelos;

import java.util.ArrayList;
import java.util.List;

public class PuntoReciclaje {
    private int idPunto;
    private String nombre;
    private String ubicacion;
    private String horario;
    private double latitud;
    private double longitud;

    // NUEVA PROPIEDAD
    private List<Integer> tiposMateriales;

    public PuntoReciclaje(int idPunto, String nombre, String ubicacion, String horario, double latitud, double longitud) {
        this.idPunto = idPunto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tiposMateriales = new ArrayList<>();
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public List<Integer> getTiposMateriales() {
        return tiposMateriales;
    }

    public void setTiposMateriales(List<Integer> tiposMateriales) {
        this.tiposMateriales = tiposMateriales;
    }

    public void agregarTipoMaterial(int idTipoMaterial) {
        if (!tiposMateriales.contains(idTipoMaterial)) {
            tiposMateriales.add(idTipoMaterial);
        }
    }

    public void eliminarTipoMaterial(int idTipoMaterial) {
        tiposMateriales.remove(Integer.valueOf(idTipoMaterial));
    }
}
