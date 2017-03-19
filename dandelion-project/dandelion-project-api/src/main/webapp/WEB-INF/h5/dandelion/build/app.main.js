function openView(t, a) {
    var e = a ? t: "链接",
        n = a ? a: t;
    console.log("openview", e),
        PG.fromApp || Param.app || window.comjs ? (PG.iosAppBug = !0, PG.openWebView(e, n, "返回")) : window.open(n)
}
function initWH(t) {
    winW = document.documentElement.clientWidth,
        winH = document.documentElement.clientHeight,
        conW = $pageIndex.find(".wrap>.con").width();
    var a = window.device_info,
        e = isIOS && a && a.screen_height; (e || t) && (e && $body.height(winH = a.screen_height), pageScroll.onResize())
}
function showMask(t, a) {
    var a = a || {},
        e = t instanceof $ ? t: $("#mask-" + t);
    a.inPage && e.appendTo(".page.show"),
        setTimeout(function() {
                e.addClass("show"),
                noPG && !e.parents(".page").length && (e.addClass("history"), history.pushState(null, null, null))
            },
            $(".topmask.show").length ? 100 : 0),
        refreshPage(e, !0)
}
function closeMask(t) {
    var a = $(".topmask.show");
    return t && (a = "string" == typeof t ? $("#mask-" + t) : t),
        a.hasClass("history") ? history.back() : void a.removeClass("show")
}
function showChoose(t, a, e, n) {
    var n = n || {},
        i = n.defVals || {};
    n.defVal = n.defval,
    n.defVal && (i = [n.defVal]);
    var o = a;
    $.isNumeric(a) && (a += ""),
    "string" == typeof a && (o = [a]);
    var r = o.length;
    r && ($maskChoose.find(".picker").attr("cols", r).find(".wrap:lt(" + r + ")").show().css("width", 100 / r + "%").last().nextAll().hide(), $maskChoose.find(".head p").text(t), $maskChoose.find(".head span").unbind("click").click(function() {
        if (closeMask("choose"), $(this).is(".right")) {
            var t = [],
                a = [];
            if ($maskChoose.find(".wrap:visible").each(function() {
                    t.push($(this).attr("val")),
                        a.push($(this).attr("txt"))
                }), 1 == r && (t = t[0], a = a[0], !t && !a)) return console.info("no choose");
            e && e(t, a)
        }
    }), showMask("choose"));
    for (var s in o) {
        s *= 1;
        var l = $maskChoose.find(".wrap").eq(s),
            c = o[s + 1];
        r && l.removeData("onchange"),
        "function" == typeof c && l.data("onchange", c);
        var d = o[s];
        l.attr("unit", n.unit || "").attr("rel", n.name).attr("data", "function" == typeof d ? "": d).data("setData")(n.append);
        var u = i[s];
        u ? "function" == typeof d && (l.attr("defVal", u), u = null) : (u = l.attr("defVal"), l.removeAttr("defVal")),
            u ? l.iScrollTo(u) : l.iscrollToIndex(0)
    }
}
function showActionPopup(t, a, e) {
    var n = "",
        i = 0;
    t && (n += '<li class="title">' + t + "</li>", i = -1);
    for (var o in a) n += '<li class="bdt" i="' + o + '">' + a[o] + "</li>";
    n += '<li class="cancel">取消</li>',
    "undefined" != typeof clipboard && clipboard.destroy(),
        $maskAction.find(".action").html(n).find("li").click(function() {
            var t = $(this);
            if (!t.hasClass("title")) {
                if (t.hasClass("cancel")) return closeMask("action");
                closeMask("action"),
                    "function" == typeof e ? e(t.attr("i"), t.text()) : console.log("无回调fn")
            }
        }),
        showMask("action")
}
function toast(t, a) {
    var a = a || 1500;
    stopLoad(t, 1, a)
}
function toastSuc(t, a) {
    stopLoad(t, 0, a)
}
function toastFail(t, a) {
    stopLoad(t, 2, a)
}
function showInfo(t, a) {
    if ("function" == typeof a) a = {
        onOk: a
    };
    else var a = a || {};
    $maskInfo.addClass("single").removeClass("input"),
    a.noCancel || !a.onOk && !a.onInput || $maskInfo.removeClass("single");
    var e = $maskInfo.find("input").val(a.defval || "").attr("val", "");
    a.onInput && (e.unbind("keyup blur"), a.bindInput && a.bindInput(e), $maskInfo.addClass("input"), e.attr("placeholder", a.hint || ""), setTimeout(function() {
            e.focus()
        },
        800));
    var n = a.title || "提示";
    $maskInfo.find(".title").text(n),
        $maskInfo.find(".info").html(t),
        $maskInfo.find(".subinfo").html(a.subinfo || "");
    var i = a.onOk ||
            function() {},
        o = a.onCancel ||
            function() {};
    $maskInfo.find(".ok").html(a.okTxt || "确定").unbind("click").bind("click",
        function() {
            if (a.onInput) {
                var t = e.attr("val") || e.val().trim(),
                    n = "";
                if (a.regExp && !t.match(a.regExp) ? (n = a.regHint || "格式不正确", console.log(a.regExp)) : a.checkInputMsg && (n = a.checkInputMsg(t)), n) return setTimeout(function() {
                        e.focus()
                    },
                    1200),
                    toast(n);
                e.blur(),
                    a.onInput(t),
                    closeMask("info")
            } else {
                var o = i();
                if (o) return;
                closeMask("info")
            }
        }),
        $maskInfo.find(".cancel").html(a.cancelTxt || "取消").unbind("click").bind("click",
            function() {
                closeMask("info"),
                    o()
            }),
        showMask("info")
}
function showImagePage(t, a) {
    t.match(/^http|upload/) || (t = Main.getImgPath(t));
    var a = a || {},
        e = '<div class="pos_mask"><div id="img_wrap" class="y_center w100"><img src="' + t + '" class="w100" /></div></div>',
        n = {
            id: "show_image",
            title: a.title || "查看图片",
            con: e,
            wrapAttr: ""
        };
    a.onChange && (n.topRight = "更换"),
        Tool.makeAndShowPage(n),
    a.onChange && $curPage.find(".top .right").click(a.onChange);
    var i = $("#img_wrap");
    i.find("img").load(function() {
        var t = {
                zoom: !0,
                scrollX: !0,
                scrollY: !0,
                freeScroll: !0,
                mouseWheel: !0,
                wheelAction: "zoom"
            },
            a = new IScroll(i[0], t);
        a.on("zoomEnd",
            function() {
                i.showClass("slides", this.scale > 1)
            })
    })
}
function showInputPage(t, a) {
    function e(t) {
        backPage(),
        "function" == typeof a && a(t)
    }
    function n() {
        if (p) {
            if (!p.isLoad()) return toast("未选择图片");
            var a = t.uploadPath || "app.php?act=post_img",
                n = Main.buildUrl(Main.api_pre + a, "img_type=" + t.imgType);
            return showLoad("上传中"),
                void $.post(n, p.getImg().split(",")[1],
                    function(t) {
                        stopLoad(),
                            p = null,
                            console.log(t),
                            e(t)
                    })
        }
        var o = Main.checkInput("wrap-input", !0);
        return o.msg && "checkbox" != i ? o.toastMsg() : void e(o.form.input)
    }
    if (!a) return toast("无回调");
    var t = t || {},
        i = t.type || "text",
        o = "image" == i,
        r = t.title || "内容",
        s = t.val || "",
        l = t.req || "";
    t.hint || (t.hint = "请输入" + r);
    var c = 'name="input" title="' + r + '" preval="' + s + '" p="' + (t.pattern || "") + '" req="' + l + '" ',
        d = {
            id: "input",
            pageClass: "down",
            backTitle: "取消",
            title: r,
            topRight: '<span class="right save" name="input" title="保存"></span>',
            con: '<input class="input" ' + c + ' type="' + i + '" placeholder="' + t.hint + '" value="' + s + '" />'
        };
    if ("checkbox" == i) {
        var u = '<div class="checkbtn right" val="' + s + '" ' + c + "></div>";
        d.con = '<ul class="set"><li><label>设置</label>' + u + "</li>"
    } else o && (d.wrapAttr = "", d.con = '<div id="clip_area" class="pos_mask" ' + c + ' val="' + s + '" /><div class="clip_btm"><p><i class="icon-file-upload"/>选择图片<input type="file" class="pos_mask"/></p></div>');
    Tool.makeAndShowPage(d);
    var p;
    if (o) {
        t.imgType = t.imgType || "jpg";
        var f = '<p><i class="icon-rotate-left"/>左转</p><p><i class="icon-rotate-right"/>右转</p>';
        p = photoClip("#clip_area", {
            file: "#page-input .clip_btm input",
            outputType: t.imgType,
            loadComplete: function() {
                f && ($curPage.find(".clip_btm").append(f).find("p").click(function() {
                    var t = $(this).find("i").attr("class");
                    /left/.test(t) ? p.rotateBy( - 90) : /right/.test(t) && p.rotateBy(90)
                }), f = "")
            }
        })
    }
    var h = $curPage.find("input");
    h.on("keyup",
        function(t) {
            13 == t.keyCode && (h.blur(), n())
        }),
        $curPage.find(".top .save").click(n),
        $curPage.find(".wrap .con").click(function() {
            h.focus()
        }),
        setTimeout(function() {
                isIOS || h.focus()
            },
            260)
}
function showFirstPage(t) {
    var t = t || Param.page;
    if (t) {
        var a = $("#page-" + t);
        a.length && ($(".page.show").removeClass("show"), a.addClass("nomove show exit"), setTimeout(function() {
                a.removeClass("nomove")
            },
            300))
    }
    Param.showpage && showPage(Param.showpage),
        $body.addClass("show app" + Param.big_app_id + " plat" + Param.plat)
}
Date.prototype.format = function(t) {
    var a = {
        "y+": this.getFullYear(),
        "m+": this.getMonth() + 1,
        "d+": this.getDate(),
        "x+": "日一二三四五六".split("")[this.getDay()],
        "H+": this.getHours(),
        h: (this.getHours() > 12 ? "下午": "上午") + this.getHours() % 12,
        "i+": this.getMinutes(),
        "s+": this.getSeconds(),
        S: this.getMilliseconds()
    };
    for (var e in a) {
        var n = new RegExp("(" + e + ")");
        if (n.test(t)) {
            var i = a[e] + "";
            RegExp.$1.length < 4 && RegExp.$1.length > 1 && 1 == i.length && (i = "0" + i),
                t = t.replace(n, i)
        }
    }
    return t
},
    $.supportTouch = "ontouchstart" in window || window.DocumentTouch && document instanceof DocumentTouch;
