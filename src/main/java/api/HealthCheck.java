package api;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/health")
public class HealthCheck extends HttpServlet {

	/**
	 * Request can be either GET or POST
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(200);
		try {
			response.getWriter().write("OK");
		} catch (IOException e) {}
	}
}
