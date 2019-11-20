<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
	<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
    <%@ include file="../../../include/css.jsp"%>
    <%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a>试剂管理</a></li>
							<li><strong>
							出库
							</strong></li>
						</ol>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="/res/regoutin/addOutDepot.do" class="form-horizontal" enctype="multipart/form-data" id="thisForm">
							 <c:if test="${fn:length(vo.id)>0}">
								<input name="id" value="${vo.id}" type="hidden" />
							</c:if>
							 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<thead>
									<tr>	
										<td class="active" colspan="4"><label class="pull-left">出库信息</label></td>
									</tr>
								</thead>
								<tbody>
									<tr>	
										<td class="width-15 active"><label class="pull-right">试剂名称:</label></td>
										<td class="width-35">
											<div class="input-group" style="width: 100%">
												<input id="reagentName" class="form-control required" validate="required" name="reagentVo.name" type="text" value="${vo.reagentVo.name }"  onclick="fnSelect()"/>
												 <input id="reagentId" name="reagentVo.id" value="${vo.reagentVo.id}" type="hidden" />
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">试剂编号:</label></td>
										<td class="width-35"><input id="reagentNo" class="form-control" name="reagentVo.no" type="text" value="${vo.reagentVo.no}" readonly="readonly"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">规格型号:</label></td>
										<td class="width-35"><input id="rule"  class="form-control"  type="text" name="reagentVo.rule" value="${vo.reagentVo.rule} " readonly="readonly"/></td>
										<td class="width-15 active"><label class="pull-right">当前库存:</label></td>
										<td class="width-35"><input id="amount"  class="form-control"  type="text" name="reagentVo.amount" value="${vo.reagentVo.amount} " readonly="readonly"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">领&nbsp;用&nbsp;&nbsp;人:</label></td>
										<td class="width-35">
											<div class="input-group" style="width: 100%">
												<input id="leadingPerson" placeholder="请输入领用人" class="form-control required" validate="required" name="leadingPerson" type="text" value="${vo.leadingPerson }"/>
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">领用时间:</label></td>
										<td class="width-35"><input id="leadingDate" placeholder="请输入领用日期"  class="form-control datetimeISO required" validate="required" name="leadingDate" type="text" value="${vo.leadingDate }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">领用数量:</label></td>
										<td class="width-35"><input id="leadingNum" placeholder="请输入领用数量"  class="form-control number required" validate="required" name="leadingNum" type="text" value="${vo.leadingNum }"/></td>
										<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
										<td class="width-35"><input maxlength="128" id="remark"  class="form-control" name="remark" type="text" value="${vo.remark }"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary"  type="button"  onclick="formSubmit4Back();"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
									<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../../include/js.jsp"%>
    <script type="text/javascript">
    function fnSelect(){
    	var reagentId=$('#reagentId').val();
    	parent.layer.open({
			title:'试剂选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/reagent/select.do?id='+reagentId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#reagentId').val(data.id);
			   $('#reagentName').val(data.name);
			   $('#reagentNo').val(data.no);
			   $('#rule').val(data.rule);
			   $('#amount').val(data.amount);
			   parent.layer.close(index);
			}
		});
    }
	function fnSelectUser(){
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#leadingPerson').val(data.name);
			   parent.layer.close(index);
			}
		});
	}
	
	function formSubmit4Back(){
		
		var b = $("#thisForm").FormValidate();
		if(b){
			var amt = parseInt($("#amount").val());
			var num = parseInt($("#leadingNum").val());
			if (amt-num<0){
				layer.msg('领用数量不能超过库存量！');
				return false;
			}
			 $('form').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	}
    </script>
</body>
</html>
