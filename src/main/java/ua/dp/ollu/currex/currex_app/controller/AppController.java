package ua.dp.ollu.currex.currex_app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final String ADMIN_OPERATIONS_VIEW = "/admin/operations";
    private static final String USER_OPERATIONS_VIEW = "/user/operations";
    private static final String ADMIN_REFERENCE_VIEW = "/admin/reference";
    private static final String USER_REFERENCE_VIEW = "/user/reference";
    private static final String INDEX_VIEW = "/index";
    private static final String EXCHANGE_VIEW = "/user/exchange";
    private static final String REFERENCES_VAR = "references";
    private static final String OPERATIONS_VAR = "operations";
    private static final String DATE_FORMAT = "dd.MM.YY HH:mm";
    private static final String USER_EXCHANGE_MAPPING = "user/exchange";
    private static final String USER_REFERENCE_MAPPING = "/user/reference";
    private static final String ADMIN_REFERENCE_MAPPING = "/admin/reference";
    private static final String ADMIN_UPDATE_REFERENCES_MAPPING = "/admin/update_references";
    private static final String USER_OPERATIONS_MAPPING = "/user/operations";
    private static final String ADMIN_OPERATIONS_MAPPING = "/admin/operations";
    private static final String REMOVE_OPERATION_ID_MAPPING = "admin/remove_operation/{id}";
    private static final String LOGIN_VIEW = "login";
    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    private AdminService adminService;
    private UserService userService;
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    public AppController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping(value = {"/login"})
    public String login() {
        logger.info("/login controller");
        return LOGIN_VIEW;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(ModelMap model) {
        String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        logger.info("/index controller; auth:" + auth);
        if ("[ROLE_USER]".equals(auth)) {
            model.addAttribute(REFERENCES_VAR, userService.getReferences());
            return EXCHANGE_VIEW;
        }
        if ("[ROLE_ADMIN]".equals(auth)) return ADMIN_OPERATIONS_VIEW;
        return INDEX_VIEW;
    }

    @GetMapping(value = USER_EXCHANGE_MAPPING)
    public String showExchange(ModelMap model) {
        logger.info("/user/exchange GET controller");
        model.addAttribute("references", userService.getReferences());
        return EXCHANGE_VIEW;
    }

    @PostMapping(value = USER_EXCHANGE_MAPPING)
    @ResponseBody
    public String addExchange(@RequestBody Operation operation, ModelMap model) {
        try {
            logger.info("/user/exchange POST controller");
            userService.addExchange(operation);
            model.addAttribute("references", userService.getReferences());
            return "Ok";
        } catch (Error error) {
            return error.getMessage();
        }
    }

    @GetMapping(value = USER_REFERENCE_MAPPING)
    public String userReference(ModelMap model) {
        logger.info("/user/reference controller");
        model.addAttribute(REFERENCES_VAR, userService.getReferences());
        return USER_REFERENCE_VIEW;
    }

    @GetMapping(value = ADMIN_REFERENCE_MAPPING)
    public String adminReference(ModelMap model) {
        logger.info("/admin/reference controller");
        model.addAttribute(REFERENCES_VAR, adminService.getReferences());
        return ADMIN_REFERENCE_VIEW;
    }

    @GetMapping(value = ADMIN_UPDATE_REFERENCES_MAPPING)
    public String updateReferences(ModelMap model) {
        logger.info("/admin/update_references controller");
        adminService.updateReferences();
        model.addAttribute(REFERENCES_VAR, adminService.getReferences());
        return ADMIN_REFERENCE_VIEW;
    }

    @GetMapping(value = USER_OPERATIONS_MAPPING)
    public String userOperations(ModelMap model) {
        logger.info("/user/operation controller");
        model.addAttribute(OPERATIONS_VAR, userService.getOperations());
        model.addAttribute("formatter", formatter);
        return USER_OPERATIONS_VIEW;
    }

    @GetMapping(value = ADMIN_OPERATIONS_MAPPING)
    public String adminOperations(ModelMap model) {
        logger.info("/admin/operation controller");
        model.addAttribute(OPERATIONS_VAR, adminService.getOperations());
        model.addAttribute("formatter", formatter);
        return ADMIN_OPERATIONS_VIEW;
    }

    @GetMapping(value = REMOVE_OPERATION_ID_MAPPING)
    public String removeOperation(@PathVariable("id") long id, ModelMap model) {
        logger.info("/admin/remove_operation controller");
        adminService.removeOperation(id);
        model.addAttribute(OPERATIONS_VAR, adminService.getOperations());
        return ADMIN_OPERATIONS_VIEW;
    }
}
