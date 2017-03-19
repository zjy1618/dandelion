<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            订单列表
        </h3>
        <div id="breadcrumb-right">
            <div class="float-right">
                <#--<a id="addProduct" href="/order/initAdd" target="_self" class="btn large primary-bg" >
                    <span class="button-content">新增订单</span>
                </a>-->
                <a href="javascript:;" onclick="exportData();" class="btn large primary-bg" id="search" title="Validate!">
                    <span class="button-content">导出订单</span>
                </a>
            </div>
        </div>
    </div>

    <div id="page-content">
        <form id="form" name="orderSearch" action="/order/list" method="GET">
            <div class="form-row">
				<div class="form-input col-md-2">
	                  <div class="form-input">
	                       <input type="text" placeholder="订单编号" name="orderNumber" class="parsley-validated" " value="${orderNumber}" data-trigger="focus" />
	                  </div>
	            </div>
                <div class="form-input col-md-2">
                    <div class="form-input">
                        <input type="text" placeholder="手机号" name="mobile" class="parsley-validated" " value="${mobile}" data-trigger="focus" />
                    </div>
                </div>
                <div class="form-input col-md-2">
                    <div class="form-input">
                        <input type="text" placeholder="授权号" name="acceptNumber" class="parsley-validated" " value="${acceptNumber}" data-trigger="focus" />
                    </div>
                </div>
            	<div class="form-input col-md-2">
                  <div class="form-input">
                      <select id="showRoomId" name="showRoomId">
						  <option value=""  >展厅</option>
						  <#list showRoom_list as showRoom>
							  <option value="${showRoom.id}" <#if showRoomId == showRoom.id> selected</#if> >${showRoom.name}</option>
						  </#list>
					  </select>
                  </div>
            	</div>
			</div>
			<div class="form-row">
                <div class="form-input col-md-2">
                    <div class="form-input">
                        <input type="text" placeholder="创建开始日期" id="startDate" name="startDate" class="parsley-validated"
                          value="${startDate}" data-trigger="focus"  onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="clear:both;overflow:hidden;display:block"/>
                    </div>
                </div>
                <div class="form-label col-md-1" style="width:30px;">
                    <label for="">—</label>
                </div>
                <div class="form-input col-md-2">
                    <div class="form-input">
                        <input type="text" placeholder="创建结束日期" id="endDate" name="endDate" class="parsley-validated"
                          value="${endDate}" data-trigger="focus"  onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="clear:both;overflow:hidden;display:block"/>
                    </div>
                </div>
            	<div class="form-input col-md-1" style="width:120px;">
                    <div class="form-input">
                        <select name="status" id="status">
                            <option value="" >状态</option>
                            <option value="UNPAY" <#if status == "UNPAY">selected</#if> >未付款</option>
                            <option value="PAID" <#if status == "PAID">selected</#if>>已付款</option>
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
                <a href="javascript:;" class="btn large primary-bg radius-all-4" id="search" title="Validate!" onclick="applyAll();">
                    <span class="button-content">
                        批量标志付款成功
                    </span>
                </a>
                <span id="batch_text"></span>
                <div style="float: right">
                    <div class="form-label">
                        <label for="datepicker2">
                            共 ${statInfo.totalCount} 笔 ${(statInfo.totalAmount)?string(',###.##')} 元,其中已付款 ${statInfo.paidCount!0}笔 ${(statInfo.paidAmount)?string(',###.##')} 元,未付款 ${statInfo.unpayCount!0}笔 ${(statInfo.unpayAmount)?string(',###.##')}元
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th><nobr><input id="contr" name="confirm" type="checkbox" onclick="allChose();" style="width: 13px;height: 13px"/></nobr></th>
                <th>订单编号</th>
                <th>状态</th>
                <th>产品数量</th>
                <th>总价</th>
                <th>客户名</th>
                <th>手机号</th>
                <th>邮政编码</th>
                <th>详细地址</th>
                <th>留言</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as order>
            <tr id="${order.id}">
                <td class="center">
                <#if order.status == "UNPAY">
                    <input name="order_id" type="checkbox" value="${order.id}" style="width: 13px;height: 13px" onClick="showChecked();"/>
                </#if>
                </td>
                <td>
                    <a href="/order/detail/${order.orderNumber}" target="_blank" title="详情" style="color: cornflowerblue;">${order.orderNumber}</a>
                </td>
                <td>
                ${order.statusValue}
                </td>
                <td>
                ${order.productAllCount}
                </td>
                <td>
                ${(order.amount!0)?string('0.00')}
                </td>
                <td>
				${order.userName}
                </td>
                <td>
				${order.mobile}
                </td>
                <td>
				${order.postCode}
                </td>
                <td>
				${order.address}
                </td>
                <td>
				${order.note}
                </td>
                <td>
                ${(order.createDate?string('yyyy-MM-dd HH:mm:ss'))!'-'}
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
                                <a href="/order/detail/${order.orderNumber}" target="_self" title="详情">
                                    <i class="glyph-icon icon-download mrg5R"></i>
                                    查看详情
                                </a>
                            </li>
                            <#if order.status == "UNPAY">
                                <li class="divider"></li>
                                <li>
                                    <a href="javascript:deleteOrder('${order.orderNumber}');" title="删除" class="font-red">
                                        <i class="glyph-icon icon-remove mrg5R"></i>
                                        删除
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

