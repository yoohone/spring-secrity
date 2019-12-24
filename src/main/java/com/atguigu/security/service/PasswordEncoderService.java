package com.atguigu.security.service;

import com.atguigu.security.utils.CrowdFundingUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author sauke
 */
@Service
public class PasswordEncoderService implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        //传入的密码不能为空
        if (rawPassword == null || rawPassword.length() == 0) {
            throw new RuntimeException("明文规定密码不能为空！！！！");
        }
        return null;
    }


    //rawPassword是密文，encodedPassword是传入的明文
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //传入的密码不能为空
        if (rawPassword == null || rawPassword.length() == 0) {
            throw new RuntimeException("明文规定密码不能为空！！！！");
        }
        //对密码进行加密，获取到加密的密码
        String newPassWord = CrowdFundingUtils.md5(rawPassword.toString());
        //比较两个密码
        boolean result = Objects.equals(newPassWord, encodedPassword);
        return result;
    }
}
