public class Cartao extends FormaPagamento {
    private String numeroCartao;
    private String validade;
    private String cvv;

    public Cartao() {
        this.tipo = "Cartão";
    }

    @Override
    public void processarPagamento() {

    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}