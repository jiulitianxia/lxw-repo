$(function() {
   $(document).ready(function() { 
	   getAllUserInFo();
	}); 
   function getAllUserInFo(){
	 $.ajax({
      url:"/api/getAllUserInfoByList",
      data:{uid:$.cookie('uid')},
      async:true,
      cache:false,
      type:"POST",
      success:function(result){
	     var data = eval("(" + result + ")");
         if(data.result.status=="true"){
        	  for(i in data.result.dataList){ //data.data指的是数组，数组里是8个对象，i为数组的索引 {
                  var tr;
                  tr='<td>'+data.result.dataList[i].phone+'</td>'+'<td>'+data.result.dataList[i].money+'</td>'+'<td>'+
                  data.result.dataList[i].accounttimes+'</td>'+'<td>'+data.result.dataList[i].time+'</td>'+
                  '<td><a href="" class="glyphicon glyphicon-share-alt" style="text-decoration:none;"></a></td>'
                  $(".t_body").append('<tr>'+tr+'</tr>')
              }
         }else if(data.result.status=="false"){
        	 if(data.result.code=="0200"){
        		 toastr.error('获取用户信息失败！');
        	 }
          }
      }
    });	 
   }    
   function getSingleUserInfo(){
	 if(''==$('.form-control').val()){
		 toastr.error('请输入手机号！');
	 }
	 $.ajax({
      url:"/api/getUserInfoByAccount",
      data:{uid:$.cookie('uid'),phone:$('.form-control').val()},
      async:true,
      cache:false,
      type:"POST",
      success:function(result){
	     var data = eval("(" + result + ")");
         if(data.result.status=="true"){
        	  for(i in data.result.dataList){ //data.data指的是数组，数组里是8个对象，i为数组的索引 {
                  var tr;
                  tr='<td>'+data.result.dataList[i].phone+'</td>'+'<td>'+data.result.dataList[i].money+'</td>'+'<td>'+
                  data.result.dataList[i].accounttimes+'</td>'+'<td>'+data.result.dataList[i].time+'</td>'+
                  '<td><a href="" class="glyphicon glyphicon-share-alt" style="text-decoration:none;"></a></td>'
                  $(".t_body").append('<tr>'+tr+'</tr>')
              }
         }else if(data.result.status=="false"){
        	 if(data.result.code=="0201"){
        		 toastr.warning('无此用户信息！');
        	 }
          }
      }
    });	 
   }    
   $("#btnserch").click(function(e) {
	   getSingleUserInfo();
   });
   toastr.options = {  
	    closeButton: false,  // 是否显示关闭按钮，（提示框右上角关闭按钮）
	    debug: false,        // 是否使用deBug模式
	    progressBar: true,    // 是否显示进度条，（设置关闭的超时时间进度条）
	    positionClass: "toast-top-full-width",   // 设置提示款显示的位置
	    onclick: null,         // 点击消息框自定义事件 
	    showDuration: "80",   // 显示动画的时间
	    hideDuration: "1000",   //  消失的动画时间
	    timeOut: "1000",          //  自动关闭超时时间 
	    extendedTimeOut: "400",   //  加长展示时间
	    showEasing: "swing",      //  显示时的动画缓冲方式
	    hideEasing: "linear",      //   消失时的动画缓冲方式
	    showMethod: "fadeIn",     //   显示时的动画方式
	    hideMethod: "fadeOut"     //   消失时的动画方式
	};
});