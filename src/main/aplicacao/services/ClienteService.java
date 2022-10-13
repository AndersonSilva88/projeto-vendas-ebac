package main.aplicacao.services;

import main.aplicacao.dao.IClienteDAO;
import main.aplicacao.domain.Cliente;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import main.aplicacao.services.generic.GenericService;

import java.sql.SQLException;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {

    private IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        super(clienteDAO);
    }


    public Boolean salvar(Cliente cliente) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return clienteDAO.cadastrar(cliente);
    }

    @Override
    public void excluir(Long cpf) throws DAOException {
        clienteDAO.excluir(cpf);
    }

    @Override
    public void alterar(Cliente cliente) throws TipoChaveNaoEncontradaException, DAOException {
        clienteDAO.alterar(cliente);
    }


    @Override
    public Cliente buscarPorCpf(Long cpf) throws DAOException {
        try {
            return this.dao.consultar(cpf);
        } catch (MaisDeUmRegistroException | TableException e) {
            e.printStackTrace();
        }

        return null;
    }
}
