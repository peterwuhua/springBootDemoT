<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">账户</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data" name="from" onsubmit="return checkLoginName(from.loginName.value);">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input name="orgId" value="${vo.orgId}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">

						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">所属部门:</label></th>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="selectName" class="form-control" placeholder="请选择部门" value="${vo.orgVo.name}" onclick="fnSelect()"> 
										<input type="hidden" id="selectId" name="orgVo.id" value="${vo.orgVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">用户名称:</label></td>
								<td class="width-35">
									<c:if test="${isEdit}">${vo.userVo.name}</c:if> 
									<c:if test="${!isEdit}">
										<div class="input-group">
											<input type="text" id="selectUserName" class="form-control required" validate="required" placeholder="请选择人员" value="${vo.userVo.name}" onclick="fnSelectUser()"> 
											<input type="hidden" id="selectUserId" name="userVo.id" value="${vo.userVo.id}">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
											</div>
										</div>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">登录名称:</label></td>
								<td><input name="loginName" class="form-control required" validate="required" maxlength=64 placeholder="登录名称" type="text" value="${vo.loginName }" /></td>
								<td class="active"><label class="pull-right">登录密码:</label></td>
								<td><input id="password" name="password" class="form-control" rangelength=1,16 placeholder="1-16位密码" type="password" autocomplete="new-password" /></td>
							</tr>
							<tr>

							</tr>
							<tr>
								<td class="active"><label class="pull-right">电子签章:</label></td>
								<td><input id="file" name="file" class="form-control" placeholder="内容" type="file" /></td>
								<td colspan="2"><c:if test="${fn:length(vo.signature)>0}">
										<a href="javascript:openImg('${vo.signature}','${vo.userVo.name}【${vo.loginName }】');" class="btn btn-w-m btn-info">打开文件</a>
									</c:if></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">账户是否启用:</label></td>
								<td><select class="form-control" name="isUse" id="isUse" value="${vo.isUse}">
										<c:choose>
											<c:when test="${'1' eq vo.isUse}">
												<option value="1" selected="selected">是</option>
												<option value="0">否</option>
											</c:when>
											<c:otherwise>
												<option value="1">是</option>
												<option value="0" selected="selected">否</option>
											</c:otherwise>
										</c:choose>
								</select></td>
								<td class="active"><label class="pull-right">账户IP:</label></td>
								<td><input id="ip" name="ip" class="form-control" maxlength=64 maxlength=16 placeholder="请输入IP" type="text" value="${vo.ip }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td><input id="sort" name="sort" class="form-control " placeholder="排序" type="text" value="${vo.sort }" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="15%" class="active"><label class="pull-right">角色:</label></td>
								<td colspan="3"><c:forEach items="${roleList}" var="e" varStatus="s">
										<label class="checkbox-inline i-checks ">
											<div class="icheckbox_square-green">
												<input type="checkbox" name="roleIds" title="${e.describtion }" value="${e.id}" <c:if test="${fn:indexOf(roleSelectIds, e.id)>-1}">checked="checked"</c:if>>
											</div> <span title="${e.describtion }">${e.name}</span>
										</label>
									</c:forEach></td>
							</tr>

						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save4Data.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" data-placement="top" data-toggle="tooltip" data-original-title="111"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../open/open_img.jsp"%>
	<script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
    </script>

	<script>
		function fnSelect(){
			var pId=$('#selectId').val();
			var pName=$('#selectName').val();
				layer.open({
					title:'部门选择',	
					type: 2,
					area: ['350px', '75%'],
					fix: false, //不固定
					maxmin: true,
					content: '${basePath}sys/org/select.do?id='+pId,
					btn: ['确定','取消'], //按钮
					btn1: function(index, layero) {
					  var iframeWin = window[layero.find('iframe')[0]['name']];
					  var data=iframeWin.fnSelect();
					  $('#selectId').val(data.id);
					  $('#selectName').val(data.name);
					}
				});
			}
		function fnSelectUser(){
			var orgId=$('#selectId').val();
			var userId=$('#selectUserId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/user/select.do?id='+userId+'&orgId='+orgId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#selectUserId').val(data.id);
				  $('#selectUserName').val(data.name);
				}
			});
		}
		function checkLoginName(loginName){
			var id = '${vo.id}';
			var flag = false;
			$.ajax({
				url:'${basePath}sys/account/checkLoginName.do',
				dataType:"json",
				data:{"id":id,"loginName":loginName},
				type:"post",
				async:false,
				success: function(data){
					if("error" == data.type){
						layer.alert(data.message, {
		   					icon: 2,
		   					shadeClose: true
		   				});
					}
					if("success" == data.type){
						flag =true;
					}
				},
				error:function(ajaxobj){
			    }  
			});
			return flag;
		}
	</script>
	<script>
	 $('input[type="file"]').prettyFile();
	</script>
</body>
</html>
