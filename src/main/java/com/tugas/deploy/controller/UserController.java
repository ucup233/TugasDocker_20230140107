package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Credentials (temporary, no database)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "20230140107";

    // Temporary in-memory student data
    private static final List<User> mahasiswaList = new ArrayList<>();

    // ===================== LOGIN =====================

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // If already logged in, redirect to home
        if (session.getAttribute("loggedIn") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", username);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ===================== HOME =====================

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mahasiswaList", mahasiswaList);
        return "home";
    }

    // ===================== FORM TAMBAH MAHASISWA =====================

    @GetMapping("/form")
    public String formPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/tambah")
    public String tambahMahasiswa(@RequestParam String nim,
            @RequestParam String nama,
            @RequestParam String jenisKelamin,
            HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        mahasiswaList.add(new User(nim, nama, jenisKelamin));
        return "redirect:/home";
    }

}
