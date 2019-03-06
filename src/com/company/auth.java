package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class auth extends JFrame implements ActionListener {
    JTextField login;
    JPasswordField password;
    private Connection conn;
    public String adresseBD;
    public String nomLogin;
    public String mdp;


    public auth() {
        setLayout(null);
        setSize(230, 225);
        setLocation(300, 200);
        JPanel conteneurmain = new JPanel();
        JLabel log = new JLabel();
        log.setBounds(20, 20, 175, 20);
        log.setText("Entrez votre identifiant :");
        login = new JTextField();
        login.setBounds(20, 40, 175, 20);
        JLabel pass = new JLabel();
        pass.setBounds(20, 65, 175, 10);
        pass.setText("Entrez votre mot de passe :");
        password = new JPasswordField();
        password.setBounds(20, 85, 175, 20);
        JButton connect = new JButton();
        connect.setBounds(40, 115, 120, 50);
        connect.setText("Se connecter");
        connect.addActionListener(this);
        log.setVisible(true);
        login.setVisible(true);
        conteneurmain.setLayout(null);
        conteneurmain.add(log);
        conteneurmain.add(login);
        conteneurmain.add(pass);
        conteneurmain.add(password);
        conteneurmain.add(connect);
        conteneurmain.setVisible(true);
        setContentPane(conteneurmain);
        setVisible(true);
        adresseBD = "jdbc:mysql://www.remotemysql.com:3306";
        nomLogin = "H7wytURoJ0";
        mdp = "Zo6modkoVX";

    }

    public static String getSHA(String input) {

        try {

            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e2) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e2);

            return null;
        }


    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Nom d'utilisateur : "+login.getText());
        String hash = getSHA(password.getText()+login.getText());
        System.out.println("Mot de passe encrypté : "+hash);
        InterrogBD();

        System.out.println(authCorrecte(login.getText(), hash));

    }

    public void InterrogBD() {
        try {
            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouve...");
            //Creation d'une connexion à la base de donnees
            conn = DriverManager.getConnection(adresseBD, nomLogin, mdp);
            System.out.println("Connexion etablie...");
            System.out.println("");
        } catch(Exception e){
            //Affiche le message d'erreur si une erreur se produit durant la connexion
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void addUser(String username, String mdp) {
        try {
            String usedb = "use H7wytURoJ0";
            String sqlStr = "INSERT INTO Users (Username, Hash) VALUES ('"+username+"', '"+mdp+"')";
            Statement stmt = conn.createStatement();
            //execution de la requete
            stmt.executeQuery(usedb);
            stmt.executeUpdate(sqlStr);

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean authCorrecte(String username, String hash) {
        try {
            String usedb = "use H7wytURoJ0";
            String sqlStr = "select Hash FROM Users where Username = "+"\""+username+"\"";
            Statement stmt = conn.createStatement();
            //execution de la requete
            stmt.executeQuery(usedb);
            ResultSet res = stmt.executeQuery(sqlStr);
            res.next();
            if(res.getString(1).equals(hash)) {
                return true;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