var touchEv = {
    start: $.supportTouch ? "touchstart": "mousedown",
    move: $.supportTouch ? "touchmove": "mousemove",
    end: $.supportTouch ? "touchend": "mouseup"
};
$.extend({
    getUrlParam: function(t) {
        var t = t || location.href,
            a = {},
            e = t.split("?");
        if (a.page_link = e[0], 2 == e.length) {
            var n = e[1].split("&");
            for (var i in n) {
                var o = n[i].match(/([^=|&]+)=([^&]*)/);
                o && (a[o[1]] = decodeURIComponent(o[2]))
            }
        }
        return a
    },
    getSrc: function(t, a) {
        $.getSrcData = $.getSrcData || {};
        var e = $.getSrcData[t];
        return e ? void(a && a(e)) : void $.get(t,
            function(e) {
                $.getSrcData[t] = e,
                a && a(e)
            })
    },
    multiStr: function(t, a) {
        if (isNaN(a) || 2 > a) return t;
        for (var e = "",
                 n = 0; a > n; n++) e += t;
        return e
    },
    cookie: function(t) {
        var a = $.getUrlParam("?" + document.cookie.replace(/;\s+/g, "&"));
        return t ? a[t] : a
    },
    addRule: function(t, a, e) {
        var n = document.styleSheets[document.styleSheets.length - 1];
        n.addRule(t, a, e)
    },
    getIndexInArr: function(t, a) {
        var e = null;
        return t.forEach(function(n, i) {
            null === e && a >= n && (i == t.length - 1 || a < t[i + 1]) && (e = i)
        }),
            e
    },
    getSetting: function(t, a) {
        var e = a;
        return t = "setting_" + t,
        window.hasOwnProperty(t) && (e = window[t]),
            e
    }
}),
    $.fn.extend({
        showClass: function(t, a) {
            a ? $(this).addClass(t) : $(this).removeClass(t)
        },
        iScrollTo: function(t, a) {
            a && 10 > t && (t = "0" + 1 * t);
            var e = $(this).find("[val=" + t + "]");
            e.length || (e = $(this).find("li:contains(" + t + ")")),
            e.length && $(this).iscrollToIndex(e.index() - 2)
        },
        iscrollToIndex: function(t, a) {
            var e = $(this).attr("id"),
                n = scroll_map[e];
            return n ? void n.scrollToElement($(this).find("li").eq(t).get(0), a ? 200 : 0) : console.log(e, "no iscroll")
        },
        touch: function(t) {
            return $(this).each(function() {
                this.addEventListener(touchEv.start, t, !1)
            }),
                this
        },
        transform: function(t) {
            for (var a = 0; a < this.length; a++) {
                var e = this[a].style;
                e.webkitTransform = e.MsTransform = e.msTransform = e.MozTransform = e.OTransform = e.transform = t
            }
            return this
        },
        transition: function(t) {
            "string" != typeof t && (t += "ms");
            for (var a = 0; a < this.length; a++) {
                var e = this[a].style;
                e.webkitTransitionDuration = e.MsTransitionDuration = e.msTransitionDuration = e.MozTransitionDuration = e.OTransitionDuration = e.transitionDuration = t
            }
            return this
        },
        transitionEnd: function(t) {
            function a(o) {
                if (o.target === this) for (t.call(this, o), e = 0; e < n.length; e++) i.off(n[e], a)
            }
            var e, n = ["webkitTransitionEnd", "transitionend", "oTransitionEnd", "MSTransitionEnd", "msTransitionEnd"],
                i = this;
            if (t) for (e = 0; e < n.length; e++) i.on(n[e], a);
            return this
        }
    });
var Param = $.getUrlParam();
$.fn.setSlide = function(t) {
    var t = t || {};
    return $(this).each(function() {
        function a(t) {
            var a = function() {
                m = 0,
                    i(),
                    l.remove(),
                    c.removeClass("slideup").css("marginTop", ""),
                    t()
            };
            c.length ? c.addClass("slideup").css("marginTop", -c.get(0).offsetHeight).transitionEnd(a) : l.slideUp(200, a)
        }
        function e(e) {
            var n = {
                    onDel: a
                },
                i = $(this);
            n.txt = i.text(),
                n.index = i.index(),
                n.li = l,
                n.rel = l.attr("rel"),
                n.label = l.children("label").text(),
            t.onClick && t.onClick(i, n)
        }
        function n() {
            var a = u.attr("actions") || l.attr("actions");
            if (a) {
                var e = t.acts || a.split("、"),
                    n = "",
                    i = ["", "#fd9b27", "#5babe4"];
                return e.forEach(function(a, e) {
                    var o = "line-height:" + l.get(0).offsetHeight + "px;",
                        r = (t.colors || i)[e] || "";
                    "删除" == a && (r = "#fc3d39"),
                    r && (o += "background:" + r + ";"),
                        n += '<p style="' + o + '">' + a + "</p>"
                }),
                    $('<li class="action">' + n + "</li>")
            }
        }
        function i() {
            return 0 != m ? (m = 0, void f.transform("translateX(0)").transitionEnd(i)) : (null != f && (f.remove(), p.remove(), h.remove(), f = null), void(b = !1))
        }
        function o(t) {
            if (g = 0, v = 0, k = !1, !$(t.target).closest("li.action").length) {
                if (b || null != f) return void i();
                if (l = $(t.target).closest("li"), p = n(), !p) return void(k = !0);
                c = l.next(),
                    f = l.clone();
                var a = l.offset().top - u.offset().top,
                    e = l.get(0).offsetHeight;
                f.addClass("slide").add(p).css({
                    top: a,
                    height: e
                })
            }
        }
        function r(t) {
            if (Math.abs(t.dy) > Math.abs(t.dx)) return k = !0,
                void i();
            if (w) {
                var a = Math.min(Math.max( - d, t.dx + v), 0);
                t.dx < -d && t.dx < g ? v = Math.abs(t.dx) - d: t.dx > 0 && t.dx > g && (v = -t.dx),
                    g = t.dx,
                    m = a,
                    f.transform("translateX(" + a + "px)")
            } else if (t.endTouch) {
                if (t.dx > 0) return void(k = !0);
                u.append(h),
                    u.append(p),
                    u.append(f),
                    p.find("p").click(e),
                    d = p.width(),
                    w = !0
            }
        }
        function s(t) {
            if (w) {
                w = !1;
                var a = Math.abs(m);
                f.addClass("move");
                var e = 0;
                a > 30 && t.dure < 500 || a > d / 2 ? (e = -d, m = e, b = !0, setTimeout(function() {
                    f.transform("translateX(" + e + "px)")
                })) : i()
            }
        }
        var l, c, d, u = $(this),
            p = null,
            f = null,
            h = $('<li class="slides mask" />'),
            v = 0,
            g = 0,
            m = 0,
            w = !1,
            b = !1,
            k = !1;
        u.data("onTouch",
            function(t, a) {
                if ("Start" == t) o(a);
                else {
                    if (k || b || null == f) return;
                    "Move" == t ? r(a) : s(a)
                }
            })
    }),
        this
},
    Param.big_app_id = Param.big_app_id || 1,
"undefined" != typeof PG && (PG.setAppBG = function() {
    isIOS && 8 == Param.big_app_id && PG.exec("setWebViewBackgroundColor", null, {
        color: "ffffff",
        alpha: 1,
        isBackroundDark: !1
    })
},
    PG.hideDefaultImage(), PG.setAppBG(), PG.exec("setWebViewScrollLock", null, {
    lock: !0
}), PG.exec("hideNavigationBar",
    function() {
        initWH(!0)
    },
    null), PG.getDeviceInfo(function(t) {
    PG.fromApp = !0;
    var a = t.DeviceInfo;
    window.device_info = a,
    isIOS && (8 == Param.big_app_id && delete a.screen_height, initWH(!0), refreshPage())
}));
var noPG = "undefined" == typeof PG,
    $pageIndex = $("#page-index"),
    winW,
    winH,
    conW,
    $body = $("body"),
    $main = $("#main"),
    scroll_map = {},
    $maskChoose = $('<div class="topmask popup clickoff" id="mask-choose"><div class="outer"><div class="head bdb"><span>取消</span><p>请选择</p><span class="right">确定</span></div><div class="picker" cols="3"><div class="choose_bd bdt bdb"></div><div class="wrap" id="select_choose1"><ul class="set"></ul></div><div class="wrap" id="select_choose2"><ul class="set"></ul></div><div class="wrap" id="select_choose3"><ul class="set"></ul></div></div></div></div>').appendTo($body); +
    function() {
        var t = navigator.userAgent;
        window.isAndroid = !!t.match(/android/i),
            window.isIOS = !!t.match(/iPhone|iPad/i),
            window.isPC = !isAndroid && !isIOS,
            window.isWeixin = !!t.match(/MicroMessenger/i)
    } (),
