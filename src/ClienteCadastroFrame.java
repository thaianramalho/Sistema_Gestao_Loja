import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteCadastroFrame extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField dataNascimentoField;
    private JTextField emailField;

    public ClienteCadastroFrame() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nomeField = new JTextField(20);
        cpfField = new JTextField(11);
        dataNascimentoField = new JTextField(10);
        emailField = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Data de Nascimento:"));
        panel.add(dataNascimentoField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        panel.add(cadastrarButton);

        add(panel);
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String email = emailField.getText();

        Connection conexao = Db.obterConexao();
        String sql = "INSERT INTO cliente (nome, cpf, data_nascimento, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, dataNascimento);
            stmt.setString(4, email);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
