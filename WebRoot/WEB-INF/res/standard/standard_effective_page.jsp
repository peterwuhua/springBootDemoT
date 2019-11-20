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
						<li><a href="standard_page.htm">标准物质管理</a></li>
						<li><strong>列表</strong></li>
					</ol>
				 </div>
				<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-outline btn-default" onclick="window.location.href='gridPage.do'">标准品信息列表</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" onclick="window.location.href='gridPaged.do'">超出警戒标准品</a>
	                    <a type="button" class="btn btn-xs btn-success active" href="#">按效期标准品</a>
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
                              <li><a href="javascript:;" onclick="jqgridExport('res-standard-export.xls','标准品信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('res-standard-export.xls','标准品信息列表.xls',1)" >选中数据</a>
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
	function fnEdit(){
		location.href='standard_edit.htm';
	}
	
	$(function() {
		var url = 'effectivePageData.do';
		var editurl='gridEdit.do';
		var colNames = ['操作','编号 ','名称 ','批号','CAS号','规格型号','含量/浓度','数量','警戒数量','有效期至','保管人','是否过效期'];
		var colModel = [
			  {
				name : 'act',
				index : 'act',
				width : 60,
				title : false,
				search : false,
				frozen : true,
				resizable : false,
				sortable : false,
			} , {
				name : 'no',
				index : 'no',
				width : 120,
				frozen : true,
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 120,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'cerNo',
				index : 'cerNo',
				width : 120,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'cas',
				index : 'cas',
				width : 100,
				frozen : false,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'rule',
				index : 'rule',
				width : 100,
				frozen : false,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'content',
				index : 'content',
				width : 100,
				frozen : false,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'amount',
				index : 'amount',
				editable : false,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'safeAmount',
				index : 'safeAmount',
				editable : false,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'exp',
				index : 'exp',
				editable : true,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'keeper',
				index : 'keeper',
				editable : true,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'dateType',
				index : 'dateType',
				editable : true,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];

			pageInit(url, colNames, colModel, editurl);
		});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="edit.do?id='+ids[i]+'">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	</script> 
	
</body>
</html>
