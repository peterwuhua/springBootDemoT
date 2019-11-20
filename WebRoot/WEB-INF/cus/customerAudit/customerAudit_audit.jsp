<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
.panel-heading{
	padding: 0px;
}
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
	<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="gridPage.do">我的客户</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
		<form method="post" action="" name="form" class="form-horizontal" id="thisForm">
			<input name="id" value="${vo.id}" type="hidden" />
			<input name="auth" value="${vo.auth}" type="hidden" />
			<input name="name" value="${vo.name}" type="hidden" />
			<input name="address" value="${vo.address}" type="hidden" />
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">${vo.name }</td>
								<td class="width-15 active"><label class="pull-right">法&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:</label></td>
								<td class="width-35">${vo.custFaRen }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35">${vo.address}</td>
								<td class="width-15 active"><label class="pull-right">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</label></td>
								<td class="width-35">${vo.telephone}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">社会信用代码:</label></td>
								<td>${vo.custCode }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35">${vo.custBank}</td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35">${vo.custAccount }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35">${vo.person}</td>
								<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
								<td class="width-35">${vo.phone}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td class="width-35">${vo.email}</td>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td class="width-35">${vo.zip}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</label></td>
								<td class="width-35">${vo.fax }</td>
								<td class="width-15 active"><label class="pull-right">成立日期:</label></td>
								<td class="width-35">${vo.buildDate }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属区域:</label></td>
								<td class="width-35">
									${vo.areaPath}
								</td>
								<td class="width-15 active"><label class="pull-right">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
								<td class="width-35">
									${vo.industry}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户属性:</label></td>
								<td class="width-35">
									${vo.cusType}
								</td>
								<td class="width-15 active"><label class="pull-right">客户类型:</label></td>
								<td class="width-35">
									<select id="cusCates" name="cusCates" class="form-control required" validate="required">
										<option value="">-请选择-</option>
										<c:forEach items="${cusCateList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e==vo.cusCates}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}" >${e}</option>													
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">销售人员:</label></td>
								<td class="width-35" >
									${vo.saler}
									<input type="hidden" name="saler" value="${vo.saler}"/>
									<input type="hidden" name="salerId" value="${vo.salerId}"/>
								</td>
								<td class="active"><label class="pull-right">提交日期:</label></td>
								<td>
								${vo.supportDate}
								</td>
							</tr>
								<tr>
								<td class="active"><label class="pull-right">跟进状态:</label></td>
								<td>
									${vo.gjStatus}
								</td>
								<td class="width-15 active"></td>
								<td class="width-35"></td>	
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${fn:length(contactsList)>0}">
						<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend>联系人</legend>
						</fieldset>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<thead>
								<tr>
									<th>
					                   	 姓名
									</th>
									<th>
					             	      部门
									</th>
									<th>
					                 	  职务
									</th>
									<th>
					                 	  出生年月
									</th>
									<th>
					                  	联系方式
									</th>
									<th>
					                 	   邮箱
									</th>
								</tr>
							</thead>
							<c:forEach items="${contactsList}" var="e" varStatus="v">
								<tr>
									<td>
					                    ${e.name}
									</td>
									<td>
					                    ${e.depart}
									</td>
									<td>
					                    ${e.duty}
									</td>
									<td>
					                    ${e.birthDate}
									</td>
									<td>
					                    ${e.phone}
									</td>
									<td>
					                    ${e.email}
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
								<td class="width-35">${vo.sumUserName} </td>
								<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
								<td class="width-35">${vo.sumDate}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="sumRemark" name="sumRemark" placeholder="审批意见"></textarea></td>
								<td><input type="hidden" name="sumUserName" value="${vo.sumUserName}"></td>
								<td><input type="hidden" name="sumDate" value="${vo.sumDate}"></td>
							</tr>
						 </tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('auditSuccess.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 审批通过</button>
							<button class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('auditFailure.do');"><i class="fa fa-check" aria-hidden="true"></i> 审批不通过</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
		</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
			   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	  <script type="text/javascript">
		function formSubmit(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
		function fnSubmitBack(url){
			var sumRemark=$('#sumRemark').val();
			if(sumRemark==''){
				swal("请在审批意见栏输入审批不通过意见！", "", "warning");
				return ;
			}			
				$('#thisForm').attr('action',url);
				$('#thisForm').submit();
		}
		
		 $(document).ready(function(){
				$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
				//cusCatesSelect();//客户类型
			});
		 
			function cusCatesSelect() { //客户类型选择
				$.ajax({
					url : '${basePath}/cus/normalList/fetchCustCates.do',
					datatype : "json",
					success : function(data) {
						var value = data;
						var optionstring = "<option value=\"\" >-请选择-</option>";
						for (var i = 0; i < value.length; i++) {
							if('${vo.cusCates}'== value[i]){
							optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
									+ value[i] + "</option>";
							}else{
								optionstring += "<option value=\"" + value[i] + "\" >"
									+ value[i] + "</option>";
							}
						}
						$("#cusCates").html(optionstring);
					}
				});
			}
	  </script>		
		
</body>
</html>
