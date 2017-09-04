package api;

import dao.TagDao;
import io.dropwizard.jersey.validation.Validators;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.Validator;

public class TagDaoTest extends AbstractDaoTest {


  public static org.jooq.Configuration setupJooq() {
    // For now we are just going to use an H2 Database.  We'll upgrade to mysql later
    // This connection string tells H2 to initialize itself with our schema.sql before allowing connections
    final String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'";
    JdbcConnectionPool cp = JdbcConnectionPool.create(jdbcUrl, "sa", "sa");

    // This sets up jooq to talk to whatever database we are using.
    org.jooq.Configuration jooqConfig = new DefaultConfiguration();
    jooqConfig.set(SQLDialect.MYSQL);   // Lets stick to using MySQL (H2 is OK with this!)
    jooqConfig.set(cp);
    return jooqConfig;
  }

  private final Validator validator = Validators.newValidator();
  private final Configuration jooqConfig = setupJooq();
  private final TagDao dao = new TagDao(jooqConfig);

  @Test
  public void testInsertThenCheckExistence() {
    dao.insert(16, "Pizza");
    Assert.assertTrue(dao.exists(16, "Pizza"));
  }


  @Test
  public void testInsertThenDelete() {

    dao.insert(16, "Pizza");
    dao.delete(16, "Pizza");

    Assert.assertFalse(dao.exists(16, "Pizza"));

  }
}