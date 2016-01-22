/**
 * Created by Silence<me@chenzhiguo.cn> on 15/7/6.
 */
$(function () {
    var dateFormat = 'YYYY-MM-DD';
    var rangePicker = $('#searchDateRange');
    var rangeSpan = rangePicker.find('span');
    var dateFrom = $('#invokeDateFrom');
    var dateTo = $('#invokeDateTo');
    //Date range picker
    rangePicker.daterangepicker({
    	dateLimit:{days:30},//起止时间的最大间隔
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        format: dateFormat
    })
        
    $('#searchDateRange').on('apply.daterangepicker', function (ev, picker) {
    	dateFrom.val(picker.startDate.format(dateFormat));
        dateTo.val(picker.endDate.format(dateFormat));
        rangeSpan.html(dateFrom.val() + ' ~ ' + dateTo.val());
    	loadData();
    });
    dateFrom.val(moment().format(dateFormat));
    dateTo.val(moment().format(dateFormat));
    rangeSpan.html(dateFrom.val() + ' ~ ' + dateTo.val());
    loadData();
});
function loadData() {
    $.ajax({
        type: "POST", url: "slowquery",  data: {
            "invokeDateFrom": new Date($('#invokeDateFrom').val().replace(new RegExp("-","gm"),"/") + ' 00:00:00'),
            "invokeDateTo": new Date($('#invokeDateTo').val().replace(new RegExp("-","gm"),"/") + ' 23:59:59')
        }, error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
        	$("#queryDivId").html(data);
        }
    });
}
