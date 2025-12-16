package biblioteca.fx.DTO;

public class livroDTO {
    private int idLivro;
    private int idautor;
    private String nomeLivro;
    private String nomeAutor;
    private String ano;
    private String genero;
    private String ibsn;

    public livroDTO() {
    }

    public livroDTO(int idLivro, int idautor, String nomeLivro, String nomeAutor, String ano, String genero,
            String ibsn) {
        this.idLivro = idLivro;
        this.idautor = idautor;
        this.nomeLivro = nomeLivro;
        this.nomeAutor = nomeAutor;
        this.ano = ano;
        this.genero = genero;
        this.ibsn = ibsn;
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
        this.nomeLivro = nomeLivro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIbsn() {
        return ibsn;
    }

    public void setIbsn(String ibsn) {
        this.ibsn = ibsn;
    }

}
