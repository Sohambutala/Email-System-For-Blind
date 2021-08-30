package emailsystem;
//Imports
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Locale;
//Speech Imports
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.EngineStateException;
//Mail Imports
import javax.mail.PasswordAuthentication;  
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
//Main class
public class Emailsystem extends JFrame
{        static String user;
         static String pass;
         static String keypass;
         static String Recorded = "Recorded.txt";
         static JFrame frame = new JFrame(); 
         static JFrame full = new JFrame();
         static int length = 0;
         static String str = null;
         static String heard = null;
         static Record r = new Record() {};
         static String host = "pop.gmail.com"; // change accordingly
         static String mailStoreType = "pop3";
         static File recorded = new File("Recorded.txt");
         static boolean done ;
         static int i;
         //main method
    public static void main(String[] args) throws IOException, ClassNotFoundException, MessagingException, InterruptedException, AudioException, EngineException, NullPointerException, ArrayIndexOutOfBoundsException
    {   //Run the Full Screen Frame
         fullframe();
         recorded.createNewFile();
         File file = new File("C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Accessories/Accessibility/Windows Speech Recognition.lnk");
         System.getProperty(File.separator);
         //Check if program is run first time or not.
         File first = new File("Check.txt");
         done = first.exists();
         if(done==true)
         {
         try
         {  FileInputStream fstream=new FileInputStream("Check.txt");
             try (BufferedReader in = new BufferedReader(new InputStreamReader(fstream))) 
             {
             while(in.ready())
             {
             str = in.readLine();
             }
             }
             }
             catch(IOException e)
             {
             System.out.println(e);
             }
         i = Integer.parseInt(str);  
         if(i==1)
         {
         retrieve();
         i=0;
         while(r.passes[i]!=null)
         {
             i++;
             length++;
         }
         }
         else
         length=0;
         }
         else
         {
             first.createNewFile();
             FileWriter fw = new FileWriter(first.getAbsoluteFile());
             try (BufferedWriter bw = new BufferedWriter(fw)) 
             {
             bw.write("0");
             }
             length=0;
         }
         speak("Welcome");
         Desktop.getDesktop().open(file);
         str=" ";
         //Main Menu
         while(!str.equals("exit"))
         {   
             speak("what would you like to do ");       
             speak("Register");
             speak("Login");
             speak("Exit");
             empty(recorded);
             hear();
             Thread.sleep(1000);
             str = heard;
             empty(recorded);
             switch (str)
         {
             case "login":
                 speak("Enter username");
                 hear();
                 Thread.sleep(10000);
                 confirm();
                 Thread.sleep(1000);
                 user = heard+"@gmail.com";
                 user.replaceAll("\\s","");
                 done = login();
                 if(done == true)
                 {   
                 speak("Login Confirmed");
                 empty(recorded);
                 str="";
                 speak("Menu:");
      //MENU STARTS
        while (!str.equals("log out"))
         {   
             speak("What would you like to do");
             speak("Read");
             speak("Compose");
             speak("Logout");
             empty(recorded);
             hear();
             str = heard;
             Thread.sleep(1000);
             switch (str)    
             {
                 case "read":
                     readmail();
                     break;
                 case "compose":
                     empty(recorded);                                                                                                                                                                                                                            
                     String us,sub,text;
                     speak("Dictate the username of recipient");
                     hear();
                     Thread.sleep(10000);//
                     confirm();
                     Thread.sleep(1000);
                     us = heard;
                     us=us+"@gmail.com";
                     us.replaceAll("\\s","");
                     empty(recorded);
                     speak("Dictate the subject");
                     hear();
                     Thread.sleep(10000);
                     confirm();
                     Thread.sleep(1000);
                     sub = heard;
                     empty(recorded);
                     speak("Dictate the text");
                     hear();
                     Thread.sleep(10000);
                     confirm();
                     Thread.sleep(1000);
                     empty(recorded);
                     text=heard;     
                     send(sub,us,text);
                     break;
                 case "log out":
                     user="";
                     pass="";
                     keypass="";
                     speak("Logout Conifrmed");
                     break;
                 default:
                     speak("Invalid Input.");
                     speak("Try Again");
                     break;
             }
         } 
                 break;
                 }
                 else
                 {
                 speak("Log In Failed.");
                 speak("Try again ");
                 break;
                 }
             case "register":
                 speak("Enter username");
                 hear();
                 confirm();
                 user = heard;
                 user=user+"@gmail.com";
                 user.replaceAll("\\s","");
                 empty(recorded);
                 speak("Enter password");
                 hear();
                 confirm();
                 pass = heard;
                 Thread.sleep(10000);
                 done = register();
                 if(done==true)
                 speak("Registeration done");
                 else
                 {
                 speak("Registeration unsuccessful.");
                 speak("Please try again");
                 }
                 empty(recorded);
                 break;
             case "exit":
                 speak("System is exiting");
                 Thread.sleep(2000);
                 System.exit(0);
                 break;
             default:
                 speak("Invalid Input try again");
                 break;
         }
         }
    }
    //Registeration function
    public static boolean register() throws IOException, ClassNotFoundException, InterruptedException
 {  
     speak("Type your new password");
     //RUN FRAME;
     jpass();
     Thread.sleep(10000);
     r.passes[length]=user;
     r.passes[length+1]= pass;
     r.passes[length+2]= keypass;
     save();
     Thread.sleep(1000);
     done = true;
     return done;
 }
    //Login 
     public static boolean login() throws IOException, ClassNotFoundException, InterruptedException
 {   
     done=false;
     speak("Enter password: ");
     jpass();
     Thread.sleep(10000);
     for( i=0; done!=true && i<=length ;i++)
     {  if(keypass.equals(r.passes[i+2]) && user.equals(r.passes[i]))
      {  
         pass = r.passes[i+1];
         done = true;
      }
        else
      {  
         done = false;
      }
     }
     return done;
 }
    ///Save the accounts
     static void save() throws IOException 
 {          File f = new File("Check.txt");
      try (FileOutputStream fileOut = new FileOutputStream("Registered.ser")) 
        {
             FileWriter fw = new FileWriter(f.getAbsoluteFile());
             try (BufferedWriter bw = new BufferedWriter(fw)) 
             {
             bw.write("1");
             }            
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(r);
        }
        catch(Exception e)
        {    System.out.println(e);}
 }
    //Retrieve the accounts 
    static void retrieve() throws IOException, ClassNotFoundException
{  try
      {
     try (FileInputStream fileIn = new FileInputStream("Registered.ser"); ObjectInputStream in = new ObjectInputStream(fileIn)) 
     {
         r = (Record)in.readObject();
     }
      }
    catch(IOException | ClassNotFoundException e)
     {   System.out.println(e); }
}
    //Speak to user
static void speak(String str) throws InterruptedException
    {    try
         {
         System.setProperty("freetts.voices",
         "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
         Central.registerEngineCentral
         ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
         Synthesizer  synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
         synthesizer.allocate();
         synthesizer.resume();
         synthesizer.speakPlainText(str,null);
         }
         catch(AudioException | EngineException | EngineStateException | SecurityException e )
         {
         System.out.println(e);
         }
    }   
    //Hear what user says
    static void hear() throws InterruptedException
    {   
        heard=null;
        try {Desktop.getDesktop().open(new File(Recorded));
             Thread.sleep(20000);
             while(heard==null)
             {
             try
                {          
                FileInputStream fstream=new FileInputStream(Recorded);
                try (BufferedReader in = new BufferedReader(new InputStreamReader(fstream))) 
                {
                while(in.ready())
                {
                heard = in.readLine();
                }
                }
                }
                catch(IOException e)
                {
                System.out.println(e);
                }  
             heard=heard.toLowerCase();
             }             
             }
        catch (IOException e)
           { 
             System.out.println(e);
           }   
    }
    //empty the recordings
    public static void empty(File file) throws IOException
    {
        file.delete();
        file.createNewFile();
    }
    //Confirm for what user has said
    static void confirm() throws InterruptedException, IOException
    { 
        empty(recorded);
        String ans;
        String temp;
        if(heard!=null)
        {       
        speak("You spoke:");
        speak(heard);
        temp=heard;
        speak("Do you want to change it?");
        hear();
        ans = heard;
        empty(recorded);
        while(ans!=null)
         switch(ans)
        {
            case "no":
                ans=null;
                heard=temp;
                empty(recorded);
                break;
            case "yes":
                speak("Say");
                hear();
                ans=null;
                empty(recorded);
                break;
            default:
                speak("Invalid Option");
                ans=null;
                break;
         }              
    }
        
    }
    //Frame for Password Field
    static void jpass()  throws InterruptedException 
            {   JPasswordField p = new JPasswordField();
                frame.setVisible(false);
                Thread.sleep(500);
                frame.setSize(300,300);
                frame.add(p);     
                keypass=null;
                frame.setVisible(true);
                frame.setAlwaysOnTop(true);
                frame.requestFocusInWindow();
                p.requestFocusInWindow();
                while(keypass==null)
                {
                p.addActionListener((ActionEvent ae) -> 
                {
                char[] temp;
                if(ae.getSource() == p)
                    {   temp = p.getPassword();
                        keypass = String.valueOf(temp);
                        
                    }
                
                frame.setVisible(false);
                });
                }
            }
    //Send Mail function
    public static void send(String subject, String to, String Text) throws InterruptedException
 {
     try
     {
  Properties props = new Properties();  
  props.put("mail.smtp.host", "smtp.gmail.com");  
  props.put("mail.smtp.socketFactory.port", "465");  
  props.put("mail.smtp.socketFactory.class",  
            "javax.net.ssl.SSLSocketFactory");  
  props.put("mail.smtp.auth", "true");  
  props.put("mail.smtp.port", "465");  
  Session session = Session.getDefaultInstance(props,  
   new javax.mail.Authenticator() 
   {  
   @Override
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,pass);//change accordingly  
   }  
  });  
  //compose message  
   MimeMessage message = new MimeMessage(session);  
   message.setFrom(new InternetAddress(user));//change accordingly  
   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   message.setSubject(subject);  
   message.setText(Text);  
   //send message  
   Transport.send(message);  
   Emailsystem.speak("message has been sent successfully");
  } 
     catch (MessagingException e) 
  {
      speak("Message sending unsuccessful");
      System.out.println(e);   
  }  
 }
    //Read the mail recieved
    public static void readmail() throws MessagingException, IOException, InterruptedException 
   {   try 
      {
      //create properties field
      Properties properties = new Properties();
      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.port", "995");
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");
      store.connect(host, user, pass);
      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);
      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println(messages.length);
      outer: for (int i = 0; i<messages.length ; i++) 
      {  Message message = messages[i];
         done = false;
         while(done!= true)
         {speak(""+message.getFrom()[0]);        
         speak("Do you want to hear this message");
         hear();
         Thread.sleep(10000);
         if("yes".equals(heard))
         {
            speak(""+Arrays.toString((message.getFrom())));
            speak(""+message.getSubject());
            speak(""+message.getContent().toString());
            break outer;
         }
         else if("no".equals(heard))
         {
           done=true;
         }
         else
         {
             done = false;
             Emailsystem.speak("invalid command");
         }         
      }
      }
      emailFolder.close(false);
      store.close();
     }  
      catch (NoSuchProviderException e) 
      {
          System.out.println(e);
      } 
   }
     static Properties getServerProperties(String protocol, String host,
            String port) {
        Properties properties = new Properties();
 
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
 
        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));
 
        return properties;
    }
    //Fullscreen ideal frame
    public static void fullframe()
    {
        full.setVisible(true);
        full.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel label = new JLabel("WELCOME",JLabel.CENTER);
        full.add(label);
    }
}  
//Class for storage
     class Record  implements Serializable
{
     String[] passes = new String[100];
}

  