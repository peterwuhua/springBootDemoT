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
					<li><a href="gridPage.do">销售管理</a></li>
					<li><strong>客户关怀</strong></li>
				</ol>
			   </div>
			</div>	
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<!-- 列表查询条件 begin --> 
					<input type="hidden" name="id" id="id">
					<div class="form-group col-md-8">
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					</div>
					<!-- 列表查询条件 end -->
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
		var colNames = ['联系人','公司名称','联系电话','部门','职位','生日'];
		var colModel = [
			  {
				name : 'contactName',
				index : 'contactName',
				sortable : false,
				width: 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customer',
				index : 'customer',
				sortable : false,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'phone',
				index : 'phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'departMent',
				index : 'departMent',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'duty',//职位
				index : 'duty',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'birthDate',//生日
				index : 'birthDate',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitMin(url, colNames, colModel,true);
		});
	



		
	</script>

</body>
</html>
