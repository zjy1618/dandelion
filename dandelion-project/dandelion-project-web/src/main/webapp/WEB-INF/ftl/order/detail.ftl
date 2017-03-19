<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<script type="text/javascript" src="/assets/js/LodopFuncs.js"></script>

<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            订单详情
        </h3>
        <div id="breadcrumb-right">
            <div class="float-right">
                <a href="javascript:;" onclick="printData();" class="btn large primary-bg" id="search" title="Validate!">
                    <span class="button-content">直接打印</span>
                </a>
                <a href="javascript:;" onclick="preViewData();" class="btn large primary-bg" id="search" title="Validate!">
                    <span class="button-content">打印预览</span>
                </a>
            </div>
        </div>
    </div>

    <div id="order-page-content">

        <div>
            <div style=" background:#CCCCCC;">
                <span style=" font-size:20px; margin-right:10px;" >订单详情</span>
            </div>
            <div id="message"></div>
            <div class="row pad5A">
                <div class="col-md-4">订单编号：${order.orderNumber}</div>
                <div class="col-md-4">订单金额：${order.amount}</div>
                <div class="col-md-4">订单时间：${(order.createDate?string('yyyy-MM-dd HH:mm:ss'))!""}</div>

            </div>
            <div class="row pad5A">
                <div class="col-md-4">手机号：${order.mobile}</div>
                <div class="col-md-4">客户名：${order.userName}</div>
                <div class="col-md-4">邮政编码：${order.address}</div>
            </div>
            <div class="row pad5A">
                <div class="col-md-12">详细地址：${order.address}</div>
            </div>
            <div class="row pad5A">
                <div class="col-md-4">产品总数：${order.productAllCount}</div>
                <div class="col-md-4">交易状态：${order.statusValue}</div>
            </div>
            <div class="row pad5A">
                <div class="col-md-12">留言：${order.note}</div>
            </div>
        </div>
        <table class="table" id="message_table">
            <thead>
            <tr>
                <th>产品名称</th>
                <th>图片</th>
                <th>价格</th>
                <th>购买数量</th>
                <th>类型</th>
            </tr>
            </thead>
            <tbody>
            <#list order.orderDetails as orderDetail>
            <tr id="${orderDetail.id}">
                <td>
                ${orderDetail.name}
                </td>
                <td>
                    <#list orderDetail.productImgList as productImg>
                        <img src="${productImg.url}" height="80">
                    </#list>
                </td>
                <td>
                    ${orderDetail.productStyleInfo}
                </td>
                <td>
                ${orderDetail.productCount}
                </td>
                <td>
                ${orderDetail.productSortName}
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>


<script language="javascript">

    function preViewData() {
        var LODOP=getLodop();
        var strHTML = document.getElementById("order-page-content").innerHTML
        LODOP.PRINT_INITA(1,1,770,660,"订单预览功能");
        LODOP.SET_PRINT_STYLE("FontSize",11);
        LODOP.ADD_PRINT_HTM(30,5,"100%","80%",strHTML);
        LODOP.PREVIEW();
    }

    function printData() {
        var LODOP=getLodop();
        var strHTML = document.getElementById("order-page-content").innerHTML
        LODOP.PRINT_INITA(1,1,770,660,"订单直接打印");
        LODOP.SET_PRINT_STYLE("FontSize",11);
        LODOP.ADD_PRINT_HTM(30,5,"100%","80%",strHTML);
        LODOP.PRINT();
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


</script>


