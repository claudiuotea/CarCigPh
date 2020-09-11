package Repositories;

import Domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepoTest {
    @Test
    public void testFind() {
        hibernateUtils.initialize();
        UserRepo userRepo = new UserRepo();
        User toFind = new User("ana","ana",0);

        User found = userRepo.findUser(toFind);
        assertEquals(found, toFind);

        toFind = new User("dadaade","dadae",1);
        found=userRepo.findUser(toFind);

        assertEquals(found,null);
    }

    @Test
    public void findByUsername() {
        hibernateUtils.initialize();
        UserRepo userRepo = new UserRepo();
        User toFind = new User("ana","ana",0);

        User found = userRepo.findByUsername(toFind);
        assertEquals(found,toFind);

        found = userRepo.findByUsername(new User("dada","dede",0));
        assertEquals(found,null);

    }

    @Test
    public void saveUser() throws Exception {
        hibernateUtils.initialize();
        UserRepo userRepo = new UserRepo();
        userRepo.saveUser(new User("test","test",0));

    }


}