package com.franciscoabsl.catalog.port.out;

import com.franciscoabsl.catalog.domain.Livro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Livro save(Livro livro);

    Optional<Livro> findById(UUID id);

    List<Livro> findAll();
}