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
		var sampTypeId = '${vo.ids}';
		var setting = {
			check: {enable: true,chkStyle:"checkbox",chkboxType: { "Y": "", "N": "" }},
			data: {simpleData: {enable: true}},
			callback : {
				onClick : zTreeOnClick
			} 
		};
		/*点击文字触发的事件*/
	    function zTreeOnClick(event,treeId,treeNode){
	    	tree.checkNode(treeNode, !treeNode.checked, true);
	    }
		$(document).ready(function(){
			var name='${vo.name}';//大类 环境 职业卫生  公共卫生
			$.ajax({
			    url: '${basePath}init/sampType/treeData4NoCheck.do?name='+encodeURI(name),
				success: function(data) {  
					initTree(sampTypeId,data);
			    }
			});
		});
	</script>
	<script>
		function fnSelect(){
		 	var treeObj = $.fn.zTree.getZTreeObj("tree");
	        var nodes = treeObj.getCheckedNodes(true);
	        var ids = [];
	        var names = [];
	        for (var i = 0; i < nodes.length; i++) {
	        	if(nodes[i].id!=''&&nodes[i].id!='0'){
	        		ids.push(nodes[i].id);
		        	names.push(nodes[i].name);
	        	}
	        }
	        var data={};
	        data.id=ids;
	        data.name=names;
			return data;
		}
	</script>
    </body>
</html>