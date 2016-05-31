import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

ITERATIONS = 1000
KEY_LENGTH = 192

println hashPassword("r@7p4ckRul3Z!!", "ratpack")

String hashPassword(String password, String salt) {
  char[] passwordChars = password.toCharArray();
  byte[] saltBytes = salt.getBytes();

  PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
  SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
  byte[] hashedPassword = key.generateSecret(spec).getEncoded();
  return String.format("%x", new BigInteger(hashedPassword));
}
