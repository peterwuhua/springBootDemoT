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
				<form action="page.do" method="post" id="listForm">
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
		var url = '${basePath}sys/account/listAccount.do?roleCode=FXRY&orgVo.id=${vo.orgId}';
		var editurl='';
		var colNames = [ '编号','名称','职务','职称','手机','邮箱'];
		var colModel = [ 
			 {
				name : 'userVo.no',
				index : 'account.user.no',
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'userName',
				index : 'account.user.name',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.duty',
				index : 'account.user.duty',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userVo.techTitle',
				index : 'techTitle',
				sortable : false,
				width : 90,
				search : false,
			},{
				name : 'userVo.mobile',
				index : 'mobile',
				width : 90,
				sortable : false,
				search : false,
			},{
				name : 'userVo.email',
				index : 'email',
				sortable : false,
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});

		function gridComplete() {
	    	var testUserId='${vo.userIds}';
	    	if(testUserId!=null&&testUserId!=''){
	    		var ids=testUserId.split(',');
	    		for(var i=0;i<ids.length;i++){
	    			if(ids[i]!=''){
	    				$(this).jqGrid('setSelection', ids[i]);
	    			}
	    		}
	    	}
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		function fnSelectUser(){
			var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var names=[];
			for(var i=0;i<rowId.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
				names.push(rData.userName);
			}
			var data={};
			data.id=rowId;
			data.name=names;
			return data;
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
				//beforeSelectRow: beforeSelectRow
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
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	}
	</script>
</body>
</html>