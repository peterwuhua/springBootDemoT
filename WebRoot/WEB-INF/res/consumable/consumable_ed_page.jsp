<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>耗材管理</a></li>
						<li><strong>列表</strong></li>
					</ol>
				 </div>
				<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-outline btn-default " onclick="window.location.href='gridPage.do'">耗材信息列表</a>
	                    <a type="button" class="btn btn-xs btn-success active" herf="#">超出警戒耗材</a>
	                </div>
	            </div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('res-consumable-export.xls','耗材信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('res-consumable-export.xls','耗材信息列表.xls',1)" >选中数据</a>
                              </li>
                          </ul>
                     </div>
				</div>
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
		var url = 'gridData.do?isSafe=Y';
		var editurl='gridEdit.do';
		var colNames = ['编号 ','名称 ','类型','单位','型号','数量','警戒数量','有效期至','保管人','操作'];
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
	  				sortable : false,
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
	  			}, {
	  				name : 'unit',
	  				index : 'unit',
	  				width : 100,
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
	  				fixed:true,
	  				width : 80,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'safeAmount',
	  				index : 'safeAmount',
	  				sortable : false,
	  				fixed:true,
	  				width : 80,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'exp',
	  				index : 'exp',
	  				sortable : false,
	  				fixed:true,
	  				width : 100,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'keeper',
	  				index : 'keeper',
	  				sortable : false,
	  				fixed:true,
	  				width :80,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			},{
					name : 'act',
					index : 'act',
					width : 70,
					fixed:true,
					title : false,
					search : false,
					sortable : false,
				}];

		gridInitMin(url, colNames, colModel, true);
		});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	</script> 
	
</body>
</html>
