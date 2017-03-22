/** Create js functions here*/
function filter() {
    var search = {}
    search["dateFromFilter"] = $("#dateFromFilter").val();
    search["dateToFilter"]   = $("#dateToFilter").val();
    search["sourceFilter"]   = $("#sourceFilter").val();
    search["typeFilter"]     = $("#typeFilter").val();
    search["categoryFilter"] = $("#categoryFilter").val();
    search["amountFilter"]   = $("#amountFilter").val();


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/entries/api/filter",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log(data)
            alert("data");
            // var json = "<h4>Ajax Response</h4><pre>"
            //     + JSON.stringify(data, null, 4) + "</pre>";
            // $('#feedback').html(json);
            //
            // console.log("SUCCESS : ", data);
            // $("#btn-search").prop("disabled", false);

        },
        error: function (e) {
            alert("error");
            // var json = "<h4>Ajax Response</h4><pre>"
            //     + e.responseText + "</pre>";
            // $('#feedback').html(json);
            //
            // console.log("ERROR : ", e);
            // $("#btn-search").prop("disabled", false);

        }
    });
}