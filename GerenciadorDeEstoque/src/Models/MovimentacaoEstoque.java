package Models;

import java.sql.Timestamp; // Importando Timestamp

public class MovimentacaoEstoque {
    private int idMovimentacao;
    private Produto produto;
    private int quantidade;
    private String tipo; // "ENTRADA" ou "SAIDA"
    private Timestamp data;

    // Construtor sem id
    public MovimentacaoEstoque(Produto produto, int quantidade, String tipo) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = new Timestamp(System.currentTimeMillis());
    }

    // gets e sets

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }
}