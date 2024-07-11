package com.example.login_auth_api.controllers;

import com.example.login_auth_api.domain.user.Professionals;
import com.example.login_auth_api.exceptions.ResourceNotFoundException;
import com.example.login_auth_api.repositories.LevelOfExpertiseRepository;
import com.example.login_auth_api.repositories.ProfessionalsRepository;
import com.example.login_auth_api.repositories.SpecialtyRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/Professionals")
@SecurityRequirement(name = "jwt")
public class ProfessionalsController {

    @Autowired
    private ProfessionalsRepository professionalsRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private LevelOfExpertiseRepository levelOfExpertiseRepository;

    @GetMapping
    public List<Professionals> getAllProfessionals() {
        return professionalsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Professionals> createProfessionals(@Valid @RequestBody Professionals professionals) {
        professionals.setSpecialty(specialtyRepository.findById(professionals.getSpecialty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found")));
        professionals.setLevelOfExpertise(levelOfExpertiseRepository.findById(professionals.getLevelOfExpertise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("LevelOfExpertise not found")));
        Professionals savedProfessionals = professionalsRepository.save(professionals);
        return ResponseEntity.ok(savedProfessionals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professionals> getProfessionalsById(@PathVariable Long id) {
        Professionals professionals = professionalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professionals not found with id " + id));
        return ResponseEntity.ok(professionals);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<Professionals> uploadPhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile photo) {
        Professionals professionals = professionalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professionals not found with id " + id));
        try {
            professionals.setPhoto(photo.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        professionalsRepository.save(professionals);
        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getProfessionalsPhoto(@PathVariable Long id) {
        Professionals professionals = professionalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professionals not found with id " + id));
        byte[] photo = professionals.getPhoto();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"photo.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(photo);
    }
}

//Este controlador lida com a lógica de criação, recuperação e atualização de profissionais,
//incluindo o upload e download de fotos,
//utilizando repositórios para persistência de dados e tratamento de exceções para recursos não encontrados.