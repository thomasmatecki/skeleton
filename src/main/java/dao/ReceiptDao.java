package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class ReceiptDao {
  DSLContext dsl;

  public ReceiptDao(Configuration jooqConfig) {
    this.dsl = DSL.using(jooqConfig);
  }

  public int insert(String merchantName, BigDecimal amount) {

    ReceiptsRecord receiptsRecord = dsl
        .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
        .values(merchantName, amount)
        .returning(RECEIPTS.ID)
        .fetchOne();

    checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

    return receiptsRecord.getId();
  }

  public ReceiptsRecord getOneReceipt(int id) {
    return dsl.selectFrom(RECEIPTS)
        .where(RECEIPTS.ID.eq(id))
        .fetchOne();
  }


  public List<ReceiptsRecord> getAllReceipts() {
    return dsl.selectFrom(RECEIPTS).fetch();
  }

  public List<TagsRecord> getAllTagsForReceipt(int id) {

    return dsl.select()
        .from(RECEIPTS)
        .join(TAGS)
        .on(RECEIPTS.ID.eq(TAGS.ID))
        .where(RECEIPTS.ID.equal(id))
        .fetchInto(TAGS);
  }

}
