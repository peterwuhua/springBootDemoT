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
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-primary active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary dropdown-toggle" href="javascript:;" onclick="fnEdit('edit.do');"><i class="fa fa-pencil-square-o" aria-hidden="true"> 批量录入</i></a>
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
	function checkItem() {
		var ids = getSelectIds();
		var id ="";
		var flag=true;
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			if(id==""){
				id=rData.itemId;
			}else if(id!=rData.itemId){
				flag=false;
				break;
			}
		}
		return flag;
	}
	function fnEdit(url){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要录入的记录', {icon: 0,time: 3000});
			return false;
		}else{
			$("#ids").val(selectIds);
		}
		if(!checkItem()){
			layer.msg('不同项目禁止批量操作', {icon: 0,time: 3000});
			return false;
		}
		openEditPage('edit.do?ids='+selectIds);
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['','','检测项目','任务编号','','样品编号','样品数量','分配日期','要求完成日期','操作'];
		var colModel = [ 
			{
				name : 'checkMsg',
				index : 'checkMsg',
				hidden:true
			},{
				name : 'itemId',
				index : 'itemId',
				hidden:true
			},{
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				width : 90,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			}, {
				name : 'samplingVo.sampCode',
				index : 'sampling.sampCode',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.sampNum',
				index : 'taskVoSampNum',
				width : 50,
				search : false,
				sortable : false
			},{
				name : 'lastUpdTime',
				index : 'lastUpdTime',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'compDate',
				index : 'compDate',
				sortable : false,
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
		gridInitMin(url, colNames, colModel,true);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			de=rData.itemName;
			if(rData.isBack == 1){
				de='<img alt="退回" title="'+rData.checkMsg+'" src="/static/img/tuihui.png" style="height:22px;width:25px;">'+rData.itemName;
			}
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?ids='+ids[i]+'\')">编辑</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be,
				itemName:de
			});
		}
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