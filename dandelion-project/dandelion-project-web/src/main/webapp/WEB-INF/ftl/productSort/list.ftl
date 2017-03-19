<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            产品类型
        </h3>
        <div id="breadcrumb-right">
        	<div class="float-right">
        		<div class="col-md-6" style="float:right;margin-top:5px;">
					<a id="addProductSort" href="#" class="btn medium primary-bg float-right" onclick="showAddProductSort();">
			            <span class="button-content">添加类型</span>
			        </a>
				</div>
			</div>
		</div>
    </div>

    <div id="page-content">
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th>类型名称</th>
                <th>备注</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as productSort>
            <tr id="${productSort.id}">
                <td>
                ${productSort.name}
                </td>
                <td>
                ${productSort.note}
                </td>
                <td>
                ${(productSort.modifyDate?string('yyyy-MM-dd HH:mm:ss'))!'-'}
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
									<a title="修改" href="javascript:showUpdateProductSort(${productSort.id});">
										<i class="glyph-icon icon-edit mrg5R"></i>
										修改
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
function showAddProductSort(){
	$.get( "/productSort/initAdd", function( data ){
		var html = '<div id="dialog" class="hide" title="添加类型"><div class="mrg10A">' + data + '</div></div>';
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
	            	
	            	$.post( '/productSort/add', $( '#dialog form' ).serialize() )
	                 .done(function(data){
	                 	if(data["status"] == "success"){
	                 		$.jGrowl("添加类型成功", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 		location.reload();
	                 	}else{
	                 		$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
	                 	}
	                 	$( "#dialog" ).dialog( "close" );
	                 })
	                 .fail(function(){
	                 	$.jGrowl("添加类型失败", {sticky:!1,position:"top-right",theme:"bg-green"});
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

// 显示类型修改界面
function showUpdateProductSort(productSort_id){
	$.get( "/productSort/update/" + productSort_id, function( data ){
	
		var html = '<div id="dialog_update" class="hide" title="类型修改"><div class="mrg10A">' + data + '</div></div>';
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
	            	
	            	$.post( '/productSort/update', $( '#dialog_update form' ).serialize() )
	                 .done(function(data){
	                 	if(data["status"] == "success"){
	                 		$.jGrowl("修改类型成功", {sticky:!1,position:"top-right",theme:"bg-green"});
	                 		location.reload();
	                 	}else{
	                 		$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
	                 	}
	                 	$( "#dialog_update" ).dialog( "close" );
	                 })
	                 .fail(function(){
	                 	$.jGrowl("修改类型失败", {sticky:!1,position:"top-right",theme:"bg-green"});
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

//开售类型
function openProductSort(productSort_id){
	$.messager.confirm( "提示", "确认开售类型吗?", function(){
    	$.post( '/productSort/onsell', { "id" : productSort_id })
         .done(function(data){
         	if(data["status"] == "success"){
         		$.jGrowl("类型开售成功", {sticky:!1,position:"top-right",theme:"bg-green"});
         		location.reload();
         	}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
         	}
         	$( "#dialog" ).dialog( "close" );
         })
         .fail(function(){
		 	$.jGrowl("类型开售失败", {sticky:!1,position:"top-right",theme:"bg-green"});
		 	$( "#dialog" ).dialog( "close" );
         });
	});
}
//停售类型
function stopProductSort(productSort_id){
	$.messager.confirm( "提示", "确认停售类型吗?", function(){
    	$.post( '/productSort/soldout', { "id" : productSort_id })
         .done(function(data){
         	if(data["status"] == "success"){
         		$.jGrowl("类型停售成功", {sticky:!1,position:"top-right",theme:"bg-green"});
         		location.reload();
         	}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
         	}
         	$( "#dialog" ).dialog( "close" );
         })
         .fail(function(){
		 	$.jGrowl("类型停售失败", {sticky:!1,position:"top-right",theme:"bg-green"});
		 	$( "#dialog" ).dialog( "close" );
         });
	});
}
</script>


