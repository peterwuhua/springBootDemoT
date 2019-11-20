<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form id="thisForm" method="post" action="gridEdit.do" class="form-horizontal">
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="padding: 0px;margin: 0px;">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">样品类型:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" readonly="readonly" id="sampTypeName" name="sampTypeName" value="${vo.sampTypeName}" class="form-control required" validate="required" placeholder="请选择"  onclick="fnSelectSampType()">
	                                      <input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
	                                      <div class="input-group-btn">
	                                          <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
	                                      </div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">指&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标:</label></td>
								<td class="width-35">
									<input type="text" value="${vo.itemType}" id="itemType" name="itemType" class="form-control" placeholder="">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label></td>
								<td>
									<input type="text" value="${vo.type}" id="type" name="type" class="form-control" placeholder="">
								</td>
								<td class="active"><label class="pull-right">其他分类:</label></td>
								<td>
									 <input type="text" value="${vo.otherType}" id="otherType" name="otherType" class="form-control" placeholder="">
								</td>
							</tr>
							<tr>
								 <td colspan="4">限值信息</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">限值类型:</label></td>
								<td>
									<select id="xzType" name="xzType" class="form-control" onchange="showDiv();">
										<c:choose>
											<c:when test="${vo.xzType=='数值'}">
												<option value="数值范围">数值范围</option>
												<option value="数值" selected="selected">数值</option>
												<option value="文字描述">文字描述</option>
											</c:when>
											<c:when test="${vo.xzType=='文字描述'}">
												<option value="数值范围">数值范围</option>
												<option value="数值">数值</option>
												<option value="文字描述" selected="selected">文字描述</option>
											</c:when>
											<c:otherwise>
												<option value="数值范围" selected="selected">数值范围</option>
												<option value="数值">数值</option>
												<option value="文字描述">文字描述</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
								<td class="active"><label class="pull-right">限值设置:</label></td>
								 <td>
								 	<div id="minDiv">
								 		<span>下限：</span>
								 		<select style="width: 60px;display: inline;" name="minFlag" class="form-control">
											<c:choose>
												<c:when test="${vo.minFlag=='>'}">
													<option value="&ge;">&ge;</option>
													<option value="&gt;" selected="selected">&gt;</option>
												</c:when>
												<c:otherwise>
													<option value="&ge;" selected="selected">&ge;</option>
													<option value="&gt;">&gt;</option>
												</c:otherwise>
											</c:choose>
								 		</select>
								 		<input id="minValue" style="width: 100px;display: inline;" type="text" name="minValue" class="form-control" value="${vo.minValue}">
								 	</div>
								 	<div id="maxDiv">
								 		<span>上限：</span>
								 		<select style="width: 60px;display: inline;" name="maxFlag" class="form-control">
											<c:choose>
												<c:when test="${vo.maxFlag=='<'}">
													<option value="&le;">&le;</option>
													<option value="&lt;" selected="selected">&lt;</option>
												</c:when>
												<c:otherwise>
													<option value="&le;" selected="selected">&le;</option>
													<option value="&lt;">&lt;</option>
												</c:otherwise>
											</c:choose>
								 		</select>
								 		<input id="maxValue" style="width: 100px;display: inline;" type="text" name="maxValue" class="form-control" value="${vo.maxValue}">
								 	</div>
								 	<div id="comDiv">
								 		<input id="flag" style="width: 60px;display: inline;" type="text" name="flag" class="form-control" value="=" readonly="readonly">
								 		<input id="value" style="width: 100px;display: inline;" type="text" name="value" class="form-control"  value="${vo.value}">
								 	</div>
								 </td>
							</tr>
							<c:if test="${vo.sampType=='气'||vo.sampType=='声'}">
								<tr>
									<td class="active"><label class="pull-right">其他限值:</label></td>
									<td>
										<select style="width: 80px;display: inline;" name="flag2" class="form-control">
											<c:choose>
												<c:when test="${vo.flag2=='≤'}">
													<option value="&le;" selected="selected">&le;</option>
													<option value="&lt;">&lt;</option>
													<option value="&ge;">&ge;</option>
													<option value="&gt;">&gt;</option>
													<option value="=">=</option>
													<option value="描述">描述</option>
												</c:when>
												<c:when test="${vo.flag2=='<'}">
													<option value="&le;">&le;</option>
													<option value="&lt;" selected="selected">&lt;</option>
													<option value="&ge;">&ge;</option>
													<option value="&gt;">&gt;</option>
													<option value="=">=</option>
													<option value="描述">描述</option>
												</c:when>
												<c:when test="${vo.flag2=='≥'}">
													<option value="&le;">&le;</option>
													<option value="&lt;">&lt;</option>
													<option value="&ge;" selected="selected">&ge;</option>
													<option value="&gt;">&gt;</option>
													<option value="=">=</option>
													<option value="描述">描述</option>
												</c:when>
												<c:when test="${vo.flag2=='>'}">
													<option value="&le;">&le;</option>
													<option value="&lt;">&lt;</option>
													<option value="&ge;">&ge;</option>
													<option value="&gt;"selected="selected">&gt;</option>
													<option value="=">=</option>
													<option value="描述">描述</option>
												</c:when>
												<c:when test="${vo.flag2=='='}">
													<option value="&le;">&le;</option>
													<option value="&lt;">&lt;</option>
													<option value="&ge;">&ge;</option>
													<option value="&gt;">&gt;</option>
													<option value="=">=</option>
													<option value="描述" selected="selected">描述</option>
												</c:when>
												<c:otherwise>
													<option value="&le;">&le;</option>
													<option value="&lt;">&lt;</option>
													<option value="&ge;">&ge;</option>
													<option value="&gt;">&gt;</option>
													<option value="=">=</option>
													<option value="描述" selected="selected">描述</option>
												</c:otherwise>
											</c:choose>
								 		</select>
								 		<input id="value2" style="width: 170px;display: inline;" type="text" name="value2" class="form-control" value="${vo.value2}" placeholder="气：浓度/ 声：夜间">
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td>
							 		<input id="sort" type="text" name="sort" value="${vo.sort}"  class="form-control" >
								</td>
							</tr>
							<tr>
								 <td colspan="4">
								 	<p>水：分类一般指<font color="red">A级、B级、C级</font>；指标一般指<font color="red">微生物指标、毒理指标...</font>等</p>
								 	<p>气：分类一般指<font color="red">一级、二级、三级</font>；指标为<font color="red">排气筒高度</font>；</p>
								 	<p>声：分类一般指<font color="red">功能区</font>；</p>
								 </td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
