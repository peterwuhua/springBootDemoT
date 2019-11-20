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
                       <form method="post" action="${fn:length(vo.id)>0? 'updateTab.do':'addTab.do'}" class="form-horizontal">
	                    <c:if test="${fn:length(vo.id)>0}">
	                    	<input name="id" value="${vo.id}" type="hidden" />
	                    </c:if>
	                    	<input name="customerVo.id" value="${vo.customerVo.id}" type="hidden" />
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">受检单位名称:</label></td>
										<td class="width-35"><input id="name" placeholder="请输入受检单位名称"  class="form-control required"  maxlength="128" name="clientVo.name" type="text" value="${vo.clientVo.name}"/></td>
										<td class="width-15 active"><label class="pull-right">所属行业:</label></td>
										<td class="width-35">
											<select id="industry" name="clientVo.industry" class="input-sm form-control input-s-sm inline" onclick="industrySelect();">
												<option value="${vo.clientVo.industry}">${vo.clientVo.industry}</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">区域:</label></td>
										<td class="width-35">
											<div class="input-group">
		                                        <div class="input-group-btn">
		                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect('${vo.clientVo.areaId}','${vo.clientVo.areaPath}')">选择</button>
		                                        </div>
		                                        <input type="text" readonly="readonly" id="selectPath" name="clientVo.areaPath" class="form-control" placeholder="请选择上级区域" value="${vo.clientVo.areaPath}">
		                                        <input type="hidden" id="selectId" name="clientVo.areaId" value="${vo.clientVo.areaId}">
		                                    </div>
										</td>
										<td class="width-15 active"><label class="pull-right">单位地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入受检单位地址"  class="form-control" maxlength="128" name="clientVo.address" type="text" value="${vo.clientVo.address}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-35"><input id="person" placeholder="请输入联系人"  class="form-control" maxlength="32" name="clientVo.person" type="text" value="${vo.clientVo.person}"/></td>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="clientVo.phone" type="text" value="${vo.clientVo.phone}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">邮箱:</label></td>
										<td class="width-35"><input id="email" placeholder="请输入邮箱"  class="form-control email" name="clientVo.email" type="text" value="${vo.clientVo.email}"/></td>
										<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
										<td class="width-35"><input id="remark" placeholder="请输入备注信息" maxlength="128" class="form-control" name="clientVo.remark" type="text" value="${vo.clientVo.remark}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
<script>
function fnSelect(pId,pName){
	layer.open({
	  title:'区域选择',	
	  type: 2,
	  area: ['300px', '300px'],
	  fix: false, //不固定
	  maxmin: true,
	  content: '${basePath}sys/area/select.do?parentVo.id='+pId+'&parentVo.name='+encodeURI(pName),
		  btn: ['确定','取消'], //按钮
		 btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
	});
}
var flagindustry = true;
function industrySelect() {
	if (!flagindustry) {
		return flase;
	}
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-sshy',
		datatype : "json",
		success : function(data) {
			var value = data.split(",");
			var optionstring = "";
			for (var i = 0; i < value.length; i++) {
				optionstring += "<option value=\"" + value[i] + "\" >"
						+ value[i] + "</option>";
			}
			$("#industry").html(optionstring);
			flagindustry = false;
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
</body>
</html>
