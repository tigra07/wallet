/** Create js functions here*/
function filter(header, token) {
    var search = {}
    search["dateFromFilterStr"] = $("#dateFromFilter").val();
    search["dateToFilterStr"]   = $("#dateToFilter").val();
    search["sourceFilterStr"]   = $("#sourceFilter").val();
    search["typeFilterStr"]     = $("#typeFilter").val();
    search["categoryFilterStr"] = $("#categoryFilter").val();


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

        beforeSend: function(xhr){xhr.setRequestHeader(header, token);},

        success: function (data) {
            var entries = data.entries;
            var table = document.getElementById("entriesTable");

            while (table.rows.length > 2){
                table.deleteRow(2);
            }
            for (var i = 0; i < entries.length; i++){
                var row = table.insertRow(2);
                var cellDate     = row.insertCell(0);
                var cellSource   = row.insertCell(1);
                var cellType     = row.insertCell(2);
                var cellCategory = row.insertCell(3);
                var cellAmount   = row.insertCell(4);
                var cellActions  = row.insertCell(5);

                cellDate.innerHTML     = entries[i].entryDate;
                cellSource.innerHTML   = entries[i].source.name;
                cellType.innerHTML     = entries[i].entryType;
                cellCategory.innerHTML = entries[i].category.name;
                cellAmount.innerHTML   = entries[i].amount;

                var id = entries[i].id
                urlInCell(cellActions, "edit", id);
                urlInCell(cellActions, "details", id);
                urlInCell(cellActions,"delete", id);
            }
            $("#totalIncome").html(data.totalIncome);
            $("#totalOutcome").html(data.totalOutcome);
            $("#balance").html(data.balance);
        },
        error: function (e) {
            alert("error");
        }
    });

    function urlInCell(cell, action, id) {
        var url = "http://" + window.location.host + "/entries/" + action + "/" + id;

        var createA = document.createElement('a');
        var createAText = document.createTextNode(action);
        createA.setAttribute('href', url);
        createA.appendChild(createAText);
        cell.appendChild(createA);

        var space = document.createTextNode(' ');
        cell.appendChild(space);
    }
}