<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
a{
	color: blue;
}
 
.table > thead > tr > th{
text-align: center;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
							<td class="width-35">
								${vo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>车间 / 岗位</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="20%">车间名称</th>
											<th width="20%">工作制度</th>
											<th width="20%">工作方式</th>
											<th>工作内容</th>
										</tr>
									</thead>
									<tbody id="room">
										<c:forEach items="${vo.roomList}" var="e" varStatus="v">
											<tr>
												<td>
													${e.sort}
													<input type="hidden" name="roomList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													${e.name}
												</td>
												<td>
													${e.workType}
												</td>
												<td>
													${e.workWay}
												</td>
												<td>
													${e.works}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>检测点</label></td>
						</tr>
					</table>	
					<div style="overflow-x:scroll;">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;min-width: 1800px">
							<thead>
								<tr>
									<th width="50" rowspan="2">序号</th>
									<th width="100" rowspan="2">车间名称</th>
									<th width="100" rowspan="2">检测点名称</th>
									<th colspan="2">作业人数</th>
									<th rowspan="2" width="150">生产设备</th>
									<th colspan="2">设备数量</th>
									<th rowspan="2" >危害因素</th>
									<th rowspan="2" width="80">接触时间</th>
									<th rowspan="2" width="150">防护设备</th>
									<th colspan="2">设备数量</th>
									<th rowspan="2" width="150">防护用品<br>及佩戴情况</th>
								</tr>
								<tr>
									<th width="80">总数</th>
									<th width="80">数/班</th>
									<th width="80">总</th>
									<th width="80">开启</th>
									<th width="80">总</th>
									<th width="80">开启</th>
								</tr>
							</thead>
							<tbody id="point">
								<c:forEach items="${vo.potList}" var="e" varStatus="v">
									<tr>
										<td>
											${e.sort}
											<input type="hidden" name="potList[${v.index}].id"  value="${e.id}">
										</td>
										<td>
											${e.roomVo.name}
										</td>
										<td>
											${e.name}
										</td>
										<td>
											${e.workTal}
										</td>
										<td>
											${e.workNum}
										</td>
										<td>
											${e.productName}
										</td>
										<td>
											${e.productTal}
										</td>
										<td>
											${e.productNum}
										</td>
										<td>
											${e.itemNames}
										</td>
										<td>
											${e.workHours}
										</td>
										<td>
											${e.fhName}
										</td>
										<td>
											${e.fhTal}
										</td>
										<td>
											${e.fhNum}
										</td>
										<td>
											${e.others}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">	 
						<tr>
							<td colspan="4"><label>物料信息</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="100">所在车间</th>
											<th width="100">类型</th>
											<th width="15%">物料名称</th>
											<th width="15%">主要成分及含量</th>
											<th width="15%">性状</th>
											<th width="15%">用量及使用<br>时间或产生量</th>
											<th>接触方式</th>
										</tr>
									</thead>
									<tbody id="material">
										<c:forEach items="${vo.materialList}" var="e" varStatus="v">
											<tr>
												<td>
													${e.sort}
													<input type="hidden" name="materialList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													${e.roomVo.name}
												</td>
												<td>
													${e.type}
												</td>
												<td>
													${e.name}
												</td>
												<td>
													${e.cts}
												</td>
												<td>
													${e.xz}
												</td>
												<td>
													${e.yl}
												</td>
												<td>
													${e.useType}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>写实调查信息</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="100">所在车间</th>
											<th width="100">检测点</th>
											<th width="100">姓名</th>
											<th width="50">工龄</th>
											<th width="100">工作开始时间</th>
											<th width="100">工作结束时间</th>
											<th width="15%">工作内容</th>
											<th >备注</th>
										</tr>
									</thead>
									<tbody id="work">
										<c:forEach items="${vo.workList}" var="e" varStatus="v">
											<tr>
												<td>
													${e.sort}
													<input type="hidden" name="workList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													${e.roomVo.name}
												</td>
												<td>
													${e.pointVo.name}
												</td>
												<td>
													${e.user}
												</td>
												<td>
													${e.age}
												</td>
												<td>
													${e.startTime}
												</td>
												<td>
													${e.endTime}
												</td>
												<td>
													${e.works}
												</td>
												<td>
													${e.remark}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" style="width: 10%"><label class="pull-right">调&nbsp;查&nbsp;&nbsp;人:</label></td>
							<td class="" style="width: 23%">
								${vo.tkUserName}
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">陪同人:</label></td>
							<td class="" style="width: 23%">
								${vo.custVo.tkJdr}
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">调查日期:</label></td>
							<td class="" >
								${vo.tkDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td class="width-35" colspan="5">
								${vo.tkMsg}
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td colspan="5" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script>
		function fnShow(id,no){
			parent.layer.open({
				title:'项目详情【'+no+'】',	
				type: 2,
				area: ['1000px','85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/bus/project/show.do?id='+id
			});
		}
		
</script>
</body>
</html>
