package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.MemberMapper;
import com.zjy.dandelion.entity.Member;
import com.zjy.dandelion.exception.BusinessExceptionCode;
import com.zjy.dandelion.service.core.MemberService;
import com.zjy.dandelion.utils.Encryption;
import com.zjy.dandelion.utils.Md5Util;
import com.zjy.dandelion.utils.ModelHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuangjy on 16/8/29.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public boolean saveMember(Member member) {
        member.setCreateDate(new Date());
        member.setModifyDate(new Date());
        member.setPassword(Md5Util.encrypt(member.getPassword()));
        return memberMapper.insert(member) > 0;
    }

    @Override
    public boolean updateMember(Member member) {
        member.setModifyDate(new Date());
        if (StringUtils.isNotEmpty(member.getPassword())){
            member.setPassword(Md5Util.encrypt(member.getPassword()));
        }else{
            member.setPassword(null);
        }
        return memberMapper.updateByPrimaryKey(member) > 0;
    }

    @Override
    public Member findMemberById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public Member findMemberByName(String name) {
        return memberMapper.selectByName(name);
    }

    @Override
    public boolean closeMember(Long id) {
        Member member = new Member();
        member.setId(id);
        member.setStatus(Member.Status.CLOSE);
        return memberMapper.updateByPrimaryKey(member) > 0;
    }

    @Override
    public Member login(HttpSession session, Model model, String username, String password) {
        Member member = this.findMemberByName(username);
        if (member == null) {
            ModelHelper.addValidateInfo(model,
                    BusinessExceptionCode.EXCEPTION_20002);
            return null;
        }

        if (member.getPassword() == null) {

            ModelHelper.addValidateInfo(model,
                    BusinessExceptionCode.EXCEPTION_20004);
            return null;
        }

        if (!member.getPassword().equals(Encryption.encrypt(password))) {

            ModelHelper.addValidateInfo(model,
                    BusinessExceptionCode.EXCEPTION_20001);
            return null;
        }

        return member;
    }


}
