package authentication;

import DTO.LoginRequestDTO;
import DTO.LoginResponseDTO;
import DTO.UsersDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import models.UsersModel;
import org.mindrot.jbcrypt.BCrypt;
import service.UsersService;
import utils.JwtUtil;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    private UsersService usersService;

    @Inject
    private JwtUtil jwtUtil;

    public AuthenticationService() {
    }

    public void register(UsersDTO dto) {
        usersService.createUser(dto);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        UsersModel user;
        try {
            user = usersService.getUsersByEmail(request.getEmail());
        } catch (NoResultException e) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = JwtUtil.generateAccessToken(user.getEmail(),user.getRole());
        String refreshToken = JwtUtil.generateRefreshToken(user.getEmail());

        UsersDTO userDTO = new UsersDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(null);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtUtil.getAccessTokenExpiration());
        response.setUsers(userDTO);

        return response;
    }
}
