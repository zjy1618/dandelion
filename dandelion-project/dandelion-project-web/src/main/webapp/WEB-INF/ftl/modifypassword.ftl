<#import "/macro.ftl" as m>
<@m.page_header title='后台' />

<div id="page-content-wrapper">

    <div id="page-title">
        	<b>修改登录密码</b>
    </div>

    <div class="pad10A">
    <form id="modify-form" action="/modifypassword" class="col-md-12" method="POST">
        <div class="col-md-12">
        <#if errorMsg??>
                		<div class="infobox error-bg mrg20B">
		                    <p>${errorMsg}</p>
		                </div>	
        </#if>
            <div class="form-row">
                <div class="form-label col-md-2">
                    <label for="">
                        原登录密码：
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-3">
                    <input type="password" id="oldPassword" name="oldPassword" data-trigger="change" data-required="true"
                           class="parsley-validated">
                </div>
            </div>
        </div>
        <div class="col-md-12">
            <div class="form-row">
                <div class="form-label col-md-2">
                    <label for="">
                        新登录密码：
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-3">
                    <input type="password" id="newPassword" name="newPassword" data-trigger="change" data-required="true"
                           class="parsley-validated">
                </div>
            </div>
        </div>
        <div class="col-md-12">
            <div class="form-row">
                <div class="form-label col-md-2">
                    <label for="">
                        再次输入新登录密码：
                        <span class="required">*</span>
                    </label>
                </div>
                <div class="form-input col-md-3">
                    <input type="password" id="newPasswordAgain" name="newPasswordAgain" data-trigger="change" data-required="true"
                           class="parsley-validated">
                </div>
            </div>
        </div>
    </form>
        <div class="form-row">
            <div class="form-input col-md-10 col-md-offset-2">
                <a href="javascript:;" class="btn medium primary-bg radius-all-4 col-md-2" id="save">
                    保存
                </a>
            </div>
        </div>
    </div>

</div>

<script>
    $("#save").click(function () {
        $('#modify-form').parsley('validate');
        $('#modify-form').trigger('submit');
    });
</script>