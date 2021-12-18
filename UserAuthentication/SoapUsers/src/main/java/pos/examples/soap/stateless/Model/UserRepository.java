package pos.examples.soap.stateless.Model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
   // public User getById(Integer userId);
}
