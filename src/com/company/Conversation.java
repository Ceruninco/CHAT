package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.sql.*;
import java.util.UUID;

public class Conversation{
    LinkedList <String> participants;
    ArrayList<Message> messages;
    auth pageauth;
    public String adresseBD;
    public String nomLogin;
    public String mdp;
    private Connection conn;
    public String idConv;

    public Conversation(LinkedList<String> participants, auth auth){
        idConv = UUID.randomUUID().toString();
        System.out.println(idConv);
        this.participants = participants;
        this.pageauth = auth;
        this.adresseBD = auth.adresseBD;
        this.nomLogin = auth.nomLogin;
        this.mdp = auth.mdp;
    }

    public Conversation(auth auth){
        this.pageauth = auth;
        this.adresseBD = auth.adresseBD;
        this.nomLogin = auth.nomLogin;
        this.mdp = auth.mdp;
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void addParticipant(String participant){
        participants.add(participant);
    }


    //Méthode qui crée le message, l'ajoute à la conversation et l'envoi sur la BD
    public void traitementMessage(String message, String username, String destinataire){
        Date date = new Date();
        Message mess = new Message(username,destinataire, message, date);
        addMessage(mess);
        sendMessage(mess);
    }

    //Envoie le message à la base de données
    public void sendMessage(Message message) {
        System.out.println("test");
        String uniqueID = UUID.randomUUID().toString();

        try {
            String usedb = "use H7wytURoJ0";
            String sqlStr = "INSERT INTO Messages (idMessage, Expediteur, Destinataire, Contenu, Date) VALUES ('"+uniqueID+"', '"+message.Expediteur+"','"+message.Destinataire+"','"+message.Contenu+"','"+message.date.toString()+"')";
            String sqlStr2 = "INSERT Conversations (idConv, idMessage) VALUES ('"+idConv+"', '"+uniqueID+"')";
            Statement stmt = conn.createStatement();
            //execution de la requete
            stmt.executeQuery(usedb);
            stmt.executeUpdate(sqlStr);
            stmt.executeUpdate(sqlStr2);
        }
        catch(Exception e){
            System.out.println("merde");
            System.out.println(e.getMessage());
        }
    }

    //crée la connection à la base de données
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

}
