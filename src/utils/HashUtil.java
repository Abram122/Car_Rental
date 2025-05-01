package utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

  //generate a BCrypt hash of the given plaintext password so now we can adding hashing feature to our system 
  public static String hashPassword(String plain) {
    //Look Abram it is 12 log rounds => work factor so it is 2^12 almost equal 4,096 iterations and that shows our interset to security
    return BCrypt.hashpw(plain, BCrypt.gensalt(12));
  }

  //vverify a plaintext password against a stored BCrypt hash so this is simply boolean function so we do comparsion
  public static boolean verifyPassword(String plain, String hashed) {
    return BCrypt.checkpw(plain, hashed);
  }
}

/* Some of the docs and repos i have looked at to implement this so you can also have a look on it because you have to search a lot 
  
  
  https://gist.github.com/craSH/5217757
  https://github.com/quarkusio/quarkus/blob/main/extensions/elytron-security-common/runtime/src/main/java/io/quarkus/elytron/security/common/BcryptUtil.java
  www.baeldung.com/java-password-hashing
  
  
 
*/