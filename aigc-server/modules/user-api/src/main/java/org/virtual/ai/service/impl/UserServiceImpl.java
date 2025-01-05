package org.virtual.ai.service.impl;

import org.virtual.common.core.exception.ServiceException;
import org.virtual.business.common.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.virtual.ai.domain.LoginResponse;
import org.virtual.ai.domain.UserInfoResponse;
import org.virtual.ai.domain.bo.LoginRequest;
import org.virtual.ai.domain.vo.UserVo;
import org.virtual.ai.mapper.MediaTaskGroupMapper;
import org.virtual.ai.mapper.UserMapper;
import org.virtual.ai.service.IUserService;
import org.virtual.ai.token.TokenService;
import org.virtual.ai.token.UserState;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final MediaTaskGroupMapper taskGroupMapper;

    private final TokenService tokenService;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, loginRequest.getUsername()));
        if (user == null) {
           throw new ServiceException("user not exist");
        }
        var userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        var response = new LoginResponse();
        response.setUser(userVo);

        var tokenResult = tokenService.createToken(user);
        response.setAccessToken(tokenResult.getAccessToken());
        response.setExpiresIn(tokenResult.getExpiresIn());
        return response;
    }

    @Override
    public UserInfoResponse info() {
        UserState dbUser = tokenService.getLoginUser();
        if (dbUser == null) {
            throw new ServiceException("no login");
        }
        var userVo = new UserVo();
        BeanUtils.copyProperties(dbUser.getUser(), userVo);
        var response = new UserInfoResponse();
        response.setUser(userVo);
        return response;
    }

}
