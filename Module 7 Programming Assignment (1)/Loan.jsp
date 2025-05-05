<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="application.Loan" %>
<%
    // Retrieve parameters from the request if they exist
    String loanAmountStr = request.getParameter("loanAmount");
    String annualInterestRateStr = request.getParameter("annualInterestRate");
    String numberOfYearsStr = request.getParameter("numberOfYears");

    // Flag to check if form is submitted
    boolean isSubmitted = (loanAmountStr != null && annualInterestRateStr != null && numberOfYearsStr != null);
    double monthlyPayment = 0.0;
    double totalPayment = 0.0;
    String errorMessage = null;

    if (isSubmitted) {
        try
        {
            // Parse the parameters
            double loanAmount = Double.parseDouble(loanAmountStr);
            double annualInterestRate = Double.parseDouble(annualInterestRateStr);
            int numberOfYears = Integer.parseInt(numberOfYearsStr);

            // Create a new Loan object and compute payments
            Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
            monthlyPayment = loan.getMonthlyPayment();
            totalPayment = loan.getTotalPayment();
        }
        catch (NumberFormatException ex)
        {
            errorMessage = "Invalid input data. Please enter valid numbers for the loan amount, interest rate, and number of years.";
        }
    }
%>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Exercise37_5</title>
</head>
<body>
    <h1>Compute Loan Payment</h1>
<% 
    // Display error message if any exists
    if (errorMessage != null) { 
%>
    <p style="color:red;"><%= errorMessage %></p>
<%
    }
%>
<% 
    // If the form has been submitted successfully, display the results
    if (isSubmitted && errorMessage == null) { 
%>
    <h2>Results</h2>
    <p>Monthly Payment: <strong><%= String.format("%.2f", monthlyPayment) %></strong></p>
    <p>Total Payment: <strong><%= String.format("%.2f", totalPayment) %></strong></p>
    <hr>
<%
    } 
%>
    <form action="LoanServlet" method="post">
        <table>
            <tr>
                <td>Loan Amount</td>
                <td>
                    <input type="text" name="loanAmount" value="<%= (loanAmountStr == null ? "1000" : loanAmountStr) %>" required />
                </td>
            </tr>
            <tr>
                <td>Annual Interest Rate</td>
                <td>
                    <input type="text" name="annualInterestRate" value="<%= (annualInterestRateStr == null ? "2.5" : annualInterestRateStr) %>" required />
                </td>
            </tr>
            <tr>
                <td>Number of Years</td>
                <td>
                    <input type="text" name="numberOfYears" value="<%= (numberOfYearsStr == null ? "1" : numberOfYearsStr) %>" required />
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">
                    <input type="submit" value="Compute Loan Payment">
                    <input type="reset" value="Reset">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
