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
							<li><a>任务分配</a></li>
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
								 <thead>
								 	<tr>
								 		<th style="text-align: center;width: 50px;">序号</th>
								 		<th style="text-align: center;width: 120px;">样品编号</th>
								 		<th style="text-align: center;width: 100px;">考核日期</th>
								 		<th style="text-align: center;width: 200px;">考核项目</th>
								 		<th style="text-align: center;width: 150px;">被考核人</th>
								 		<th style="text-align: center;width: 200px;">要求完成日期</th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${vo.itemList}" var="e" varStatus="s">
										<tr index="${s.index}">
									 		<td style="text-align: center;">
									 			${s.index+1}
									 			<input type="hidden" name="itemList[${s.index}].samplingVo.id" value="${e.samplingVo.id}">
									 		</td>
									 		<td>
									 			${e.samplingVo.sampCode}
									 		</td>
									 		<td>
									 			${e.samplingVo.sampDate}
									 		</td>
									 		<td>
									 			${e.itemName}
									 			<input type="hidden" name="itemList[${s.index}].itemName" value="${e.itemName}">
									 			<input type="hidden" name="itemList[${s.index}].itemId" value="${e.itemId}">
									 			<input type="hidden" name="itemList[${s.index}].unit" value="${e.unit}">
									 		</td>
									 		<td style="padding:4px;">
									 			<div class="input-group">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectUsers(${s.index})">选择</button>
													</div>
													<input type="text" id="testMan${s.index}" name="itemList[${s.index}].testMan" class="form-control" placeholder="请选择" value="${e.testMan}">
													<input type="hidden" id="testManId${s.index}" name="itemList[${s.index}].testManId" value="${e.testManId}">
												</div>
									 		</td>
									 		<td>
									 			<input type="text" id="compDate${s.index}" name="itemList[${s.index}].compDate" class="form-control dateISO" placeholder="请选择" value="${e.compDate}" >
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
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
		swal({
	        title: "您确定要提交该任务吗",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').attr('action',url);
			var b = $("#thisForm").FormValidate();
			if(b){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			}
	    });
	}
    function fnSelectUsers(n){
		var userIds=$('#testManId'+n).val();
		var userNames=$('#testMan'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?objIds='+userIds+'&objNames='+userNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#testManId'+n).val(data.id);
				$('#testMan'+n).val(data.name);
			}
		});
	}
    
    </script>
</body>
</html>
