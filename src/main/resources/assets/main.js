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
  return $(`<div id="tag-${receiptId}-${tag}" class="tag" onclick="removeTag(this)">${tag}<span class=""></span></div>`);
}

function appendReceipt(receipt) {

  var inputId = tagInputPrefix + receipt.id;
  var receiptTagSpanId = tagSpanPrefix + receipt.id;

  var row = $(`<tr id="receipt-${receipt.id} class="receipt">
        <th>
            <h4 class="receiptId">${receipt.id}</h4>
            <h4>${receipt.created}</h4>
              </th>
              <td>
                  <h4>${receipt.merchantName}</h4>
              </td>
              <td>
                  <h4>$${receipt.value.toFixed(2)}</h4>
              </td>
              <td><span id="${receiptTagSpanId}"
                        class="receiptTag">
                          
              <button   id="add-tag-${receipt.id}"
                        type="button"
                        class="add-tag btn btn-primary pull-left"
                        onclick="toggleTagInput(this, ${receipt.id})">Add Tag</button>
                        
              <input    id="${inputId}"
                        type="text"
                        class="form-control"
                        placeholder="new tag"
                        onkeypress="tagInputKeyPress(event)">
                     
              </span></td></tr>`
  );


  row.appendTo($('#receiptList'));

  $('#' + inputId).hide();

  for (var i = 0; i < receipt.tags.length; i++) {
    tagHtml(receipt.id, receipt.tags[i]).appendTo($('#' + receiptTagSpanId));
  }

}


$(function () {

  $.getJSON(api + "/receipts", function (receipts) {
    //$.getJSON(api + "assets/receipts.json", function (receipts) {

    for (var i = 0; i < receipts.length; i++) {
      var receipt = receipts[i];
      appendReceipt(receipt);
    }

  });

});