isNaN(Param.plat) && (Param.plat = isAndroid ? 2 : 1);
var addFixtop = !!Param.fix_top;
initWH(),
    window.onresize = function() {
        initWH(!0),
            $(".page.show .tabbar li.active").click()
    },
    +
        function() {
            function t() {
                if (addFixtop) {
                    var t = '<div class="prev">' + $pageIndex.children(".top").html() + '</div><div class="next"></div>';
                    g.length ? g.removeClass("push").html(t) : g = $('<div class="top" id="top">' + t + "</div>").prependTo($main),
                        g.delegate(".right", "click",
                            function(t) {
                                $curPage.find(".top .right").click()
                            })
                }
            }
            function a(t, a) {
                "string" == typeof t && t && (t = $("#page-" + t));
                var t = t || $curPage;
                t.find(".wrap[id]").each(function() {
                    pageScroll.refresh(this.id, a)
                })
            }
            function e() {
                return f ? (setTimeout(function() {
                        f = !1
                    },
                    300), !0) : !1
            }
            function n(t, n) {
                if (!e()) {
                    if ("string" == typeof t && (t = $("#page-" + t)), !t.length) return console.log("页面不存在");
                    if (a(t, !0), t.hasClass("show")) return console.info("the page is current");
                    if (t.hasClass("prev")) return console.info("the page is previous");
                    var n = n || {};
                    n.rel && t.data("rel", n.rel),
                    n.exit && t.addClass("exit"),
                    n.clear && Main.resetForm(t.children(".wrap").attr("id")),
                        f = !0,
                        setTimeout(function() {
                                f = !1
                            },
                            300),
                        c = $main.children(".show").removeClass("show"),
                        $curPage = t.addClass("show").data("prev", c),
                        $curPage.find(".tabbar li").eq(n.tab || 0).attr("init", 1).click(),
                        $curPage.hasClass("down") ? c.addClass("keep") : ($main.find(".cur_prev").removeClass("cur_prev"), c.addClass("prev cur_prev"), addFixtop && (g.children(".prev").html(c.children(".top").html()), g.children(".next").html($curPage.children(".top").html()), g.removeClass("push").addClass("move"), setTimeout(function() {
                            g.addClass("push"),
                                setTimeout(function() {
                                        g.removeClass("move")
                                    },
                                    250)
                        }))),
                        d++,
                    noPG && !$curPage.hasClass("exit") && setTimeout(function() {
                            n.pushTitle && (document.title = n.pushTitle),
                                history.pushState(n || null, n.pushTitle || null, n.pushUrl || null)
                        },
                        $(".topmask.show").length ? 100 : 0)
                }
            }
            function i(t) {
                if (!e()) {
                    var t = t || {};
                    if (t.noExit && $curPage.removeClass("exit"), $curPage.is($pageIndex) || $curPage.hasClass("exit")) return void PG.exitApp();
                    var a = $curPage.hasClass("down");
                    "page-input" == $curPage.attr("id") && $("#sys_inp").removeClass("focus show"),
                        f = !0,
                        $curPage.removeClass("show").addClass("visible"),
                        c.removeClass("prev cur_prev keep").addClass("show"),
                        $curPage.add(c).removeAttr("style"),
                    !a && addFixtop && g.addClass("move").removeClass("push"),
                        setTimeout(function() {
                                $curPage.removeClass("visible"),
                                    $curPage = c,
                                    c = $curPage.data("prev"),
                                    f = !1,
                                c && (c.addClass("cur_prev"), addFixtop && !a && (f = !0, setTimeout(function() {
                                        f = !1,
                                            g.children(".prev").html(c.children(".top").html()),
                                            g.children(".next").html($curPage.children(".top").html()),
                                            g.removeClass("move").addClass("push")
                                    },
                                    260))),
                                    d--
                            },
                            300)
                }
            }
            function o(a) {
                function e() {
                    console.log("back", Param.prev_len),
                        history.back(),
                        Param.prev_len--,
                    Param.prev_len > 0 && setTimeout(e, 100)
                }
                Param.prev_len = $(".page.prev").removeClass("prev").add($curPage).not($pageIndex).removeClass("exit").length,
                0 != Param.prev_len && (c.removeClass("keep cur_prev"), c = $pageIndex.addClass("cur_prev"), d = 1, $curPage.hasClass("down") && ($pageIndex.addClass("keep nomove"), setTimeout(function() {
                    $pageIndex.removeClass("nomove")
                }), t()), i(), e(), setTimeout(function() {
                        a && a()
                    },
                    300))
            }
            function r(t) {
                if (noPG && !t) return history.back();
                $curPage.find("input").blur();
                var a = $(".page.show .topmask.show,body>.topmask.show").not(noPG ? "#loading": "#loading.done").last();
                return a.length ? void((a.is(".clickoff,.backoff") || noPG) && a.removeClass("show")) : void i()
            }
            function s(t) {
                $main.removeClass("onmove").addClass("endmove"),
                    t ? r() : $curPage.add(c).removeAttr("style"),
                    setTimeout(function() {
                            $main.removeClass("endmove")
                        },
                        150),
                    u = !1,
                    h = 0
            }
            function l() {
                var t;
                $main.swipe({
                    swipeStatus: function(a, e, n, i, o, r) {
                        if (p) {
                            var l = $(a.target);
                            if (!f) {
                                var h = $curPage.hasClass("down");
                                if (l.closest(".slides").length || $curPage.is(".exit,.lazy") || $curPage.find(".topmask.show").length) return u && s(),
                                    void(p = !1);
                                if (!u && d > 0) {
                                    if ("right" != n || h) {
                                        if ("down" == n && h) {
                                            if (!$(a.target).parents(".top").length) return;
                                            u = !0
                                        }
                                    } else {
                                        if (a.layerX > 80 && 10 > i) return;
                                        u = !0
                                    }
                                    u ? (t = n, $main.addClass("onmove")) : i > 10 && (p = !1)
                                }
                                if (u) if (v = i, "right" == t) {
                                    if ("left" == n) return s();
                                    $curPage.transform("translateX(" + v + "px)");
                                    var m = parseInt((winW - v) / 3);
                                    if (c.transform("translateX(-" + m + "px)"), addFixtop) {
                                        var w = parseFloat(v / winW).toFixed(4);
                                        g.children(".prev").css("opacity", w).children("h1").css("left", 50 * w - 50 + "%"),
                                            g.children(".next").css("opacity", 1 - w).children("h1").css("left", 50 * w + "%")
                                    }
                                } else if ("down" == t) {
                                    if ("up" == n) return s();
                                    $curPage.transform("translateY(" + v + "px)")
                                }
                            }
                        }
                    },
                    swipe: function(a, e, n, i, o) {
                        if (u) {
                            var r = "right" == t ? winW / 2 : winH / 2,
                                l = v > r || 600 > i && n > 60 && n / i > .3;
                            addFixtop && "right" == t && (g.addClass("endmove"), setTimeout(function() {
                                g.find("[style]").removeAttr("style"),
                                    setTimeout(function() {
                                            g.removeClass("endmove")
                                        },
                                        100)
                            })),
                                s(l)
                        }
                        p = !0
                    },
                    threshold: 0
                })
            }
            window.$curPage = $main.children(".show").last();
            var c, d = 0,
                u = !1,
                p = !0,
                f = !1,
                h = 0,
                v = 0,
                g = $("#top");
            t(),
            $curPage.is($pageIndex) || ($(".page.show").removeClass("show"), $pageIndex.addClass("show"), n($curPage)),
                window.onpopstate = function(t) {
                    Param.prev_len > 0 || r(!0)
                },
            addSwipe && l(),
                window.showPage = n,
                window.backPage = r,
                window.closePage = i,
                window.closeAllPage = o,
                window.refreshPage = a
        } ();
