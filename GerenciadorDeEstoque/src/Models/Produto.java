package Models;

public class Produto {
    // atributos da tabela
    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private double precoCompra;
    private double precoVenda;
    private Categoria categoria;

    // construtor sem id
    public Produto(String nome, String descricao, int quantidade, double precoCompra, double precoVenda,
            Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
    }

    // constructor com id
    public Produto(int id, String nome, String descricao, int quantidade, double precoCompra, double precoVenda,
            Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
    }

    // gets e sets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setIdCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}