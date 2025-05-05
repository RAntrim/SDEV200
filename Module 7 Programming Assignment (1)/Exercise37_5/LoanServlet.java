package application;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import application.Loan;

@WebServlet("/LoanServlet")
public class LoanServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setContentType("text/html;charset=ISO-8859-1");

        // Retrieve data from the form's parameters.
        String loanAmountStr = request.getParameter("loanAmount");
        String annualInterestRateStr = request.getParameter("annualInterestRate");
        String numberOfYearsStr = request.getParameter("numberOfYears");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='ISO-8859-1'>");
        out.println("<title>Loan Payment Results</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Loan Payment Results</h1>");
        
        try
        {
            double loanAmount = Double.parseDouble(loanAmountStr);
            double annualInterestRate = Double.parseDouble(annualInterestRateStr);
            int numberOfYears = Integer.parseInt(numberOfYearsStr);

            Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
            double monthlyPayment = loan.getMonthlyPayment();
            double totalPayment = loan.getTotalPayment();

            out.println("<p>Loan Amount: " + loanAmount + "</p>");
            out.println("<p>Annual Interest Rate: " + annualInterestRate + "%</p>");
            out.println("<p>Number of Years: " + numberOfYears + "</p>");
            out.println("<p><strong>Monthly Payment:</strong> " + String.format("%.2f", monthlyPayment) + "</p>");
            out.println("<p><strong>Total Payment:</strong> " + String.format("%.2f", totalPayment) + "</p>");
        }
        catch (NumberFormatException e)
        {
            out.println("<p style='color: red;'>Invalid input provided. Please ensure that you enter valid numerical values.</p>");
        }

        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
