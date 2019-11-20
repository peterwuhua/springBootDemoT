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
				<ol class="breadcrumb">
					<li><a href="gridPage.do">我的客户</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="form-group col-md-8">
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								导出 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',0)">全部数据</a></li>
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',1)">选中数据</a></li>
							</ul>
						</div>
						<%-- <c:if test="${vo.fstatus == '0'||vo.fstatus == '-1'}">
							<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
						</c:if> --%>
						
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
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['','客户名称','区域','行业','客户属性','销售人员','客户类型','跟进状态','操作'];
		var colModel = [
			{
				name : 'fstatus',
				index : 'fstatus',
				hidden:true,
				search : false
			},{
				name : 'name',
				index : 'name', //客户名称
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},
			{
				name : 'areaPath',  
				index : 'areaPath', //区域
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'industry',  
				index : 'industry', //行业
				sortable : false,
				width : 160,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'cusType',  
				index : 'cusType', //客户属性
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',
				index : 'saler',  //销售
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},
			{
				name : 'cusCates', //客户类型
				index : 'cusCates',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'gjStatus',
				index : 'gjStatus',  //跟进状态
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 130,
				fixed:false,
				title : false,
				search : false,
				sortable : false,
			}];
			gridInitMin(url, colNames, colModel,true);
		});
	
	
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				var rowData = $("#listTable").jqGrid("getRowData",cl);//行数据
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+ids[i]+'\');">查看</a>';
				if (rowData.fstatus == '0'||rowData.fstatus == '-1')
				{
					ce += ' <a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
					ce += ' <a class="btn btn-outline btn-success btn-xs" title="删除" href="javascript:fnDel(\''+cl+'\')">删除</a>';
				}
				
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce 
				});
			}
		}
		
		function fnDel(id){
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(id);
				$("#listForm").attr("action","delete.do");
				$("#listForm").submit();
			});
		}
		function fnShow(id){
			var index = parent.layer.open({
				title:'客户信息',	
				type: 2,
				area: ['1200px','90%'],
				fix: false, //不固定
				maxmin: true,
				content: '/cus/customer/show.do?id='+id
			});
		}
		
		
		
		
		/**
		 * 删除
		 */
		function jqgridDelete(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({
					url : 'update2delete.do?ids='+selectIds,
					datatype : "json",
					success : function(data) {
						if(data=='false'){
							layer.alert("该客户不能被删除，请先删除关联数据！");
						}else{
							window.location.href="gridPage.do";
						}
					}
				});
				
			});
		}
		
		
		function fnSearch(){
			var postData = $("#listTable").jqGrid("getGridParam", "postData");
			$.extend(postData, {'gjStatus':$('#gjStatus').val(),'areaPath':$('#areaPath').val()
				,'cusCates':$('#cusCates').val(),'industry':$('#industry').val()
				,'cusType':$('#cusType').val(),'name':$('#name').val()});
			jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Used.do'}).trigger("reloadGrid")
		}
		
	</script>

</body>
</html>
