package app;

import java.util.List;

public class UserProfile {
  private String username;
  private List<String> roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public boolean isAuthorized(String role) {
    return roles.contains(role);
  }
}
