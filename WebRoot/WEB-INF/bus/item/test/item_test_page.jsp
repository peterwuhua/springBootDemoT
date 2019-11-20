<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
.red{
    color:red;
}
.blue{
    color:blue;
}
.yellow{
    color:#FFCC00;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>数据录入</a></li>
						<li><strong id="mesg">列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<!-- <a class="btn btn-primary" href="javascript:;" onclick="fnEdit();"><i class="fa fa-pencil-square-o" aria-hidden="true"> 批量录入</i></a> -->
						<span>（注：<font color="red">红色</font>：距离完成时间不足2小时或超时，<font color="blue">蓝色</font>：距离要求完成时间不到12小时）</span>
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
	function checkItem() {
		var ids = getSelectIds();
		var id ="";
		var flag=true;
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var itemId=rData['itemId'];
			if(id==""){
				id=itemId;
			}else if(id!=itemId){
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
			layer.msg('不同项目禁止批量操作');
			
			return false;
		}
		openEditPage('editBatch.do?ids='+selectIds);
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['','','','','检测项目','任务编号','检测性质','样品名称','样品数量','检测截止时间','上报截止日期','操作'];
		var colModel = [ 
			{
				name : 'taskVo.id',
				index : 'task.id',
				search : false,
				hidden:true
			},{
				name : 'color',
				index : 'color',
				search : false,
				hidden:true
			},{
				name : 'itemId',
				index : 'itemId',
				search : false,
				hidden:true
			},{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'itemName',
				index : 'itemName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				formatter:formatTask,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'taskVo.taskType',
				index : 'task.taskType',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'SampName',
				width : 120,
				sortable : false,
				search : false,
				sortable : false
			}, {
				name : 'sampNum',
				index : 'sampNum',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'compDate',
				index : 'compDate',
				width : 130,
				sortable : false,
				search : false
			},{
				name : 'sbDate',
				index : 'sbDate',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 120,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,false);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var de=rData.itemName;
			var be='';
			if(rData.isBack == 'Y'){
				de=rData.itemName+'<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
			}
			be += '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			ce = '<a class="btn btn-outline btn-info btn-xs" title="任务转移" href="javascript:fnEdit4Zy(\'edit4Zy.do?id='+ids[i]+'\')">转移</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : ce+' '+be,
				itemName:de
			});
			jQuery('#'+ids[i]).addClass(rData.color);
		}
	}
	function fnEdit4Zy(url){
		layer.open({
			title:'任务转移',	
			type: 2,
			area: ['600px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content:url,
			btn: ['确定','关闭'], //按钮
			yes: function(index, layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				  iframeWin.submitSave();
			}
		});
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
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
	function deleteOne(id){
		layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({
				url:'${basePath}bus/itemTest/update2delOne.do?id='+id,
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
						layer.msg(data.message, {icon: 0,time: 1000});
						location.reload();
					}
				},
				error:function(ajaxobj){
			    }  
			});
		});
	}
	</script>
</body>
</html>