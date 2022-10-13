package vendasTestes;

import main.aplicacao.dao.IProdutoDAO;
import main.aplicacao.dao.ProdutoDAO;
import main.aplicacao.domain.Produto;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.Assert.*;

public class ProdutoDaoTest {

    private IProdutoDAO produtoDao;

    public ProdutoDaoTest() {
        produtoDao = new ProdutoDAO();
    }

    @After
    public void end() throws DAOException {
        Collection<Produto> list = produtoDao.buscarTodos();
        list.forEach(prod -> {
            try {
                produtoDao.excluir(prod.getCodigo());
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    private Produto criarProduto(String codigo) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setPeso(15.0);
        produto.setValor(BigDecimal.TEN);
        produtoDao.cadastrar(produto);
        return produto;
    }

    private void excluir(String valor) throws DAOException {
        this.produtoDao.excluir(valor);
    }

    @Test
    public void pesquisar() throws MaisDeUmRegistroException, TableException, DAOException, TipoChaveNaoEncontradaException, SQLException {
        Produto produto = criarProduto("A1");
        assertNotNull(produto);
        Produto produtoDB = this.produtoDao.consultar(produto.getCodigo());
        assertNotNull(produtoDB);
        excluir(produtoDB.getCodigo());
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Produto produto = criarProduto("A2");
        assertNotNull(produto);
        excluir(produto.getCodigo());
    }

    @Test
    public void excluir() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, SQLException {
        Produto produto = criarProduto("A3");
        assertNotNull(produto);
        excluir(produto.getCodigo());
        Produto produtoBD = this.produtoDao.consultar(produto.getCodigo());
        assertNull(produtoBD);
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException, SQLException {
        Produto produto = criarProduto("A4");
        produto.setNome("Anderson java");
        produtoDao.alterar(produto);
        Produto produtoBD = this.produtoDao.consultar(produto.getCodigo());
        assertNotNull(produtoBD);
        Assert.assertEquals("Anderson java", produtoBD.getNome());

        excluir(produto.getCodigo());
        Produto produtoBD1 = this.produtoDao.consultar(produto.getCodigo());
        assertNull(produtoBD1);
    }

    @Test
    public void buscarTodos() throws DAOException, TipoChaveNaoEncontradaException, SQLException {
        criarProduto("A5");
        criarProduto("A6");
        Collection<Produto> list = produtoDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        for (Produto prod : list) {
            excluir(prod.getCodigo());
        }

        list = produtoDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 0);

    }
}
