package api;

import dao.TagDao;
import org.junit.Assert;
import org.junit.Test;

public class TagDaoTest extends AbstractDaoTest {


  private final TagDao tagDao = new TagDao(jooqConfig);

  @Test
  public void testInsertThenCheckExistence() {
    tagDao.insert(16, "Pizza");
    Assert.assertTrue(tagDao.exists(16, "Pizza"));
  }

/*@Test
  public void testInsertThenDelete() {
    dao.insert(16, "Pizza");
    dao.delete(16, "Pizza");

    Assert.assertFalse(dao.exists(16, "Pizza"));
  }*/

}