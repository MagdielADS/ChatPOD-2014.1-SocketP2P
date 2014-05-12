/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ifpb.edu.pod.chat.socket;

import ifpb.edu.pod.chat.dao.UserChatDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author magdiel-bruno
 */
public class ChatServer {
    static ServerSocket server;
    static Socket socket;
    static int qtdeUser;
    
    public ChatServer(int port){
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
        }
        
        System.out.println("POD-Chat-Server-Message> Waiting for connections...");
        while(true){
            try {
                socket = server.accept();
            } catch (IOException ex) {
            }

            Thread t = new Thread(new ChatServerRunnable());
            t.start();
        }
        
    }
    
    public boolean validateLogin(String login, String pass){
        UserChatDAO udb = new UserChatDAO();
        if(udb.findUser(login, pass)!= null){
            return true;
        }
        return false;
    }
    
    public class ChatServerRunnable implements Runnable{
        InputStreamReader input;
        
        public ChatServerRunnable(){
        }
        
        public void run() {
            String text;
            try {
                

                while(true){
                    input = new InputStreamReader(socket.getInputStream());
                    PrintWriter write = new PrintWriter(socket.getOutputStream());
                    BufferedReader buff = new BufferedReader(input);
                    
                    text = buff.readLine();
                    String[] texts = text.split("@");
                    
                    if (texts[0].equalsIgnoreCase("-l")) {
                        String[] log = texts[1].split("&");
                        if (validateLogin(log[0], log[1])) {
                            qtdeUser++;
                            write.println("OK");
                            write.flush();
                         } else {
                            System.out.println("POD-Chat-Server-Message> "+log[0]+ " tried to connect, but username or "
                                    + "password not exist");
                            write.println("NOT OK");
                            write.flush();
                        }
                    } else if (texts[0].equalsIgnoreCase("-m")) {
                        String[] msg = texts[1].split("&");
                        System.out.println("POD-Chat-" + msg[0] + "-Message> " + msg[1]);
                        write.println(msg[1]);
                        write.flush();
                    } else {
                        write.println("Invalid communication protocol");
                        write.flush();
                        System.out.println("POD-Chat-Server-Message> Invalid communication protocol");
                    }
                }

            } catch (IOException ex) {
                try {
                    input.close();
                    socket.close();
                    return;
                } catch (IOException ex1) {
                }                
            }
        }   
    }
    
    public static void main(String[] args) {
        ChatServer cs = new ChatServer(10999);
    }
}
