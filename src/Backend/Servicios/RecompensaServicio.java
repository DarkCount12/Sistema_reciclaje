package Backend.Servicios;

import Backend.DAOs.RecompensaDAO;
import Backend.Modelos.Recompensa;

import java.util.List;

public class RecompensaServicio {
    private RecompensaDAO recompensaDAO;

    public RecompensaServicio() {
        this.recompensaDAO = new RecompensaDAO();
    }

    public List<Recompensa> obtenerTodasLasRecompensas() {
        return recompensaDAO.obtenerTodasLasRecompensas();
    }

    public Recompensa obtenerRecompensaPorId(int id_recompensa) {
        return recompensaDAO.obtenerRecompensaPorId(id_recompensa);
    }
}
