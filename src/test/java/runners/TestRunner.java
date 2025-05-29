package runners;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource; // Using this is generally preferred
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME; // Keep this import for clarity, though SelectClasspathResource might override

@Suite
@Cucumber
@SelectClasspathResource("features") // This tells Cucumber to look for features in the 'features' directory on the classpath.
// For Maven/Gradle, src/test/resources is automatically added to the classpath,
// so 'features' refers to 'src/test/resources/features'.
@IncludeTags("smoke")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "hooks,stepDefinitions") // **IMPORTANT:** These must be the EXACT root package names
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports/html-report.html,json:target/cucumber-reports/cucumber.json")
// @ConfigurationParameter(key = DRY_RUN_PROPERTY_NAME, value = "false") // Uncomment if needed
// @ConfigurationParameter(key = CAPTURE_STDOUT_PROPERTY_NAME, value = "true") // Uncomment if needed
public class TestRunner {
    // No code needed inside the class.
}
