var code;
var tree;
function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("tree"),
	type = { "Y":py + sy, "N":pn + sn};
	showCode('setting.check.chkboxType = { "Y" : "s", "N" : "s" };');
}
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}

function initTree(selectedIds,data){
	//初始化树结构
	tree = $.fn.zTree.init($("#tree"), setting, data);
	var ids = selectedIds.split(",");
	for(var i=0; i<ids.length; i++) {
		var node = tree.getNodeByParam("id", ids[i]);
		try{tree.checkNode(node, true, false);}catch(e){}
	}
}

function setSelect(target){
	var ids = ""; 
	var nodes = tree.getCheckedNodes(true);
	for(var i=0; i<nodes.length; i++) {
		if(i>0)ids +=",";
		ids +=nodes[i].id;
	}
	$("#"+target).val(ids);
} 

function reloadTree(treeUrl){
	tree = $.fn.zTree.getZTreeObj("tree");
	var nodes = tree.getSelectedNodes();
	$.ajax({
			    url: treeUrl,
				success: function(data) {
					initTree('',data);
					tree.selectNode(nodes[0],false);
					if(nodes.length>0){
						$.each(nodes,function(i,obj){
						var node = tree.getNodeByParam("id", obj.id);
						if(null != node){
							tree.expandNode(node,true);
						}
						});
					}
			    }
		});
}
