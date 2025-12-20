package biblioteca.fx.DTO;

import java.time.LocalDate;

public class emprestimoDTO {

    private int id_emprestimo;
    private int id_cliente;
    private int id_livro;
    private String nomeCliente;
    private String nomeLivro;
    private LocalDate data_emprestimo;
    private LocalDate data_devolucao;

    public emprestimoDTO() {
    }

    public emprestimoDTO(int id_emprestimo, int id_cliente, int id_livro, String nomeCliente, String nomeLivro,
            LocalDate data_emprestimo,
            LocalDate data_devolucao) {
        this.id_emprestimo = id_emprestimo;
        this.id_cliente = id_cliente;
        this.id_livro = id_livro;
        this.nomeCliente = nomeCliente;
        this.nomeLivro = nomeLivro;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
    }

    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public LocalDate getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(LocalDate data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public LocalDate getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(LocalDate data_devolucao) {
        this.data_devolucao = data_devolucao;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

}
