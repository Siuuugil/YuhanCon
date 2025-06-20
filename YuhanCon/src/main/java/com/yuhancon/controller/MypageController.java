package com.yuhancon.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuhancon.domain.Board;
import com.yuhancon.domain.Member;
import com.yuhancon.repository.BoardRepository;
import com.yuhancon.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberRepository memberRepository;	
	private final BoardRepository boardRepository;
	
	@GetMapping("/mypage")
	public String mypage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		String email = userDetails.getUsername();
		Member member = memberRepository.findByEmail(email).orElseThrow();
		
		
		List<Board> myPosts = boardRepository.findByMember(member);
	    
	   
	    model.addAttribute("member", member);
	    model.addAttribute("myPosts", myPosts);
		return "mypage";
	}
	
}
