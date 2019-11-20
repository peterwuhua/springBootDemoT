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
					<input type="hidden" id="id" value="${vo.id}"/>
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
		var pId = '${vo.id}';
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
	    	if(!treeNode.checked){
	    		$("#id").val(treeNode.id);
	    	}else{
	    		$("#id").val(null);
	    	}
	    	tree.checkNode(treeNode, !treeNode.checked, true);
	    }
	    /*勾选触发的事件*/
	    function zTreeOnCheck(event,treeId,treeNode){
	    	if(treeNode.checked){
	    		$("#id").val(treeNode.id);
	    	}else{
	    		$("#id").val(null);
	    	}
	    }
		$(document).ready(function(){
			$.ajax({
			    url: 'treeData4All.do',
				success: function(data) {  
					initTree('${vo.id}',data);
			    }
			});
		});
	</script>
	<script>
		function fnSelect(){
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes=treeObj.getCheckedNodes(true);
			var data={};
			data.id=nodes[0].id;
			data.name=nodes[0].name;
			return data;
		}
	</script>
    </body>
</html>