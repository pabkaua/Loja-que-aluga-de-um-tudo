package com.loja.model;

import java.util.UUID;

public abstract class Usuario {

    private final String id;
    private String nome;
    private String login;
    private String senha;
    private boolean ativo;

    protected Usuario(String nome, String login, String senha) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ativo = true;
    }

    public void arquivarUsuario() {
        this.ativo = false;
    }

    public void reativarUsuario() {
        this.ativo = true;
    }

    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getLogin() {
        return login;
    }
    public String getSenha() {
        return senha;
    }
    public boolean estaAtivo() {
        return ativo;
    }

    public void atualizarDados(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }
}