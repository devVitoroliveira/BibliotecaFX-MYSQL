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
        if (periodoFim == null || periodoFim.isEqual(null)) {
            throw new IllegalArgumentException("A data de falecimento não pode ser nula.");
        }
        if (periodoFim.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de falecimento não pode ser futura.");
        }
        this.periodoFim = periodoFim;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        if (nacionalidade == null || nacionalidade.isEmpty() || !nacionalidade.matches("[a-zA-Z ]+"))
            throw new IllegalArgumentException("A nacionalidade deve conter apenas letras e espaços.");
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getPeriodoVida() {
        return periodoVida;
    }

    public void setPeriodoVida(LocalDate periodoVida) {
        if (periodoVida == null || periodoVida.isEqual(null)) {
            throw new IllegalArgumentException("A data de nascimento não pode ser nula.");
        }
        if (periodoVida.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser futura.");
        }
        if (periodoVida.isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser hoje.");
        }
        this.periodoVida = periodoVida;
    }

}
