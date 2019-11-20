<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? '/res/supplierEvaluate/update2File.do':'/res/supplierEvaluate/add2File.do'}" class="form-horizontal" enctype="multipart/form-data">
					<input type="hidden" name="supplierVo.id" id="supplierVo.id" value="${vo.supplierVo.id}" /> 
					<input type="hidden" name="id" id="id" class="formText" value="${vo.id}" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">供应商名称:</label></td>
								<td class="width-35">${vo.supplierVo.name }</td>
								<td class="width-15 active"><label class="pull-right">供应商编号 :</label></td>
								<td class="width-35">${vo.supplierVo.no }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">商品名称:</label></td>
								<td><input id="tradeName" placeholder="请输入商品名称" class="form-control" name="tradeName" type="text" value="${vo.tradeName}" /></td>
								<td class=" active"><label class="pull-right">供应时间 :</label></td>
								<td><input id="supTime" placeholder="请输入供应时间" class="form-control dateISO" name="supTime" type="text" value="${vo.supTime}" /></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">评价时间 :</label></td>
								<td><input id="evaluateTime" placeholder="请输入评价时间" class="form-control dateISO"  name="evaluateTime" type="text" value="${vo.evaluateTime}" /></td>
								<td class=" active"><label class="pull-right">评价人:</label></td>
								<td><input id="evaluatePerson" placeholder="请输入评价人" class="form-control" name="evaluatePerson" type="text" value="${vo.evaluatePerson}" /></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">价额:</label></td>
								<td>
									<select class="form-control" id="price" name="price">
										<c:choose>
											<c:when test="${vo.price=='高'}">
												<option value="高" selected="selected">高</option>
												<option value="中">中</option>
												<option value="低">低</option>
											</c:when>
											<c:when test="${vo.price=='中'}">
												<option value="高">高</option>
												<option value="中" selected="selected">中</option>
												<option value="低">低</option>
											</c:when>
											<c:otherwise>
												<option value="高">高</option>
												<option value="中">中</option>
												<option value="低" selected="selected">低</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
								<td class=" active"><label class="pull-right">交货期:</label></td>
								<td>
									<select class="form-control">
										<c:choose>
											<c:when test="${'很慢' != vo.deliveryTime}">
												<option value="及时" selected="selected">及时</option>
												<option value="很慢">很慢</option>
											</c:when>
											<c:otherwise>
												<option value="及时">及时</option>
												<option value="很慢" selected="selected">很慢</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>	
								<td class=" active"><label class="pull-right">产品质量:</label></td>
								<td>
									<select class="form-control" id="productQuality" name="productQuality">
										<c:choose>
											<c:when test="${'一星级' == vo.productQuality}">
												<option value="一星级" selected="selected">一星级</option>
												<option value="二星级">二星级</option>
												<option value="三星级">三星级</option>
												<option value="四星级">四星级</option>
												<option value="五星级">五星级</option>
											</c:when>
											<c:when test="${'二星级' == vo.productQuality}">
												<option value="一星级">一星级</option>
												<option value="二星级" selected="selected">二星级</option>
												<option value="三星级">三星级</option>
												<option value="四星级">四星级</option>
												<option value="五星级">五星级</option>
											</c:when>
											<c:when test="${'三星级' == vo.productQuality}">
												<option value="一星级">一星级</option>
												<option value="二星级">二星级</option>
												<option value="三星级" selected="selected">三星级</option>
												<option value="四星级">四星级</option>
												<option value="五星级">五星级</option>
											</c:when>
											<c:when test="${'四星级' == vo.productQuality}">
												<option value="一星级">一星级</option>
												<option value="二星级">二星级</option>
												<option value="三星级">三星级</option>
												<option value="四星级" selected="selected">四星级</option>
												<option value="五星级">五星级</option>
											</c:when>
											<c:otherwise>
												<option value="一星级">一星级</option>
												<option value="二星级">二星级</option>
												<option value="三星级">三星级</option>
												<option value="四星级">四星级</option>
												<option value="五星级" selected="selected">五星级</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
								<td class=" active"><label class="pull-right">服务质量:</label></td>
								<td>
									<select class="form-control" id="serviceQuality" name="serviceQuality">
										<c:choose>
											<c:when test="${'一般' != vo.serviceQuality}">
												<option value="好" selected="selected">好</option>
												<option value="一般">一般</option>
											</c:when>
											<c:otherwise>
												<option value="好" >好</option>
												<option value="一般" selected="selected">一般</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评价内容:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">上传附件:</label></td>
								<td>
									<input id="file" name="file"  class="form-control" placeholder="内容" type="file"/>
								</td>
								<td colspan="2">
									<c:if test="${fn:length(vo.filePath)>0}">
										<a href="download.do?filePath=${vo.filePath}&trueName=${vo.fileName}" class="btn btn-w-m btn-info">下载附件</a>
									</c:if>
								</td>	
							</tr>
						</tbody>
					</table>
                 </form>
             </div>
         </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
<script>
</script>
 <script>
	 $('input[type="file"]').prettyFile();
	 
	var index = parent.layer.getFrameIndex(window.name); 
	function fnSelect(){
		var t = $("#thisForm").FormValidate();
		if(t){
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    		parent.layer.msg(res.message, {icon: 0,time: 3000})
					parent.jqgridReload();
					parent.layer.close(index);
		        }else{
		        	layer.msg(res.message, {icon: 0,time: 3000});
		        }
			});
		}
	}
</script>
</body>
</html>
