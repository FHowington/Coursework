import java.util.*;

/**
 * Created by forbes on 7/8/17.
 */
public class Substitute implements SymCipher {
    private byte[] key;
    private HashMap<Byte,Character> decodeMap;

    // Randomly permutes values of ASCII characters (0-256 byte values are shuffled)
    public Substitute(){
        decodeMap = new HashMap<>();
        key = new byte[256];
        Byte[] tempKey = new Byte[256];
        for(int i=0;i<256;i++){
            tempKey[i]=(byte)i;
        }
        Collections.shuffle(Arrays.asList(tempKey));

        for(int i=0;i<256;i++){
            decodeMap.put(tempKey[i],(char)i);
        }
        int i=0;
        for(Byte bytes: tempKey)
            key[i++]=bytes;
    }

    // Sets key to input byte array and generates reverse key map
    public Substitute(byte[] bytekey){
        decodeMap = new HashMap<>();
        key=bytekey;
        for(int i=0;i<256;i++){
            decodeMap.put(bytekey[i],(char)i);
        }

    }

    public byte[] getKey() {
        return key;
    }

    // Encodes by substituting ASCII characters with corresponding value at ASCII value's index
    public byte[] encode(String S) {
        byte[] SBytes = S.getBytes();
        byte[] result = new byte[SBytes.length];
        for(int i=0;i<SBytes.length;i++){
            result[i]=key[SBytes[i] & 0xFF];
        }
        return result;
    }

    // Decodes by finding ASCII value that maps to byte value
    public String decode(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<bytes.length;i++) {
            result.append(decodeMap.get(bytes[i]));
        }
        return result.toString();
    }
}
