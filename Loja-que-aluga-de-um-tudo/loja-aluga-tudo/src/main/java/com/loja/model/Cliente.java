package com.loja.model;

public class Cliente extends Usuario {

    private boolean inadimplente;

    public Cliente(String id, String nome, String login, String senha) {
        super(id,nome, login, senha); this.inadimplente = false;
    }

    @Override
    public String getPerfil(){
        return "CLIENTE";
    }

    public boolean isInadimplente(){
        return inadimplente;
    }

    public void setInadimplente(boolean inadimplente) {
        this.inadimplente = inadimplente;
    }

}
