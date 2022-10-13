package main.aplicacao.dao;

import main.aplicacao.dao.generic.GenericDAO;
import main.aplicacao.domain.Cliente;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

    public ClienteDAO() {
        super();
    }


    @Override
    public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    public void atualizarDados(Cliente entity, Cliente entityCadastrado) {
        entityCadastrado.setCidade(entity.getCidade());
        entityCadastrado.setCpf(entity.getCpf());
        entityCadastrado.setRg(entity.getRg());
        entityCadastrado.setEndereco(entity.getEndereco());
        entityCadastrado.setEstado(entity.getEstado());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setNumero(entity.getNumero());
        entityCadastrado.setTelefone(entity.getTelefone());
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_CLIENTE ");
        sb.append("(ID, NOME, CPF, RG, TEL, ENDERECO, NUMERO, CIDADE, ESTADO)");
        sb.append("VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_CLIENTE WHERE CPF = ?";
    }

    @Override
    protected String getQueryAtualizacao() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_CLIENTE ");
        sb.append("SET NOME = ?,");
        sb.append("TELEFONE = ?,");
        sb.append("ENDERECO = ?,");
        sb.append("NUMERO = ?,");
        sb.append("CIDADE = ?,");
        sb.append("ESTADO = ?");
        sb.append("WHERE CPF = ?,");
        sb.append("RG = ? ");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Cliente entity) throws SQLException {
        stmInsert.setString(1, entity.getNome());
        stmInsert.setLong(2, entity.getCpf());
        stmInsert.setLong(3,entity.getRg());
        stmInsert.setLong(4, entity.getTelefone());
        stmInsert.setString(5, entity.getEndereco());
        stmInsert.setLong(6, entity.getNumero());
        stmInsert.setString(7, entity.getCidade());
        stmInsert.setString(8, entity.getEstado());
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmDelete, Long valor) throws SQLException {
        stmDelete.setLong(1, valor);
    }

    @Override
    protected void setParametrosQueryAtualizao(PreparedStatement stmUpdate, Cliente entity) throws SQLException {
        stmUpdate.setString(1, entity.getNome());
        stmUpdate.setLong(2, entity.getTelefone());
        stmUpdate.setString(3, entity.getEndereco());
        stmUpdate.setLong(4, entity.getNumero());
        stmUpdate.setString(5, entity.getCidade());
        stmUpdate.setString(6, entity.getEstado());
        stmUpdate.setLong(7, entity.getCpf());
        stmUpdate.setLong(8, entity.getRg());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, Long valor) throws SQLException {
        stmSelect.setLong(1, valor);
    }
}
