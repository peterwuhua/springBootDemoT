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
				<input type="hidden" name="id" id="id" value="${vo.id}">
				<input type="hidden" name="name" id="name" value="${vo.name}">
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
		var orgId='${vo.orgId}';
		var url = 'gridData.do';
		if(orgId!=''){
			url+='?orgId='+orgId;
		}
		var colNames = ['','编号','姓名','部门','职务','手机','邮箱'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				hidden:true
			}, {
				name : 'no',
				index : 'no',
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 70,	
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'orgName',
				index : 'orgName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'duty',
				index : 'duty',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'mobile',
				index : 'mobile',
				width : 90,
				sortable : false,
				search : false,
			},{
				name : 'email',
				index : 'email',
				sortable : false,
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', 20,'#pager',true);
		});

		function gridComplete() {
	    	var id='${vo.id}';
	    	if(id!=''){
	    		$(this).jqGrid('setSelection', id);
	    	}
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		var index = parent.layer.getFrameIndex(window.name);
		function fnSelect(){
			return $("#listTable").jqGrid("getRowData",getSelectId());
		}
	</script> 
	<script>
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
				rowList : [10,20,50,100],
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
