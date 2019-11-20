<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
.col-sm-6{
	padding: 0px 0px 1px 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">客户跟踪</a></li>
					<li><strong>联系人编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<div class="panel-heading">
					<div class="panel-options">
						<div class="col-sm-6" >
							<a class="btn btn-primary" href="javascript:fnEidt();">新增</a>
							<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
						</div>
					</div>
				</div>
				<form action="${basePath}cus/contacts/gridTab.do" method="post" id="listForm">
				 <input type="hidden" value="${vo.customerVo.id}" id="customerId" name="customerVo.id" />
					<input type="hidden" name="ids" id="ids">
					<div class="col-sm-5"></div>
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
	<script>
		$(function() {
			var url = '${basePath}cus/contacts/gridData4Tab.do?customerVo.id=${vo.customerVo.id}';
			var colNames = [ '联系人', '部门', '职务', '出生年月','联系方式', '邮箱','操作'];
			var colModel = [
				{
				name : 'name',
				index : 'name',
				width : 70,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'depart',
				index : 'depart',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'duty',
				index : 'duty',
				width : 80,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'birthDate',
				index : 'birthDate',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'phone',
				index : 'phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'email',
				index : 'email',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'act',
				index : 'act',
				width : 60,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
			gridInitMin(url, colNames, colModel,true);
		});

		
		function fnSelect()
		{
			var rowid = getSelectId();
			var data = {};
			if(rowid!=null && rowid!=''){
				var rowData = $("#listTable").jqGrid('getRowData',rowid);
				data.contactId=rowid; 
				data.contactPerson=rowData.name;
				data.contactWay=rowData.phone;
				data.department=rowData.depart;
				data.duty=rowData.duty;
				data.email=rowData.email;
				data.phone=rowData.phone;
				data.birthDate=rowData.birthDate;
			}
			return data;
		}
		
		
		
		
		//

		
		
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEidt(\''+ids[i]+'\');">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-250);
		}
		/**
		 * 设置树组件高度与jqgrid表格的高度随着窗口大小的改变而改变
		 * 
		 * 此事件在窗口大小改变时触发
		 */
		window.onresize = function doResize() {
			 var win = getWinSize();
			 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-250);
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
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(selectIds);
				$("#listForm").attr("action","update2del4List.do");
				$("#listForm").submit();
			});
		}
	</script>
<script type="text/javascript">
function fnEidt(parm){
	var id = parm;
	if(id == null || id == undefined || id == ''){
		id = "";
	}
	layer.open({
		title:'联系人',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: 'editTab.do?id='+id+'&customerVo.id=${vo.customerVo.id}',
		btn: ['确定','取消'], //按钮
		yes: function(index, layero) {
			 var iframeWin = window[layero.find('iframe')[0]['name']];
			 var frameName = '_blank';
			 if(top!=this){
				 frameName= self.frameElement.name;
			 }
			 iframeWin.saveTable(frameName);
		}
	});
}




</script>
</body>
</html>
