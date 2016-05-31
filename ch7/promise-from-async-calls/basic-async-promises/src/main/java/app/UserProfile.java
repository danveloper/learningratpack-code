package app;

import java.util.List;

public class UserProfile {
  private Long id;
  private Long userId;
  private String username;
  private String firstName;
  private String middleName;
  private String lastName;
  private List<Long> friendIds;

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public List<Long> getFriendIds() {
    return friendIds;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFriendIds(List<Long> friendIds) {
    this.friendIds = friendIds;
  }

}
