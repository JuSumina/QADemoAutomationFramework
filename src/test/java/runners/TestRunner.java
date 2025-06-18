package runners;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;

@Suite
@Cucumber
@SelectClasspathResource("features")
@IncludeTags("createaccount")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "hooks,stepDefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports/html-report.html,json:target/cucumber-reports/cucumber.json")
// @ConfigurationParameter(key = DRY_RUN_PROPERTY_NAME, value = "false")
// @ConfigurationParameter(key = CAPTURE_STDOUT_PROPERTY_NAME, value = "true")
public class TestRunner {

}
