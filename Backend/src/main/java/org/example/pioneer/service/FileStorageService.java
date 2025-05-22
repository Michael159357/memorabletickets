package org.example.pioneer.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("src/main/resources/static/image");

    // Constructor para inicializar el directorio de almacenamiento
    public FileStorageService() {
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
                System.out.println("Directorio creado: " + fileStorageLocation.toAbsolutePath());
            }
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento", ex);
        }
    }

    /**
     * Método para guardar un archivo con progreso en la consola.
     */
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("El archivo está vacío");
            }

            System.out.println("Archivo recibido: " + file.getOriginalFilename());

            // Convertir la imagen a WEBP
            String newFileName = FilenameUtils.removeExtension(file.getOriginalFilename()) + ".webp";
            Path targetLocation = fileStorageLocation.resolve(newFileName);

            // Obtener el tamaño total del archivo
            long totalBytes = file.getSize();
            long bytesRead = 0;

            // Convertir y guardar la imagen como WEBP
            try (InputStream inputStream = file.getInputStream();
                 OutputStream outputStream = Files.newOutputStream(targetLocation)) {

                byte[] buffer = new byte[8192]; // Tamaño del buffer
                int bytesReadThisTime;

                while ((bytesReadThisTime = inputStream.read(buffer)) != -1) {
                    // Escribir los bytes en el archivo de destino
                    outputStream.write(buffer, 0, bytesReadThisTime);
                    bytesRead += bytesReadThisTime;

                    // Calcular el progreso y mostrarlo en la consola
                    int progress = (int) ((bytesRead * 100) / totalBytes);
                    System.out.print("\rProgreso: [" + "=".repeat(progress) + "] " + progress + "%");
                }
            }

            System.out.println(); // Nueva línea después de mostrar el progreso
            System.out.println("Archivo guardado en: " + targetLocation.toAbsolutePath());

            return "/image/" + newFileName; // Ruta accesible al cliente
        } catch (IOException ex) {
            System.err.println("Error al guardar el archivo: " + ex.getMessage());
            throw new RuntimeException("Error al guardar el archivo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Método para guardar un archivo de forma asíncrona.
     */
    @Async
    public CompletableFuture<String> storeFileAsync(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> storeFile(file));
    }
}
