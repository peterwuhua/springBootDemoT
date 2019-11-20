<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">文件夹</a></li>
					<li><strong>信息</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<tbody>
						<tr>
							<td class="width-15 active"><label class="pull-right">编码:</label></td>
							<td class="width-35">${vo.code}</td>
							<td class="width-15 active"><label class="pull-right">名称:</label></td>
							<td class="width-35">${vo.name}</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">说明:</label></td>
							<td class="width-35" colspan="3">${vo.describtion}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>角色信息</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form method="post" action="updateRoleDocument.do" class="form-horizontal">
					<input name="dirIds" value="${vo.id}" type="hidden" /> <input name="id" value="${vo.id}" type="hidden" />
					<c:forEach items="${roleList}" var="e" varStatus="s">
						<label class="checkbox-inline i-checks ">
							<div class="icheckbox_square-green">
								<input type="checkbox" name="roleIds" value="${e.id}" <c:if test="${fn:indexOf(roleSelectIds, e.id)>-1}">checked="checked"</c:if>>
							</div>${e.name}
						</label>
					</c:forEach>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" id="reSave" type="button"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" id="submitBut" type="button"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
			$(document).ready(function() {
				$(".i-checks").iCheck({
					checkboxClass : "icheckbox_square-green",
					radioClass : "iradio_square-green",
				})
			});
			
			$(function(){
				$('#submitBut').click(function(){
					var flag=false;
					$("input[name='roleIds']:checked").each(function(){
						flag=true;
					}); 
					if(!flag){
							parent.layer.confirm('确认删除该文件夹下所有已授权文件信息吗？', {
						    btn: ['确定','取消'], 
						    shade: false //不显示遮罩
						}, function(){
							$('form').ajaxSubmit(function(res) {
						    	if(res.status=='success'){
						    	    parent.toastr.success(res.message, '');
							        backMainPage();
						        }else{
						        	parent.toastr.error(res.message, '');
						        }
							});
							parent.layer.msg('取消授权成功', {icon: 1});
						});
					}else{
						$('form').ajaxSubmit(function(res) {
					    	if(res.status=='success'){
					    	    parent.toastr.success(res.message, '');
						        backMainPage();
					        }else{
					        	parent.toastr.error(res.message, '');
					        }
						});
					}
				})
			})
			
			$(function(){
				$('#reSave').click(function(){
					var flag=false;
					$("input[name='roleIds']:checked").each(function(){
						flag=true;
					}); 
					if(!flag){
							parent.layer.confirm('确认删除该文件夹下所有已授权文件信息吗？', {
							btn: ['确定','取消'], 
						    shade: false //不显示遮罩
						}, function(){
							$('form').attr('action','saveRoleDocument.do');
							$('form').submit();
							parent.layer.msg('取消授权成功', {icon: 1});
						});
					}else{
						$('form').attr('action','saveRoleDocument.do');
						$('form').submit();
					}
				})
			})
	</script>
</body>
</html>
