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
					<li><a>任务下达</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" />
					<div align="center" style="font-size: 18px; font-weight: bold;">${vo.year}年${vo.month}月份内部质控计划表</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th style="text-align: center; width: 50px;">序号</th>
								<th style="text-align: center; width: 120px;">样品编号</th>
								<th style="text-align: center; width: 100px;">考核日期</th>
								<th style="text-align: center; width: 200px;">考核项目</th>
								<th style="text-align: center; width: 150px;">检测科室</th>
								<th style="text-align: center; width: 150px;">被考核人</th>
								<th style="text-align: center; width: 200px;">要求完成日期</th>
							</tr>
						</thead>
						<tbody id="detail_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="s">
								<tr index="${s.index}">
									<td style="padding: 4px; text-align: center;">${s.index+1} <input type="hidden" name="sampList[${s.index}].id" value="${e.id}">
									</td>
									<td style="padding: 4px;">${e.sampCode}</td>
									<td style="padding: 4px;">${e.sampDate}</td>
									<td style="padding: 4px;">${e.itemNames}</td>
									<td style="padding: 4px;">
										<div class="input-group">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg(${s.index})">选择</button>
											</div>
											<input type="text" id="orgName${s.index}" name="sampList[${s.index}].orgName" class="form-control" placeholder="请选择" value="${e.orgName}"> <input type="hidden" id="orgId${s.index}" name="sampList[${s.index}].orgId" value="${e.orgId}">
										</div>
									</td>
									<td style="padding: 4px;">
										<div class="input-group">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers(${s.index})">选择</button>
											</div>
											<input type="text" id="userNames${s.index}" name="sampList[${s.index}].userNames" class="form-control" placeholder="请选择" value="${e.userNames}"> <input type="hidden" id="userIds${s.index}" name="sampList[${s.index}].userIds" value="${e.userIds}">
										</div>
									</td>
									<td style="padding: 4px;"><input type="text" id="compDate${s.index}" name="sampList[${s.index}].compDate" class="form-control dateISO" placeholder="请选择" value="${e.compDate}"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('updateData.do?isCommit=1');">
								<i class="fa fa-check" aria-hidden="true"></i> 提交
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
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
    function fnSelectUsers(n){
		var userIds=$('#userIds'+n).val();
		var userNames=$('#userNames'+n).val();
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
				$('#userIds'+n).val(data.id);
				$('#userNames'+n).val(data.name);
			}
		});
	}
    function fnSelectOrg(n){
    	var orgId=$('#orgId'+n).val();
		var orgName=$('#orgName'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['300px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'org_select.do?objIds='+orgId+'&objNames='+orgName,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#orgId'+n).val(data.id);
				$('#orgName'+n).val(data.name);
			}
		});
    }
    </script>
</body>
</html>