var pageScroll = {
    initTabbar: function() {
        this.outWrap.find(".tabbar").addClass("bdb").each(function() {
            var t = $(this).parents(".page,.outer"),
                a = t.find(".tab-auto"),
                e = a.children().length,
                n = 0,
                i = 100 / e + "%",
                o = $(this).find("li").css("width", i),
                r = $('<span class="indicator"></span>').css("width", i).appendTo(this);
            o.click(function(t) {
                var e = $(this);
                e.addClass("active").siblings(".active").removeClass("active");
                var i = e.offset().left - e.parents(".page").offset().left;
                r.transform("translateX(" + i + "px)"),
                    n = e.index(),
                    a.transform("translateX(" + n * winW * -1 + "px)");
                var o = a.find(".wrap").eq(n);
                o.addClass("active").siblings(".active").removeClass("active"),
                t.isTrigger && !e.attr("init") || (pageScroll.refresh(o.attr("id"), !0), e.removeAttr("init"))
            });
            var s = a.parents(".wrap"),
                l = s.attr("id");
            s.removeAttr("id").children(".con").removeClass("con"),
                a.children().each(function(t) {
                    $(this).css("width", 100 / e + "%").show().addClass("wrap").attr("id", "tab_" + l + t).html('<div class="con">' + $(this).html() + "</div>")
                }),
                a.css("width", 100 * e + "%");
            var c = -1,
                d = 0,
                u = 0,
                p = !1;
            a.data("onTouch",
                function(t, i) {
                    function r() {
                        a.removeClass("trans slides")
                    }
                    if ("Start" != t) {
                        if ("Move" == t) {
                            if (!p) return;
                            if (c * i.dx < 0 || "x" != i.moveFlag) return;
                            return d = i.dx,
                                a.transform("translateX(" + (u + d) + "px)"),
                                !0
                        }
                        var s = !1,
                            l = n;
                        return Math.abs(d) > winW / 5 && (l = 0 > d ? ++n: --n, s = !0, a.find("input").blur()),
                            a.addClass("trans"),
                            o.eq(l).click(),
                            setTimeout(r, 200),
                            a.ontransitionend = r,
                            d = 0,
                            p = !1,
                            s
                    }
                    if (! (i.pageX < 30 || 2 > e)) {
                        p = !0;
                        var f = a.attr("style").match(/translateX\((-?\d+)px\)/);
                        u = f ? 1 * f[1] : 0,
                            c = n == e - 1 ? 1 : 0 == n ? -1 : 0,
                            a.showClass("slides", 0 != n)
                    }
                })
        })
    },
    onResize: function() {
        this.outWrap = $body,
            this.initWrap()
    },
    initWrap: function(t) {
        var t = t || this.outWrap;
        t.find(".wrap").each(function() {
            var t = winH,
                a = $(this),
                e = a.parents(".wrap,.outer");
            if (!a.parents(".popup,.m_tpl").length) {
                e.length && (t = e.height());
                var n = 0;
                a.siblings().not(".wrap").each(function() {
                    var a = $(this).get(0).offsetHeight;
                    "absolute" != $(this).css("position") ? t -= a: $(this).is(".ios9") && (n += a)
                }),
                    a.height(t).children().css("minHeight", t + "px"),
                n && a.css("paddingTop", n)
            }
        })
    },
    initCurWrap: function() {
        this.initWrap($curPage),
            refreshPage()
    },
    getPickerOpt: function(t, a) {
        function e(t) {
            var a = n.attr("data") || "",
                e = [];
            if ( - 1 != a.indexOf("->")) for (var i = a.split("->"), o = 3 == i.length ? i[2] : 1, r = 1 * i[0]; r <= 1 * i[1]; r += 1 * o) e.push(10 > r && n.is("[time]") ? "0" + r: r);
            else e = a.split("、");
            var s = "<li></li><li></li>",
                l = "";
            for (var r in e) {
                var c = e[r] + "",
                    d = c,
                    i = c.split("-");
                2 == i.length && (d = i[0], c = i[1]),
                    c += t || "",
                    l += '<li val="' + d + '">' + c + "</li>"
            }
            n.removeAttr("i val txt").find("ul").html(s + l + s),
                pageScroll.refresh(n.attr("id"))
        }
        var n = a;
        t.snap = "li",
            t.hScroll = !1;
        var i = 2 * n.css("fontSize").replace("px", "");
        return n.html("<ul></ul>"),
            e(),
            n.data("setData", e),
            n.data("setItem",
                function(t) {
                    var a = n.find("li").eq(t),
                        e = a.text(),
                        i = a.attr("val") || e;
                    if (1 * n.attr("i") !== t || !i || n.attr("val") != i) {
                        n.attr("i", t).attr("val", i).attr("txt", e);
                        var o = n.data("onchange");
                        "function" == typeof o && o(t, i)
                    }
                }),
            t.onScrollEnd = function() {
                var t = 2 + -1 * this.y / i;
                t % 1 > 0 || n.data("setItem")(t)
            },
            t
    },
    getDockOpt: function(t, a) {
        var e, n, i, o, r = a.offset().top,
            s = [],
            l = null,
            c = null,
            d = $('<div class="dock" style="position:absolute;left:0;width:100%;z-index:10;" />').insertBefore(a).css("top", r),
            u = $('<div class="rel_index y_center ta-c" style="right:0;z-index:100;" />').insertBefore(a);
        return t.onRefresh = function() {
            var t = this;
            s = [],
                n = [],
                e = a.find(".header,header").each(function() {
                    s.push($(this).offset().top - r - t.y);
                    var a = $(this).attr("rel");
                    a && 1 == a.length && n.push(a)
                });
            var i = "",
                o = [];
            n.length > 1 && n.length == s.length && (i = "<p>" + n.join("</p><p>") + "</p>"),
                u.html(i).data("onTouch",
                    function(a, n) {
                        if (n.preventDefault(), n.pageY) {
                            var i = $.getIndexInArr(o, n.pageY);
                            t.scrollToElement(e.eq(i)[0], 0)
                        }
                    }).find("p").each(function() {
                    o.push($(this).offset().top)
                })
        },
            t.onMove = function(t) {
                var a = Math.abs(t.y);
                if (l = null, t.y < 0 && (l = $.getIndexInArr(s, a)), null === l) return c = null,
                    void d.html("");
                if (l != c) {
                    c = l;
                    var n = e.eq(l);
                    o = n.get(0).offsetHeight,
                        i = n.clone(),
                    "rgba(0, 0, 0, 0)" == n.css("backgroundColor") && i.css("backgroundColor", $curPage.css("backgroundColor")),
                        d.html(i)
                }
                var r = s[l + 1] - a,
                    u = 0;
                o > r && (u = -(o - r)),
                    i.transform("translateY(" + u + "px)")
            },
            t
    },
    getPullOpt: function(opt, $wrap) {
        var fn = $wrap.attr("pull");
        return opt.onMove = function(e) {
            var next = $wrap.attr("next");
            if (!$wrap.find(".loadcon").length && void 0 != next && e.y <= e.maxScrollY) {
                var loadcon = "加载中"; - 1 == next && (loadcon = "没有更多了"),
                    console.info(loadcon),
                    $wrap.children().append('<div class="nocon loadcon">' + loadcon + "</div>"),
                next > 0 && eval(fn + "(" + next + ")"),
                    refreshPage()
            }
        },
            opt
    },
    initScroll: function() {
        var t = this,
            a = function(t) {
                for (var a = t.target; 1 != a.nodeType;) a = a.parentNode;
                "SELECT" != a.tagName && "INPUT" != a.tagName && "TEXTAREA" != a.tagName && t.preventDefault()
            };
        this.outWrap.find(".wrap[id]").each(function() {
            var e = $(this);
            if (!e.parents(".m_tpl").length) {
                var n = e.attr("id"),
                    i = {
                        onBeforeScrollStart: a,
                        hScrollbar: !1,
                        vScrollbar: !1
                    },
                    o = e.parent().hasClass("picker");
                o ? i = t.getPickerOpt(i, e) : e.hasClass("dock") ? i = t.getDockOpt(i, e) : e.attr("pull") && (i = t.getPullOpt(i, e));
                var r = new iScroll(n, i);
                scroll_map[n] = r,
                    setTimeout(function() {},
                        300)
            }
        })
    },
    refresh: function(t, a, e) {
        var e = e || 200,
            n = scroll_map[t] || scroll_map["wrap-" + t];
        return n ? (n.refresh(), "object" == typeof a ? n.scrollToElement(a.get(0), e) : a && ($("#" + t).scrollTop(0), n.scrollTo(0, 0, e)), n) : void 0
    },
    watchInput: function() {
        function t() {
            $body.addClass("wx_focus"),
                a = setTimeout(function() {
                        $body.removeClass("wx_focus")
                    },
                    isAndroid ? 3500 : 3e3)
        }
        if (isWeixin) {
            var a, e, n, i = $('<div style="position: fixed;bottom: 0;" />').appendTo($body),
                o = i.offset().top;
            this.outWrap.find("input").focus(function() {
                if (! ("file" == this.type || isAndroid && $body.hasClass("wx_focus"))) if (a && clearTimeout(a), isIOS) t();
                else {
                    e = i.offset().top,
                    n && clearInterval(n);
                    var r = 0;
                    n = setInterval(function() {
                            r++>200 && clearInterval(n);
                            var a = i.offset().top;
                            o > a && (a != e && t(), clearInterval(n)),
                                e = a
                        },
                        20)
                }
            }).blur(function() {
                n && clearInterval(n)
            })
        }
        this.outWrap.find("input").siblings(".icon-close").each(function() {
            var t = $(this).addClass("y_center clear_input"),
                a = t.siblings("input");
            a.on("input",
                function() {
                    t.showClass("show", "" != this.value)
                }),
                t.click(function() {
                    a.val("").trigger("input").focus()
                })
        }),
            this.outWrap.find("[name]").each(function() {
                var t = $(this); (t.val() || t.is("[val]")) && (t.attr("defval", t.val() || t.attr("val")), t.is("span.right") && t.attr("deftxt", t.text())),
                t.is("input") && this.addEventListener("touchmove",
                    function(t) {
                        t.preventDefault()
                    },
                    !1)
            }).filter("input[auto]").each(function() {
                var t = $(this);
                t.attr("placeholder") || t.attr("placeholder", "请输入" + t.prev("label").text())
            })
    },
    init: function(t) {
        this.outWrap = t || $body,
            this.initTabbar(),
            this.initWrap(),
            this.initScroll(),
            this.watchInput(),
            this.outWrap.find(".top .back").html('<i class="icon"></i>').click(function() {
                backPage()
            }),
            this.outWrap.find(".top .menu").html('<i class="icon icon-more-horiz"></i>'),
            this.outWrap.find("i.icon").each(function() {
                var t = $(this).text();
                t && $(this).addClass("icon-" + t).text("")
            }),
            $(".checkbtn").each(function() {
                var t = $(this),
                    a = t.attr("fn"),
                    e = t.attr("save");
                e && (e = "setting_" + e, window[e] = 0),
                    t.html('<input type="checkbox" /><label></label>'),
                t.is("[disabled]") && t.children("input").attr("disabled", "disabled");
                var n = t.find("input").change(function() {
                    var n = this.checked ? 1 : 0;
                    if (t.attr("val", n), e && (localStorage.setItem(e, n), window[e] = n), a) {
                        var i = window[a];
                        "function" == typeof i && i.call(t.get(), n)
                    }
                });
                e && localStorage.hasOwnProperty(e) && t.attr("val", localStorage.getItem(e));
                var i = t.attr("val");
                1 == i && n.click()
            }),
            this.outWrap.find(".top h1").click(function() {
                $(this).parents(".page").find(".wrap").each(function() {
                    pageScroll.refresh(this.id, !0)
                })
            }),
            this.outWrap.find(".checkbar span").click(function() {
                var t = $(this);
                t.addClass("check").siblings().removeClass("check"),
                    t.parent().nextAll(".seg_con").removeClass("active").eq(t.index()).addClass("active")
            }).eq(0).click(),
        "undefined" != typeof Prism && (this.outWrap.find("pre code.js").addClass("language-js"), this.outWrap.find("pre code").each(function() {
            Prism.highlightElement(this)
        }), this.outWrap.find("pre[src]").each(function() {
            var t = $(this),
                a = t.attr("src"),
                e = a.match(/\.(\w+)$/);
            e && $.getSrc(a,
                function(a) {
                    var n = $('<code class="language-' + e[1] + '"/>').text(a + "\n\n");
                    t.append(n),
                        Prism.highlightElement(n.get(0)),
                        pageScroll.refresh(t.parents(".wrap").attr("id"))
                })
        }))
    }
};
pageScroll.init(),
    setTimeout(function() {
            initWH(!0),
                pageScroll.refresh("index")
        },
        800);
var $maskAction = $('<div class="topmask popup clickoff" id="mask-action"><div class="outer"><div class="con"><ul class="action"></ul></div></div></div>').appendTo($body),
    $maskInfo = $('<div class="topmask" id="mask-info"><div class="pan pos_center"><div class="con"><h2 class="title">提示</h2><div class="info"></div><input type="text" /><div class="subinfo"></div><p class="btn_wrap bdt"><span class="cancel bdr">取消</span><span class="ok">确认</span></p></div></div></div>').appendTo($body),
    $loading = $('<div class="topmask" id="loading"><div class="pos_center"><div class="inner"><div class="head"><span class="icon"></span><div class="loadspin"><div class="loading-style-5">' + $.multiStr("<span></span>", 8) + '</div></div></div><div class="msg"></div></div></div></div>').appendTo($body);
