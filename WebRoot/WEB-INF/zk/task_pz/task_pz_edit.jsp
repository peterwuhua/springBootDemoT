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
							<li><a href="javascript:backMainPage();">忙样配置</a></li>
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
								 		<th style="text-align: center;width:45px;">序号</th>
								 		<th style="text-align: center;width: 105px;">配置日期</th>
								 		<th style="text-align: center;width: 100px;">样品编号</th>
								 		<th style="text-align: center;min-width: 100px;">检测项目</th>
								 		<th style="text-align: center;width: 70px;">样品体积</th>
								 		<th style="text-align: center;">配制方法及过程</th>
								 		<th style="text-align: center;width: 70px;">配制浓度</th>
								 		<th style="text-align: center;width: 70px;">测定浓度</th>
								 		<th style="text-align: center;width: 60px;">回收率</th>
								 		<th style="text-align: center;width: 60px;">精密度</th>
								 		<th style="text-align: center;width: 120px;">配制人</th>
								 		<th style="text-align: center;width: 120px;">检测人</th>
								 		<th style="text-align: center;width: 120px;">备注</th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${vo.sampList}" var="e" varStatus="s">
										<tr index="${s.index}">
									 		<td style="padding:4px;text-align: center;">
									 			${s.index+1}
									 			<input type="hidden" name="sampList[${s.index}].id" value="${e.id}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="sampDate${s.index}" name="sampList[${s.index}].sampDate" class="form-control dateISO" placeholder="请选择" value="${e.sampDate}" >
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="sampCode${s.index}" name="sampList[${s.index}].sampCode" class="form-control" value="${e.sampCode}">
									 		</td>
									 		<td style="padding:4px;">
									 			${e.itemNames}
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="tj${s.index}" name="sampList[${s.index}].tj" class="form-control" placeholder="请输入" value="${e.tj}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="ffgc${s.index}" name="sampList[${s.index}].ffgc" class="form-control" placeholder="请输入" value="${e.ffgc}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="pnd${s.index}" name="sampList[${s.index}].pnd" class="form-control" placeholder="请输入" value="${e.pnd}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="cnd${s.index}" name="sampList[${s.index}].cnd" class="form-control" placeholder="请输入" value="${e.cnd}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="hsl${s.index}" name="sampList[${s.index}].hsl" class="form-control" placeholder="请输入" value="${e.hsl}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="jmd${s.index}" name="sampList[${s.index}].jmd" class="form-control" placeholder="请输入" value="${e.jmd}">
									 		</td>
									 		<td style="padding:4px;">
									 			<div class="input-group">
													<div class="input-group-btn">
														<button style="width:40px;padding-left: 5px;" tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectUser1(${s.index})">选择</button>
													</div>
													<input type="text" id="muserName${s.index}" name="sampList[${s.index}].muserName" class="form-control" placeholder="请选择" value="${e.muserName}">
													<input type="hidden" id="muserId${s.index}" name="sampList[${s.index}].muserId" value="${e.muserId}">
												</div>
									 		</td>
									 		<td style="padding:4px;">
									 			<div class="input-group">
													<div class="input-group-btn">
														<button style="width:40px;padding-left: 5px;" tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectUser2(${s.index})">选择</button>
													</div>
													<input type="text" id="cuserName${s.index}" name="sampList[${s.index}].cuserName" class="form-control" placeholder="请选择" value="${e.cuserName}">
													<input type="hidden" id="cuserId${s.index}" name="sampList[${s.index}].cuserId" value="${e.cuserId}">
												</div>
									 		</td>
									 		<td style="padding:4px;">
									 			<input maxlength="128" type="text" name="sampList[${s.index}].remark" class="form-control" placeholder="请输入" value="${e.remark}">
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitSave('updateData.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
                                    <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存并提交</button>
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
    function formSubmitSave(url){
    	$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
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
	function fnSelectUser1(n){
		var muserId=$('#muserId'+n).val();
		var muserName=$('#muserName'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?objIds='+muserId+'&objNames='+muserName,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#muserId'+n).val(data.id);
				$('#muserName'+n).val(data.name);
			}
		});
	}
	function fnSelectUser2(n){
		var cuserId=$('#cuserId'+n).val();
		var cuserName=$('#cuserName'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?objIds='+cuserId+'&objNames='+cuserName,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#cuserId'+n).val(data.id);
				$('#cuserName'+n).val(data.name);
			}
		});
	}
    </script>
</body>
</html>
