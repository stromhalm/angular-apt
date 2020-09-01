package api;

import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AptServlet extends HttpServlet {

	/**
	 * Options method. Needed for CORS headers
	 */
	public void doOptions(HttpServletRequest req, HttpServletResponse response) {
		sendCorsHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
    }

	/**
	 * Request can be either GET or POST
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	/**
	 * Main Servlet response router
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)  {
		JSONObject requestData = null;
		PrintWriter output = null;
		try {
			requestData = getJSONData(request.getInputStream());
			output = response.getWriter();
		} catch (IOException e) {}

		JSONObject jsonResponse = processData(requestData);
		sendCorsHeaders(response);
		response.setContentType("application/javascript");
		output.println(jsonResponse.toString());
	}

	public JSONObject processData(JSONObject requestData) {
		return null;
	}

	/**
	 * Send HTTP headers needed fo CORS
	 */
	private void sendCorsHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}

	/**
	 * Converts the given input stream to a String
	 * @param stream Stream to be read
	 * @return String from input stream
	 * @throws IOException
	 */
	private String getStringFromInputStream(ServletInputStream stream) throws IOException
	{
		String inputString = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		for (String buffer; (buffer = in.readLine()) != null; inputString += buffer + "\n") ;

		return inputString;
	}

	/**
	 * Converts the given input stream to a JSONObject.
	 * @param stream Stream with JSON formated data.
	 * @return JSONObject that contains the data from the input stream
	 * @throws IOException
	 */
	private JSONObject getJSONData(ServletInputStream stream) throws IOException
	{
		String input = getStringFromInputStream(stream);

		if (input.equals("")) {
			return new JSONObject();
		} else {
			return new JSONObject(new JSONTokener(input));
		}
	}
}
