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
  		var colNames = ['编号 ','名称 ','单位','型号','数量','有效期至','保管人','类型'];
		var colModel = [ 
			  {
	  				name : 'no',
	  				index : 'no',
	  				width : 120,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'name',
	  				index : 'name',
	  				width : 120,
	  				sortable : false,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			},{
	  				name : 'unit',
	  				index : 'unit',
	  				width : 60,
	  				sortable : false,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'model',
	  				index : 'model',
	  				width : 100,
	  				sortable : false,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'amount',
	  				index : 'amount',
	  				sortable : false,
	  				width : 70,
	  				fixed:true,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			},{
	  				name : 'exp',
	  				index : 'exp',
	  				sortable : false,
	  				width : 80,
	  				fixed:true,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'user',
	  				index : 'user',
	  				sortable : false,
	  				width : 80,
	  				fixed:true,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'type',
	  				index : 'type',
	  				width : 100,
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
			names.push(rData.model+rData.name);
		}
		var data={};
		data.id=rowIds;
		data.name=names;
		return data;
	}
	</script>

</body>
</html>
