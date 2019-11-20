<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<style type="text/css">
body{
	line-height: inherit;
}
.tm{
	float: left;
	-webkit-box-sizing: border-box;
	border-collapse: collapse;
	width: 80mm;
	margin: 2px 4px;
 }
.tm td{
	border:1px solid #333;
	color: #333;
	font-size: 10px;
	line-height:6mm;
	padding-left:1px;
	padding-right:1px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="form-group">
				<div class="col-sm-12" style="text-align: center;">
					<a class="btn btn-w-m btn-info" href="javascript:;" onclick="prn1_preview()"><i class="fa fa-search" aria-hidden="true"></i> 打印预览</a>
					<a class="btn btn-w-m btn-info" href="javascript:;" onclick="prn_Design()"><i class="fa fa-search" aria-hidden="true"></i> 打印设计</a>
					<!-- <a class="btn btn-w-m btn-success" href="javascript:;" onclick="prn1_print()"><i class="fa fa-print" aria-hidden="true"></i> 直接打印</a> -->
				</div>
			</div>
			<div class="ibox-content">
				 <div id="tm_div">
				 	<c:forEach items="${sampList}" var="e" varStatus="v">
				 		 <div class="tm" style="width: 80mm;">
				 			<table style="border-collapse: collapse;width: 100%" cellspacing="0" >
				 				<tr>
				 					<td colspan="5" style="text-align:center;">
				 						<span style="font-size: 14px;">安徽省中环检测有限公司<br>采样专用标签</span>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;width:20mm">样品编号</td>
				 					<td style="text-align:left;font-size: 12px;padding-left: 5px;" colspan="3">${e.sampCode}</td>
				 					<td rowspan="3" id="ewm" style="text-align:center;width:20mm;padding-top: 2px;">
				 						<div class="img" id="img_${v.index}" key1="${e.sampCode}" key2="${e.itemNames}" ></div>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;">采样日期</td>
				 					<td style="text-align:left;font-size: 12px;padding-left: 5px;" colspan="3" >${e.cyDate}</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;">样品名称</td>
				 					<td style="text-align:left;font-size: 12px;padding-left: 5px;" colspan="3" >${e.sampName}</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;">检测项目</td>
				 					<td colspan="4"  style="text-align:left;font-size: 12px;padding-left: 5px;">${e.itemNames}</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;">固&nbsp;&nbsp;定&nbsp;&nbsp;剂</td>
				 					<td  colspan="4" style="text-align:left;font-size: 12px;padding-left: 5px;">${e.tjj}</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;font-size: 12px;">样品状态</td>
				 					<td colspan="4" style="text-align:left;font-size: 12px;">&nbsp;&nbsp;□&nbsp;未检&nbsp;&nbsp;□&nbsp;在检&nbsp;&nbsp;□&nbsp;检毕</td>
				 				</tr>
				 			</table>
				 		</div>
				 	</c:forEach>
				 </div>
			</div>
		</div>
	</div>
<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}h/js/plugins/qrcode/jquery.qrcode.min.js"></script>
<script src="${basePath}static/js/Lodop/LodopFuncs.js?basePath=${basePath}"></script>

<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="${basePath}static/js/Lodop/install_lodop64.exe"></embed>
</object>

<script type="text/javascript">
	$('.img').each(function(n){
		var sampCode=$('#img_'+n).attr('key1');
		var itemName=$('#img_'+n).attr('key2');
		fnTm(n,sampCode,itemName);
	});
	function fnTm(i,sampCode,itemName){
		$("#img_"+i).empty();
		$("#img_"+i).qrcode({
			width: 60,
			height:60,
			correctLevel:1,
			text: toUtf8("样品编号:"+sampCode+";\r\n"+"检测项目:"+itemName+";")//任意内容
		});
	}
	function toUtf8(str) {
	    var out, i, len, c;   
	    out = "";   
	    len = str.length;   
	    for(i = 0; i < len; i++) {   
	    	c = str.charCodeAt(i);   
	    	if ((c >= 0x0001) && (c <= 0x007F)) {   
	        	out += str.charAt(i);   
	    	} else if (c > 0x07FF) {   
	        	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
	        	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
	        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
	    	} else {
	        	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
	        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
	    	}   
	    }
	    return out;   
	}
 
</script>

<script type="text/javascript">
function prn1_preview() {	
	CreateOneFormPage();	
	LODOP.PREVIEW();	
};
function prn1_print() {		
	CreateOneFormPage();
	LODOP.PRINT();	
};
function prn_Design() {		
	CreateOneFormPage();
	LODOP.PRINT_DESIGN();		
};	
function CreateOneFormPage(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("条码打印");
	var strBodyStyle='<style type="text/css" id="style">'+
	'body{line-height: inherit;}'+
	'.tm{margin: 5px;float: left;-webkit-box-sizing: border-box;border-collapse: collapse;width: 80mm;}'+
	'.tm td{border:1px solid #333;color: #333;font-size: 12px;line-height:6mm;}'+
	'</style>';
	$('.tm').each(function(){
		var htmlct=strBodyStyle+'<body><div class="tm">'+$(this).html()+'</div></body>';
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM('2mm','0mm','100mm','90mm',htmlct);
		var canvas=$(this).find('canvas')[0];
		LODOP.ADD_PRINT_IMAGE('18mm','62mm','64px','64px','<img border="0" src="'+canvas.toDataURL()+'"/>');
	});
	
};  
</script>
</body>
</html>
