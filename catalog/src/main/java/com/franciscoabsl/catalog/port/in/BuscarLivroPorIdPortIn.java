package com.franciscoabsl.catalog.port.in;

import com.franciscoabsl.catalog.domain.Livro;

import java.util.Optional;
import java.util.UUID;

public interface BuscarLivroPorIdPortIn {
    Optional<Livro> execute(UUID id);
}