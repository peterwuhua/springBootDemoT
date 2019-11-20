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
			<div class="ibox-content">
				<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				<input type="hidden" id="selectName" value="${vo.objNames}"/>
				<input type="hidden" id="selectId" value="${vo.objIds}"/>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
    <link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var index = parent.layer.getFrameIndex(window.name); 
		var pId = '${vo.objIds}';
		var setting = {
			check: {enable: true,chkStyle:"radio",radioType: "all",chkboxType: { "Y": "", "N": "" }},
			 data: {simpleData: {enable: true}},
			// 回调函数
			callback : {
				onClick : zTreeOnClick,onCheck: zTreeOnCheck
			} 
		};
	    /*点击文字触发的事件*/
	    function zTreeOnClick(event,treeId,treeNode){
	    	$("#selectId").val(treeNode.id);
	    	$("#selectName").val(treeNode.name);
	    	tree.checkNode(treeNode, !treeNode.checked, true);
	    }
	    /*勾选触发的事件*/
	    function zTreeOnCheck(event,treeId,treeNode){
	    	$("#selectId").val(treeNode.id);
	    	$("#selectName").val(treeNode.name);
	    }
		$(document).ready(function(){
			$.ajax({
			    url: '${basePath}sys/org/treeData.do',
				success: function(data) {  
					initTree('${selectedIds}',data);
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					var node = treeObj.getNodeByParam("id", pId);
					tree.checkNode(node,!node.checked, true);
					if(null!=node.getParentNode()){
						var parentNode = node.getParentNode();
						treeObj.expandNode(parentNode,true);
					}
			    }
			});
		});
	</script>
	<script>
		function fnSelect(){
			var id = $("#selectId").val();
			var name = $("#selectName").val();
			var data={};
			data.id=id;
			data.name=name;
			return data;
		}
	</script>
    </body>
</html>