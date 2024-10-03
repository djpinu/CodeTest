package com.example.imaginnovate.employee.controller;

import com.example.imaginnovate.employee.model.Employee;
import com.example.imaginnovate.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Validated @RequestBody Employee employee){
        System.out.println("hi");
        if (service.addEmployee(employee)!=null)
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{employeeId}/tax-deductions")
    public ResponseEntity<?> taxDeduction(@PathVariable String employeeId){
        return new ResponseEntity<>(service.getTaxDeduction(employeeId), HttpStatus.OK);
    }
}
