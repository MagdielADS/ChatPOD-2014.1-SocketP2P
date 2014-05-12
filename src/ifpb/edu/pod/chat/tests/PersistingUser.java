/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ifpb.edu.pod.chat.tests;

import ifpb.edu.pod.chat.dao.UserChatDAO;
import ifpb.edu.pod.chat.model.UserChat;

/**
 *
 * @author magdiel-bruno
 */
public class PersistingUser {
    public static void main(String[] args) {
        UserChatDAO udb = new UserChatDAO();
        UserChat user = new UserChat();
        
        user.setLogin("arigarcia");
        user.setPassword("123456");
        udb.persist(user);
        
        user.setLogin("fernan");
        user.setPassword("123456");
        udb.persist(user);
        
        user.setLogin("fernan");
        user.setPassword("123456");
        udb.persist(user);
        
        //System.out.println(udb.findUser("arigarcia", "123456"));
    }
}
