/**
 * Copyright (c) Microsoft Corporation
 * 
 * All rights reserved. 
 * 
 * MIT License
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.microsoft.azure.hdinsight.spark.actions;

import java.util.HashSet;
import java.util.List;

import com.microsoft.azure.hdinsight.Activator;
import com.microsoft.azure.hdinsight.common.CallBack;
import com.microsoft.azure.hdinsight.spark.ui.SparkSubmissionExDialog;
import com.microsoft.azure.hdinsight.util.Messages;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;

import com.microsoft.azure.hdinsight.common.ClusterManagerEx;
import com.microsoft.azure.hdinsight.common2.HDInsightUtil;
import com.microsoft.azure.hdinsight.sdk.cluster.IClusterDetail;
import com.microsoft.tooling.msservices.components.DefaultLoader;
import com.microsoftopentechnologies.wacommon.telemetry.AppInsightsCustomEvent;
import com.microsoftopentechnologies.wacommon.utils.PluginUtil;

public class SubmitHandler extends AbstractHandler {
    private List<IClusterDetail> cachedClusterDetails = null;
    private static final HashSet<IProject> isActionPerformedSet = new HashSet<>();
    
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		synchronized (SubmitHandler.class) {
					AppInsightsCustomEvent.create(Messages.SparkSubmissionRightClickProject,Activator.getDefault().getBundle().getVersion().toString());
                    HDInsightUtil.showInfoOnSubmissionMessageWindow("List spark clusters ...", true);
                   
                    cachedClusterDetails = ClusterManagerEx.getInstance().getClusterDetailsWithoutAsync(true, null);
                    if(!ClusterManagerEx.getInstance().isSelectedSubscriptionExist()) {
                        HDInsightUtil.showWarningMessageOnSubmissionMessageWindow("No selected subscription(s), Please go to HDInsight Explorer to sign in....");
                    }

                    if (ClusterManagerEx.getInstance().isListClusterSuccess()) {
                        HDInsightUtil.showInfoOnSubmissionMessageWindow("List spark clusters successfully");
                    } else {
                        HDInsightUtil.showErrorMessageOnSubmissionMessageWindow("Error : Failed to list spark clusters.");
                    }
                    if (ClusterManagerEx.getInstance().isLIstAdditionalClusterSuccess()) {
                        HDInsightUtil.showInfoOnSubmissionMessageWindow("List additional spark clusters successfully");
                    } else {
                        HDInsightUtil.showErrorMessageOnSubmissionMessageWindow("Error: Failed to list additional cluster");
                    }
                    SparkSubmissionExDialog dialog = new SparkSubmissionExDialog(PluginUtil.getParentShell(), cachedClusterDetails, null);
                    dialog.open();
            return null;
        }

	}
}
