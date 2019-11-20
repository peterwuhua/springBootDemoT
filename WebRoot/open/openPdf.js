/**
 * 打开PDF 弹窗
 * @param uri
 * @param allName
 */
function openPdf(uri,allName){
	var url = uri+'?allName='+allName;
	 layer.confirm("请选择打开方式", {icon:3, title:'系统提示',btn:['当前窗口打开','新窗口打开']
	 },function(index, layero){
		 var index2 = layer.open({
			  type: 2,
			  content: url,
			  area: ['893px', '600px'],
			  shadeClose: true,
			  maxmin: true,
			  fix: false, //不固定
			  resize: true
			});
			/*layer.full(index);*/
		 layer.close(index);
	 }, function(index){
		 window.open(url);
		 layer.close(index);
	 });
}
