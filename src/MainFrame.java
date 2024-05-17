import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Sistema de Gestão");
        setSize(695, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuProduto = new JMenu("Produto");
        JMenuItem itemCadastrarProduto = new JMenuItem("Cadastrar");
        JMenuItem itemConsultarProduto = new JMenuItem("Consultar");
        JMenuItem itemAtualizarProduto = new JMenuItem("Atualizar");
        JMenuItem itemRemoverProduto = new JMenuItem("Remover");

        itemCadastrarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoCadastroFrame().setVisible(true);
            }
        });
        itemConsultarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoConsultaFrame().setVisible(true);
            }
        });
        itemAtualizarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoAtualizarFrame().setVisible(true);
            }
        });
        itemRemoverProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoRemoverFrame().setVisible(true);
            }
        });

        menuProduto.add(itemCadastrarProduto);
        menuProduto.add(itemConsultarProduto);
        menuProduto.add(itemAtualizarProduto);
        menuProduto.add(itemRemoverProduto);
        menuBar.add(menuProduto);

        JMenu menuCliente = new JMenu("Cliente");
        JMenuItem itemCadastrarCliente = new JMenuItem("Cadastrar");
        JMenuItem itemConsultarCliente = new JMenuItem("Consultar");
        JMenuItem itemAtualizarCliente = new JMenuItem("Atualizar");
        JMenuItem itemRemoverCliente = new JMenuItem("Remover");

        itemCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteCadastroFrame().setVisible(true);
            }
        });
        itemConsultarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteConsultaFrame().setVisible(true);
            }
        });
        itemAtualizarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteAtualizarFrame().setVisible(true);
            }
        });
        itemRemoverCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteRemoverFrame().setVisible(true);
            }
        });

        menuCliente.add(itemCadastrarCliente);
        menuCliente.add(itemConsultarCliente);
        menuCliente.add(itemAtualizarCliente);
        menuCliente.add(itemRemoverCliente);
        menuBar.add(menuCliente);

        JMenu menuVenda = new JMenu("Venda");
        JMenuItem itemRealizarVenda = new JMenuItem("Realizar Venda");
        JMenuItem itemHistoricoVenda = new JMenuItem("Histórico de Vendas");

        itemRealizarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendaFrame().setVisible(true);
            }
        });
        itemHistoricoVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistoricoVendaFrame().setVisible(true);
            }
        });

        menuVenda.add(itemRealizarVenda);
        menuVenda.add(itemHistoricoVenda);
        menuBar.add(menuVenda);

        setJMenuBar(menuBar);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\repositorios\\TrabalhoPOO_etapa2_ThaianRamalho\\img\\Gestão-de-Qualidades.jpg"));
        getContentPane().add(lblNewLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
