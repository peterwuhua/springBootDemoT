$(function() {
	$(window).bind("resize", function() {
		setJgridWidth();
	})
	initButtonIco();
});
/**
 * 设置grid宽度
 */
function setJgridWidth() {
	var width = $(".jqGrid_wrapper").width();
	$("#listTable").setGridWidth(width);
}
/**
 * 设置grid高度
 */
function setJgridHeight() {
	var win = getWinSize();
	$("#listTable").jqGrid('setGridHeight', win.WinH-245);
} 
/**
 * 设置行内查询
 */
function setFilterToolbar() {
	jQuery("#listTable").jqGrid('filterToolbar',{searchOperators : true});
}
/**
 * 设置行内编辑
 */
function setNavGrid() {
	//jQuery("#listTable").jqGrid('navGrid','#pager',{add:true,edit:true,del:true,search:false,refresh:true});
}
/**
 * 获取选中数据数组
 */
function getSelectIds() {
	return $('#listTable').jqGrid('getGridParam', 'selarrrow');
}
/**
 *  获取选中数据
 */
function getSelectStrIds() {
	return $('#listTable').jqGrid('getGridParam', 'selarrrow').join(',');
}
/**
 * 获取选择行数据
 */
function getSelectId() {
	return $('#listTable').jqGrid('getGridParam', 'selrow');
}
/**
 * 设置选择行
 */
function setSelection(id) {
	return $('#listTable').jqGrid('setSelection',id);
}
/**
 * 获取当前行数据
 */
function getSelectRowData(id) {
	return $("#grid-table").jqGrid("getRowData",id)
}
/**
 * 设置选择行
 */
function setSelections(ids) {
	var ids = ids.replace("，",",");
	var s = ids.split(",");
	for ( var i = 0; i <= s.length; i++){
		 $('#listTable').jqGrid('setSelection',s[i]);
	}
}
/**
 * 行内回调方法
 */
function gridComplete() {}
/**
 * 回调方法
 */
function loadComplete(data) {}
/**
 * 导出
 * @param source 模板
 * @param target 模板文件
 * @param type 类型
 */
function jqgridExport(source,target,type){
	var uri = "export.do?_type="+type+'&_source='+source+'&_target='+target;
	fnExport(uri,source,target,type);
}
/**
 * 导出
 * @param uri 路径
 * @param source 模板
 * @param target 模板
 * @param type 类型
 */
function jqgridExportOther(url,source,target,type){
	var uri = "";
	if(null != url|| url.length>0){
		uri = url;
	}else{
		uri = "export.do";
	}
	uri += "?_type="+type+'&_source='+source+'&_target='+target;
	fnExport(uri,source,target,type);
}
/**
 * 导出
 * @param uri 路径
 * @param source 模板
 * @param target 模板
 * @param type 类型(0 导出所有 1导出选中数据 2导出当前数据 3自定义导出)
 */
function fnExport(uri,source,target,type){
	if('0'==type){//所有数据
		uri +='&orderColumn='+$('#listTable').jqGrid('getGridParam', 'sortname');
		uri +='&order='+$('#listTable').jqGrid('getGridParam', 'sortorder');
		$("#listForm").attr("action",uri);
		$("#listForm").submit();
	}else if('1'==type){//选中数据
		var selectIds = getSelectIds();
		//console.info(selectIds);
		if(selectIds.length<1){
			layer.msg('请选择要导出的记录', {icon: 0,time: 3000});
			return false;
		}else{
			$("#ids").val(selectIds);
		}
		uri +='&orderColumn='+$('#listTable').jqGrid('getGridParam', 'sortname');
		uri +='&order='+$('#listTable').jqGrid('getGridParam', 'sortorder');
		$("#listForm").attr("action",uri);
		$("#listForm").submit();
	}else if('2'==type){//当前显示数据
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		uri = "exportGridData.do?_type="+type+'&_source='+source+'&_target='+target;
		uri+="&rows="+postData.rows; 
		uri+="&page="+postData.page;
		uri+="&sidx="+postData.sidx;
		uri+="&sord="+postData.sord;
		if(typeof(postData.filters)!="undefined")
			uri+="&filters="+postData.filters;
		$("#listForm").attr("action",uri);
		$("#listForm").submit();
	}else{
		alert('请传入正确的参数');
	}
}
/**
 * 获取自定义导出列
 */
