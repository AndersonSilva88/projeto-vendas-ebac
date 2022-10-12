package main.aplicacao.domain;

import main.DB.ColunaTabela;
import main.DB.Tabela;
import main.DB.TipoChave;
import main.aplicacao.dao.Persistence;

import java.math.BigDecimal;

@Tabela("TB_PRODUTOS")
public class Produto implements Persistence {

    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;
    @TipoChave("getCodigo")
    @ColunaTabela(dbName = "codigo", setJavaName = "setCodigo")
    private String codigo;
    @ColunaTabela(dbName = "nome", setJavaName = "setNome")
    private String nome;
    @ColunaTabela(dbName = "descricao", setJavaName = "setDescricao")
    private String descricao;
    @ColunaTabela(dbName = "peso", setJavaName = "setPeso")
    private Double peso;
    @ColunaTabela(dbName = "valor", setJavaName = "setValor")
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
