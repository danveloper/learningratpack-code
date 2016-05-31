package app

import com.google.inject.Inject
import groovy.sql.Sql
import org.pac4j.core.exception.CredentialsException
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.pac4j.http.credentials.authenticator.UsernamePasswordAuthenticator
import org.pac4j.http.profile.HttpProfile

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class DatabaseUsernamePasswordAuthenticator 
      implements UsernamePasswordAuthenticator {
  private static final int ITERATIONS = 1000
  private static final int KEY_LENGTH = 192 

  private final Sql sql

  @Inject
  public DatabaseUsernamePasswordAuthenticator(Sql sql) { // <1>
    this.sql = sql
  }

  @Override
  void validate(UsernamePasswordCredentials credentials) { 
    def userRow = sql.firstRow(  // <2>
    "SELECT * FROM USER_AUTH WHERE USER = ${credentials.username}"
    )

    if (!userRow) { // <3>
      throwsException("Invalid username or password")
    }

    def passHash = userRow["PASS"] // <4>

    if (!passHash || passHash != hashPassword(credentials.password, 
        credentials.username)) { 
      throwsException("Invalid username or password.") 
    }

    credentials.userProfile = new HttpProfile(id: credentials.username)
  }

  protected void throwsException(final String message) {
    throw new CredentialsException(message);
  }

  public static String hashPassword(String password, String salt) { 
    char[] passwordChars = password.toCharArray();
    byte[] saltBytes = salt.getBytes();

    PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, 
                                     KEY_LENGTH); 
    SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); 
    byte[] hashedPassword = key.generateSecret(spec).getEncoded(); 
    return String.format("%x", new BigInteger(hashedPassword)); 
  }
}
