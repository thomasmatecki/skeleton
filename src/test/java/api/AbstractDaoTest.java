package api;

import io.dropwizard.jersey.validation.Validators;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import javax.validation.Validator;

abstract public class AbstractDaoTest {

  static final Validator validator = Validators.newValidator();
  static final String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'";
  static final JdbcConnectionPool cp = JdbcConnectionPool.create(jdbcUrl, "sa", "sa");
  static final Configuration jooqConfig = new DefaultConfiguration();
  static {
    jooqConfig.set(SQLDialect.MYSQL);   // Lets stick to using MySQL (H2 is OK with this!)
    jooqConfig.set(cp);
  }
}
