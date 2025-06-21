package com.yuhancon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuhancon.domain.Concert;
import com.yuhancon.repository.ConcertRepository;

@Controller
public class MainController {	
	@Autowired
	private ConcertRepository concertRepository;

	@GetMapping("/main")
    public String showMainPage(Model model) {
        List<Concert> concertList = concertRepository.findTop3ByOrderByDateAsc();
        model.addAttribute("concertList", concertList);
        return "main";
    }
}
