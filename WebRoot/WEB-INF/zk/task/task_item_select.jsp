<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
</head>
<body id="tab_context">
<div class="col-sm-12" style="height: 100%">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 0px;">
			<form action="" method="post" >
				<div class="jqGrid_wrapper">
					<table id="listTable"></table>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="../../include/js.jsp"%>
<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		var url = '${basePath}init/item/gridData.do';
		var colNames = ['检测项目','计量单位','默认检测人'];
		var colModel = [ 
		  {
			name : 'name',
			index : 'name',
			editable : true,
			searchoptions:{
				sopt : ['cn']
			}
		},{
			name : 'unit',
			index : 'unit',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'testUserName',
			index : 'testUserName',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		}];
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-140);
	});
	 
	function gridComplete() {
		var id='${vo.objIds}';
    	if(id!=null&&id!=''){
    		var ids=id.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				$(this).jqGrid('setSelection', ids[i]);
    			}
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
				selectNames+=rowData.name;
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
 
</html>
