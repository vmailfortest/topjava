var userAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: userAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: userAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": userAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories",
                },
                {
                    "orderable": false,
                    "defaultContent": "Edit",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "Delete",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                // $(row).attr("data-mealExceed", false);
                if (data.excess) {
                    $(row).attr("data-mealExceed", true);
                } else {
                    $(row).attr("data-mealExceed", false);
                }
            },
            columnDefs:[{targets:0, render:function(data){
                    return moment(data).format('YYYY-MM-DD HH:mm');
                }}]
        }),

        updateTable: function () {
            $.get(userAjaxUrl, updateTableByData);
        }
    });
});
