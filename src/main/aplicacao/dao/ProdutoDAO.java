package main.aplicacao.dao;

import main.aplicacao.dao.generic.GenericDAO;
import main.aplicacao.domain.Produto;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {



    public ProdutoDAO() {
        super();
    }


    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    public void atualiarDados(Produto entity, Produto entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setPeso(entity.getPeso());
        entityCadastrado.setValor(entity.getValor());
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTOS ");
        sb.append("(ID, CODIGO, NOME, DESCRICAO, PESO, VALOR)");
        sb.append("VALUES (nextval('sq_produto'),?,?,?,?)");

        return sb.toString();
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_PRODUTOS WHERE CODIGO = ?";
    }

    @Override
    protected String getQueryAtualizacao() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO ");
        sb.append("SET CODIGO = ?,");
        sb.append("NOME = ?,");
        sb.append("DESCRICAO = ?,");
        sb.append("PESO = ?,");
        sb.append("VALOR = ?");
        sb.append("WHERE CODIGO = ? ");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Produto entity) throws SQLException {
        stmInsert.setString(1, entity.getCodigo());
        stmInsert.setString(2, entity.getNome());
        stmInsert.setString(3, entity.getDescricao());
        stmInsert.setDouble(4,entity.getPeso());
        stmInsert.setBigDecimal(5, entity.getValor());
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
        stmDelete.setString(1, valor);
    }

    @Override
    protected void setParametrosQueryAtualizao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
        stmUpdate.setString(1, entity.getCodigo());
        stmUpdate.setString(2, entity.getNome());
        stmUpdate.setString(3, entity.getDescricao());
        stmUpdate.setDouble(4, entity.getPeso());
        stmUpdate.setBigDecimal(5, entity.getValor());
        stmUpdate.setString(6, entity.getCodigo());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
            stmSelect.setString(1, valor);
    }




}

