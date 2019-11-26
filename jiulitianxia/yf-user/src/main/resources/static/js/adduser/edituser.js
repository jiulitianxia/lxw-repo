$(function() {
	$(function(){ 
		jinyong();
		funcEdit();
		$("#selaccount").prop("disabled", true);
		$("#inphone").prop("disabled", true);
		/*$("#selaccount").prop("disabled", true);        //设置下拉框不可用
		initSelectOptions("selmoney","getMoneySetingByList");
		 funcAcc();*/
		$('.spinner .btn:first-of-type').on('click', function() {
			$('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
		  });
		  $('.spinner .btn:last-of-type').on('click', function() {
			  if(parseInt($('.spinner input').val(), 10)>0){
				  $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
			  }
		  });
	});
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
		function Decrypt(word){ 
			var key = CryptoJS.enc.Utf8.parse("{g;,9~lde^[w`SR5"); 
			var iv = CryptoJS.enc.Utf8.parse('$JL<&*lZFsZ?:p#1'); 
			var encryptedHexStr = CryptoJS.enc.Hex.parse(word);
			var srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr);
			var decrypt = CryptoJS.AES.decrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC,padding: CryptoJS.pad.Pkcs7});
			var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8); 
			return decryptedStr.toString();
		}
	/**
	 * 初始化次数
	 */
	funcEdit = function (){
		 var phone=$.getURLParam("phone");
         var money=$.getURLParam("money");
         var account=$.getURLParam("account");
		 $("#inphone").val(Decrypt(phone));	
		 $("#selmoney").html(new Option(money));
		 $("#selaccount").html(new Option(account));
		 initMoneyOptions(money);
	}
	function editnotice(e){
	  e.preventDefault();
	  e.stopPropagation();
	  return $.growl.notice({
		title:"编辑成功",
	    message: "正在跳转用户信息列表!"
	  });
   }
	
   $("#editbtn").click(function(e) {
	 if(''==$('#inphone').val()){
		 toastr.error('请输入手机号！');
	 }else{
		$.ajax({
	      url:"/api/editNewUserInfo",
	      data:{uid:$.cookie('uid'),phone:aesMinEncrypt($('#inphone').val()),money:$('#selmoney').val(),account:$('#selaccount').val()
	    	  ,rewardacc:$('#rewardacc').val()},
	      async:true,
	      cache:false,
	      type:"POST",
	      success:function(result){
		     var data = eval("(" + result + ")");
	         if(data.result.status=="true"){
	        	 editnotice(e);
	        	 setTimeout(function () { 
	        		 $(location).attr('href', '/yf/home');
	        		  }, 1500);
	         }else if(data.result.status=="false"){
	        	 if(data.result.code=="0200"){
		        	 toastr.error('编辑失败！');
	        	 }
	          }
	      }
	    });		 
	 }
   });
   
   $("#backbtn").click(function(e) {
	   $(location).attr('href', '/yf/home');
	  });

	/**
	 * 初始化次数
	 */
	funcAcc = function (){  
		// 当金额变动时，初始化次数
		var selmoney = $("#selmoney").val();
		initSelectOptions("selaccount","getAccountsSetting?selmoneysel="+selmoney);
	}
	/**
	    * 获取除当前传过来的金额
	    * @param moneyid
	    * @param parentid
	    */
	   function initMoneyOptions(money) {
			var selectObj = $("#selmoney");
			$.ajax({
		        url : "/api/getMoneyList",
		        async : false,
		        type : "POST",
		        data:{money:$.getURLParam("money")},
		        success : function(result) {
		        	 var data = eval("(" + result + ")");
		        	 if (data.result.status=="true") {
		        		var configs =data.result.dataList;
		        		selectObj.find("option:not(:first)").remove();
		        		var value;
		        		for (var i in configs) {
		        			var addressConfig = configs[i];
		        			var optionText;
		        			optionText = addressConfig.money;	
	        				  
		        			var optionValue = addressConfig.money;
		        			
		        			selectObj.append(new Option(optionText, optionValue));
		        		}
		        		selectObj.selectpicker('val',value);
		        		// 刷新select
		    			selectObj.selectpicker('refresh');
		        	}else if(data.result.status=="false"){
		        		 toastr.error('需要设置金额和次数！');
		        	} 
		        }
			});
		}
   /**
    * 获取金额和次数
    * @param moneyid
    * @param parentid
    */
   function initSelectOptions(moneyid,parentid) {
		var selectObj = $("#"+moneyid);
		$.ajax({
	        url : "/api/"+parentid,
	        async : false,
	        type : "GET",
	        success : function(result) {
	        	 var data = eval("(" + result + ")");
	        	 if (data.result.status=="true") {
	        		var configs =data.result.dataList;
	        		selectObj.find("option:not(:first)").remove();
	        		var value;
	        		for (var i in configs) {
	        			var addressConfig = configs[i];
	        			var optionText;
	        			if(moneyid=='selaccount'){
        				  optionText = addressConfig.accounttimes;	
        				  if(i==0){
 	        				value = addressConfig.accounttimes;	
		 	        	  }
	        			}else{
        				  optionText = addressConfig.money;	
        				  if(i==0){
 	        				value = addressConfig.money;	
	 	        		  }
	        			}
	        			var optionValue = addressConfig.accounttimes;
	        			
	        			selectObj.append(new Option(optionText, optionValue));
	        		}
	        		selectObj.selectpicker('val',value);
	        		// 刷新select
	    			selectObj.selectpicker('refresh');
	        	}else if(data.result.status=="false"){
	        		 toastr.error('需要设置金额和次数！');
	        	} 
	        }
		});
	}
   
   $("#investbtn").click(function(e) {
	   $.ajax({
	      url:"/api/investmoney",
	      data:{uid:$.cookie('uid'),phone:aesMinEncrypt($('#inphone').val()),money:$('#selmoney').val(),account:$('#selaccount').val()},
	      async:true,
	      cache:false,
	      type:"POST",
	      success:function(result){
		     var data = eval("(" + result + ")");
	         if(data.result.status=="true"){
	        	 inverstnotice(e);
	        	 setTimeout(function () { 
	        		 $(location).attr('href', '/yf/home');
	        		  }, 1500);
	         }else if(data.result.status=="false"){
	        	 if(data.result.code=="0200"){
		        	 toastr.error('充值失败！');
	        	 }
	          }
	      }
	    });		 
	   });
   
   function inverstnotice(e){
		  e.preventDefault();
		  e.stopPropagation();
		  return $.growl.notice({
			title:"充值成功",
		    message: "正在跳转用户信息列表!"
		  });
	   }
   
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