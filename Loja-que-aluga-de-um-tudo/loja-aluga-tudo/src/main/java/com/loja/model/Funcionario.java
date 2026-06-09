package com.loja.model;

public class Funcionario extends Usuario {

    private String cargo;

    public Funcionario(String nome, String login, String senha, String cargo) {
        super(nome, login, senha);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void promoverPara(String novoCargo) {
        this.cargo = novoCargo;
    }
}