import java.util.ArrayList;
import java.util.List;

public class GerenciadorEstoque {
    private List<Produto> produtos;

    public GerenciadorEstoque() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(int codigo) {
        produtos.removeIf(produto -> produto.getCodigo() == codigo);
    }

    public Produto buscarProdutoPorCodigo(int codigo) {
        return produtos.stream().filter(p -> p.getCodigo() == codigo).findFirst().orElse(null);
    }

    public List<Produto> buscarProdutoPorNome(String nome) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    public List<Produto> buscarProdutoPorCategoria(Categoria categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria().getNomeCategoria().equalsIgnoreCase(categoria.getNomeCategoria())) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    public void atualizarProduto(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo() == produto.getCodigo()) {
                produtos.set(i, produto);
                break;
           
