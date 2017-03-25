/** Create js functions here*/
function filter() {
    var search = {}
    search["dateFromFilterStr"] = $("#dateFromFilter").val();
    search["dateToFilterStr"]   = $("#dateToFilter").val();
    search["sourceFilterStr"]   = $("#sourceFilter").val();
    search["typeFilterStr"]     = $("#typeFilter").val();
    search["categoryFilterStr"] = $("#categoryFilter").val();
    search["amountFilterStr"]   = $("#amountFilter").val();


    $.ajax({
        type: "POST",
        url: "/entries/api/filter",
        data: JSON.stringify(search),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: 'json',
        cache: false,
        timeout: 600000,

        success: function (data) {
            var entries = data.entries;
            var table = document.getElementById("entriesTable");

            while (table.rows.length > 2){
                table.deleteRow(2);
            }
            for (var i = 0; i < entries.length; i++){
                var row = table.insertRow(2);
                var cellId       = row.insertCell(0)
                var cellDate     = row.insertCell(1);
                var cellDate2    = row.insertCell(2);
                var cellSource   = row.insertCell(3);
                var cellType     = row.insertCell(4);
                var cellCategory = row.insertCell(5);
                var cellAmount   = row.insertCell(6);
                var cellActions  = row.insertCell(7);
                cellId.innerHTML       = entries[i].id;
                cellDate.innerHTML     = entries[i].entryDate;
                cellDate2.innerHTML    = "";
                cellSource.innerHTML   = entries[i].source.name;
                cellType.innerHTML     = entries[i].entryType;
                cellCategory.innerHTML = entries[i].category.name;
                cellAmount.innerHTML   = entries[i].amount;


            }

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