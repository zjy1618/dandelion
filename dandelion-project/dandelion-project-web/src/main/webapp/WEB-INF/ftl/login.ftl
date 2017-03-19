<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>帘到家登陆</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


    <link rel="stylesheet" type="text/css" href="/assets/css/minified/aui-production.min.css" />
    <link id="layout-theme" rel="stylesheet" type="text/css" href="/assets/themes/minified/fides/color-schemes/dark-blue.min.css" />
    <script type="text/javascript" src="/assets/js/minified/aui-production.js"></script>
    <script>
        jQuery(window).load(
        	function(){
                var wait_loading = window.setTimeout( function(){
                            $('#loading').slideUp('fast');
                            jQuery('body').css('overflow','auto');
                },1000
             );
         });
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body style="overflow: hidden;">

<div id="loading" class="ui-front loader ui-widget-overlay bg-white opacity-100">
    <img src="/assets/images/loader-dark.gif" alt="" />
</div>
<div id="page-wrapper" class="demo-example">
<div id="page-header" class="clearfix">
</div><!-- #page-header -->
<div id="page-content">
<div class="pad20A mrg25T">
    <div class="row mrg25T">
        <form id="form" action="/login" class="col-md-4 center-margin form-vertical mrg25T" method="POST">
            <div class="ui-dialog modal-dialog mrg25T" id="login-form" style="position: relative !important;">
                <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
                    <span class="ui-dialog-title">帘到家运营系统登陆</span>
                </div>
                <div class="pad20A pad0B ui-dialog-content ui-widget-content">
                	<#if errorMsg??>
                		<div class="infobox error-bg mrg20B">
		                    <p>${errorMsg}</p>
		                </div>	
                	</#if>
                    <div class="form-row">
                        <div class="form-label col-md-2">
                            <label for="">
                                用户名:
                            </label>
                        </div>
                        <div class="form-input col-md-10">
                            <div class="form-input-icon">
                                <i class="glyph-icon icon-phone ui-state-default"></i>
                                <input placeholder="用户名" value="" type="text" name="username" id="username" data-required="true">
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-label col-md-2">
                            <label for="">
                                密码:
                            </label>
                        </div>
                        
                        <div class="form-input col-md-10">
                            <div class="form-input-icon">
                                <i class="glyph-icon icon-unlock-alt ui-state-default"></i>
                                <input placeholder="密码" value="" type="password" name="password" id="pass" data-required="true">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ui-dialog-buttonpane text-center">
                    <button type="submit" class="btn large primary-bg text-transform-upr font-bold font-size-11 radius-all-4" id="demo-form-valid" title="Validate!"
                    	onclick="$('#form').parsley( 'validate' );">
                        <span class="button-content">
                            登陆
                        </span>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</div><!-- #page-content -->
</div><!-- #page-main -->
</div><!-- #page-wrapper -->
</body>
</html>
