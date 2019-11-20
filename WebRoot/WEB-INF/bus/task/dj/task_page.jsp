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
						<li><a>任务登记</a></li>
						<li><strong>列表</strong></li>
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
						<a class="btn btn-primary" href="javascript:void(0);" onclick="editOpen('环境')"><i class="fa fa-plus" aria-hidden="true"></i> 环境</a> 
						<a class="btn btn-primary" href="javascript:void(0);" onclick="editOpen('职业卫生')"><i class="fa fa-plus" aria-hidden="true"></i> 职业卫生</a> 
						<a class="btn btn-primary" href="javascript:void(0);" onclick="editOpen('公共卫生')"><i class="fa fa-plus" aria-hidden="true"></i> 公共卫生</a>
						<a class="btn btn-default" href="javascript:;"><i class="fa fa-plus" aria-hidden="true"></i> 食品</a> 
						<a class="btn btn-default" href="javascript:;"><i class="fa fa-plus" aria-hidden="true"></i> 安全</a> 
						<a class="btn btn-primary" href="javascript:void(0);" onclick="showOldWin()"><i class="fa fa-plus" aria-hidden="true"></i> 复制</a>  
						<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a> 
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
	function editOpen(type){
		openEditPage('edit.do?sampType='+encodeURI(type));
	}
	$(function() {
		var url = 'gridData.do';
		var colNames = ['','任务编号','受检单位','样品名称','样品来源','检测类型','受理日期','受理人','操作'];
		var colModel = [ 
			{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'no',
				index : 'no',
				formatter:formatNo,
				width : 150,
				fixed:true,
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
				name : 'source',
				index : 'source',
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
				name : 'date',
				index : 'date',
				width : 130,
				fixed:true,
				sortable : false,
				search:false
			},{
				name : 'userName',
				index : 'userName',
				width : 100,
				fixed:true,
				sortable : false,
				search:false
			},{
				name : 'act',
				index : 'act',
				width : 110,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,true);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>'
				 +' <a class="btn btn-outline btn-success btn-xs" title="查看"  href="javascript:fnShow(\''+ids[i]+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function jqgridDelete() {
		var selectIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		if (selectIds.length < 1) {
			layer.msg('请选择要删除的记录', {
				icon : 0,
				time : 3000
			});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","delete.do");
			$("#listForm").submit();
		});
	}
	function showOldWin(){
		parent.layer.open({
			title:'历史记录',	
			type: 2,
			area: ['1000px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/task/old.do',
			btn: ['复制','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
				iframeWin.contentWindow.fnSelect(index);
			},
			end: function () {
                location.reload();
            }
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.isBack =='Y'){
			ops+='<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
		}
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow(id){
		parent.layer.open({
			title:'委托信息',	
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