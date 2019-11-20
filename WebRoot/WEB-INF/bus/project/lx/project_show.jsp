<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
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
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
								<td class="active" style="width: 15%;"><label class="pull-right">单位名称:</label></td>
								<td style="width: 35%">
									${vo.custVo.custName}
								</td>
								 <td class="active" style="width: 15%;"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.custAddress} 
								</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.custUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.custTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
									${vo.custVo.industry} 
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td>
									${vo.custVo.attribute}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3">
									${vo.custVo.product}
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
								<td class=" active"><label class="pull-right">单位名称:</label></td>
								<td >
									${vo.custVo.wtName}
								</td>
								<td class=" active"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.wtAddress}
								</td>
								
							</tr>
							<tr>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.wtUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.wtTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td>
									${vo.custVo.wtEmail}
								</td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td>
									${vo.custVo.wtZip}
								</td>
							</tr>
							<tr>
								<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
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
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td>
									${vo.pc} ${vo.pcUnit}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									${vo.rdate}
								</td>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td>
									${vo.cycle}
									${vo.cycleUnit}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td>
									${vo.reportNum}
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									${vo.finishDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">取报告方式:</label></td>
								<td>
									${vo.reportWay}
								</td>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									${vo.jj}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">样品来源:</label></td>
								<td>
									${vo.zsy}
								</td>
								<td class="active"><label class="pull-right ">同意使用非标准方法:</label></td>
								<td>
									${vo.fbzff}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									${vo.xctk}
								</td>
								<td class="active"><label class="pull-right ">是否评价:</label></td>
								<td>
									${vo.pj}
								</td>
							</tr>
							<tr id="pjTr">
								<td class="active"><label class="pull-right">评价依据:</label></td>
								<td colspan="3">
									${vo.standNames}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">委托方提供资料:</label></td>
								<td colspan="3">
									${vo.info}
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td colspan="3">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;"><label>受<br>理<br>方<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">立&nbsp;&nbsp;项&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right ">受理日期:</label></td>
								<td>
									${vo.sdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${vo.zsy=='送样'}">
						 <fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						 	<legend>样品信息</legend>
						 </fieldset>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							 <thead>
							 	<tr>
							 		<th width="50">序号</th>
							 		<th width="100">样品类型</th>
							 		<th>样品名称</th>
							 		<th>样品性状</th>
							 		<th>包装情况</th>
							 		<th>检测项目</th>
							 		<th>备注</th>
							 	</tr>
							 </thead>
							<tbody id="detail_tb">
								<c:forEach items="${vo.pointList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
								 		</td>
								 		<td>
								 			${e.sampTypeName}
								 		</td>
								 		<td>
								 			${e.sampName}
								 		</td>
								 		<td>
								 			${e.xz}
								 		</td>
								 		<td>
								 			${e.packing}
								 		</td>
								 		<td>
								 			<a href="javascript:void(0);" onclick="showIM('${e.id}');">${e.itemName}</a>
								 		</td>
								 		<td>
								 			${e.remark}
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
						</table>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<td class="active"><label class="pull-right">样品处理要求:</label></td>
								<td>
									${vo.schemeVo.dealRequest}
								</td>
								<td class="active"><label class="pull-right">样品保存条件:</label></td>
								<td>
									${vo.schemeVo.saveRequest}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">检测费用:</label></td>
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
								<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
								<td class="width-35">
									${vo.invoiceVo.htPrice}
								</td>
								<td class="active"><label class="pull-right ">是否分包:</label></td>
								<td>
									${vo.fb}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">送样人员:</label></td>
								<td class="width-35">${vo.schemeVo.cyUserName}</td>
								<td class="width-15 active"><label class="pull-right">送样时间:</label></td>
								<td class="width-35">
									${vo.schemeVo.cyDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他:</label></td>
								<td colspan="3">
									${vo.schemeVo.remark}
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
					</c:if>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<script type="text/javascript">
$('input[type="file"]').prettyFile();
function changedPj(){
	var radioVal='${vo.pj}';
	if(radioVal=='是'){
		$('#pjTr').show();
		$('#rowsTd').attr('rowspan',10);
	}else{
		$('#pjTr').hide();
		$('#rowsTd').attr('rowspan',9);
	}
};
$(document).ready(function() {
	changedPj();
	//多选框内删除
	$(".chosen-container").hover(function(){  
        $(this).addClass('chosen-container-active');  
    },function(){  
    	 $(this).removeClass('chosen-container-active');  
    })
    
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
	
});
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
