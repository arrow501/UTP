import java.util.*;
import java.net.*;
import java.io.*;

class Server {

    public static void main(String[] args) throws IOException{
        String absolute = new File(".").getAbsolutePath();
        String path = "files";
        String current = absolute+"/"+path;
        int port = Integer.parseInt(args[0]);
        File fileDir = new File(current);
        File[] filenames = fileDir.listFiles(); ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        /*
            Uwagi:

            1. Trzeba zamknąć printwritera, zanim czytamy z bufferedReadera (w przeciwnym razie program się zawiesza)
            2. Polecam BufferedWriter zamiast PrintWritera (nie trzeba używać flusha)
            3. Za dużo ifów, można to zrobić w jednym ifie z else ifami albo najlepiej w switchu!
            4. a co jeśli klient wyśle komendę, która nie jest obsługiwana? (np. "GET LIST")
         */
        while (true) {
            String line = in.readLine();
            if (line!=null){
                String[] strings = line.split(" ");
                if (strings[0].equals("FILE") && strings[1].equals("LIST")&&filenames!=null) {
                    StringBuilder listoftxt= new StringBuilder();
                    for (File file : filenames) {
                        if(file.isFile() && file.getName().endsWith(".txt")){
                            listoftxt.append(file.getName()).append("\n");
                        }
                    }
                    pw.println(String.valueOf(listoftxt)); // wystarczy pw.println(listoftxt);
                } else if (strings[0].equals("GET")&&filenames!=null) {
                    boolean exists = false;
                    for (File file : filenames) {
                        if (strings[1].equals(file.getName())) {
                            exists=true;
                        }
                    }
                    if (exists){
                        FileInputStream fileInputStream = new FileInputStream(current+strings[1]);
                        String s = new String(fileInputStream.readAllBytes());
                        pw.println(s);
                    }else{ // może to powininno być w else if?
                        pw.println("NO SUCH FILE");
                    }  else { // else bez ifa
                        pw.println("NO SUCH COMMAND");}
                }else{
                    socket.close();
                    break;
                }
            }
        }
    }