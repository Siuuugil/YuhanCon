package com.yuhancon.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.yuhancon.domain.Member;
import com.yuhancon.repository.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	 private final PasswordEncoder passwordEncoder; 
	
	//로그인 view
	@GetMapping("/login")
	public String loginPage() {
	    return "login"; // login.html
	}
	
	//회원가입 view 
	@GetMapping("/signup")
	public String signupView(Model model) {
		model.addAttribute("member", new Member());
		return "/signup";
	}
	
	//회원가입 데이터 처리
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("member") Member member,
						 BindingResult bindingResult,
						 Model model
						 ) {
		
		System.out.println("에러 여부: " + bindingResult.hasErrors());
	    System.out.println("에러 목록: " + bindingResult.getAllErrors());
	    
		if (bindingResult.hasErrors()) {
	        return "signup";
	    }

	    if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
	        model.addAttribute("emailError", "이미 등록된 이메일입니다.");
	        return "signup";
	    }

	    
	    member.setRole("USER");

	    memberRepository.save(member);
	    
	    return "redirect:/login";
	}
	
	//회원수정 view
	@GetMapping("/editMember")
	public String editMemberForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		String email = userDetails.getUsername();
	    Member member = memberRepository.findByEmail(email).orElseThrow();
	    model.addAttribute("member", member);
		return "editMember";
	}
	
	
	//회원수정 데이터 처리
	@PostMapping("/editMember")
	public String editMember(@AuthenticationPrincipal UserDetails userDetails,
							 @Valid @ModelAttribute Member editmember,
							 BindingResult bindingResult,
							 Model model){
		
		if (bindingResult.hasErrors()) {
	        model.addAttribute("editmember", editmember); // 다시 입력한 내용 유지
	        return "editMember"; 
	    }
		
		Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow();
		
		member.setName(editmember.getName());
	    member.setPhone(editmember.getPhone());
	    member.setPassword(editmember.getPassword());
	    
		//member.setRole("USER"); 필요 없다잉
		memberRepository.save(member);
		
		return "redirect:/mypage";
	}
}