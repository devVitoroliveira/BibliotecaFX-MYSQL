package biblioteca.fx.DTO;

public abstract class Pessoa {

    private int id_pessoa;
    private String nome;

    public Pessoa() {
    }

    public Pessoa(int id_pessoa, String nome) {
        this.id_pessoa = id_pessoa;
        this.nome = nome;
    }

    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
