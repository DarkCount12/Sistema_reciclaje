package Backend.Modelos;

public class Puntaje {
    private int idUsuario;
    private Integer puntosTotales;
    private Integer puntosGastados;
    private Integer puntosGanados;

    public Puntaje(int idUsuario, Integer puntosTotales, Integer puntosGastados, Integer puntosGanados) {
        this.idUsuario = idUsuario;
        this.puntosTotales = puntosTotales;
        this.puntosGastados = puntosGastados;
        this.puntosGanados = puntosGanados;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(Integer puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public Integer getPuntosGastados() {
        return puntosGastados;
    }

    public void setPuntosGastados(Integer puntosGastados) {
        this.puntosGastados = puntosGastados;
    }

    public Integer getPuntosGanados() {
        return puntosGanados;
    }

    public void setPuntosGanados(Integer puntosGanados) {
        this.puntosGanados = puntosGanados;
    }
}
