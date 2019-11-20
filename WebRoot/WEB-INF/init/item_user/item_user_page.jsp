<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">测试参数</a></li>
						<li><strong>关联检测人</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
				<form action="" method="post" id="listForm">
					<input id="itemId" name="itemId" value="${vo.itemId}" type="hidden" />
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:fnEdit('')">新增</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
						<a class="btn btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回上一层</a>
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
</body>
<script type="text/javascript">
	function fnEdit(id){
		layer.open({
			title:'新增/修改',	
			type: 2,
			 area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/itemUser/edit.do?itemId=${vo.itemId}&id='+id,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.submitFn();
			}
		});
	}
	$(function() {
		var url = 'gridData.do?itemId=${vo.itemId}';
		var editurl='gridEdit.do';
		var colNames = ['检测部门','默认检测人','关联检测人','操作'];
		var colModel = [ 
		{
			name : 'orgName',
			index : 'orgName',
			width : 100,
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'userName',
			index : 'userName',
			width : 50,
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'userNames',
			index : 'userNames',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\''+ids[i]+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	
	function jqgridDelete(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","${basePath}init/itemUser/delete.do?id=${vo.id}");
			$("#listForm").submit();
		});
	}
</script>
 
</html>