<form class="none" name="setPaidForm" method="POST" ></form>

<script language="javascript">

function searchDate(){
    var orderForm = $('form[name=orderSearch]');
    orderForm.attr("action", "/order/list");
    orderForm.submit();
}

function exportData() {
    var orderForm = $('form[name=orderSearch]');
    orderForm.attr("action", "/order/export");
    orderForm.submit();
}

$(document).ready(function() {
    $("#batch_text").hide();
	if("${active}" == "display"){
        $.jGrowl("${msg}", {sticky:!1,position:"top-right",theme:"bg-green", beforeClose:function(){}});
	}
});

//删除订单
function deleteOrder(orderNumber){
	$.messager.confirm( "提示", "确认删除订单吗?", function(){
    	$.post( '/order/deleteOrder', { "orderNumber" : orderNumber })
         .done(function(data){
         	if(data["status"] == "success"){
         		$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
         		location.reload();
         	}else{
				$.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
         	}
         	$( "#dialog" ).dialog( "close" );
         })
         .fail(function(){
		 	$.jGrowl("删除订单", {sticky:!1,position:"top-right",theme:"bg-green"});
		 	$( "#dialog" ).dialog( "close" );
         });
	});
}

function applyAll(){
    var length = $("input[name='order_id']:checked").length;
    if(length == 0){
        alert("请选择记录！");
    }else{
        var setPaidForm = $('form[name=setPaidForm]');
        setPaidForm.attr("action","/order/setPaid");

        var orderId = new Array();
        for(var i = 0; i < length; i++){
            orderId[i] = $("input[name='order_id']:checked")[i].value;
        }
        setPaidForm.append('<input type=\"text\" name=\"orderId\" value=\"' + orderId + '\" />');
        setPaidForm.submit();
    }
}

//批量操作
function allChose(){
    if($('#contr')[0].checked){
        var count = 0;
        var totalAmount = 0;
        $(":checkbox").each(function(index, element){
            element.checked = true;
            if(index != 0){
                count++;
                item = $(element).parent();
                item = item.next().next().next().next();
                totalAmount += parseFloat(item.html());
            }
        });
        $("#batch_text")[0].innerHTML = "已选择 "+count+" 笔，共 "+totalAmount+" 元";
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
    var totalAmount = 0;

    $(":checkbox").each(function(index, element){
        if(index != 0){
            if(element.checked){
                count++;
                item = $(element).parent();
                item = item.next().next().next().next();
                totalAmount += parseFloat(item.html());
            }
        }
    });

    $("#batch_text")[0].innerHTML = "已选择 "+count+" 笔，共 "+totalAmount+" 元";
    $("#batch_text").show("");
}
</script>


