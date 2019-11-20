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
		<div class="ibox float-e-margins ">
			<div class="ibox-title">
				<h5>部门树</h5>
			</div>
			<div class="ibox-content treeboxheight">
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
					<li><a>账户</a></li>
					<li><strong id="mesg">列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids"> 
					<input type="hidden" name="accountIsUse" id="accountIsUse"> 
					<input type="hidden" name="orgId" id="orgId" value="${vo.orgId }">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()">新增</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
						<c:if test="${isEdit}">
							<a class="btn btn-info" href="javascript:;" onclick="fnSyn()">同步账户至IM</a>
						</c:if>
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								账户状态 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="fnAccountIsUse(1);">启用</a></li>
								<li><a href="javascript:;" onclick="fnAccountIsUse(0);">停用</a></li>
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
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>

	<script type="text/javascript">
		var setting = {
			data: {simpleData: {enable: true}},
			// 回调函数
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#orgId").val(treeNode.id);
					$("#mesg").text(treeNode.name);
					var postData = $("#listTable").jqGrid("getGridParam", "postData");
					$.extend(postData, {orgId:treeNode.id});
					$("#orgId").val(treeNode.id);
					jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
				}
			}
		};
		$(document).ready(function(){
			$.ajax({
			    url: '${basePath}sys/org/treeData.do',
				success: function(data) {
					var orgId = '${vo.orgId}';
					initTree('${selectedIds}',data);
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					if(orgId.length>0){
						var node = treeObj.getNodeByParam("id", orgId);
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
		openEditPage('edit.do?orgId='+$('#orgId').val());
	}
	$(function() {
		var orgId = '${vo.orgId}';
		var url ="";
		if(orgId.length>0){
		 	url = 'gridData.do?orgId='+orgId;
		}else{
			url = 'gridData.do';
		}
		var editurl='gridEdit.do';
		var colNames = ['部门','用户名','登录名','账户状态','排序','操作'];
		var colModel = [ 
			{
				name : 'orgVo.name',
				index : 'org.name',
				sortable : false,
				search : false
			}, {
				name : 'userVo.name',
				index : 'user.name',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'loginName',
				index : 'loginName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'isUse',
				index : 'isUse',
				sortable : false,
				stype : 'select',
				formatter : cellvalue,
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['eq'],
					value:{'':'全部','1':'启用','0':'停用'}
				}
			},{
				name : 'sort',
				index : 'sort',
				width : 50,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitAuto(url, colNames, colModel, '','20','#pager',true)
		});

		function gridComplete() {
			var orgId =  $("#orgId").val();
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&orgId='+orgId+'\')">修改</a>';
				//ce = '<a class="btn btn-primary btn-xs" title="详情" href="showPerm.do?id='+ids[i]+'&orgId='+orgId+'">查看权限</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		function fnAccountIsUse(status){
			var selectIds = getSelectStrIds();
			if(selectIds.length<1){
				layer.msg('请选择要启用/停用的账户', {icon: 0,time: 3000});
				return false;
			}else{
				$("#ids").val(selectIds);
			}
			$("#accountIsUse").val(status);
			var uri = "updateAccountIsUse.do";
			$("#listForm").attr("action",uri);
			$("#listForm").submit();
			
		}
		function cellvalue(cellValue) {
		    if (cellValue == '1') {
		        return "启用"
		    }
		    else if(cellValue =='0') {
		        return "停用";
		    }else{
		    	return '';
		    }
	    }
		function fnSyn(){
			$.ajax({ 
				url:"synAccount.do",
				async:false,
				success: function(data){
					if("success"==data.status){
						layer.msg(data.message, {icon: 0,time: 3000});
					}else{
						layer.msg(data.message, {icon: 0,time: 3000});
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }
			});
		}
	</script>
</body>
</html>