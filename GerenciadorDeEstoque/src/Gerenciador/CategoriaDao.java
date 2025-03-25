package Gerenciador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Categoria;

public class CategoriaDao {

    // conexão com banco de dados
    private Connection conn;

    public CategoriaDao(Connection conn) {
        this.conn = conn;
    }

    // cadastrando categoria
    public void cadastrarCategoria(Categoria categoria) {
        String query = "CALL cadastrarCategoria(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.executeUpdate();
            System.out.println("Categoria cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar categoria: " + e.getMessage());
        }
    }

    // atualizando categoria
    public void atualizarCategoria(Categoria categoria) {
        String query = "CALL atualizarCategoria(?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoria.getId());
            stmt.setString(2, categoria.getNome());
            stmt.setString(3, categoria.getDescricao());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Categoria atualizada com sucesso!");
            } else {
                System.err.println("Erro: Categoria com ID " + categoria.getId() + " não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a categoria: " + e.getMessage());
        }
    }

    // deletando categoria
    public void deletarCategoria(Categoria categoria) {
        String query = "CALL deletarCategoria(?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoria.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Categoria deletada com sucesso!");
            } else {
                System.err.println("Erro: Categoria com ID " + categoria.getId() + " não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar a categoria: " + e.getMessage());
        }
    }

    // Consultando categorias cadastradas
    public void consultarCategoria() {
        String query = "CALL consultar_categorias()";
        try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) { // Usar executeQuery
            System.out.println("\n** Categorias cadastradas **\n");
            while (rs.next()) {
                System.out.printf("ID: %d | Nome: %s | Descrição: %s\n",
                        rs.getInt("id_categoria"),
                        rs.getString("nome"),
                        rs.getString("descricao"));
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar a categoria: " + e.getMessage());
        }
    }
}