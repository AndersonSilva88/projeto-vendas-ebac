package vendasTestes;

import main.aplicacao.dao.IProdutoDAO;
import main.aplicacao.domain.Produto;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import main.aplicacao.services.IProdutoService;
import main.aplicacao.services.ProdutoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vendasTestes.dao.ProdutoDaoMock;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ProdutoServiceTest {
    private IProdutoService produtoService;

    private Produto produto;

    public ProdutoServiceTest() {
        IProdutoDAO dao = new ProdutoDaoMock();
        produtoService = new ProdutoService(dao);
    }

    @Before
    public void init() {
        produto = new Produto();
        produto.setCodigo("A1");
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setPeso(15.0);
        produto.setValor(BigDecimal.TEN);
    }

    @Test
    public void pesquisar() throws DAOException {
        Produto produtor = this.produtoService.consultar(produto.getCodigo());
        Assert.assertNotNull(produtor);
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        Boolean retorno = produtoService.cadastrar(produto);
        Assert.assertTrue(retorno);
    }

    @Test
    public void excluir() throws DAOException {
        produtoService.excluir(produto.getCodigo());
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        produto.setNome("Anderson java");
        produtoService.alterar(produto);

        Assert.assertEquals("Anderson java", produto.getNome());
    }
}
