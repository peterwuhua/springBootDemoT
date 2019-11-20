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
						<li><a>车辆使用申请</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="openEditPage('edit.do')">新增</a> 
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
		var url = 'gridData.do';
  		var colNames = ['编号','出差人数','申请人员','申请日期','开始日期','归还日期','状态','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'destRynum',
				index : 'destRynum',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'date',
				index : 'date',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'startTime',
				index : 'startTime',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				width : 100,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 110,
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
				var be='';
				if(rData.status=='已保存'){
					be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>'
						 +' <a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:deleteOne(\''+ids[i]+'\')">删除</a>';
				}else{
					be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.no+'\')">查看</a>';
				}
				jQuery("#listTable").jqGrid('setRowData', rData.id, {
					act : be
				});
			}
		}
		/**
		 * 删除
		 */
		function deleteOne(id){
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({
					url : '${basePath}init/carUse/deleteOne.do?id='+id,
					async:false,
					success : function(data) {
						if(data.status=='success'){
							jqgridReload();
				        }
						layer.msg(data.message, {icon: 0,time: 3000});
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 1000});
			    	}
				});
			});
		}
		function fnShow(id,no){
			var index = layer.open({
				title:'查看【'+no+'】',	
				type: 2,
				area: ['1000px','400px'],
				fix: false, //不固定
				maxmin: true,
				content: '/init/carUse/show.do?id='+id
			});
		}
	
		
	</script>

</body>
</html>
