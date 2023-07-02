package com.example.hra.Repository;

import com.example.hra.Entity.JobHistory;
import com.example.hra.Entity.JobHistoryId;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public interface JobHistoryRepository extends JpaRepository<JobHistory,Integer> {

    JobHistory findFirstByEmployee_EmployeeIdOrderByEndDateDesc(BigDecimal empId);

    Optional<Object> findById(JobHistoryId jobHistoryId);
}
