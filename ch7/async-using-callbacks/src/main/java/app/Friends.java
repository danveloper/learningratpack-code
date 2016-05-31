package app;

import java.util.List;
import java.util.ArrayList;

public class Friends {

  private List<FriendReference> references;

  public List<Long> getPhotoIds() {
    List<Long> photoIds = new ArrayList<>();
    for (FriendReference reference : references) {
      photoIds.addAll(reference.getPhotoIds());
    }
    return photoIds;
  }

  public List<FriendReference> getReferences() {
    return references;
  }

  public void setReferences(List<FriendReference> references) {
    this.references = references;
  }

  public static class FriendReference {
    private Long userId;
    private List<Long> photoIds;

    public FriendReference(Long userId, List<Long> photoIds) {
      this.userId = userId;
      this.photoIds = photoIds;
    }

    public Long getUserId() {
      return this.userId;
    }

    public List<Long> getPhotoIds() {
      return this.photoIds;
    }
  }
}
