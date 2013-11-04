/**
 * init creating pie chart
 * 
 * @param id
 */
function initPieChart(id, title, datas) {
	if(!datas)  return;
	
	// init slice part
	if(datas.length > 1) {
		var n = datas[1][0], t = datas[1][1];
		datas[1] = {name: n, y: t, sliced: true, selected: true};
	}
    
	$("#" + id).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: "<b>" + title + "</b>"
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.y}%</b>',
    	    valueDecimals: 2
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(2) +' %';
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '比例',
            data: datas
        }]
    });
}