$(".topmask.clickoff").click(function(t) {
    var a = $(t.target);
    a.hasClass("topmask") && closeMask()
}),
    $(".topmask .close").click(function() {
        var t = $(this).parents(".topmask");
        closeMask(t)
    }),
    +
        function() {
            function t() {
                a && clearTimeout(a),
                e && clearTimeout(e)
            }
            var a, e, n, i, o = 1;
            window.showLoad = function(e, r, s) {
                t();
                var e = e || "加载中",
                    r = r || 15e3;
                r > 10 && 100 > r && (r *= 1e3);
                var s = s || {};
                s.inPage ? $loading.appendTo(".page.show") : $loading.appendTo($body),
                    i = 220,
                isNaN(s.minShowTime) || (i = s.minShowTime);
                var l = ++o;
                n = 1 * new Date,
                    $loading.removeAttr("flag").attr("num", l).removeClass("done").addClass("show"),
                    $loading.find(".msg").text(e),
                    a = setTimeout(function() {
                            l == o && (stopLoad("加载超时", 2, 2e3), s.onTimeout && s.onTimeout())
                        },
                        r)
            },
                window.stopLoad = function(a, n, i) {
                    if (t(), "undefined" == typeof a) return void $loading.removeClass("show");
                    $loading.addClass("show");
                    var i = i || 1600,
                        n = n || 0,
                        o = ["done", "info-outline", "highlight-remove"],
                        r = ""; ! isNaN(n) && n < o.length && (n = o[n]),
                        r = "icon icon-" + n,
                        $loading.attr("flag", n).addClass("done").find(".msg").html(a),
                        $loading.find(".head .icon").attr("class", r),
                        e = setTimeout(function() {
                                $loading.removeClass("show")
                            },
                            i)
                }
        } (),
    +
        function() {
            function t(t, a) {
                if ($(a.target).is("input")) return ! 1;
                var e = !1;
                return s.each(function() {
                    var n = $(this).data("onTouch");
                    n && (e = n(t, a) || e)
                }),
                    e
            }
            function a() {
                var t = s.filter(g);
                s.addClass("hover"),
                t.length && $.getSetting("btn_wave", !0) && n(o, r, t)
            }
            function e() {
                i(),
                    s.removeClass("hover")
            }
            function n(t, a, e) {
                return
            }
            function i() {
                p && (p.addClass("ripple-wave-fill").transform(rippleTransform.replace("scale(1)", "scale(1.01)")).transitionEnd(function() {
                    var t = $(this).addClass("ripple-wave-out").transform(rippleTransform.replace("scale(1)", "scale(1.01)"));
                    setTimeout(function() {
                            t.transitionEnd(function() {
                                $(this).remove()
                            })
                        },
                        0)
                }), p = f = void 0)
            }
            var o, r, s, l, c, d, u, p, f, h = ($("html"), 8),
                v = 60,
                g = ".set li.more,button,.btn span,li.enter_page,.tabbar li,.top span";
            window.addRippleSelector && (g += "," + addRippleSelector),
                $.addRule(g, "position:relative;overflow:hidden;");
            var m = 0;
            $body.on(touchEv.start,
                function(e) {
                    s = $(e.target),
                        o = e.pageX = e.pageX || e.originalEvent.touches[0].pageX,
                        r = e.pageY = e.pageY || e.originalEvent.touches[0].pageY,
                        c = 0,
                        d = 0,
                        l = e.timeStamp,
                        u = !1,
                        s = s.add(s.parents()),
                        t("Start", e),
                        setTimeout(function() {
                                u || a()
                            },
                            v)
                }),
                $body.on(touchEv.move,
                    function(a) {
                        if (s) {
                            a.pageX = a.pageX || a.originalEvent.touches[0].pageX,
                                a.pageY = a.pageY || a.originalEvent.touches[0].pageY,
                                a.dx = c = a.pageX - o,
                                a.dy = d = a.pageY - r,
                            !m && c * c + d * d > 25 && (m = Math.abs(c) > Math.abs(d) ? "x": "y"),
                                a.moveFlag = m,
                            u || (u = Math.abs(c) > h || Math.abs(d) > h, u && e()),
                                a.endTouch = u;
                            t("Move", a)
                        }
                    }),
                $body.on(touchEv.end,
                    function(n) {
                        if (s) {
                            var i = 0;
                            u = !0,
                            n.timeStamp - l < v && !u && (a(), i = v),
                                setTimeout(function() {
                                        e(),
                                        u && (s = null)
                                    },
                                    i),
                                n.dx = c,
                                n.dy = d,
                                n.dure = n.timeStamp - l,
                                n.vx = c / n.dure,
                                n.vy = d / n.dure,
                                m = 0;
                            var o = t("End", n),
                                r = !addSwipe || $(".page.show").is(".down,.exit,.lazy");
                            r && c > 60 && Math.abs(d) < 30 && !s.filter(".slides").length && backPage(),
                            o && n.preventDefault()
                        }
                    })
        } ();
