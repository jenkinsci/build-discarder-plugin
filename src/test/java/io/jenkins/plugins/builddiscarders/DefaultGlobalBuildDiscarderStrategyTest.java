package io.jenkins.plugins.builddiscarders;

import hudson.model.FreeStyleProject;
import hudson.tasks.LogRotator;
import jenkins.model.GlobalBuildDiscarderConfiguration;
import jenkins.model.JobGlobalBuildDiscarderStrategy;
import jenkins.model.SimpleGlobalBuildDiscarderStrategy;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class DefaultGlobalBuildDiscarderStrategyTest {
    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void defaultDiscarderWithoutAJobSpecificDiscarder() throws Exception {
        FreeStyleProject p = j.createFreeStyleProject();
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new JobGlobalBuildDiscarderStrategy()
        );
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new DefaultGlobalBuildDiscarderStrategy(itemsToKeep(2))
        );

        {
            j.buildAndAssertSuccess(p);
            j.buildAndAssertSuccess(p);
            j.buildAndAssertSuccess(p);
            j.buildAndAssertSuccess(p);
            j.buildAndAssertSuccess(p);
            Assert.assertEquals("Since there's not job discarder use the default",
                2, p.getBuilds().stream().count());
        }
    }

    @Test
    public void jobSpecificDiscarderShouldOverrideTheDefaultDiscarder() throws Exception {
        FreeStyleProject p = j.createFreeStyleProject();
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(new JobGlobalBuildDiscarderStrategy());
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new DefaultGlobalBuildDiscarderStrategy(itemsToKeep(2)));

        p.setBuildDiscarder(itemsToKeep(4));

        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);

        Assert.assertEquals("Should respect the specified job discarder",
            4, p.getBuilds().stream().count());
    }

    @Test
    public void simpleDiscarderShouldOverrideOtherDiscarders() throws Exception {
        FreeStyleProject p = j.createFreeStyleProject();
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new JobGlobalBuildDiscarderStrategy()
        );
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new SimpleGlobalBuildDiscarderStrategy(itemsToKeep(2))
        );
        GlobalBuildDiscarderConfiguration.get().getConfiguredBuildDiscarders().add(
            new DefaultGlobalBuildDiscarderStrategy(itemsToKeep(3))
        );
        p.setBuildDiscarder(itemsToKeep(4));

        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);
        j.buildAndAssertSuccess(p);

        Assert.assertEquals("The simple discarder will override all other settings",
            2, p.getBuilds().stream().count());
    }

    private static LogRotator itemsToKeep(int numberToKeep) {
        return new LogRotator(-1, numberToKeep, -1, -1);
    }
}
