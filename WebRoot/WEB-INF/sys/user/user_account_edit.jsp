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
							<li><a href="gridPage.do">人员</a></li>
							<li><strong>账户信息编辑</strong></li>
						</ol>
					</h5>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					</div>
				</div>
				<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<div class="panel-heading">
					<div class="panel-options">
							<ul class="nav nav-tabs">
								<li><a href="javascript:;" onclick="location.href='edit.do?id=${vo.userVo.id}'"  data-toggle="tab"> 基本信息</a></li>
								<c:if test="${isEdit}">
								<c:forEach items="${accountList}" var="e" varStatus="s">
								<li <c:if test="${e.id eq vo.id}">class="active"</c:if>>
									<a href="javascript:;"   onclick="location.href='accountEdit.do?id=${e.id}&userVo.id=${vo.userVo.id}'" data-toggle="tab">登陆账号(${e.loginName})</a>
								</li>
								</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
					<form method="post" action="addOrUpdateAccount.do" name="from" class="form-horizontal" enctype="multipart/form-data" onsubmit="return checkLoginName(from.loginName.value);">
                    <c:if test="${fn:length(vo.id)>0}">
                    	<input name="id" value="${vo.id}" type="hidden" />
                    </c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">登录名称:</label></td>
								<td><input id="loginName" name="loginName"
									class="form-control required" validate="required" maxlength=64 placeholder="登录名称" type="text" value="${vo.loginName }" />
								</td>
								<td class="active"><label class="pull-right">登录密码:</label></td>
								<td><input id="password" name="password"
									class="form-control" maxlength=16 placeholder="1-16位密码" type="password" value="" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">部门:</label></th>
								<td class="width-35">
									<input type="text" id="selectName" class="form-control"  value="${vo.orgVo.name}" readonly="readonly">
									<input type="hidden" id="selectId"  name="orgVo.id" value="${vo.orgVo.id}">
								</td>
								<td class="active"><label class="pull-right">用户名称:</label></td>
								<td colspan="3">${vo.userVo.name}
                             	 	<input type="hidden" name="userVo.id" value="${vo.userVo.id }"/>
								</td>
							</tr>
							<td class="active"><label class="pull-right">电子签章:</label></td>
								<td>
									<input id="file" name="file"  class="form-control" placeholder="内容" type="file"/>
								</td>
								<td colspan="2">
									<c:if test="${fn:length(vo.signature)>0}">
										<a href="javascript:openImg('${vo.signature}','${vo.userVo.name}【${vo.loginName }】');" class="btn btn-w-m btn-info">打开文件</a>
										<%-- <a href="javascript:openFile('${basePath }sys/open/open.do','${vo.signature}','pdf');" class="btn btn-w-m btn-info">打开文件pdf</a> --%>
									</c:if>
								</td>	
							</tr>
							<tr>
								<td class="active"><label class="pull-right">账户是否启用:</label></td>
								<td>
									<select class="form-control" name="isUse" id="isUse" value="${vo.isUse}">
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
									</select>
									
								</td>
								<td class="active"><label class="pull-right">账户IP:</label></td>
								<td>
									<input id="ip" name="ip"
									class="form-control" maxlength=64 maxlength=16  placeholder="请输入IP" type="text" value="${vo.ip }"/>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td><input id="sort" name="sort"
									class="form-control " placeholder="排序" type="text" value="${vo.sort }" />
								</td>
								<td></td>
								<td></td>
							</tr>	
							<tr>
								<td width="15%" class="active"><label class="pull-right">角色:</label></td>
								<td colspan="3">
									<c:forEach items="${roleList}" var="e" varStatus="s">
                                    	<label class="checkbox-inline i-checks " >
										<div class="icheckbox_square-green" >
											<input type="checkbox"  name="roleIds" value="${e.id}" <c:if test="${fn:indexOf(roleSelectIds, e.id)>-1}">checked="checked"</c:if>>
										</div>${e.name}
										</label> 
                                    </c:forEach>
								</td>
							</tr>
						</tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					 <div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<button class="btn btn-primary" type="button" onclick="formSubmit('saveAccount.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
						<button class="btn btn-primary" type="submit"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存并返回</button>
						<a href=" ${basePath }sys/user/gridPage.do" class="btn btn-white"  data-placement="top" data-toggle="tooltip" data-original-title="111"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
					</div>
					</div>
					</form>
				</div>
			</div>
		</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../open/open_img.jsp"%>
	 <script>
        $(document).ready(function(){
        	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
       	});
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
</script>
<script type="text/javascript">
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
