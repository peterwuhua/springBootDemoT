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
									<td class="active"><label class="pull-right">客户经理:</label></td>
									<td><input id="saler" placeholder="客户经理"  class="form-control openLayer" name="saler" type="text" value="${vo.saler}" readOnly="readOnly"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">客户来源:</label></td>
									<td><input id="souce" placeholder="客户来源"  class="form-control openLayer" name="souce" type="text" value="${vo.souce}" readOnly="readOnly"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">客户行业:</label></td>
									<td><input id="industry" placeholder="客户行业"  class="form-control openLayer" name="industry" type="text" value="${vo.industry}" readOnly="readOnly"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">类型:</label></td>
									<td><input id="customerType" placeholder="类型"  class="form-control openLayer" name="customerType" type="text" value="${vo.customerType}" readOnly="readOnly"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">客户建立时间:</label></td>
									<td><input id="buildDate" placeholder="客户建立时间"  class="form-control openLayer" name="buildDate" type="text" value="${vo.buildDate}" readOnly="readOnly"/></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">区域:</label></td>
									<td><input id="areaPath" placeholder="区域"  class="form-control openArea" name="areaPath" type="text" value="${vo.areaPath}" readOnly="readOnly"/></td>
								</tr>
							</tbody>
					</table>
					 <div class="form-group">
						<div class="col-sm-offset-2">
<!-- 							 <button class="btn btn-success" type="button" id="submitBut">确定</button> -->
<!-- 							 <a href="javascript:;" class="btn btn-white" id="cleanBut" type="button" >清空</a> -->
							 <button class="btn btn-success" type="button" id="cleanBut">清空</button>
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
				<div class="pull-right">
	                <div class="panel-options btn-group" style="padding: 0px 0px 0px;">
		                <div class="panel-heading" style="padding: 0px 0px 0px;">
							<ul class="nav nav-tabs" style="border-bottom:0px;padding: 0px 0px 0px;">
								<!-- <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">图形</a></li>
								<li><a data-toggle="tab" href="#tab-2" aria-expanded="false">数据</a></li> -->
								<a type="button" class="btn btn-xs btn-success active" href="#tab-1" data-toggle="tab" aria-expanded="true" id="tab1">图形信息</a>
	                			<a type="button" class="btn btn-xs btn-outline btn-default" href="#tab-2" data-toggle="tab" aria-expanded="false" id="tab2">数据信息</a>
							</ul>
						</div>
					</div>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
						<a class="close-link"> <i class="fa fa-times"></i></a>
					</div>
				</div>
			</div>
			<div class="ibox-content" >
                <div class="tab-content">
                	<div id="tab-1" class="tab-pane active">
                    	<div class="panel-body">
							<div id="container" style="width:700px;height: 400px"></div>
						</div>
					</div>
					<div id="tab-2" class="tab-pane">
                   		<div class="panel-body">
                             <form action="" method="post" id="listForm">
								<input type="hidden" name="ids" id="ids">
								<input type="hidden" name="souce" id="newSouce" value="${vo.souce}">
								<input type="hidden" name="industry" id="newIndustry" value="${vo.industry}">
								<input type="hidden" name="customerType" id="newCustomerType" value="${vo.customerType}">
								<input type="hidden" name="buildDate" id="newBuildDate" value="${vo.buildDate}">
								<input type="hidden" name="areaPath" id="newAreaPath" value="${vo.areaPath}">
								<input type="hidden" name="disPage" id="disPage" value="grade">
								<div class="col-sm-9">
									<button class="btn btn-info" href="javascript:;" onclick="jqgridExportOther('exportOther.do','bi-customer-grade-export.xls','客户比例统计.xls',0)">导出</button>
								</div>
								<div class="jqGrid_wrapper">
									<table id="listTable"></table>
								</div>
							</form>
                        </div>
                    </div>
				</div>
			  </div>	
		</div>
	</div>
    </body>
<%@ include file="../../include/js.jsp"%>
<%@ include file="../../include/grid_page.jsp"%>
 <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
    title : {
        text: '客户比例',
        subtext: '',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    	orient: 'vertical',
        x: 'left',
        data:['VIP3','VIP2','VIP1','协议','其它']
    },
    toolbox: {
        show : true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: true},
            magicType : {
                show: true,
                type: ['pie', 'funnel']
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'会员客户',
            type:'pie',
            roseType : 'radius',
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            lableLine: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data:[
                {value:100, name:'VIP1'},
                {value:80, name:'VIP2'},
                {value:50, name:'VIP3'},
                {value:305, name:'协议'},
                {value:520, name:'其它'}
            ]
        }
    ]
};
;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

