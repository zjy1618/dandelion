package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.Member;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.page.PagerHelper;
import com.zjy.dandelion.service.core.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhuangjy on 16/8/30.
 */
@Controller
@RequestMapping("management")
public class MemberController {

    @Resource
    private MemberService memberService;

    /**
     * 账户列表
     */
    @RequestMapping(value = "member", method = RequestMethod.GET)
    public String memberList(Pager<Member> pager, Model model) {
        List<Member> list = memberService.findAll();

        PagerHelper<Member> pagerHelper = new PagerHelper<Member>();
        pagerHelper.setPager(pager);
        pagerHelper.setBaseUrl("member");

        model.addAttribute("list", list);
        model.addAttribute("pagerHelper", pagerHelper);
        return "management/member";
    }

    /**
     * 账户添加页面跳转
     */
    @RequestMapping(value = "addMember", method = RequestMethod.GET)
    public String addMember() {
        return "management/addMember";
    }

    /**
     * 账户添加
     */
    @RequestMapping(value = "addMember", method = RequestMethod.POST)
    public String addMemberAction(Member member, RedirectAttributes redirectAttributes) {
        if (memberService.findMemberByName(member.getName()) != null){
            redirectAttributes.addFlashAttribute("msg", "账户名" + member.getName() + "已存在");
            return "redirect:member";
        }
        if (memberService.saveMember(member)) {
            redirectAttributes.addFlashAttribute("msg", "账户添加添加成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "账户添加添加失败");
        }
        return "redirect:member";
    }

    /**
     * 账户修改页面跳转
     */
    @RequestMapping(value = "updateMember/{id}", method = RequestMethod.GET)
    public String updateMember(@PathVariable Long id, Model model) {
        Member member = memberService.findMemberById(id);
        model.addAttribute("member", member);
        return "management/addMember";
    }

    /**
     * 账户修改
     */
    @RequestMapping(value = "updateMember", method = RequestMethod.POST)
    public String updateMemberAction(Member member, RedirectAttributes redirectAttributes) {
        if (memberService.updateMember(member)) {
            redirectAttributes.addFlashAttribute("msg", "账户修改成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "账户修改失败");
        }
        return "redirect:member";
    }

    /**
     * 账户删除,逻辑删除
     */
    @RequestMapping(value = "deleteMember/{id}", method = RequestMethod.GET)
    public String resourcesMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (memberService.closeMember(id)) {
            redirectAttributes.addFlashAttribute("msg", "账户删除成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "账户删除失败");
        }

        return "redirect:/management/member";
    }
}
