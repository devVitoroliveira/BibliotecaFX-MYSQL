package biblioteca.fx.DTO;

public class clienteDTO extends Pessoa {
    private String telefone, email;

    public clienteDTO() {
    }

    public clienteDTO(int id_pessoa, String nome, String telefone, String email) {
        super(id_pessoa, nome);
        this.telefone = telefone;
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
