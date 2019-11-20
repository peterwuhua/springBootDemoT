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
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">客户</a></li>
						<li><strong>客户跟踪记录</strong></li>
					</ol>
			</div>
			<div class="ibox-content">
				  <div class="panel-heading">
						<div class="panel-options">
							<div class="col-sm-6">
								<ul class="nav nav-tabs">
									<li><a href="#" onclick="location.href='${basePath}cus/customer/edit.do?id=${vo.customerId}'" data-toggle="tab"> 客户信息 </a></li>
									<li><a onmousedown="checkSave();" href="#" onclick="location.href='${basePath}cus/contacts/gridTab.do?customerVo.id=${vo.customerId}'" data-toggle="tab">联系人</a></li>
									<li class="active"><a href="#"  data-toggle="tab">客户跟踪记录</a></li>
								</ul>
							</div>
						</div>
					</div>
					<form action="gridPage4Cus.do" method="post" id="listForm">
						<div class="jqGrid_wrapper">
							<table id="listTable"></table>
							<div id="pager">
						</div>
					</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridDatad.do?customerId=${vo.customerId}';
		var colNames = ['客户名称','联系人','联系方式','拜访地点','拜访日期','拜访人员','拜访内容'];
		var colModel = [
			{
				name : 'customerName',
				index : 'customerName', //客户名称
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contactPerson',
				index : 'contactPerson', //客户名称
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contactWay',
				index : 'contactWay', //联系方式
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'address',
				index : 'address', //拜访地点
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},
			{
				name : 'gzDate',  
				index : 'gzDate', //拜访日期
				sortable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',  
				index : 'saler', //拜访人员
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'content',  
				index : 'content', //拜访内容
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitMin(url, colNames, colModel,true);
		});


	
	
	
		
		
		
		
	
		
	</script>

</body>
</html>
