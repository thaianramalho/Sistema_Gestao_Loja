public class Cheque extends FormaPagamento {
    private String numeroCheque;
    private String banco;

    public Cheque() {
        this.tipo = "Cheque";
    }

    @Override
    public void processarPagamento() {

    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
