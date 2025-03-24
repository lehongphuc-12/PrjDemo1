package service;


import model.Role;
import model.User;
import userDAO.UserDAO;

public class AuthService {
    
    private final UserDAO userDAO;
    
    public AuthService() {
        userDAO = new UserDAO();
    }
    
    public User findByEmailAndPassword(String email,String password){
        return userDAO.findByEmailAndPasswordDAO(email, password);
    }
    
    public User findByEmail(String email){
        return userDAO.findByEmailDAO(email);
    }
    
    public Role findRoleById(int id){
        return userDAO.findRoleByIdDAO(id);
    }
    
    public void create(User user){
        userDAO.createDAO(user);
    }
    public void updatePassword(String email,String newPassword){
        userDAO.updatePasswordDAO(email, newPassword);
    }
    
    public static void main(String[] args) {
        AuthService a = new AuthService();
        System.out.println(a.findByEmail("lethiven@gmail.com"));
    }
}