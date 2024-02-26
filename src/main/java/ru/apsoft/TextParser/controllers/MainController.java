package ru.apsoft.TextParser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.apsoft.TextParser.entities.ErrorResponse;
import ru.apsoft.TextParser.entities.Response;
import ru.apsoft.TextParser.services.FileService;

@RestController
@RequestMapping(path = "api")
public class MainController {

    @Autowired
    private FileService fileService;

    @PostMapping(path = "/file-parse")
    public ResponseEntity<Object> parseFile(@NonNull @RequestParam("file") MultipartFile file) {
        try {
            final Response response = fileService.parseFile(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

}