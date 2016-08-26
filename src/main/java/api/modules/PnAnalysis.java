package api.modules;

import api.AptServlet;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.io.renderer.impl.AptLTSRenderer;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;
import uniol.apt.pnanalysis.PnAnalysisModule;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/pnAnalysis")
public class PnAnalysis extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		PnAnalysisModule analysisModule = new PnAnalysisModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(analysisModule);

		// Read apt input
		String aptCode = (String) requestData.get("pn");

		try {
			// Parse input
			AptPNParser aptPNParser = new AptPNParser();
			PetriNet pn = aptPNParser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("pn", pn);

			// Run Coverability Module
			analysisModule.run(input, moduleOutput);

			// Parse output
			TransitionSystem cov = (TransitionSystem) moduleOutput.getValue("lts");
			AptLTSRenderer aptLTSRenderer = new AptLTSRenderer();
			jsonOut.put("lts", aptLTSRenderer.render(cov));

		} catch (Exception e) {}
		return jsonOut;
	}
}