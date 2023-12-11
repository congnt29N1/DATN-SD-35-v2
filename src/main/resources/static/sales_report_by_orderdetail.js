// Sales Report by Product
var data;
var chartOptions;

$(document).ready(function() {
    setupButtonEventHandlers("_orderdetail", loadSalesReportByDateForOrderDetail);
});
