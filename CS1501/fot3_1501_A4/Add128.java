import java.util.Random;

/**
 * Written by Forbes Turley for the University of Pittsburgh
 * 7/9/17
 */

public class Add128 implements SymCipher {
    private byte[] key;

    // Randomly generates 128 byte key
    public Add128(){
        key = new byte[128];
        Random rand = new Random();
        rand.nextBytes(key);
    }

    // Sets key to given input byte array
    public Add128(byte[] bytekey) {
        key=bytekey;
    }

    public byte[] getKey() {
        return key;
    }

    // Encodes the given string by adding the corresponding number of bytes for each index
    // When end of key is reached, returns to the front of key and continues encoding
    public byte[] encode(String S) {
        byte[] SBytes = S.getBytes();
        byte[] result = new byte[SBytes.length];
        int j=0;
        int reset=0;
        for(int i=0;i<SBytes.length;i++){
            result[i]=(byte)(SBytes[i]+key[i-key.length*reset]);
            j++;
            if(j==key.length) {
                j = 0;
                reset++;
            }
        }
        return result;
    }

    // Decodes the given array of bytes by subtracting the corresponding number of bytes based on the index
    // Moves back to the beginning of the key when end is reached and continues decoding
    public String decode(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        int j=0;
        int reset=0;
        for(int i=0;i<bytes.length;i++){
            result[i]=(byte)(bytes[i]-key[i-key.length*reset]);
            j++;
            if(j==key.length) {
                j = 0;
                reset++;
            }
        }
        return new String(result);
    }
}
