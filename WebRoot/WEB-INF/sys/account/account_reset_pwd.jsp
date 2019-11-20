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
                     <h5>修改密码</h5>
                     <div class="ibox-tools">
                         <a class="collapse-link">
                             <i class="fa fa-chevron-up"></i>
                         </a>
                     </div>
                 </div>
                 <div class="ibox-content">
			<%@ include file="../../include/status.jsp"%>
                     <form method="post" action="resetPwd.do" class="form-horizontal">
                         <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<tbody>
						<tr>
							<td class="active"><label class="pull-right">旧密码:</label></td>
							<td colspan="3">
								<input id="oldPwd"  name="oldPwd" class="form-control required" validate="required" placeholder="旧密码" type="password" value="" minlength="6" maxlength="15"/>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">新密码:</label></td>
							<td colspan="3">
								<input id="newPwd" name="newPwd" class="form-control required"  validate="required" placeholder="新密码" type="password" value="" minlength="6" maxlength="15"/>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">确认新密码:</label></td>
							<td colspan="3">
								<input id="newPwdConfirm" name="newPwdConfirm" class="form-control required" validate="required" placeholder="确认密码" type="password" value="" minlength="6" maxlength="15"/>
							</td>
						</tr>
					</tbody>
				</table>
                         <div class="hr-line-dashed"></div>
                         <div class="form-group">
                             <div class="col-sm-4 col-sm-offset-2">
                                 <button class="btn btn-primary" type="submit">修改</button>
                             </div>
                         </div>
                     </form>
                 </div>
             </div>
 		</div>
    <%@ include file="../../include/js.jsp"%>
</body>
</html>
