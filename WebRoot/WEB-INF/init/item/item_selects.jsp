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
		var url = '${basePath}init/item/gridData4Select.do';
		var sampType='${vo.sampTypeIds}';
		if(sampType!=''){
			url+="?sampTypeIds="+sampType;
		}
		var colNames = ['监测项目','计量单位','现场监测','样品类别'];
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
			name : 'isNow',
			index : 'isNow',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'sampTypeNames',
			index : 'sampTypeNames',
			sortable : false,
			title : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}];
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	});
	 
	function gridComplete() {
		var id='${vo.ids}';
    	if(id!=null&&id!=''){
    		var ids=id.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				$(this).jqGrid('setSelection', ids[i]);
    			}
    		}
    	}
	}
	function formatStr(cellValue){
		if(cellValue=='Y'){
			return '是';
		}else{
			return '否';
		}
	}
	function fnSelect(){
		var selectIds = getSelectIds();
		var selectNames=[];
		var price=0;
		for(var i=0;i<selectIds.length;i++){
			var rowid=selectIds[i];
			if(rowid!=''){
				var rowData = $("#listTable").jqGrid('getRowData',rowid);
				selectNames.push(rowData.name);
				price+=parseFloat(rowData.price);
			}
		}
		var data={};
		data.id=selectIds;
		data.name=selectNames;
		data.price=price;
		return data;
	}
</script>
 
</html>
