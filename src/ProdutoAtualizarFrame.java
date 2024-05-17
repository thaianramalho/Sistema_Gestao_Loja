import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoAtualizarFrame extends JFrame {
    private JTextField idField;
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

    public ProdutoAtualizarFrame() {
        setTitle("Atualizar Produto");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(10);
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

        panel.add(new JLabel("ID do Produto:"));
        panel.add(idField);
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

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarProduto();
            }
        });

        panel.add(atualizarButton);

        add(panel);
    }

    private void atualizarProduto() {
        int id = Integer.parseInt(idField.getText());
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
        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco_custo = ?, preco_venda = ?, quantidade_estoque = ?, categoria_id = ?, marca = ?, modelo = ?, data_validade = ?, toxico = ? WHERE id = ?";

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
            stmt.setInt(11, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
