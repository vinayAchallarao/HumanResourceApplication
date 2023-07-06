package com.example.hra.service;

import com.example.hra.entity.*;
import com.example.hra.repository.DepartmentRepository;
import com.example.hra.repository.EmployeeRepository;
import com.example.hra.repository.JobHistoryRepository;
import com.example.hra.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
public class JobHistoryServiceImplement implements JobHistoryService {

    private JobHistoryRepository jobHistoryRepository;
    private EmployeeRepository employeeRepository;
    private JobRepository jobRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public void setJobHistoryRepository(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }
    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public String createJobHistoryEntry(BigDecimal employeeId,String jobId, Date startDate, BigDecimal departmentId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Department department = departmentRepository.findByDepartmentId(departmentId);

        if (employee == null  || department == null) {
            System.out.println("Employee, Job, or Department not found.");
            return "Employee, Job, or Department not found.";
        }

        JobHistoryId jobHistoryId = new JobHistoryId(employeeId,startDate);
        if (jobHistoryRepository.existsById(jobHistoryId)) {
            System.out.println("Job history entry already exists for the given employee and start date.");
            return "Job history entry already exists for the given employee and start date.";
        }

        Date endDate = new Date();
        JobHistory jobHistory = new JobHistory();
        System.out.println("1");
        jobHistory.setDepartment(department);
        System.out.println("1");
        jobHistory.setEmployee(employee);
        System.out.println("1");
        jobHistory.setId(jobHistoryId);
        System.out.println("1");
        jobHistory.setEndDate(endDate);
        System.out.println("1");
        jobHistoryRepository.save(jobHistory);

        return "Record Created Successfully";
    }

    @Override
    public String modifyJobHistory(BigDecimal employeeId, Date endDate) {
        // Find the job history entry based on employeeId and endDate
        String s = new String();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        List<JobHistory> jobHistory = jobHistoryRepository.findAll();
        for (JobHistory j:jobHistory) {
            Employee employee1 = j.getEmployee();
            int comparisonResult = employee.getEmployeeId().compareTo(employee1.getEmployeeId());
            if (comparisonResult == 0) {
                j.setEndDate(endDate);
                System.out.println(endDate);
                s="Record Modified Successfully";
                jobHistoryRepository.save(j);
                break;
            }
            else {
                s = "Not Updated";
            }
        }
        return s;
    }
    public Map<String, Integer> findExperienceOfEmployee(BigDecimal employeeId) {
        List<JobHistory> jobHistoryList = jobHistoryRepository.findByIdEmployeeId(employeeId);

        int totalYears = 0;
        int totalMonths = 0;
        int totalDays = 0;

        for (JobHistory jobHistory : jobHistoryList) {
            Date startDate = jobHistory.getStartDate();
            Date endDate = jobHistory.getEndDate();

            if (startDate != null && endDate != null) {
                LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(startLocalDate, endLocalDate);
                totalYears += period.getYears();
                totalMonths += period.getMonths();
                totalDays += period.getDays();
            }
        }
        // Normalize the total months and days
        totalYears += totalMonths / 12;
        totalMonths = totalMonths % 12;

        Map<String, Integer> experienceMap = new HashMap<>();
        experienceMap.put("years", totalYears);
        experienceMap.put("months", totalMonths);
        experienceMap.put("days", totalDays);

        return experienceMap;
    }

    public Duration getEmployeeExperience(BigDecimal employeeId) {
        List<JobHistory> jobHistoryList = jobHistoryRepository.findByIdEmployeeId(employeeId);

        Date currentDate = new Date();
        Duration totalDuration = Duration.ZERO;

        for (JobHistory jobHistory : jobHistoryList) {
            Date startDate = jobHistory.getStartDate();
            Date endDate = jobHistory.getEndDate() != null ? jobHistory.getEndDate() : currentDate;

            long durationInMillis = endDate.getTime() - startDate.getTime();
            totalDuration = totalDuration.plusMillis(durationInMillis);
        }

        return totalDuration;
    }



}
