package org.virtual.ai.token;


import jakarta.servlet.http.HttpServletRequest;
import org.virtual.business.common.entity.User;
import org.virtual.common.core.constant.CacheConstants;
import org.virtual.common.core.constant.SecurityConstants;
import org.virtual.common.core.utils.IdUtils;
import org.virtual.common.core.utils.SecurityUtils;
import org.virtual.common.core.utils.ServletUtils;
import org.virtual.common.core.utils.StringUtils;
import org.virtual.common.redis.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * token验证处理
 */
@Component
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     */
    public Token createToken(User user) {
        String token = IdUtils.fastUUID();
        var userState = new UserState();
        userState.setToken(token);
        userState.setUser(user);
        refreshToken(userState);

        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, user.getId());
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, user.getNickName());

        Token rspMap = new Token();
        rspMap.setAccessToken(JwtUtils.createToken(claimsMap));
        rspMap.setExpiresIn(expireTime);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserState getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserState getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserState getLoginUser(String token) {
        UserState user = null;
        try {
            if (StringUtils.isNotEmpty(token)) {
                String userkey = JwtUtils.getUserKey(token);
                user = RedisUtils.getCacheObject(getTokenKey(userkey));
                return user;
            }
        } catch (Exception e) {
            log.error("获取用户信息异常'{}'", e.getMessage());
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(UserState loginUser) {
        if (StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            RedisUtils.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(UserState loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(UserState loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        String userKey = getTokenKey(loginUser.getToken());
        RedisUtils.setCacheObject(userKey, loginUser, Duration.ofMinutes(CacheConstants.EXPIRATION));
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}
