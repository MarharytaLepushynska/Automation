import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@WebServlet(name = "ComputerInfoServlet", value = "/cоmputer-info-servlet")
public class ConputerInfoServlet extends HttpServlet {
    private String message;

    public void init() {
        StringBuilder sb = new StringBuilder();
        OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
        sb.append("<h1>" + "Info about computer where server is running" + "</h1>");
        sb.append("<h3>" + "Name of OS: " + osmxb.getName()  + "</h3>");
        sb.append("<h3>" +"Version: " + osmxb.getVersion()  + "</h3>");
        sb.append("<h3>" +"Architecture: " + osmxb.getArch()  + "</h3>");
        sb.append("<h3>" +"Available Processors: " + osmxb.getAvailableProcessors()  + "</h3>");
        message = sb.toString();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(message);
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
