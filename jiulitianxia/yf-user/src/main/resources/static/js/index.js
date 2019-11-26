$(function() {
	function jinyong(){
		 if (window.history && window.history.pushState) {
			$(window).on('popstate', function () {
				// 当点击浏览器的 后退和前进按钮 时才会被触发，
				window.history.pushState('forward', null, '');
				window.history.forward(1);
			});
		 }
		 window.history.pushState('forward', null, '');  //在IE中必须得有这两行
		 window.history.forward(1);
	   }
	jinyong();
	function aesMinEncrypt(word){
        var _word = CryptoJS.enc.Utf8.parse(word),
            _key = CryptoJS.enc.Utf8.parse("{g;,9~lde^[w`SR5"),
            _iv = CryptoJS.enc.Utf8.parse("$JL<&*lZFsZ?:p#1");
        var encrypted = CryptoJS.AES.encrypt(_word, _key, {
                    iv: _iv,
                    mode: CryptoJS.mode.CBC,
                    padding: CryptoJS.pad.Pkcs7
            });
        return encrypted.toString();
    }
	
	
   $(".input input").focus(function() {
      $(this).parent(".input").each(function() {
         $("label", this).css({
            "line-height": "18px",
            "font-size": "18px",
            "font-weight": "100",
            "top": "0px"
         })
         $(".spin", this).css({
            "width": "100%"
         })
      });
   }).blur(function() {
      $(".spin").css({
         "width": "0px"
      })
      if ($(this).val() == "") {
         $(this).parent(".input").each(function() {
            $("label", this).css({
               "line-height": "60px",
               "font-size": "24px",
               "font-weight": "300",
               "top": "10px"
            })
         });

      }
   });
   
   function loginnotice(e){
	  e.preventDefault();
	  e.stopPropagation();
	  return $.growl.notice({
		title:"登录成功",
	    message: ""
	  });
   }
    var cookieName='userName';
    var cookiePass='passWord';
    if($.cookie(cookieName)){
    	$("#name").val($.cookie(cookieName));
    }
    if($.cookie(cookiePass)){
    	$("#pass").val($.cookie(cookiePass));
    	 $(".input input").focus();
    }
    /**
     * 登录
     */
     $(".box button:first").click(function(e) {
	      $("button", this).addClass('active');

	      if(''==$("#name").val()){
	    	  $(".box button:first").attr("disabled","true");
	    	  toastr.error('用户名不能为空');
	    	  setTimeout(function () { 
	    		  $(".box button:first").removeAttr("disabled");
      		  }, 1000);
	      }else if(''==$("#pass").val()){
	    	  $(".box button:first").attr("disabled","true");
	    	  toastr.error('密码不能为空');
	    	  setTimeout(function () { 
	    		  $(".box button:first").removeAttr("disabled");
      		  }, 1000);
	      }else{
	    	$.ajax({
	          url:"/login",
	          data:{username:aesMinEncrypt($("#name").val()),password:aesMinEncrypt($("#pass").val())},
	          async:true,
	          cache:false,
	          type:"POST",
	          success:function(result){
	    	     var data = eval("(" + result + ")");
	             if(data.result.status=="true"){
	            	 $.cookie('userName',$('#name').val(), { expires: 1});
	 				 $.cookie('passWord',$("#pass").val(),{ expires: 1});
	 				 $.cookie('uid',data.result.id,{ expires: 1});
	            	 $("#name").val("");
	                 $("#pass").val("");
	                 $(location).attr('href', '/yf/home');
	             }else if(data.result.status=="false"){
	            	 if(data.result.code=="0101"){
	            		 toastr.error('用户不存在！');
	            	 }else if(data.result.code=="0100"){
	            		 toastr.error('当前用户已登录！');
	            	 }else if(data.result.code=="0102"){
	            		 toastr.error('密码错误！');
	            	 }
	              }
	          }
	       });
	      }
	   })
   function registernotice(e){
	  e.preventDefault();
	  e.stopPropagation();
	  return $.growl.notice({
		title:"注册成功",
	    message: "注册成功,快去登录吧!"
	  });
   }
   
   $(".button").click(function(e) {
      var pX = e.pageX,
         pY = e.pageY,
         oX = parseInt($(this).offset().left),
         oY = parseInt($(this).offset().top);
      $(this).append('<span class="click-efect x-' + oX + ' y-' + oY + '" style="margin-left:' + (pX - oX) + 'px;margin-top:' + (pY - oY) + 'px;"></span>')
      $('.x-' + oX + '.y-' + oY + '').animate({
         "width": "500px",
         "height": "500px",
         "top": "-250px",
         "left": "-250px",

      }, 400);
      $("button", this).addClass('active');
   })
   $(".overbox button").click(function(e) {
	  
      register(e);
   })
   function register(e){
     if(''==$("#regname").val()){
    	 
	  $(".overbox button").attr("disabled","true");
	  toastr.error('用户名不能为空');
	  
       setTimeout(function () { 
	       $(".overbox button").removeAttr("disabled");
	    }, 1000);
      }
      else if(''==$("#regpass").val()){
    	  $(".overbox button").attr("disabled","true");
    	  toastr.error('密码不能为空');
    	  
          setTimeout(function () { 
    	       $(".overbox button").removeAttr("disabled");
    	    }, 1000);
      }
      else if(''==$("#reregpass").val()){
    	  $(".overbox button").attr("disabled","true");
    	  toastr.error('确认密码不能为空');
    	  
          setTimeout(function () { 
    	       $(".overbox button").removeAttr("disabled");
    	    }, 1000);
      }
      else if($("#regpass").val() !=$("#reregpass").val()){
    	  $(".overbox button").attr("disabled","true");
    	  toastr.error("密码不一致");
    	  
          setTimeout(function () { 
    	       $(".overbox button").removeAttr("disabled");
    	    }, 1000);
      }else{
    	$.ajax({
          url:"/register",
          data:{username:aesMinEncrypt($("#regname").val()),password:aesMinEncrypt($("#regpass").val()),repassword:aesMinEncrypt($("#reregpass").val())},
          async:true,
          cache:false,
          type:"POST",
          success:function(result){
    	     var data = eval("(" + result + ")");
             if(data.status=="true"){
            	 $("#regname").val("");
                 $("#regpass").val("");
                 $("#reregpass").val("");
	        	 registernotice(e);
	        	 setTimeout(function () { 
        		    $(".alt-2").click(); 
        		    $("#name").val("");
	                 $("#pass").val("");
        		  }, 2000);
             }else if(data.result.status=="false"){
            	 if(data.result.code=="0103"){
            		 toastr.error('帐号已存在，不能重复注册！');
            	 }
              }
          }
       });
      }
   }
   $(".alt-2").click(function() {
      if (!$(this).hasClass('material-button')) {
         $(".shape").css({
            "width": "100%",
            "height": "100%",
            "transform": "rotate(0deg)"
         })
         setTimeout(function() {
            $(".overbox").css({
               "overflow": "initial"
            })
         }, 600)

         $(this).animate({
            "width": "100px",
            "height": "100px"
         }, 500, function() {
            $(".box").removeClass("back");

            $(this).removeClass('active')
         });

         $(".overbox .title").fadeOut(300);
         $(".overbox .input").fadeOut(300);
         $(".overbox .button").fadeOut(300);

         $(".alt-2").addClass('material-buton');
      }
   })
   
   $(".material-button").click(function() {

      if ($(this).hasClass('material-button')) {
         setTimeout(function() {
            $(".overbox").css({
               "overflow": "hidden"
            })
            $(".box").addClass("back");
         }, 200)
         $(this).addClass('active').animate({
            "width": "550px",
            "height": "700px"
         });

         setTimeout(function() {
            $(".shape").css({
               "width": "50%",
               "height": "50%",
               "transform": "rotate(45deg)"
            })

            $(".overbox .title").fadeIn(300);
            $(".overbox .input").fadeIn(300);
            $(".overbox .button").fadeIn(300);
         }, 700)

         $(this).removeClass('material-button');
      }

      if ($(".alt-2").hasClass('material-buton')) {
         $(".alt-2").removeClass('material-buton');
         $(".alt-2").addClass('material-button');
      }

   });
   /**
    * 关闭系统
    */
  
    $(".pass-forgot").click(function(e) {
    	if (navigator.userAgent.indexOf("MSIE") > 0) {
           window.opener = null;
           window.close();
        } else if (navigator.userAgent.indexOf("Firefox")!=-1 || navigator.userAgent.indexOf("Chrome")!=-1) {
           window.location.href = 'about:blank';
           window.close();
        } else {
           window.opener = null;
           window.location.href = 'about:blank';
           window.close();
        }
   
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