package at.luojia.spring6.iocxml.auto.server;

import at.luojia.spring6.iocxml.auto.dao.UserDao;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("UserServiceImpl-addUserService 方法执行了...");
        userDao.addUserDao();
    }
}
