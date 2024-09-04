package bank;

import java.sql.*;
public class DataSource {

  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username){
    String sql = "select * from Customers where username=?";
    Customer customer = null;

    try(Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)){
          statement.setString(1, username);
          try(ResultSet resutlSet = statement.executeQuery()) {
            customer = new Customer(
              resutlSet.getInt("id"),
              resutlSet.getString("name"),
              resutlSet.getString("username"),
              resutlSet.getString("password"),
              resutlSet.getInt("account_id")
            );
          }
        }catch(SQLException e){
          e.printStackTrace();
        }
        return customer;
  }

  public static Account getAccount(int accoundId){
    String sql = "select * from Accounts where id = ?";// sql query
    Account account = null; // object initailezed later

    // try with resource structure
    // automatically closes specified resources after the structure has been
    // executed
    try(Connection connection = connect(); // connect with database
        // prepared statement used to efficiently and sercurely 
        PreparedStatement statement = connection.prepareStatement(sql)){
          statement.setInt(1, accoundId);
          try(ResultSet resultSet = statement.executeQuery()){
            account = new Account(
              resultSet.getInt("id"), 
              resultSet.getString("type"), 
              resultSet.getDouble("balance"));
          }
        }catch(SQLException e){
          e.printStackTrace();
        }
    return account;
  }
  
  public static void updateAccountBalance(int accoundId, double balance){
    String sql = "update accounts set balance = ? where id = ?";
    try(
      Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql);
    ){
      statement.setDouble(1, balance);
      statement.setInt(2, accoundId);

      statement.executeUpdate();

    }catch(SQLException e){
      e.printStackTrace();
    }
  }

}
