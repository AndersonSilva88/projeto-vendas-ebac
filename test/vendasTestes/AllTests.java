package vendasTestes;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ClienteDaoTest.class, ClienteDaoTest.class,
    ProdutoServiceTest.class, ProdutoDaoTest.class, VendaDaoTest.class})
public class AllTests {

}
