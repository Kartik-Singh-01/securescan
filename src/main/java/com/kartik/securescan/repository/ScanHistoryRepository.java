package com.kartik.securescan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kartik.securescan.entity.ScanHistory;

public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

}