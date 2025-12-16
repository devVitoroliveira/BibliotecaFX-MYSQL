package biblioteca.fx.DTO;

import java.time.LocalDate;

public class autorDTO extends Pessoa {

    private String nacionalidade;
    private LocalDate periodoVida;
    private LocalDate periodoFim;

    public autorDTO() {
    }

    public autorDTO(int id_pessoa, String nome) {
        super(id_pessoa, nome);
    }

    public LocalDate getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(LocalDate periodoFim) {
        this.periodoFim = periodoFim;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getPeriodoVida() {
        return periodoVida;
    }

    public void setPeriodoVida(LocalDate periodoVida) {
        this.periodoVida = periodoVida;
    }

}
