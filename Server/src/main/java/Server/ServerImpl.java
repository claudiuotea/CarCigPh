package Server;

import Domain.*;
import Repositories.RaspunsRepo;
import Repositories.UserRepo;
import ServiceInterface.Observer;
import ServiceInterface.Services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

public class ServerImpl implements Services {
    private UserRepo userRepo;
    private RaspunsRepo raspunsRepo;
    private Map<String, Observer> loggedClients;
    private Map<Observer,Raspuns> raspunsuri;
    private ArrayList<Character> characters;
    char nextLetter;

    public ServerImpl(UserRepo userRepo, RaspunsRepo raspunsRepo) {
        this.userRepo = userRepo;
        this.raspunsRepo = raspunsRepo;
        loggedClients = new HashMap<>();
        raspunsuri = new HashMap<>();
        characters = new ArrayList<>();
        initializeCharacters();
        nextLetter = 'a';
    }

    private void initializeCharacters() {
        characters.add('a'); //a b c d f g h   k l m n o p r s t v w
        characters.add('b');
        characters.add('c');
        characters.add('d');
        characters.add('f');
        characters.add('h');
        characters.add('k');
        characters.add('l');
        characters.add('m');
        characters.add('n');
        characters.add('o');
        characters.add('p');
        characters.add('r');
        characters.add('s');
        characters.add('t');
        characters.add('v');
        characters.add('w');
        characters.add('g');
    }

    @Override
    public synchronized User login(User user, Observer client) throws Exception {
        User userResult = null;
        System.out.println("ServerImpl sends to repo" + user.getUsername() + " + " +user.getPassword());
        userResult =  userRepo.findUser(user);
        System.out.println("ServerImpl got from repo the result: " + userResult.getUsername() + " -- " + userResult.getPassword());
        if(userResult!=null)
        {
            //acum verific daca e deja in logged clients, daca e logat
            if(loggedClients.get(user.getUsername())!=null)
                throw new Exception("User already logged in!");
            loggedClients.put(user.getUsername(),client);
            return userResult;
        }
        throw new Exception("Authentication failed!");
    }

    @Override
    public void logout(User user, Observer client) throws Exception {
        if(loggedClients.get(user.getUsername()) != null){
            loggedClients.remove(user.getUsername());
        }
        return;
    }

    @Override
    public void register(User user) throws Exception {
        User userResult = null;
        userResult =  userRepo.findByUsername(user);
        if(userResult == null){
            userRepo.saveUser(user);
        }
        else
            throw new Exception("User already registered!");


    }

    /*
    Send a random letter to all the players, clear all the answers from the last round
     */
    @Override
    public void startGame() throws Exception {
        if(loggedClients.size() >= 2) {
            randomLetter();
            raspunsuri.clear();
            for (Observer o : loggedClients.values()) {
                o.receiveLetter(nextLetter);
                o.disableStart();
                o.clearTextbox();
            }
        }
        else
        {
            throw new Exception("Not enough players, need at least" + (2 - loggedClients.size()) + " more!");
        }
    }

    /*
    Save the answers in a dictionary. If everybody sent the answer, compute the points
     */
    @Override
    public synchronized void checkAnswer(Observer o, Raspuns raspuns) throws SQLException, RemoteException {
        raspunsuri.put(o,raspuns);
        if(raspunsuri.size() == loggedClients.size()){
            computePoints();
        }
    }

    /*
    Update
     */
    private void randomLetter() {
        Collections.shuffle(characters);
        Random random = new Random();
        nextLetter =  characters.get(random.nextInt(5));
    }


    /*
    Check if the given answers exists, or if they start with the given letter
     */
    boolean checkIfCarExist(Raspuns r) {
        boolean isValid = raspunsRepo.checkExistenceMasina(new BrandMasina(r.getMasina()));

        if (!isValid || r.getMasina().charAt(0) != Character.toUpperCase(nextLetter) ||r.getMasina().equals(""))
            return false;

        return true;
    }

    /*
    Check if the given answers exists, or if they start with the given letter
     */
    boolean checkIfCigarettesExists(Raspuns r) {
        boolean isValid = raspunsRepo.checkExistenceTigari(new BrandTigari(r.getTigari()));

        if (!isValid || r.getTigari().charAt(0) != Character.toUpperCase(nextLetter) ||r.getTigari().equals(""))
            return false;

        return true;
    }

    /*
    Check if the given answers exists, or if they start with the given letter
     */
    boolean checkIfPhoneExists(Raspuns r) {
        boolean isValid = raspunsRepo.checkExistenceTelefon(new BrandTelefon(r.getTelefon()));

        if (!isValid || r.getTelefon().charAt(0) != Character.toUpperCase(nextLetter) ||r.getTelefon().equals(""))
            return false;

        return true;
    }


    private void computePoints() throws RemoteException {
        for(Observer o: raspunsuri.keySet()){

            Raspuns r = raspunsuri.get(o);
            //10 * (nr de playeri - 1) tot inmultit cu 3 [3 raspunsuri].
            int punctaj = 10 * (loggedClients.size() - 1) * 3;

            //which answer is valid to be checked with others
            boolean phoneExists = checkIfPhoneExists(r);
            boolean cigarettesExists = checkIfCigarettesExists(r);
            boolean carExists = checkIfCarExist(r);

            //for each invalid answer there are 0 given points,so we have to substract
            if(!phoneExists)
                punctaj -= 10 * (loggedClients.size() - 1);
            if(!carExists)
                punctaj -= 10 * (loggedClients.size() - 1);
            if(!cigarettesExists)
                punctaj -= 10 * (loggedClients.size() - 1);

            for(Raspuns rasp:raspunsuri.values()){
                if(rasp.getId() != r.getId()){
                    if(rasp.getTelefon().equals(r.getTelefon()) && phoneExists)
                        punctaj -= 10;
                    if(rasp.getMasina().equals(r.getMasina()) && carExists)
                        punctaj -= 10;
                    if(rasp.getTigari().equals(r.getTigari()) && cigarettesExists)
                        punctaj -= 10;
                }
            }


            o.receivePunctaj(punctaj);
            o.enableStart();
        }
    }
}
