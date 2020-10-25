package wey.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import wey.dao.AnimalsDao;
import wey.entity.Animals;
import wey.util.HibernateUtil;

@WebServlet("/ReadAllAnimals")
public class ReadAllAnimals extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processAction(request, response);
	}

	private void processAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session hSession = factory.getCurrentSession();
		
		AnimalsDao animalsDao = new AnimalsDao(hSession);
		List<Animals> list = animalsDao.readAll();
		request.setAttribute("AnimalsList", list);
		
		HibernateUtil.closeSessionFactory();
		RequestDispatcher rd = request.getRequestDispatcher("/wey/animal/ReadAnimal.jsp");
		rd.forward(request, response);
		return;
	}

}
