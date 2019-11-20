<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	  <%@ include file="../common/echarts.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>统计参数</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<form id="seachForm" action="" class="form-horizontal">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">开始时间(授权):</label></td>
								<td><input id="startDate" placeholder="开始时间"  class="form-control dateISO" name="startDate" type="text" placeholder="YYYY-MM-DD" value="${vo.startDate}" readOnly="readOnly"/></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">结束时间(授权):</label></td>
								<td><input id="endDate" placeholder="结束时间"  class="form-control dateISO" name="endDate" type="text" placeholder="YYYY-MM-DD" value="${vo.endDate}" readOnly="readOnly"/></td>
							</tr>
						</tbody>
					</table>
					<div class="form-group">
						 <div class="col-sm-offset-2">
							 <button class="btn btn-success" type="button" id="submitBut">确定</button>
							 <a href="javascript:;" class="btn btn-white" id="cleanBut" type="button" >清空</a>
						 </div>
					 </div>
				 </form>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>统计视图</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content" >
				<div id="container" style="width:700px;height: 400px"></div>
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
		    title : {
		        text: '角色(文件)权限统计图',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
			toolbox: {
				show: true,
				orient: 'vertical',
				left: 'right',
				top: 'center',
				feature: {
					dataView: {readOnly: true},
					restore: {},
					saveAsImage: {}
				}
			},
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: []
		    },
		    series : [
		        {
		            name: '有权查看文件',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	};

$(function(){
	//初始化
	seachForm();
	//查询
	$('#submitBut').click(function(){
		seachForm();
	})
	
	//清空
	$('#cleanBut').click(function(){
		$('input').each(function(){
			$(this).val('');
		})
		seachForm();
	})

	//打开弹窗
	$('.openLayer').click(function(){
		var inputId = $(this).attr('id'),
		inputValue = $(this).val(),
		inputName = $(this).attr('name'),
		title = $(this).parent().parent().find('label').html();
		title = title.replace(':',' ');
		layer.open({
			title:title,	
			type: 2,
			area: ['600px', '350px'],
			fix: false, //不固定
			maxmin: true,
			content: 'selectSeachValue.do?pageType='+inputName+'&pageValue='+title+'&inputValue='+inputValue,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				iframeWin.fnSelect();
				seachForm();
			}
		});
	})
})

//ajax调用查询
function seachForm(){
	var cateList = new Array(),
		numList = new Array();
	$.ajax({
		type : "post",
		async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "ajaxSeachRoleNum.do",    
		data : $('#seachForm').serialize(),
		dataType : "json",       
		success : function(result) {
			for(var key in result){
				cateList.push(key);
				numList.push(result[key]);
			}
			refreshData(cateList,numList);
		}
	})
}

//刷新echarts
function refreshData(cate,num){
	if(!myChart){
		return;
	}
	//更新数据
	var option = myChart.getOption();
	var dataList = new Array();
	for (var i = 0; i < cate.length ; i++){
		var cateList = {
			value : num[i],
			name : cate[i]
		}
		dataList.push(cateList);
	}
	option.series[0].data = dataList;
	option.legend[0].data = cate;
	myChart.setOption(option);    
}
</script>
</html>