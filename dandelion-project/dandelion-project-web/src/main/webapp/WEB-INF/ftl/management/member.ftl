<#import "/macro.ftl" as m>
<@m.page_header title='后台' />
<div id="page-content-wrapper">

    <div id="page-title">
        <h3>
            账户管理
        </h3>

        <div id="breadcrumb-right">
            <div class="float-right">
                <a href="/management/addMember" class="btn medium primary-bg">
                    <span class="button-content">
                    <i class="glyph-icon icon-cog float-left"></i>
                    添加
                    </span>
                </a>
            </div>
        </div>
    </div>

    <div id="page-content">
        <table class="table" id="exp">
            <thead>
            <tr>
                <th>账户名</th>
                <th>中文名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as member>
            <tr id="${member.id}" onclick="displayDetail(${member.id});">
                <td>
                ${member.name}
                </td>
                <td>
                ${member.chineseName!}
                </td>
                <td>
                    <div class="dropdown">
                        <a href="javascript:;" title="" class="btn medium bg-blue" data-toggle="dropdown">
                                <span class="button-content">
                                    <i class="glyph-icon font-size-11 icon-cog"></i>
                                    <i class="glyph-icon font-size-11 icon-chevron-down"></i>
                                </span>
                        </a>
                        <ul class="dropdown-menu float-right">
                            <li><a href="/management/updateMember/${member.id}" title=""><i class="glyph-icon icon-edit mrg5R"></i>修改</a>
                            </li>

                            <li class="divider"></li>
                            <li><a href="/management/deleteMember/${member.id}" class="font-red del" title=""><i
                                    class="glyph-icon icon-remove mrg5R"></i>删除</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <div class="button-group center-div">
    ${pagerHelper.content}
    </div>
</div>
<script language="JavaScript">
    function displayDetail(memberId){

        $("#"+memberId+"").after();
    }

    $(function () {
    <#if msg!=null>
        $.jGrowl("${msg}", {sticky: !1, position: 'bottom-right', theme: 'bg-green'});
    </#if>
    });
</script>