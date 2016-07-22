package api;

import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by adrian-jagusch on 22.07.16.
 */
public class AptServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)  {
		JSONObject requestData = null;
		PrintWriter output = null;
		try {
			requestData = getJSONData(request.getInputStream());
			output = response.getWriter();
		} catch (IOException e) {}

		JSONObject jsonResponse = processData(requestData);
		response.setContentType("application/javascript");
		output.println(jsonResponse.toString());
	}

	public JSONObject processData(JSONObject requestData) {
		return null;
	}

	/**
	 * Konvertiert den gegebenen Input-Stream in einen String.
	 * @param stream Stream, der gelesen werden soll.
	 * @return Zeichenkette aus dem String.
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
	 * Konvertiert den gegebenen Stream in ein JSONObject.
	 * @param stream Stream mit JSON formatierten Daten.
	 * @return JSONObject, der die Daten aus dem Stream enthält.
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
