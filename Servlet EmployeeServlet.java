import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String empid = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companydb", "root", "password");

            String query;
            if (empid != null && !empid.isEmpty()) {
                query = "SELECT * FROM Employee WHERE EmpID = ?";
            } else {
                query = "SELECT * FROM Employee";
            }

            PreparedStatement ps = con.prepareStatement(query);

            if (empid != null && !empid.isEmpty()) {
                ps.setInt(1, Integer.parseInt(empid));
            }

            ResultSet rs = ps.executeQuery();

            out.println("<h2>Employee Records</h2>");
            out.println("<table border='1'><tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>"
                        + rs.getString("Name") + "</td><td>"
                        + rs.getDouble("Salary") + "</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
