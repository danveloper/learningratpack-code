package app;

public class User {
  private Long id;
  private String username;
  private Long profileId;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public Long getProfileId() {
    return profileId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setProfileId(Long profileId) {
    this.profileId = profileId;
  }
}
