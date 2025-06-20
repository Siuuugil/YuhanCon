package com.yuhancon.controller;

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
	
	@GetMapping("/login")
	public String loginPage() {
	    return "login"; // login.html
	}
	@GetMapping("/signup")
	public String signupView(Model model) {
		model.addAttribute("member", new Member());
		return "/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("member") Member member,
						 BindingResult bindingResult,
						 Model model
						 ) {
		
		if (bindingResult.hasErrors()) {
	        return "signup";
	    }

	    if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
	        model.addAttribute("emailError", "이미 등록된 이메일입니다.");
	        return "signup";
	    }

	    member.setPassword(passwordEncoder.encode(member.getPassword()));
	    member.setRole("USER");

	    memberRepository.save(member);
	    return "redirect:/login";
	}
}