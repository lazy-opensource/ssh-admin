$(function() {
	/**
	 * EasyUI初始化
	 */
	$("#username").textbox('textbox').focus();
	//$("#verifyCode").validatebox('textbox').attr("maxlength",4);

	
	/**
	 * 验证码打开实现
	 */
	var vcTr=$("#vcTr");
	var vcImg=$("#vcImg");
	
	$("#verifyCode").on("click", function() {
		vcTr.show();
		return false;
	}).on("focus", function() {
		vcTr.show();
		return false;
	}).on("keydown", function(event) {
			if(event.which==13){
				$("#loginBtn").click();
				vcTr.show();
			}
	});

	$("#vcImg").hover(function() {
		vcTr.show();
	});

	$(document).click(function() {
		vcTr.hide();
	})
	
	$("#vcTr").on("click", function(event) {
		vcImg.attr("src",EasySSH.basePath + "jsp/VerifyCode.jsp?r=" + new Date());
		event.stopPropagation();
	});
	
	/*
	 * 登录检测和提交
	 */
	$("#loginBtn").on("click",function(){
		
		if(uiEx.validate("#loginForm")){
			var vc=$("#verifyCode");
			$.post("checkVerifyCode.action","verifyCode="+vc.val(),function(data){
				if(data.statusCode==200){
					$('#loginBtn').linkbutton('disable');
					uiEx.submitForm('#loginForm');
				}else{
					uiEx.msg(data.msg);
					vcImg.attr("src",EasySSH.basePath + "jsp/VerifyCode.jsp?r=" + new Date());
					vc.select();
					vc.focus();
				}
			});
		}
		
		
	});
	
	
	$(":input.textbox-text").keydown(function(event){
		if(event.keyCode==13){
			var nxtIdx = $(":input.textbox-text,A.easyui-linkbutton").index(this);
	  		 $(":input.textbox-text,A.easyui-linkbutton").eq(nxtIdx+1).focus();
		}
	});

	
	/**
	 * easyui主题切换下拉菜单
	 */
	
	$("#themeCombobox")
			.combobox(
					{
						editable : false,
						panelHeight : "auto",
						valueField : "value",
						textField : 'text',
						url : EasySSH.basePath
								+ "json/easyui.theme.combobox.json",
						/*
						 * "data": [{ "value":"default", "text":"default",
						 * "selected":true },{ "value":"black", "text":"black"
						 * },{ "value":"bootstrap", "text":"bootstrap" },{
						 * "value":"gray", "text":"gray" },{ "value":"metro",
						 * "text":"metro" }],
						 */
						onSelect : function(selObj) {
							if(document.getElementById("themeLink")){
								document.getElementById("themeLink").href = EasySSH.basePath
										+ "easyui/themes/"
										+ selObj.value
										+ "/easyui.css";
							}
						},
						// 加载成功后设置默认值
						onLoadSuccess : function() {
							var defaultTheme = "metro";
							// $("#themeCombobox").combobox("setValue",
							// defaultTheme);
							// document.getElementById("themeLink").href=EasySSH.basePath+"easyui/themes/"+defaultTheme+"/easyui.css";
						}
					});
	

})