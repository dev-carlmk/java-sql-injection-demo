import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RecommendedSourceCode extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "root", ""
            );

            // ✅ SAFE QUERY
            String query = "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                response.getWriter().println("Login Successful (Secure System)");
            } else {
                response.getWriter().println("Login Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