function fnSelectSpecial (){
	layer.open({
	  title:'自定义导出列',	
	  type: 2,
	  area: ['800px', '470px'],
	  fix: false, //不固定
	  maxmin: true,
	  content:'${basePath}sys/code/select.do',
	  btn: ['取消']
	})
}
/**
 * 刷新
 */
function jqgridReload(){
	 var postData = $("#listTable").jqGrid("getGridParam", "postData");
	 delete postData.filters;
	$(".ui-search-table .form-control").each(function (index,domEle){
		$(this).val("");
	});
	jQuery("#listTable").trigger("reloadGrid");
}
/**
 * 删除
 */
function jqgridDelete(){
	var selectIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
	if(selectIds.length<1){
		layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
		return false;
	}
	layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
		$("#ids").val(selectIds);
		$("#listForm").attr("action","update2del.do");
		$("#listForm").submit();
	});
}
/**
 * 初始化
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param editurl 修改URL
 * @param isPull 是否可拖拽行
 */
function pageInit4Pull(url, colNames, colModel, editurl,isPull) {
	gridInit(url, colNames, colModel, editurl, 20,'#pager',true,isPull);
}
/**
 * 初始化
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param editurl 修改URL
 */
function pageInit(url, colNames, colModel, editurl) {
	gridInit(url, colNames, colModel, editurl, 20,'#pager',true,false);
}
/**
 * 初始化(获取全部数据)
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param multiselect 是否可以多选
 */
function listAllInit(url, colNames, colModel, multiselect) {
	gridInitAuto(url, colNames, colModel, '', -1,'',true);
}

/**
 * 初始化(获取{rowNum}条数据)
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param colModel 获取多少条数据
 * @param multiselect 是否可以多选
 */
function listInit(url, colNames, colModel, rowNum ,multiselect) {
	gridInit(url, colNames, colModel, '',rowNum,'',multiselect,false)
}
/**
 * 初始化
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param editurl 修改URL
 * @param rowNum 每页显示条数
 * @param pager 当前页
 * @param multiselect 是否可以多选
 */
function gridInit(url, colNames, colModel, editurl,rowNum,pager,multiselect,isPull) {
	$.jgrid.defaults.styleUI = "Bootstrap";
	var mygrid = $("#listTable").jqGrid({
		url : url,
		datatype : "json",
		mtype : "POST",
		colNames : colNames,
		colModel : colModel,
		rownumbers:true,
		rowNum : rowNum,
		rowList : [10,20,50,100,1000],
		pager : pager,
		sortname : 'sort',
		sortorder : "asc",
		viewrecords : true,
		height:'auto',
		recordpos : 'right',
		jsonReader : {
			root : 'datas'
		},
		shrinkToFit:false,    
        autoScroll: true,  
        search:true,
		editurl:editurl,
		multiselect : multiselect,
		gridComplete : gridComplete,
		loadComplete : loadComplete
	});
	setFilterToolbar();//行内查询
	setJgridWidth();//宽度
	setJgridHeight();//高度
	setNavGrid();//功能按钮
	jQuery("#listTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });//横向滚动条 
	jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	$(".ui-search-oper").hide();
	fncleanName();
	if(!!isPull){
		fnUpdate4Pull();
	}
}
/**
 * 初始化 宽度自动化
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param editurl 修改URL
 * @param rowNum 每页显示条数
 * @param pager 当前页
 * @param multiselect 是否可以多选
 */
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
		rowList : [10,20,50,100,1000],
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
		gridComplete : gridComplete,
		loadComplete : loadComplete
	});
	setFilterToolbar();//行内查询
	setJgridWidth();//宽度
	setJgridHeight();//高度
	setNavGrid();//功能按钮
	//jQuery("#listTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });//横向滚动条 
	jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	$(".ui-search-oper").hide();
	fncleanName();
}
/**
 * 初始化 宽度自动化
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param multiselect 多选
 * @param pager 当前页
 */
