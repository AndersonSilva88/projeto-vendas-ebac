package main.aplicacao.dao;

import main.aplicacao.dao.factory.ProdutoQuantidadeFactory;
import main.aplicacao.dao.factory.VendaFactory;
import main.aplicacao.dao.generic.GenericDAO;
import main.aplicacao.domain.ProdutoQuantidade;
import main.aplicacao.domain.Venda;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.sql.*;
import java.util.*;

public class VendaDAO  extends GenericDAO<Venda, String> implements IVendaDAO {


    @Override
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            String sql = "UPDATE TB_VENDAS SET STATUS_VENDA = ? WHERE ID = ?";
            connection= getConnection();
                stm = connection.prepareStatement(sql);
                stm.setString(1, Venda.Status.CONCLUIDA.name());
                stm.setLong(2, venda.getId());
                stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ATUALIZANDO OBJETO", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE TB_VENDAS SET STATUS_VENDA = ? WHERE ID = ?";
            connection = getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, Venda.Status.CANCELADA.name());
            stm.setLong(2, venda.getId());
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("ERRO ATUALIZANDO OBJETO ", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Collection<Venda> buscarTodos() throws DAOException {
        List<Venda> lista = new ArrayList<>();
        StringBuilder sb = sqlBaseSelect();

        try {
            Connection connection = getConnection();
            PreparedStatement stm = connection.prepareStatement(sb.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Venda venda = VendaFactory.convert(rs);
                buscarAssociacaoVendaProdutos(connection, venda);
                lista.add(venda);
            }

        } catch (SQLException e) {
            throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
        }
        return lista;
    }

    private StringBuilder sqlBaseSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT V.ID AS ID_VENDA, V.CODIGO, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA, ");
        sb.append("C.ID AS ID_CLIENTE, C.NOME, C.CPF, C.RG, C.TELEFONE, C.ENDERECO, C.NUMERO, C.CIDADE, C.ESTADO ");
        sb.append("FROM TB_VENDAS V ");
        sb.append("INNER JOIN TB_CLIENTES C ON V.ID_CLIENTE_FK = C.ID ");
        return sb;
    }


    @Override
    public Boolean cadastrar(Venda entity) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = getConnection();
            stm = connection.prepareStatement(getQueryInsercao(), Statement.RETURN_GENERATED_KEYS);
            setParametrosQueryInsercao(stm, entity);
            int rowsAffected = stm.executeUpdate();

            if(rowsAffected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()){
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                }

                for (ProdutoQuantidade prod : entity.getProdutos()) {
                    stm = connection.prepareStatement(getQueryInsercaoProdQuant());
                    setParametrosQueryInsercaoProdQuant(stm, entity, prod);
                    rowsAffected = stm.executeUpdate();
                }


                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("ERRO CADASTRANDO OBJETO ", e);
        } finally {
            closeConnection(connection, stm, null);
        }
        return false;
    }

    @Override
    public Class<Venda> getTipoClasse() {
        return Venda.class;
    }


    @Override
    public void atualiarDados(Venda entity, Venda entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setStatus(entity.getStatus());
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_VENDAS ");
        sb.append("(ID, CODIGO, ID_CLIENTE_FK, VALOR_TOTAL, DATA_VENDA, STATUS_VENDA)");
        sb.append("VALUES (nextval('sq_vendas'),?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected String getQueryExclusao() {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected String getQueryAtualizacao() {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Venda entity) throws SQLException {
        stmInsert.setString(1, entity.getCodigo());
        stmInsert.setLong(2, entity.getCliente().getId());
        stmInsert.setBigDecimal(3, entity.getValorTotal());
        stmInsert.setTimestamp(4, Timestamp.from(entity.getDataVenda()));
        stmInsert.setString(5, entity.getStatus().name());
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected void setParametrosQueryAtualizao(PreparedStatement stmUpdate, Venda entity) throws SQLException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
        stmSelect.setString(1, valor);
    }

    private String getQueryInsercaoProdQuant() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO_QUANTIDADE ");
        sb.append("(ID, ID_PRODUTO_FK, ID_VENDA_FK, QUANTIDADE, VALOR_TOTAL)");
        sb.append("VALUES (nextval('sq_produto_quantidade'),?,?,?,?)");
        return sb.toString();
    }

    private void setParametrosQueryInsercaoProdQuant(PreparedStatement stm, Venda venda, ProdutoQuantidade prod) throws SQLException {
        stm.setLong(1, prod.getProduto().getId());
        stm.setLong(2, venda.getId());
        stm.setInt(3, prod.getQuantidade());
        stm.setBigDecimal(4, prod.getValorTotal());
    }

    private void buscarAssociacaoVendaProdutos(Connection connection, Venda venda)
            throws DAOException {
        PreparedStatement stmProd = null;
        ResultSet rsProd = null;
        try {
            StringBuilder sbProd = new StringBuilder();
            sbProd.append("SELECT PQ.ID, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
            sbProd.append("P.ID AS ID_PRODUTO, P.CODIGO, P.NOME, P.DESCRICAO, P.PESO, P.VALOR ");
            sbProd.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
            sbProd.append("INNER JOIN TB_PRODUTOS P ON P.ID = PQ.ID_PRODUTO_FK ");
            sbProd.append("WHERE PQ.ID_VENDA_FK = ?");
            stmProd = connection.prepareStatement(sbProd.toString());
            stmProd.setLong(1, venda.getId());
            rsProd = stmProd.executeQuery();
            Set<ProdutoQuantidade> produtos = new HashSet<>();
            while(rsProd.next()) {
                ProdutoQuantidade prodQ = ProdutoQuantidadeFactory.convert(rsProd);
                produtos.add(prodQ);
            }
            venda.setProdutos(produtos);
            venda.recalcularValorTotalVenda();
        } catch (SQLException e) {
            throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
        } finally {
            closeConnection(connection, stmProd, rsProd);
        }
    }

}
