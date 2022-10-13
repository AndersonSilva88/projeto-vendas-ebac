package main.aplicacao.services.generic;

import main.aplicacao.dao.Persistence;
import main.aplicacao.dao.generic.IGenericDAO;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

public class GenericService<T extends Persistence, E extends Serializable> implements IGenericService<T, E> {

    protected IGenericDAO<T,E> dao;

    public GenericService(IGenericDAO<T,E> dao) {
        this.dao = dao;
    }

    @Override
    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return this.dao.cadastrar(entity);
    }

    @Override
    public void excluir(E valor) throws DAOException {
        this.dao.excluir(valor);
    }

    @Override
    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
            this.dao.alterar(entity);
    }

    @Override
    public T consultar(E valor) throws DAOException {

        try {
            return this.dao.consultar(valor);
        } catch (MaisDeUmRegistroException | TableException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<T> buscarTodos() throws DAOException {
        return this.dao.buscarTodos();
    }
}
