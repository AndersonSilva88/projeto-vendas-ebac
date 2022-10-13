package vendasTestes;

import main.aplicacao.dao.ClienteDAO;
import main.aplicacao.dao.IClienteDAO;
import main.aplicacao.domain.Cliente;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class ClienteDaoTest {

    private IClienteDAO clienteDao;

    public ClienteDaoTest() {
        clienteDao = new ClienteDAO();
    }

    @After
    public void end() throws DAOException {
        Collection<Cliente> list = clienteDao.buscarTodos();
        list.forEach(cli -> {
            try {
                clienteDao.excluir(cli.getCpf());
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws MaisDeUmRegistroException, TableException, TipoChaveNaoEncontradaException, DAOException, SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(56565656565L);
        cliente.setRg(896559995444L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("PR");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);
        clienteDao.cadastrar(cliente);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.excluir(cliente.getCpf());
    }


    @Test
    public void salvarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException, SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(56565656565L);
        cliente.setRg(896559995444L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("PR");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);
        Boolean retorno = clienteDao.cadastrar(cliente);
        assertTrue(retorno);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.excluir(cliente.getCpf());
    }

    @Test
    public void excluirCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException, SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(56565656565L);
        cliente.setRg(896559995444L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("PR");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);
        Boolean retorno = clienteDao.cadastrar(cliente);
        assertTrue(retorno);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.excluir(cliente.getCpf());
        clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNull(clienteConsultado);
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException, SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(56565656565L);
        cliente.setRg(896559995444L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("PR");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);
        Boolean retorno = clienteDao.cadastrar(cliente);
        assertTrue(retorno);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);

        clienteConsultado.setNome("Anderson java");
        clienteDao.alterar(clienteConsultado);

        Cliente clienteAlterado = clienteDao.consultar(clienteConsultado.getCpf());
        Assert.assertNotNull(clienteAlterado);
        Assert.assertEquals("Anderson java", clienteAlterado.getNome());

        clienteDao.excluir(cliente.getCpf());
        clienteConsultado = clienteDao.consultar(cliente.getCpf());
        Assert.assertNull(clienteConsultado);
    }

    @Test
    public void buscarTodos() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(56565656565L);
        cliente.setRg(896559995444L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("PR");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);
        Boolean retorno = clienteDao.cadastrar(cliente);
        assertTrue(retorno);

        Cliente cliente1 = new Cliente();
        cliente1.setCpf(56565656569L);
        cliente.setRg(896559995444L);
        cliente1.setNome("Anderson");
        cliente1.setCidade("Curitiba");
        cliente1.setEndereco("End");
        cliente1.setEstado("PR");
        cliente1.setNumero(10);
        cliente1.setTelefone(1199999999L);
        Boolean retorno1 = clienteDao.cadastrar(cliente1);
        assertTrue(retorno1);

        Collection<Cliente> list = clienteDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        list.forEach(cli -> {
            try {
                clienteDao.excluir(cli.getCpf());
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        Collection<Cliente> list1 = clienteDao.buscarTodos();
        assertTrue(list1 != null);
        assertTrue(list1.size() == 0);
    }
}
