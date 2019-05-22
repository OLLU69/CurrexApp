package ua.dp.ollu.currex.currex_app.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;
import ua.dp.ollu.currex.currex_app.repository.UserDao;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private OperationValidator validator;

    public UserServiceImpl(@Qualifier("userDaoJDBC") UserDao userDao, OperationValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public List<Reference> getReferences() {
        return userDao.getReferences();
    }

    @Override
    public List<Operation> getOperations() {
        return userDao.getOperations();
    }

    @Override
    public void addExchange(Operation operation) {
        userDao.addExchange(validator.validate(operation));
    }
}
