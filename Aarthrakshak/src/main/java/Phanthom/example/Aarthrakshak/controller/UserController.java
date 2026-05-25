package Phanthom.example.Aarthrakshak.controller;

import Phanthom.example.Aarthrakshak.model.User;
import Phanthom.example.Aarthrakshak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/user/create
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {
        User user = userService.createUser(
                body.get("name"),
                body.get("email"),
                body.get("archetype"));
        return ResponseEntity.ok(user);
    }

    // GET /api/user/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
