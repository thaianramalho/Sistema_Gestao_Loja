import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteConsultaFrame extends JFrame {
    private JTextField idField;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField dataNascimentoField;
    private JTextField emailField;
    private JTextArea resultadoArea;

    public ClienteConsultaFrame() {
        setTitle("Consulta de Clientes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(10);
        nomeField = new JTextField(20);
        cpfField = new JTextField(11);
        dataNascimentoField = new JTextField(10);
        emailField = new JTextField(20);
        resultadoArea = new JTextArea(10, 40);
        resultadoArea.setEditable(false);

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

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarCliente();
            }
        });

        panel.add(consultarButton);
        panel.add(new JScrollPane(resultadoArea));

        add(panel);
    }

    private void consultarCliente() {
        String id = idField.getText();
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String email = emailField.getText();

        Connection conexao = Db.obterConexao();
        StringBuilder sql = new StringBuilder("SELECT * FROM cliente WHERE 1=1");
        if (!id.isEmpty()) {
            sql.append(" AND id = ").append(id);
        }
        if (!nome.isEmpty()) {
            sql.append(" AND nome LIKE '%").append(nome).append("%'");
        }
        if (!cpf.isEmpty()) {
            sql.append(" AND cpf LIKE '%").append(cpf).append("%'");
        }
        if (!dataNascimento.isEmpty()) {
            sql.append(" AND data_nascimento LIKE '%").append(dataNascimento).append("%'");
        }
        if (!email.isEmpty()) {
            sql.append(" AND email LIKE '%").append(email).append("%'");
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql.toString())) {
            ResultSet rs = stmt.executeQuery();
            resultadoArea.setText("");
            while (rs.next()) {
                resultadoArea.append("ID: " + rs.getInt("id") + "\n");
                resultadoArea.append("Nome: " + rs.getString("nome") + "\n");
                resultadoArea.append("CPF: " + rs.getString("cpf") + "\n");
                resultadoArea.append("Data de Nascimento: " + rs.getString("data_nascimento") + "\n");
                resultadoArea.append("Email: " + rs.getString("email") + "\n");
                resultadoArea.append("\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar clientes: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
