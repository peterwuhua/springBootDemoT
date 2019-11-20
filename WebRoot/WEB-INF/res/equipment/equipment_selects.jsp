<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<%@ include file="../../include/status.jsp"%>
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
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData.do';
  		var colNames = ['设备编号','设备名称','规格型号','保管科室','保管人'];
		var colModel = [ 
			 {
				name : 'no',
				index : 'no',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'spec',
				index : 'spec',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'deptName',
				index : 'deptName',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'keeper',
				index : 'keeper',
				width : 70,
				sortable : false,
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
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var names=[];
		for(var i=0;i<rowIds.length;i++){
			var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
			names.push(rData.name+rData.spec);
		}
		var data={};
		data.id=rowIds;
		data.name=names;
		return data;
	}
	</script>

</body>
</html>
