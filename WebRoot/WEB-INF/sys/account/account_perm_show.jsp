<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<h5>
				<ol class="breadcrumb">
					<li><a href="gridPage.do">账户</a></li>
					<li><strong>权限查看</strong></li>
				</ol>
			</h5>
		</div>
		<div class="panel-heading">
			<div class="panel-options">
				<ul class="nav nav-tabs">
					<c:if test="${fn:length(accountRoleList)>0}">
						<c:forEach items="${accountRoleList}" var="e" varStatus="s">
						<li <c:if test="${e.roleVo.id eq roleVo.id}">class="active"</c:if>>
							<a href="javascript:;"   onclick="location.href='showPerm.do?id=${e.accountVo.id}&roleId=${e.roleVo.id}'" data-toggle="tab">${e.roleVo.name}</a>
						</li>
						<input type="hidden" id="msg" value=""/>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(accountRoleList)<=0}">
							<input type="hidden" id="msg" value="暂无角色/权限信息"/>
					</c:if>			
				</ul>
			</div>
		</div>
		<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><strong id="mesg">权限列表</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content">
					<%@ include file="../../include/status.jsp"%>
					<form action="gridPage.do" method="post" id="listForm">
						<input type="hidden" name="ids" id="ids">
						<input type="hidden" name="funId" id="funId">
						<input type="hidden" name="roleId" id="roleId" value="${roleVo.id }">
						<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						 <a href="gridPage.do?orgId=${vo.orgVo.id }"  class="btn btn-white" >返回</a>
						<div class="jqGrid_wrapper">
		                      <table id="listTable"></table>
		                      <div id="pager"></div>
		               	</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var msg = $("#msg").val();
		if(msg.length>0){
			layer.confirm(msg, {icon:5,title:'系统提示',btn: ['返回列表', '取消']
			},function(index, layero){
				var orgId = '${vo.orgId}';
				window.location.href="gridPage.do?orgId="+orgId;
				layer.close(index);
			}, function(index){
			  
			}); 
		}
		
		var roleId = '${roleVo.id}';
		var url = 'gridData4RolePerm.do?roleId='+roleId;
		var editurl='';
		var colNames = ['功能','名称','编码','说明'];
		var colModel = [
			 {
				name : 'permissionVo.functionVo.name',
				index : 'permission.function.name',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'permissionVo.name',
				index : 'permission.name',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'permissionVo.code',
				index : 'permission.code',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'permissionVo.describtion',
				index : 'permission.describtion',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			} ];
			listAllInit(url, colNames, colModel, false);
		});

		function gridComplete() {
			/* var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-primary btn-xs" title="修改" href="edit.do?id='+ids[i]+'">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			} */
		}
	</script>
    </body>
</html>