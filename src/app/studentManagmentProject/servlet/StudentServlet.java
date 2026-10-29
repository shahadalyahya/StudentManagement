package app.studentManagmentProject.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.studentManagmentProject.exception.StudentAlreadyExistsException;
import app.studentManagmentProject.model.Student;
import app.studentManagmentProject.service.StudentService;
import app.studentManagmentProject.service.impl.StudentDBServiceImpl;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentDBServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        try {
            if ("search".equals(action)) {
                String name = request.getParameter("name");

                out.println("<!DOCTYPE html><html><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Search Result</title></head><body>");
                out.println("<h2>Search Result</h2>");
                out.println("<p>" +
                        escapeHtml(studentService.searchStudent(name)) +
                        "</p>");
                out.println("<a href='students'>Back to Students</a>");
                out.println("</body></html>");
                return;
            }

            List<Student> students = studentService.showStudents();

            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'>");
            out.println("<title>Student Management</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:35px;background:#f5f5fa}");
            out.println("table{border-collapse:collapse;width:100%;background:white}");
            out.println("th,td{padding:10px;border:1px solid #ddd;text-align:left}");
            out.println("th{background:#867ab9;color:white}");
            out.println("form{margin:18px 0;padding:15px;background:white}");
            out.println("input{padding:8px;margin:5px}");
            out.println("button{padding:8px 14px;cursor:pointer}");
            out.println("</style></head><body>");

            out.println("<h1>Student Management System</h1>");

            String message = request.getParameter("message");
            if (message != null) {
                out.println("<p>" + escapeHtml(message) + "</p>");
            }

            // Add Student
            out.println("<h2>Add Student</h2>");
            out.println("<form method='post' action='students'>");
            out.println("<input type='hidden' name='action' value='add'>");
            out.println("Name: <input name='name' required>");
            out.println("Age: <input type='number' name='age' min='1' required>");
            out.println("Grade: <input type='number' name='grade' min='0' max='100' step='0.01' required>");
            out.println("National ID: <input name='nationalId' pattern='[12][0-9]{9}' maxlength='10' required>");
            out.println("<button type='submit'>Add Student</button>");
            out.println("</form>");

            // Search Student
            out.println("<h2>Search Student</h2>");
            out.println("<form method='get' action='students'>");
            out.println("<input type='hidden' name='action' value='search'>");
            out.println("<input name='name' placeholder='Student name' required>");
            out.println("<button type='submit'>Search</button>");
            out.println("</form>");

            // Students Table
            out.println("<h2>Students List</h2>");
            out.println("<table><tr>");
            out.println("<th>ID</th><th>Name</th><th>Age</th>");
            out.println("<th>Grade</th><th>National ID</th>");
            out.println("<th>Status</th><th>Grade Level</th><th>Actions</th>");
            out.println("</tr>");

            for (Student student : students) {
                out.println("<tr>");
                out.println("<td>" + student.getId() + "</td>");
                out.println("<td>" + escapeHtml(student.getName()) + "</td>");
                out.println("<td>" + student.getAge() + "</td>");
                out.println("<td>" + student.getGrade() + "</td>");
                out.println("<td>" + escapeHtml(student.getNationalId()) + "</td>");
                out.println("<td>" + escapeHtml(student.getPassFailStatus()) + "</td>");
                out.println("<td>" + escapeHtml(student.getGradeLevel()) + "</td>");

                out.println("<td>");

                // Update Student
                out.println("<form method='post' action='students'>");
                out.println("<input type='hidden' name='action' value='update'>");
                out.println("<input type='hidden' name='nationalId' value='" +
                        escapeHtml(student.getNationalId()) + "'>");
                out.println("<input name='name' value='" +
                        escapeHtml(student.getName()) + "' required>");
                out.println("<input type='number' name='age' min='1' value='" +
                        student.getAge() + "' required>");
                out.println("<input type='number' name='grade' min='0' max='100' step='0.01' value='" +
                        student.getGrade() + "' required>");
                out.println("<button type='submit'>Update</button>");
                out.println("</form>");

                // Delete Student
                out.println("<form method='post' action='students'>");
                out.println("<input type='hidden' name='action' value='delete'>");
                out.println("<input type='hidden' name='nationalId' value='" +
                        escapeHtml(student.getNationalId()) + "'>");
                out.println("<button type='submit'>Delete</button>");
                out.println("</form>");

                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            throw new ServletException("Unable to load students", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                int age = Integer.parseInt(request.getParameter("age"));
                double grade = Double.parseDouble(request.getParameter("grade"));
                String nationalId = request.getParameter("nationalId");

                Student student = new Student(name, age, grade, nationalId);

                boolean added = studentService.addStudent(student);

                redirectWithMessage(response,
                        added ? "Student added successfully."
                              : "Student could not be added.");
                return;
            }

            if ("delete".equals(action)) {
                String nationalId = request.getParameter("nationalId");

                boolean deleted = studentService.deleteStudent(nationalId);

                redirectWithMessage(response,
                        deleted ? "Student deleted successfully."
                                : "Student was not found.");
                return;
            }

            if ("update".equals(action)) {
                String nationalId = request.getParameter("nationalId");
                String name = request.getParameter("name");
                int age = Integer.parseInt(request.getParameter("age"));
                double grade = Double.parseDouble(request.getParameter("grade"));

                Student student = new Student(name, age, grade, nationalId);

                boolean updated =
                        studentService.updateStudent(nationalId, student);

                redirectWithMessage(response,
                        updated ? "Student updated successfully."
                                : "Student could not be updated.");
                return;
            }

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Unknown action.");

        } catch (StudentAlreadyExistsException e) {
            redirectWithMessage(response,
                    "This National ID already exists.");
        } catch (IllegalArgumentException e) {
            redirectWithMessage(response,
                    "Invalid input: " + e.getMessage());
        } catch (Exception e) {
            throw new ServletException("Student operation failed", e);
        }
    }

    private void redirectWithMessage(HttpServletResponse response,
                                     String message) throws IOException {
        response.sendRedirect("students?message=" +
                URLEncoder.encode(message, "UTF-8"));
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
