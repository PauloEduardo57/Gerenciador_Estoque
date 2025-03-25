package Gerenciador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Produto;

public class ProdutoDao {

    // conexão com banco de dados
    private Connection conn;

    public ProdutoDao(Connection conn) {
        this.conn = conn;
    }

    // cadastrando produtos
    public void cadastrarProduto(Produto produto) {
        String query = "CALL cadastrarProduto(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setDouble(4, produto.getPrecoCompra());
            stmt.setDouble(5, produto.getPrecoVenda());
            stmt.setInt(6, produto.getCategoria().getId());
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar o produto: " + e.getMessage());
        }
    }

    // atualizando produto
    public void atualizarProduto(Produto produto) {
        String query = "CALL atualizarProduto(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, produto.getId());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setDouble(5, produto.getPrecoCompra());
            stmt.setDouble(6, produto.getPrecoVenda());
            stmt.setInt(7, produto.getCategoria().getId());
    
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Erro: Produto com ID " + produto.getId() + " não encontrado.");
            }
        } catch (SQLException e) {
            if ("45000".equals(e.getSQLState())) {
                // Mensagem do alerta do trigger (erro customizado do banco)
                System.err.println("Erro: " + e.getMessage());
            } else {
                System.err.println("Erro ao atualizar o produto: " + e.getMessage());
            }
        }
    }
    
    // deletando produto
    public void deletarProduto(Produto produto) {
        String query = "CALL deletarProduto(?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, produto.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.err.println("Erro: Produto com ID " + produto.getId() + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o produto: " + e.getMessage());
        }
    }

    // registrando entrada de produtos no estoque
    public void registrarEntradaEstoque(Produto produto, int quantidade) {
        String query = "CALL registrar_entrada_estoque(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, produto.getId());
            stmt.setInt(2, quantidade);
            stmt.executeUpdate();
            System.out.println("Entrada de produtos registrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar entrada de estoque: " + e.getMessage());
        }
    }

    // registrando saida de produtos no estoque
    public void registrarSaidaEstoque(Produto produto, int quantidade) {
        String query = "CALL registrar_saida_estoque(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, produto.getId());
            stmt.setInt(2, quantidade);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Saída de estoque registrada com sucesso!");
            } else {
                System.out.println("Erro: Produto com ID " + produto.getId() + " não encontrado ou quantidade inválida.");
            }
        } catch (SQLException e) {
            // Captura o alerta do trigger
            if ("45000".equals(e.getSQLState())) {
                // Mensagem do alerta do trigger (erro customizado do banco)
                System.err.println("Erro: " + e.getMessage());
            } else {
                // Tratamento genérico para outros erros SQL
                System.err.println("Erro ao registrar saída de estoque: " + e.getMessage());
            }
        }
    }

    // consultando produtos com filtros
    public void consultarProdutos(String nome, Integer categoria, Integer quantidade) {
        // A query inicial já está configurada na stored procedure para lidar com nulos
        String query = "{CALL consultar_produtos(?, ?, ?)}"; // Chama a stored procedure
    
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Define os parâmetros dinamicamente conforme os valores passados
            if (nome != null && !nome.isEmpty()) {
                stmt.setString(1, nome); // Define o nome se não for nulo
            } else {
                stmt.setNull(1, java.sql.Types.VARCHAR); // Caso contrário, passa null
            }
    
            if (categoria != null) {
                stmt.setInt(2, categoria); // Define a categoria se não for nulo
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER); // Caso contrário, passa null
            }
    
            if (quantidade != null) {
                stmt.setInt(3, quantidade); // Define a quantidade se não for nulo
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER); // Caso contrário, passa null
            }
    
            // Executa a consulta
            try (ResultSet rs = stmt.executeQuery()) {
                boolean produtoEncontrado = false;  // Flag para verificar se algum produto foi encontrado
    
                // Exibe os resultados
                while (rs.next()) {
                    produtoEncontrado = true;  // Produto encontrado
    
                    int idProduto = rs.getInt("id_produto");
                    String nomeProduto = rs.getString("nome_produto");
                    String descricao = rs.getString("descricao");
                    int qtd = rs.getInt("quantidade");
                    double precoCompra = rs.getDouble("preco_compra");
                    double precoVenda = rs.getDouble("preco_venda");
                    String nomeCategoria = rs.getString("nome_categoria");
    
                    // Exibe as informações do produto
                    System.out.printf(
                            "ID Produto: %d | Nome Produto: %s | Descrição: %s | Quantidade: %d | Preço Compra: %.2f | Preço Venda: %.2f | Categoria: %s\n",
                            idProduto, nomeProduto, descricao, qtd, precoCompra, precoVenda, nomeCategoria);
                    System.out.println("--------------------------------------------");
                }
    
                // Se nenhum produto foi encontrado
                if (!produtoEncontrado) {
                    System.out.println("Nenhum produto encontrado com os filtros especificados.");
                }
    
            }
        } catch (SQLException e) {
            // Tratamento dos erros SQL
            System.err.println("Erro ao consultar produtos: " + e.getMessage());
        }
    }    

    // relatorio de produtos com baixo estoque
    public void relatorioBaixoEstoque(){
        String query = "CALL relatorio_produtos_baixo_estoque()";
        try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) { // Usar executeQuery para retornos
            System.out.println("\n** Produtos com baixo estoque **\n");
            while (rs.next()) {
                System.out.printf("ID: %d | Nome: %s | Quantidade: %d | Categoria: %s\n",
                        rs.getInt("id_produto"),
                        rs.getString("produto_nome"),
                        rs.getInt("quantidade"),
                        rs.getString("categoria_nome"));
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar o relatório de produtos em baixo estoque: " + e.getMessage());
        }
    }
}