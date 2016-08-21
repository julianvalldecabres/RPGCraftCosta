/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author jail
 */
public class ComboBoxRendererBehaviour extends JLabel implements ListCellRenderer {

    private Font uhOhFont;

    public ComboBoxRendererBehaviour() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        int selectedIndex = ((Integer) value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        ImageIcon icon = GUI.images6.get(selectedIndex);
        String imageName = GUI.imagesNames6.get(selectedIndex);
        setIcon(icon);
        if (icon != null) {
            setText(imageName);
            setFont(list.getFont());
        } else {
            setUhOhText(imageName + " (no image available)",
                    list.getFont());
        }

        return this;
    }

    //Set the font and text when no image was found.
    protected void setUhOhText(String uhOhText, Font normalFont) {
        if (uhOhFont == null) { //lazily create this font
            uhOhFont = normalFont.deriveFont(Font.ITALIC);
        }
        setFont(uhOhFont);
        setText(uhOhText);
    }
}
