package com.mstem.virusshootergame;

import com.mstem.virusshootergame.Target;

import java.util.Random;


/**
 * Created by catherinehuang on 4/15/15.
 */
public class TargetMessage {

    String[] adwareMessage = {"Adware is commonly known as software that is embedded with advertisements.",
                                "Adware may be consider a spyware if it collects information about the user.",
                                "Some adware may track your Internet surfing habits in order to serve ads related to you."};
    String[] spywareMessage = {"Spyware monitors user activity on the Internet and transmits that information in the background to someone else.",
                            "A common way to become a victim of spyware is to download peer-to-peer file that are available today.",
                            "Spyware uses computer's memory and resources to perform task, which can lead to system crashes."};

    private String message = "";

    public TargetMessage() {

    }

    public String randomPick(String name) {
        Random random = new Random();
        chooseFrom(name, random.nextInt(3));
        return message;

    }

    private void chooseFrom(String name, int i) {
        message ="";
        if(name.equals("adware")) {
            message += adwareMessage[i];
        }
        else if(name.equals("spyware")){
            message += spywareMessage[i];
        }
    }


}
