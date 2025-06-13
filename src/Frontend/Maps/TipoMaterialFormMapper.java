package Frontend.Maps;

import Backend.Modelos.TipoMaterial;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class TipoMaterialFormMapper implements FormMapper<TipoMaterial> {

    @Override
    public Map<String, JComponent> crearCampos(TipoMaterial tm) {
        Map<String, JComponent> campos = new LinkedHashMap<>();


        JTextField txtCategoria = new JTextField();
        JTextField txtPuntos = new JTextField();
        JTextField txtCO2 = new JTextField();

        if (tm != null) {
            txtCategoria.setText(tm.getCategoria());
            txtPuntos.setText(String.valueOf(tm.getPuntos_kg()));
            txtCO2.setText(String.valueOf(tm.getCo2_reducido_kg()));
        }

        campos.put("Categoría", txtCategoria);
        campos.put("Puntos por Kg", txtPuntos);
        campos.put("CO2 reducido por Kg", txtCO2);

        return campos;
    }

    @Override
    public TipoMaterial construirDesdeCampos(Map<String, JComponent> campos) {
        TipoMaterial tm = new TipoMaterial();
        tm.setCategoria(((JTextField) campos.get("Categoría")).getText());
        tm.setPuntos_kg(Double.parseDouble(((JTextField) campos.get("Puntos por Kg")).getText()));
        tm.setCo2_reducido_kg(Double.parseDouble(((JTextField) campos.get("CO2 reducido por Kg")).getText()));
        return tm;
    }

    @Override
    public int getId(TipoMaterial tm) {
        return tm.getId_tipo_material();
    }
}
