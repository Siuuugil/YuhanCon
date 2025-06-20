package com.yuhancon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuhancon.domain.Board;
import com.yuhancon.domain.Member;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findByMember(Member member);

}
