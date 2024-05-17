import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricoVendaFrame extends JFrame {
    private JTable tabelaHistorico;

    public HistoricoVendaFrame() {
        setTitle("Histórico de Vendas");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        tabelaHistorico = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);

        panel.add(scrollPane, BorderLayout.CENTER);

        carregarHistoricoVendas();

        add(panel);
    }

    private void carregarHistoricoVendas() {
        Connection conexao = Db.obterConexao();
        String sql = "SELECT id, cliente_id, data_venda, valor_total, metodo_pagamento FROM venda";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Cliente ID");
            model.addColumn("Data Venda");
            model.addColumn("Valor Total");
            model.addColumn("Método de pagamento");
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("cliente_id");
                row[2] = rs.getDate("data_venda");
                row[3] = rs.getDouble("valor_total");
                row[4] = rs.getString("metodo_pagamento");
                model.addRow(row);
            }
            tabelaHistorico.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o histórico de vendas: " + e.getMessage());
        } finally {
            Db.fecharConexao(conexao);
        }
    }
}
