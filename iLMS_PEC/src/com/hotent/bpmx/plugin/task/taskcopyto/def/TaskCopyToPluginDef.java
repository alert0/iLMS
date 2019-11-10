package com.hotent.bpmx.plugin.task.taskcopyto.def;

import java.util.ArrayList;
import java.util.List;

import com.hotent.bpmx.plugin.core.plugindef.AbstractBpmTaskPluginDef;
import com.hotent.bpmx.plugin.task.taskcopyto.def.model.CopyToItem;

public class TaskCopyToPluginDef extends AbstractBpmTaskPluginDef{
	private List<CopyToItem> copyToItems = new ArrayList<CopyToItem>();

	public List<CopyToItem> getCopyToItems() {
		return copyToItems;
	}

	public void setCopyToItems(List<CopyToItem> copyToItems) {
		this.copyToItems = copyToItems;
	}
	
}
