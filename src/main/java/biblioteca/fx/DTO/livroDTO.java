package biblioteca.fx.DTO;

import java.time.Year;

public class livroDTO {
    private int idLivro;
    private int idautor;
    private String nomeLivro;
    private String nomeAutor;
    private Year ano;
    private String genero;
    private String isbn;

    public livroDTO() {
    }

    public livroDTO(int idLivro, int idautor, String nomeLivro, String nomeAutor, Year ano, String genero,
            String isbn) {
        this.idLivro = idLivro;
        this.idautor = idautor;
        this.nomeLivro = nomeLivro;
        this.nomeAutor = nomeAutor;
        this.ano = ano;
        this.genero = genero;
        this.isbn = isbn;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdautor() {
        return idautor;
    }

    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        if (nomeLivro == null || nomeLivro.isEmpty()) {
            throw new IllegalArgumentException("O nome do livro não pode ser vazio.");
        }
        if (!nomeLivro.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("O nome do livro deve conter apenas letras e espaços.");
        }
        this.nomeLivro = nomeLivro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Year getAno() {
        return ano;
    }

    public void setAno(Year ano) {
        if (ano.isAfter(Year.now())) {
            throw new IllegalArgumentException("O ano do livro não pode ser futura.");
        }
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("O gênero do livro não pode ser vazio.");
        }
        this.genero = genero;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("O ISBN não pode ser vazio.");
        }
        String cleanIsbn = isbn.replace("-", "").replace(" ", "");
        if (cleanIsbn.length() != 10 && cleanIsbn.length() != 13) {
            throw new IllegalArgumentException("O ISBN deve ter 10 ou 13 caracteres válidos.");
        }
        if (!cleanIsbn.matches("[0-9]{9}[0-9X]|[0-9]{13}")) {
            throw new IllegalArgumentException("O ISBN contém caracteres inválidos.");
        }
        if (!isValidISBN(cleanIsbn)) {
            throw new IllegalArgumentException("O ISBN informado é inválido.");
        }
        this.isbn = isbn;
    }

    private boolean isValidISBN(String isbn) {
        int s = 0;
        if (isbn.length() == 10) {
            for (int i = 0; i < 10; i++) {
                char c = isbn.charAt(i);
                s += ((c == 'X') ? 10 : (c - '0')) * (10 - i);
            }
            return s % 11 == 0;
        } else {
            for (int i = 0; i < 13; i++) {
                int d = isbn.charAt(i) - '0';
                s += (i % 2 == 0) ? d : d * 3;
            }
            return s % 10 == 0;
        }
    }
}
