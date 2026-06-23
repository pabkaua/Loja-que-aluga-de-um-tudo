package com.loja.repositories.interfaces;

import com.loja.model.Usuario;
import java.util.List;
import java.util.Map;

public interface UsuarioRepository {

    void salvar(Usuario usuario);

    Usuario buscar(String id);

    Usuario buscarPorEmail(String email);

    Map<String, Usuario> listar();

    Map<String, Usuario> listarPorPerfil(String perfil);

    boolean atualizar(Usuario usuario);

    boolean deletar(String id);

    void carregarDados();

    void salvarDados();
}
