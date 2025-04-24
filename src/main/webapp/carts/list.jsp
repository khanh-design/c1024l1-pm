<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }

        #total {
            position: relative;
            left: 1200px;
            font-size: 18px;
            font-weight: bold;
            color: red;
        }
    </style>
</head>
<body>
<div id="content">
    <h1>
        Carts
    </h1>
    <table>
        <tr>
            <th>Product Name</th>
            <th>Product Price</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:set var="totalPrice" value="${0}"/>
        <c:forEach var="item" items="${sessionScope.cart}">
            <tr>
                <td><p><c:out value="${item.getProduct().getName().toString()}"/></p></td>
                <td><p><c:out value="${item.getProduct().getPrice().toString()}"/></p></td>
                <td>
                    <input type="text" id="quantity" name="quantity"
                           value="${item.getQuantity().toString()}" />
                </td>
                <td><p><c:out value="${item.getQuantity() * item.getProduct().getPrice()}"/></p></td>
            </tr>
            <c:set var="totalPrice" value="${totalPrice + (item.getQuantity() * item.getProduct().getPrice())}"/>
        </c:forEach>
    </table>
    <p id="total">Total Price: <c:out value="${totalPrice}" /></p>
    <a href="/carts?action=checkout">Checkout</a>
</div>
</body>
</html>