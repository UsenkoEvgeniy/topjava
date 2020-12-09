var ctx;
var mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
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
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: function () {
            $.get(mealAjaxUrl, updateTableByData);
        }
    };
    makeEditable();

    jQuery.datetimepicker.setLocale(navigator.language);
    jQuery('#dateTime').datetimepicker({
        format: 'Y-m-d H:i',
        lang: 'ru',
    });
});

jQuery(function () {
    jQuery('#startDate').datetimepicker({
        format: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: jQuery('#endDate').val() ? jQuery('#endDate').val() : false
            })
        },
        timepicker: false
    });
    jQuery('#endDate').datetimepicker({
        format: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: jQuery('#startDate').val() ? jQuery('#startDate').val() : false
            })
        },
        timepicker: false
    });
    jQuery('#startTime').datetimepicker({
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                maxTime: jQuery('#endTime').val() ? jQuery('#endTime').val() : false
            })
        },
        datepicker: false
    });
    jQuery('#endTime').datetimepicker({
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                minTime: jQuery('#startTime').val() ? jQuery('#startTime').val() : false
            })
        },
        datepicker: false
    });
});