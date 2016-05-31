package app

import org.pac4j.core.exception.CredentialsException
import org.pac4j.core.profile.CommonProfile
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.pac4j.http.credentials.authenticator.UsernamePasswordAuthenticator
import org.pac4j.http.profile.HttpProfile

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class MapUsernamePasswordAuthenticator implements UsernamePasswordAuthenticator { // <1>


  private static final int ITERATIONS = 1000 // <2>
  private static final int KEY_LENGTH = 192 // <3>

  Map<String, String> userMap

  public MapUsernamePasswordAuthenticator(Map<String, String> userMap) { // <4>
    this.userMap = userMap
  }

  @Override
  void validate(UsernamePasswordCredentials credentials) { // <5>
    def passHash = userMap.get(credentials.username) // <6>

    if (!passHash || passHash != hashPassword(credentials.password,
        credentials.username)) { // <7>
      throwsException("Invalid username or password.") // <8>
    }

    final HttpProfile profile = new HttpProfile();
    profile.setId(credentials.username);
    profile.addAttribute(CommonProfile.USERNAME, credentials.username);
    credentials.setUserProfile(profile);
  }

  protected void throwsException(final String message) {
    throw new CredentialsException(message);
  }

  public static String hashPassword(String password, String salt) { // <9>
    char[] passwordChars = password.toCharArray();
    byte[] saltBytes = salt.getBytes();

    PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS,
        KEY_LENGTH); // <10>
    SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); // <11>
    byte[] hashedPassword = key.generateSecret(spec).getEncoded(); // <12>
    return String.format("%x", new BigInteger(hashedPassword)); // <13>
  }
}