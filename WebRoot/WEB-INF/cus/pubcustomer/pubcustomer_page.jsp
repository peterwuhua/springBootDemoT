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
					<li><a href="gridPage.do">公共池客户</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<%-- <c:if test="${fenpei==true}"> --%> <!-- 经理才有 删除按钮 -->
							<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
						<%-- </c:if> --%>
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								导出 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',0)">全部数据</a></li>
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',1)">选中数据</a></li>
							</ul>
						</div>
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
		var colNames = ['客户名称','区域','行业','客户属性','销售人员','操作'];
		var colModel = [
			  {
				name : 'name',
				index : 'name',
				sortable : false,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'areaPath',//区域
				index : 'areaId',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'industry', //行业
				index : 'industry',
				width : 180,
				fixed:true,
				title : false,
				sortable : false,
			},{
				name : 'cusType', //客户属性
				index : 'cusType',
				width : 180,
				fixed:true,
				title : false,
				sortable : false,
			},{
				name : 'saler', //销售
				index : 'saler',
				width : 120,
				fixed:true,
				title : false,
				sortable : false,
			},{
				name : 'act',
				index : 'act',
				width : 120,
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
				var cl = ids[i];
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+ids[i]+'\');">查看</a>';
				be = '  <a class="btn btn-outline btn-success btn-xs"  href="javascript:;" onclick="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce + be
				});
			}
		}
		
		
		
		function fnShow(id){
			var index = layer.open({
				title:'客户信息',	
				type: 2,
				area: ['800px','85%'],
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
					url : 'update2del.do?ids='+selectIds,
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
			$.extend(postData, {'areaPath':$('#areaPath').val()
				,'industry':$('#industry').val()
				,'cusType':$('#cusType').val(),'name':$('#name').val()});
			jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Used.do'}).trigger("reloadGrid")
		}

		
		function formatCustomer(cellValue,options,rowObject){
			var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
			if(rowObject.taskVo.jj =='是'){
				ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
			}
			return ops;
		}
		
	</script>

</body>
</html>
