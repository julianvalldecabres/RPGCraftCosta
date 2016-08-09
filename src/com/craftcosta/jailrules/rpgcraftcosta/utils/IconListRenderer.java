/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author jail
 */
public class IconListRenderer 
	extends DefaultListCellRenderer {

	private Map<Object, Icon> icons = null;
	
    /**
     *
     * @param icons
     */
    public IconListRenderer(Map<Object, Icon> icons) {
		this.icons = icons;
	}
	
	@Override
	public Component getListCellRendererComponent(
		JList list, Object value, int index, 
		boolean isSelected, boolean cellHasFocus) {
		
		// Get the renderer component from parent class
		
		JLabel label = 
			(JLabel) super.getListCellRendererComponent(list, 
				value, index, isSelected, cellHasFocus);
		
		// Get icon to use for the list item value
		
		Icon icon = icons.get(value);
		
		// Set icon to display for value
		
		label.setIcon(icon);
		return label;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// setup mappings for which icon to use for each value
		
		Map<Object, Icon> icons = new HashMap<Object, Icon>();
		icons.put("details", 
			MetalIconFactory.getFileChooserDetailViewIcon());
		icons.put("folder", 
			MetalIconFactory.getTreeFolderIcon());
		icons.put("computer", 
			MetalIconFactory.getTreeComputerIcon());
		
		JFrame frame = new JFrame("Icon List");
		frame.setDefaultCloseOperation(
			JFrame.DISPOSE_ON_CLOSE);
		
		// create a list with some test data
		
		JComboBox combo = new JComboBox(
			new Object[] { 
				"details", "computer", "folder", "computer"});
		
		// create a cell renderer to add the appropriate icon
		
		combo.setRenderer(new IconListRenderer(icons));
		frame.add(combo);
		frame.pack();
		frame.setVisible(true);
	}

}