function fnSelectSampType(){
	layer.open({
		title:'分类选择',	
		type: 2,
		area: ['300', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: 'type_select.do?sampType=${vo.sampType}&sampTypeId='+$('#sampTypeId').val(),
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			 var data= iframeWin.fnSelect();
			 $('#sampTypeId').val(data.id);
			 $('#sampTypeName').val(data.name);
			 reload(data.id);
		}
	});
}
 
var index = parent.layer.getFrameIndex(window.name); 
function fnSelect(){
	var xzType=$('#xzType').val();
	if(xzType=='数值范围'){
		var maxValue=$('#maxValue').val();
		var minValue=$('#minValue').val();
		var reg=/^[+-]?((\d+)|(\d+\.\d+))$/;
		if(maxValue=='' && minValue==''){
			 layer.msg('最大或最小值不能为空', {icon: 0,time: 3000});
			 return null;
		}else if(maxValue!='' && !reg.test(maxValue)){
			layer.msg('最大值输入格式不正确', {icon: 0,time: 3000});
			 return null;
		}else if(minValue!='' && !reg.test(minValue)){
			layer.msg('最小值输入格式不正确', {icon: 0,time: 3000});
			 return null;
		}
	}
	var b = $("form").FormValidate();
	if(b){
		 $('form').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    parent.layer.close(index);
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
}
 
function showDiv(){
	var xzType=$('#xzType').val();
	if(xzType=='数值范围'){
		$('#minDiv').show();
		$('#maxDiv').show();
		$('#comDiv').hide();
	}else if(xzType=='数值'){
		$('#minDiv').hide();
		$('#maxDiv').hide();
		$('#comDiv').show();
		$('#flag').show();
		$('#value').css('width','100px');
		$('#value').addClass('number');
		$('#flag').show();
	}else{
		$('#minDiv').hide();
		$('#maxDiv').hide();
		$('#flag').hide();
		$('#value').css('width','100%');
		$('#value').removeClass('number');
		$('#comDiv').show();
	}
}

$(function() {
	showDiv();
});
</script>
</html>