package com.example.andersendbapp.DAO;

import com.example.andersendbapp.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
   private  Connection con;

    public UserDAOImpl() {
        con = getConnection();
    }

    private final String SQL_FIND_ALL =
            "SELECT * FROM users";

    private final String SQL_FIND_BY_ID =
            "SELECT * FROM users " +
                    "WHERE id = ?";

    private final String SQL_UPDATE =
            "UPDATE users " +
                    "SET firstname = ? , " +
                    "lastname = ? , " +
                    "age = ? " +
                    "WHERE id = ?";


    private final String SQL_SAVE =
            "INSERT INTO users (firstname,lastname,age) VALUES(?, ?, ?)";


    private final String SQL_DELETE =
            "DELETE FROM users " +
                    "WHERE id = ?";

    Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(
            "jdbc:postgresql://10.211.55.40:5432/mydbtest","fedor","123456");
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return con;
    }



    @Override
    public Optional<User> findById(long id) {
        Optional<User> optionalUser;

        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQL_FIND_BY_ID);

        statement.setLong(1,id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        optionalUser = Optional.of(
                new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4) ));

        return optionalUser;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveUser(User user) {
        try {
        PreparedStatement statement = con.prepareStatement(SQL_SAVE);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAge());
        return (statement.executeUpdate() >= 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(User user) {

        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQL_UPDATE);

        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAge());
        statement.setLong(4, user.getId());
        statement.execute();

        return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id){
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQL_DELETE);

        statement.setLong(1,id);
        if(statement.executeUpdate() == 0){
           // throw new IllegalAccessException("was not deleted");
            //придумать шо там как
        }
        return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findall() {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQL_FIND_ALL);

        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            User user = new User();
            user.setAge(rs.getInt("age"));
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            userList.add(user);
        }

        return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
