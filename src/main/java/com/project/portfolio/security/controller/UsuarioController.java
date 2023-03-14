package com.project.portfolio.security.controller;

import com.project.portfolio.security.dto.JwtDTO;
import com.project.portfolio.security.dto.LoginUsuario;
import com.project.portfolio.security.dto.NuevoUsuario;
import com.project.portfolio.security.dto.UsuarioDTO;
import com.project.portfolio.security.entity.Rol;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.entity.UsuarioPrincipal;
import com.project.portfolio.security.enums.RolNombre;
import com.project.portfolio.security.jwt.JwtProvider;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.security.service.RolService;
import com.project.portfolio.security.service.UserDetailsImplement;
import com.project.portfolio.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://frontendportfolio-bff8c.web.app")
public class UsuarioController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsImplement userDetailsImplement;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return  new ResponseEntity(new Mensaje ("Campos mal puestos o emal inválido."), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("Ese email ya existe."), HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()),
                nuevoUsuario.getTitulo(), "https://photos.app.goo.gl/CgSF6uLjeBoMX7Eh9"
                ,"https://photos.app.goo.gl/CgSF6uLjeBoMX7Eh9");

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        if (nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);

        return new ResponseEntity(new Mensaje ("Usuario guardado."), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return  new ResponseEntity(new Mensaje ("Campos mal puestos."), HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) userDetailsImplement.loadUserByUsername(userDetails.getUsername());

        JwtDTO jwtDTO = new JwtDTO (jwt, usuarioPrincipal.getId(), userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity(jwtDTO, HttpStatus.OK);
    }

    @GetMapping("/traer/perfil/{id}")
    public UsuarioDTO findUser(@PathVariable("id") Integer id){
        return usuarioService.findUser(id);
    }

    //Para eliminar usuario (eliminar la cuenta)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar/userId/{id}")
    public String deleteUser(@PathVariable Integer id){
        usuarioService.delete(id);
        return "El usuario fue eliminado correctamente.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarUsuario/userId/{id}")
    public Usuario editUser(@PathVariable Integer id,
                               @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTitulo(usuarioDTO.getTitulo());
        usuario.setSobreMi(usuarioDTO.getSobreMi());
        usuario.setFoto(usuarioDTO.getFoto());
        usuario.setBanner(usuarioDTO.getBanner());

        usuarioService.save(usuario);
        return usuario;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarBanner/userId/{id}")
    public Usuario editBanner(@PathVariable Integer id,
                            @RequestBody String banner){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setBanner(banner);

        usuarioService.save(usuario);
        return usuario;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarNombreYApellido/userId/{id}")
    public Usuario editNombreYApellido(@PathVariable Integer id,
                                       @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());

        usuarioService.save(usuario);
        return usuario;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarTitulo/userId/{id}")
    public Usuario editTitulo(@PathVariable Integer id,
                              @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setTitulo(usuarioDTO.getTitulo());

        usuarioService.save(usuario);
        return usuario;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarFoto/userId/{id}")
    public Usuario editFoto(@PathVariable Integer id,
                              @RequestBody String foto){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setFoto(foto);

        usuarioService.save(usuario);
        return usuario;
    }
}
