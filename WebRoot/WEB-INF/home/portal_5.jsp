<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>H+ 后台主题UI框架 - 百度ECHarts</title>
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${basePath}h/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${basePath}h/css/animate.css" rel="stylesheet">
    <link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
<style type="text/css">
.col-sm-6{
	padding: 0px 1px;
}
.col-sm-12{
	padding: 0px 1px;
}
</style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>监测项目趋势</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="echarts" id="item-line-chart"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>任务统计</h5>
                        <div class="ibox-tools">
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="echarts" id="task_chart"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 全局js -->
    <script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <!-- ECharts -->
    <script src="${basePath}h/js/plugins/echarts/echarts-all.js"></script>
	
    <!-- 自定义js -->
    <script src="${basePath}h/js/content.js?v=1.0.0"></script>
 
 <script type="text/javascript">
 //任务统计
 var taskChart = echarts.init(document.getElementById("task_chart"));
 var taskoption = {
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
     legend: {
         data:['完成数','总数']
     },
     grid:{
         x:20,
         x2:20,
         y2:24,
         borderWidth:'0'
     },
     calculable : true,
     xAxis: [
	       {
		        type: 'category',
		        splitLine:{  
                    show:false  
                }, 
		        data: ['委托检测','监督抽查','抽样检测','例行'],
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
	            name:'总数',
	            type:'bar',
	            data:[0,0,0,0]
	        },
	        {
	            name:'完成数',
	            type:'bar',
	            data:[0,0,0,0]
	        }
	    ]
 };
 if (taskoption && typeof taskoption === "object") {
 	taskChart.setOption(taskoption, true);
 };
 $(function(){
	 seachForm_rw();
})
//ajax调用查询
function seachForm_rw(){
	$.ajax({
		type : "post",
		url :'${basePath}bi/task/chartPie.do',    
		dataType : "json",       
		success : function(result) {
			taskoption.xAxis[0].data = result.itemNames;
			taskoption.series[0].data = result.total; 
			taskoption.series[1].data = result.finish; 
			taskChart.setOption(taskoption);    
		}
	})
}
</script>
<script type="text/javascript">
var itemChart = echarts.init(document.getElementById("item-line-chart"));
var itemoption = {
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['去年','今年']
    },
    grid:{
        x:40,
        x2:40,
        y2:24,
        borderWidth:'0'
    },
    calculable : true,
    xAxis : [
        {
        	name : '月份',
            type : 'category',
            boundaryGap : false,
            splitLine:{  
                show:false  
            }, 
            data : ['']
        }
    ],
    yAxis : [
        {
        	name : '数量',
            type : 'value'
        }
    ],
    series : [
        {
            name:'去年',
            type:'line',
            data:[''],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'今年',
            type:'line',
            data:[''],
            markPoint : {
            	data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        }
    ]
};
if (itemoption && typeof itemoption === "object") {
 	itemChart.setOption(itemoption); 
};
$(function(){
	seachForm_xm();
})
//ajax调用查询
function seachForm_xm(){
	$.ajax({
		type : "post",
		url :'${basePath}bi/taskItem/chartNum.do',    
		dataType : "json",       
		success : function(result) {
			console.log(result)
			itemoption.xAxis[0].data = result.month;
			itemoption.series[0].data = result.valued; 
			itemoption.series[1].data = result.value; 
			itemChart.setOption(itemoption);    
		}
	})
}
</script>
 
</body>

</html>