var Main = {
        api: "",
        title: document.title,
        location_search: location.search,
        inSae: location.host.match(/([^.]*)\.(applinzi|sinaapp)\.com/),
        getLinkPre: function() {
            return location.origin + location.pathname.replace(/[^\/]*$/, "")
        },
        refresh: !1,
        getStorkey: function(t, a) {
            var e = [t];
            for (var n in a) e.push(n + a[n]);
            return e.join("_")
        },
        buildUrl: function(t, a) {
            if ("object" == typeof a && (a = $.param(a)), !a) return t;
            var e = /\?/.test(t) ? "&": "?";
            return /\?$/.test(t) && (e = ""),
            t + e + a
        },
        addInnerLoad: function(t, a) {
            function e(t) {
                var t = t || 100;
                clearInterval(o),
                    n.css("width", t + "%").transitionEnd(function() {
                        n.addClass("over").transitionEnd = function() {
                            n.remove()
                        }
                    })
            }
            t.find(".load_inner").remove();
            var n = $('<div class="load_inner"></div>');
            t.append(n);
            var i = 0,
                a = a || 5,
                o = setInterval(function() {
                        i += i > 70 ? Math.random() : Math.random() * a + 5,
                        i >= 90 && (i = 90, clearInterval(o)),
                            n.css("width", i + "%")
                    },
                    100);
            return n.done = e,
                n
        },
        invoke_apis: [],
        api_pre: "",
        ajax_code_err: !0,
        ajax: function(t, a, e, n) {
            function i(t, a) {
                e(t.data || t, a)
            }
            var o, r = (new Date).getTime(),
                a = a || {},
                n = n || {},
                s = n.update || Main.refresh,
                l = n.stor_key || Main.getStorkey(n.stor_pre || t, n.stor_param || a); - 1 == this.invoke_apis.indexOf(t) && this.invoke_apis.push(t),
                t.match(/^http/) ? o = t: (o = this.api_pre + t, o = Main.buildUrl(o, "_t=" + r));
            var c = n.type || "get",
                d = "";
            "number" == typeof n ? (d = n, n = {}) : "object" == typeof n && (d = n.timeout);
            var u;
            if (d > 0) {
                var p = JSON.parse(localStorage.getItem(l) || "null");
                if (p && !s) {
                    var f = 1e3 * d - (r - p.time);
                    if (f > 0) return console.log(l + " ---timeout after--- " + Math.ceil(f / 1e3) + "s"),
                        void i(p);
                    n.noCache ? console.log(l, "不使用过期缓存") : i(p, !0)
                } else p && p.data && (a.pre_svtime = p.svtime, u = p.data);
                Main.refresh = !1
            }
            var h = !1;
            n.load ? ("object" != typeof n.load && (n.load = {}), showLoad(n.load.txt, n.load.timeout, {
                onTimeout: function() {
                    h = !0;
                    var t = n.load.onTimeout;
                    t && t()
                }
            })) : n.addLoadTo && (n.loader = Main.addInnerLoad(n.addLoadTo), n.minWait = Math.max(n.minWait || 0, 800));
            var v = n.minWait || n.min_wait || 0;
            n.jsonp && o.match(/(u51|51zhangdan)\.com\//) && (a.user_id = Param.user_id, a.userId = Param.user_id, a.token = Param.token);
            var g = {
                url: o,
                data: a,
                type: c,
                contentType: n.contentType || "application/json;charset=UTF-8",
                success: function(a) {
                    if (a || (a = {}), ("object" != typeof a || a.concat) && (a = {
                            data: a
                        }), Main.ajax_code_err && n.jsonp && a.code > 0 || a.err) return g.error(a);
                    var e = 1 * new Date;
                    d ? (a.pre_svtime && u && Main.updateAjaxData && (a.data = Main.updateAjaxData(u, a.data), console.log("updateAjaxData", a)), n.stor_pre && Main.limitLocalStorage(n.stor_pre, n.stor_max), console.log(l + " ---get json at---" + e), a.time = e, localStorage.setItem(l, JSON.stringify(a))) : v = v || 600,
                    "undefined" == typeof App && Main.invoke_apis > 6 && (v = 3e3 + 3e3 * Math.random());
                    var o = v - (e - r);
                    0 > o && (o = 0),
                        setTimeout(function() {
                                return h ? console.log(t, "超时返回", a) : (n.load && stopLoad(), i(a), void(n.loader && n.loader.done()))
                            },
                            o)
                },
                error: function(a) {
                    stopLoad(),
                    n.loader && n.loader.done(),
                        localStorage.removeItem(l);
                    var e = a.responseJSON || {
                            message: "网络访问出错"
                        };
                    if (e.errors ? e = e.errors[0] : (a.code || a.err) && (e = a), n.onError) {
                        var i = n.onError(e);
                        if (!i) return
                    }
                    404 == a.status ? console.error(t, a.statusText) : showInfo(e.message || e.msg || e.error || e.status + "错误")
                },
                headers: n.headers || {}
            };
            if (o.match(/renpinloan/) && (g.headers = {
                    "X-USERID": Param.user_id || "no_userid",
                    "X-AUTHORIZATION": Param.token || "no_token",
                    "X-BIG-APP-ID": Param.big_app_id
                }), n.jsonp) g.dataType = "jsonp",
                g.jsonp = "callback",
                g.jsonpCallback = "cb" + r;
            else {
                var m = Main.dispatchUrl && !n.noDispatch; (Main.needAgent || m) && ("get" == c && (o = Main.buildUrl(o, a)), g.headers["X-VISIT-URL"] = m ? Main.dispatchUrl: o, Param.tp_userid && (g.headers["X-TP-USERID"] = Param.tp_userid, g.headers["X-APPFROM"] = Param.appfrom), m && (g.headers["X-AGENT-URL"] = o), g.url = (Main.needAgent ? Main.agent_pre: Main.dispatchUrl) + "?_p=" + c + "_" + t.replace(/(^http[^\\]*\/\/)|(\?.*$)/g, "").replace(/\/|\\/g, "-"))
            }
            $.ajax(g)
        },
        needAgent: !1,
        isLocalServer: !!location.host.match(/^(192|127)\.|localhost/),
        agent_pre: "a.php",
        post: function(t, a, e, n) {
            var n = n || {};
            n.type = "post",
                this.ajax(t, a, e, n)
        },
        jsonp: function(t, a, e, n) {
            if ("number" == typeof n) n = {
                timeout: n
            };
            else var n = n || {};
            n.jsonp = !0,
            t.match(/^\/\//) && (t = location.protocol + t),
            t.match(/^http/) || (t = Main.api + t),
                this.ajax(t, a, e, n)
        },
        getParam: function(t) {
            var t = "object" == typeof t ? t: {};
            return t
        },
        concat: function(t, a) {
            var e = {};
            for (var n in arguments) {
                var i = arguments[n];
                if ("object" == typeof i) for (var o in i) e[o] = i[o]
            }
            return e
        },
        limitLocalStorage: function(t, a) {
            if (!t) return console.error("no regStr");
            var a = a || 10,
                e = 1,
                n = "clear_" + t,
                i = localStorage.getItem(n),
                o = new RegExp(t);
            for (var r in localStorage) if (r.match(o)) if (i) localStorage.removeItem(r),
                console.log("clear", r);
            else if (++e >= a) {
                localStorage.setItem(n, 1),
                    console.info("下次清除" + t);
                break
            }
            i && (localStorage.removeItem(n), localStorage.removeItem(r), console.info("已清除" + t))
        },
        getChinaArea: function(t, a) {
            var e = "//www.51zhangdan.com/service/sys/get_province.ashx",
                n = {};
            t && (e = "//www.51zhangdan.com/service/sys/get_area.ashx", n = {
                city_code: t
            }),
                Main.jsonp(e, n,
                    function(e) {
                        if (t) {
                            var n = {};
                            e.Areas.forEach(function(t) {
                                n[t.code] = t.name
                            }),
                            a && a(n)
                        } else {
                            var n = {};
                            e.Adr.forEach(function(t) {
                                var a = {};
                                t.City.forEach(function(t) {
                                    a[t.code] = t.name
                                }),
                                    n[t.code] = {
                                        name: t.name,
                                        citys: a
                                    }
                            }),
                            a && a(n)
                        }
                    },
                    {
                        stor_pre: t ? "china_area": "china_all",
                        timeout: 2592e3,
                        load: !0
                    },
                    !0)
        },
        showChooseArea: function(t, a) {
            Main.getChinaArea(0,
                function(e) {
                    var n = [""];
                    for (var i in e) n.push(i + "-" + e[i].name);
                    showChoose("请选择省市区", [n.join("、"),
                        function(t, a) {
                            if (a) {
                                var n = e[a].citys,
                                    i = [];
                                for (var t in n) i.push(t + "-" + n[t]);
                                setTimeout(function() {
                                        showChoose("", {
                                            1 : i.join("、")
                                        })
                                    },
                                    10)
                            }
                        },
                        function(t, a) {
                            a && (console.log(a), Main.getChinaArea(a,
                                function(t) {
                                    var a = [];
                                    for (var e in t) a.push(e + "-" + t[e]);
                                    showChoose("", {
                                        2 : a.join("、")
                                    })
                                }))
                        }], t, a)
                })
        },
        getRelTime: function(t, a) {
            function e() {
                if (!t) return ""; (t + "").length < 11 && (t *= 1e3);
                var e = new Date(t);
                if (10 == a) return [e.getFullYear(), e.getMonth() + 1, e.getDate()].join("-");
                var n = new Date,
                    i = "",
                    o = e.getMonth() + 1 + "月" + e.getDate() + "日 ",
                    r = e.getHours() + ":" + e.getMinutes();
                if (n.getTime() - t < 864e5) {
                    if (n.getDate() == e.getDate()) {
                        if (1 == a) return r;
                        i = "今天 "
                    } else i = "昨天 ";
                    if (2 == a) return i ? i: o + r
                }
                return e.getFullYear() != n.getFullYear() && (o = e.getFullYear() + "年" + o),
                    4 == a ? o: 3 == a ? "今天 " == i ? r: o: o + r
            }
            return e().replace(/\b(\d)\b/g, "0$1")
        },
        compareVersion: function(t, a) {
            if (!t) return - 2;
            if (!a) return 1;
            var e = t.split("."),
                n = a.split(".");
            console.log(e, n);
            for (var i in e) if (e[i] != n[i]) return 1 * e[i] > 1 * n[i] ? 1 : -1;
            return 0
        },
        genMaskToken: function(t) {
            for (var a = "",
                     e = 0; t > e; e++) a += "*";
            return a
        },
        setLiWarn: function(t, a) {
            a && toast(a),
            "string" == typeof t && (t = $(t)),
            t.is("li") || (t = t.parents("li")),
                t.addClass("warn"),
                setTimeout(function() {
                        t.removeClass("warn")
                    },
                    1e3)
        },
        checkRegMap: {
            mobile: /^1\d{10}$/,
            email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
            vcode: /^\d{4,6}$/,
            zwname: /^[\u4e00-\u9fa5]{2,}$/,
            bankcard: /^\d{12,20}$/,
            idcard: /^\d{14,17}\S$/
        },
        checkInput: function(t, a) {
            var e = !0,
                n = {},
                i = "",
                o = 0,
                r = !1;
            "string" == typeof t && (t = $("#" + t).find("[name]")),
                t.each(function() {
                    if (e) {
                        var t = $(this);
                        if (!t.is(".json") && !t.closest(".hide,li[freq]").length) {
                            var s = t.attr("name"),
                                l = t.attr("json");
                            l && (l = n[l] = n[l] || {});
                            var c = (t.attr("val") || t.val() || "").trim(),
                                d = t.attr("preval");
                            if (d != c) l && (r = !0),
                                o++;
                            else if (a && !l) return;
                            var u, p = t.attr("title") || t.prev("label").text(),
                                f = t.attr("fn"),
                                h = t.attr("p");
                            if (h) u = new RegExp(h);
                            else if (t.is("[req]")) {
                                var v = t.attr("req") || "";
                                if ("idcard" != v || f || (f = "validIdcard"), u = Main.checkRegMap[v], !u) {
                                    var g = v > 0 ? v: 1;
                                    u = new RegExp("\\S{" + g + ",}")
                                }
                            }
                            if (u && p && !l) {
                                if (c.match(u)) f && "function" == typeof Main[f] && (i = Main[f](c, t));
                                else {
                                    console.info(p, u);
                                    var m = "请正确填写";
                                    "" === c && (m = "请输入"),
                                    (t.is(".radio") || t.parents(".more").not(".edit").length) && (m = "请选择"),
                                        i = m + p,
                                    0 == c.length && t.is("[allow_null]") && (i = "")
                                }
                                if (i) return e = !1,
                                    refreshPage(null, t.parent()),
                                    void Main.setLiWarn(t.parent())
                            }
                            e && (l ? l[s] = c: n[s] = c)
                        }
                    }
                }),
            a && 0 == o && (i = "无修改");
            var s = {
                ok: e,
                form: n,
                msg: i,
                change: o
            };
            return i && (s.toastMsg = function() {
                toast(i)
            }),
                s
        },
        validIdcard: function(t) {
            var a = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],
                e = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2];
            if (t.length < 15) return "身份证号位数不正确";
            var n = 0;
            for (var i in a) {
                var o = parseInt(t[i]),
                    r = a[i];
                n += o * r
            }
            var s = n % 11,
                l = t[t.length - 1];
            return e[s] == l ? "": "身份证号填写有误"
        },
        getImgPath: function(t) {
            if (!t) return "img/avatar.jpg";
            var a = "upload/" + t;
            return Main.inSae && (a = "http://" + Main.inSae[1] + "-upload.stor." + Main.inSae[2] + ".com/" + t),
                a
        },
        setData: function(t, a, e) {
            e && this.resetForm(t),
                $("#" + t).find("[row],[name]").each(function() {
                    var t = $(this),
                        e = t.attr("row") || t.attr("name"),
                        n = (t.attr("alt") || "", t.attr("type"));
                    if (e in a) {
                        var i = a[e];
                        if ("object" == typeof i) return;
                        if (t.attr("preval", i), t.is("input")) t.val(i);
                        else if (t.is("[name]")) {
                            var o = i;
                            if ("image" == n) {
                                if (i) {
                                    var r = Main.getImgPath(i),
                                        s = "onclick=\"showImagePage('" + r + "')\"";
                                    t.parent("li").hasClass("more") && (s = ""),
                                        o = '<img src="' + r + '" ' + s + " />"
                                }
                            } else if ("checkbox" == n) o = i > 0 ? "是": "否";
                            else if (t.is(".checkbtn")) t.children("input").get(0).checked = 1 == i;
                            else if (t.is(".check_list")) t.children('li[val="' + i + '"]').click();
                            else if (t.parent().is(".more[data]")) {
                                var l = t.parent().attr("data").match(new RegExp(i + "-([^、]+)"));
                                l && (o = l[1])
                            }
                            t.attr("val", i).filter("span.right").not("[unset]").html(o)
                        } else t.is("img") ? t.attr("src", i) : t.html(i)
                    }
                })
        },
        showPageData: function(t, a, e) {
            this.setData("page-" + t, a, e),
                showPage(t)
        },
        resetForm: function(t) {
            var a = {};
            $("#" + t).find("[name]").each(function() {
                var t = $(this),
                    e = t.attr("defval") || "";
                a[t.attr("name")] = e
            }),
                Main.setData(t, a)
        },
        getTpl: function(t, a) {
            var e = t instanceof $ ? t: $(t),
                n = e.data("tpl");
            if (n) return n;
            var i = e.find(".m_tpl");
            if (!i.length) return console.info("找不到元素");
            var n = i.eq(a || 0).detach().removeClass("m_tpl")[0].outerHTML.replace(/s_rc/g, "src");
            return e.data("tpl", n),
                n
        },
        inflate: function(t, a) {
            if (!a) return toast("inflate参数不足");
            for (var e in a) {
                var n = RegExp("{" + e + "}", "g"),
                    i = a[e];
                null != i && void 0 != i || (i = ""),
                t.match(n) && (t = t.replace(n, i))
            }
            return t
        },
        inflateRows: function(t, a) {
            var e = "";
            for (var n in a) {
                a[n];
                e += this.inflate(t, a[n])
            }
            return e
        },
        genRandomStr: function(t, a) {
            var e = "abcdefghijklmnopqrstuvwxyz0123456789_";
            a = a || e.length;
            var n, i = [];
            for (n = 0; t > n; n++) i.push(e.substr(Math.floor(Math.random() * a), 1));
            return i.join("")
        },
        cutStr: function(t, a) {
            var a = a || 20;
            return t.length > a ? t.substr(0, a) + "...": t
        },
        decountSendSec: function(t, a) {
            if (t.hasClass("disabled")) return console.info("验证码重复发送了");
            a > 0 && (a = {
                sec: a
            });
            var a = a || {},
                e = a.sec || 60,
                n = a.sendedTxt || "验证码已发送",
                i = a.startTxt || "发送验证码";
            toastSuc(n),
                t.addClass("disabled").text(n).prev("input[req=vcode]").val("");
            var o = setInterval(function() {
                    return e--,
                        0 >= e ? (clearInterval(o), void t.text(i).removeClass("disabled")) : void t.text("重发(" + e + "s)")
                },
                1e3)
        }
    },
    pageTpl = '<div class="page {pageClass}" id="page-{id}"><div class="top">{topLeft}<h1>{title}</h1> {topRight}</div><div class="subtop">{tabbar}</div><div class="wrap" {wrapAttr}><div class="con">{con}</div></div></div>',
    Tool = {
        makePage: function(t, a) {
            if (!t || !t.id) return console.info("page必须指定id");
            var e = $("#page-" + t.id);
            t.backTitle = t.backTitle ? 'title="' + t.backTitle + '"': "",
                t.pageClass = t.pageClass || "",
                t.topRight = t.topRight || "",
            t.topLeft || (t.topLeft = '<span class="back" ' + t.backTitle + "></span>"),
            t.topRight && -1 == t.topRight.indexOf('class="right') && (t.topRight.match(/icon/) ? t.topRight = '<span class="right">' + t.topRight + "</span>": t.topRight = '<span class="right" title="' + t.topRight + '"></span>');
            var n = t.tabs;
            if (t.tabbar = "", n) {
                t.tabbar = '<div class="tabbar"><ul><li>' + n.join("</li><li>") + "</li></ul></div>";
                var i = "";
                n.forEach(function(a, e) {
                    i += "<div>" + t["con" + e] + "</div>"
                }),
                    t.con = '<div class="tab-auto">' + i + "</div>"
            } else t.con = t.con || t.con0 || "";
            t.hasOwnProperty("wrapAttr") || (t.wrapAttr = 'id="wrap-' + t.id + '"');
            var o = Main.inflate(pageTpl, t),
                r = $(o);
            return e.length ? r = e.html(r.html()) : r.appendTo($main),
                pageScroll.init(r),
            a && showPage(t.id),
                r
        },
        makeAndShowPage: function(t) {
            this.makePage(t, !0)
        }
    };
