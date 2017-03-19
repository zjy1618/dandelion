<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<script type="text/javascript" src="/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/assets/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="/assets/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/assets/js/md5.js"></script>

<div id="page-content-wrapper">

    <div id="page-title">

        <h3>
        <#if member>修改<#else>添加</#if>账户
        </h3>
    </div>
    <div class="pad10A">
    <#if member>
    <form id="demo-form1" action="/management/updateMember" class="col-md-12" method="POST">
        <input type="hidden" name="id" value="${((member.id)!'')}"/>
    <#else>
    <form id="demo-form1" action="/management/addMember" class="col-md-12" method="POST">
    </#if>
        <div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    账户名:
                    <span class="required">*</span>
                </label>
            </div>
            <div class="form-input col-md-8">
                <input type="text" id="name" name="name" data-trigger="change" data-required="true"
                       class="parsley-validated"
                       value="${((member.name)!'')}">
            </div>
        </div>

		<div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    中文名:
                    <span class="required">*</span>
                </label>
            </div>
            <div class="form-input col-md-8">
                <input type="text" id="chineseName" name="chineseName" data-trigger="change" data-required="true"
                       class="parsley-validated"
                       value="${((member.chineseName)!'')}">
            </div>
        </div>

        <div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    密码:<span class="required">*</span>
                </label>
            </div>
            <div class="form-input col-md-2">
                <input type="text" id="password" name="password" data-trigger="change"
                <#if member> <#else>data-required="true" </#if> class="parsley-validated" <#if member??>value="******"<#else>value=""</#if>>
            </div>
        </div>


    </form>
        <div class="divider"></div>
        <div class="form-row">
            <input type="hidden" name="superhidden" id="superhidden">

            <div class="form-input col-md-10 col-md-offset-2">
                <a href="javascript:;" class="btn medium primary-bg radius-all-4 col-md-2" id="demo-form-2-valid">
                    提交
                </a>
            </div>
        </div>
    </div>
</div>


<script>
    $("#demo-form-2-valid").click(function () {
        if($('#password').val() == "******"){
            $('#password').val("");
        }

        $('option').val();
        $('#demo-form1').parsley('validate');
        $('#demo-form1').trigger('submit');
    });
</script>