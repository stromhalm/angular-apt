package api.modules;
import javax.servlet.annotation.WebServlet;
import api.AptServlet;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.io.renderer.impl.AptLTSRenderer;
import uniol.apt.module.impl.*;

@WebServlet("/api/coverabilityGraph")
public class CoverabilityGraph extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		CoverabilityModule covMod = new CoverabilityModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(covMod);

		// Read apt input
		String aptCode = (String) requestData.get("pn");

		try {
			// Parse input
			AptPNParser aptPNParser = new AptPNParser();
			PetriNet pn = aptPNParser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("pn", pn);

			// Run Coverability Module
			covMod.run(input, moduleOutput);

			// Parse output
			TransitionSystem cov = (TransitionSystem) moduleOutput.getValue("lts");
			AptLTSRenderer aptLTSRenderer = new AptLTSRenderer();
			jsonOut.put("lts", aptLTSRenderer.render(cov));

		} catch (Exception e) {
			jsonOut.put("error", e.getMessage());
		}
		return jsonOut;
	}
}