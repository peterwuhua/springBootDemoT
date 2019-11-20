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
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a>质控评价</a></li>
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
											检测方法
										</td>
										 <td style="width: 120px;text-align: center;">
											检测人员
										</td>
										<td style="width: 120px;text-align: center;">
											检测结果
										</td>
										<td style="width: 120px;text-align: center;">
											给定标准值
										</td>
										<td style="width: 120px;text-align: center;">
											测定结果相对差值
										</td>
										 <td style="width: 120px;text-align: center;">
											回收率
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
											<td>
												${v.testMan}
											</td>
	                                 		<td>
                              					${v.value}
                               				</td>
	                                 		<td>
												${v.limited}
											</td>
											 <td>
												${v.limitLine}
											</td>
											<td>
												${v.hsl}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="active"><label class="pull-right">考核结果评价:</label></td>
										<td colspan="3">
											<textarea style="width: 600px;" class="form-control" id="result" name="result">${vo.result}</textarea>
										</td>
									</tr>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <div class="col-sm-12 col-sm-offset-1">
									<button class="btn btn-w-m btn-primary" type="button" onclick="fnSubmitGo('updateData.do?isCommit=1')"><i class="fa check" aria-hidden="true"></i> 提交</button>
									<a class="btn btn-w-m btn-white" href="gridPage.do" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
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
    
    function fnSubmitGo(url){
    	swal({
            title: "您确定要提交该任务吗",
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
    </script>
</body>
</html>
