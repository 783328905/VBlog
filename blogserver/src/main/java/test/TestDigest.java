package test;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * Created by caiping on 2019/11/6.
 */
public class TestDigest {

    public static void main(String[] args) {
        CharSequence charSequence = new String("test");
        String s = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());

        System.out.println(s);
    }

    @Test
    public void testReg(){
        String s = "韩信";
        System.out.println(s.replaceAll("<p .*?>", ""));

        String a [] = {"2","adsf","1"};
        System.out.println();
    }
}
