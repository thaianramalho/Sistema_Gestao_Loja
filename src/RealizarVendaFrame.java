import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RealizarVendaFrame extends JFrame {
    private JTextField clienteIdField;
    private JTextField produtoIdField;
    private JTextField quantidadeField;

    public RealizarVendaFrame() {
        setTitle("Realizar Venda");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        clienteIdField = new JTextField(10);
        produtoIdField = new JTextField(10);
        quantidadeField = new JTextField(5);

        panel.add(new JLabel("ID do Cliente:"));
        panel.add(clienteIdField);
        panel.add(new JLabel("ID do Produto:"));
        panel.add(produtoIdField);
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidadeField);

        JButton realizarVendaButton = new JButton("Realizar Venda");
        realizarVendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarVenda();
            }
        });

        panel.add(realizarVendaButton);

        add(panel);
    }

    private void realizarVenda() {
        int clienteId = Integer.parseInt(clienteIdField.getText());
        int produtoId = Integer.parseInt(produtoIdField.getText());
        int quantidade = Integer.parseInt(quantidadeField.getText());
        

        Connection conexao = Db.obterConexao();
        String sqlVenda = "INSERT INTO venda (cliente_id, data_venda, valor_total) VALUES (?, ?, ?)";
        String sqlItemVenda = "INSERT INTO item_venda (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        String sqlAtualizaEstoque = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
        String sqlPrecoVenda = "SELECT preco_venda FROM produto WHERE id = ?";

        try {
            conexao.setAutoCommit(false);

            double precoVenda;
            try (PreparedStatement stmtPrecoVenda = conexao.prepareStatement(sqlPrecoVenda)) {
                stmtPrecoVenda.setInt(1, produtoId);
                ResultSet rs = stmtPrecoVenda.executeQuery();
                if (rs.next()) {
                    precoVenda = rs.getDouble("preco_venda");
                } else {
                    throw new SQLException("Produto não encontrado.");
                }
            }

            double valorTotal = precoVenda * quantidade;

            try (PreparedStatement stmtVenda = conexao.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtVenda.setInt(1, clienteId);
                stmtVenda.setDate(2, new java.sql.Date(new Date().getTime()));
                stmtVenda.setDouble(3, valorTotal);
                stmtVenda.executeUpdate();

                int vendaId;
                try (ResultSet generatedKeys = stmtVenda.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vendaId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Falha ao obter o ID da venda.");
                    }
                }

                try (PreparedStatement stmtItemVenda = conexao.prepareStatement(sqlItemVenda)) {
                    stmtItemVenda.setInt(1, vendaId);
                    stmtItemVenda.setInt(2, produtoId);
                    stmtItemVenda.setInt(3, quantidade);
                    stmtItemVenda.setDouble(4, precoVenda);
                    stmtItemVenda.executeUpdate();
                }

                try (PreparedStatement stmtAtualizaEstoque = conexao.prepareStatement(sqlAtualizaEstoque)) {
                    stmtAtualizaEstoque.setInt(1, quantidade);
                    stmtAtualizaEstoque.setInt(2, produtoId);
                    stmtAtualizaEstoque.executeUpdate();
                }

                conexao.commit();
                JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
            }
        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Erro ao realizar venda: " + e.getMessage());
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
            Db.fecharConexao(conexao);
        }
    }

}
