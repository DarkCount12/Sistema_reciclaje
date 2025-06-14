package Backend.Modelos;

import java.sql.Timestamp;

public class Canje {
    private int idCanje;
    private Timestamp fechaCanje;
    private int puntosGastados;
    private int idUsuario;
    private int idRecompensa;

    public Canje(Timestamp fechaCanje, int puntosGastados, int idUsuario, int idRecompensa) {
        this.fechaCanje = fechaCanje;
        this.puntosGastados = puntosGastados;
        this.idUsuario = idUsuario;
        this.idRecompensa = idRecompensa;
    }

    public int getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(int idCanje) {
        this.idCanje = idCanje;
    }

    public Timestamp getFechaCanje() {
        return fechaCanje;
    }

    public void setFechaCanje(Timestamp fechaCanje) {
        this.fechaCanje = fechaCanje;
    }

    public int getPuntosGastados() {
        return puntosGastados;
    }

    public void setPuntosGastados(int puntosGastados) {
        this.puntosGastados = puntosGastados;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(int idRecompensa) {
        this.idRecompensa = idRecompensa;
    }
}
