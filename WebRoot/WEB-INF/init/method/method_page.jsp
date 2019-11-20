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
						<li><a href="javascript:backMainPage();">检测方法</a></li>
						<li><strong>方法列表</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:openEditPage('edit.do');">新增</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
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
			 area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/method/edit.do?id='+id,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
		});
	}
	$(function() {
		var url = 'gridData.do';
		var colNames = ['','标准','条款','方法名称','检出限','检测项目','使用仪器','操作'];
		var colModel = [ 
		{
			name : 'minLine',
			index : 'minLine',
			search : false,
			hidden:true
		},{
			name : 'code',
			index : 'code',
			width : 140,
			fixed:true,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'chapter',
			index : 'chapter',
			width : 80,
			fixed:true,
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'name',
			index : 'name',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'limitLine',
			index : 'limitLine',
			width : 100,
			fixed:true,
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'itemNames',
			index : 'itemNames',
			width : 150,
			fixed:true,
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'appNames',
			index : 'appNames',
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
		gridInitAuto(url, colNames, colModel, '','20','#pager',true)
	});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ ids[i] + '\')">修改</a>';
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
			$("#listForm").attr("action","${basePath}init/method/update2del.do?id=${vo.id}");
			$("#listForm").submit();
		});
	}
</script>
 
</html>
