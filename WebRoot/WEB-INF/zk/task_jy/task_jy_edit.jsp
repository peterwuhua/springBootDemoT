<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
    
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
.compTd{
	background-color: #33FF00;
}
.backTd{
	background-color: #FF6600;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a>数据复核</a></li>
							<li>
								<strong>编辑</strong>
							</li> 
						</ol>
					</div>
                    <div class="ibox-content">
                        <form id="thisForm" method="post" class="form-horizontal" >
		                    <input name="id" value="${vo.id}" type="hidden" />
		                    <input id="ids" name="ids" value="" type="hidden" />
		                    <div align="center" style="font-size: 18px;font-weight: bold;">
		                    	${vo.year}年${vo.month}月份内部质控计划表
		                    </div>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<thead>
									<tr>
										<td style="width: 120px;text-align: center;">
											样品编号
										</td>
										<td style="text-align: center;">
											检测项目
										</td>
										<td style="text-align: center;">
											分析方法
										</td>
										<td style="width: 150px;text-align: center;">
											检测结果
										</td>
										<td style="width: 150px;text-align: center;">
											检测日期
										</td>
										<td style="width: 150px;text-align: center;">
											检测人员
										</td>
										<td style="width: 250px;text-align: center;">
											附件
										</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${vo.itemList}" var="v" varStatus="vs">
										<tr>
											<td style="text-align: center;">
												${v.samplingVo.sampCode}
											</td>
											<td>
												${v.itemName}
											</td>
											<td>
												${v.standCode}&nbsp;&nbsp;${v.methodName}
											</td>
	                                 		<c:choose>
	                                 			<c:when test="${v.status=='1'}">
	                                 				<td class="compTd" onclick="backFun(this)"><!-- 已完成检测项目 -->
		                               					${v.value}
		                                   				<input type="hidden" name="itemList[${vs.index}].id" value="${v.id}">
	                                 				</td>
	                                 			</c:when>
	                                 			<c:otherwise>
	                                 				<td><!-- 未完成检测项目 -->
		                               					未完成
		                                   				<input type="hidden" name=".itemList[${vs.index}].id" value="${v.id}">
		                                   				<input type="hidden" name="itemComp" value="0">
	                                 				</td>
	                                 			</c:otherwise>
	                                 		</c:choose>
	                                 		<td>
												${v.testTime}
											</td>
											<td>
												${v.testMan}
											</td>
											<td>
												<c:choose>
													<c:when test="${fn:length(e.fileList)>0}">
														<c:forEach items="${e.fileList}" var="et" varStatus="vt">
															<a href="download.do?filePath=${et.filePath}&trueName=${et.fileName}" class="btn btn-w-m btn-info">${et.fileName}</a>
														 </c:forEach>
													</c:when>
													<c:otherwise>未上传</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">校&nbsp;验&nbsp;&nbsp;人:</label></td>
										<td class="width-35">
											<input id="auditId" name="auditId" type="hidden" value="${vo.auditId}" /> 
											<input id="auditName" name="auditName" class="form-control" maxlength=32 placeholder="校验人" type="text" value="${vo.auditName}" readonly="readonly" />
										</td>
										<td class="width-15 active"><label class="pull-right">校验时间:</label></td>
										<td class="width-35"><input id="auditDate" name="auditDate" class="form-control datetimeISO" maxlength=20 placeholder="校验时间" type="text" value="${vo.auditDate}" /></td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">校验意见:</label></td>
										<td colspan="3">
											<textarea maxlength="128" style="width: 600px;" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
										</td>
									</tr>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <div class="col-sm-12 col-sm-offset-1">
									<button class="btn btn-w-m btn-success" type="button" onclick="fnSubmitBack('updateData.do?isCommit=-1')"><i class="fa fa-undo" aria-hidden="true"></i> 退回</button>
									<button class="btn btn-w-m btn-primary" type="button" onclick="fnSubmitGo('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
									<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
								</div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
<%@ include file="../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="${basePath}h/js/jquery-ui-1.10.4.min.js"></script>
    <script type="text/javascript">
    
    function fnSubmitGo(url){
    	var num=$('input[name="itemComp"]').length;
    	if(num>0){
    		swal("部分项目未检测完成，不能提交！", "", "warning");
    		return ;
    	}
    	swal({
            title: "您确定要提交该任务吗",
            text: "提交后将无法修改，请谨慎操作！",
            type: "warning",
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
    function fnSubmitBack(url){
    	var ids="";
    	$('.backTd').each(function(){
    		ids+=$(this).attr('key')+",";
    	});
    	if(ids==''){
    		swal("请选择退回项目！", "", "warning");
    		return ;
    	}
    	$('#ids').val(ids);
    	var remark=$('#remark').val();
    	if(remark==''){
    		swal("请输入审核意见！", "", "warning");
    		return ;
    	}
    	swal({
            title: "您确定要退回这些项目吗",
            text: "将会退回重新录入，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "退回",
            cancelButtonText: "取消"
        }, function () {
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
     
    function backFun(obj){
    	if($(obj).attr('class')=='compTd'){
    		 $(obj).switchClass( "compTd", "backTd");
    		 var id=$(obj).find('input').val();
    		 $(obj).attr('key',id);
    	}else{
    		 $(obj).switchClass( "backTd", "compTd");
    		 $(obj).removeAttr('key');
    	}
    }
    </script>
</body>
</html>
