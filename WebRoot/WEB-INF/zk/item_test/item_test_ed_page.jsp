<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>样品检测</a></li>
						<li><strong id="mesg">已提交列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-primary active" href="javascript:;">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="taskVo.ids" id="ids">
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
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>

	<script>
	function fnEdit(){
		location.href='edit.do?id='+$('#id').val();
	}
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['','检测项目','任务编号','检测性质','样品编号','样品类别','实测值','检测日期','要求完成日期','操作'];
		var colModel = [ 
			{
				name : 'progressVo.id',
				index : 'progress.Id',
				hidden:true
			},{
				name : 'itemName',
				index : 'itemName',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.No',
				width : 90,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'task.taskType',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'samplingVo.sampCode',
				index : 'sampling.sampCode',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'samplingVo.sampTypeName',
				index : 'sampling.sampTypeName',
				search : false,
			},{
				name : 'value',
				index : 'value',
				width : 50,
				search : false,
			},{
				name : 'progressVo.date',
				index : 'progress.date',
				width : 100,
				search : false
			},{
				name : 'compDate',
				index : 'compDate',
				width : 80,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.progressVo.id+'\',\''+rData.id+'\')">进度</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	//进度 弹出层
	function fnSelectProcess(id,busId){
		layer.open({
			  type: 2,
			  title: false,
			  closeBtn: 1, //不显示关闭按钮
			  fix: false, //不固定
			  shadeClose: true,
			  anim:5,
			  area: ['1000px','500px'],
			  content: '${basePath}bus/progress/show.do?id='+id+'&busId='+busId
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnShow(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(taskId,no){
		var index = layer.open({
			title:'任务单【'+no+'】',	
			type: 2,
			area: ['60%','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+taskId
		});
	}
	</script>
</body>
</html>