package RepoInterface;

import Domain.User;

public interface IUserRepository {
    User findUser(User user);
    User findByUsername(User user);
    void saveUser(User user) throws Exception;
}
