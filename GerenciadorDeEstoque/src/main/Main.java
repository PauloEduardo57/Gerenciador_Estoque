package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Database.Conexao;
import Gerenciador.CategoriaDao;
import Gerenciador.MovimentacaoEstoqueDao;
import Gerenciador.ProdutoDao;
import Models.Categoria;
import Models.Produto;

public class Main {

    // Scanner para entrada de dados do usuário
    private static final Scanner scanner = new Scanner(System.in);
    
    // Conexão com o banco de dados
    private static Connection conn = Conexao.conectar();

    public static void main(String[] args) {
        int opcao;

        // Loop do menu principal
        do {
            exibirMenu(); // Exibe as opções do menu
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha após o número

            // Switch para tratar cada opção do menu
            switch (opcao) {
                case 1:
                    cadastrarCategoria();
                    break;
                case 2:
                    atualizarCategoria();
                    break;
                case 3:
                    deletarCategoria();
                    break;
                case 4:
                    cadastrarProduto();
                    break;
                case 5:
                    atualizarProduto();
                    break;
                case 6:
                    deletarProduto();
                    break;
                case 7:
                    consultarCategorias();
                    break;
                case 8:
                    consultarProdutos();
                    break;
                case 9:
                    registrarEntradaEstoque();
                    break;
                case 10:
                    registrarSaidaEstoque();
                    break;
                case 11:
                    relatorioBaixoEstoque();
                    break;
                case 12:
                    relatorioMovimentacaoEstoque();
                    break;
                case 13:
                    relatorioVendasLucro();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    fecharConexao(); // Fecha a conexão ao encerrar o programa
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0); // Continua até que o usuário escolha sair
    }

    // Exibe as opções disponíveis no menu principal
    private static void exibirMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Cadastrar Categoria");
        System.out.println("2. Atualizar Categoria");
        System.out.println("3. Deletar Categoria");
        System.out.println("4. Cadastrar Produto");
        System.out.println("5. Atualizar Produto");
        System.out.println("6. Deletar Produto");
        System.out.println("7. Consultar Categorias");
        System.out.println("8. Consultar Produtos");
        System.out.println("9. Registrar Entrada no Estoque");
        System.out.println("10. Registrar Saída do Estoque");
        System.out.println("11. Relatório de Baixo Estoque");
        System.out.println("12. Relatório de Movimentações de Estoque");
        System.out.println("13. Relatório de Vendas e Lucros");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // funções do crud
    
    // função de categoria
    // cadastrando uma nova categoria
    private static void cadastrarCategoria() {
        System.out.println("\n** Cadastrar nova Categoria **\n");
        System.out.print("Informe o nome da categoria: ");
        String nome = scanner.nextLine();
        System.out.print("Informe a descrição da categoria: ");
        String descricao = scanner.nextLine();

        // passando dados para a tabela categoria
        Categoria categoria = new Categoria(nome, descricao);

        // // Chama o método no DAO
        CategoriaDao categoriaDao = new CategoriaDao(conn);
        categoriaDao.cadastrarCategoria(categoria);
    }

    // atualizando categoria
    private static void atualizarCategoria() {
        System.out.println("\n** Atualizar Categoria **\n");
        System.out.print("Informe o ID da categoria a ser atualizada: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        System.out.print("Novo nome da categoria: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descrição da categoria: ");
        String descricao = scanner.nextLine();

        // passando novos dados para a categoria
        Categoria categoria = new Categoria(id, nome, descricao);

        // Chama o método no DAO
        CategoriaDao categoriaDao = new CategoriaDao(conn);
        categoriaDao.atualizarCategoria(categoria);
    }

    // deletando categorias
    private static void deletarCategoria() {
        System.out.println("\n** Deletar Categoria **\n");
        System.out.print("Informe o ID da categoria a ser deletada: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        // passando o id da categoria
        Categoria categoria = new Categoria(id, null, null);
        CategoriaDao categoriaDao = new CategoriaDao(conn);

        // // Chama o método no DAO
        categoriaDao.deletarCategoria(categoria);
    }

    // consultando categorias
    private static void consultarCategorias() {
        // Chama o método no DAO
        CategoriaDao categoriaDao = new CategoriaDao(conn);
        categoriaDao.consultarCategoria();
    }

    // Funções CRUD para Produto
    // cadastrando produtos
    private static void cadastrarProduto() {
        System.out.println("\n** Cadastrar Produto **\n");
        System.out.print("Informe o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Informe a descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Informe a quantidade do produto: ");
        int quantidade = scanner.nextInt();
        System.out.print("Informe o preço de compra do produto: ");
        double precoCompra = scanner.nextDouble();
        System.out.print("Informe o preço de venda do produto: ");
        double precoVenda = scanner.nextDouble();
        scanner.nextLine(); // Consumir a linha
        System.out.print("Informe o ID da categoria do produto: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        // passando dados para a tabela de produtos
        Categoria categoria = new Categoria(categoriaId, null, null);
        Produto produto = new Produto(nome, descricao, quantidade, precoCompra, precoVenda, categoria);

        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.cadastrarProduto(produto);
    }

    // atualizando produtos pelo id
    private static void atualizarProduto() {
        System.out.println("\n** Atualizar Produto **\n");
        System.out.print("Informe o ID do produto a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        System.out.print("Novo nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Nova descrição do produto: ");
        String descricao = scanner.nextLine();

        System.out.print("Nova quantidade do produto: ");
        int quantidade = scanner.nextInt();

        System.out.print("Novo preço de compra do produto: ");
        double precoCompra = scanner.nextDouble();

        System.out.print("Novo preço de venda do produto: ");
        double precoVenda = scanner.nextDouble();
        scanner.nextLine(); // Consumir a linha

        System.out.print("Informe o novo ID da categoria do produto: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        // passando os novos dados para as tabelas
        Categoria categoria = new Categoria(categoriaId, null, null);
        Produto produto = new Produto(id, nome, descricao, quantidade, precoCompra, precoVenda, categoria);

        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.atualizarProduto(produto);
    }

    // deletando produtos
    private static void deletarProduto() {
        System.out.println("\n** Deletar Produto **\n");
        System.out.print("Informe o ID do produto a ser deletado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha

        // passando o id do produto
        Produto produto = new Produto(id, null, null, 0, 0, 0, null);

        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.deletarProduto(produto);
    }

    // Registrando saída de produtos no estoque
    private static void registrarEntradaEstoque() {
        System.out.println("\n** Registrar entrada de produtos no estoque **\n");
        System.out.print("Informe o ID do produto: ");
        int produtoId = scanner.nextInt();

        System.out.print("Informe a quantidade de entrada: ");
        int quantidade = scanner.nextInt();

        // passando o id do produto
        Produto produto = new Produto(produtoId, null, null, 0, 0, 0, null);

        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.registrarEntradaEstoque(produto, quantidade);
    }

    // Registrando saída de produtos no estoque
    private static void registrarSaidaEstoque() {
        System.out.println("\n** Registrar saída de produtos no estoque **\n");
        System.out.print("Informe o ID do produto: ");
        int produtoId = scanner.nextInt();

        System.out.print("Informe a quantidade de saida: ");
        int quantidade = scanner.nextInt();

        // passando o id do produto
        Produto produto = new Produto(produtoId, null, null, 0, 0, 0, null);

        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.registrarSaidaEstoque(produto, quantidade);
    }

    // Função de consultar produtos com filtros
    private static void consultarProdutos() {
        System.out.println("\n** Consultar Produtos **\n");
    
        // Solicitar os filtros
        System.out.print("Informe o nome do produto (pressione Enter para ignorar): ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            nome = null; // Define como null se o usuário pressionar Enter
        }
    
        System.out.print("Informe o ID da categoria (pressione Enter para ignorar): ");
        String categoriaInput = scanner.nextLine().trim();
        Integer categoria = null;
        if (!categoriaInput.isEmpty()) {
            try {
                categoria = Integer.parseInt(categoriaInput); // Converte para Integer se houver valor
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido para categoria. Ignorando filtro...");
            }
        }
    
        System.out.print("Informe a quantidade mínima (pressione Enter para ignorar): ");
        String quantidadeInput = scanner.nextLine().trim();
        System.out.println(); // espaço
        Integer quantidade = null;
        if (!quantidadeInput.isEmpty()) {
            try {
                quantidade = Integer.parseInt(quantidadeInput); // Converte para Integer se houver valor
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido para quantidade. Ignorando filtro...");
            }
        }
    
        // Chama o método no DAO 
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.consultarProdutos(nome, categoria, quantidade);
    }

    // relatórios 
    // função de relatório de produtos em baixo estoque
    private static void relatorioBaixoEstoque() {
        // Chama o método no DAO
        ProdutoDao produtoDao = new ProdutoDao(conn);
        produtoDao.relatorioBaixoEstoque();
    }

    // função de relatório de Movimentações de estoque
    private static void relatorioMovimentacaoEstoque() {
        // Chama o método no DAO
        MovimentacaoEstoqueDao movimentacaoDao = new MovimentacaoEstoqueDao(conn);
        movimentacaoDao.relatorioMovimentacaoEstoque();
    }

    // função de relatório de vendas e lucro
    private static void relatorioVendasLucro() {
        // Chama o método no DAO 
        MovimentacaoEstoqueDao movimentacaoDao = new MovimentacaoEstoqueDao(conn);
        movimentacaoDao.relatorioVendasLucro();
    }

    // Função para fechar conexão com banco de dados
    private static void fecharConexao() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco: " + e.getMessage());
            }
        }
    }
}