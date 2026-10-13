package pe.edu.utp.inventariopedidos.controller;

import pe.edu.utp.inventariopedidos.security.JwtService;
import pe.edu.utp.inventariopedidos.repository.UserRepository;
import pe.edu.utp.inventariopedidos.repository.RoleRepository;
import pe.edu.utp.inventariopedidos.user.Role;
import pe.edu.utp.inventariopedidos.user.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new RuntimeException("No existe el rol USER"));

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);

        userRepository.save(user);

        return Map.of("mensaje", "Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(authentication.getName());

        return Map.of("token", token);
    }

    public record RegisterRequest(
            String username,
            String email,
            String password) {
    }

    public record LoginRequest(String username, String password) {
    }
}
