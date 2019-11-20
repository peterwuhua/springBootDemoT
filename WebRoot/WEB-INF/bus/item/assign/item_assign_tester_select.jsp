<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12" style="height: 100%">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<div class="tabs-container">
                    <ul class="nav nav-tabs" id="tabUl">
                        <li class="active" id="li-1">
                        	<a data-toggle="tab" href="#tab-1" aria-expanded="true">配置检测人</a>
                        </li>
                        <li class="" id="li-2">
                        	<a data-toggle="tab" href="#tab-2" aria-expanded="false">所有检测人</a>
                        </li>
                    </ul>
                    <div class="tab-content">
						<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
	                      <div id="pager"></div>
		               </div>
                    </div>
                </div>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
		var url = '${basePath}sys/account/listAccount.do?roleCode=FXRY';
		var url_1 = '${basePath}bus/itemAssign/listTesterSelect.do?itemId=${vo.itemId}';
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
			$(function() {
				gridInitAuto(url_1, colNames, colModel, '', -1,'',true);
			});
			$('#li-1').click(function(){
				$("#listTable").setGridParam(
						{
						    url:url_1
						}
					) .trigger("reloadGrid");
			});
			$('#li-2').click(function(){
				$("#listTable").setGridParam(
					{
					    url:url
					}
				) .trigger("reloadGrid");
			});
			function gridComplete() {
		    	var idstr='${vo.testManId}';
		    	if(idstr!=''){
		    		var	ids=idstr.split(',');
		    		for(var i=0;i<ids.length;i++){
		    			if(ids[i]!=''){
		    				$(this).jqGrid('setSelection', ids[i]);
		    			}
		    		}
		    	}
			}
			function fnSelectUser(){
				var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
				var content = [];
				for(var i=0;i<rowId.length;i++){
					var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
					content.push(rData.userName);
				}
				var data={};
				data.id=rowId.join(',');
				data.name=content.join(',');
				return data;
			}
		</script> 
		 
		<script type="text/javascript">
		//设置弹窗中jqgrid表格高度
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-130);
		}
		//当窗口大小发生改变时触发
		window.onresize = function doResize() {
			 var win = getWinSize();
			 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-130);
		}
		</script>
</body>
</html>
