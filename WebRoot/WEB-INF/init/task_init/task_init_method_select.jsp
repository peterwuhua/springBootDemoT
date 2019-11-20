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
		<div class="">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" id="listForm">
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
		var url = '${basePath}init/method/gridData.do';
		var editurl='';
		var colNames = ['标准编号','检测标准','测试方法'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'standName',
				index : 'standName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
		function gridComplete() {
			var idStr='${vo.ids}';
			 var ids=idStr.split(',');
			 for(var i=0;i<ids.length;i++){
				var rows=ids[i];
				if(rows!=''){
					jQuery("#listTable").jqGrid('setSelection',rows);
				}
			}
		}
		function fnSelect(){
			var selectIds = getSelectIds();
			var selectNames=[];
			for(var i=0;i<selectIds.length;i++){
				var rowid=selectIds[i];
				if(rowid!=''){
					var rowData = $("#listTable").jqGrid('getRowData',rowid);
					selectNames.push(rowData.code+' '+rowData.name);
				}
			}
			var data={};
			data.id=selectIds;
			data.name=selectNames;
			return data;
		}
	</script> 
    </body>
</html>