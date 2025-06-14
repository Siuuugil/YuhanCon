package com.yuhancon.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhancon.domain.Member;
import com.yuhancon.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	 private final PasswordEncoder passwordEncoder; 
	
	@GetMapping("/login")
	public String loginPage() {
	    return "login"; // login.html
	}
	@GetMapping("/signup")
	public String signupView() {
		return "/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@RequestParam String email,
						 @RequestParam String name,
						 @RequestParam String password,
						 @RequestParam String phone) {
		
		if (memberRepository.findByEmail(email).isPresent()) {
			return "/signup";
		}  
		
		Member member = new Member();
		member.setEmail(email);
		member.setName(name);
		member.setPassword(passwordEncoder.encode(password));
		member.setPhone(phone);
		member.setRole("USER");
		
		memberRepository.save(member);
		return "/login";
		
		
	}
	

}
