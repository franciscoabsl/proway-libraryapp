package com.franciscoabsl.catalog.adapter.out.db;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.out.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DbBookRepository implements BookRepository {

    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public DbBookRepository(String dbUrl, String dbUser, String dbPass) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        createTableIfNotExists();
    }

    public DbBookRepository() {
        this.dbUrl = "jdbc:h2:./data/catalogdb";
        this.dbUser = "sa";
        this.dbPass = "";
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS livro (" +
                "id VARCHAR(36) PRIMARY KEY," +
                "titulo VARCHAR(255)," +
                "autor VARCHAR(255)," +
                "anoPublicacao INT" +
                ")";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Livro save(Livro livro) {
        String sql = "INSERT INTO livro (id, titulo, autor, anoPublicacao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livro.getId().toString());
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setInt(4, livro.getAnoPublicacao());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }

    @Override
    public Optional<Livro> findById(UUID id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Livro(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anoPublicacao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Livro> findAll() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(new Livro(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anoPublicacao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
}