package Backend.Servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Cache {
    public static void guardarEnCache(String contenido) {
        File archivo = new File("cache.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(contenido);
            System.out.println("Cache guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el cache: " + e.getMessage());
        }
    }

    public static String leerCache() {
        File archivo = new File("cache.txt");

        if (!archivo.exists()) {
            return null;
        }

        try {
            return new String(java.nio.file.Files.readAllBytes(archivo.toPath()));
        } catch (IOException e) {
            System.out.println("Error al leer el cache: " + e.getMessage());
            return null;
        }
    }
}
