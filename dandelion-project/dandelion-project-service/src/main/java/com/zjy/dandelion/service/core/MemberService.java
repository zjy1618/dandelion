package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.Member;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 后台成员服务
 * @author zhuangjy
 *
 */
public interface MemberService {

	List<Member> findAll();

	boolean saveMember(Member member);
	
	boolean updateMember(Member member);
	
	Member findMemberById(Long id);

	Member findMemberByName(String name);

	/**
	 * 关闭账户
	 */
	boolean closeMember(Long id);

	/**
	 * 登录
	 * @param username
	 * @param password
     * @return
     */
	Member login(HttpSession session, Model model, String username, String password);
}
