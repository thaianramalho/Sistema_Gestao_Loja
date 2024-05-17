import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoRemoverFrame extends JFrame {
    private JTextField codigoField;

    public ProdutoRemoverFrame() {
        setTitle("Remover Produto");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        codigoField = new JTextField(10);

        panel.add(new JLabel("Código do Produto:"));
        panel.add(codigoField);

        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        });

        panel.add(removerButton);

        add(panel);
    }

    private void removerProduto() {
        int codigo = Integer.parseInt(codigoField.getText());

        Connection conexao = Db.obterConexao();
        String sqlVerificaVenda = "SELECT COUNT(*) FROM item_venda WHERE produto_id = ?";
        String sqlRemoveProduto = "DELETE FROM produto WHERE id = ?";

        try (PreparedStatement stmtVerifica = conexao.prepareStatement(sqlVerificaVenda)) {
            stmtVerifica.setInt(1, codigo);
            ResultSet rs = stmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Não é possível remover produtos que possuam vendas realizadas.");
            } else {
                try (PreparedStatement stmtRemove = conexao.prepareStatement(sqlRemoveProduto)) {
                    stmtRemove.setInt(1, codigo);
                    int rowsAffected = stmtRemove.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
