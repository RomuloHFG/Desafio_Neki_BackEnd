package com.example.login_auth_api.domain.user;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class Professionals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    @NotNull(message = "Specialty is required")
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "area_of_expertise_id", nullable = false)
    @NotNull(message = "Area of Expertise is required")
    private AreaOfExpertise areaOfExpertise;

    @NotEmpty(message = "Address is required")
    private String address;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Phone is required")
    private String phone;

    @Lob
    private byte[] photo;
}

//Este código define a estrutura e as regras básicas de validação para a entidade Professionals,
//que será usada para persistir dados no banco de dados,
//incluindo relacionamentos com outras entidades (Specialty e AreaOfExpertise) e validações para assegurar que os campos
//obrigatórios sejam preenchidos corretamente.