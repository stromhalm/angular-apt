// Import required java libraries
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.generator.cycle.CycleGenerator;
import uniol.apt.io.renderer.impl.AptPNRenderer;
import uniol.apt.module.impl.*;

@WebServlet("/api/coverabilityGraph")
public class AptApi extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String aptCode = request.getParameter("apt");
		JSONObject jsonOut = new JSONObject();
		CoverabilityModule covMod = new CoverabilityModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(covMod);


		try {
			/*
			AptPNParser parser = new AptPNParser();
			PetriNet pn = parser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("pn", pn);

			covMod.run(input, moduleOutput);

			TransitionSystem cov = (TransitionSystem) output.getValue("lts");
			AptLTSRenderer parser1 = new AptLTSRenderer();
			covOutput = parser1.render(cov); */

			CycleGenerator cycleGenerator = new CycleGenerator();
			PetriNet test = cycleGenerator.generateNet(4);
			AptPNRenderer renderer = new AptPNRenderer();
			jsonOut.put("coverabilityGraph", renderer.render(test));

			// Set response content type
			response.setContentType("application/javascript");

			PrintWriter output = response.getWriter();
			output.println(jsonOut.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		// do nothing.
	}
}