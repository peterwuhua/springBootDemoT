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
				<form id="thisForm" method="post" action="gridEdit4Zw.do" class="form-horizontal">
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
								<td class="width-15 active"><label class="pull-right">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label></td>
								<td class="width-35">
									<input type="text" value="${vo.type}" id="type" name="type" class="form-control" placeholder="">
								</td>
							</tr>
							<tr>
								 <td colspan="4">标准浓度（mg/m3）</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">MAC:</label></td>
								<td>
							 		<input id="value3"   type="text" name="value3" value="${vo.value3}"  class="form-control" >
								</td>
								<td class="active"><label class="pull-right">PC-WTA:</label></td>
								<td>
							 		<input id="value" type="text" name="value" value="${vo.value}"  class="form-control number" >
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">PC-STEL:</label></td>
								<td>
							 		<input id="value2" type="text" name="value2" value="${vo.value2}"  class="form-control number" >
								</td>
								<td class="active"><label class="pull-right">超限倍数:</label></td>
								<td>
							 		<input id="maxValue" type="text" name="maxValue" class="form-control number" value="${vo.maxValue}" >
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td>
							 		<input id="sort" type="text" name="sort" value="${vo.sort}"  class="form-control" >
								</td>
							</tr>
							<tr>
								 <td colspan="4">
								 	<p>分类一般指类似粉尘类型等</p>
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
 
</script>
</html>