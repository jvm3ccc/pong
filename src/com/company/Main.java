package com.company;

import java.io.*;
import java.net.Socket;

public class Main {

    private static int moveVal = 0;
    private static String[] playerPos;

    public static void main(String[] args) {
	// write your code here

        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws IOException {
        Socket s = null;

        int serverPort = 7000;
        String ip = "localhost";

        s = new Socket(ip, serverPort);

        OutputStream os = s.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        PrintWriter bw = new PrintWriter(osw);

        String number = "-2";

        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);

            String[] lineTmp = line.split(" ");

            // Speichere Player Line zwischen
            if(lineTmp[0].equals("player")) {
                playerPos = lineTmp;
            }

            // Fang ball ab und schreibe zweite und dritte Stelle in eine Variable
            if(lineTmp[0].equals("ball")) {
                extractBall(lineTmp);
            }

            if (line.equals("update")){
                bw.println("move " + moveVal);
                bw.flush();
                System.out.println("move " + moveVal);
            }
        }

    }


    private static void extractBall(String[] line) {
        // Windowgroesse: 800x600
        // top-left ball: 0, 0

        // Position des Balles
        double posX = Double.parseDouble(line[1]);
        double posY = Double.parseDouble(line[2]);

        // Geschwindigkeit des Balles
        double velX = Double.parseDouble(line[3]);
        double velY = Double.parseDouble(line[4]);

        // Position Player
        double playerX = Double.parseDouble(playerPos[1]);
        double playerY = Double.parseDouble(playerPos[2]);

        // Finde heraus an welcher Position das eigene Paddle ist
        double diff = playerY - posY;
        if( diff < -500 ) {
            moveVal = 20;
        } else if ( diff < -250 && diff >= -500) {
            moveVal = 12;
        } else if ( diff < -125 && diff >= -250) {
            moveVal = 6;
        } else if ( diff < 0 && diff >= -125 ) {
            moveVal = 3;
        } else if ( diff == 0) {
            moveVal = 0;
        } else if ( diff > 0 && diff <= 125) {
            moveVal = -3;
        } else if ( diff > 125 && diff <= 250) {
            moveVal = -6;
        } else if ( diff > 250 && diff <= 500) {
            moveVal = -12;
        } else if ( diff > 500) {
            moveVal = -20;
        }
        System.out.println("DIFFERENZ: " + diff);

        // -36 SCHNELL nach oben
        // -10 langsam nach oben
        // 10 langsam runter
        // 36 SCHNELL runter
    }
}
