<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12" style="height: 100%">
	<div class="ibox float-e-margins">
		<div class="">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
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
	      <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
     <%@ include file="../../sys/open/open_img.jsp"%>
	<script>
	$(function() {
		var url = '${basePath}doc/document/gridData4Select.do?categoryVo.code=YSJLD';
		var editurl='';
		var colNames = ['名称','类型','标记','文件'];
		var colModel = [ 
			 {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'type',
				index : 'type',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sign',
				index : 'sign',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'title',
				index : 'title',
				formatter:showFile,
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-85);
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
					selectNames.push(rowData.name);
				}
			}
			var data={};
			data.id=selectIds;
			data.name=selectNames;
			return data;
		}
		function showFile(cellValue,options,rowObject){
			var r='';
			if(rowObject.type.indexOf('doc')>=0||rowObject.type.indexOf('xls')>=0){
				r='<a href="javascript:fnOpenWord(\''+rowObject.id+'\');" class="btn btn-w-m btn-info">'+cellValue+'</a>';
			}else if(rowObject.type=='pdf'){
				r='<a href="javascript:openFile(\'${basePath}sys/open/open.do\',\''+rowObject.relativePath+'\',\'pdf\');" class="btn btn-w-m btn-info">'+cellValue+'</a>';
			}else{
				r='<a href="javascript:openImg(\''+rowObject.relativePath+'\',\''+cellValue+'\');" class="btn btn-w-m btn-info">'+cellValue+'</a>';
			}
			return r;
		}
		function fnOpenWord(id){
			POBrowser.openWindow('${basePath}doc/document/open.do?id='+id,'width=1200px;height=800px;');
		}
	</script> 
    </body>
</html>