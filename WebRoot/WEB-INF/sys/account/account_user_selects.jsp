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
		var url = '${basePath}sys/account/gridData.do';
		var editurl='';
 		var colNames = ['','编号','姓名','部门','职务','手机','邮箱'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				title : false,
				search:false,
				hidden:true
			},{
				name : 'userVo.no',
				index : 'user.no',
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.name',
				index : 'user.name',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'orgVo.name',
				index : 'org.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.duty',
				index : 'user.duty',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.mobile',
				index : 'user.mobile',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.email',
				index : 'user.email',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, editurl, 20,'#pager',true);
		});

		function gridComplete() {
			var idstr='${vo.ids}';
	    	if(idstr!=''){
	    		var	ids=idstr.split(',');
	    		for(var i=0;i<ids.length;i++){
	    			if(ids[i]!=''){
	    				$(this).jqGrid('setSelection', ids[i]);
	    			}
	    		}
	    	}
		}
		function fnSelect(){
			var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var content = [];
			for(var i=0;i<rowId.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
				content.push(rData["userVo.name"]);
			}
			var data={};
			data.id=rowId;
			data.name=content;
			return data;
		}
	</script> 
	<script type="text/javascript">
	function gridInit(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
		$.jgrid.defaults.styleUI = "Bootstrap";
		var mygrid = $("#listTable").jqGrid({
			url : url,
			datatype : "json",
			mtype : "POST",
			colNames : colNames,
			colModel : colModel,
			rownumbers:true,
			rowNum : rowNum,
			rowList : [10,20,30,50,100],
			pager : pager,
			sortname : 'sort',
			sortorder : "asc",
			viewrecords : true,
			height:'auto',
			recordpos : 'right',
			jsonReader : {
				root : 'datas'
			},
			shrinkToFit:false,    
	        autoScroll: true,  
	        search:true,
			editurl:editurl,
			multiselect : multiselect,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });//横向滚动条 
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	}
	</script>
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-125);
	}
	</script>
</body>
</html>