function gridInitMin(url, colNames, colModel,multiselect) {
	$.jgrid.defaults.styleUI = "Bootstrap";
	var mygrid = $("#listTable").jqGrid({
		url : url,
		datatype : "json",
		mtype : "POST",
		colNames : colNames,
		colModel : colModel,
		rownumbers:true,
		rowNum : 20,
		rowList : [10,20,50,100,1000],
		pager : '#pager',
		sortname : 'lastUpdTime',
		sortorder : "desc",
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
		multiselect : multiselect,
		gridComplete : gridComplete,
		loadComplete : loadComplete
	});
	setFilterToolbar();//行内查询
	setJgridWidth();//宽度
	setJgridHeight();//高度
	jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	$(".ui-search-oper").hide();
	//fncleanName();
}
/**
 * 初始化 宽度自动化
 * 不带查询框的
 * @param url url
 * @param colNames 列模型
 * @param colModel 数据模型
 * @param multiselect 多选
 * @param pager 当前页
 */
function gridInitMin2(url, colNames, colModel,multiselect) {
	$.jgrid.defaults.styleUI = "Bootstrap";
	var mygrid = $("#listTable").jqGrid({
		url : url,
		datatype : "json",
		mtype : "POST",
		colNames : colNames,
		colModel : colModel,
		rownumbers:true,
		rowNum : 20,
		rowList : [10,20,50,100,1000],
		pager : '#pager',
		sortname : 'lastUpdTime',
		sortorder : "desc",
		viewrecords : true,
		height:'auto',
		autowidth:true,
		recordpos : 'right',
		jsonReader : {
			root : 'datas'
		},
		shrinkToFit:true,    
        autoScroll: true,
        search:false,
		gridComplete : gridComplete,
		loadComplete : loadComplete
	});
	setJgridWidth();//宽度
	var win = getWinSize();
	$("#listTable").jqGrid('setGridHeight', win.WinH-220);
}
function fncleanName(){
	//当有锁定列时清空锁定列的input的name值
	jQuery(".frozen-div").eq(0).find("input.form-control").attr("name","");
}
function fnUpdate4Pull(){
	jQuery("#listTable").jqGrid('sortableRows',{
		update:function(e, ui){
			array = [];               
			select_item = ui.item; //当前拖动的元素
			var select_id = select_item.attr("id");
			var select_rowData = $("#listTable").jqGrid("getRowData",select_id);//拖动行的数据
			select_sort = select_rowData.sort; //当前元素的顺序
			place_item = select_item.next('tr');//新位置下的下一个元素
			var place_id = place_item.attr("id");
			var place_rowData = $("#listTable").jqGrid("getRowData",place_id);//拖动行的数据
			place_sort = place_rowData.sort;
			place_sx = parseInt(place_sort);
			select_sx = parseInt(select_sort);
			if(select_sx > place_sx){ //说明是 向上移动
			temp = place_sort;
			place_sx = select_sort;//最大
			select_sx = temp;//最小
			flag = false;
			}else{ //向下移动
				place_item = select_item.prev('tr');
			    place_id = place_item.attr("id");
				place_rowData = $("#listTable").jqGrid("getRowData",place_id);//拖动行的数据
				place_sort = place_rowData.sort;
				place_sx = parseInt(place_sort);
			    flag = true;
			}
			/*array.push(select_id);
			array.push(place_id);*/
		 	$.ajax({
			    url:'update4Pull.do',
			    type:'POST',
			    async: false,
			    data:{"selectId":select_id,"placeId":place_id},
	            datatype:'json',
	            success:function(data){
	            	if("error"===data.status){
	            		layer.msg(data.message, {icon: 2,time: 3000 });
	            	}
	            	jqgridReload();
		        },
	            error:function(data){
	            	jqgridReload();
	            	layer.msg(data.message, {icon: 2,time: 3000 });
	            }
	       }); 
		},
		scroll:true
	});
}

