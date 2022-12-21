package br.com.dbccompany.vemser.captacao.run;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        tags = {"@wip"},
        glue = "br/com/dbccompany/vemser/captacao/steps",
        plugin = {"pretty", "io.qameta.allure.cucumberjvm.AllureCucumberJvm"},
        snippets = SnippetType.CAMELCASE
)
public class RunTest {
}
