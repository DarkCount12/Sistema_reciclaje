package Backend.Modelos;

public class TipoMaterial {
    private int id_tipo_material;
    private String categoria;
    private double puntos_kg;
    private double co2_reducido_kg;

    public TipoMaterial() {}

    public TipoMaterial(int id_tipo_material, String categoria, double puntos_kg, double co2_reducido_kg) {
        this.id_tipo_material = id_tipo_material;
        this.categoria = categoria;
        this.puntos_kg = puntos_kg;
        this.co2_reducido_kg = co2_reducido_kg;
    }

    public int getId_tipo_material() {
        return id_tipo_material;
    }

    public void setId_tipo_material(int id_tipo_material) {
        this.id_tipo_material = id_tipo_material;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPuntos_kg() {
        return puntos_kg;
    }

    public void setPuntos_kg(double puntos_kg) {
        this.puntos_kg = puntos_kg;
    }

    public double getCo2_reducido_kg() {
        return co2_reducido_kg;
    }

    public void setCo2_reducido_kg(double co2_reducido_kg) {
        this.co2_reducido_kg = co2_reducido_kg;
    }

    @Override
    public String toString() {
        return categoria + " (" + puntos_kg + " pts/kg)";
    }
}
