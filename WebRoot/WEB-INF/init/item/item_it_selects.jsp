<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目选择弹窗</title>
<%@ include file="../../include/css.jsp"%>
</head>
<body id="tab_context">
<div class="col-sm-12" style="height: 100%">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 0px;">
			<form action="" method="post" >
				<div class="jqGrid_wrapper" style="width: 300px;float: left;">
					<table id="listTable"></table>
				</div>
				<div id="im_div" style="width: 380px;float:left;overflow-y:auto;">
					<table class="table table-hover">
						<thead>
							<tr style="background-color: #FBFBFB">
								<th colspan="4">已选项目</th>
							</tr>
							<tr style="background-color: #FBFBFB">
								<th style="width: 45px;">序号</th>
								<th>项目</th>
								<th style="width: 45px;">操作</th>
							</tr>
						</thead>
						<tbody id="im_tb">
						
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="../../include/js.jsp"%>
<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
var page=0;
var global_ids='${vo.ids}';
	$(function() {
		var url = '${basePath}init/item/gridData4Im.do?isNow=${vo.isNow}';
		var sampTypeIds='${vo.sampTypeIds}';
		if(sampTypeIds!=''){
			url+="&sampTypeIds="+sampTypeIds;
		}
		var sampType='${vo.sampType}';//大类
		if(sampType!=''){
			url+="&sampType="+encodeURI(sampType);
		}
		var colNames = ['','','可选项目'];
		var colModel = [ 
			{
				name : 'sampTypeNames',
				index : 'sampTypeNames',
				hidden : true,
				title : false,
				search: false
			},{
				name : 'id',
				index : 'id',
				hidden : true,
				title : false,
				search: false
			},{
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions:{
					sopt : ['cn']
				}
			}];
		gridInitAuto(url, colNames, colModel, '', -1,'',false);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-85);
		$('#im_div').height(win.WinH-5);
	});
	//jqgrid 每次刷新  自动执行方法
	function loadComplete(data) {
		if(page==0){
			//首次获取ids 为项目方法关系组合，进行拆分
			var idArr=global_ids.split(',');
			var itemIds=[];
			var methodIds=[];
			for(var i=0;i<idArr.length;i++){
				if(idArr[i]=='')continue;
				var im=idArr[i].split('-');
				itemIds.push(im[0]);
				if(im.length<2||im[1]==''){
					methodIds.push('');
				}else{
					methodIds.push(im[1]);
				}
			}
			//获取右侧已选项目列表
			ajaxGetImList(itemIds.join(','));
			//给已选项目的方法select赋值
			for(var i=0;i<methodIds.length;i++){
				if(methodIds[i]!=''){
					$('#im_tb').find('td[key="'+itemIds[i]+'"]').find('select').val(methodIds[i]);
				}
			}
		}else{
			//检索结果 隐藏已添加得数据
			for (var i = 0; i < data.datas.length; i++) {
				if(hasChecked(data.datas[i].id)){
					setHiden(data.datas[i].id);
				}
			}
		}
		page++;
	}
	//获取项目方法集合
	function ajaxGetImList(idStr){
		var index=0;
		if($('#im_tb').find('tr').length>0){
			index=parseInt($('#im_tb').find('tr:last').attr('key'));
		}
		$.ajax({ 
			url:"${basePath}init/item/list4Im.do",
			data: {'ids':idStr},
			async:false,
			success: function(data){
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						index++;
						generateTabRow(data[i],index);
					}
				}
			},
			error:function(ajaxobj){  
				layer.msg('获取数据异常！', {icon: 0,time: 1000});
	    	}  
		});
	}
	//添加数据
	function generateTabRow(row,index){
		if(!hasChecked(row.id)){
			var opt='';
			if(row.mlist!=null&&row.mlist.length>0){
				for(var i=0;i<row.mlist.length;i++){
					var m=row.mlist[i];
					opt+='<option value="'+m.id+'">'+m.name+'</option>';
				}
			}
			$('#im_tb').append($('<tr key='+index+'>')
					.append('<td style="text-align: center;">'+index+'</td>')
					.append('<td><input type="hidden" name="itemId" value="'+row.id+'"><input type="hidden" name="itemName" value="'+row.name+'"><input type="hidden" name="price" value="'+row.price+'">'+row.name+'</td>')
					.append('<td style="text-align: center;"><a href="javascript:void(0);" onclick="removeTr(\''+row.id+'\',this);"><i class="fa fa-close text-danger"></i></a></td>'));
			setHiden(row.id);
		}
	}
	//检测是否已添加
	function hasChecked(id){
		var l=$('#im_tb').find('input[value="'+id+'"]').length;
		if(l==0){
			return false;
		}else{
			return true;
		}
	}
	function removeTr(id,obj){
		$(obj).parent().parent().remove();
		$('#listTable').setRowData(id,null,{display: 'block'});
		$('#'+id).width(300);
		$('#'+id).find('td:first').width(26);
		$('#'+id).find('td:last').width(270);
	}
	//jqGrid隐藏项目行
	function setHiden(id){
		 $('#listTable').setRowData(id,null,{display: 'none'});
	}
	//点击左侧选择项目时触发
	function pushToCheck(id){
		ajaxGetImList(id);
	}
	function fnSelect(){
		var itemIdArr =[];
		var itemNameArr=[];
		var imIdArr=[];
		var price=0;
		$('#im_tb').find("tr").each(function(){
			var itemId=$(this).find('input[name="itemId"]').val();
			var itemName=$(this).find('input[name="itemName"]').val();
			var p=$(this).find('input[name="price"]').val();
			itemIdArr.push(itemId);
			itemNameArr.push(itemName);
			price+=parseFloat(p);
		});
		var data={};
		data.id=itemIdArr.join(',');
		data.name=itemNameArr.join(',');
		data.price=price;
		return data;
	}
</script>
<script type="text/javascript">
function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
	$.jgrid.defaults.styleUI = "Bootstrap";
	var mygrid = $("#listTable").jqGrid({
		url : url,
		datatype : "json",
		mtype : "POST",
		colNames : colNames,
		colModel : colModel,
		rownumbers:true,
		rowNum : rowNum,
		rowList : [10,20,50,100],
		pager : pager,
		sortname : 'sort',
		sortorder : "asc",
		viewrecords : true,
		height:'auto',
		autowidth:true,
		recordpos : 'right',
		jsonReader : {
			root : 'datas'
		},
		shrinkToFit:true,    
        autoScroll: true,
        search:true,
		editurl:editurl,
		multiselect : multiselect,
		loadComplete : loadComplete,
		onSelectRow:function(id){
			 pushToCheck(id);
		}
	});
	setFilterToolbar();//行内查询
	setJgridWidth();//宽度
	setJgridHeight();//高度
	$(".ui-search-oper").hide();
}
</script>
</html>