window.addEventListener("load",
    function() {
        FastClick.attach(document.body)
    },
    !1),
    window.applicationCache.onupdateready = function() {
        $main.addClass("blur"),
            isWeixin && isAndroid ? showInfo("检测到更新，需退出后重进生效。") : showLoad("自动更新...");
        var t = 1500;
        closeAllPage(),
            setTimeout(function() {
                    location.reload()
                },
                t)
    },
isAndroid && ((Param.system_version || "").match(/^4\.[0-3]|2/) || navigator.userAgent.match(/android\s(4.[0-3]|2)/i)) && $body.addClass("android-low"),
    $body.click(function(t) {
        $main.addClass("noclick"),
            setTimeout(function() {
                    $main.removeClass("noclick")
                },
                300)
    }),
    $main.delegate(".enter_page", "click",
        function() {
            var t = $(this),
                a = t.attr("alert");
            if (a) return showInfo(a);
            var e = t.attr("rel");
            if (e) {
                var n = $.getUrlParam(e),
                    i = n.page_link;
                if (i && !t.parents(".unedit").length && !t.is(".disabled,.load")) {
                    var o = i.match(/pg:(.+)/);
                    if (o) return PG.exec(o[1], Main["pg_" + o[1]]);
                    if ($("#page-" + i).length) showPage(i, n);
                    else {
                        var r = t.attr("timeout") || 5e3;
                        showLoad("加载中", r),
                            $.get("./page/" + i + ".html", {},
                                function(t) {
                                    stopLoad(),
                                        $main.append(t);
                                    var a = $("#page-" + i);
                                    pageScroll.init(a),
                                        showPage(i)
                                })
                    }
                }
            }
        }),
    $("i.checkbox").click(function(t) {
        var a = $(t.target);
        if (!a.is("u")) {
            var e = $(this).attr("val") || 0;
            e = 1 - e,
                $(this).attr("val", e);
            var n, i = $(this).attr("rel");
            n = i ? $(i) : $(this).siblings("button"),
                n.showClass("disabled", 0 == e)
        }
    }),
    $(".radio").delegate("span", "click",
        function(t) {
            var a = $(this),
                e = a.parent().attr("fn");
            a.addClass("active").siblings(".active").removeClass("active");
            var n = a.attr("v") || a.text();
            a.parent().attr("val", n),
            Trade[e] && Trade[e](n, a, t)
        }),
    $(".check_list").delegate("li", "click",
        function() {
            var t = $(this),
                a = t.parent().attr("fn");
            t.is(".add") || (t.addClass("checked").siblings(".checked").removeClass("checked"), t.parent("[name]").attr("val", t.attr("val")), Trade[a] && Trade[a](t.find("label").text(), t))
        }),
    $(".set li label").click(function() {
        $(this).parents(".page").find("input:focus").blur()
    }),
    $(".collapse").delegate(".set li.more", "click",
        function() {
            if (!$(this).hasClass("enter_page")) {
                var t = $(this).parents(".set").toggleClass("expand");
                refreshPage(null, t.hasClass("expand") ? t: !1)
            }
        }),
    $(".picker .wrap").delegate("li[val]", "click",
        function() {
            var t = $(this).index() - 2;
            $(this).parents(".wrap").nextAll(".wrap").removeAttr("val").end().iscrollToIndex(t, !0)
        }),
    $main.delegate(".set li[data]", "click",
        function() {
            var t = $(this);
            if (!t.parents(".unedit").length) {
                var a = t.children(".right"),
                    e = {};
                e.current = a.attr("val");
                var n = t.attr("data"),
                    i = "请选择" + t.children("label").text(),
                    o = t.attr("onset");
                o && (o = window[o]);
                var e = {
                    defVal: a.attr("val"),
                    append: t.attr("append"),
                    unit: t.attr("unit"),
                    name: a.attr("name")
                };
                showChoose(i, n,
                    function(t, e) {
                        a.text(e).attr("val", t),
                        "function" == typeof o && o(t, e)
                    },
                    e)
            }
        }),
    $(".submit[fn]").click(function(t) {
        var a = $(this);
        if (a.hasClass("disabled")) return void(a.siblings(".checkbox").length && toast("请同意协议"));
        var e = a.attr("trade") || "Trade",
            n = a.attr("fn"),
            i = window[e][n];
        i ? i.call(this) : toast("未定义")
    }),
    $(".save_input input[name]").each(function(t, a) {
        var e = $(this),
            n = e.attr("s") || "in_" + Param.user_id + "_" + e.parents(".wrap").attr("id") + "_" + e.attr("name") + t,
            i = localStorage.getItem(n);
        i && e.val(i),
            e.on("input",
                function() {
                    i = $(this).val(),
                        localStorage.setItem(n, i)
                })
    }),
    $("li.more.choose_area").click(function() {
        var t = $(this).find(".right"),
            a = (t.attr("val") || "").split("-");
        Main.showChooseArea(function(a, e) {
                a[0] && (t.text(e.join(" ")), t.attr("val", e.join("-")))
            },
            {
                defVals: a
            })
    }),
    $(".alert[rel]").each(function() {
        var t = $(this),
            a = t.attr("rel"),
            e = "close_alert_" + a;
        localStorage.getItem(e) || (t.fadeIn(200,
            function() {
                pageScroll.initCurWrap()
            }), $('<span class="close y_center">×</span>').appendTo(t).click(function() {
            localStorage.setItem(e, 1),
                t.fadeOut(200,
                    function() {
                        pageScroll.initCurWrap()
                    })
        }))
    }),
    +
        function() {
            $main.find("ul.set li").on("click",
                function() {
                    $(this).children("input.right").focus()
                });
            var t = {
                    bankcard: /(\d{4})|(\d+)/g
                },
                a = {
                    bankcard: 20
                };
            $main.delegate("input", "input",
                function(e) {
                    var n = $(this);
                    if (n.is("[mode]")) {
                        var i = n.attr("mode"),
                            o = n.val().replace(/\s/g, ""),
                            r = a[i] || n.attr("maxlen");
                        r && (o = o.substring(0, 1 * r)),
                            n.attr("val", o);
                        var s = t[i] || new RegExp(i, "g"),
                            l = o.match(s);
                        l && n.val(l.join(" "))
                    } else if (n.is("[req]")) {
                        var c = n.attr("req"),
                            s = Main.checkRegMap[c];
                        if (s) {
                            var d = s.toString().replace(/^\/\^/, "(").replace(/\$\/$/, ")"),
                                l = this.value.match(new RegExp(d));
                            if (l && this.value != l[1]) {
                                if ("email" == c && this.value.match(/\.$/)) return;
                                this.value = l[1]
                            }
                        }
                        "idcard" == c && (this.value = this.value.replace(/\D$/, "X"))
                    }
                }),
                $main.delegate("ul.set input", "click blur",
                    function(t) {
                        var a = $(this),
                            e = a.parents(".wrap");
                        if (!e.find("ul.form").length && (a.parents(".con").showClass("pb-more", "click" == t.type), "click" == t.type)) {
                            var n = pageScroll.refresh(e.attr("id"));
                            if (isAndroid) {
                                var i = a.parent();
                                setTimeout(function() {
                                        n.scrollToElement(i.get(0)),
                                            setTimeout(function() {
                                                    a.focus()
                                                },
                                                300)
                                    },
                                    200)
                            }
                        }
                    })
        } (),
    $(".trify_bg").each(function() {
        "static" == $(this).css("position") && $(this).css("position", "relative");
        var t = Trianglify({
            width: this.offsetWidth,
            height: this.offsetHeight,
            cell_size: 50,
            variance: .8,
            seed: 14
        }).svg();
        $('<div class="pos_mask" style="z-index:-1" />').append(t).appendTo(this)
    }),
    $("ul.grid.bd li").addClass("bdb bdr"),
    $("ul.navbar li").touch(function() {
        var t = $(this),
            a = t.children("p").text();
        t.addClass("theme").siblings().removeClass("theme");
        var e = t.attr("rel");
        $("#" + e).removeClass("hide").siblings().addClass("hide"),
            t.parents(".page").find(".top h1").text(a).siblings("span[nav]").show().not("[nav=" + e + "]").hide()
    }),
    $(".calculator").each(function() {
        function t(t) {
            isNaN(t) && (t = 1),
                e > t ? t = e: t > n && (t = n, toast("最大不能超过" + n)),
                a.val(t).trigger("change")
        }
        var a = $(this).find("input"),
            e = a.attr("min") || 1,
            n = 1 * a.attr("max") || 100;
        a.on("input",
            function() {
                t($(this).val())
            }),
            $(this).children("b").click(function() {
                var e = parseInt(a.val());
                "-" == $(this).text() ? e--:e++,
                    t(e)
            })
    }),
    setTimeout(function() {
            $body.hasClass("show") || (Main.isLocalServer && showInfo("未调用首页显示"), console.error("===请调用showFirstPage==="), console.info("1、如有Param.page则打开以page命名的页面为首页；2、如有Param.showpage，则在当前首页基础上打开以showpage命名的页面。"))
        },
        1e3),
    showFirstPage(),
    Main.api = api_pre,
    Main.title = "curtain",
    Main.ajax_code_err = !1;
