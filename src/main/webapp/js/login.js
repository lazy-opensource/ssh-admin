$(function() {
	$("#username").textbox('textbox').focus();/*进入后光标停留在用户输入框*/
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
		vcImg.attr("src",EasySSH.basePath +"jsp/VerifyCode.jsp?r=" + new Date());
		event.stopPropagation();
	});
	$("#loginBtn").on("click",function(){
		if(uiEx.validate("#loginForm")){
			var vc=$("#verifyCode");
			$.get(
                EasySSH.basePath + "login/loginVerify",
					{verify:vc.val(),time:new Date()},
					function(data){
				       if(data.statusCode==200){//如果验证码没有错
					      $('#loginBtn').linkbutton('disable');
				       	  uiEx.submitForm('#loginForm');//提交
				       }else{
						  uiEx.msg(data.msg);
						  vcImg.attr("src",EasySSH.basePath+"jsp/VerifyCode.jsp?r=" + new Date());
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
	$("#themeCombobox")
			.combobox(
					{
						editable : false,
						panelHeight : "auto",
						valueField : "value",
						textField : 'text',
						url : EasySSH.basePath
								+ "json/easyui.theme.combobox.json",
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