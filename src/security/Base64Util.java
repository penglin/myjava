/*
 * Created on 2005-7-12
 *
 * BASE64���������
 */
package security;

import java.io.*;

/*
 * Base64 �ַ���
 * ��ֵ �ַ�      ��ֵ �ַ�      ��ֵ �ַ�      ��ֵ �ַ�
 *    0    A        17    R        34    i        51    z
 *    1    B        18    S        35    j        52    0
 *    2    C        19    T        36    k        53    1
 *    3    D        20    U        37    l        54    2
 *    4    E        21    V        38    m        55    3
 *    5    F        22    W        39    n        56    4
 *    6    G        23    X        40    o        57    5
 *    7    H        24    Y        41    p        58    6
 *    8    I        25    Z        42    q        59    7
 *    9    J        26    a        43    r        60    8
 *   10    K        27    b        44    s        61    9
 *   11    L        28    c        45    t        62    +
 *   12    M        29    d        46    u        63    /
 *   13    N        30    e        47    v
 *   14    O        31    f        48    w     (pad)    =
 *   15    P        32    g        49    x
 *   16    Q        33    h        50    y
 */

/**
 * @author Administrator
 * 
 * BASE64���������
 */
public final class Base64Util
{
    /**
     * ��̬��Ա��ʼ��,BASE64�����
     */
    final private static String encodeTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * BASE64������,�����㷨����:
     * <1>.�����ݰ�3���ֽ�һ��ֳ����飻
     * <2>.ÿ�齫3��8λ������ת����4��6λ���ݶΣ�
     *     11111111 00000000 11111111  ----  111111 110000 000011 111111
           12345678 12345678 12345678  ----  123456 781234 567812 345678
     * <3>.����Base64�ַ����õ�4��6λ���ݶζ�Ӧ���ַ���
     * <4>.������һ��ֻ�������ֽڣ�����������0λ��ת���ɶ�ӦBase64�ַ����������ַ������ڽ�β��һ��'='�ַ���
     *     ������һ��ֻ��һ���ֽڣ��������ĸ�0λ��ת���ɶ�ӦBase64�ַ����������ַ������ڽ�β������'='�ַ���
     * @param data ��Ҫ���������
     * @return ���ر������
     */
    public static String encode(byte[] data)
    {
        //�ж��������ݵ���Ч��
        if (data == null)
        {
            return null;
        }
        //��������ṹ�Ļ�����
        StringBuffer encoded = new StringBuffer();
        //ѭ����ʼ����
        int i, individual, remain = 0;
        for (i = 0; i < data.length; i++)
        {
            //��BYTE��������ת����int
            individual = data[i] & 0x000000ff;
            switch (i % 3)
            {
                case 0:
                    //�����������
                    encoded.append(encodeTable.charAt(individual >> 2));
                    //�������ƶ���λ
                    remain = (individual << 4) & 0x30;
                    break;
                case 1:
                    //�����������
                    encoded.append(encodeTable.charAt(remain | individual >> 4));
                    //�������ƶ���λ
                    remain = (individual << 2) & 0x3c;
                    break;
                case 2:
                    //�����������
                    encoded.append(encodeTable.charAt(remain | individual >> 6));
                    //�����������
                    encoded.append(encodeTable.charAt(individual & 0x3f));
                    break;
            }
            //�жϻ���
            if ( ( (i + 1) % 57) == 0)
                encoded.append("\r\n");
        }
        //ĩβ��λ
        switch (i % 3)
        {
            case 1:
                //���ĸ�0λ����������Base64�ַ�,ĩβ��==
                encoded.append(encodeTable.charAt(remain));
                encoded.append("==");
                break;
            case 2:
                //������0λ����������Base64�ַ�,ĩβ��=
                encoded.append(encodeTable.charAt(remain));
                encoded.append('=');
                break;
        }
        //���ؽ��
        return encoded.toString();
    }

    /**
     * BASE64����,�����㷨����
     * <1>.�����ݰ�4���ֽ�һ��ֳ����飻
     * <2>.ÿ�齫4���ַ�ȥ�������λ��ת����3��8λ�����ݶΣ�
     *     ע�⣺���ݿ��е��ַ�ȡֵ����ASCII����ֵ������Base64�ַ��������Ӧ������ֵ��
     *     00 111111   00 110000   00 000011   00 111111 ---- 11111111 00000000 11111111
     * <3>.����ASCII�ַ����õ�3��8λ���ݶζ�Ӧ���ַ���
     * <4>.������һ��ֻ������'='��ȥ������'='����ȥ�������λ��ת���ɶ�ӦASSCII�ַ����������ַ���
     *    ������һ��ֻ��һ��'='��ȥ��'='����ȥ�������λ��ת���ɶ�ӦASSCII�ַ�����һ���ַ���
     * @param encodedData ��Ҫ���������
     * @return ����������
     */
    public static byte[] decode(String encodedData)
    {
        byte[] data = encodedData.getBytes();
        //�����������ݻ�����
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        //ѭ������,ÿ�ĸ��ֽڷ�Ϊһ���������ݿ�
        int nBlockIndex = 0;
        byte individual = 0, remain = 0;
        for (int i = 0; i < data.length; i++)
        {
            //���˻س����з�
            if (data[i] == '\r' || data[i] == '\n')
                continue;
            //�������'=',�������
            if (data[i] == '=')
                break;
            //��ȡһ�����ַ�
            individual = (byte) decodeBase64Char( (char) data[i]);
            //����ڿ�Ĳ�ͬλ�ã��ֱ���
            switch ( (nBlockIndex++) % 4)
            {
                case 0:
                    remain = (byte) (individual << 2);
                    break;
                case 1:
                    output.write( (char) (remain | (individual >> 4)));
                    remain = (byte) (individual << 4);
                    break;
                case 2:
                    output.write( (char) (remain | (individual >> 2)));
                    remain = (byte) (individual << 6);
                    break;
                case 3:
                    output.write(remain | individual);
                    break;
            }
        }
        //���ؽ������
        return output.toByteArray();
    }

    /**
     * BASE64�ַ�������������
     * @param code �����ַ�
     * @return ����
     */
    private static byte decodeBase64Char(char code)
    {
        if (code >= 'A' && code <= 'Z')
            return (byte) (code - 'A');
        else if (code >= 'a' && code <= 'z')
            return (byte) (code - 'a' + 26);
        else if (code >= '0' && code <= '9')
            return (byte) (code - '0' + 52);
        else if (code == '+')
            return 62;
        else if (code == '/')
            return 63;
        return 64;
    }
}