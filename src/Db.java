
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final String URL = "jdbc:mysql://localhost:3306/trabalho2etapa";
    private static final String USUARIO = "root";
    private static final String SENHA = "Abc@1234";

    public static Connection obterConexao() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("ConexÃ£o com o banco de dados estabelecida.");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC nÃ£o encontrado: " + e.getMessage());
        }
        return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("ConexÃ£o com o banco de dados fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexÃ£o com o banco de dados: " + e.getMessage());
            }
        }
    }
}