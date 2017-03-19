<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            ${product.name} 款式列表
        </h3>
        <div id="breadcrumb-right">
        	<div class="float-right">
        		<div class="col-md-6" style="float:right;margin-top:5px;">
					<a id="addProductStyle" href="#" class="btn medium primary-bg float-right" onclick="showAddProductStyle();">
			            <span class="button-content">添加款式</span>
			        </a>
				</div>
			</div>
		</div>
    </div>

    <div id="page-content">
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th>款式名称</th>
                <th>单价金额</th>
                <th>单位名称</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as productStyle>
            <tr id="${productStyle.id}">
                <td>
                ${productStyle.name}
                </td>
                <td>
				${(productStyle.unitAmount!0)?string('0.00')}
                </td>
				<td>
				${productStyle.unitName}
				</td>
                <td>
                ${(productStyle.modifyDate?string('yyyy-MM-dd HH:mm:ss'))!'-'}
                </td>
                <td>
	            	<div class="dropdown">
	            		<a class="btn medium bg-blue" data-toggle="dropdown" title="" href="javascript:;">
							<span class="button-content">
								<i class="glyph-icon font-size-11 icon-cog"></i>
								<i class="glyph-icon font-size-11 icon-chevron-down"></i>
							</span>
						</a>
						<ul class="dropdown-menu float-right">
							<li>
								<a title="修改" href="javascript:showUpdateProductStyle(${productStyle.id});">
									<i class="glyph-icon icon-edit mrg5R"></i>
									修改
								</a>
							</li>
                            <li class="divider"></li>
                            <li>
                                <a href="javascript:deleteProductStyle('${productStyle.id}');" title="删除" class="font-red">
                                    <i class="glyph-icon icon-remove mrg5R"></i>
                                    删除
                                </a>
                            </li>
						</ul>
	            	</div>
	            </td>
            </tr>
            </#list>
            </tbody>

        </table>
        <div class="button-group center-div">
            ${pagerHelper.content}
        </div>
    </div>
</div>

<script language="javascript">

function searchDate(){
    $('#form').parsley('validate');
    $('#form').trigger('submit');
}

$(document).ready(function() {
	if("${active}" == "display"){
	    if("${statusCode}" == "1"){
	        $.jGrowl("${msg}", {sticky:!1,position:"top-right",theme:"bg-red"});
	    }else{
	        $.jGrowl("${msg}", {sticky:!1,position:"top-right",theme:"bg-green", beforeClose:function(){
	        }});
	    }
	}
});

// 显示添加售前工单界面
function showAddProductStyle(){
	$.get( "/productStyle/initAdd/${product.productCode}", function( data ){
		var html = '<div id="dialog" class="hide" title="添加款式"><div class="mrg10A">' + data + '</div></div>';
		$( html ).dialog({
	        resizable:!0,
	        minWidth:700,
	        minHeight:300,
	        modal:!0,
	        dialogClass:"modal-dialog",
	        closeOnEscape:!0,
	        close : function() {
				$( this ).dialog( "destroy" );
			},
	        buttons: {
	           	 "保存": function() {
	            	var valid = $('#dialog form').parsley( 'validate' );
	            	if(!valid){
	            		return;
	            	}
	            	
	            	$.post( '/productStyle/add', $( '#dialog form' ).serialize() )
	                 .done(function(data){
	                 	if(data["status"] == "success"){
	                 		$.jGrowl("添加款式成功", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 		location.reload();
	                 	}else{
	                 		$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
	                 	}
	                 	$( "#dialog" ).dialog( "close" );
	                 })
	                 .fail(function(){
	                 	$.jGrowl("添加款式失败", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 	$( "#dialog" ).dialog( "close" );
	                 });
	            },
	            "取消": function() {
	        	 	$( "#dialog" ).dialog( "close" );
	        	 }
			}
		});
	});
};

// 显示款式修改界面
function showUpdateProductStyle(productStyle_id){
	$.get( "/productStyle/update/" + productStyle_id, function( data ){
	
		var html = '<div id="dialog_update" class="hide" title="款式修改"><div class="mrg10A">' + data + '</div></div>';
		$( html ).dialog({
	        resizable:!0,
	        minWidth:700,
	        minHeight:300,
	        modal:!0,
	        dialogClass:"modal-dialog",
	        closeOnEscape:!0,
	        close : function() {
				$( this ).dialog( "destroy" );
			},
	        buttons: {
	        	 保存: function() {
	            	var valid = $('#dialog_update form').parsley( 'validate' );
	            	if(!valid){
	            		return;
	            	}
	            	
	            	$.post( '/productStyle/update', $( '#dialog_update form' ).serialize() )
	                 .done(function(data){
	                 	if(data["status"] == "success"){
	                 		$.jGrowl("修改款式成功", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 		location.reload();
	                 	}else{
	                 		$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
	                 	}
	                 	$( "#dialog_update" ).dialog( "close" );
	                 })
	                 .fail(function(){
	                 	$.jGrowl("修改款式失败", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 	$( "#dialog_update" ).dialog( "close" );
	                 });
	            },
	           	"取消": function() {
	        	 	$( "#dialog_update" ).dialog( "close" );
	        	 }
			}
		});
	});
}
//删除产品
function deleteProductStyle(id){
    $.messager.confirm( "提示", "确认删除产品款式吗?", function(){
        $.post( '/productStyle/delete', { "id" : id })
                .done(function(data){
                    if(data["status"] == "success"){
                        $.jGrowl("产品款式删除成功", {sticky:!1,position:"top-right",theme:"bg-green"});
                        location.reload();
                    }else{
                        $.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
                    }
                    $( "#dialog" ).dialog( "close" );
                })
                .fail(function(){
                    $.jGrowl("产品款式删除失败", {sticky:!1,position:"top-right",theme:"bg-red"});
                    $( "#dialog" ).dialog( "close" );
                });
    });
}
</script>


