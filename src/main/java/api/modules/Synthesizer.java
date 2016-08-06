package api.modules;

import api.AptServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.analysis.synthesize.SynthesizeModule;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.io.renderer.impl.AptLTSRenderer;
import uniol.apt.io.renderer.impl.AptPNRenderer;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/coverabilityGraph")
public class Synthesizer extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		SynthesizeModule synthesizer = new SynthesizeModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(synthesizer);

		// Read apt input
		String aptCode = (String) requestData.get("lts");
		JSONArray options = requestData.getJSONArray("options");
		String optionsString = "quick-fail"; // Stop the algorithm when the result 'success: No' is clear.

		for (int i=0; i<options.length(); i++) {
			optionsString += "," + options.getString(i);
		}

		try {
			// Parse input
			AptPNParser aptPNParser = new AptPNParser();
			PetriNet pn = aptPNParser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("options", optionsString);
			input.setParameter("lts", pn);

			// Run Coverability Module
			synthesizer.run(input, moduleOutput);

			// Parse output
			PetriNet net = (PetriNet) moduleOutput.getValue("pn");
			AptPNRenderer aptLTSRenderer = new AptPNRenderer();
			jsonOut.put("pn", aptLTSRenderer.render(net));

		} catch (Exception e) {}
		return jsonOut;
	}
}