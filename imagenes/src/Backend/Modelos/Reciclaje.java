package Backend.Modelos;

import java.sql.Date;

public class Reciclaje {
    private int idReciclaje;
    private Date fechaReciclaje;
    private int idUsuario;
    private Integer idPunto;

    public Reciclaje(int idReciclaje, Date fechaReciclaje, int idUsuario, Integer idPunto) {
        this.idReciclaje = idReciclaje;
        this.fechaReciclaje = fechaReciclaje;
        this.idUsuario = idUsuario;
        this.idPunto = idPunto;
    }

    public int getIdReciclaje() {
        return idReciclaje;
    }

    public void setIdReciclaje(int idReciclaje) {
        this.idReciclaje = idReciclaje;
    }

    public Date getFechaReciclaje() {
        return fechaReciclaje;
    }

    public void setFechaReciclaje(Date fechaReciclaje) {
        this.fechaReciclaje = fechaReciclaje;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Integer idPunto) {
        this.idPunto = idPunto;
    }
}
