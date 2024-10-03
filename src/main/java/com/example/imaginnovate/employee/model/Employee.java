package com.example.imaginnovate.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @NotBlank(message = "Employee ID is mandatory")
    @Pattern(regexp = "^E\\d*$", message = "Employee ID must start with 'E' followed by zero or more digits (e.g., E, E1, E123)")
    private String employeeId;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone numbers cannot be empty")
    private List<@Pattern(regexp = "^\\d{10}$", message = "Phone number must be of 10 digits")String> phoneNumbers;

    @NotNull(message = "Date of Joining is mandatory")
    @PastOrPresent(message = "Date of Joining must be in the past or present")
    private LocalDate doj;  // Date of Joining

    @Positive(message = "Salary must be positive")
    private double salary;

    private double taxAmount; // Calculated tax amount
    private double cessAmount;
}
