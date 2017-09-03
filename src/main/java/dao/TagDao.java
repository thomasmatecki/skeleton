package dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class TagDao {
  DSLContext dsl;

  public TagDao(Configuration jooqConfig) {
    this.dsl = DSL.using(jooqConfig);
  }
  public int insert(int receiptId, String Tag) {

    return 0;

  }

}
