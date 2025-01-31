package com.example.final20.controllers;
import com.example.final20.dao.EmployeesRepository;
import com.example.final20.entities.Employees;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
@Controller
@AllArgsConstructor
public class EmployeesController {
    private EmployeesRepository employeesRepository;
    @GetMapping("/employees")
    public String showPatients(Model model) {
        List<Employees> patients = employeesRepository.findByNameNotNullOrderByName();
        model.addAttribute("employees", patients);
        return "employees";
    }
    @PostMapping("/add_employees")
    public String addPatients(@RequestParam String name,
                              @RequestParam String address, @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam int medical_card_number, @RequestParam String diagnosis) {
        Employees employees = new Employees();
        employees.setName(name);
        employees.setAddress(address);
        employees.setPhone(phone);
        employees.setEmail(email);
        employees.setMedicalCardNumber(medical_card_number);
        employees.setDiagnosis(diagnosis);
        employeesRepository.save(employees);
        return "redirect:/employees";
    }
    @GetMapping("/delete_employees")
    public String deletePatients(@RequestParam int id) {
        employeesRepository.deleteById(id);
        return "redirect:/employees";
    }
    @GetMapping("/edit_employees")
    public String editPatients(@RequestParam int id, Model model) {
        Optional<Employees> optionalPatients = employeesRepository.findById(id);
        if (optionalPatients.isEmpty()) {
            return "redirect:/employees";
        }
        model.addAttribute("employees", optionalPatients.get());
        return "edit_employees";
    }
    @PostMapping("/update_employees")
    public String updateReception(@RequestParam int id, @RequestParam String name, @RequestParam String address,
                                  @RequestParam("phone") String phone, @RequestParam String email,
                                  @RequestParam("medical_card_number") int medical_card_number,
                                  @RequestParam String diagnosis) {
        Optional<Employees> optionalPatients = employeesRepository.findById(id);
        optionalPatients.ifPresent(t -> {
            t.setName(name);
            t.setAddress(address);
            t.setPhone(phone);
            t.setEmail(email);
            t.setMedicalCardNumber(medical_card_number);
            t.setDiagnosis(diagnosis);
            employeesRepository.save(t);
        });
        return "redirect:/employees";
    }
}