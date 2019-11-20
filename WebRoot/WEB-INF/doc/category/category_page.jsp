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
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>文件夹树</h5>
				</div>
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
				<li><a href="gridPage.do">文件夹</a></li>
				<li><strong id="mesg">列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="pid" id="pid">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()">新增</a>${vo.pid}
					<c:choose>
						<c:when test="${vo.pid!='0' && vo.pid!=null  && vo.pid!=''}">
							<a id="deleteBtn" class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
						</c:when>
						<c:otherwise>
							<a id="deleteBtn" style="display: none;" class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
						</c:otherwise>
					</c:choose>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('doc-category-export.xls','文件夹列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('doc-category-export.xls','文件夹列表.xls',1)" >选中数据</a>
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
						$("#pid").val(treeNode.id);
						$("#mesg").text(treeNode.name);
						if(treeNode.level!=0){
							$('#deleteBtn').show();
						}else{
							$('#deleteBtn').hide();
						}
						var postData = $("#listTable").jqGrid("getGridParam", "postData");
						$.extend(postData, {pid:treeNode.id});
						jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
					}
				}
			};
			$(document).ready(function(){
				$.ajax({
				    url: 'treeData.do',
					success: function(data) {  
						initTree('${selectedIds}',data);
						var pid = '${vo.pid}';
						var treeObj = $.fn.zTree.getZTreeObj("tree");
						if(pid.length>0){
							var node = treeObj.getNodeByParam("id", pid);
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
		openEditPage('edit.do?pid='+$('#pid').val());
	}
	$(function() {
		var pid = '${vo.pid}';
		var url ="";
		if(pid.length>0){
		 url = 'gridData.do?pid='+pid;
		}else{
			url = 'gridData.do';
		}
		var editurl='gridEdit.do';
		var colNames = ['编码','名称','创建人','创建时间','说明','操作'];
		var colModel = [ 
		   			 {
		   				name : 'code',
		   				index : 'code',
		   				width : 100,
		   				sortable : false,
		   				searchoptions : {
		   					sopt : ['cn']
		   				}
		   			},{
		   				name : 'name',
		   				index : 'name',
		   				width : 200,
		   				sortable : false,
		   				searchoptions : {
		   					sopt : ['cn']
		   				}
		   			}, {
						name : 'lastUpdUser',
						index : 'lastUpdUser',
						width : 100,
						fixed:true,
						sortable : false,
						search : false
					}, {
						name : 'time',
						index : 'time',
						width : 130,
						fixed:true,
						sortable : false,
						search : false
					},{
		   				name : 'describtion',
		   				index : 'describtion',
		   				sortable : false,
						search : false
		   			},{
		   				name : 'act',
		   				index : 'act',
		   				width : 70,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&pid='+$('#pid').val()+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
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
			layer.confirm('点击确认将删除该文件夹下所有的文件及其授权信息,确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(selectIds);
				$("#listForm").attr("action","update2del.do");
				$("#listForm").submit();
			});
		}
	</script> 
    </body>
</html>