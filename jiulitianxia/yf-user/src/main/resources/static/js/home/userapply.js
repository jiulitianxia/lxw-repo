$(function() {
	$(function(){ 
		funcEdit();
		$("#selmoney").prop("disabled", true);
		$("#inphone").prop("disabled", true);
		$("#selcishu").prop("disabled", true);
		$("#cishu").prop("disabled", true);
		$('.spinner .btn:first-of-type').on('click', function() {
			if($.getURLParam("account")>parseInt($('.spinner input').val(), 10)){
				$('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
			}
		  });
		  $('.spinner .btn:last-of-type').on('click', function() {
			  if(parseInt($('.spinner input').val(), 10)>0){
				  $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
			  }
		  });
	}); 
	/**
	 * 初始化次数
	 */
	funcEdit = function (){
		 var phone=$.getURLParam("phone");
         var money=$.getURLParam("money");
         var account=$.getURLParam("account");
		 $("#inphone").val(phone);	
		 $("#selmoney").val(money);	
		 $("#selcishu").val(account);
	}
	function addnotice(e){
	  e.preventDefault();
	  e.stopPropagation();
	  return $.growl.notice({
		title:"使用成功",
	    message: "正在跳转用户信息列表!"
	  });
   }
	
   $("#editbtn").click(function(e) {
	 if(''==$('#inphone').val()){
		 toastr.error('请输入手机号！');
	 }else if($('#cishu').val()==0){
		 toastr.error('使用次数不能小于 1！');
	 }else{
		$.ajax({
	      url:"/api/userapply",
	      data:{uid:$.cookie('uid'),phone:$('#inphone').val(),account:$('#cishu').val()},
	      async:true,
	      cache:false,
	      type:"POST",
	      success:function(result){
		     var data = eval("(" + result + ")");
	         if(data.result.status=="true"){
	        	 addnotice(e);
	        	 setTimeout(function () { 
	        		 $(location).attr('href', '/yf/home');
	        		  }, 1500);
	         }else if(data.result.status=="false"){
	        	 if(data.result.code=="0200"){
		        	 toastr.error('使用失败！');
	        	 }
	          }
	      }
	    });		 
	 }
   });
   
   $("#backbtn").click(function(e) {
	   $(location).attr('href', '/yf/home');
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