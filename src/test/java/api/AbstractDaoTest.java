package api;

import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

public class AbstractDaoTest {

  public static Configuration setupJooq() {
    // For now we are just going to use an H2 Database.  We'll upgrade to mysql later
    // This connection string tells H2 to initialize itself with our schema.sql before allowing connections
    final String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'";
    JdbcConnectionPool cp = JdbcConnectionPool.create(jdbcUrl, "sa", "sa");

    // This sets up jooq to talk to whatever database we are using.
    Configuration jooqConfig = new DefaultConfiguration();
    jooqConfig.set(SQLDialect.MYSQL);   // Lets stick to using MySQL (H2 is OK with this!)
    jooqConfig.set(cp);
    return jooqConfig;
  }

  protected final Configuration jooqConfig = setupJooq();


}
