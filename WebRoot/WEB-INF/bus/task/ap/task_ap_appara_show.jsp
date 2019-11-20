<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<%@ include file="../../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = '${basePath}res/apparaOut/listData.do?apparaVo.id=${vo.ids}';
  		var colNames = ['开始日期','结束日期','预约人'];
		var colModel = [ 
			 {
				name : 'useTime',
				index : 'useTime',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'backTime',
				index : 'backTime',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'cyNames',
				index : 'cyNames',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',false);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
	</script>

</body>
</html>
