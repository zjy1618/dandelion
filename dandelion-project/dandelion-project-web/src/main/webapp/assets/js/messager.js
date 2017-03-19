$.messager = (function() {

	var alert = function(title, message) {
		var model = $.messager.model;

		if (arguments.length < 2) {
			message = title || "";
			title = "&nbsp;"
		}

		$("<div><div class='text-center pad25T'>" + message + "</div></div>").dialog({
			title : title,
			minWidth : 350,
			minHeight : 200,
			// override destroy methods;
			onClose : function() {
				$(this).dialog("destroy");
			},
			buttons : [ {
				text : model.ok.text,
				classed : model.ok.classed || "btn-success",
				click : function() {
					$(this).dialog("destroy");
				}
			} ]
		});
	};

	var confirm = function(title, message, callback) {
		var model = $.messager.model;

		$("<div><div class='text-center pad25T'>" + message + "</div></div>")
				.dialog({
					title : title,
					minWidth : 350,
					minHeight : 200,
					
					// override destroy methods;
					onClose : function() {
						$(this).dialog("destroy");
					},
					buttons : [ {
						text : model.cancel.text,
						classed : model.cancel.classed || "btn-danger",
						click : function() {
							$(this).dialog("destroy");
						}
					}, {
						text : model.ok.text,
						classed : model.ok.classed || "btn-success",
						click : function() {
							$(this).dialog("destroy");
							callback && callback();
						}
					} ]
				});
	};

	return {
		alert : alert,
		confirm : confirm
	};

})();

$.messager.model = {
	ok : {
		text : "确定",
		classed : 'btn-default',
	},
	cancel : {
		text : "取消",
		classed : 'btn-error'
	}
};