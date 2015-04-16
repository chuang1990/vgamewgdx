package com.mstem.virusshootergame;

import com.mstem.virusshootergame.Target;

import java.util.ArrayList;
//import java.util.Random;


/**
 * Created by catherinehuang on 4/15/15.
 */
public class TargetMessage {

    String[] adwareMessageT = {"Adware is commonly known as software that is embedded with advertisements.",
            "Adware may be consider a spyware if it collects information about the user.",
            "Some adware may track your Internet surfing habits in order to serve ads related to you."};
    String[] spywareMessageT = {"Spyware monitors user activity on the Internet and transmits that information in the background to someone else.",
                            "A  common way to become a victim of spyware is to download peer-to-peer file that are available today.",
                            "Spyware uses computer's memory and resources to perform task, which can lead to system crashes."};
    String[] adMessageT = {"Randomly click on website advertisement may lead you to harmful site that steal your web surfing information",
                            "Clicking on ads was 182 times more likely to install a virus on a user's computer than surfing the Internet for porn.",
                            ""};
    ArrayList<String> adwareMessage = new ArrayList<String>();
    ArrayList<String> spywareMessage = new ArrayList<String>();


    private String message = "";

    public TargetMessage() {
        for (int a = 0; a < 3; a ++) {
            for (int i = 0; i < adwareMessageT.length; i++) {
                adwareMessage.add(adwareMessageT[i]);
                spywareMessage.add(spywareMessageT[i]);
            }
        }
    }


    public String randomPick(String name, CollisionDetect cd) {
        chooseFrom(name, cd);
        return message;

    }

    private void chooseFrom(String name, CollisionDetect cd) {
        message ="";
        if(name == "adware" || name.equals("adware")) {
            int i = cd.getNumberOfHits()-1;
            message = adwareMessage.get(i);
        }
        else if(name == "spyware" || name.equals("spyware")){
            int i = cd.getNumberOfHits()-1;
            message += spywareMessage.get(i);
        }
//        else if(name == "runscan" || name.equals("runscan")){
//            int i = cd.getNumberOfHits()-1;
//            message =
//        }
    }

//    public static void main(String[] args) {
//        TargetMessage tm = new TargetMessage();
//        System.out.println(tm.randomPick("adware"));
//        System.out.println(tm.randomPick("spyware"));
//        System.out.println(tm.randomPick("spyware"));
//        System.out.println(tm.randomPick("spyware"));
//
//
//    }


}
