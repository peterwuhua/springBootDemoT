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
		var url = 'listPoint.do?customerVo.id=${vo.customerVo.id}&sampTypeId=${vo.sampTypeId}';
		var editurl='';
		var colNames = ['','','','监测点位','点位代码','样品名称','监测项目'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'sampId',
				index : 'sampId',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'itemId',
				index : 'itemId',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'code',
				index : 'code',
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'itemName',
				index : 'itemName',
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});
		function gridComplete() {
			var sampPointId='${vo.ids}';
	    	if(sampPointId!=null&&sampPointId!=''){
	    		var ids=sampPointId.split(',');
	    		for(var i=0;i<ids.length;i++){
	    			if(ids[i]!=''){
	    				$(this).jqGrid('setSelection', ids[i]);
	    			}
	    		}
	    	}
		}
		function fnSelect(){
			var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var data=[];
			for(var i=0;i<rowIds.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
				data.push(rData);
			}
			return data;
		}
	</script> 
	 
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-85);
	}
	</script>
</body>
</html>
