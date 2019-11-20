<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>文件夹树</h5>
			</div>
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
				<li><a href="gridPage.do">文件</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="dirIds" id="dirIds" value="${vo.dirIds}">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" onclick="fnEdit()">新增</a> 
					<!-- <a class="btn btn-primary" href="import.do">导入</a> -->
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('doc-document-export.xls','文件列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('doc-document-export.xls','文件列表.xls',1)" >选中数据</a>
                              </li>
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
				    url: '${basePath}doc/category/treeData.do',
					success: function(data) {  
						initTree('${selectedIds}',data);
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
		var colNames = ['名称','大小','类型','标记','上传人','上传时间','操作'];
		var colModel = [
			{
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'size',
				index : 'size',
				sortable : false,
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'type',
				index : 'type',
				sortable : false,
				width : 70,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sign',
				index : 'sign',
				sortable : false,
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'lastUpdUser',
				index : 'lastUpdUser',
				sortable : false,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'time',
				index : 'time',
				sortable : false,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
		   		name : 'act',
		   		index : 'act',
		   		width : 140,
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
			ce = '<a class="btn btn-outline btn-success btn-xs" title="更新" href="javascript:openEditPage(\'updateFile.do?id='+ids[i]+'\')">更新</a>';	
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&dirIds='+$('#dirIds').val()+'\')">修改</a>';
			de = '<a class="btn btn-outline btn-success btn-xs" title="历史" href="javascript:openEditPage(\'fileHistory.do?id='+ids[i]+'&dirIds='+$('#dirIds').val()+'\')">历史</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+" "+ce+" "+de
			});
		}
	}
	</script> 
	
</body>
</html>
