package main.aplicacao.dao.generic;

import main.DB.TipoChave;
import main.aplicacao.dao.Persistence;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Collection;

import static main.aplicacao.dao.generic.jdbc.ConnectionFactory.getConnection;

public abstract class GenericDAO<T extends Persistence, E extends Serializable> implements IGenericDAO<T, E> {

    public abstract Class<T> getTipoClasse();
    public abstract void atualizarDados(T entity, T entityCadastrado);
    protected abstract String getQueryInsercao();
    protected abstract String getQueryExclusao();
    protected abstract String getQueryAtualizacao();

    protected abstract void setParametrosQueryInsercao(PreparedStatement stmInsert, T entity) throws SQLException;
    protected abstract void setParametrosQueryExclusao(PreparedStatement stmDelete, E valor) throws SQLException;
    protected abstract void setParametrosQueryAtualizao(PreparedStatement stmUpdate, T entity) throws SQLException;
    protected abstract void setParametrosQuerySelect(PreparedStatement stmSelect, E valor) throws SQLException;

    public GenericDAO() {

    }

    public E getChave(T entity) throws TipoChaveNaoEncontradaException {
        Field[] fields = entity.getClass().getDeclaredFields();
        E returnValue = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(TipoChave.class)) {
                TipoChave tipoChave = field.getAnnotation(TipoChave.class);
                String nomeMetodo = tipoChave.value();
                try {
                    Method method = entity.getClass().getMethod(nomeMetodo);
                    returnValue = (E) method.invoke(entity);
                    return returnValue;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new TipoChaveNaoEncontradaException("Chave principal do objeto " + entity.getClass() + " não encontrada", e);
                }
            }
        }
        if (returnValue == null) {
            String msg = "Chave principal dp objeto" + entity.getClass() + " não encontrada";
            System.out.println("***** ERRO ****" + msg);
            throw new TipoChaveNaoEncontradaException(msg);
        }

        return null;
    }

    @Override
    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = getConnection();
            stm = connection.prepareStatement(getQueryInsercao(), Statement.RETURN_GENERATED_KEYS);
            setParametrosQueryInsercao(stm, entity);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()){
                    if (rs.next()) {
                        Persistence per = (Persistence) entity;
                        per.setId(rs.getLong(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao cadastrar", e);
        } finally {
            closeConnection(connection, stm, null);
        }

        return false;
    }

    @Override
    public void excluir(E valor) throws DAOException {

    }

    @Override
    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {

    }

    @Override
    public T consultar(E valor) throws MaisDeUmRegistroException, TableException, DAOException {
        return null;
    }

    @Override
    public Collection<T> buscarTodos() throws DAOException {
        return null;
    }

    protected void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
