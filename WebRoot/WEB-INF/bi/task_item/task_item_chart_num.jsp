<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../common/echarts.jsp"%>
</head>
<body>
	<div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>统计参数</h5>
			</div>
			<div class="ibox-content" style="padding: 0px 5px;">
				<form id="seachForm" action="" class="form-horizontal">
					<div class="form-group" id="itemDiv" style="height: 320px;"></div>
					<div class="form-group">
						<div class="radio i-checks">
							<label> <input type="radio" value="thisYear" name="startDate" checked="checked">今年内
							</label> <label> <input type="radio" value="oneYear" name="startDate">一年内
							</label> <label> <input type="radio" value="all" name="startDate">全部
							</label>
						</div>
						<div class="col-sm-offset-2">
							<button class="btn btn-primary" type="button" id="confirmBut">确定</button>
							<button class="btn btn-primary" type="button" id="cleanBut">重置</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="panel-body">
							<div id="container" style="width: 700px; height: 400px"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
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
	    	 data:['检测数量']
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
		    	name : '测试项目',
		        type: 'category',
		        data: [],
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
	var url="chartNum.do";
	seachForm(url);
	//清空
	$('#cleanBut').click(function(){
		url="chartNum.do";
		$('input[value="thisYear"]').attr('checked',true);
		$('input[value="thisYear"]').iCheck('check');
		seachForm(url);
	});
	$('#confirmBut').click(function(){
		var startDate=$('input[name="startDate"]:checked').val();
		var item='';
		$('input[name="itemId"]:checked').each(function(){
			item+=$(this).val()+",";
		});
		url="chartNum.do?itemId="+item+'&startDate='+startDate;
		seachForm(url);
	});
})

//ajax调用查询
function seachForm(url){
	$.ajax({
		type : "post",
		async : false,
		url :url,    
		dataType : "json",       
		success : function(result) {
			refreshData(result.itemNames,result.values,result.average);
			refreshItem(result.itemList);
		}
	})
}
//刷新echarts
function refreshData(name,num,average){
	if(!myChart){
		return;
	}
	//更新数据
	var option = myChart.getOption();
	option.series[0].data = num; 
	option.xAxis[0].data = name;
	myChart.setOption(option);    
}
function refreshItem(itemList){
	$('#itemDiv').html('');
	for(var i=0;i<itemList.length;i++){
		$('#itemDiv').append('<div class="checkbox i-checks"><label><input type="checkbox" name="itemId" value="'+itemList[i].itemId+'" checked> <i></i>'+itemList[i].itemName+'</label></div>');
	}
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
}
</script>
</html>