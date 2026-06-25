package com.loja.business;

import com.loja.model.Usuario;
import com.loja.repositories.interfaces.IUsuarioRepository;

import java.util.List;

public class UsuarioBusiness {
    private IUsuarioRepository usuarioRepository;

    public UsuarioBusiness(IUsuarioRepository repo) {
        this.usuarioRepository = repo;
    }

    public void cadastrar(Usuario usuario) {
        if (usuarioRepository.buscarPorEmail(usuario.getLogin()) != null) {
            throw new RuntimeException("Login já cadastrado: " + usuario.getLogin());
        }
        usuarioRepository.salvar(usuario);
    }

    public Usuario buscarPorId(String id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado: " + id);
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado: " + email);
        }
        return usuario;
    }

    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    public void atualizar(String id, Usuario dados) {
        Usuario existente = usuarioRepository.buscarPorId(id);
        if (existente == null) {
            throw new RuntimeException("Usuário não encontrado: " + id);
        }
        existente.setNome(dados.getNome());
        existente.setLogin(dados.getLogin());
        existente.setSenha(dados.getSenha());
        usuarioRepository.salvar(existente);
    }

    public void deletar(String id) {
        if (usuarioRepository.buscarPorId(id) == null) {
            throw new RuntimeException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deletar(id);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Email ou senha inválidos.");
        }
        return usuario;
    }
}