var $product = $("#index ul.product"),
    $ordlist = $("#ord_list"),
    prod_tpl = Main.getTpl($product),
    Trade = {
        products: {},
        cart: [],
        showBuyMask: function(t) {
            var a = Trade.products[t],
                e = "";
            a.style_list.forEach(function(t, a) {
                e += '<span v="' + t.id + '">' + t.productStyleInfo + "</span>"
            }),
                Main.setData("mask-buy", a),
                $("#mask-buy .calculator input").val(1),
                $("#buy_type").html(e).find("span:first").click(),
                showMask("buy")
        },
        onCalcAmount: function(t) {
            var a = Main.checkInput("mask-buy").form,
                e = a.buyCount * Trade.products[a.productCode].style_map[a.styleId].unitAmount;
            $("#buy_amount").text(e).attr("val", e)
        },
        addToCart: function() {
            var t = Main.checkInput("mask-buy").form;
            t.buyCount *= 1,
                t.amount *= 1;
            var a = !1;
            this.cart.forEach(function(e) {
                e.productCode == t.productCode && e.styleId == t.styleId && (a = !0, e.buyCount += t.buyCount, e.amount += t.amount)
            }),
            a || this.cart.push(t),
                this.showCart(),
                closeMask(),
                toastSuc("已添加到购物车")
        },
        delFromCart: function(t, a, e) {
            var n = $(e).parents("li");
            showInfo(n.find("p.pos_right").html(), {
                title: "是否确认删除",
                onOk: function() {
                    var e = [];
                    Trade.cart.forEach(function(n) {
                        t == n.productCode && a == n.styleId ? console.info("已删除") : e.push(n)
                    }),
                        Trade.cart = e,
                        n.fadeOut(200),
                        setTimeout(function() {
                                Trade.showCart()
                            },
                            200)
                }
            })
        },
        showCart: function(t) {
            t && (this.products = JSON.parse(localStorage.cart_products || null), this.products ? this.cart = JSON.parse(localStorage.cart || "[]") : this.products = {});
            var a = this.cart,
                e = 0,
                n = [],
                i = 0,
                o = {};
            a.forEach(function(t) {
                e += 1 * t.buyCount;
                var a = Trade.products[t.productCode];
                o[t.productCode] = a;
                var r = Main.concat(t, a);
                r.product_name = Main.cutStr(r.product_name, 25),
                    r.style_info = a.style_map[t.styleId].productStyleInfo,
                    i += 1 * t.amount,
                    n.push(r)
            }),
            t || (localStorage.cart = JSON.stringify(this.cart), localStorage.cart_products = JSON.stringify(o)),
                e = a.length,
                setTimeout(function() {
                        $("#shopcart .notice").attr("tipnum", e)
                    },
                    300);
            var r = $("#cart ul.product"),
                s = Main.getTpl(r);
            r.html(Main.inflateRows(s, n));
            var l = {
                amount: i,
                count: e
            };
            Main.setData("page-cart .btm", l),
                $("#page-cart .btm button").showClass("disabled", !e),
                $("#cart .nocon").showClass("hide", e > 0)
        },
        onSubmitOrder: function() {
            var lu = localStorage.userinfo;
            var mobile='',userName='',address='',postCode='';
            if(lu){
                var u = jQuery.parseJSON(lu);
                mobile = u['mobile'];
                userName = u['userName'];
                address = u['address'];
                postCode = u['postCode'];
            }

                var a = '<p class="gray">为了保证下单后能及时联系到您，首次下单需要填写您的手机号,姓名,详细地址,邮政编码和留言。</p>' +
                    '<div id="auth_input"><input title="手机号" type="tel" name="mobile" value="' + mobile+ '" placeholder="请输入手机号" req="mobile"/>' +
                    '<input title="姓名" name="userName" value="' + userName+ '" placeholder="请输入姓名" req />' +
                    '<input title="详细地址" name="address" value="' + address + '"  placeholder="请输入详细地址" req="address"/>' +
                    '<input title="邮政编码" type="tel" name="postCode" value="' + postCode + '" placeholder="请输入邮政编码" req="postCode"/>' +
                    '<input title="留言"  name="note" placeholder="请输入留言"/></div>';
                return void showInfo(a,
                    function() {
                        var t = Main.checkInput("auth_input");
                        if(t.msg){
                            t.toastMsg();
                        } else {
                            localStorage.userinfo = JSON.stringify(t.form);
                            lu = localStorage.userinfo;
                            var e = JSON.parse(lu);
                            e.products = Trade.cart,
                                Main.jsonp("order/add", {
                                        orderJson: JSON.stringify(e)
                                    },
                                    function(t) {
                                        return console.log(t),
                                            "success" != t.status ? (showInfo(t.message), void localStorage.removeItem("userinfo")) : (Trade.cart = [], Trade.showCart(), toastSuc("订单已提交"), $pageIndex.find(".top .right").addClass("notice").one("click",
                                                function() {
                                                    $(this).removeClass("notice")
                                                }), void closeAllPage(function() {
                                                Trade.getOrders(function() {
                                                    $("#ord_list ul:first").click()
                                                })
                                            }))
                                    },
                                    {
                                        load: !0
                                    })
                        }
                    })



        },
        orders: [],
        getOrders: function(t) {
            var a = JSON.parse(localStorage.userinfo || "null");
            if (a) {
                var e = 1;
                isNaN(t) || (e = t);
                var n = {
                    pageNumber: e,
                    pageSize: 8,
                    mobile: a.mobile
                };
                Main.refresh = !!t,
                    Main.jsonp("order/list.json", n,
                        function(a) {
                            var n = "";
                            a.list.forEach(function(t) {
                                var a = [],
                                    e = 0;
                                t.orderDetails.forEach(function(t) {
                                    t.product_name = Main.cutStr(t.name, 30) + '<sub class="d-ib">(' + t.productStyleInfo + " × " + t.productCount + ")</sub>",
                                        t.sell_price = t.unitAmount * t.productCount,
                                        t.main_img = img_pre + t.productImgList[0].url,
                                        t.productSort_name = t.productSortName,
                                        e += t.sell_price,
                                        a.push(t)
                                }),
                                    t.detail = Main.inflateRows(prod_tpl, a),
                                    t.date = new Date(t.createDate).format("yy-mm-dd HH:ii:ss");
                                var i = "red-color";
                                Trade.orders[t.id] = t,
                                "已付款" == t.statusValue && (i = "green"),
                                    n += '<ul class="product set" onclick="Trade.showOrdInfo(' + t.id + ')"><li><label for="">' + t.date + '</label><span class="right ' + i + '">' + t.statusValue + "</span></li>" + t.detail + "<li>共" + t.orderDetails.length + '样产品<span class="right">合计<span class="money red-color">' + t.amount + "</span></span></li></ul>"
                            }),
                                e > 1 ? $ordlist.append(n) : $ordlist.html(n).prev(".nocon").showClass("hide", a.list.length),
                                $ordlist.next(".loadcon").remove(),
                                $ordlist.parents(".wrap").attr("next", a.pager.next ? 1 * e + 1 : -1),
                            "function" == typeof t && t()
                        },
                        {
                            timeout: 300
                        })
            }
        },
        showOrdInfo: function(t) {
            var a = Trade.orders[t];
            $("#ordinfo .detail").html(a.detail),
                Main.showPageData("ordinfo", a)
        },
        getIndex: function(t) {
            var t = t || 1,
                a = {
                    pageNumber: t,
                    pageSize: 8,
                    productSortId: Param.productSortId || null
                },
                e = $pageIndex.find("input.search").val().trim();
            e && (a.productName = e),
                Main.jsonp("product/list.json", a,
                    function(a) {
                        var n = [];
                        a.pager.list.forEach(function(t) {
                            var a = {};
                            a.productCode = t.productCode,
                                a.product_name = Main.cutStr(t.name, 50),
                                a.description = t.description,
                                a.productSort_name = t.productSort.name,
                                a.main_img = img_pre + t.productImgList[0].url,
                                a.sell_price = t.productStyleList[0].unitAmount,
                                a.style_list = t.productStyleList,
                                a.style_map = {},
                                a.style_list.forEach(function(t) {
                                    a.style_map[t.id] = t,
                                        a.sell_price = Math.min(a.sell_price, t.unitAmount)
                                }),
                                n.push(a),
                                Trade.products[a.productCode] = a
                        });
                        var i = Main.inflateRows(prod_tpl, n);
                        e && !i && (i = '<div class="nocon">未搜到相关内容</div>'),
                            t > 1 ? $product.append(i) : (a.showRoom && $pageIndex.find(".top h1").text(a.showRoom.name), $product.html(i)),
                            $product.next(".loadcon").remove(),
                            $product.parents(".wrap").attr("next", a.pager.next ? 1 * t + 1 : -1),
                            refreshPage()
                    },
                    {
                        load: !0,
                        noCache: !0,
                        timeout: 300
                    })
        },
        init: function() {
            this.showCart(!0),
                this.getIndex(),
                this.getOrders()
        }
    };
$("#page-orders").length && Trade.init();