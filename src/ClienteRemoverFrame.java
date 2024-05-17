import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRemoverFrame extends JFrame {
    private JTextField idField;

    public ClienteRemoverFrame() {
        setTitle("Remover Cliente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(10);

        panel.add(new JLabel("ID do Cliente:"));
        panel.add(idField);

        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente();
            }
        });

        panel.add(removerButton);

        add(panel);
    }

    private void removerCliente() {
        int id = Integer.parseInt(idField.getText());

        Connection conexao = Db.obterConexao();
        String sqlVerificaVenda = "SELECT COUNT(*) FROM venda WHERE cliente_id = ?";
        String sqlRemoveCliente = "DELETE FROM cliente WHERE id = ?";

        try (PreparedStatement stmtVerifica = conexao.prepareStatement(sqlVerificaVenda)) {
            stmtVerifica.setInt(1, id);
            ResultSet rs = stmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Não é possível remover clientes que possuam vendas realizadas.");
            } else {
                try (PreparedStatement stmtRemove = conexao.prepareStatement(sqlRemoveCliente)) {
                    stmtRemove.setInt(1, id);
                    int rowsAffected = stmtRemove.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover cliente: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
