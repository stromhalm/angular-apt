package api.modules;

import api.AptServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.synthesize.SynthesizeModule;
import uniol.apt.io.parser.impl.AptLTSParser;
import uniol.apt.io.renderer.impl.AptPNRenderer;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/synthesize")
public class Synthesizer extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		SynthesizeModule synthesizer = new SynthesizeModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(synthesizer);

		// Read apt input
		String ltsCode = (String) requestData.get("lts");
		JSONArray options = requestData.getJSONArray("options");
		String optionsString = "quick-fail"; // Stop the algorithm when the result 'success: No' is clear.

		for (int i=0; i<options.length(); i++) {
			optionsString += "," + options.getString(i);
		}

		try {
			// Parse input
			AptLTSParser aptLTSParser = new AptLTSParser();
			TransitionSystem lts = aptLTSParser.parseString(ltsCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("options", optionsString);
			input.setParameter("lts", lts);

			// Run Coverability Module
			synthesizer.run(input, moduleOutput);

			// Parse output
			PetriNet net = (PetriNet) moduleOutput.getValue("pn");
			AptPNRenderer aptLTSRenderer = new AptPNRenderer();

			if (moduleOutput.getValue("success").equals(true)) {
				jsonOut.put("success", true);
				jsonOut.put("pn", aptLTSRenderer.render(net));
			} else {
				jsonOut.put("success", false);
				jsonOut.put("failedStateSeparationProblems", moduleOutput.getValue("failedStateSeparationProblems"));
				jsonOut.put("failedEventStateSeparationProblems", moduleOutput.getValue("failedEventStateSeparationProblems"));
			}

		} catch (Exception e) {
			jsonOut.put("error", e.getMessage());
		}
		return jsonOut;
	}
}