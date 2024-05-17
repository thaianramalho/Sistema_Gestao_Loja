import java.util.ArrayList;
import java.util.List;

public class Venda {
    private List<Produto> produtos;
    private Cliente cliente;
    private FormaPagamento formaPagamento;
    private double valorTotal;

    public Venda() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        valorTotal += produto.getPrecoVenda();
    }

    public double calcularValorTotal() {
        valorTotal = produtos.stream().mapToDouble(Produto::getPrecoVenda).sum();
        return valorTotal;
    }

    public void aplicarDesconto(double percentual) {
        valorTotal -= valorTotal * percentual / 100;
    }

    public void finalizarVenda() {
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
