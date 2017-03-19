<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            产品列表
        </h3>
        <div id="breadcrumb-right">
        	<div class="float-right">
        		<div style="float:right;margin-top:5px;">
					<a id="addProduct" href="/product/initAdd" target="_self" class="btn large primary-bg" >
			            <span class="button-content">添加产品</span>
			        </a>
                    <a href="/product/initImport" target="_self" class="btn large primary-bg">
                        <span class="button-content">导入产品</span>
                    </a>
				</div>
			</div>
		</div>
    </div>

    <div id="page-content">
        <form id="form" action="/product/list" method="GET">
            <div class="form-row">
				<div class="form-input col-md-2"  style="width:225px;">
	                  <div class="form-input">
	                       <input type="text" placeholder="产品名称" name="name" class="parsley-validated" " value="${name}" data-trigger="focus" />
	                  </div>
	            </div>
                <div class="form-input col-md-2">
                    <div class="form-input">
                        <input type="text" placeholder="产品编码" name="productCode" class="parsley-validated" " value="${productCode}" data-trigger="focus" />
                    </div>
                </div>
            	<div class="form-input col-md-2">
                  <div class="form-input">
                      <select id="productSortId" name="productSortId">
                          <option value=""  >类别</option>
						  <#list productSort_list as productSort>
							  <option value="${productSort.id}" <#if productSortId == productSort.id> selected</#if> >${productSort.name}</option>
						  </#list>
                      </select>
                  </div>
            	</div>
            	<div class="form-input col-md-1" style="width:120px;">
                    <div class="form-input">
                        <select name="status" id="status">
                            <option value="" >状态</option>
                            <option value="CREATE" <#if status == "CREATE">selected</#if> >创建状态</option>
                            <option value="ONSELL" <#if status == "ONSELL">selected</#if> >在售</option>
                            <option value="SOLDOUT" <#if status == "SOLDOUT">selected</#if>>停售</option>
                        </select>
                    </div>
                </div>
                <div class="form-input col-md-2">
                    <a href="javascript:;" class="btn large primary-bg radius-all-4" id="search" title="Validate!" onclick="searchDate();">
			            <span class="button-content">
			                查询
			            </span>
                    </a>
                </div>
                
            </div>
        </form>
        <div class="form-row">
            <div class="form-input col-md-12">
                <a href="javascript:;" class="btn large primary-bg radius-all-4"  onclick="onSellAll();">
                    <span class="button-content">
                        批量开售
                    </span>
                </a>
                <a href="javascript:;" class="btn large primary-bg radius-all-4"  onclick="soldOutAll();">
                    <span class="button-content">
                        批量停售
                    </span>
                </a>
                <span id="batch_text"></span>
            </div>
        </div>
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th><nobr><input id="contr" name="confirm" type="checkbox" onclick="allChose();" style="width: 13px;height: 13px"/></nobr></th>
                <th width="200">产品名称</th>
                <th>产品编码</th>
                <th>图片</th>
                <th width="160">价格</th>
                <th>类别</th>
                <th>状态</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as product>
            <tr id="${product.id}">
                <td class="center">
					<input name="product_id" type="checkbox" value="${product.id}" style="width: 13px;height: 13px" onClick="showChecked();"/>
                </td>
                <td>
                ${product.name}
                </td>
                <td>
				${product.productCode}
                </td>
				<td>
				<#list product.productImgList as productImg>
					<img src="${productImg.url}" height="80">
				</#list>
				</td>
                <td>
					<#list product.productStyleList as productStyle>
                        ${productStyle.productStyleInfo}</br>
					</#list>
                </td>
                <td>
                ${product.productSort.name}
                </td>
                <td>
                ${product.statusValue}
                </td>
                <td>
                ${(product.modifyDate?string('yyyy-MM-dd HH:mm:ss'))!'-'}
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
							<#if product.status == "SOLDOUT" || product.status == "CREATE">
								<li>
									<a title="修改" href="/product/update/${product.productCode}" target="_self">
										<i class="glyph-icon icon-edit mrg5R"></i>
										编辑
									</a>
								</li>
								<li>
									<a href="javascript:openProduct('${product.productCode}');" title="开售">
										<i class="glyph-icon icon-upload mrg5R"></i>
										开售
									</a>
								</li>
							</#if>
							<#if product.status == "CREATE">
                                <li class="divider"></li>
                                <li>
                                    <a href="javascript:deleteProduct('${product.productCode}');" title="删除" class="font-red">
                                        <i class="glyph-icon icon-remove mrg5R"></i>
                                        删除
                                    </a>
                                </li>
							</#if>
							<#if product.status == "ONSELL">
								<li>
									<a href="javascript:stopProduct('${product.productCode}');" title="停售">
										<i class="glyph-icon icon-download mrg5R"></i>
										停售
									</a>
								</li>
							</#if>
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

