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
		var url = '${basePath}doc/document/gridData4Select.do?categoryVo.code=XMZDS';
		var editurl='';
		var colNames = ['','','名称','类型','标记','文件'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				title:false,
				hidden:true
			},{
				name : 'relativePath',
				index : 'relativePath',
				title:false,
				hidden:true
			},{
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
			var id='${vo.id}';
			if(id!=''){
				jQuery("#listTable").jqGrid('setSelection',id);
			}
		}
		function fnSelect(){
			return $("#listTable").jqGrid("getRowData",getSelectId());
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
		function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
			$.jgrid.defaults.styleUI = "Bootstrap";
			var mygrid = $("#listTable").jqGrid({
				url : url,
				datatype : "json",
				mtype : "POST",
				colNames : colNames,
				colModel : colModel,
				rownumbers:true,
				rowNum : rowNum,
				rowList : [10,20,50,100,1000],
				pager : pager,
				sortname : 'sort',
				sortorder : "asc",
				viewrecords : true,
				height:'auto',
				autowidth:true,
				recordpos : 'right',
				jsonReader : {
					root : 'datas'
				},
				shrinkToFit:true,    
		        autoScroll: true,
		        search:true,
				editurl:editurl,
				multiselect : multiselect,
				gridComplete : gridComplete,
				loadComplete : loadComplete,
				beforeSelectRow: beforeSelectRow
			});
			setFilterToolbar();//行内查询
			setJgridWidth();//宽度
			setJgridHeight();//高度
			setNavGrid();//功能按钮
			jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
			$(".ui-search-oper").hide();
			fncleanName();
		}
		function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true);
	    }
	</script> 
    </body>
</html>