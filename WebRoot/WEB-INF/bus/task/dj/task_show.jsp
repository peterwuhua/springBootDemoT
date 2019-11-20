<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
							<td class="active" style="width: 150px;"><label class="pull-right">单位名称:</label></td>
							<td  style="width:30%;">
								${vo.custVo.custName}
								<input type="hidden" id="custName" name="custVo.custName" class="form-control" value="${vo.custVo.custName}"> 
								<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
							</td>
							 <td class="active" style="width: 150px;"><label class="pull-right">单位地址:</label></td>
							<td>
								${vo.custVo.custAddress}
								<input type="hidden" id="custAddress" name="custVo.custAddress" class="form-control" value="${vo.custVo.custAddress}"> 
							</td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.custUser}
								<input type="hidden" id="custUser" name="custVo.custUser" class="form-control" value="${vo.custVo.custUser}"> 
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custVo.custTel}
								<input type="hidden" id="custTel" name="custVo.custTel" class="form-control" value="${vo.custVo.custTel}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
							<td>
								${vo.custVo.industry}
								<input type="hidden" id="industry" name="custVo.industry" class="form-control" value="${vo.custVo.industry}"> 
							</td>
							<td class="active"><label class="pull-right">单位性质:</label></td>
							<td>
								${vo.custVo.attribute}
								<input type="hidden" id="attribute" name="custVo.attribute" class="form-control" value="${vo.custVo.attribute}"> 
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">主要产品:</label></td>
							<td colspan="3">
								${vo.custVo.product}
								<input type="hidden" id="product" name="custVo.product" class="form-control" value="${vo.custVo.product}"> 
							</td>
						</tr>
						<tr>
							<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
							<td class="active"><label class="pull-right">单位名称:</label></td>
							<td>
								${vo.custVo.wtName}
								<input type="hidden" id="wtCustName" name="custVo.wtName" class="form-control" value="${vo.custVo.wtName}"> 
								<input type="hidden" id="wtCustId" name="custVo.customerVo.id" value="${vo.custVo.customerVo.id}">
							</td>
							<td class="active"><label class="pull-right">单位地址:</label></td>
							<td>
								${vo.custVo.wtAddress}
								<input type="hidden" id="wtAddress" name="custVo.wtAddress" class="form-control" value="${vo.custVo.wtAddress}"> 
							</td>
							
						</tr>
						<tr>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.wtUser}
								<input type="hidden" id="wtUser" name="custVo.wtUser" class="form-control" value="${vo.custVo.wtUser}"> 
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custVo.wtTel}
								<input type="hidden" id="wtTel" name="custVo.wtTel" class="form-control" value="${vo.custVo.wtTel}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
							<td>
								${vo.custVo.wtEmail}
								<input type="hidden" id="wtEmail" name="custVo.wtEmail" class="form-control email" value="${vo.custVo.wtEmail}"> 
							</td>
							<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
							<td>
								${vo.custVo.wtZip}
								<input type="hidden" id="wtZip" name="custVo.wtZip" class="form-control" value="${vo.custVo.wtZip}"> 
							</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${vo.source=='送样' && vo.pj=='是'}">
									<td id="rowsTd" class="active" rowspan="11" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
								</c:when>
								<c:when test="${vo.pj=='是'}">
									<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
								</c:when>
								<c:when test="${vo.source=='送样'}">
									<td id="rowsTd" class="active" rowspan="10" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
								</c:when>
								<c:otherwise>
									<td id="rowsTd" class="active" rowspan="8" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
								</c:otherwise>
							</c:choose>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td colspan="3">
								${vo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品来源:</label></td>
							<td>
								${vo.source}
							</td>
						</tr>
						<c:if test="${vo.source=='送样'}">
							<tr>
								<td class="active"><label class="pull-right">样品处理要求:</label></td>
								<td>
									${vo.dealRequest}
								</td>
								<td class="active"><label class="pull-right">样品保存条件:</label></td>
								<td>
									${vo.saveRequest}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">送样人员:</label></td>
								<td>
									${vo.cyName}
								</td>
								<td class="active"><label class="pull-right">送样日期:</label></td>
								<td>
									${vo.cyDate}
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="active"><label class="pull-right">报告份数:</label></td>
							<td>
								${vo.reportNum}
							</td>
							<td class="active"><label class="pull-right">取报告方式:</label></td>
							<td>
								${vo.reportWay}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否加急:</label></td>
							<td>
								${vo.jj}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否评价:</label></td>
							<td>
								${vo.pj}
							</td>
							<td class="active"><label class="pull-right">是否分包:</label></td>
							<td>
								${vo.fb}
							</td>
						</tr>
						<c:if test="${vo.pj=='是'}">
							<tr>
								<td class="active pjTr"><label class="pull-right">评价标准:</label></td>
								<td colspan="3">
									${vo.standNames}
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="active"><label class="pull-right">受理人员:</label></td>
							<td>
								${vo.userName}
							</td>
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								${vo.date}
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td colspan="3" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td colspan="5">
								检测点位信息
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th>样品类型</th>
											<th>样品名称</th>
											<c:if test="${vo.source=='外采'}">
												<th>检测点位</th>
												<th>点位编号</th>
											</c:if>
											<th>检测项目</th>
											<th style="width: 130px;">采样单</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td>
													${e.sort}<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.sampTypeName}
												</td>
												<td>
													${e.sampName}
												</td>
												<c:if test="${vo.source=='外采'}">
													<td>
														${e.pointName}
													</td>
													<td>
														${e.pointCode}
													</td>
												</c:if>
												<td>
													<a href="javascript:void(0);" onclick="showIM('${e.id}');">${e.itemNames}</a>
												</td>
												<td> 
													<c:choose>
														<c:when test="${e.status=='2'}">
															<a class="btn btn-outline btn-success" type="button" onclick="fnPoint('${e.id}','${e.sampName}')">查看</a>
															<a class="btn btn-outline btn-info" type="button" onclick="fnPrint('${e.id}')">打印</a>
														</c:when>
														<c:otherwise>
															未填报
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<c:if test="${vo.fb=='是'}">
							<tr>
								<td colspan="5">
									分包信息
								</td>
							</tr>
							<tr>
								<td colspan="5">
									<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
										 <thead>
										 	<tr>
										 		<th style="width: 50px;">序号</th>
										 		<th style="width: 25%;">分包单位</th>
										 		<th style="width: 15%;">联系人</th>
										 		<th style="width: 15%;">联系电话</th>
										 		<th >分包项目</th>
										 		<th style="width: 15%;">分包数量</th>
										 	</tr>
										 </thead>
										<tbody id="fb_tb">
											<c:forEach items="${vo.fbList}" var="e" varStatus="s">
												<tr index="${s.index}">
											 		<td>
											 			${s.index+1}
											 			<input type="hidden" name="fbList[${s.index}].id" value="${e.id}">
											 		</td>
											 		<td>
											 			${e.fbVo.name}
											 		</td>
											 		<td>
											 			${e.fbUser}
											 		</td>
											 		<td>
											 			${e.fbMobile}
											 		</td>
											 		<td>
											 			${e.itemNames}
											 		</td>
													<td>
											 			${e.num}
											 		</td>					 		 
											 	</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</c:if>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<!-- Sweet alert -->
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript">
function showRwd(id){
	POBrowser.openWindow('${basePath}bus/task/showRwd.do?id='+id,'width=1200px;height=800px;');
}
//采样单
function fnPoint(pointId,sampName){
	parent.layer.open({
		title:'【'+sampName+'】采样单',	
		type: 2,
		area: ['90%', '80%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}bus/taskXc/showCyd.do?id='+pointId
	});
}
function fnPrint(pointId){
	parent.layer.open({
		title:'选择模板/打开已有文件',	
		type: 2,
		area: ['400px', '300px'],
		fix: true, //不固定
		maxmin: true,
		content: '${basePath}bus/taskXc/selectCyd.do?id='+pointId
	});
}
function showIM(id){
	parent.layer.open({
		title:'已选项目方法列表',	
		type: 2,
		area: ['700px', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}/bus/im/list4Im.do?busId='+id,
	});
}
</script>
</body>
</html>
