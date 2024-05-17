public class ProdutoEletronico extends Produto {
    private String marca;
    private String modelo;

    @Override
    public double calcularPrecoVenda() {
        return this.precoVenda;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}

public class ProdutoAlimenticio extends Produto {
    private String dataValidade;

    @Override
    public double calcularPrecoVenda() {
        return this.precoVenda;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
}

public class ProdutoLimpeza extends Produto {
    private boolean toxico;

    @Override
    public double calcularPrecoVenda() {
        return this.precoVenda;
    }

    public boolean isToxico() {
        return toxico;
    }

    public void setToxico(boolean toxico) {
        this.toxico = toxico;
    }
}
