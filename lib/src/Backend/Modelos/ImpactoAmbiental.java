package Backend.Modelos;

import java.math.BigDecimal;
import java.sql.Date;

public class ImpactoAmbiental {
    private int idImpacto;
    private Date fecha;
    private BigDecimal kgReciclados;
    private BigDecimal co2Reducidos;
    private Integer idReciclaje;

    public ImpactoAmbiental(int idImpacto, Date fecha, BigDecimal kgReciclados, BigDecimal co2Reducidos, Integer idReciclaje) {
        this.idImpacto = idImpacto;
        this.fecha = fecha;
        this.kgReciclados = kgReciclados;
        this.co2Reducidos = co2Reducidos;
        this.idReciclaje = idReciclaje;
    }

    public int getIdImpacto() {
        return idImpacto;
    }

    public void setIdImpacto(int idImpacto) {
        this.idImpacto = idImpacto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getKgReciclados() {
        return kgReciclados;
    }

    public void setKgReciclados(BigDecimal kgReciclados) {
        this.kgReciclados = kgReciclados;
    }

    public BigDecimal getCo2Reducidos() {
        return co2Reducidos;
    }

    public void setCo2Reducidos(BigDecimal co2Reducidos) {
        this.co2Reducidos = co2Reducidos;
    }

    public Integer getIdReciclaje() {
        return idReciclaje;
    }

    public void setIdReciclaje(Integer idReciclaje) {
        this.idReciclaje = idReciclaje;
    }
}
