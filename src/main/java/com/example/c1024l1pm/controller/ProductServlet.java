package com.example.c1024l1pm.controller;

import com.example.c1024l1pm.model.Product;
import com.example.c1024l1pm.service.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersion = 1L;
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
        System.out.println("initializing Product");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
//                    showNewform(req, resp);
                    break;
                case "edit":
//                    showEditForm(req, resp);
                    break;
                case "delete":
//                    DeleteUser(req, resp);
                    break;
                default:
                    listProduts(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listProduts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Product> listProducts = productDAO.findAll();
        request.setAttribute("products", listProducts);
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("products/list.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
        System.out.println("destroying Product");
    }
}
