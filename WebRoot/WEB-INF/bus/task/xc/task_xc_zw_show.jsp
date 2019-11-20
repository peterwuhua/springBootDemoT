<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 2px;
}
.table > thead > tr > th{
    text-align: center;
}
#cyd_tb .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="page.do">现场采样</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 2px;">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								${vo.no}
							</td>
							 <td class="active"  style="width: 150px;"><label class="pull-right">受检单位:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.custUser}
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custVo.custTel}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">单位地址:</label></td>
							<td colspan="3">
								${vo.custVo.custAddress}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">开始采样时间:</label></td>
							<td class="width-35">
								${vo.cyDate}
							</td>
							<td class="width-15 active"><label class="pull-right">采样结束时间:</label></td>
							<td class="width-35">
								${vo.cyEndDate}
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;min-width: 1300px;">
							 <thead>
							 	<tr>
							 		<td colspan="14">现场情况</td>
							 	</tr>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 10%;">车间/岗位</th>
							 		<th style="width: 10%;">点位名称</th>
							 		<th style="width: 60px;">采样<br>数量</th>
							 		<th style="width: 100px;">采样方式</th>
							 		<th style="width: 80px;">采样时长（min）</th>
							 		<th style="width: 60px;">采样<br>频次</th>
							 		<th >接触职业病<br>危害因素</th>
							 		<th style="width: 60px;">作业<br>人数</th>
							 		<th style="width: 80px;">接触方式</th>
							 		<th style="width: 60px;">接触<br>频次</th>
							 		<th style="width: 65px;">接触时间（h/d）</th>
							 		<th style="width: 10%;">职业病防护设<br>施及运行情况</th>
							 		<th style="width: 10%;">个人防护用品<br>及佩戴情况</th>
							 	</tr>
							 </thead>
							 <tbody id="point_tb">
								<c:forEach items="${vo.tpList}" var="e" varStatus="v">
									<tr>
								 		<td align="center">
								 			${v.index+1}
								 			<input type="hidden" name="tpList[${v.index}].id" value="${e.id}">
								 		</td>
								 		<td>
								 			${e.room}
								 		</td>
								 		<td>
								 			${e.pointName}
								 		</td>
								 		<td align="center">
								 			${e.sampNum}
								 		</td>
								 		<td><input type="text" name="tpList[${v.index}].cyType" class="form-control" value="${e.cyType}"></td>
										<td><input type="text" name="tpList[${v.index}].cyHours" class="form-control digits" value="${e.cyHours}"></td>
										<td align="center">${e.pc}${e.pcUnit}</td>
										<td><input type="hidden" id="allItemId_${v.index}" name="tpList[${v.index}].allItemId" value="${e.allItemId}"> <textarea id="allItemName_${v.index}" name="tpList[${v.index}].allItemName" class="form-control" onclick="chooseItem('${v.index}');">${e.allItemName}</textarea></td>
										<td><input type="text" name="tpList[${v.index}].workNum" class="form-control" value="${e.workNum}"></td>
										<td><input type="text" name="tpList[${v.index}].workType" class="form-control" value="${e.workType}"></td>
										<td><input type="text" name="tpList[${v.index}].workPc" class="form-control" value="${e.workPc}"></td>
										<td><input type="text" name="tpList[${v.index}].workHours" class="form-control" value="${e.workHours}"></td>
										<td><textarea name="tpList[${v.index}].fh" class="form-control">${e.fh}</textarea></td>
										<td><textarea name="tpList[${v.index}].others" class="form-control">${e.others}</textarea></td>
								 	</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						 <thead>
						 	<tr>
						 		<td colspan="10">采样单列表
						 		</td>
						 	</tr>
						 	<tr>
						 		<th style="width: 50px;">序号</th>
						 		<th >车间/岗位</th>
						 		<th >点位名称</th>
						 		<th >检测项目</th>
						 		<th >采样日期</th>
						 		<th >采样数量</th>
						 		<th style="width:200px;">采样单</th>
						 	</tr>
						 </thead>
						 <tbody id="cyd_tb">
							<c:forEach items="${vo.cydList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${e.sort}
							 		</td>
							 		<td>
							 			${e.room}
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
							 		<td align="center">
								 		${e.cyDate}
							 		</td>
							 		<td align="center">
								 		${e.sampNum}
							 		</td>
									<td style="text-align: center;">
										<c:choose>
											<c:when test="${e.cySt=='2'}">
												<a class="btn btn-outline btn-success" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">已填报</a>
											</c:when>
											<c:when test="${e.cySt=='1'}">
												<a class="btn btn-outline btn-warning" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">填报中</a>
											</c:when>
											<c:otherwise>
												<a class="btn btn-outline btn-danger" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">未填报</a>
											</c:otherwise>
										</c:choose>
										<a class="btn btn-outline btn-info" type="button" onclick="fnPointShow('${e.id}','${e.pointName}-${e.cyDate}')">查看</a>
										<a class="btn btn-outline btn-info" type="button" onclick="fnShowFile('${e.id}')">下载打印</a>
									</td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">现场数据上报:</label></td>
							<td class="width-35">
								<c:choose>
									<c:when test="${vo.xcSt=='-1'}">
										无现场数据
									</c:when>
									<c:when test="${vo.xcSt=='2'}">
										<a class="btn btn-outline btn-success" type="button" onclick="fnItem()">已填报</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
									</c:when>
									<c:when  test="${vo.xcSt=='1'}">
										<a class="btn btn-outline btn-warning" type="button" onclick="fnItem()">填报中</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
									</c:when>
									<c:otherwise>
										<a class="btn btn-outline btn-danger" type="button" onclick="fnItem()">未填报</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">填报人员:</label></td>
							<td class="width-35">
								${vo.cyUserName}
							</td>
							<td class="width-15 active"><label class="pull-right">填报日期:</label></td>
							<td class="width-35">
									${vo.cyTime}
							</td>
						</tr>
						<tr>
						<th class="active"><label class="pull-right">上传附件:</label></th>
						<td colspan="3" id="removeFile"><c:forEach items="${vo.fileList}" var="e" varStatus="v">
								<div style="float: left; margin-right: 10px;">
									<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
								</div>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
						<td colspan="3">${vo.cyMsg}</td>
					</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
 <!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript">

//采样单
function fnPointShow(pointId,sampName){
        	parent.layer.open({
        		title:'【'+sampName+'】采样单',	
        		type: 2,
        		area: ['90%', '80%'],
        		fix: false, //不固定
        		maxmin: true,
        		content: '${basePath}bus/taskXc/showCyd4Zw.do?id='+pointId
        	});
}
//采样单
function fnPoint(pointId, sampName) {
	parent.layer.open({
		title : '【' + sampName + '】采样单',
		type : 2,
		area : [ '100%', '100%' ],
		fix : true,
		maxmin : false,
		content : '${basePath}bus/taskXc/editCyd4Zw.do?id='
				+ pointId,
		end : function() {
			location.reload();
		}
	});
}
function fnShowFile(cydId){
	parent.layer.open({
		title:'选择模板/打开已有文件',	
		type: 2,
		area: ['400px', '300px'],
		fix: true, //不固定
		maxmin: true,
		content: '${basePath}bus/sampCyd/selectZwCyd.do?id='+cydId
	});
}
function fnItem(){
	parent.layer.open({
		title : '现场分析记录单',
		type : 2,
		area : [ '100%', '100%' ],
		fix : true, //不固定
		maxmin : false,
		content : '${basePath}bus/taskXc/edit4Item.do?id=${vo.id}',
		end : function() {
			location.reload();
		}
	});
}
</script>


</body>
</html>
