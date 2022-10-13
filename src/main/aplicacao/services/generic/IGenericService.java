package main.aplicacao.services.generic;

import main.aplicacao.dao.Persistence;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

public interface IGenericService<T extends Persistence, E extends Serializable> {

    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException;

    public void excluir(E valor) throws DAOException;

    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E valor) throws DAOException;

    public Collection<T> buscarTodos() throws DAOException;
}
