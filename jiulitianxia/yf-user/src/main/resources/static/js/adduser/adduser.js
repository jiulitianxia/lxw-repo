$(function() {
	$(function(){ 
		 $("#selaccount").prop("disabled", true);        //设置下拉框不可用
		initSelectOptions("selmoney","getMoneySetingByList");
		funcAcc();
	}); 
   
	function addnotice(e){
	  e.preventDefault();
	  e.stopPropagation();
	  return $.growl.notice({
		title:"添加成功",
	    message: "正在跳转用户信息列表!"
	  });
   }
	
   $("#addbtn").click(function(e) {
	 if(''==$('#inphone').val()){
		 toastr.error('请输入手机号！');
	 }else{
		$.ajax({
	      url:"/api/addNewUserInfo",
	      data:{uid:$.cookie('uid'),phone:$('#inphone').val(),money:$('#selmoney').val(),account:$('#selaccount').val()},
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
		        	 toastr.error('添加失败！');
	        	 }else if(data.result.code=="0205"){
	        		 toastr.error('用户已经存在！');
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