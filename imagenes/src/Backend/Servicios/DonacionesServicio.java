package Backend.Servicios;

import Backend.DAOs.DonacionesDAO;
import Backend.Modelos.Donaciones;
import java.util.Date;
import java.util.List;

public class DonacionesServicio {
    private DonacionesDAO donacionesDAO = new DonacionesDAO();
    
    public boolean registrarDonacion(int idUsuario, double monto, String metodoPago, Date fecha) {
        Donaciones donacion = new Donaciones();
        donacion.setIdUsuario(idUsuario);
        donacion.setMonto(monto);
        donacion.setMetodoPago(metodoPago);
        donacion.setFechaDonacion(fecha);
        return donacionesDAO.registrarDonacion(donacion);
    }
    
    public List<Donaciones> obtenerDonacionesPorUsuario(int idUsuario) {
        return donacionesDAO.obtenerDonacionesPorUsuario(idUsuario);
    }
    
    public double obtenerTotalDonacionesUsuario(int idUsuario) {
        return donacionesDAO.obtenerTotalDonacionesUsuario(idUsuario);
    }
}