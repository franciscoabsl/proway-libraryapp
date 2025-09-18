package com.franciscoabsl.catalog.port.in;

import com.franciscoabsl.catalog.domain.Livro;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookUseCasePortIn {

    Livro cadastrarLivro(String titulo, String autor, int anoPublicacao);

    Optional<Livro> buscarLivroPorId(UUID id);

    List<Livro> listarTodos();
}