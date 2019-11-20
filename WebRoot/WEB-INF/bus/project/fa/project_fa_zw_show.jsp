<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
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
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
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
							<td class="width-15 active"><label class="pull-right">检测类型:</label></td>
							<td class="width-35">
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
							<td class="width-15 active"><label class="pull-right">项目频次:</label></td>
							<td class="width-35">
								${vo.pc} ${vo.pcUnit}
							</td>
							<td class="width-15 active"><label class="pull-right">单次周期:</label></td>
							<td class="width-35">
								${vo.cycle}
								${vo.cycleUnit}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					 <c:if test="${vo.xctk=='是'}">
                      	<div style="overflow-x: auto;">
                        	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;min-width: 1500px;">
								 <thead>
								 	<tr>
								 		<td colspan="14">现场调查记录表（表一）</td>
								 	</tr>
									<tr>
										<th width="50" rowspan="2">序号</th>
										<th style="min-width:100px;" rowspan="2">车间名称</th>
										<th style="min-width:100px;" rowspan="2">检测点名称</th>
										<th colspan="2">作业人数</th>
										<th rowspan="2" style="min-width:150px;" >生产设备</th>
										<th colspan="2">设备数量</th>
										<th rowspan="2" >危害因素</th>
										<th rowspan="2" width="50">接触<br>时间</th>
										<th rowspan="2" style="min-width:150px;" >防护设备</th>
										<th colspan="2">设备数量</th>
										<th rowspan="2" style="min-width:150px;" >防护用品<br>及佩戴情况</th>
									</tr>
									<tr>
										<th width="50">总数</th>
										<th width="50">数/班</th>
										<th width="50">总</th>
										<th width="50">开启</th>
										<th width="50">总</th>
										<th width="50">开启</th>
									</tr>
								</thead>
								<tbody id="point">
									<c:forEach items="${potList}" var="e" varStatus="v">
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
	                     
                              <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							  <thead>
							  	<tr>
							  		<td colspan="8">
							  			现场调查记录表（表二）
							  		</td>
							  	</tr>
								<tr>
									<th width="50">序号</th>
									<th width="100">类型</th>
									<th width="15%">物料名称</th>
									<th width="15%">主要成分及含量</th>
									<th width="15%">性状</th>
									<th width="15%">用量及使用<br>时间或产生量</th>
									<th width="100">所在车间</th>
									<th>接触方式</th>
								</tr>
							</thead>
							<tbody id="material">
								<c:forEach items="${mtList}" var="e" varStatus="v">
									<tr>
										<td>
											${e.sort}
											<input type="hidden" name="materialList[${v.index}].id"  value="${e.id}">
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
											${e.roomVo.name}
										</td>
										<td>
											${e.useType}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
	                     
                              <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							 <thead>
							 		<tr>
							 			<td colspan="11">
							 				劳动者工作日写实调查表
							 			</td>
							 		</tr>
									<tr>
										<th width="50">序号</th>
										<th>所在车间</th>
										<th>检测点</th>
										<th width="60">姓名</th>
										<th width="50">工龄</th>
										<th width="150">工作开始时间</th>
										<th width="150">工作结束时间</th>
										<th >工作内容</th>
										<th width="50px">耗费工时</th>
										<th >危害因素</th>
										<th >备注</th>
									</tr>
								</thead>
								<tbody id="work">
									<c:forEach items="${workList}" var="e" varStatus="v">
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
												${e.workNum}
											</td>
											<td>
												${e.itemNames}
											</td>
											<td>
												${e.remark}
											</td>
										</tr>
									</c:forEach>
								</tbody>
						</table>
					</c:if>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>方案信息</legend>
					</fieldset>
					<c:forEach items="${vo.schemeList}" var="e" varStatus="s">
                   		<div class="schemeDiv">
                   			<input type="hidden" name="schemeList[${s.index}].num" value="${e.num}" >
                   			<input type="hidden" name="schemeList[${s.index}].id" value="${e.id}" >
                   			<table class="table table-bordered" style="margin-bottom: -1px;">
								<c:if test="${fn:length(vo.schemeList)>1}">
									<tr style="background-color:#f5f5f5">
								 		<td colspan="8">
								 			&nbsp;&nbsp;<strong>第&nbsp;&nbsp;<font style="font-style:italic">${e.num}</font>&nbsp;&nbsp;期</strong>
								 		</td>
								 	</tr>
								</c:if>
								<tr>
									<td class="active"  style="width: 100px;"><label class="pull-right">开始日期:</label></td>
									<td>
										${e.startDate}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">截止日期:</label></td>
									<td>
										${e.endDate}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">检测费用:</label></td>
									<td>
										${e.fxPrice}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">采样费用:</label></td>
									<td>
										${e.cyPrice}
									</td>
								</tr>
								<tr>
									<td class="active" style="width: 100px;"><label class="pull-right">采样天数:</label></td>
									 <td>
										${e.cyDay}
									</td> 
								</tr>
							</table>
                  			<table class="table table-bordered">
                  				<thead>
								 	<tr>
								 		<th style="width: 50px;">序号</th>
								 		<th style="width: 15%;">检测岗位/车间</th>
								 		<th style="width: 15%;">检测点</th>
								 		<th style="width: 80px;">采样频次</th>
								 		<th >检测项目</th>
								 		<th style="width: 120px;">采样方式</th>
								 		<th style="width: 80px;">采样时长</th>
								 	</tr>
								 </thead>
								 <tbody id="detail_tb_${s.index}">
									<c:forEach items="${e.pointList}" var="e1" varStatus="s1">
										<tr index="${s.index}">
									 		<td>
									 			${e1.sort}
									 			<input type="hidden" name="schemeList[${s.index}].pointList[${s1.index}].id" value="${e1.id}">
									 		</td>
									 		<td>
									 			${e1.room}
									 		</td>
									 		<td>
									 			${e1.pointName}
									 		</td>
									 		<td>
									 			${e1.pc} ${e1.pcUnit}
									 		</td>
									 		<td>
									 			<a href="javascript:void(0);" onclick="showIM('${e1.id}');">${e1.itemName}</a>
									 		</td>
									 		<td>
									 			${e1.cyType}
									 		</td>
									 		<td>
									 			${e1.cyHours}（min）
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
                   			</table>
                   		</div>
                   	</c:forEach>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" >
						<tr>
							<td class="width-15 active"><label class="pull-right">费用合计:</label></td>
							<td class="width-35">
								${vo.invoiceVo.price}
							</td>
							<td class="width-15 active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
							<td class="width-35">
								${vo.invoiceVo.discount}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">交通费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.jtPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">报告费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.bgPrice}
							</td>
						</tr>
						<tr>
							
							<td class="width-15 active"><label class="pull-right">税费等其他费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.otherPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">优惠费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.yhPrice}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">费用总计:</label></td>
							<td class="width-35">
								${vo.invoiceVo.totalPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.htPrice}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">是否分包:</label></td>
							<td class="width-35">
								${vo.fb}
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传文件:</label></th>
							<td colspan="3" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.faMsg}
							</td>
						</tr>
					</table>
					<c:if test="${vo.fb=='是'}">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 250px;">分包单位</th>
							 		<th style="width: 100px;">联系人</th>
							 		<th style="width: 120px;">联系电话</th>
							 		<th >分包项目</th>
							 		<th style="width: 100px;">分包数量</th>
							 		<th style="width: 100px;">分包费用</th>
							 	</tr>
							 </thead>
							<tbody id="fb_tb">
								<c:forEach items="${vo.fbList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
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
								 		<td>
								 			${e.price}
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="7" style="text-align: right;"><div class="input-group" style="width: 100%"><label style="padding:6px 8px;">费用合计:</label><input style="width: 100px;float: right;" type="text" id="fbPrice" name="fbPrice" class="form-control" value="${vo.fbPrice}" readonly="readonly"></div></td>
								</tr>
							</tfoot>
						</table> 
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<!-- 最多5个部门可选 -->
						<tr>
								<td class="active"  style="width: 100px;"><label class="pull-right">评审部门1:</label></td>
								<td>
										${vo.orgName1}
								</td>
								<td class="active"  style="width: 100px;"><label class="pull-right">评审部门2:</label></td>
								<td>
										${vo.orgName2}
								</td>
								<td class="active"  style="width: 100px;"><label class="pull-right">评审部门3:</label></td>
								<td>
									${vo.orgName3}
								</td>
								<td class="active"  style="width: 100px;"><label class="pull-right">评审部门4:</label></td>
								<td>
										${vo.orgName4}
								</td>
							 </tr>
							<tr>
								<td class="active"  style="width: 100px;"><label class="pull-right">评审部门5:</label></td>
								<td>
										${vo.orgName5}
								</td>
							 </tr>
				   </table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<script type="text/javascript">
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
