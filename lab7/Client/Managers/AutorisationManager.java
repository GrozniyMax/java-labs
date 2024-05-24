package Managers;


import CommonClasses.Interaction.UserAuthData;

import Input.ConsoleInputManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class AutorisationManager {

    private boolean isRegistering;
    private String login;
    private String passwd;


    private void readLogin(){
        System.out.print("Введите логин: ");
        String read = ConsoleInputManager.getInstance().readLine();
        login = Objects.requireNonNull(read);
    }
    private void readPassword(){
        System.out.print("Введите пароль: ");
        String read = String.valueOf(ConsoleInputManager.getInstance().readLine());
        passwd = Objects.requireNonNull(read);
    }

    public void login(){
        boolean done=false;
        boolean step1 = false;
        boolean step2 = false;
        while (!done){
            try {
                if (!step1) {
                    String r = ConsoleInputManager.getInstance().readLine("Регистрация нового пользователя? Если да, то введите Р ");
                    isRegistering = r.equalsIgnoreCase("Р") || r.equalsIgnoreCase("регистрация");
                    step1=true;
                }
                if (!step2) readLogin();
                step2=true;
                readPassword();
                done=true;
            }catch (Throwable throwable){
                continue;
            }
        }
    }

    public UserAuthData packToRequest(){
        try {
            MessageDigest ms = MessageDigest.getInstance("SHA-384");
            byte[] p = ms.digest(passwd.getBytes("UTF-8"));
            return new UserAuthData(login,p);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRegistering() {
        return isRegistering;
    }
}
