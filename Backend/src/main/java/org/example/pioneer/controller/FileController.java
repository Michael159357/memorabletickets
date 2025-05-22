package org.example.pioneer.controller;

import org.example.pioneer.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/api/pioneer/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Endpoint para subir un archivo.
     * El archivo será guardado como .webp.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Almacenar el archivo y obtener la ruta de almacenamiento
        String filePath = fileStorageService.storeFile(file);

        // Responder con la ruta del archivo guardado en un objeto JSON
        return ResponseEntity.ok().body("{\"filePath\":\"" + filePath + "\"}");
    }


    /**
     * Endpoint para obtener una imagen ya convertida.
     * La imagen se devuelve con el formato .webp.
     */
    @GetMapping("/image/{filename}")
    public ResponseEntity<String> getImage(@PathVariable String filename) {

        System.out.println("Filename recibido: " + filename);

        // Aquí aseguramos que la extensión del archivo sea .webp
        String imageFilePath = "/image/" + filename + ".webp";

        return ResponseEntity.ok("Accede a la imagen en: " + imageFilePath);
    }
}
