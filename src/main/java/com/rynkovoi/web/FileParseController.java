package com.rynkovoi.web;

import com.rynkovoi.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/parse/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileParseController {

    private final UploadService uploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam MultipartFile movies,
                           @RequestParam MultipartFile genres,
                           @RequestParam MultipartFile posters) {
        log.info("Upload and parse files: {}, {}, {}", movies.getOriginalFilename(), genres.getOriginalFilename(), posters.getOriginalFilename());
        uploadService.parseAndSaveFiles(movies, genres, posters);
    }
}