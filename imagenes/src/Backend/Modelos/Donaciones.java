package Backend.Modelos;

import java.util.Date;

public class Donaciones {
    private int idDonacion;
    private int idUsuario;
    private double monto;
    private String metodoPago;
    private Date fechaDonacion;
    
    // Getters y Setters
    public int getIdDonacion() { return idDonacion; }
    public void setIdDonacion(int idDonacion) { this.idDonacion = idDonacion; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public Date getFechaDonacion() { return fechaDonacion; }
    public void setFechaDonacion(Date fechaDonacion) { this.fechaDonacion = fechaDonacion; }
}