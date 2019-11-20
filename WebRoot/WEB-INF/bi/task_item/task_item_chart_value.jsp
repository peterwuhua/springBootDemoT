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
				<h5>测试项目</h5>
			</div>
			<div class="ibox-content" style="padding:0px 5px;">
				<form id="seachForm" action="" class="form-horizontal">
					<input type="hidden" name="itemId" id="itemId" value="${vo.itemId}">
					<div class="form-group" id="itemDiv" style="height: 320px;">
					</div>
					<div class="form-group">
						 <div class="radio i-checks">
                             <label>
                                 <input type="radio" value="thisYear" name="startDate" checked="checked">今年内
                             </label>
                             <label>
                                 <input type="radio" value="oneYear"  name="startDate">一年内
                            </label>
                             <label>
                                 <input type="radio" value="all" name="startDate">全部
                            </label>
                         </div>
					 </div>
				 </form>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
			<div class="ibox-content" >
                <div class="tab-content">
                	<div id="tab-1" class="tab-pane active">
                    	<div class="panel-body">
							<div id="container" style="width:700px;height: 400px"></div>
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
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	    	 data:['最大值','最小值','平均值']
	    },
	    grid: {
	    	left: '3%',
			right: '10%',
			bottom: '3%',
			containLabel: true
	    },
	    xAxis: [
	       {
		    	name : '测试项目',
		        type: 'category',
		        data: [],
	       }
	    ],
	    yAxis : [
	       {
	    	   type : 'value'
	 		}
	    ],
	    series: [
	    	{
	            name:'最大值',
	            type:'bar',
	            data:[]
	        },
	        {
	            name:'最小值',
	            type:'bar',
	            data:[]
	        },
	        {
	            name:'平均值',
	            type:'bar',
	            data:[]
	        }
	    ]
	};
if (option && typeof option === "object") {
	myChart.setOption(option, true);
};
 
$(function(){
	//初始化
	var url="chartValue.do?itemId="+$('#itemId').val();
	seachForm(url);
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
	$('input').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		checkDate();
	});
})
function checkDate(){
	var startDate=$('input[name="startDate"]:checked').val();
	url="chartValue.do?itemId="+$('#itemId').val()+'&startDate='+startDate;
	seachForm(url);
}
//ajax调用查询
function seachForm(url){
	$.ajax({
		type : "post",
		async : false,
		url :url,    
		dataType : "json",       
		success : function(result) {
			refreshData(result.itemNames,result.maxs,result.mins,result.averages);
			refreshItem(result.itemList);
		}
	})
}
//刷新echarts
function refreshData(name,maxs,mins,averages){
	if(!myChart){
		return;
	}
	//更新数据
	var option = myChart.getOption();
	option.xAxis[0].data = name;
	option.series[0].data = maxs; 
	option.series[1].data = mins;
	option.series[2].data = averages;
	myChart.setOption(option);    
}
function refreshItem(itemList){
	$('#itemDiv').html('');
	for(var i=0;i<itemList.length;i++){
		$('#itemDiv').append('<div style="margin:10px 20px;"><label>'+(i+1)+'、'+itemList[i].itemName+'</label></div>');
	}
}
</script>
</html>