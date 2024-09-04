package bank;

import java.sql.*;

import javax.jcr.LoginException;

public class Authenticator {
  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);
    if (username == null) {
      throw new LoginException("Username not found");
    }

    if (password.equals(customer.getPassword())) {
      customer.setAccountverified(true);
      return customer;
    } else
      throw new LoginException("Incorrect password");
  }

  public static void logout(Customer customer) {
    customer.setAccountverified(false);
  }
}
