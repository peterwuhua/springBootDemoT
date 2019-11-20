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
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a href="javascript:backMainPage();">计划审核</a></li>
							<li>
								<strong>编辑</strong>
							</li> 
						</ol>
					</div>
                    <div class="ibox-content">
                        <form id="thisForm" method="post" class="form-horizontal" >
		                   <input name="id" value="${vo.id}" type="hidden" />
		                    <div align="center" style="font-size: 18px;font-weight: bold;">
		                    	${vo.year}年${vo.month}月份内部质控计划表
		                    </div>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">制定人:</label></td>
										<td class="width-35">
											${vo.acceptName}
	                                    </td>
										<td class="width-15 active"><label class="pull-right">制定时间:</label></td>
										<td class="width-35">
											${vo.acceptDate }
										</td>
	                                </tr>
								</tbody>
							</table>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								 <thead>
								 	<tr>
								 		<th style="text-align: center;width: 50px;">序号</th>
								 		<th style="text-align: center;width: 100px;">考核日期</th>
								 		<th style="text-align: center;width: 150px;">考核项目</th>
								 		<th style="text-align: center;width: 120px;">考核方式</th>
								 		<th style="text-align: center;width: 100px;">被考核人员</th>
								 		<th style="text-align: center;width: 150px;">检测仪器</th>
								 		<th style="text-align: center;width: 200px;">标准物质</th>
								 		<th>备注</th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${vo.sampList}" var="e" varStatus="s">
										<tr index="${s.index}">
									 		<td style="text-align: center;">
									 			${s.index+1}
									 		</td>
									 		<td>
									 			${e.assDate}
									 		</td>
									 		<td>
									 			${e.itemNames}
									 		</td>
									 		<td>
									 			${e.type}
									 		</td>
									 		<td>
									 			${e.userNames}
									 		</td>
									 		<td>
									 			${e.appNames}
									 		</td>
									 		<td>
									 			${e.standNames}
									 		</td>
									 		<td>
									 			${e.remark}
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tr>
									<td class="active"><label class="pull-right">审批结果:</label></td>
									<td><select class="input-sm form-control input-s-sm inline" name="isCommit" id="isCommit">
											<option value="1">通过</option>
											<option value="-1">退回</option>
										</select>
									</td>
									<td class="active"><label class="pull-right">审批人:</label></td>
									<td>
										<input id="auditName" class="form-control" name="auditName" type="text" value="${vo.auditName}" readonly="readonly"/>
										<input type="hidden" id="auditId"  name="auditId" value="${vo.auditId}">
									</td>
									<td class="active"><label class="pull-right">审批日期:</label></td>
									<td>
										<div class="input-group date">
			                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                              	<input id="auditDate" name="auditDate" class="form-control required dateISO" validate="required" placeholder="审批日期" type="text" value="${vo.auditDate}" />
			                            </div>
									</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
									<td colspan="5"><textarea maxlength="128" class="form-control" style="width: 580px;" rows="2" cols="80" id="remark" name="remark" ></textarea></td>
								</tr>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('updateData.do');"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
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
    <script type="text/javascript">
    function formSubmit(url){
    	var isCommit=$('#isCommit').val();
    	if(isCommit==-1&&$('#remark').val()==''){
    		layer.msg('请输入退回原因', {icon: 0,time: 3000});
    		return true;
    	}
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
