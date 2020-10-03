package io.jenkins.plugins.builddiscarders;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Job;
import jenkins.model.BuildDiscarder;
import jenkins.model.GlobalBuildDiscarderStrategy;
import jenkins.model.GlobalBuildDiscarderStrategyDescriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * Apply a user-specified build discarder periodically on all jobs.
 */
@Restricted(NoExternalUse.class)
public class DefaultGlobalBuildDiscarderStrategy extends GlobalBuildDiscarderStrategy {

    private BuildDiscarder discarder;

    @DataBoundConstructor
    public DefaultGlobalBuildDiscarderStrategy(BuildDiscarder discarder) {
        this.discarder = discarder;
    }

    public BuildDiscarder getDiscarder() {
        return discarder;
    }

    /**
     * Checks if the discarder should be applied to the job
     *
     * @param job targeted for discard
     * @return applicable for jobs without a specified build discarder
     */
    @Override
    public boolean isApplicable(Job<?, ?> job) {
        return job.getBuildDiscarder() == null;
    }

    @Override
    public void apply(Job<?, ?> job) throws IOException, InterruptedException {
        if (discarder != null) {
            discarder.perform(job);
            int j = 1;
        }
    }

    @Extension
    @Symbol("defaultBuildDiscarder")
    public static class DescriptorImpl extends GlobalBuildDiscarderStrategyDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.DefaultGlobalBuildDiscarderStrategy_DisplayName();
        }
    }
}
