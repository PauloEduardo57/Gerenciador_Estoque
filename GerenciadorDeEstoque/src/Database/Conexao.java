package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // dados usados para fazer a conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/gerenciador_estoque"; // link padrão do mysql
    private static final String USUARIO = "root"; // usuário padrão do mysql
    private static final String SENHA = "SuaSenha"; // insira sua senha do mysql

    // função para criar conexão com o banco
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }

    // testando conexão
    public static void main(String[] args) {
        Connection conexao = conectar();
        if (conexao != null) {
            System.out.println("Conexão bem-sucedida!");
        }
    }
}