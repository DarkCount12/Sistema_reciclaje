package Backend.Utils;

public class Colores {

    public enum Color {
        BLACK("#000000"),
        WHITE("#FFFFFF"),
        YELLOW("#FFFF00"),
        LIGHT_GRAY("#E6E5E5"),
        BLUE("#121EA1"),
        DARK_BLUE("#04102F"),
        LIGHT_BLUE("#E5E7EA"),
        RED("#C13E3E");

        private final String hexCode;

        Color(String hexCode) {
            this.hexCode = hexCode;
        }

        public String getHexCode() {
            return hexCode;
        }
    }

    public static String BLACK = Color.BLACK.getHexCode();
    public static String WHITE = Color.WHITE.getHexCode();
    public static String YELLOW = Color.YELLOW.getHexCode();
    public static String LIGHT_GRAY = Color.LIGHT_GRAY.getHexCode();
    public static String BLUE = Color.BLUE.getHexCode();
    public static String DARK_BLUE = Color.DARK_BLUE.getHexCode();
    public static String LIGHT_BLUE = Color.LIGHT_BLUE.getHexCode();
    public static String RED = Color.RED.getHexCode();
}