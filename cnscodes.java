1.RSA ALGORITHM
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter a prime number p: ");
        BigInteger p = sc.nextBigInteger();

        System.out.print("Enter another prime number q: ");
        BigInteger q = sc.nextBigInteger();

        // Step 1: Compute n = p * q
        BigInteger n = p.multiply(q);

        // Step 2: Compute phi(n) = (p-1)*(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));

        // Step 3: Generate e
        BigInteger e = generateE(phi);

        // Step 4: Compute d (mod inverse)
        BigInteger d = e.modInverse(phi);

        // Output keys
        System.out.println("\nPublic Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
    }

    public static BigInteger generateE(BigInteger phi) {X
        Random rand = new Random();
        BigInteger e;

        do {
            e = new BigInteger(phi.bitLength(), rand);
        } while (e.compareTo(BigInteger.TWO) <= 0 ||
                 e.compareTo(phi) >= 0 ||
                 !phi.gcd(e).equals(BigInteger.ONE));

        return e;
    }
}
Enter a prime number p: 11
Enter another prime number q: 13

Public Key (e, n): (7, 143)
Private Key (d, n): (103, 143)
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
2.RINJEDEAL ALGORITHM
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class AESExample {

    // Convert byte array to hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // 1. Take input from user
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        // 2. Generate AES Key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // key size
        SecretKey secretKey = keyGen.generateKey();

        // Convert key
        byte[] raw = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

        // 3. Create Cipher
        Cipher cipher = Cipher.getInstance("AES");

        // 4. Encrypt
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(message.getBytes());

        System.out.println("Encrypted text: " + bytesToHex(encrypted));

        // 5. Decrypt
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        String originalText = new String(decrypted);

        System.out.println("Decrypted text: " + originalText);
    }
}
Enter your message: hello
Encrypted text: 5f4dcc3b5aa765d61d8327deb882cf99
Decrypted text: hello
---------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------
3.SHA-1
import java.security.MessageDigest;

public class SHA1Example {

    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            System.out.println("Message Digest Info:");
            System.out.println("Algorithm = " + md.getAlgorithm());
            System.out.println("Provider = " + md.getProvider());
            System.out.println();

            // Test inputs
            String[] inputs = {
                "",
                "abc",
                "abcdefghijklmnopqrstuvwxyz"
            };

            for (String input : inputs) {
                md.update(input.getBytes());
                byte[] output = md.digest();

                System.out.println("SHA1(\"" + input + "\") = " + bytesToHex(output));
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    // Convert bytes to hex
    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {
            '0','1','2','3','4','5','6','7',
            '8','9','A','B','C','D','E','F'
        };

        StringBuffer buf = new StringBuffer();

        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0F]);
            buf.append(hexDigit[b[j] & 0x0F]);
        }

        return buf.toString();
    }
}
Message Digest Info:
Algorithm = SHA-1
Provider = SUN version 1.8

SHA1("") = DA39A3EE5E6B4B0D3255BFEF95601890AFD80709

SHA1("abc") = A9993E364706816ABA3E25717850C26C9CD0D89D

SHA1("abcdefghijklmnopqrstuvwxyz") = 32D10C7B8CF96570CA04CE37F2A19D84240D3A89
---------------------------------------------------------------------
import java.math.BigInteger;

public class DiffieHellmanExample {

    public static void main(String[] args) {

        // Public values
        BigInteger p = BigInteger.valueOf(47);
        BigInteger g = BigInteger.valueOf(71);

        // Private keys
        BigInteger Xa = BigInteger.valueOf(9);  // Alice
        BigInteger Xb = BigInteger.valueOf(14); // Bob

        // Step 1: Public values
        BigInteger Ya = g.modPow(Xa, p); // Alice public
        BigInteger Yb = g.modPow(Xb, p); // Bob public

        System.out.println("Alice Public Key (Ya): " + Ya);
        System.out.println("Bob Public Key (Yb): " + Yb);

        // Step 2: Shared secret
        BigInteger Ka = Yb.modPow(Xa, p); // Alice computes
        BigInteger Kb = Ya.modPow(Xb, p); // Bob computes

        System.out.println("Secret Key (Alice): " + Ka);
        System.out.println("Secret Key (Bob): " + Kb);
    }
}
Alice Public Key (Ya): 34
Bob Public Key (Yb): 4
Secret Key (Alice): 9
Secret Key (Bob): 9
