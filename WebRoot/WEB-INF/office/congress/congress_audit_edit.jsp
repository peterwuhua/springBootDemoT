<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>会务审批</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>会<br>议<br>物<br>资</label></td>
								<td class="width-15 active"><label class="pull-right">会议名称:</label></td>
								<td class="width-35">
								    <input  id="name" name="name" class="form-control" readonly="readonly" type="text" value="${vo.name}" />
								</td>
																<td class="width-15 active"><label class="pull-right">会议地点:</label></td>
								<td class="width-35">
									<input  id="address" name="address" class="form-control " readonly="readonly" type="text" value="${vo.address}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">主&nbsp;&nbsp;讲&nbsp;人:</label></td>
								<td class="width-35">
									<input  id="zjr" name="zjr" class="form-control " readonly="readonly" type="text" value="${vo.zjr}" />
									<input type="hidden" id="zjrId" name="zjrId" value="${vo.zjrId }">
								</td>
								<td class="width-15 active"><label class="pull-right">参与人员:</label></td>
								<td class="width-35">
										<input type="text" id="users" name="users" class="form-control" readonly="readonly"  value="${vo.users}" /> 
										<input type="hidden" id="userIds" name="userIds" value="${vo.userIds}"/>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">会议内容:</label></td>
								<td colspan="3">
								   <textarea rows="3" class="form-control" id="content" readonly="readonly"  name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">
										<input id="beginTime" name="beginTime"  class="form-control" readonly="readonly"  type="text" value="${vo.beginTime }" />
								</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">
										<input id="endTime" name="endTime"  class="form-control" readonly="readonly"  type="text" value="${vo.endTime }" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;width: 50px;"><label>会<br>议<br>物<br>资</label></td>
								<td class="width-15 active"><label class="pull-right">使用车辆:</label></td>
								<td class="width-35">									
									<input type="text" id="carNames" name="carNames" class="form-control" readonly="readonly"  value="${vo.carNames}" /> 
									<input type="hidden" id="carIds" name="carIds" value="${vo.carIds}"/>
								</td>
								<td class="width-15 active"><label class="pull-right">会议预算:</label></td>
								<td class="width-35">
									<input  id="ysfee" name="ysfee" class="form-control number" readonly="readonly" maxlength="128" type="text" value="${vo.ysfee}" />
								</td>
							</tr>
							<tr>
							    <td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	<textarea maxlength="128" rows="3" class="form-control" id="qtRemark" readonly="readonly" name="qtRemark">${vo.qtRemark}</textarea>
							    </td>
							</tr>
							<tr>
								<td class="active" style="text-align: center;width: 50px;"></td>
								<td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;人:</label></td>
								<td class="width-35">
									<input id="sqr" name="sqr"  class="form-control"  type="text" value="${vo.sqr }"  readonly="readonly"/>
									<input type="hidden" id="sqrId" name="sqrId" value="${vo.sqrId }" >
								</td>
								<td class="width-15 active"><label class="pull-right">申请日期:</label></td>
								<td class="width-35">
									<input id="supportDate" name="supportDate"  class="form-control"  type="text" value="${vo.supportDate }"  readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批人员:</label></td>
								<td class="width-35">
									<input id="sumUserName" name="sumUserName" readonly="readonly"  class="form-control"  maxlength="128" type="text" value="${vo.sumUserName}" />
								    <input id="sumUserId" name="sumUserId" type="hidden" value="${vo.sumUserId}"/>
								</td>
								<td class="width-15 active"><label class="pull-right">审批日期:</label></td>
								<td class="width-35">
									<input id="sumDate" name="sumDate" readonly="readonly"  class="form-control"  type="text" value="${vo.sumDate}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">审批意见:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="3" class="form-control" id="sumRemark" name="sumRemark" placeholder="审批意见"></textarea></td>
							</tr>
							
						 </tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('auditSuccess.do?isCommit=1');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 审批通过</button>
							<button class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('auditFailure.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 审批不通过</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
    <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
	}
	
	function fnSubmitBack(url){
		var reciveMsg=$('#sumRemark').val();
		if(reciveMsg==''){
			swal("请在审批意见栏输入退回原因！", "", "warning");
			return ;
		}
		swal({
	        title: "您确定要退回该会议申请吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').attr('action',url);
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
    	});
	}

	
	function backMainPage(){
		 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
		 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
		 var editPageKey=$(currentIframe).attr("data-id");
		 var mainPageKey=window.parent.pageMap.get(editPageKey);
		 window.parent.pageMap.remove(editPageKey);//移除map缓存
		 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
		 $(mainIframe).attr("data-id",editPageKey);
		 $(mainIframe).css("display","inline");
		 var treeUrl=mainIframe.contentWindow.treeUrl;
		 if(!!treeUrl&&treeUrl!='undefined'){
			 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
		 }
		 mainIframe.contentWindow.jqgridReload(); 
		 currentIframe.remove();
	}
	
	</script>
</body>
</html>