package biblioteca.fx.DTO;

public class funcionarioDTO extends Pessoa {
    private String telefone, email;
    private double salario;
    private int id_cargo;
    private String cargo;

    public funcionarioDTO() {
    }

    public funcionarioDTO(int id_pessoa, String nome, String telefone, String email) {
        super(id_pessoa, nome);
        this.telefone = telefone;
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone.isEmpty() || telefone == null) {
            throw new IllegalArgumentException("O telefone não pode estar vazio");
        }
        if (telefone.length() != 11) {
            throw new IllegalArgumentException("O telefone deve ter 11 dígitos");
        }
        if (!telefone.matches("[0-9]+")) {
            throw new IllegalArgumentException("O telefone deve conter apenas números");
        }
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.isEmpty() || email == null) {
            throw new IllegalArgumentException("O email não pode estar vazio");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("O email informado é inválido");
        }
        this.email = email;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 1520.00) {
            throw new IllegalArgumentException("O salário não pode ser menor ao salário mínimo");
        }
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
