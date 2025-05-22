package org.example.pioneer.service;

import org.example.pioneer.domain.User;
import org.example.pioneer.dto.UserDTO;
import org.example.pioneer.dto.UserProfileDTO;
import org.example.pioneer.dto.UserRegisterDTO;
import org.example.pioneer.enumrole.Role;
import org.example.pioneer.mapper.UserMapper;
import org.example.pioneer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * Guardar un nuevo usuario
     */
    public UserDTO saveUser(UserRegisterDTO userRegisterDTO) {
        System.out.println("DTO recibido: " + userRegisterDTO);

        User user = userMapper.toUser(userRegisterDTO);
        System.out.println("Entidad User después del mapeo: " + user);

        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRoles(List.of(Role.ROLE_USER));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        System.out.println("Usuario guardado: " + savedUser);

        return userMapper.toUserDTO(savedUser);
    }


    /**
     * Encontrar un usuario por correo electrónico
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    /**
     * Verificar contraseña
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Obtener un usuario por su ID
     */
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
        return userMapper.toUserDTO(user);
    }

    /**
     * Obtener todos los usuarios
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar información de perfil del usuario
     */
    public UserDTO updateUser(Long userId, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        // Actualizar datos del usuario según los valores proporcionados en el DTO
        if (userProfileDTO.getUsername() != null && !userProfileDTO.getUsername().isEmpty()) {
            user.setUsername(userProfileDTO.getUsername());
        }
        if (userProfileDTO.getLastName() != null && !userProfileDTO.getLastName().isEmpty()) {
            user.setLastName(userProfileDTO.getLastName());
        }
        if (userProfileDTO.getEmail() != null && !userProfileDTO.getEmail().isEmpty()) {
            user.setEmail(userProfileDTO.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    /**
     * Eliminar un usuario por su ID
     */
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}