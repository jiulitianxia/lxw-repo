$(function() {
	$(function(){ 
		getAllUserInFo($.getURLParam("phone"));
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
   
   
   var currentPage = 1;
   var pageSize = 13;
   var totalPages=0;
   var tempphone ;
   function getAllUserInFo(phone) {
       $.ajax({
           url: "/api/getUserApplyByList",
           data: {
             page: currentPage,
             pageSize: pageSize,
             uid:$.cookie('uid'),
             phone:phone
           },
 	      async:true,
 	      cache:false,
 	      type:"POST",
           success: function (result) {
               // 将数据渲染到页面
        	 tempphone =phone;
      	     var data = eval("(" + result + ")");
               if(data.result.status=="true"){
              	  for(i in data.result.dataList){ //data.data指的是数组，数组里是8个对象，i为数组的索引 {
                        var tr;
                        tr='<td>'+data.result.dataList[i].phone+'</td>'+'<td>'+
                        data.result.dataList[i].accounttimes+'</td>'+'<td>'+data.result.dataList[i].time+'</td>'
                        $(".t_body").append('<tr>'+tr+'</tr>')
                    }
               }else if(data.result.status=="false"){
              	 if(data.result.code=="0200"){
              		 toastr.error('获取用户信息失败！');
              	 }
                }
               totalPages =Math.ceil(data.result.total/pageSize);//总页数
               // 调用分页函数.参数:当前所在页, 总页数(用总条数 除以 每页显示多少条,在向上取整), ajax函数
               
               setPage(currentPage, Math.ceil(data.result.total/pageSize), getAllUserInFo)
           }
       })
   }

   /**
    *
    * @param pageCurrent 当前所在页
    * @param pageSum 总页数
    * @param callback 调用ajax
    */
   function setPage(pageCurrent, pageSum, callback) {
       $(".pages").bootstrapPaginator({
           //设置版本号
           bootstrapMajorVersion: 3,
           // 显示第几页
           currentPage: pageCurrent,
           // 总页数
           totalPages: pageSum,
           //当单击操作按钮的时候, 执行该函数, 调用ajax渲染页面
           onPageClicked: function (event,originalEvent,type,page) {
               // 把当前点击的页码赋值给currentPage, 调用ajax,渲染页面
               currentPage = page
               callback && callback()
           }
       })
   }
   $(".next").click(function(e) {
	   if(currentPage<totalPages){
	    currentPage+=1;
	    $(".t_body").html('');
	    getAllUserInFo(tempphone);
	   }
   });
   $(".previous").click(function(e) {
	   if(currentPage>1){
	    currentPage=currentPage-1;
	    $(".t_body").html('');
	    getAllUserInFo(tempphone);
	   }
   });
   $("#backbtn").click(function(e) {
	   $(location).attr('href', '/yf/home');
	  });
});