package biblioteca.fx.DTO;

public class autorDTO extends Pessoa {

    private String nacionalidade;
    private String periodoVida;
    private String periodoFim;

    public autorDTO() {
    }

    public autorDTO(int id_pessoa, String nome) {
        super(id_pessoa, nome);
    }

    public String getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(String periodoFim) {
        this.periodoFim = periodoFim;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPeriodoVida() {
        return periodoVida;
    }

    public void setPeriodoVida(String periodoVida) {
        this.periodoVida = periodoVida;
    }

}
