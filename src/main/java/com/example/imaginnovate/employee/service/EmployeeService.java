package com.example.imaginnovate.employee.service;

import com.example.imaginnovate.employee.model.Employee;
import com.example.imaginnovate.employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo repository;

    public Employee addEmployee(Employee employee) {

        repository.save(employee);
        return employee;
    }

    public Employee getTaxDeduction(String employeeId) {
        Optional<Employee> OpEmployee = repository.findById(employeeId);
        if (OpEmployee.isPresent()) {
            Employee employee = OpEmployee.get();
            double salary = employee.getSalary();
            double totalSalary = calculateTotalSalary(employee.getSalary(), employee.getDoj());
            double taxAmount = calculateTax(totalSalary);
            double cessAmount = calculateCess(totalSalary);

            employee.setTaxAmount(taxAmount);
            employee.setCessAmount(cessAmount);

            return employee;
        }
        return null;
    }
    private double calculateTotalSalary(double monthlySalary, LocalDate doj) {
        // Calculate the number of months worked
        LocalDate currentDate = LocalDate.now();
        long monthsWorked = ChronoUnit.MONTHS.between(doj, currentDate);
        if (monthsWorked < 0) {
            monthsWorked = 0; // If the DOJ is in the future
        }
        // Calculate the total salary based on months worked
        return monthlySalary * monthsWorked;
    }

    private double calculateTax(double totalSalary) {
        double tax = 0;

        // No tax for <= 250,000
        if (totalSalary > 250000) {
            // 5% tax for > 250,000 and <= 500,000
            if (totalSalary <= 500000) {
                tax += (totalSalary - 250000) * 0.05; // 5%
            }
            // 10% tax for > 500,000 and <= 1,000,000
            else if (totalSalary <= 1000000) {
                tax += 250000 * 0.05; // 5% on 250,000
                tax += (totalSalary - 500000) * 0.10; // 10%
            }
            // 20% tax for > 1,000,000
            else {
                tax += 250000 * 0.05; // 5% on first 250,000
                tax += 500000 * 0.10; // 10% on next 500,000
                tax += (totalSalary - 1000000) * 0.20; // 20%
            }
        }
        return tax;
    }

    private double calculateCess(double totalSalary) {
        if (totalSalary > 2500000) {
            return (totalSalary - 2500000) * 0.02; // 2% cess on the amount above 2,500,000
        }
        return 0;
    }
}
