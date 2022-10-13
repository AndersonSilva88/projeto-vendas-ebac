package main.aplicacao.dao;

import main.aplicacao.dao.generic.IGenericDAO;
import main.aplicacao.domain.Venda;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda, String> {

    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
}
