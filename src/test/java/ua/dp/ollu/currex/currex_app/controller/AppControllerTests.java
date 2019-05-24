package ua.dp.ollu.currex.currex_app.controller;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.ui.ModelMap;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.service.AdminService;
import ua.dp.ollu.currex.currex_app.service.UserService;

public class AppControllerTests {

    @Test
    public void testIndex() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/index", appController.index());
    }

    @Test
    public void testShowExchange() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/exchange",
                appController.showExchange(new ModelMap()));
    }

    @Test
    public void testAddExchange() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("Ok",
                appController.addExchange(new Operation(), new ModelMap()));
    }

    @Test
    public void testUserReference() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/user_reference",
                appController.userReference(new ModelMap()));
    }

    @Test
    public void testAdminReference() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/admin_reference",
                appController.adminReference(new ModelMap()));
    }

    @Test
    public void testUpdateReferences() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/admin_reference",
                appController.updateReferences(new ModelMap()));
    }

    @Test
    public void testUserOperations() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/user_operations",
                appController.userOperations(new ModelMap()));
    }

    @Test
    public void testAdminOperations() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/admin_operations",
                appController.adminOperations(new ModelMap()));
    }

    @Test
    public void testRemoveOperation() {
        AdminService adminService = PowerMockito.mock(AdminService.class);
        UserService userService = PowerMockito.mock(UserService.class);
        AppController appController =
                new AppController(adminService, userService);

        Assert.assertEquals("/admin_operations",
                appController.removeOperation(1L, new ModelMap()));
    }
}
