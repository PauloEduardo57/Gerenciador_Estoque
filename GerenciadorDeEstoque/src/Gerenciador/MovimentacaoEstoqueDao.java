package Gerenciador;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Gerando relatório de movimentações no estoque
public class MovimentacaoEstoqueDao {

    // conexão com banco de dados
    private Connection conn;

    public MovimentacaoEstoqueDao(Connection conn) {
        this.conn = conn;
    }

    // Relatório de movimentação de estoque
    public void relatorioMovimentacaoEstoque() {
        String query = "CALL relatorio_movimentacao_estoque()"; // Chama a stored procedure

        try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) { // Usar executeQuery para retornos

            System.out.println("\n** Relatório de Movimentação no Estoque **\n");
            while (rs.next()) {
                int idMovimentacao = rs.getInt("id_movimentacao");
                String produtoNome = rs.getString("produto_nome");
                int quantidade = rs.getInt("quantidade");
                String tipo = rs.getString("tipo");
                Timestamp data = rs.getTimestamp("data");

                // Exibe as informações de cada movimentação
                System.out.printf("ID: %d | Produto: %s | Quantidade: %d | Tipo: %s | Data: %s\n",
                        idMovimentacao, produtoNome, quantidade, tipo, data);
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar o relatório de movimentações no estoque: " + e.getMessage());
        }
    }

    // Relatorio de vendas e lucros
    public void relatorioVendasLucro() {
        String query = "CALL relatorio_vendas_lucro()"; // Chama a stored procedure

        try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) { // Usar executeQuery para retornos

            System.out.println("\n** Relatório de Vendas e Lucro **\n");
            while (rs.next()) {
                int idProduto = rs.getInt("id_produto");
                String produtoNome = rs.getString("produto_nome");
                int totalVendido = rs.getInt("total_vendido");
                double precoVenda = rs.getDouble("preco_venda");
                double precoCompra = rs.getDouble("preco_compra");
                double lucroTotal = rs.getDouble("lucro_total");

                // Exibe as informações de vendas e lucro
                System.out.printf(
                        "ID Produto: %d | Produto: %s | Total Vendido: %d | Preço de Venda: %.2f | Preço de Compra: %.2f | Lucro Total: %.2f\n",
                        idProduto, produtoNome, totalVendido, precoVenda, precoCompra, lucroTotal);
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar o relatório de Vendas e Lucro: " + e.getMessage());
        }
    }
}