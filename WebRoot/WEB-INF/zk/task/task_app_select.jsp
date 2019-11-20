<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12" style="height: 100%">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form action="gridPage.do" method="post" id="listForm">
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
		var url = '${basePath}res/appara/gridData.do';
		var editurl='';
		var colNames = ['仪器名称','型号','仪器编号'];
		var colModel = [ 
			 {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'spec',
				index : 'spec',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'no',
				index : 'no',
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', 20,'#pager',true);
		});
		function gridComplete() {
			 var idStr='${vo.objIds}';
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
			var selectNames='';
			for(var i=0;i<selectIds.length;i++){
				var rowid=selectIds[i];
				if(rowid!=''){
					var rowData = $("#listTable").jqGrid('getRowData',rowid);
					selectNames+=rowData.name+"("+rowData.spec+")";
				}
				if(i!=selectIds.length-1){
					selectNames+=",";
				}
			}
			var data={};
			data.id=selectIds;
			data.name=selectNames;
			return data;
		}
	</script> 
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-125);
	}
	function beforeSelectRow(){
    	$("#listTable").jqGrid('resetSelection');  
        return(true); 
    }
	</script>
    </body>
</html>