$(function(){
	//初始化
	seachForm();
	//查询
	$('#submitBut').click(function(){
		seachForm();
	})
	
	//清空
	$('#cleanBut').click(function(){
		var disPage = $('#disPage').val();
		$('input').each(function(){
			$(this).val('');
		})
		$('#disPage').val(disPage);
		seachForm();
	})
	$('#tab1').click(function(){
		$('#tab2').removeClass("btn-success active");
		$('#tab2').addClass("btn-white");
		$('#tab1').removeClass("btn-white");
		$('#tab1').addClass("btn-success active");
	})
	$('#tab2').click(function(){
		$('#tab1').removeClass("btn-success active");
		$('#tab1').addClass("btn-white");
		$('#tab2').removeClass("btn-white");
		$('#tab2').addClass("btn-success active");
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
	
	//打开区域弹窗
	$('.openArea').click(function(){
		var title = $(this).parent().parent().find('label').html();
		title = title.replace(':',' ');
		layer.open({
			title:title,	
			type: 2,
			area: ['600px', '350px'],
			fix: false, //不固定
			maxmin: true,
			content: 'selectSeachArea.do',
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
	$('#newSouce').val($('#souce').val());
	$('#newIndustry').val($('#industry').val());
	$('#newCustomerType').val($('#customerType').val());
	$('#newBuildDate').val($('#buildDate').val());
	$('#newAreaPath').val($('#areaPath').val());
	var gradeList = new Array(),
		numList = new Array();
	$.ajax({
		type : "post",
		async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "ajaxSeachGrade.do",    
		data : $('#seachForm').serialize(),
		dataType : "json",       
		success : function(result) {
			var jsonStr ="[";
			for(var key in result){
				gradeList.push(key);
				numList.push(result[key]);
				jsonStr += "{name:'" + key + "',value:'" + result[key] + "'},";
			}
			jsonStr=jsonStr.substring(0,jsonStr.length-1);
			jsonStr += "]";
			refreshData(gradeList,numList);
			// 数据列表
			dataTable(jsonStr);
			jQuery("#listTable").jqGrid('setGridParam',{postData:jsonStr}).trigger("reloadGrid");
		}
	})
}

//刷新echarts
function refreshData(grade,num){
	if(!myChart){
		return;
	}
	//更新数据
	var option = myChart.getOption();
	var dataList = new Array();
	for (var i = 0; i < grade.length ; i++){
		var souceList = {
			value : num[i],
			name : grade[i]
		}
		dataList.push(souceList);
	}
	option.series[0].data = dataList;
	option.legend[0].data = grade;
	myChart.setOption(option);    
}

function dataTable(jsonStr){
    var str = eval(jsonStr);
    $("#listTable").jqGrid('clearGridData');  //清空表格
	$.jgrid.defaults.styleUI = "Bootstrap";
	$.jgrid.gridUnload("listTable");
	$("#listTable").jqGrid({ 
		data: str,
		datatype: "local", 
		rowNum: 10, 
		rowList: [10,20,30], 
		colNames:['操作','名称','数量'], 
		colModel:[
		          {
					name : 'act',
					index : 'act',
					width : 80,
					title : false,
					search : false,
					frozen : true,
					sortable : false
				},{
	        	   name:'name',
	        	   index:'name', 
	        	   width:525,
	        	   align: 'center',
	        	   editable : true,
	        	   searchoptions : {
	        		   sopt : ['cn']
	           		}
		        },{
		    	   name:'value',
		    	   index:'value',
		    	   width:525,
		    	   align: 'center',
		    	   editable : true,
		    	   searchoptions : {
		    		   sopt : ['cn']
		       		}
		       }],		
	    sortname : 'sort',
		sortorder : "asc",
		viewrecords : true,
		height:'auto',
		recordpos : 'right',
		jsonReader : {
			root : 'datas'
		},
		shrinkToFit:false,    
	    autoScroll: true,  
	    search:true,
		editurl:null,
		multiselect : false,
		gridComplete : gridComplete,
		loadComplete : loadComplete
	});
}

function loadComplete(data) {
	var ids = jQuery("#listTable").jqGrid('getDataIDs');
	for (var i = 0; i < ids.length; i++) {
		var cl = ids[i];
		var rowData = data.rows[i];
		be = '<a class="btn btn-outline btn-success btn-xs" title="查看" onclick="seeData(\''+rowData.name+'\');">查看</a>';
		jQuery("#listTable").jqGrid('setRowData', ids[i], {
			act : be
		});
	}
}

function seeData(grade){
	layer.open({
		type: 2,
		area: ['800px', '600px'],
		fix: false, //不固定
		maxmin: true,
		content: 'seeData.do?grade='+grade+'&customerType='+$('#customerType').val()+'&saler='+$('#saler').val()+'&areaPath='+$('#areaPath').val()
					+'&buildDate='+$('#buildDate').val()+'&industry='+$('#industry').val()+'&souce='+$('#souce').val()
	});
}
</script>
</html>