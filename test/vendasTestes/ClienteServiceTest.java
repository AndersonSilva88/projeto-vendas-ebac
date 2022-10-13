package vendasTestes;

import main.aplicacao.dao.IClienteDAO;
import main.aplicacao.domain.Cliente;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import main.aplicacao.services.ClienteService;
import main.aplicacao.services.IClienteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vendasTestes.dao.ClienteDaoMock;

import java.sql.SQLException;

public class ClienteServiceTest {

    private IClienteService clienteService;

    private Cliente cliente;

    public ClienteServiceTest() {
        IClienteDAO dao = new ClienteDaoMock();
        clienteService = new ClienteService(dao);
    }

    @Before
    public void init() {
        cliente = new Cliente();
        cliente.setCpf(12312312312L);
        cliente.setRg(458965896255L);
        cliente.setNome("Anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("End");
        cliente.setEstado("SP");
        cliente.setNumero(10);
        cliente.setTelefone(1199999999L);

    }

    @Test
    public void pesquisarCliente() throws DAOException {
        Cliente clienteConsultado = clienteService.buscarPorCpf(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);
    }

    @Test
    public void salvarCliente() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = clienteService.cadastrar(cliente);

        Assert.assertTrue(retorno);
    }

    @Test
    public void excluirCliente() throws DAOException {
        clienteService.excluir(cliente.getCpf());
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        cliente.setNome("Anderson java");
        clienteService.alterar(cliente);

        Assert.assertEquals("Anderson java", cliente.getNome());
    }
}
