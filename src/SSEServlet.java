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
               
                String mes;
                mes = String.valueOf(tempmes);
                if (mes.startsWith("data:") == false) {
                    writer.write("event: Message\n");
                    writer.write(tempmes + "\n\n");
                    tempmes = tempmes + "\n";

                }

        }
    }


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
