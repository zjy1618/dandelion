<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            新增订单
        </h3>
    </div>

    <div id="page-content">
        <form id="form" action="/order/initAdd" method="GET">
            <div class="form-row">
                <div class="form-input col-md-4">
                    <div class="form-input">
                        <input type="text" placeholder="产品名称" name="name" class="parsley-validated"  value="${name}" data-trigger="focus" />
                    </div>
                </div>
                <div class="form-input col-md-2" >
                    <div class="form-input">
                        <select id="showRoomId" name="showRoomId">
                        <#list showRoom_list as showRoom>
                            <option value="${showRoom.id}" <#if showRoomId == showRoom.id> selected</#if> >${showRoom.name}</option>
                        </#list>
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
        <form id="order_form" action="/order/add"  method="POST"  class="col-md-10 left-margin">
            <input type="hidden" id="showRoomId" name="showRoomId" value="${showRoomId}">
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th>产品名称</th>
                <th>图片</th>
                <th>价格</th>
                <th>公司</th>
                <th>展厅</th>
                <th>购买数量</th>
            </tr>
            </thead>
            <tbody>
            <#list list as product>
            <tr id="${product.id}">
                <td>
                ${product.name}
                </td>
                <td>
                    <#list product.productImgList as productImg>
                        <img src="${productImg.url}" height="80">
                    </#list>
                </td>
                <td>
                    <#--<input style="width: 20px;" type="radio" name="price_${product.productCode}" id="price_${product.productCode}_dKAmount" value="${product.dKAmount}">-->
                    <#--<label for="">吊卡:&nbsp;${(product.dKAmount!0)?string('0.00')}&nbsp;元/套</label></br>-->
                    <#--<input style="width: 20px;" type="radio" name="price_${product.productCode}" id="price_${product.productCode}_dYAmount" value="${product.dYAmount}">-->
                    <#--<label for="">大样:&nbsp;${(product.dYAmount!0)?string('0.00')}&nbsp;元/件</label></br>-->
                    <#--<input style="width: 20px;" type="radio" name="price_${product.productCode}" id="price_${product.productCode}_mLAmount" value="${product.mLAmount}">-->
                    <#--<label for="">面料:&nbsp;${(product.mLAmount!0)?string('0.00')}&nbsp;元/米</label>-->
                    <#list product.productStyleList as productStyle>
                        <input style="width: 20px;" type="radio" name="style_${product.productCode}" id="price_${productStyle.id}" value="${productStyle.id}" />
                        <#--统计总额使用-->
                        <input type="hidden" id="price_${productStyle.id}_unitAmount" value="${productStyle.unitAmount}">
                        <label for="">${productStyle.productStyleInfo}</label></br>
                    </#list>
                </td>
                <td>
                ${product.productSort.name}
                </td>
                <td>
                ${product.showRoom.name}
                </td>
                <td>
                    <div class="form-input  col-md-4" >
                        <input id="style_${product.productCode}_count" type="text" class="spinner-input ui-spinner-input" max="2000" min="0" autocomplete="off" role="spinbutton" aria-valuenow="1"
                           name="count_${product.productCode}" value="0">
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
        <div class="divider"></div>
        <div class="form-row">
            <div class="form-input col-md-4">
                <div class="form-input">
                    <input type="text" placeholder="手机号" name="mobile" id="mobile" class="parsley-validated" data-trigger="focus" />
                </div>
            </div>
            <div class="form-input col-md-4">
                <div class="form-input">
                    <input type="text" placeholder="授权号" name="acceptNumber" id="acceptNumber" class="parsley-validated" data-trigger="focus" />
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-input col-md-3">
                <a href="javascript:;" class="primary-bg large radius-all-4 display-block btn white-modal-60" id="search"  onclick="settleOrder();">
                    <span class="button-content">
                            结算订单
                    </span>
                </a>
            </div>
        </div>
        </form>
    </div>
</div>


<script language="javascript">

    function searchDate(){
        $('#form').parsley('validate');
        $('#form').trigger('submit');
    }

    function settleOrder(){
        var priceTotal = 0.0;//购买总金额
        var countTotal = 0;//购买总量
        var price_list=$('input:radio[id^=price_]');
        for(var i=0;i<price_list.size();i++){
            if(price_list[i].checked){
                var unitAmountID = price_list[i].id + '_unitAmount';
                var unitAmount = parseFloat($('#' + unitAmountID).val());
                var count = $('#' +price_list[i].name + '_count').val();
                if(parseFloat(count) == 0){
                    $(price_list[i]).attr("checked",false);
                }
                priceTotal += unitAmount*parseFloat(count);
                countTotal += parseFloat(count);
            }
        }

        if(countTotal == 0){
            alert("请选择购买的商品!");
            return;
        }
        var mobile = $('#mobile').val();
        mobile = $.trim(mobile);
        if(mobile == '' ){
            alert("请输入手机号!");
            return;
        }

        var regex = /^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|147|145)\d{8}$/;
        var ret = regex.test(mobile);
        if (ret == false || mobile.length !=11) {
            alert("手机号存在格式错误!");
            return;
        }

        if($('#acceptNumber').val() == '' ){
            alert("请输入授权号!");
            return;
        }

        submitOrder("总价:" + priceTotal + " ,购买总量:" + countTotal);

    }

    //提交订单
    function submitOrder(orderInfo){
        $.messager.confirm( "确认提交订单吗?", orderInfo, function(){
            $.post( '/order/add', { "orderJson" : JSON.stringify($('#order_form').serializeJson()) })
                .done(function(data){
                    if(data["status"] == "success"){
                        $.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
                        window.setTimeout(function(){ location.href = "/order/list"; },1000);
                    }else{
                        $.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
                    }
                    $( "#dialog" ).dialog( "close" );
                })
                .fail(function(){
                    $.jGrowl("新增订单失败", {sticky:!1,position:"top-right",theme:"bg-red"});
                    $( "#dialog" ).dialog( "close" );
                });
        });
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

    //Jquery 将表单序列化为Json对象
    (function($){
        $.fn.serializeJson=function(){
            var serializeObj={};
            var array=this.serializeArray();
            var str=this.serialize();
            $(array).each(function(){
                if(serializeObj[this.name]){
                    if($.isArray(serializeObj[this.name])){
                        serializeObj[this.name].push(this.value);
                    }else{
                        serializeObj[this.name]=[serializeObj[this.name],this.value];
                    }
                }else{
                    if(this.value !='0'){
                        serializeObj[this.name]=this.value;
                    }
                }
            });
            return serializeObj;
        };
    })(jQuery);

</script>


