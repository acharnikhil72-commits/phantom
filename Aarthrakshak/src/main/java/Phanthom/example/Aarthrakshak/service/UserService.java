package Phanthom.example.Aarthrakshak.service;

import Phanthom.example.Aarthrakshak.model.User;
import Phanthom.example.Aarthrakshak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String name, String email, String archetype) {
        User user = User.builder()
                .name(name)
                .email(email)
                .archetype(archetype)
                .averageSpend(0.0)
                .healthScore(50)
                .build();
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateAverageSpend(Long userId, Double newAmount) {
        User user = getUser(userId);
        Double current = user.getAverageSpend();
        user.setAverageSpend((current + newAmount) / 2);
        userRepository.save(user);
    }
}
