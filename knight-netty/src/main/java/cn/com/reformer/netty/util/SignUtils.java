package cn.com.reformer.netty.util;


import java.math.BigInteger;

/**
 * Created by feixiang on 2017-06-13.
 */
public class SignUtils {

    public static final byte[]  keys={0x59, (byte) 0xDC,0x27,0x7F,0x08,0x3A, (byte) 0x95,0x01, (byte) 0xA4, (byte) 0xBF,0x17,0x45, (byte) 0xA5,0x69, (byte) 0xE6,0x31};


    public static String getSigin(String sn,byte cmd,String notice){
        byte[] ret=new byte[16];
        byte[] _temp_crc=new byte[14];
        int _sn=Integer.parseInt(sn);
        int _notice=Integer.parseInt(notice);
        byte[]  sn_byte=sn.getBytes();
        byte[] nonce_byte=notice.getBytes();


        System.arraycopy(sn_byte, 0, ret,0,9);
        ret[9]=cmd;
        System.arraycopy(nonce_byte, 0, ret,10,4);

  //======================crc16========================================
        System.arraycopy(sn_byte, 0, _temp_crc,0,9);
        _temp_crc[9]=cmd;
        System.arraycopy(nonce_byte, 0, _temp_crc, 10, 4);

        String crc = getCRC(_temp_crc);

        byte[] src = hexStringToBytes(crc);
        System.arraycopy(src, 0, ret, 14, 2);
        try {
            String bytes2Hex = bytes2Hex(ret);
            System.out.println(bytes2Hex);
            byte[] encryptResult = AESCoder.encrypt(ret, keys);
            String s = bytes2Hex(encryptResult);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bytes2Hex = bytes2Hex(ret);
        return bytes2Hex;
    }

    private static byte[] intToByteArray(final int integer) {
        int byteNum = (40 -Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer))/ 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
            byteArray[3 - n] = (byte) (integer>>> (n * 8));

        return (byteArray);
    }


    /**
     * 计算CRC16校验码
     *
     * @param bytes 字节数组
     * @return {@link String} 校验码
     * @since 1.0
     */
    public static String getCRC(byte[] bytes) {
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;

        return Integer.toHexString(crc);
    }
    /**
     * byte数组转hex字符串<br/>
     * 一个byte转为2个hex字符
     * @param src
     * @return
     */
    public static String bytes2Hex(byte[] src){
        char[] res = new char[src.length*2];
        final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        for(int i=0,j=0; i<src.length; i++){
            res[j++] = hexDigits[src[i] >>>4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }

        return new String(res);
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    /**
     * 将16进制单精度浮点型转换为10进制浮点型
     *
     * @return float
     * @since 1.0
     */
    private float parseHex2Float(String hexStr) {
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }

    /**
     * 将十进制浮点型转换为十六进制浮点型
     *
     * @return String
     * @since 1.0
     */
    private String parseFloat2Hex(float data) {
        return Integer.toHexString(Float.floatToIntBits(data));
    }

    public static void main(String[] args) {


        String str="43276876";
        str.getBytes();
        String sing=getSigin("170627000", (byte) 0x01,"43276876");
        System.out.println(sing);
        System.out.println("============解密 ===============");
        try {
            byte[] bytes =hexStringToBytes(sing);
            byte[] bb=AESCoder.decrypt(bytes,AESCoder.keys);
            System.out.println(bytes2Hex(bb));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
