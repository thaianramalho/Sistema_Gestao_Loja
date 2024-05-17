import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoCadastroFrame extends JFrame {
    private JTextField nomeField;
    private JTextArea descricaoField;
    private JTextField precoCustoField;
    private JTextField precoVendaField;
    private JTextField quantidadeEstoqueField;
    private JComboBox<String> categoriaBox;
    private JTextField marcaField;
    private JTextField modeloField;
    private JTextField dataValidadeField;
    private JCheckBox toxicoBox;

    public ProdutoCadastroFrame() {
        setTitle("Cadastro de Produto");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        nomeField = new JTextField(20);
        descricaoField = new JTextArea(5, 20);
        precoCustoField = new JTextField(10);
        precoVendaField = new JTextField(10);
        quantidadeEstoqueField = new JTextField(5);
        categoriaBox = new JComboBox<>(new String[]{"Eletrônico", "Alimentício", "Limpeza"});
        marcaField = new JTextField(20);
        modeloField = new JTextField(20);
        dataValidadeField = new JTextField(10);
        toxicoBox = new JCheckBox("Tóxico");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Descrição:"));
        panel.add(new JScrollPane(descricaoField));
        panel.add(new JLabel("Preço de Custo:"));
        panel.add(precoCustoField);
        panel.add(new JLabel("Preço de Venda:"));
        panel.add(precoVendaField);
        panel.add(new JLabel("Quantidade em Estoque:"));
        panel.add(quantidadeEstoqueField);
        panel.add(new JLabel("Categoria:"));
        panel.add(categoriaBox);
        panel.add(new JLabel("Marca:"));
        panel.add(marcaField);
        panel.add(new JLabel("Modelo:"));
        panel.add(modeloField);
        panel.add(new JLabel("Data de Validade:"));
        panel.add(dataValidadeField);
        panel.add(new JLabel("Tóxico:"));
        panel.add(toxicoBox);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });

        panel.add(cadastrarButton);

        add(panel);
    }

    private void cadastrarProduto() {
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        double precoCusto = Double.parseDouble(precoCustoField.getText());
        double precoVenda = Double.parseDouble(precoVendaField.getText());
        int quantidadeEstoque = Integer.parseInt(quantidadeEstoqueField.getText());
        String categoria = (String) categoriaBox.getSelectedItem();
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String dataValidade = dataValidadeField.getText();
        boolean toxico = toxicoBox.isSelected();

        Connection conexao = Db.obterConexao();
        String sql = "INSERT INTO produto (nome, descricao, preco_custo, preco_venda, quantidade_estoque, categoria_id, marca, modelo, data_validade, toxico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setDouble(3, precoCusto);
            stmt.setDouble(4, precoVenda);
            stmt.setInt(5, quantidadeEstoque);
            stmt.setInt(6, categoria.equals("Eletrônico") ? 1 : categoria.equals("Alimentício") ? 2 : 3);
            stmt.setString(7, marca);
            stmt.setString(8, modelo);
            stmt.setString(9, dataValidade);
            stmt.setBoolean(10, toxico);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
