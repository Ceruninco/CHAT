package com.company;

import java.util.Date;

public class Message {
    String Expediteur;
    String Destinataire;
    String Contenu;
    Date date;
    public Message(String Expediteur, String Destinataire, String Contenu, Date date){
        this.Expediteur = Expediteur;
        this.Destinataire = Destinataire;
        this.Contenu = Contenu;
        this.date = date;
    }

    public String toString(){
        return date+" "+Contenu;
    }
}
