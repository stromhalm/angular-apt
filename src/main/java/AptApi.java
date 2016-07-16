// Import required java libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import uniol.apt.adt.pn.PetriNet;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.generator.cycle.CycleGenerator;
import uniol.apt.io.renderer.impl.AptPNRenderer;
import uniol.apt.module.impl.*;

@WebServlet("/api/coverabilityGraph")
public class AptApi extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String aptCode = request.getParameter("apt");
		String covOutput = "";
		CoverabilityModule covMod = new CoverabilityModule();
		ModuleOutputImpl output = ModuleUtils.getModuleOutput(covMod);


		try {

			/*
			AptPNParser parser = new AptPNParser();
			PetriNet pn = parser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("pn", pn);

			covMod.run(input, output);

			TransitionSystem cov = (TransitionSystem) output.getValue("lts");
			AptLTSRenderer parser1 = new AptLTSRenderer();
			covOutput = parser1.render(cov); */

			CycleGenerator cycleGenerator = new CycleGenerator();
			PetriNet test = cycleGenerator.generateNet(4);
			AptPNRenderer renderer = new AptPNRenderer();
			covOutput = renderer.render(test);

		} catch (Exception e) {
			e.printStackTrace();
		}


		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>Hallo Welt</h1>");
		out.println(covOutput);
	}

	public void destroy() {
		// do nothing.
	}
}