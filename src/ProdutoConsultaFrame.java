import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoConsultaFrame extends JFrame {
    private JTextField codigoField;
    private JTextArea resultadoArea;

    public ProdutoConsultaFrame() {
        setTitle("Consulta de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        codigoField = new JTextField(10);
        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);

        panel.add(new JLabel("Código do Produto:"));
        panel.add(codigoField);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarProduto();
            }
        });

        panel.add(consultarButton);
        panel.add(new JScrollPane(resultadoArea));

        add(panel);
    }

    private void consultarProduto() {
        int codigo = Integer.parseInt(codigoField.getText());

        Connection conexao = Db.obterConexao();
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double precoCusto = rs.getDouble("preco_custo");
                double precoVenda = rs.getDouble("preco_venda");
                int quantidadeEstoque = rs.getInt("quantidade_estoque");
                String categoria = rs.getInt("categoria_id") == 1 ? "Eletrônico" : rs.getInt("categoria_id") == 2 ? "Alimentício" : "Limpeza";
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String dataValidade = rs.getString("data_validade");
                boolean toxico = rs.getBoolean("toxico");

                resultadoArea.setText("Nome: " + nome + "\n"
                                    + "Descrição: " + descricao + "\n"
                                    + "Preço de Custo: " + precoCusto + "\n"
                                    + "Preço de Venda: " + precoVenda + "\n"
                                    + "Quantidade em Estoque: " + quantidadeEstoque + "\n"
                                    + "Categoria: " + categoria + "\n"
                                    + "Marca: " + marca + "\n"
                                    + "Modelo: " + modelo + "\n"
                                    + "Data de Validade: " + dataValidade + "\n"
                                    + "Tóxico: " + (toxico ? "Sim" : "Não"));
            } else {
                resultadoArea.setText("Produto não encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar produto: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
