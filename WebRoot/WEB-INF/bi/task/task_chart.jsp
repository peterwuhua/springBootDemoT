<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../common/echarts.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><strong id="mesg">图表</strong></li>
					</ol>
				</h5>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">列表信息</a>
						<a type="button" class="btn btn-xs btn-primary active" href="javascript:;">图表信息</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				 <div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="panel-body">
							<div id="container" style="width: 100%; height: 400px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
<script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};

option = null;
app.title = '坐标轴刻度与标签对齐';

option = {
		color: ['#26C0C0'],
		tooltip : {
			trigger: 'axis',
			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
			}
		},
	    legend: {
	    	 data:['任务数量']
	    },
	    grid: {
	    	left: '3%',
			right: '10%',
			bottom: '3%',
			containLabel: true
	    },
	    toolbox: {
			show: true,
			feature: {
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
		},
	    xAxis: [
	       {
		    	name : '月份',
		        type: 'category',
		        data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
		        boundaryGap: false,
	       }
	    ],
	    yAxis : [
	       {
 				name : '数量',
 				type : 'value'
	 		}
	    ],
	    series: [
	        {
	        	name:'数量',
				type:'line',
				data:[],
				markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            },
	            markLine: {
	                data: [
	                    {type: 'average', name: '平均值'}
	                ]
	            }
	        }
	    ]
	};
if (option && typeof option === "object") {
	myChart.setOption(option, true);
};

$(function(){
	//初始化
	var url="charts.do";
	seachForm(url);
})
//ajax调用查询
function seachForm(url){
	$.ajax({
		type : "post",
		async : false,
		url :url,    
		dataType : "json",       
		success : function(result) {
			refreshData(result);
		}
	})
}
//刷新echarts
function refreshData(values){
	if(!myChart){
		return;
	}
	//更新数据
	var option = myChart.getOption();
	option.series[0].data = values; 
	myChart.setOption(option);    
}
</script>
</body>
</html>