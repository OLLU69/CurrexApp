package ua.dp.ollu.currex.currex_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.service.AdminService;
import ua.dp.ollu.currex.currex_app.service.UserService;

import java.text.SimpleDateFormat;

@RequestMapping("/")
@Controller
public class AppController {

    private static final String ADMIN_OPERATIONS_VIEW = "/admin_operations";
    private static final String USER_OPERATIONS_VIEW = "/user_operations";
    private static final String ADMIN_REFERENCE_VIEW = "/admin_reference";
    private static final String USER_REFERENCE_VIEW = "/user_reference";
    private static final String INDEX_VIEW = "/index";
    private static final String EXCHANGE_VIEW = "/exchange";
    private static final String REFERENCES_VAR = "references";
    private static final String OPERATIONS_VAR = "operations";
    private static final String DATE_FORMAT = "dd.MM.YY HH:mm";
    private AdminService adminService;
    private UserService userService;
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    public AppController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return INDEX_VIEW;
    }

    @GetMapping(value = "exchange")
    public String showExchange(ModelMap model) {
        model.addAttribute("references", userService.getReferences());
        return EXCHANGE_VIEW;
    }

    @PostMapping(value = "/exchange")
    @ResponseBody
    public String addExchange(@RequestBody Operation operation, ModelMap model) {
        try {
            userService.addExchange(operation);
            model.addAttribute("references", userService.getReferences());
            return "Ok";
        } catch (Error error) {
            return error.getMessage();
        }
    }

    @GetMapping(value = "/user_reference")
    public String userReference(ModelMap model) {
        model.addAttribute(REFERENCES_VAR, userService.getReferences());
        return USER_REFERENCE_VIEW;
    }

    @GetMapping(value = "/admin_reference")
    public String adminReference(ModelMap model) {
        model.addAttribute(REFERENCES_VAR, adminService.getReferences());
        return ADMIN_REFERENCE_VIEW;
    }

    @GetMapping(value = "/update_references")
    public String updateReferences(ModelMap model) {
        adminService.updateReferences();
        model.addAttribute(REFERENCES_VAR, adminService.getReferences());
        return ADMIN_REFERENCE_VIEW;
    }

    @GetMapping(value = "/user_operations")
    public String userOperations(ModelMap model) {
        model.addAttribute(OPERATIONS_VAR, userService.getOperations());
        model.addAttribute("formatter", formatter);
        return USER_OPERATIONS_VIEW;
    }

    @GetMapping(value = "/admin_operations")
    public String adminOperations(ModelMap model) {
        model.addAttribute(OPERATIONS_VAR, adminService.getOperations());
        model.addAttribute("formatter", formatter);
        return ADMIN_OPERATIONS_VIEW;
    }

    @GetMapping(value = "remove_operation/{id}")
    public String removeOperation(@PathVariable("id") long id, ModelMap model) {
        adminService.removeOperation(id);
        model.addAttribute(OPERATIONS_VAR, adminService.getOperations());
        return ADMIN_OPERATIONS_VIEW;
    }
}
