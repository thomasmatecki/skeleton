
const api = "";
const tagInputPrefix = "tag-input-";
const tagSpanPrefix = "tags-";

/**
 *
 * @param elem
 */
function submitReceipt(elem) {

  $.ajax({
    type: "POST",
    url: api + "/receipts",
    data: JSON.stringify({
      merchant: $("input#merchant").val(),
      amount: $("input#amount").val()
    }),
    contentType: "application/json",
    dataType: "json",
    success: function (data, textStatus, jqXHR) {
      appendReceipt(data);
    }
  });
}

/**
 *
 * @param receiptId
 * @param tag
 */
function toggleReceiptTag(receiptId, tag) {

  $.ajax({
    type: "PUT",
    url: api + "/tags/" + tag,
    data: receiptId,
    contentType: "application/json",
    dataType: "json",
    success: function (data, textStatus, jqXHR) {
      console.log("Success");
    }
  });

}

/**
 *
 * @param elem
 * @param receiptId
 */
function toggleTagInput(elem, receiptId) {
  $('#' + tagInputPrefix + receiptId).toggle();
}

/**
 *
 * @param event
 */
function tagInputKeyPress(event) {

  if (event.keyCode == 13) {

    var tag = event.target.value;
    var receiptId = event.target.id.replace(tagInputPrefix, "");

    console.log("add tag " + tag + " event for row " + receiptId)

    event.target.value = "";                                      //Clear the input

    tagHtml(receiptId, tag).appendTo(event.target.parentElement);

    toggleReceiptTag(receiptId, tag);

    $('#' + event.target.id).hide();

  }
}

/**
 *
 * @param elem
 */
function removeTag(elem) {

  var receiptId = elem.parentElement.id.replace(tagSpanPrefix, "");
  var tag = elem.textContent;

  toggleReceiptTag(receiptId, tag);
  elem.remove();

}

function tagHtml(receiptId, tag) {
  return $(`<div id="tag-${receiptId}-${tag}" class="tagValue tag" onclick="removeTag(this)">${tag}<span class=""></span></div>`);
}

function appendReceipt(receipt) {

  var inputId = tagInputPrefix + receipt.id;
  var receiptTagSpanId = tagSpanPrefix + receipt.id;

  var row = $(`
    <div id="receipt-${receipt.id}" class="receipt">
        <div class="row">
          <div class="col-md-2">${receipt.created}</div>
          <div class="merchant col-md-2">${receipt.merchantName}</div>
          <div class="amount col-md-2">${receipt.value.toFixed(2)}</div>
          <div class="tags col-md-6">
              <span id="${receiptTagSpanId}" class="receiptTag">
  
                  <button   id="add-tag-${receipt.id}"
                            type="button"
                            class="add-tag btn pull-left"
                            onclick="toggleTagInput(this, ${receipt.id})">+</button>
    
                  <input    id="${inputId}"
                            type="text"
                            class="tag_input tag"
                            onkeypress="tagInputKeyPress(event)">
    
                  </span>
          </div>
        </div>
    </div>`);

  row.appendTo($('#receiptList'));

  $('#' + inputId).hide();

  for (var i = 0; i < receipt.tags.length; i++) {
    tagHtml(receipt.id, receipt.tags[i]).appendTo($('#' + receiptTagSpanId));
  }
}


$(function () {

  $.getJSON(api + "/receipts", function (receipts) {
    for (var i = 0; i < receipts.length; i++) {
      var receipt = receipts[i];
      appendReceipt(receipt);
    }

  });

});