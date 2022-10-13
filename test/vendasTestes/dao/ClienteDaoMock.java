package vendasTestes.dao;

import main.aplicacao.dao.IClienteDAO;
import main.aplicacao.domain.Cliente;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;
import java.util.Collection;

public class ClienteDaoMock implements IClienteDAO {
    @Override
    public Boolean cadastrar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return null;
    }

    @Override
    public void excluir(Long valor) throws DAOException {

    }

    @Override
    public void alterar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {

    }

    @Override
    public Cliente consultar(Long valor) throws MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = new Cliente();
        cliente.setCpf(valor);
        return cliente;
    }

    @Override
    public Collection<Cliente> buscarTodos() throws DAOException {
        return null;
    }
}
