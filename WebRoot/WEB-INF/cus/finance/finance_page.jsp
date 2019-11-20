<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="gridPage.do">账户</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
					<a class="btn btn-primary" href="import.do">导入</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('cus-finance-export.xls','账户信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('cus-finance-export.xls','账户信息列表.xls',1)" >选中数据</a>
                              </li>
                          </ul>
                      </div>
				</div>
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
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['操作','客户名称','单位名称','地址','开户银行','账号','行号','同城结算号','税务登记号'];
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
				name : 'customerVo.name',
				index : 'customer.name',
				width : 200,
				frozen : true,
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 200,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'address',
				index : 'address',
				editable : true,
				resizable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'bank',
				index : 'bank',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'accountNum',
				index : 'accountNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'bankNum',
				index : 'bankNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'settlementNum',
				index : 'settlementNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'registerNum',
				index : 'registerNum',
				editable : true,
				resizable : true,
				width : 150,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>
