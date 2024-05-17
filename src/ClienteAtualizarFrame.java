import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteAtualizarFrame extends JFrame {
    private JTextField idField;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField dataNascimentoField;
    private JTextField emailField;

    public ClienteAtualizarFrame() {
        setTitle("Atualizar Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(10);
        nomeField = new JTextField(20);
        cpfField = new JTextField(11);
        dataNascimentoField = new JTextField(10);
        emailField = new JTextField(20);

        panel.add(new JLabel("ID do Cliente:"));
        panel.add(idField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Data de Nascimento:"));
        panel.add(dataNascimentoField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });

        panel.add(buscarButton);
        panel.add(atualizarButton);

        add(panel);
    }

    private void buscarCliente() {
        int id = Integer.parseInt(idField.getText());

        Connection conexao = Db.obterConexao();
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomeField.setText(rs.getString("nome"));
                cpfField.setText(rs.getString("cpf"));
                dataNascimentoField.setText(rs.getString("data_nascimento"));
                emailField.setText(rs.getString("email"));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }

    private void atualizarCliente() {
        int id = Integer.parseInt(idField.getText());
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String email = emailField.getText();

        Connection conexao = Db.obterConexao();
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, data_nascimento = ?, email = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, dataNascimento);
            stmt.setString(4, email);
            stmt.setInt(5, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
