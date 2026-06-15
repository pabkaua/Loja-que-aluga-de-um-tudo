package com.loja.model;

public class Funcionario extends Usuario {

    private String cargo;

    public Funcionario(String id, String nome, String login, String senha, String cargo) {
        super(id, nome, login, senha);
        this.cargo = cargo;
    }
    public String getPerfil(){
        return "FUNCIONARIO";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }
}