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
		var url = 'gridData4Run.do?sampTypeId=${vo.sampTypeId}';
		var colNames = [ '标准编号','样品类型','标准名称'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sampTypeName',
				index : 'sampTypeName',
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
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});
		function gridComplete() {
			var idstr='${vo.ids}';
	    	if(idstr!=null&&idstr!=''){
	    		var ids=idstr.split(',');
	    		for(var i=0;i<ids.length;i++){
	    			if(ids[i]!=''){
	    				$(this).jqGrid('setSelection', ids[i]);
	    			}
	    		}
	    	}
		}
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var names=[];
			var codes=[];
			var nameCodes=[];
			for(var i=0;i<rowIds.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
				names.push(rData.name);
				codes.push(rData.code);
				nameCodes.push(rData.name+' '+rData.code);
			}
			var data={'id':rowIds,'name':names,'code':codes,'nameCode':nameCodes};
			return data;
		}
	</script> 
	 
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	}
	</script>
</body>
</html>
