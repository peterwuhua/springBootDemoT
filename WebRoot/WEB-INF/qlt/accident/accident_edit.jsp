<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

<style type="text/css">
.panel-heading {
	padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">事故单位:</label></th>
								<td>
									<div class="input-group" style="width: 100%">
                                        <input type="text" readonly="readonly" id="custName" name="custName" class="form-control" placeholder="请选择" value="${vo.custName}">
                                        <input type="hidden" id="custId" name="custId" value="${vo.custId}">
                                   		<div class="input-group-btn">
                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer()">选择</button>
                                        </div>
                                    </div>
								</td>
								<th class="width-15 active"><label class="pull-right">事故类型:</label></th>
								<td class="width-35">
									<input id="type" class="form-control"  name="type" type="text" value="${vo.type }" />
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></th>
								<td class="width-35">
									<input id="user" class="form-control required" validate="required" name="user" type="text" value="${vo.user }" />
								</td>
								<th class="width-15 active"><label class="pull-right">联系电话:</label></th>
								<td class="width-35">
									<input id="phone" class="form-control required" validate="required" name="phone" type="text" value="${vo.phone }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">事故描述:</label></td>
								<td colspan="3">
									<textarea name="content" id="content" placeholder="事故发生的原因、经过描述" class="form-control" >${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" name="remark" id="remark" class="form-control" >${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">登&nbsp;&nbsp;记&nbsp;&nbsp;人:</label></td>
								<td>
									<input name="userName" value="${vo.userName}" id="userName" type="text" class="form-control" readonly="readonly"/>
									<input name="userId" value="${vo.userId}" id="userId" type="hidden" />
								</td>
								<th class="width-15 active"><label class="pull-right">登记日期:</label></th>
								<td class="width-35">
									<input id="date" class="form-control required dateISO" validate="required" name="date" type="text" value="${vo.date}" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12" align="center">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?status=0':'addData.do?status=0'}');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?status=1':'addData.do?status=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:;" onclick="closeThis();"><i class="fa fa-undo" aria-hidden="true"></i> 取消</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name);
	
	function fnSelectCustomer(){
		var pId=$('#custId').val();
		parent.layer.open({
			title:'客户信息选择',	
			type: 2,
			area: ['800px', '470px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}cus/pubcustomer/select.do?id='+pId,
			btn: ['确定','取消'],
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			  $("#custName").val(data.name);
			  $("#custId").val(data.id);
			}
		});
	}
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
				 parent.layer.msg(res.message, {icon: 0,time: 3000})
		    	if(res.status=='success'){
		    		parent.jqgridReload();
		    		parent.layer.close(index);
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	}	
	function closeThis(){
		parent.layer.close(index);
	}
</script>
</body>
</html>
