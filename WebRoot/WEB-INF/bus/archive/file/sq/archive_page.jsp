<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
		 <div class="ibox-title">
			<ol class="breadcrumb">
				<li><a href="gridPage.do">档案</a></li>
				<li><strong id="mesg">列表</strong></li>
			</ol>
		</div>
			<div class="ibox-content">
				<%@ include file="../../../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="dirIds" id="dirIds" value="${vo.dirIds}">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" onclick="fnEdit()">新增</a> 
				</div>
				<div class="jqGrid_wrapper">
                      <table id="listTable"></table>
                      <div id="pager"></div>
               </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../../include/js.jsp"%>
	<%@ include file="../../../../include/grid_page.jsp"%>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>
	<script type="text/javascript">
		var setting = {
				data: {simpleData: {enable: true}},
				// 回调函数
				callback : {
					onClick : function(event, treeId, treeNode, clickFlag) {
						$("#dirIds").val(treeNode.id);
						$("#mesg").text(treeNode.name);
						var postData = $("#listTable").jqGrid("getGridParam", "postData");
						$.extend(postData, {dirIds:treeNode.id});
						jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
					}
				}
			};
			$(document).ready(function(){
				$.ajax({
				    url: '${basePath}bus/archiveType/treeData.do',
					success: function(data) {  
						initTree('${pid}',data);
						var dirIds = '${vo.dirIds}';
						var treeObj = $.fn.zTree.getZTreeObj("tree");
						if(dirIds.length>0){
							var node = treeObj.getNodeByParam("id", dirIds);
							if(null!=node.getParentNode()){
								var parentNode = node.getParentNode();
								treeObj.expandNode(parentNode,true);
							}
						}
				    }
				});
			});
	</script>
	<script>
	function fnEdit(){
		openEditPage('edit.do?dirIds='+$('#dirIds').val());
	}
	$(function() {
		var dirIds = '${vo.dirIds}';
		var url ="";
		if(dirIds.length>0){
		 url = 'gridData.do?dirIds='+dirIds;
		}else{
			url = 'gridData.do';
		}
		var colNames = ['分类','编号','名称','归档人','归档时间','状态','操作'];
		var colModel = [
			{
				name : 'archiveTypeVo.name',
				index : 'archiveType.name',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'code',
				index : 'code',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'title',
				index : 'title',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				sortable : false,
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'time',
				index : 'time',
				sortable : false,
				width :130,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				search : false,
				sortable : false,
				width :60
			},{
		   		name : 'act',
		   		index : 'act',
		   		width : 110,
		   		title : false,
		   		search : false,
		   		sortable : false,
		   	}];
		gridInitMin(url, colNames, colModel,false);
		});

	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			var rowData = jQuery("#listTable").jqGrid("getRowData",cl);
			ce = '<a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">查看</a>';	
			if(rowData.status=='已保存'||rowData.status=='不通过'){
				ce += ' <a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&dirIds='+$('#dirIds').val()+'\')">编辑</a>';	
				ce += ' <a class="btn btn-outline btn-success btn-xs" title="删除" href="javascript:fnDel(\''+cl+'\')">删除</a>';
			}
			
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : ce
			});
		}
	}
	function fnDel(id){
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({
				url : '${basePath}bus/archiveSq/deleteArchive.do?id='+id,
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
	
	</script> 
	
</body>
</html>
