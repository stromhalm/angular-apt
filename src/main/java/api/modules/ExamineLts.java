package api.modules;

import api.AptServlet;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.ExamineLTSModule;
import uniol.apt.analysis.ExaminePNModule;
import uniol.apt.io.parser.impl.AptLTSParser;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/examinePn")
public class ExamineLts extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		ExamineLTSModule examineLTSModule = new ExamineLTSModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(examineLTSModule);

		// Read apt input
		String aptCode = (String) requestData.get("pn");

		try {
			// Parse input
			AptLTSParser aptLTSParser = new AptLTSParser();
			TransitionSystem lts = aptLTSParser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("lts", lts);

			// Run Coverability Module
			examineLTSModule.run(input, moduleOutput);

			// Parse output
			jsonOut.put("deterministic", moduleOutput.getValue("deterministic"));
			jsonOut.put("persistent", moduleOutput.getValue("persistent"));
			jsonOut.put("backwards_persistent", moduleOutput.getValue("backwards_persistent"));
			jsonOut.put("totally_reachable", moduleOutput.getValue("totally_reachable"));
			jsonOut.put("reversible", moduleOutput.getValue("reversible"));
			jsonOut.put("isolated_elements", moduleOutput.getValue("isolated_elements"));
			jsonOut.put("strongly_connected", moduleOutput.getValue("strongly_connected"));
			jsonOut.put("weakly_connected", moduleOutput.getValue("weakly_connected"));
			jsonOut.put("same_parikh_vectors", moduleOutput.getValue("same_parikh_vectors"));
			jsonOut.put("same_or_mutually_disjoint_pv", moduleOutput.getValue("same_or_mutually_disjoint_pv"));

		} catch (Exception e) {
			jsonOut.put("error", e.getMessage());
		}
		return jsonOut;
	}
}