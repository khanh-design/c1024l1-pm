<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/16/2025
  Time: 10:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>From Add Products</title>
</head>
<body>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New Products</h2>
            </caption>
            <tr>
                <th>Name: </th>
                <td>
                    <input type="text" name="name" id="name" size="45"/>
                </td>
            </tr>

            <tr>
                <th>Price: </th>
                <td>
                    <input type="text" name="price" id="price" size="15"/>
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="save">
                    <input type="button" value="back">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
