package com.example.login_auth_api.domain.user;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
public class AreaOfExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;
}

//Este código define a estrutura e as regras básicas de validação para a entidade AreaOfExpertise,
//que será usada para persistir dados no banco de dados.