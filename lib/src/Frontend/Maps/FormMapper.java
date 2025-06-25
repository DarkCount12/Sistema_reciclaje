package Frontend.Maps;

import java.util.Map;
import javax.swing.*;

public interface FormMapper<T> {
    Map<String, JComponent> crearCampos(T entidad);
    T construirDesdeCampos(Map<String, JComponent> campos);
    int getId(T entidad);
}
