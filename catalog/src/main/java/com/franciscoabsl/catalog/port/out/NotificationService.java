package com.franciscoabsl.catalog.port.out;

import com.franciscoabsl.catalog.domain.Livro;

public interface NotificationService {

    /**
     * Tenta notificar sobre novo livro cadastrado.
     * @param livro livro cadastrado
     * @return true se notificação enviada com sucesso, false se falhou
     */
    boolean notifyNewBook(Livro livro);
}
