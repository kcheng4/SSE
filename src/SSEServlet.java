import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/SSEServlet")
public class SSEServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    int notif=0;
    String stringtime="No notifications";
    String message = new String();
    String tempmes = null;
    String datamessage=new String();
    int flag=0;
    Date time=new Date();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("event: Notif\n");
        writer.write("data:Notifications:");
        writer.write(String.valueOf(notif) + "\n\n");

        writer.write("event: Time\n");
        writer.write("data:Last Notification on:\n");
        writer.write("data:" + stringtime + "\n\n");

        //Runs through one more time than needed
        if (message != null && !message.startsWith("data:data:")) {

            message = "data:" + message;
            datamessage=message;
            if (message.startsWith("data:data:")) {
                datamessage=null;
            }
                tempmes = tempmes + datamessage;
                //tempmes=message;
                String mes;
                mes = String.valueOf(tempmes);
                if (mes.startsWith("data:") == false) {
                    writer.write("event: Message\n");
                    //writer.write("data:" + tempmes + "\n\n");
                    writer.write(tempmes + "\n\n");
                    tempmes = tempmes + "\n";

                }

        }
    }


        /* PrintWriter writer = response.getWriter();
            writer.write("Notifications:" + String.valueOf(notif) +"\n\n" );*/





       /* PrintWriter printWriter;
        printWriter = null;
        response.getWriter().println("Does it work?");
        response.getWriter().flush();
        for (int i=0;i<1000;i++){

                response.getWriter().println("Does it work?");
                response.getWriter().flush();
                double rand = Math.random()*10000;
                printWriter = response.getWriter();
                printWriter.write("Data: " + "[Next server check in " + Math.round(rand / 1000) + " seconds]\n\n");
                printWriter.write("Data: " + "Time: " + Calendar.getInstance().getTime() + " \n\n");
                //response.flushBuffer();
            try {
                Thread.sleep((long)rand);
            } catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
        printWriter.close();*/


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String site = "http://localhost:8078/SSE.html" ;
        message=request.getParameter("textMessage");
        if (request.getParameter("clear")!=null){
            notif=0;
        }
        else if(request.getParameter("sub")!=null) {
            notif=notif+1;
        }

        time = Calendar.getInstance().getTime();
        stringtime = time.toString();
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location",site);

    }

}
/*
if (message!= null) {
        message="data:"+message;
        tempmes = tempmes + message;
        tempmes=message;
        String mes;
        mes = String.valueOf(tempmes);
        //if(mes.startsWith("data:data")==false) {
        writer.write("event: Message\n");
        //writer.write("data:" + tempmes + "\n\n");
        writer.write(tempmes + "\n\n");
        tempmes = tempmes + "\n";
        }
        }*/