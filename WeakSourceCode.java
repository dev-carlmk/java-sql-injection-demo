import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class WeakSourceCode extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "root", ""
            );

            Statement st = connection.createStatement();

            // ❌ SQL INJECTION VULNERABILITY
            String query = "SELECT * FROM users WHERE username='"
                    + username + "' AND password='" + password + "'";

            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                response.getWriter().println("Login Successful (Weak System)");
            } else {
                response.getWriter().println("Login Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
