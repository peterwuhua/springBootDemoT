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
                    <div class="ibox-title">
						<h5>
							<ol class="breadcrumb">
								<li><a href="javascript:backMainPage();">受检单位</a></li>
								<li><strong>编辑</strong></li>
							</ol>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
                    <div class="ibox-content">
                    	<%@ include file="../../include/status.jsp"%>
                       <form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal">
	                    <c:if test="${fn:length(vo.id)>0}">
	                    	<input name="id" value="${vo.id}" type="hidden" />
	                    </c:if>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<th class="width-15 active"><label class="pull-right">客户名称:</label></th>
										<td class="width-35">
										<div class="input-group">
	                                        <div class="input-group-btn">
	                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCustomer('${vo.customerVo.id}','${vo.customerVo.name}')">选择</button>
	                                        </div>
	                                        <input type="text" readonly="readonly" id="cusName" class="form-control" placeholder="请选择客户名称" value="${vo.customerVo.name}">
	                                        <input type="hidden" id="cusId" name="customerVo.id" value="${vo.customerVo.id}">
	                                    </div>
	                                    </td>
										<td class="width-15 active"><label class="pull-right">受检单位名称:</label></td>
										<td class="width-35"><input id="name" placeholder="请输入受检单位名称"  class="form-control required"  maxlength="128" name="clientVo.name" type="text" value="${vo.name}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">所属行业:</label></td>
										<td class="width-35">
											<select id="industry" name="clientVo.industry" class="input-sm form-control input-s-sm inline" onclick="industrySelect();">
												<option value="${vo.clientVo.industry}">${vo.clientVo.industry}</option>
											</select>
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
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">单位地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入受检单位地址"  class="form-control" maxlength="128" name="clientVo.address" type="text" value="${vo.clientVo.address}"/></td>
										<td class="width-15 active"><label class="pull-right">联系人:</label></td>
										<td class="width-35"><input id="person" placeholder="请输入联系人"  class="form-control" maxlength="32" name="clientVo.person" type="text" value="${vo.clientVo.person}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="clientVo.phone" type="text" value="${vo.clientVo.phone}"/></td>
										<td class="width-15 active"><label class="pull-right">邮箱:</label></td>
										<td class="width-35"><input id="email" placeholder="请输入邮箱"  class="form-control email" name="clientVo.email" type="text" value="${vo.clientVo.email}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
										<td class="width-35"><input id="remark" placeholder="请输入备注信息" maxlength="128" class="form-control" name="clientVo.remark" type="text" value="${vo.clientVo.remark}"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-success" type="button" onclick="formSubmit('save.do');">保存</button>
									<button class="btn btn-primary" type="submit">保存并返回</button>
									<a href="javascript:backMainPage();" class="btn btn-white" type="submit">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
    <script>
function fnSelectCustomer(pId,pName){
	layer.open({
		title:'客户信息选择',	
		type: 2,
		area: ['800px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}cus/customer/select.do?id='+pId+'&name='+pName,
		btn: ['确定','取消'],
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var selectData = iframeWin.fnSelect();
		  $("#cusName").val(selectData.name);
		  $("#cusId").val(selectData.id);
		}
	});
}
</script>
<script>
function fnSelect(pId,pName){
	layer.open({
	  title:'区域选择',	
	  type: 2,
	  area: ['300px', '470px'],
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
</body>
</html>
