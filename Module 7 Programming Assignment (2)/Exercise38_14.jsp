<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
<head>
    <title>Addition Quiz</title>
    <style>
        table
        {
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        td
        {
            padding: 2px 10px;
            vertical-align: middle;
        }
        input[type="text"] { width: 50px; }
    </style>
</head>
<body>
<%
    // Check if the form has been submitted by testing for the "submitted" parameter.
    if (request.getParameter("submitted") == null) {
        // If no form has been submitted, then create one.
        int numQuestions = 10;
        int[] num1s = new int[numQuestions];
        int[] num2s = new int[numQuestions];

        for (int i = 0; i < numQuestions; i++)
        {
            num1s[i] = (int)(Math.random() * 11) + 20;
            num2s[i] = (int)(Math.random() * 10) + 1;
        }
%>
    <h1>Addition Quiz</h1>
    <form method="post" action="Exercise38_14.jsp">
        <table>
            <%
                // For each question, print a table row with three cells:
                // 1. The expression "num1 + num2".
                // 2. The equals sign, which will line up vertically.
                // 3. The text box for the user's answer.
                for (int i = 0; i < numQuestions; i++)
                {
            %>
            <tr>
                <td><%= num1s[i] %> + <%= num2s[i] %></td>
                <td>=</td>
                <td>
                    <input type="text" name="answer<%= i %>" />
                    <!-- Save the generated numbers so we can check the answers later -->
                    <input type="hidden" name="num1_<%= i %>" value="<%= num1s[i] %>" />
                    <input type="hidden" name="num2_<%= i %>" value="<%= num2s[i] %>" />
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <input type="submit" name="submitted" value="Submit" />
    </form>
    <p>Click the browser's Refresh button to get a new quiz</p>
<%
    }
    else
    {
        // --- Process Submitted Answers ---
        int numQuestions = 10;
        int totalCorrect = 0;
%>
    <h1>Addition Quiz Result</h1>
    <table>
        <%
            // Process each question;
            // retrieve the generated numbers from the hidden fields and the user's answer;
            // check if the answer is correct, and count the correct answers.
            for (int i = 0; i < numQuestions; i++)
            {
                int num1 = Integer.parseInt(request.getParameter("num1_" + i));
                int num2 = Integer.parseInt(request.getParameter("num2_" + i));
                int correctAnswer = num1 + num2;
                String userAnswerStr = request.getParameter("answer" + i);
                int userAnswer = 0;
                boolean isNumeric = true;

                try
                {
                    userAnswer = Integer.parseInt(userAnswerStr.trim());
                }
                catch(Exception e)
                {
                    // If the answer cannot be parsed to a number, treat it as a wrong answer.
                    isNumeric = false;
                }

                boolean isCorrect = isNumeric && (userAnswer == correctAnswer);
                if (isCorrect)
                    totalCorrect++;
        %>
        <tr>
            <td><%= num1 %> + <%= num2 %></td>
            <td>=</td>
            <td>
                <input type="text" readonly="readonly" value="<%= userAnswerStr %>" />
            </td>
            <td>
                <% if (isCorrect) %>
                    Correct
                <% else %>
                    Wrong
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <h3>The total correct count is <%= totalCorrect %></h3>
<%
    }
%>
</body>
</html>