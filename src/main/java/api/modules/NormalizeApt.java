package api.modules;

import api.AptServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.synthesize.SynthesizeModule;
import uniol.apt.io.parser.impl.AptLTSParser;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.io.renderer.impl.AptLTSRenderer;
import uniol.apt.io.renderer.impl.AptPNRenderer;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/normalizeApt")
public class NormalizeApt extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		JSONObject jsonOut = new JSONObject();

		// Read apt input
		String aptCode = (String) requestData.get("apt");

		try {

			if (!aptCode.replace(".type LTS", "").equals(aptCode)) {
				AptLTSParser aptLTSParser = new AptLTSParser();
				AptLTSRenderer aptLTSRenderer = new AptLTSRenderer();
				TransitionSystem lts = aptLTSParser.parseString(aptCode);
				jsonOut.put("apt", aptLTSRenderer.render(lts));
			} else {
				AptPNParser aptPNParser = new AptPNParser();
				AptPNRenderer aptPNRenderer = new AptPNRenderer();
				PetriNet pn = aptPNParser.parseString(aptCode);
				jsonOut.put("apt", aptPNRenderer.render(pn));
			}

		} catch (Exception e) {
			jsonOut.put("error", e.getMessage());
		}
		return jsonOut;
	}
}