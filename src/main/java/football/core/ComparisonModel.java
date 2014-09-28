package football.core;

import java.util.List;
import java.util.Map;

import football.config.CustomScoringHelperProperties;
import football.core.intface.CustomScoringHelperModel;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.StatType;
import football.util.metrics.Metric;

public final class ComparisonModel implements CustomScoringHelperModel
{
	private SimpleModel model1, model2;

	public ComparisonModel() {
		model1 = new SimpleModel();
		model2 = new SimpleModel();
	}

	// run model1 with give custom rules; run model2 with default rules
	@Override
	public ScoringResults run(String[] args) {
		// run each model with corresponding set of rules
		SimpleScoringResults results1 = (SimpleScoringResults)model1.run(args);
		SimpleScoringResults results2 = (SimpleScoringResults)model2.run(
				CustomScoringHelperProperties.getDefaultRules().toArgs(Mode.fromString(args[0])));

		// merge two results
		return mergeResults(results1, results2);
	}

	@Override
	public ScoringResults run() {
		// run each model with corresponding set of rules
		SimpleScoringResults results1 = (SimpleScoringResults)model1.run();
		SimpleScoringResults results2 = (SimpleScoringResults)model2.run();

		// merge two results
		return mergeResults(results1, results2);
	}

	@Override
	public void logResults(ScoringResults results) {
		// Note: this is sufficient, since ResultsLogger is generalized to handle simple or 
		// composite ScoringResults
		// TODO: may want to re-work this
		model1.logResults(results);
	}

	@Override
	public void updateResultsLogger(String fullPathToFile) {
		// TODO: may want to re-work this
		model1.updateResultsLogger(fullPathToFile);
	}

	@Override
	public void updateResultsLogger(Mode mode) {
		// TODO: may want to re-work this
		model1.updateResultsLogger(mode);
	}

	@Override
	public Map<Mode,List<Player>> getModesToPlayersMap(int modelID) {
		if(modelID == 1) {
			return model1.getModesToPlayersMap(modelID);
		} else if(modelID == 2) {
			return model2.getModesToPlayersMap(modelID);
		} else {
			throw new IllegalArgumentException("modelID " + modelID + " is not 1 or 2");
		}
	}

	@Override
	public int getNumberOfModels() {
		return 2;
	}

	@Override
	public void setMode(Mode mode) {
		model1.setMode(mode);
		model2.setMode(mode);
	}

	@Override
	public <T extends Enum<T> & StatType> void setRule(Rule<T> rule, int modelID) {
		if(modelID == 1) {
			model1.setRule(rule, modelID);
		} else if(modelID == 2) {
			model2.setRule(rule, modelID);
		} else {
			throw new IllegalArgumentException("modelID " + modelID + " is not 1 or 2");
		}
	}

	@Override
	public void setRules(RuleMap rules, int modelID) {
		if(modelID == 1) {
			model1.setRules(rules, modelID);
		} else if(modelID == 2) {
			model2.setRules(rules, modelID);
		} else {
			throw new IllegalArgumentException("modelID " + modelID + " is not 1 or 2");
		}
	}

	@Override
	public void close() {
		model1.close();
		model2.close();
	}




	// Merge 2 simple results into a comparison results object after computing metric
	// information (if metrics are enabled)
	private ComparisonScoringResults mergeResults(SimpleScoringResults results1, SimpleScoringResults results2) {
		double distance;
		if(CustomScoringHelperProperties.metricsEnabled()) {
			Metric metric = CustomScoringHelperProperties.getDefaultMetric();
			distance = metric.distance(results1.getPlayers().get(0), results2.getPlayers().get(0));
		} else {
			distance = -1;
		}

		return new ComparisonScoringResults(results1, results2, distance);
	}
}
