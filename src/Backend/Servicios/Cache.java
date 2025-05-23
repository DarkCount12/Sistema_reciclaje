package Backend.Servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Cache {

  private static final File archivo = new File("cache.txt");

    public static void guardarEnCache(String correo, String rol) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(correo + "," + rol);
            System.out.println("Cache guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el cache: " + e.getMessage());
        }
    }

    public static String[] leerCache() {
        if (!archivo.exists()) {
            return null;
        }

        try {
            String contenido = new String(java.nio.file.Files.readAllBytes(archivo.toPath()));
            return contenido.split(",", 2); // [correo, rol]
        } catch (IOException e) {
            System.out.println("Error al leer el cache: " + e.getMessage());
            return null;
        }
    }


    public static String obtenerRol() {
    String[] datos = leerCache();
    if (datos != null && datos.length > 1) {
        return datos[1];
    }
    return null;
   }


    public static String obtenerCorreo() {
    String[] datos = leerCache();
    if (datos != null) {
        return datos[0];
    }
    return null;
   }




















}