<form class="none" name="onSellForm" method="POST" ></form>

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

function onSellAll(){
    var length = $("input[name='product_id']:checked").length;
    if(length == 0){
        alert("请选择记录！");
    }else{
        $.messager.confirm( "提示", "确认开售" +length+ "个产品吗?", function(){
            var onSellForm = $('form[name=onSellForm]');
            onSellForm.attr("action","/product/onSellAll");

            var productId = new Array();
            for(var i = 0; i < length; i++){
                productId[i] = $("input[name='product_id']:checked")[i].value;
            }
            onSellForm.append('<input type=\"text\" name=\"productId\" value=\"' + productId + '\" />');
            onSellForm.submit();
        });
    }
}

function soldOutAll(){
    var length = $("input[name='product_id']:checked").length;
    if(length == 0){
        alert("请选择记录！");
    }else{
        $.messager.confirm( "提示", "确认停售" +length+ "个产品吗?", function(){
            var onSellForm = $('form[name=onSellForm]');
            onSellForm.attr("action","/product/soldOutAll");

            var productId = new Array();
            for(var i = 0; i < length; i++){
                productId[i] = $("input[name='product_id']:checked")[i].value;
            }
            onSellForm.append('<input type=\"text\" name=\"productId\" value=\"' + productId + '\" />');
            onSellForm.submit();
        });
    }
}

//开售产品
function openProduct(productCode){
	$.messager.confirm( "提示", "确认开售产品吗?", function(){
    	$.post( '/product/onSell', { "productCode" : productCode })
         .done(function(data){
         	if(data["status"] == "success"){
         		$.jGrowl("产品开售成功", {sticky:!1,position:"top-right",theme:"bg-green"});
         		location.reload();
         	}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
         	}
         	$( "#dialog" ).dialog( "close" );
         })
         .fail(function(){
		 	$.jGrowl("产品开售失败", {sticky:!1,position:"top-right",theme:"bg-red"});
		 	$( "#dialog" ).dialog( "close" );
         });
	});
}
//停售产品
function stopProduct(productCode){
	$.messager.confirm( "提示", "确认停售产品吗?", function(){
    	$.post( '/product/soldOut', { "productCode" : productCode })
         .done(function(data){
         	if(data["status"] == "success"){
         		$.jGrowl("产品停售成功", {sticky:!1,position:"top-right",theme:"bg-green"});
         		location.reload();
         	}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
         	}
         	$( "#dialog" ).dialog( "close" );
         })
         .fail(function(){
		 	$.jGrowl("产品停售失败", {sticky:!1,position:"top-right",theme:"bg-red"});
		 	$( "#dialog" ).dialog( "close" );
         });
	});
}

//删除产品
function deleteProduct(productCode){
    $.messager.confirm( "提示", "确认删除产品吗?", function(){
        $.post( '/product/delete', { "productCode" : productCode })
		.done(function(data){
			if(data["status"] == "success"){
				$.jGrowl("产品删除成功", {sticky:!1,position:"top-right",theme:"bg-green"});
				location.reload();
			}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
			}
			$( "#dialog" ).dialog( "close" );
		})
		.fail(function(){
			$.jGrowl("产品删除失败", {sticky:!1,position:"top-right",theme:"bg-red"});
			$( "#dialog" ).dialog( "close" );
		});
    });
}

//批量操作
function allChose(){
    if($('#contr')[0].checked){
        var count = 0;
        $(":checkbox").each(function(index, element){
            element.checked = true;
            if(index != 0){
                count++;
            }
        });
        $("#batch_text")[0].innerHTML = "已选择 "+count+" 个产品";
        $("#batch_text").show("");
    }else{
        $(":checkbox").each(function(index, element){
            element.checked = false;
        });
        $("#batch_text").hide();
    }
}

function showChecked(){
    var count = 0;

    $(":checkbox").each(function(index, element){
        if(index != 0){
            if(element.checked){
                count++;
            }
        }
    });

    $("#batch_text")[0].innerHTML = "已选择 "+count+" 个产品";
    $("#batch_text").show("");
}
</script>


