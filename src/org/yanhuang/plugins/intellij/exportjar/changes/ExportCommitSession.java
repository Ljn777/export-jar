package org.yanhuang.plugins.intellij.exportjar.changes;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitSession;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yanhuang.plugins.intellij.exportjar.ui.SettingDialog;
import org.yanhuang.plugins.intellij.exportjar.ui.UIFactory;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;

/**
 * commit action of export jar
 */
public class ExportCommitSession implements CommitSession {
    private static final Logger LOG = Logger.getInstance(ExportCommitSession.class);

    private final SettingDialog settingDialog;

    public ExportCommitSession(Project project) {
        this.settingDialog = UIFactory.createSettingDialog(project);
    }

    @Nullable
    @Override
    public JComponent getAdditionalConfigurationUI(@NotNull Collection<Change> changes, @Nullable String commitMessage) {
        return settingDialog.getSettingPanel();
    }

    @Override
    public boolean canExecute(Collection<Change> changes, String commitMessage) {
        return changes != null && changes.size() > 0;
    }

    @Override
    public void execute(@NotNull Collection<Change> changes, @Nullable String commitMessage) {
        LOG.info("in " + this.getClass().getSimpleName() + ".execute(), commitMessage: " + commitMessage);
        final VirtualFile[] changeFiles = changes.stream().map(Change::getVirtualFile).filter(Objects::nonNull).toArray(VirtualFile[]::new);
        settingDialog.doExport(changeFiles);
    }

    @Override
    public void executionCanceled() {
        LOG.info("in " + this.getClass().getSimpleName() + ".executionCanceled()");
        settingDialog.dispose();
    }

    @Nullable
    @Override
    public String getHelpId() {
        return null;//TODO help doc
    }

}