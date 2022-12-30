package com.example.andersendbapp.DAO;

import com.example.andersendbapp.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> findById(long id) ;
    boolean saveUser(User user) ;
    boolean update(User user) ;
    boolean delete(long id) ;

    List<User> findall() ;
}
