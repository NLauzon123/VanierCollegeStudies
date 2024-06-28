import java.io.*;
import java.util.*;
public class FileIOTests {
    public static void main(String[] args) {
        try { doIO();}
        catch (IOException e) {System.out.println(e.getMessage()); System.out.println("We have an exception.");}
    }
    public static void doIO() throws IOException {
        File file = new File("scores.txt");
        if (file.exists()) {
            System.out.println("File already exists; delete and recreate.");
            file.delete();
            file = new File("scores.txt");
        }
        System.out.println("Using PrintWriter");
        PrintWriter p = new PrintWriter(file);
        p.print("Mango aplenty this season");
        p.println(" cannot wait to eat them");
        p.print("Nicolas");
        p.println(", great consumer of fruits, as always.");
        p.close();
        System.out.println("Using BufferedWriter");
        BufferedWriter br = new BufferedWriter(new FileWriter(file,true));
        br.write("Adding further to the file, nothing related to mangoes.");
        br.write("\r\nWriting further herein.");
        br.close();
        System.out.println("Using OutputStream and Writer");
        Writer w = new OutputStreamWriter(new FileOutputStream(file,true));
        w.write("\r\nLet's move on to oranges.");
        w.write("\r\nThey are just as good as mangoes.");
        w.close();
        System.out.println("\nUsing BufferedReader");
        BufferedReader br1 = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br1.readLine()) != null) {System.out.println(s);}
        br1.close();
        System.out.println("\nUsing Scanner");
        Scanner sc = new Scanner(new FileReader(file));
        while (sc.hasNextLine()) {System.out.println(sc.nextLine());}
        //try {while ((s = sc.nextLine()) != null) {System.out.println(s);}}
        //catch (NoSuchElementException e) {}
        sc.close();
        System.out.println("\nUsing InputStreamer and Reader");
        Reader r = new InputStreamReader(new FileInputStream(file));
        int ichar;
        while ((ichar = r.read()) != -1) {System.out.print((char)ichar);}
        r.close();
    }
}