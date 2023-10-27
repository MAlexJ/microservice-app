package com.malexj.service.impl;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.dto.request.UserRequest;
import com.malexj.model.dto.response.UserResponse;
import com.malexj.model.entity.User;
import com.malexj.repository.RoleRepository;
import com.malexj.repository.UserRepository;
import com.malexj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ObjectMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Mono<User> findUserCredentials(String username) {
        return userRepository.findUserEntitiesByUsernameIgnoreCase(username).flatMap(this::fetchRole);
    }

    private Mono<User> fetchRole(User user) {
        return roleRepository.findRoleById(user.getRoleId()).map(user::addRole);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest request) {
        User entity = mapper.convertToEntity(request);
        return userRepository.save(entity).map(mapper::convertToResponse);
    }
}
