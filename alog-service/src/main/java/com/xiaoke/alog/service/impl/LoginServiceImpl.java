package com.xiaoke.alog.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xiaoke.alog.common.AlogConfiguration;
import com.xiaoke.alog.common.Result;
import com.xiaoke.alog.dto.LoginResultDTO;
import com.xiaoke.alog.entity.User;
import com.xiaoke.alog.mapper.UserMapper;
import com.xiaoke.alog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result register(String account, String password, String nickname) {
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)) {
            return Result.buildByCode(Result.Code.BAD_REQUEST, "用户名、密码或昵称不能为空！");
        }

        // 登录账号查重
        if (userMapper.accountExist(account))
            return Result.buildByCode(Result.Code.BAD_REQUEST, "账号已被占用");

        // 存储用户信息到数据库
        userMapper.insertUser(User.builder()
                .account(account)
                .password(encryptPassword(password)) // 密码加密后再存储到数据库中！
                .avatar("/pic/default_avatar.jpg")
                .nickname(nickname)
                .sex(0)
                .birthday(new Date(315504000000L))
                .profile("这个人很懒，什么都没有写...")
                .countSubscriptions(0)
                .countFans(0)
                .build());
        return Result.ok("注册成功");
    }

    @Override
    public Result login(String account, String password) {
        // 在数据库中查找用户信息
        User user = userMapper.selectByAccount(account);
        if (user == null)
            return Result.buildByCode(Result.Code.BAD_AUTHORIZATION, "用户名或密码错误");

        // 验证密码
        if (!checkPassword(password, user.getPassword()))
            return Result.buildByCode(Result.Code.BAD_AUTHORIZATION, "用户名或密码错误");

        // 生成secret
        String secret = initSecret();
        // 生成token
        String token = JWT.create()
                .withClaim("id", user.getId())
                .withIssuedAt(new Date()) // Token签名时间
                .sign(Algorithm.HMAC256(secret));
        // 保存用户secret到Redis。key是用户id，value是用户token
        stringRedisTemplate.opsForValue().set(user.getId().toString(),
                secret,
                AlogConfiguration.userSecretTimeout,
                TimeUnit.MILLISECONDS);

        return Result.ok(LoginResultDTO.builder()
                .id(user.getId())
                .account(user.getAccount())
                .token(token)
                .build());
    }

    /**
     * SHA-512加密
     *
     * @param src 源字符串
     * @return 加密之后的字符串。src为空时返回null
     */
    private String encryptBySHA512(String src) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            log.error("MessageDigest获取实例失败", e);
        }

        return new String(messageDigest.digest(src.getBytes()));
    }

    /**
     * 密码加密
     *
     * @param password 用户密码
     * @return 加密后的字符串
     */
    private String encryptPassword(String password) {
        // 使用SHA-512先加密一次密码
        String tmp = encryptBySHA512(password);
        // 再使用bcrypt算法加密之前的中间结果
        return BCrypt.hashpw(tmp, BCrypt.gensalt(13));
    }

    /**
     * 验证密码是否正确
     *
     * @param password 密码
     * @param hashed   加密存储的密码
     * @return true 密码一致
     */
    private boolean checkPassword(String password, String hashed) {
        // 使用SHA-512先加密一次密码
        String tmp = encryptBySHA512(password);
        // 验证密码是否一致
        return BCrypt.checkpw(tmp, hashed);
    }

    /**
     * 随机生成一个密钥
     * @return 密钥
     */
    private String initSecret() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return encryptBySHA512(uuid);
    }
}
