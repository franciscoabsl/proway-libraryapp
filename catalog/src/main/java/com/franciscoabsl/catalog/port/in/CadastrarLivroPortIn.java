package com.franciscoabsl.catalog.port.in;

import com.franciscoabsl.catalog.domain.Livro;

public interface CadastrarLivroPortIn {
    Livro execute(String titulo, String autor, int anoPublicacao);
}
