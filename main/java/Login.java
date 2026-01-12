import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/login")
public class Login extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Login servlet called");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            
            // SQL Injection Vulnerability - directly concatenating user input
            String query = "SELECT * FROM users WHERE username = '" + username + 
            "' AND password = '" + password + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                response.sendRedirect("home.html");
            } else {
                response.sendRedirect("fail.html");
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h2>Error occurred!</h2>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
            response.getWriter().println("<a href='index.html'>Back to Login</a>");
            response.getWriter().println("</body></html>");
            e.printStackTrace();
        }
    }
}
