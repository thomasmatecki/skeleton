package dao;

import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
  DSLContext dsl;

  public TagDao(Configuration jooqConfig) {
    this.dsl = DSL.using(jooqConfig);
  }

  public void insert(int receiptId, String tag) {
    dsl.insertInto(TAGS, TAGS.ID, TAGS.TAG)
        .values(receiptId, tag)
        .returning()
        .fetchOne();
  }

  public boolean exists(int receiptId, String tag) {

    return 0 < dsl.selectCount()
        .from(TAGS)
        .where(TAGS.ID.equal(receiptId))
        .and(TAGS.TAG.equal(tag))
        .fetchOne(0, Integer.class);

  }

  public void delete(int receiptId, String tag) {

    dsl.delete(TAGS)
        .where(TAGS.ID.equal(receiptId))
        .and(TAGS.TAG.equal(tag)).execute();

  }

  public List<ReceiptsRecord> getAllReceiptsForTag(String tagName) {
    return dsl.select()
        .from(TAGS)
        .join(RECEIPTS)
        .on(TAGS.ID.eq(RECEIPTS.ID))
        .where(TAGS.TAG.equal(tagName))
        .fetchInto(RECEIPTS);
  }

}