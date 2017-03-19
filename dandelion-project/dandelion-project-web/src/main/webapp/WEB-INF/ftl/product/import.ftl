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
            导入产品
        </h3>
        <div id="breadcrumb-right">
            <div class="float-right">
                <div style="float:right;margin-top:5px;">
                    <a  href="/upload/product/importTemple.xlsx" class="btn large primary-bg" ><span class="button-content">下载模版文件</span></a>
                </div>
            </div>
        </div>
    </div>
    <div id="page-content">
        <form id="form" action="/product/import"  method="POST" enctype="multipart/form-data" class="col-md-10 left-margin">
            <div >
                <div class="form-row">

                    <div class="form-label col-md-2">
                        <label for="">
                            产品文件:
                        </label>
                    </div>
                    <div class="form-input col-md-6 file-box " >
                        <input type='text' class='txt' id='coverImg' />
                        <input type='button' class='btn_brower' value='浏览...' />
                        <input type="file" name="productFile" id="productFile" class="file" onChange="document.getElementById('coverImg').value=this.value">
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


<script>

    function saveProduct(){
        $("#form").ajaxSubmit({
            type: "POST",
            url:"/product/import",
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