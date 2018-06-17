/**
 * EasyUIEx validate
 * 
 * Version 2.0.0
 * 
 * http://easyproject.cn 
 * https://github.com/ushelp
 * 
 * Copyright 2014 Ray [ inthinkcolor@gmail.com ]
 * 
 * Dependencies: jQuery EasyUI
 * 
 */
/*
 * ################ EasyUiEx 验证功能定义
 */
$(function() {
	/*
	 * ################# 自定义验证部分
	 */

	/**
	 * 比较输入是否与指定元素一致
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '{1}'
		}
	});

	/**
	 * 输入最小长度验证
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		minLength : {
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : uiEx.msg.minLength
		}
	});

})