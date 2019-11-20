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
	width: 60mm;
	margin: 2px 4px;
 }
.tm td{
	border:1px solid #333;
	color: #333;
	font-size: 10px;
	line-height:6mm;
	padding: 1px;
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
				 		 <div class="tm" style="width: 60mm;">
				 			<table style="border-collapse: collapse;width: 100%" cellspacing="0" >
				 				<tr>
				 					<td colspan="4" style="text-align:center;padding: 1px;">
				 						<span style="font-size: 12px;">安徽省中环检测有限公司<br>采样专用标签</span>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;width: 8mm">项目</td>
				 					<td  style="text-align:center;width: 25mm">${e.itemNames}</td>
				 					<td rowspan="2" style="width: 20mm;" id="ewm" align="center">
				 						<div class="img" id="img_${v.index}" key1="${e.sampCode}" key2="${e.itemNames}" ></div>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;">编号</td>
				 					<td  style="text-align:left;word-wrap:break-word;word-break:break-all;">${e.sampCode}</td>
				 				</tr>
				 				<tr>
				 					<td class="labelTd" style="text-align:center;">状态</td>
				 					<td colspan="3" >&nbsp;&nbsp;□&nbsp;未检&nbsp;&nbsp;□&nbsp;在检&nbsp;&nbsp;□&nbsp;检毕</td>
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
			width: 65,
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
	'.tm{margin: 5px;float: left;-webkit-box-sizing: border-box;border-collapse: collapse;}'+
	'.tm td{border:1px solid #333;color: #333;font-size: 10px;line-height:6mm;padding: 1px;}'+
	'</style>';
	$('.tm').each(function(){
		var htmlct=strBodyStyle+'<body><div class="tm">'+$(this).html()+'</div></body>';
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM('1mm','1mm','60mm','40mm',htmlct);
		var canvas=$(this).find('canvas')[0];
		LODOP.ADD_PRINT_IMAGE('17mm','37mm','65px','60px','<img border="0" src="'+canvas.toDataURL()+'"/>');
	});
	
};  
</script>
</body>
</html>
