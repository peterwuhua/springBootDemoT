<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>数据复核</a></li>
						<li><strong>已提交列表</strong></li>
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>

	<script>
	function fnEdit(){
		location.href='edit.do?id='+$('#id').val();
	}
	$(function() {
		var url ="";
		url = 'gridDatad.do';
		var colNames = ['检测项目','任务编号','检测性质','样品数量','样品名称','审核日期','上报日期','操作'];
		var colModel = [ 
			{
				name : 'itemName',
				index : 'itemName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.No',
				formatter:formatTask,
				width : 120,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'task.TaskType',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampNum',
				index : 'sampNum',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampName',
				index : 'sampName',
				search : false,
			},{
				name : 'checkTime',
				index : 'checkTime',
				width : 130,
				sortable : false,
				search : false
			},{
				name : 'sbDate',
				index : 'sbDate',
				width : 100,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 110,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitAuto(url, colNames, colModel,'',20,'#pager',false)
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
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
			title:'审核详情',	
			type: 2,
			area: ['1000px','500px'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/itemCheck/show.do?id='+id
		});
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
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
	</script>
</body>
</html>