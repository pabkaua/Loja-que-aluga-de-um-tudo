package com.loja.repositories;

import com.loja.model.Usuario;
import com.loja.repositories.interfaces.UsuarioRepository;

import java.io.*;
import java.util.Collections;
import java.util.stream.*;
import java.util.Map;

public class UsuarioPersistenciaCSV implements UsuarioRepository {

    private String caminhoArquivo;
    private Map<String, Usuario> usuarios;

    public UsuarioPersistenciaCSV(String caminhoArquivo, Map<String, Usuario> usuarios) {
        this.caminhoArquivo = caminhoArquivo;
        this.usuarios = usuarios;
    }

    public String getCaminhoArquivo() {return caminhoArquivo;}
    public void setCaminhoArquivo(String caminhoArquivo) {this.caminhoArquivo = caminhoArquivo;}

    @Override
    public void salvar(Usuario usuario){
        usuarios.put(usuario.getId(), usuario);
    }

    @Override
    public Usuario buscar(String id){
        return usuarios.get(id);
    }

    @Override
    public Usuario buscarPorEmail(String email){
        return this.usuarios.values().stream()
                .filter(usuario -> usuario.getLogin().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<String, Usuario> listar(){
        return Collections.unmodifiableMap(this.usuarios);
    }

    @Override
    public Map<String, Usuario> listarPorPerfil(String perfil){
        return this.usuarios.entrySet().stream()
                .filter(entry -> entry.getValue().getPerfil().equalsIgnoreCase(perfil))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        if (usuarios.containsKey(usuario.getId())) {
            usuarios.put(usuario.getId(), usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletar(String id) {
        if (usuarios.containsKey(id)) {
            usuarios.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.caminhoArquivo))) {
            String linha;

            String cabecalho = br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    String id = dados[0];
                    String nome = dados[1];
                    String login = dados[2];
                    String senha = dados[3];
                    String perfil = dados[4];

                    Usuario usuario = null;
                    if (perfil.equalsIgnoreCase("ADMIN")) {
                    } else if (perfil.equalsIgnoreCase("CLIENTE")) {
                    }

                    if (usuario != null) {
                        this.usuarios.put(id, usuario);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do arquivo CSV: " + e.getMessage());
        }
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            escritor.write("id;nome;login;senha;perfil");
            escritor.newLine();

            for (Usuario usuario : usuarios.values()) {
                String linha =
                        usuario.getId() + ";" +
                        usuario.getNome() + ";" +
                        usuario.getLogin() + ";" +
                        usuario.getSenha() + ";" +
                        usuario.getPerfil();
                escritor.write(linha);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo CSV: " + e.getMessage());
        }
    }


}
