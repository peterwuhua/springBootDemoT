<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids"> <input type="hidden" name="roleVo.id" id="roleId" value="${vo.id}" />
					<div class="col-sm-9"></div>
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = '${basePath}sys/accountRole/gridData.do?roleVo.id=${vo.id}';
		var editurl='${basePath}sys/accountRole/gridEdit.do';
		var colNames = ['部门','用户名','登录名','排序'];
		var colModel = [ 
			{
				name : 'accountVo.orgVo.name',
				index : 'account.org.name',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'accountVo.userVo.name',
				index : 'account.user.name',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'accountVo.loginName',
				index : 'account.loginName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			} ];
			gridInitAuto(url, colNames, colModel, '','20','#pager',false)
		});
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH - 125);
	}
	</script>
</body>
</html>