<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <form method="post" action="${fn:length(vo.id)>0? 'updateTab.do':'addTab.do'}" class="form-horizontal" enctype="multipart/form-data">
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
		                    <input name="customerVo.id" value="${vo.customerVo.id}" type="hidden" />
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">合同名称 :</label></td>
										<td class="width-35"><input id="code" placeholder="请输入合同名称"  class="form-control required" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
										<td class="width-15 active"><label class="pull-right">合同编号 :</label></td>
										<td class="width-35"><input id="code" placeholder="请输入合同编号"  class="form-control" maxlength=64 name="code" type="text" value="${vo.code}"/></td>
	                                </tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">合同批次:</label></td>
										<td class="width-35"><input id="num" placeholder="请输入合同批次"  class="form-control" maxlength=16 name="num" type="text" value="${vo.num}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">开始日期:</label></td>
										<td class="width-35"><input id="sdate" placeholder="请输入开始日期"  class="form-control dateISO" maxlength=16 name="sdate" type="text" value="${vo.sdate}"/></td>
										<td class="width-15 active"><label class="pull-right">结束日期:</label></td>
										<td class="width-35"><input id="edime" placeholder="请输入结束日期"  class="form-control dateISO" maxlength=16 name="edime" type="text" value="${vo.edime}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">优惠折扣:</label></td>
										<td class="width-35"><input id="discount" placeholder="请输入优惠折扣"  class="form-control" maxlength=16 name="discount" type="text" value="${vo.discount}"/></td>
										<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
										<td class="width-35"><input id="contractSum" placeholder="请输入合同金额"  class="form-control" maxlength=16 name="contractSum" type="text" value="${vo.contractSum}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">付款类型:</label></td>
										<td class="width-35"><input id="payType" placeholder="请输入付款类型"  class="form-control" maxlength=32 name="payType" type="text" value="${vo.payType}"/></td>
										<td class="width-15 active"><label class="pull-right">付款方式:</label></td>
										<td class="width-35"><input id="payWay" placeholder="请输入付款方式"  class="form-control" maxlength=32 name="payWay" type="text" value="${vo.payWay}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-35"><input id="contacts" placeholder="请输入联系人"  class="form-control" maxlength=32 name="contacts" type="text" value="${vo.contacts}"/></td>
										<td class="width-15 active"><label class="pull-right">负责人:</label></td>
										<td class="width-35"><input id="headMan" placeholder="请输入负责人"  class="form-control" maxlength=32 name="headMan" type="text" value="${vo.headMan}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">执行状态:</label></td>
										<td class="width-35">
										<select id="status" name="status" class="input-sm form-control input-s-sm inline" onclick="conStatusSelect();">
											<option value="${vo.status}">${vo.status}</option>
										</select>
										</td>
										<td class="width-15 active"><label class="pull-right">备注:</label></td>
										<td><input id="remark" placeholder="请输入备注"  class="form-control" name="remark" maxlength=128 type="text" value="${vo.remark}"/></td>
									</tr>
									<%-- <tr>
										<td class="active"><label class="pull-right">合同原件:</label></td>
										<td>
											<input id="file" name="file"  class="form-control" placeholder="内容" type="file"/>
										</td>
										<td colspan="2">
											<c:if test="${fn:length(vo.path)>0}">
												<a href="download.do?filePath=${vo.path}&trueName=${vo.trueName}" class="btn btn-w-m btn-info">下载文件</a>
											</c:if>
										</td>	
									</tr> --%>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
<script>
/**
 * 通过公共代码获取合同执行状态
 */
var flag = false;
function conStatusSelect(){
	if(flag){
		return false;
	}
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=con-status',
		datatype : "json",
		success : function(data) {
			console.info(data);
			var value = data.split(",");
			var optionstring = "";
			var status = '${vo.status}';
			for (var i = 0; i < value.length; i++) {
				if(value[i] == status){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\" >"+ value[i] + "</option>";
				}else{
					optionstring += "<option value=\"" + value[i] + "\"  >"+ value[i] + "</option>";
				}
			}
			$("#status").html(optionstring);
			flag = true;
		}
	});
}	
</script>
<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function saveTable(_target){
		$("form").attr('target',_target);
		$("form").submit();
		parent.$("#customerId").val('${vo.customerVo.id}');
		//parent.layer.close(index);
	}
</script>
 <script>
	 $('input[type="file"]').prettyFile();
</script>
</body>
</html>
