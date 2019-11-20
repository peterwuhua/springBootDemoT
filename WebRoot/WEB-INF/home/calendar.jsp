<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<style type="text/css">
#LDay td {
	height: 30px;
	text-align: center;
	font-family: "宋体";
	font-size: 12px;
}

#LDay div {
	cursor: pointer;
	width: 25px;
	line-height: 25px;
	margin: 0 auto;
}

.title {
	color: #5FB5FE;
	width: 14%;
}

#currentDay {
	font-family: "宋体";
	font-size: 36px;
	color: #E52355;
}

#currentWeekDay {
	font-size: 12px;
	color: #BCB8B9;
	font-weight: bold;
}

.LeftArrow {
	float: left;
	width: 7px;
	height: 10px;
	background-image: url(./h/img/left_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	margin: 10px;
	margin-right: 8px;
	cursor: pointer;
}

.LeftArrow2 {
	float: left;
	width: 7px;
	height: 10px;
	background-image: url(./h/img/left_hot_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	margin: 10px;
	margin-right: 8px;
	cursor: pointer;
}

.changeMonth {
	float: left;
	font-size: 16px;
	color: #E52355;
	height: 28px;
	line-height: 28px;
	margin-left: 10px;
	margin-right: 10px;
}

.RightArrow {
	float: left;
	width: 7px;
	height: 10px;
	background-image: url(./h/img/right_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	margin: 10px;
	margin-left: 8px;
	cursor: pointer;
}

.RightArrow2 {
	float: left;
	width: 7px;
	height: 10px;
	background-image: url(./h/img/right_hot_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	margin: 10px;
	margin-left: 8px;
	cursor: pointer;
}

.notSelectMonthDay {
	color: #BCB8B9;
}

.hand {
	cursor: pointer;
}

.hashPlanDiv {
	background-image: url(./h/img/green_circle_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	color: #fff;
}

.currentCalendar {
	background-image: url(./h/img/blue_circle_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
	color: #fff;
}

.currentSelect {
	background-image: url(./h/img/blue_ring_wev8.png);
	background-repeat: no-repeat;
	text-align: center;
}

.planDataEvent {
	height: 70px;
	width: 100%;
	overflow-y: hidden;
	position: relative;
}

.dataEvent {
	line-height: 30px;
	border-width: 0px;
	border-bottom: 1px;
	border-style: solid;
	border-color: #F3F2F2;
	float: left;
	height: 30px;
	width: 100%;
}

.dataEvent1 {
	line-height: 30px;
	float: left;
	height: 30px;
	width: 2px;
}

.dataEvent2 {
	line-height: 30px;
	float: left;
	height: 30px;
	width: 50px;
	background: #F5F5F5;
}

.dataEvent2_1 {
	margin-left: 5px;
}

.dataEvent3 {
	margin-left: 5px;
	height: 30px;
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

.addWorkPlan {
	border: none;
	BACKGROUND: url(./h/img/add_wev8.png) no-repeat;
	color: #333;
	WIDTH: 24px;
	height: 24px;
	text-align: left;
	cursor: pointer;
	padding-left: 10px;
	padding-top: 2px;
	margin-right: 5px;
	background-position: 50% 50%;
}

.addWorkPlan2 {
	border: none;
	BACKGROUND: url(./h/img/add_hot_wev8.png) no-repeat;
	color: #333;
	WIDTH: 24px;
	height: 24px;
	text-align: left;
	cursor: pointer;
	padding-left: 10px;
	padding-top: 2px;
	margin-right: 5px;
	background-position: 50% 50%;
}
TR.Spacing {
	HEIGHT: 1px
}
.paddingLeft0 {
    padding: 0px!important;
    height: 1px!important;
    background-color: #fff!important;
}
.intervalDivClass{
	height: 1px!important;
	background-color: #F3F2F2;
	/*background-image:url(./line_wev8.png);*/
	background-repeat:repeat-x;
}
</style>
</head>
<body onload="ShowTime();">
	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		<colgroup>
			<col width="">
		<tr>
			<td valign="top">
				<div>
					<table id="Calendar" width="100%" border=0>
						<tr>
							<td>
								<table height="80" width="100%">
									<tr align="center">
										<td class="hand" align="center" width="33%" onclick="todayClick()" title="今天"><span id="currentDay"></span> <br> <span id="currentWeekDay"></span></td>
										<td align="center" style="border-left: #E4E1E1 1px solid;">
											<div style="display: inline-block;">
												<div class="LeftArrow" id="prevbtn" title="上一个月" onclick="prev(this)"></div>
												<div class="changeMonth " id="showDate"></div>
												<div class="RightArrow" id="nextbtn" title="下一个月" onclick="next(this)"></div>
											</div>
										</td>
									</tr>
									<tr style="height: 1px !important;" class="Spacing">
										<td class="paddingLeft0" colspan="2">
											<div class="intervalDivClass"></div>
										</td>
									</tr>
								</table>
								<table id="LDay" width="100%">

								</table>

							</td>
						</tr>
						<tr style="height: 1px !important;" class="Spacing">
							<td class="paddingLeft0">
								<div class="intervalDivClass"></div>
							</td>
						</tr>
						<tr>
							<td style="height: 30px;padding: 5px 0px;">
							 <span id="stime" style="font-size: 14px;font-weight: bold;padding: 0px 70px 0px 20px;"></span><input class="addWorkPlan" type="button" onMouseOut="" onclick="doAdd()" title="添加">
							</td>
						</tr>
						<tr>
							<td style="text-align: left;">
								<div id="planDataEvent" class="planDataEvent" style="overflow-y:auto;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</td>
			</td>
		</tr>
	</table>
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	
	<script language="javascript">
	var planData=null;
	var currentToday=new Date();
	var pageDate=new Date();
	var lastOpt;
	var lastOptCss="";
	var calendarDialog;
	var currentSelectDate="";
	function initToday(){
        var todayD=currentToday.getDate();//本日
        var todayWD=currentToday.getDay();//本周几
        $("#currentDay").html(todayD);
 		$("#currentWeekDay").html(getWeekDay(todayWD));
	}
    //实现日历
    function calendar(showObj) {
    	clearData();
        var year = pageDate.getFullYear();      //选中年
        var month = pageDate.getMonth() + 1;    //选中月
        var day = pageDate.getDate();           //选中日
		var todayY=currentToday.getFullYear();//本年
		var todayM=currentToday.getMonth()+1;//本月
		var todayD=currentToday.getDate();//今天
		var todayStr=todayY+"-"+(todayM>9?todayM:"0"+todayM)+"-"+(todayD>9?todayD:"0"+todayD);
		var selectDate=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
 		if("7"=="7"||"7"=="9"){
 			$("#showDate").html(year+"年"+(month>9?month:"0"+month)+"月");
 		}else{
 			$("#showDate").html(year+"/"+(month>9?month:"0"+month));
 		}
 		//计算出vStart,vEnd具体时间
 		//选中月第一天是星期几（距星期日离开的天数）
        var startDay = new Date(year, month - 1, 1).getDay();
 		var firstdate = new Date(year, month-1, 1);//选中月第一天
 		var lastMonth = new Date(year, month-2, 1);//选中月上个月第一天
		var nextMonth = new Date(year, month, 1);//选中月下月第一天
		
		var lastStr = lastMonth.getFullYear()+"-"+((lastMonth.getMonth() + 1)>9?(lastMonth.getMonth() + 1):"0"+(lastMonth.getMonth() + 1)); 
		var currentStr=year+"-"+(month>9?month:"0"+month);
 		var nextStr = nextMonth.getFullYear()+"-"+((nextMonth.getMonth() + 1)>9?(nextMonth.getMonth() + 1):"0"+(nextMonth.getMonth() + 1));   
		
 		var lastMothStart = DateAdd("d", -startDay, firstdate).getDate();//日期第一天
 		var lastMothend = DateAdd("d", -1, firstdate).getDate();//上月的最后一天

        //本月有多少天(即最后一天的getDate()，但是最后一天不知道，我们可以用“上个月的0来表示本月的最后一天”)
        var nDays = new Date(year, month, 0).getDate();
 
        //开始画日历
        var numRow = 0;  //记录行的个数，到达7的时候创建tr
        var totalRow=1;
        var i;        //日期
        var html = '<tr><td class="title">日</td>'+
			        '<td class="title">一</td>'+
			        '<td class="title">二</td>'+
			        '<td class="title">三</td>'+
        			'<td class="title">四</td>'+
        			'<td class="title">五</td>'+
        			'<td class="title">六</td></tr>';
        //第一行
        html+='<tr style="height:1px!important;" class="Spacing"><td class="paddingLeft0" colspan="7"><div class="intervalDivClass" style="width:100%"></div></td></tr>';
        html += '<tr>';
        for (i = lastMothStart; startDay!=0&&i<=lastMothend; i++) {
            html += '<td  id="'+lastStr+'-'+i+'" onclick="prev(this)" data="">';
            html+='<div class="notSelectMonthDay " title="'+lastStr+'-'+i+'">';
            html+=i;
            html+='</div></td>';
            numRow++;
        }
        for (var j = 1; j <= nDays; j++) {
            if (year==todayY&&month==todayM&&j == todayD) {
                html += '<td id="'+currentStr+'-'+(j>9?j:'0'+j)+'" onclick="clickDate(this)" data="" >';  
                html += '<div class="currentCalendar" title="'+currentStr+'-'+(j>9?j:"0"+j)+'">';
            }
            else {
                html += '<td id="'+currentStr+'-'+(j>9?j:'0'+j)+'" onclick="clickDate(this)" data="">';
                html += '<div title="'+currentStr+'-'+(j>9?j:"0"+j)+'">';
            }
            html += j; 
            html += '</div></td>';
            numRow++;
            if (numRow == 7) {  //如果已经到一行（一周）了，重新创建tr
                numRow = 0;
                totalRow++;
                html += '</tr><tr>';
            }
        }
		//补充后一个月
        if(numRow>0){
        	for(var j=1;j<=7;j++){
	        	html += '<td  id="'+nextStr+'-0'+j+'" onclick="next(this)" data="">';
	        	html+='<div class="notSelectMonthDay " title="'+nextStr+'-0'+j+'">'+j+'</div></td>';
	            numRow++;
	        	if (numRow == 7) {  //如果已经到一行（一周）了，重新创建tr
	                numRow = 0;
	                html += '</tr>';
	                break;
	            }
        	}
        }
        $('#LDay').html(html);
        //标记选中日期
        if(showObj!='undefined'&&showObj!=undefined){
        	$('div[title="'+showObj+'"]').addClass("currentSelect");
        }else{
        	if(selectDate!=todayStr){
	        	$('div[title="'+selectDate+'"]').addClass("currentSelect");
        	}
        }
        //ajax获取数据
       $.post("${basePath}office/workPlan/listDate.do", {"selectdate":currentStr},function(data){
			var sd=$(".currentSelect").attr("title");
		 	if(sd==undefined||selectDate=='undefined'){
		 		sd=$(".currentCalendar").attr("title");
		 	}
	   		//var datas=data.dateevents;
	   		planData=data;
	   		for(var i=0;i<data.length;i++){
	   			$('#'+data[i]).children('div').eq(0).addClass("hashPlanDiv"); 
	   			$('#'+data[i]).attr("data",data[i]);
	   			if(data[i]==sd){
	   				showData($('#'+sd));
	   			}
	   		}
   		});
    }
    function clearData(){
    	$('#planDataEvent').html("暂无日程安排");
    }

    //点击日期
    function clickDate(obj){
    	showData(obj)
 		//reGetDate();
 	}
 	
 	function showData(obj){
 		if(lastOpt==undefined||lastOpt=='undefined'){

    	}else{
    	   $(lastOpt).children('div').eq(0).addClass(lastOptCss);	
    	}
    	
    	$('div').removeClass("currentSelect");
    	var divObj=$(obj).children('div').eq(0);
    	
    	lastOpt=obj;
    	lastOptCss=$(divObj).attr("class");
    	$(divObj).removeClass(lastOptCss);
    	$(divObj).addClass("currentSelect");
    	currentSelectDate=$(divObj).attr("title");
    	clearData();
    	$.post("${basePath}office/workPlan/listPlan.do",{"selectdate":$(obj).attr('id')},function(data){
    		var html='';
     		for(var key in data){
     			html+='<div class="hand dataEvent" onclick="clickData(\''+data[key].id+'\')" title="'+data[key].title+'">';
    		    html+='<div class="dataEvent1" style="background:#4e5d6c;"></div>';
    		    html+='<div class="dataEvent2" ><div class="dataEvent2_1">'+data[key].startTime+'</div></div>';
    		    html+='<div class="dataEvent3">'+data[key].title+'</div>';
    		    html+='</div>';
     		}
     		if(html=='') html="暂无日程安排";
     		$('#planDataEvent').html(html);
     	});
 	}
 	
 	var hasRfdh = false;
 	//点击数据
 	function clickData(id){
 		var url='${basePath}office/workPlan/show.do?id='+id;
 		 layer.open({
		  title:'工作计划',	
		  type: 2,
		  area: ['700px', '450px'],
		  fix: false, //不固定
		  maxmin: true,
		  content: url
		});
 	}
 function tc(d) {
      function zc(c, i) {
          var d = "666666888888aaaaaabbbbbbdddddda32929cc3333d96666e69999f0c2c2b1365fdd4477e67399eea2bbf5c7d67a367a994499b373b3cca2cce1c7e15229a36633cc8c66d9b399e6d1c2f029527a336699668cb399b3ccc2d1e12952a33366cc668cd999b3e6c2d1f01b887a22aa9959bfb391d5ccbde6e128754e32926265ad8999c9b1c2dfd00d78131096184cb05288cb8cb8e0ba52880066aa008cbf40b3d580d1e6b388880eaaaa11bfbf4dd5d588e6e6b8ab8b00d6ae00e0c240ebd780f3e7b3be6d00ee8800f2a640f7c480fadcb3b1440edd5511e6804deeaa88f5ccb8865a5aa87070be9494d4b8b8e5d4d47057708c6d8ca992a9c6b6c6ddd3dd4e5d6c6274878997a5b1bac3d0d6db5a69867083a894a2beb8c1d4d4dae54a716c5c8d8785aaa5aec6c3cedddb6e6e41898951a7a77dc4c4a8dcdccb8d6f47b08b59c4a883d8c5ace7dcce";
          return "#" + d.substring(c * 30 + i * 6, c * 30 + (i + 1) * 6);
      }
      return zc(d, 0);
  }
  
  
  function doAdd(){
	 layer.open({
	  title:'工作计划',	
	  type: 2,
	  area: ['700px', '490px'],
	  fix: false, //不固定
	  maxmin: true,
	  content: '${basePath}office/workPlan/edit.do?selectdate='+currentSelectDate,
	  btn: ['确定','取消'], //按钮
	  yes: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  iframeWin.submitSave();
	  },
	  end: function () {
          location.reload();
      }
	});
  }
  
 
 function refashDate(){
 	if(calendarDialog &&calendarDialog.closed && hasRfdh ){
 		hasRfdh = false;
   		reGetDate();
 	}
 }
 
 function reGetDate(){
 	var selectDate=$(".currentSelect").attr("title");
 	if(selectDate==undefined||selectDate=='undefined'){
 		selectDate=$(".currentCalendar").attr("title");
 	}
 	
	$.post("${basePath}office/workPlan/listPlan.do",{"selectdate":selectDate},function(data){
  		var datas=data.dateevents;
  		planData=data.events;
  		$(".hashPlanDiv").parent("TD").attr("data","");
  		$(".hashPlanDiv").removeClass("hashPlanDiv");
  		clearData();
  		var sd=$(".currentSelect").attr("title");
	 	if(sd==undefined||sd=='undefined'){
	 		sd=$(".currentCalendar").attr("title");
	 	}
  		for(var key in datas){
  			$('#'+key).children('div').eq(0).addClass("hashPlanDiv"); 
  			$('#'+key).attr("data",datas[key]);
  			if(key==sd){
  				showData($('#'+key));
  			}
  		}
 	});
 }
 
  
  function closeByHand(){
  	calendarDialog.close();	
  }
  
  function refreshCal(){
  	calendar();
	calendarDialog.close();	
	
}
        
 function getWeekDay(day){
 	var weekDay="";
    if(day==0){
  		weekDay="星期日";
    }else if(day==1){
    	weekDay="星期一";
    }else if(day==2){
    	weekDay="星期二";
    }else if(day==3){
    	weekDay="星期三";
    }else if(day==4){
    	weekDay="星期四";
    }else if(day==5){
    	weekDay="星期五";
    }else if(day==6){
    	weekDay="星期六";
    }
 	return weekDay;
 }
 function next(obj){
 	pageDate.setDate(1);//设置本月第一天
 	pageDate.setMonth(pageDate.getMonth() + 1);
 	var idv=$(obj).attr("id");
 	if(idv!='prevbtn'&&idv!='nextbtn'){
 		calendar(idv);
 	}else{
 		calendar();
 	}
 }
 
 function prev(obj){
 	pageDate.setDate(1);//设置本月第一天
 	 pageDate.setMonth(pageDate.getMonth() - 1);
 	var idv=$(obj).attr("id");
 	if(idv!='prevbtn'&&idv!='nextbtn'){
 		calendar(idv);
 	}else{
 		calendar();
 	}
 	
 }
 
 function todayClick(){
	pageDate=new Date(currentToday.getFullYear(),currentToday.getMonth(),currentToday.getDate());
	calendar();
 }
 
 function DateAdd(interval, number, idate) {
 	   var date=new Date(idate.getFullYear(),idate.getMonth(),idate.getDate());
       number = parseInt(number);
       switch (interval) {
           case "y": date.setFullYear(date.getFullYear() + number); break;
           case "m": date.setMonth(date.getMonth() + number); break;
           case "d": date.setDate(date.getDate() + number); break;
           case "w": date.setDate(date.getDate() + 7 * number); break;
           case "h": date.setHours(date.getHours() + number); break;
           case "n": date.setMinutes(date.getMinutes() + number); break;
           case "s": date.setSeconds(date.getSeconds() + number); break;
           case "l": date.setMilliseconds(date.getMilliseconds() + number); break;
       }
       return date;
   }
        
var isSubmit=false;

$(document).ready(function() {
	jQuery(".addWorkPlan").hover(function(){
		jQuery(this).addClass("addWorkPlan2");
	},function(){
		jQuery(this).removeClass("addWorkPlan2");
	});
	jQuery(".LeftArrow").hover(function(){
		jQuery(this).addClass("LeftArrow2");
	},function(){
		jQuery(this).removeClass("LeftArrow2");
	});
	jQuery(".RightArrow").hover(function(){
		jQuery(this).addClass("RightArrow2");
	},function(){
		jQuery(this).removeClass("RightArrow2");
	});
	 initToday();
	 calendar();
	 window.setInterval("refashDate()",100);
});
</script>
<script type="text/javascript">
	function check(val) {
		if (val < 10) {
			return ("0" + val);
		} else {
			return (val);
		}
	}
	function ShowTime() {
		var date = new Date();
		var hour = date.getHours();
		var minutes = date.getMinutes();
		var second = date.getSeconds();
		var sp='上午';
		if(hour>12){
			sp='下午';
		}
		var timestr =sp+" "+check(hour)+ ":" + check(minutes) + ":" + check(second);
		document.getElementById("stime").innerHTML = timestr;
		var timerID = setTimeout('ShowTime()', 1000);
	}
</script>
</body>
</html>