function addRevampSort(id){
	jQuery("#listTable").find("#"+id).find("td[aria-describedby=listTable_sort]").on('click',revampSort);
}
revampSort = function fnRevampSort(){
	var id = $(this).parent("tr").attr("id");
	var rowData = $("#listTable").jqGrid("getRowData",id);
	var sort = rowData.sort;
	jQuery("#listTable").jqGrid('setSelection', id.toString());
	layer.prompt({formType: 0,value:sort,title: '请输入值',}, 
		function(value, index, elem){
			var reg = new RegExp("^[0-9]*$");  
		    if(!reg.test(value) || value.length<=0){ 
		         return false;
			}
			$.ajax({
			    url:'update4Sort.do',
			    type:'POST',
			    async: false,
			    data:{'id':id,'sort':value},
			    datatype:'json',
			    success:function(data){
			    	if("error"===data.status){
			    		layer.msg(data.message, {icon: 2,time: 3000 });
			    	}
			    	jqgridReload();
			    },
			    error:function(data){
			    	jqgridReload();
					layer.msg(data.message, {icon: 2,time: 3000 });   
			    }
			});
	  layer.close(index);
	});
}
function initButtonIco(){
	 var a=$(".btn");
	   $.each(a,function(i,e){
//		   $(e).addClass('btn-sm');
		   var val=$(e).text().trim();
		   if(val=='刷新'){
			   $(e).html("<i class='fa fa-refresh'> 刷新<i>");
		   }else if(val=='新增'){
			   $(e).html("<i class='fa fa-plus'> 新增<i>");
		   }else if(val=='删除'){
			   $(e).html("<i class='fa fa-trash-o'> 删除<i>");
		   }else if(val=='导入'){
			   $(e).html("<i class='fa fa-level-down'> 导入<i>");
		   }else if(val=='导出'){
			   $(e).html("<i class='fa fa-level-up'> 导出<i>");
		   }
		  // else if(val=='关联检测方法'){
		//	   $(e).html("<i class='fa  fa-ticket'> 关联检测方法<i>");
		  // }
		   else if(val=='提交'){
			   $(e).html("<i class='fa  fa-check'> 提交<i>");
		   }else if(val=='复制'){
			   $(e).html("<i class='fa  fa-copy'> 复制<i>");
		   }else if(val=='标签打印'){
			   $(e).html("<i class='fa  fa-print'> 标签打印<i>");
		   }else if(val=='审核'){
			   $(e).html("<i class='fa  fa-eye'> 审核<i>");
		   }
		  // else if(val=='按项目下达'){
		  //   $(e).html("<i class='fa  fa-sign-out'> 按项目下达<i>");
		  // }else if(val=='按样品下达'){
		  //   $(e).html("<i class='fa  fa-sign-out'> 按样品下达<i>");
		  // }else if(val=='按项目分配'){
		  //   $(e).html("<i class='fa  fa-sign-out'> 按项目分配<i>");
		  // }else if(val=='按样品分配'){
		  //   $(e).html("<i class='fa  fa-sign-out'> 按样品分配<i>");
		  // }
		   else if(val=='接收'){
			   $(e).html("<i class='fa  fa-sign-in'> 接收<i>");
		   }
		 /*  else if(val=='数据录入'){
			   $(e).html("<i class='fa  fa-pencil-square-o'> 数据录入<i>");
		   }else if(val=='数据校对'){
			   $(e).html("<i class='fa  fa-arrows-v'> 数据校对<i>");
		   }else if(val=='数据审核'){
			   $(e).html("<i class='fa  fa-eye'> 数据审核<i>");
		   }else if(val=='编制'){
			   $(e).html("<i class='fa  fa-pencil-square-o'> 编制<i>");
		   }else if(val=='批量入库'){
			   $(e).html("<i class='fa  fa-barcode'> 批量入库<i>");
		   }else if(val=='扫码批量入库'){
			   $(e).html("<i class='fa  fa-barcode'> 扫码批量入库<i>");
		   }else if(val=='扫码单条入库'){
			   $(e).html("<i class='fa  fa-barcode'> 扫码单条入库<i>");
		   }else if(val=='批量出库'){
			   $(e).html("<i class='fa  fa-barcode'> 批量出库<i>");
		   }else if(val=='扫码批量出库'){
			   $(e).html("<i class='fa  fa-barcode'> 扫码批量出库<i>");
		   }else if(val=='扫码单条出库'){
			   $(e).html("<i class='fa  fa-barcode'> 扫码单条出库<i>");
		   }else if(val=='批量归还'){
			   $(e).html("<i class='fa  fa-toggle-left'> 批量归还<i>");
		   }*/
		   else if(val=='批量退回'){
			   $(e).html("<i class='fa  fa-toggle-left'> 批量退回<i>");
		   }else if(val=='终止'){
			   $(e).html("<i class='fa fa-close'> 终止<i>");
		   }else if(val=='赋权'){
			   $(e).html("<i class='fa fa-key'> 赋权<i>");
		   }
	   });
}

