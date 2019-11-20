<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>采样准备</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					</div>
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>

	<script>
	 
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['任务编号','受检单位','样品名称','检测类型','准备日期','要求完成日期','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				formatter:formatTask,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'zbDate',
				index : 'zbDate',
				width : 140,
				fixed:true,
				search:false
			},{
				name : 'finishDate',
				index : 'finishDate',
				width : 100,
				fixed:true,
				search:false
			},{
				name : 'act',
				index : 'act',
				width : 270,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,false);
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+rData.id+'\')">查看</a>';
			fe = '<a class="btn btn-outline btn-info btn-xs" title="打印" href="javascript:fnPrint4Sop(\''+ids[i]+'\')">采样规范</a>';
			de = '<a class="btn btn-outline btn-info btn-xs" title="打印" href="javascript:fnPrint4Ct(\''+ids[i]+'\')">耗材清单</a>';
			ee = '<a class="btn btn-outline btn-info btn-xs" title="打印" href="javascript:fnPrint4Tm(\''+ids[i]+'\')">条码</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+de+' '+ee+' '+fe+' '+ce
			});
		}
	}
	 
	//进度 弹出层
	function fnSelectProcess(id,status){
		layer.open({
			  type: 2,
			  title: false,
			  closeBtn: 1, //不显示关闭按钮
			  fix: false, //不固定
			  shadeClose: true,
			  anim:5,
			  area: ['1000px','500px'],
			  content: '${basePath}bus/progress/show.do?busId='+id+'&busType='+encodeURI(status)
		});
	}
	function fnShow(id){
		var index = layer.open({
			title:'采样准备详情',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/taskZb/show.do?id='+id
		});
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	//打印
	function fnPrint4Tm(id){
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['900px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskJj/print.do?id='+id
		});
	}
	 
	function fnPrint4Sop(id){
		parent.layer.open({
			title:'采样规范打印',	
			type: 2,
			 area: ['80%', '90%'],
			fix: false, //不固定
			maxmin: true,
			content:'${basePath}bus/taskZb/printSop.do?id='+id
		});
	}
	function fnPrint4Ct(id){
		var	url='${basePath}bus/taskZb/printCt.do?id='+id;
		parent.layer.open({
			title:'耗材清单',	
			type: 2,
			 area: ['800px', '90%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	</script>
</body>
</html>