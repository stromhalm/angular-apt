package api.modules;

import api.AptServlet;
import org.json.JSONObject;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.ExaminePNModule;
import uniol.apt.analysis.coverability.CoverabilityModule;
import uniol.apt.io.parser.impl.AptPNParser;
import uniol.apt.io.renderer.impl.AptLTSRenderer;
import uniol.apt.module.Category;
import uniol.apt.module.impl.ModuleInputImpl;
import uniol.apt.module.impl.ModuleOutputImpl;
import uniol.apt.module.impl.ModuleUtils;
import uniol.apt.pnanalysis.PnAnalysisModule;

import javax.servlet.annotation.WebServlet;
import java.util.Iterator;
import java.util.Map;

@WebServlet("/api/examinePn")
public class ExaminePN extends AptServlet {

	public JSONObject processData(JSONObject requestData) {

		// Load APT Module
		JSONObject jsonOut = new JSONObject();
		ExaminePNModule examinePNModule = new ExaminePNModule();
		ModuleOutputImpl moduleOutput = ModuleUtils.getModuleOutput(examinePNModule);

		// Read apt input
		String aptCode = (String) requestData.get("pn");

		try {
			// Parse input
			AptPNParser aptPNParser = new AptPNParser();
			PetriNet pn = aptPNParser.parseString(aptCode);
			ModuleInputImpl input = new ModuleInputImpl();
			input.setParameter("pn", pn);

			// Run Coverability Module
			examinePNModule.run(input, moduleOutput);

			// Parse output
			jsonOut.put("pure", moduleOutput.getValue("pure"));
			jsonOut.put("nonpure_only_simple_side_conditions", moduleOutput.getValue("nonpure_only_simple_side_conditions"));
			jsonOut.put("free_choice", moduleOutput.getValue("free_choice"));
			jsonOut.put("restricted_free_choice", moduleOutput.getValue("restricted_free_choice"));
			jsonOut.put("t_net", moduleOutput.getValue("t_net"));
			jsonOut.put("s_net", moduleOutput.getValue("s_net"));
			jsonOut.put("output_nonbranching", moduleOutput.getValue("output_nonbranching"));
			jsonOut.put("conflict_free", moduleOutput.getValue("conflict_free"));
			jsonOut.put("k-marking", moduleOutput.getValue("k-marking"));
			jsonOut.put("safe", moduleOutput.getValue("safe"));
			jsonOut.put("bounded", moduleOutput.getValue("bounded"));
			jsonOut.put("k-bounded", moduleOutput.getValue("k-bounded"));
			jsonOut.put("isolated_elements", moduleOutput.getValue("isolated_elements"));
			jsonOut.put("strongly_connected", moduleOutput.getValue("strongly_connected"));
			jsonOut.put("weakly_connected", moduleOutput.getValue("weakly_connected"));
			jsonOut.put("bcf", moduleOutput.getValue("bcf"));
			jsonOut.put("bicf", moduleOutput.getValue("bicf"));
			jsonOut.put("strongly_live", moduleOutput.getValue("strongly_live"));
			jsonOut.put("weakly_live", moduleOutput.getValue("weakly_live"));
			jsonOut.put("simply_live", moduleOutput.getValue("simply_live"));
			jsonOut.put("persistent", moduleOutput.getValue("persistent"));
			jsonOut.put("backwards_persistent", moduleOutput.getValue("backwards_persistent"));
			jsonOut.put("reversible", moduleOutput.getValue("reversible"));
			jsonOut.put("homogeneous", moduleOutput.getValue("homogeneous"));
			jsonOut.put("asymmetric_choice", moduleOutput.getValue("asymmetric_choice"));

		} catch (Exception e) {
			jsonOut.put("error", e.getMessage());
		}
		return jsonOut;
	}
}