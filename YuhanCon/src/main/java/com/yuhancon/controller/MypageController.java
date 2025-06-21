package com.yuhancon.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuhancon.domain.Board;
import com.yuhancon.domain.Member;
import com.yuhancon.domain.RecentConcertView;
import com.yuhancon.domain.Reserve;
import com.yuhancon.repository.BoardRepository;
import com.yuhancon.repository.MemberRepository;
import com.yuhancon.repository.RecentConcertViewRepository;
import com.yuhancon.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberRepository memberRepository;	
	private final BoardRepository boardRepository;
	private final ReserveRepository reserveRepository;
	private final RecentConcertViewRepository recentConcertViewRepository;
	
	@GetMapping("/mypage")
	public String mypage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		//로그인한 사용자 정보
		Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow();
		
		//사용자 예약 정보
		List<Reserve> reserves = reserveRepository.findByMemberIdWithConcert(member.getId());

		//내가 쓴 글
		List<Board> myPosts = boardRepository.findByMember(member);
	    
		// 최근 본 공연
        List<RecentConcertView> recentConcerts = recentConcertViewRepository.findTop5ByMemberOrderByViewedAtDesc(member);
        
	   
	    model.addAttribute("member", member);
	    model.addAttribute("reserveList", reserves);
	    model.addAttribute("myPosts", myPosts);
	    model.addAttribute("recentConcerts", recentConcerts);
		return "mypage";
	}
	
	
	
}
