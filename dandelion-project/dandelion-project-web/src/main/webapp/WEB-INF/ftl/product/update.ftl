<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<script type="text/javascript" src="/assets/js/jquery.form.js"></script>
<style type="text/css">
    input{ vertical-align:middle; margin:0; padding:0}
    .file-box{ position:relative;width:360px}
    .txt{ height:22px; border:1px solid #cdcdcd; width:260px;}
    .btn_brower{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
    .file{ position:absolute; top:0; left:0; height:24px; opacity: 0;width:330px }
</style>

<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            编辑产品
        </h3>
    </div>
    <div id="page-content">
        <form id="form" action="/product/update"  method="POST" enctype="multipart/form-data" class="col-md-10 left-margin">
            <input type="hidden" name="productCode" value="${product.productCode}">
            <input type="hidden" name="id" value="${product.id}">
            <div >
                <div class="form-row">
                    <div class="form-label col-md-2">
                        <label for="">
                                产品名称:
                        </label>
                    </div>
                    <div class="form-input col-md-5">
                        <input placeholder="产品名称" type="text" name="name" value="${product.name}"  data-required="true" class="parsley-validated" >
                    </div>
                </div>
                <div class="form-row">

                    <div class="form-label col-md-2">
                        <label for="">
                            缩略图:
                        </label>
                    </div>
                    <div class="form-input col-md-6 file-box " >
                        <input type='text' class='txt' id='coverImg' />
                        <input type='button' class='btn_brower' value='浏览...' />
                        <input type="file" name="coverImg" id="llPayFile" class="file" onChange="document.getElementById('coverImg').value=this.value">
                    </div>
                    <div class="form-input col-md-2">
                    <#list product.productImgList as productImg>
                        <img src="${productImg.url}" height="80">
                    </#list>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-label col-md-2">
                        <label for="">产品描述：</label>
                    </div>
                    <div class="form-input col-md-10" >
                        <textarea placeholder="问题描述详情" name="description"  class="small-textarea" data-required="true" data-rangelength="[1,1000]" class="parsley-validated">${product.description}</textarea>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-label col-md-2">
                        <label for="">类型：</label>
                    </div>
                    <div class="form-input col-md-5">
                        <select id="productSort" name="productSort.id">
                            <#list productSort_list as productSort>
                                <option value="${productSort.id}" <#if product.productSort.id == productSort.id> selected</#if>>${productSort.name}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-label col-md-2">
                        <label for="">
                            产品款式:
                        </label>
                    </div>
                    <div class="form-input col-md-2">
                        <a href="javascript:;" class="btn medium radius-all-4 mrg5A ui-state-default tooltip-button"
                           onclick="showAddProductStyle();">
                            <i class="glyph-icon icon-plus"></i>
                        </a>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-label col-md-2">
                    </div>
                    <div id="productStyle-rows" class="form-label col-md-10">
                        <div id="productStyle-row-template" class="hide" style="margin:8px 0;color:white">
                            <input type="hidden" name="productStyleIds">
                            <a id="updateid" href="javascript:;" class="btn large bg-gray radius-all-10" title="">
                                <span class="button-content"></span>
                            </a>
                            <a id="deleteid" class="btn medium radius-all-4 mrg5A ui-state-default tooltip-button" title="" href="javascript:;"><i class="glyph-icon icon-minus"></i></a>
                        </div>
                    </div>
                </div>

                <div class="divider"></div>
                <div class="form-row">
                    <div class="form-label col-md-2"></div>
                    <div class="form-input col-md-3">
                        <a href="javascript:;" class="primary-bg large radius-all-4 display-block btn white-modal-60" id="search"  onclick="saveProduct();">
                            <span class="button-content">
                                    保存
                            </span>
                        </a>
                    </div>
                </div>
            </div>
        </form>

    </div>
</div>

<div class="hide" id="add-product-style">
    <div class="pad10A">
        <form>
            <input type="hidden" name="productCode" value="${product.productCode}">
            <div class="form-row">
                <div class="form-label col-md-3">
                    <label for="">
                        款式名称:
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-6">
                    <input placeholder="款式名称" type="text" name="name" value="${productStyle.name}"  data-required="true" class="parsley-validated" >
                </div>
            </div>
            <div class="form-row">
                <div class="form-label col-md-3">
                    <label for="">
                        单价金额:
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-6">
                    <input placeholder="单价金额" type="text" name="unitAmount" value="${productStyle.unitAmount}"  data-required="true" class="parsley-validated" data-type="number">
                </div>
            </div>
            <div class="form-row">
                <div class="form-label col-md-3">
                    <label for="">
                        单位名称:
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-6">
                    <input placeholder="单位名称" type="text" name="unitName" value="${productStyle.unitName}"  data-required="true" class="parsley-validated" >
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    $(function(){
        var template = $('#productStyle-row-template');

        <#list productStyle_List as productStyle>

            var row = template.clone();

            row.removeAttr('id');
            row.removeAttr('class');

            row.attr('id', ${productStyle.id});
            row.find('input').val(${productStyle.id});
            row.find('#updateid span').html('${productStyle.productStyleInfo}');

            row.find('#deleteid').attr('onclick', 'deleteProductStyle("' + ${productStyle.id} + '");');
            row.find('#updateid span').attr('onclick', 'updateProductStyle("' + ${productStyle.id} + '");');

            template.after(row);
        </#list>
    });

    function showAddProductStyle() {
        var template = $('#add-product-style');
        var html = '<div id="productStyle_dialog" title="添加产品款式"><div>' + template.html() + '</div></div>';

        $(html).dialog({
            minWidth: 400,
            minHeight: 300,
            model: !0,
            dialogClass: "modal-dialog",
            closeOnEscape: !0,
            close: function () {
                $(this).dialog('destroy');
            },
            buttons: {
                提交: function () {
                    var valid = $('#productStyle_dialog form').parsley('validate');
                    if (!valid) {
                        return valid;
                    }
                    $.post("/productStyle/add", $('#productStyle_dialog form').serialize())
                            .done(function (data) {
                                if (data.status == 'success') {
                                    $.jGrowl(data.message, {sticky: !1, position: 'top-right', theme: 'bg-green'});

                                    var template = $('#productStyle-row-template');

                                    var row = template.clone();
                                    row.removeAttr('id');
                                    row.removeAttr('class');

                                    row.attr('id', data.data.id);
                                    row.find('input').val(data.data.id);
                                    row.find('#updateid span').html(data.data.productStyleInfo);

                                    row.find('#deleteid').attr('onclick', 'deleteProductStyle("' + data.data.id + '");');
                                    row.find('#updateid span').attr('onclick', 'updateProductStyle("' + data.data.id + '");');
                                    template.after(row);
                                }
                            })
                            .fail(function () {
                                $.jGrowl('添加产品款式', {sticky: !1, position: 'top-right', theme: 'bg-red'});
                            });

                    $('#productStyle_dialog').dialog('close');
                }
            }
        });
    }

    function updateProductStyle(id) {
        $.get('/productStyle/update/' + id)
                .done(function (data) {
                    var html = '<div id="updateProductStyle_dialog" class="hide" title="产品款式修改"><div class="pad10A">' + data + '</div></div>';
                    $(html).dialog({
                        minWidth: 400,
                        minHeight: 300,
                        model: !0,
                        dialogClass: "modal-dialog",
                        closeOnEscape: !0,
                        close: function () {
                            $(this).dialog('destroy');
                        },
                        buttons: {
                            提交: function () {
                                var valid = $('#updateProductStyle_dialog form').parsley('validate');
                                if (!valid) {
                                    return valid;
                                }
                                $.post('/productStyle/update', $('#updateProductStyle_dialog form').serialize())
                                        .done(function (data) {
                                            if (data.status == 'success') {

                                                $.jGrowl(data.message, {sticky: !1, position: 'top-right', theme: 'bg-green'});
                                                $('#' + data.data.id).find('#updateid span').html(data.data.productStyleInfo);

                                            }
                                        })
                                        .fail(function () {
                                            $.jGrowl('产品款式失败', {sticky: !1, position: 'top-right', theme: 'bg-red'});
                                        });

                                $('#updateProductStyle_dialog').dialog('close');
                            }
                        }
                    });
                })
                .fail(function () {
                    $.jGrowl('产品款式失败', {sticky: !1, position: 'top-right', theme: 'bg-red'});
                });

    }

    function deleteProductStyle(id) {
        $.messager.confirm("提示", "确认删除该商品款式吗？", function () {

            $.post('/productStyle/delete',{ "id" : id })
                    .done(function (data) {
                        if (data.status == 'success') {
                            $.jGrowl(data.message, {sticky: !1, position: 'top-right', theme: 'bg-green'});

                            $('#productStyle-rows').find('div[id=' + data.data + ']').remove();
                        }
                    })
                    .fail(function () {
                        $.jGrowl('删除商品款式失败', {sticky: !1, position: 'top-right', theme: 'bg-red'});
                    });
        });
    }

    function saveProduct(){
        $("#form").ajaxSubmit({
            type: "POST",
            url:"/product/update",
            dataType: "json",
            success: function(data){
                if(data["status"] == "success"){
                    $.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-green"});
                    window.setTimeout(function(){ location.href = "/product/list"; },1000);
                }else{
                    $.jGrowl(data["message"], {sticky:!1,position:"top-right",theme:"bg-red"});
                }
            }
        });
    }
</script>