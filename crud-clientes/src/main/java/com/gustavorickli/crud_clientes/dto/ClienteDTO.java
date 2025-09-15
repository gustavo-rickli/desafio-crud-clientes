package com.gustavorickli.crud_clientes.dto;

import com.gustavorickli.crud_clientes.entity.Cliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class ClienteDTO {


    private Long id;

    @NotEmpty(message = "nome nao pode ser vazio")
    private String name;
    private String cpf;
    private Double income;

    @PastOrPresent(message = "data de nascimento nao pode ser data futura")
    private LocalDate birthDate;
    private Integer children;

    public ClienteDTO() {   }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.name = cliente.getName();
        this.cpf = cliente.getCpf();
        this.income = cliente.getIncome();
        this.birthDate = cliente.getBirthDate();
        this.children = cliente.getChildren();
    }

    public ClienteDTO(Long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClienteDTO(String name, String cpf, Double income, LocalDate birthDate, Integer children) {
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", income=" + income +
                ", birthDate=" + birthDate +
                ", children=" + children +
                '}';
    }
}
