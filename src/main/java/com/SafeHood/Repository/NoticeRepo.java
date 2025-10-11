package com.SafeHood.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SafeHood.Entities.Notice;

public interface NoticeRepo  extends JpaRepository<Notice, Integer> {

}
