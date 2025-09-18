package com.franciscoabsl.catalog.domain;

import java.util.UUID;

public class Livro {

    private final UUID id;
    private final String titulo;
    private final String autor;
    private final int anoPublicacao;

    public Livro(UUID id, String titulo, String autor, int anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    @Override
    public String toString() {
        return "Livro {\n" +
                "  id: " + id + ",\n" +
                "  título: '" + titulo + "',\n" +
                "  autor: '" + autor + "',\n" +
                "  ano de publicação: " + anoPublicacao + "\n" +
                "}";
    }
}