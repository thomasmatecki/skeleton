package api;

import dao.TagDao;
import org.junit.Assert;
import org.junit.Test;

public class TagDaoTest extends AbstractDaoTest {


  private final TagDao dao = new TagDao(jooqConfig);

  @Test
  public void testInsertThenCheckExistence() {
    dao.insert(16, "Pizza");
    Assert.assertTrue(dao.exists(16, "Pizza"));
  }
  
}