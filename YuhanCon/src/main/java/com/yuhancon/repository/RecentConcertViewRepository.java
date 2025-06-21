package com.yuhancon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.yuhancon.domain.Concert;
import com.yuhancon.domain.Member;
import com.yuhancon.domain.RecentConcertView;

public interface RecentConcertViewRepository extends JpaRepository<RecentConcertView, Long> {
    List<RecentConcertView> findTop5ByMemberOrderByViewedAtDesc(Member member);
    void deleteByMemberAndConcert(Member member, Concert concert);
    void deleteByConcertId(@Param("concertId") Long concertId);
}
