/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ifpb.edu.pod.chat.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author magdiel-bruno
 */
public class ChatClient {
    
    public ChatClient(String ip, int port){
        Thread t = new Thread(new ChatClientRunnable(ip, port));
        t.start();
    }
    
    public class ChatClientRunnable implements Runnable{
        Scanner in;
        Socket socket;
        PrintWriter print;
        String ip, log, pass;
        int port;
        
        public ChatClientRunnable(String ip, int port){
            this.port = port;
            this.ip = ip;
        }

        public void run() {
            String msg = "";
            in = new Scanner(System.in);
            try {
                socket = new Socket(this.ip, this.port);
                PrintWriter print = new PrintWriter(socket.getOutputStream());
                InputStreamReader input = new InputStreamReader(socket.getInputStream());
                BufferedReader buff = new BufferedReader(input);
                
                System.out.println("POD-Chat-User-Message> Enter your Username : ");
                String log = in.nextLine();

                System.out.println("POD-Chat-User-Message> Enter your Password: ");
                String pass = in.nextLine();

                String control = "-l@" + log + "&" + pass;
                print.println(control);
                print.flush();
                
                System.out.println("POD-Chat-User-Message> Waiting for authentication...");
                String t = buff.readLine();
                
                if(t.equalsIgnoreCase("OK")){
                    System.out.println("POD-Chat-User-Message> Welcome on Chat POD");
                    while (!msg.equalsIgnoreCase("CLOSE")) {
                        msg = in.nextLine();
                        msg = msg.trim();
                        
                        if(msg.equalsIgnoreCase("CLOSE")){
                            System.out.println("POD-Chat-User-Message> Desconected");
                        }else if((msg != null) && (!msg.equalsIgnoreCase(""))){
                            control = "-m@" + log + "&" + msg;
                            print.println(control);
                            print.flush();
                            System.out.println("POD-Chat-User-Message> You: " + msg);
                        }else{
                            System.out.println("POD-Chat-User-Message> text message null or empty will not be sent");
                        }
                    }
                }else {
                    System.out.println("POD-Chat-User-Message> Username or password invalid");
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        ChatClient cc = new ChatClient("192.168.43.229", 10999);
    }
}