<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a>标准品管理</a></li>
							<li><strong>
							<c:if test="${fn:length(vo.id)>0}">编辑</c:if>
							<c:if test="${fn:length(vo.id)==0}">新增</c:if>
							</strong></li>
						</ol>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal"  id="thisForm">
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">编号 :</label></td>
										<td class="width-35">
											<input id="no" placeholder="请输入编号"  class="form-control required" validate="required" name="no" type="text" value="${vo.no }"/></td>
	                                    </td>
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td class="width-35">
	                                        <input type="text" id="name" class="form-control required" validate="required" placeholder="请输入名称" name="name" maxlength=64  value="${vo.name }">
	                                    </td>
	                                </tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">CAS:</label></td>
										<td class="width-35"><input id="cas" placeholder="请输入CAS"  class="form-control" name="cas" type="text" value="${vo.cas }"/></td>
										<td class="width-15 active"><label class="pull-right">英文名称:</label></td>
										<td class="width-35"><input id="ename" placeholder="请输入英文名称"  class="form-control" name="ename" type="text" value="${vo.ename }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">数量:</label></td>
										<td class="width-35"><input id="amount" placeholder="请输入数量"  class="form-control required number" validate="required" name="amount" type="text" value="${vo.amount }"/></td>
										<td class="width-15 active"><label class="pull-right">警戒数量:</label></td>
										<td class="width-35"><input id="safeAmount" placeholder="请输入警戒数量"  class="form-control required number" validate="required" name="safeAmount" type="text" value="${vo.safeAmount }"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">规格型号:</label></td>
										<td class="width-35"><input id="rule" placeholder="请输入规格型号"  class="form-control" name="rule" type="text" value="${vo.rule }"/></td>
										<td class="width-15 active"><label class="pull-right">供应商:</label></td>
										<td class="width-35">
											<div class="input-group">
												<input name="supplier" id="supplier" value="${vo.supplier}" placeholder="请选择供应商" type="text" class="form-control" onclick="fnSelectSupplier()" /> 
												<input name="supplierId" id="supplierId" value="${vo.supplierId}" type="hidden" />
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSupplier()">选择</button>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">批号:</label></td>
										<td class="width-35"><input id="cerNo" placeholder="请输入批号"  class="form-control" name="cerNo" type="text" value="${vo.cerNo }"/></td>
										<td class="width-15 active"><label class="pull-right">含量/浓度:</label></td>
										<td class="width-35"><input id="content" placeholder="请输入含量/浓度"  class="form-control" name="content" type="text" value="${vo.content }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">生产厂家:</label></td>
										<td class="width-35"><input id="producer" placeholder="请输入生产厂家"  class="form-control" name="producer" type="text" value="${vo.producer }"/></td>
										<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
										<td class="width-35"><input id="saveCondition" placeholder="请输入保存条件"  class="form-control" name="saveCondition" type="text" value="${vo.saveCondition }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">生产日期:</label></td>
										<td class="width-35">
											<div class="input-group date">
												<input id="mfg" placeholder="请输入生产日期"  class="form-control dateISO required" validate="required" name="mfg" type="text" value="${vo.mfg }"/>
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">有效期至:</label></td>
										<td class="width-35">
											<div class="input-group date">
												<input id="exp" placeholder="请输入有效期"  class="form-control dateISO required" validate="required" name="exp" type="text" value="${vo.exp }"/>
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">保&nbsp;管&nbsp;&nbsp;人:</label></td>
										<td>
											<div class="input-group">
												<input name="keeper" id="keeper" value="${vo.keeper}" placeholder="请选择保管人" type="text"  class="form-control" onclick="fnSelectUser()" /> 
												<input name="keepId" id="keepId" value="${vo.keepId}" type="hidden" />
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">参考单价:</label></td>
										<td class="width-35"><input id="price" placeholder="请输入参考单价"  class="form-control number" name="price" type="text" value="${vo.price }"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">购买年份:</label></td>
										<td class="width-35">
	                                        <input type="text" id="purDate" class="form-control "  placeholder="请输入年份" name="purDate" maxlength=20  value="${vo.purDate }">
	                                    </td>
	                                    <td class="width-15 active"><label class="pull-right">不确定度:</label></td>
										<td class="width-35">
	                                        <input type="text" id="notSure" class="form-control "   name="notSure"   value="${vo.notSure }">
	                                    </td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">备注:</label></td>
										<td class="width-35"><input maxlength="128" id="remark" placeholder="请输入备注"  class="form-control" name="remark" type="text" value="${vo.remark }"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-success" type="button" onclick="formsubmit2Save('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
                                    <button class="btn btn-w-m btn-primary"  type="button"  onclick="formsubmit();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
									<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
 <script type="text/javascript">
/*laydate.render({ 
  elem: '#mfg',
  type: 'month',
  format: 'yyyy-MM' //可任意组合
});
laydate.render({ 
	  elem: '#exp',
	  type: 'month',
	  format: 'yyyy-MM' //可任意组合
	});
*/
 function fnSelectUser(){
		var keepId=$('#keepId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?id='+keepId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#keepId').val(data.id);
				$('#keeper').val(data.name);
			}
		});
	}
	function fnSelectSupplier(){
		var supplierId=$('#supplierId').val();
		layer.open({
			title:'供应商选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/supplier/select.do?id='+supplierId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#supplierId').val(data.id);
				$('#supplier').val(data.name);
			}
		});
	}
	function formsubmit2Save(url){
		if(url.length>0){
			$("form").attr('action',url);
			$("form").submit();
		}else{
			layer.alert('传入的url有误!', {icon: 2,title:'系统提示',shadeClose: true});
		}
	}
	function formsubmit(){
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
	}
 </script>
</body>
</html>
