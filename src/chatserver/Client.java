/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Star
 */
class Client implements Runnable {
   Socket socket;
   Scanner in;
   PrintStream out;
   ChatServer server;

   public Client(Socket socket,ChatServer server){

       this.socket = socket;
       this.server=server;
       new Thread(this).start();
   }
   void receive(String message){
       out.println(message);
   }
   public void run() {
       try {
           // получаем потоки ввода и вывода
           InputStream is = socket.getInputStream();
           OutputStream os = socket.getOutputStream();

           // создаем удобные средства ввода и вывода
           in = new Scanner(is);
           out = new PrintStream(os);

           // читаем из сети и пишем в сеть
           out.println("Welcome to Chat!");
           String input = in.nextLine();
           while (!input.equals("bye")) {
               server.sendAll(input);
               input = in.nextLine();
           }
           socket.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}