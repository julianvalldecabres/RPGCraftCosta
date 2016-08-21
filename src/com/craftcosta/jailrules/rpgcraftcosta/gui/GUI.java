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
package com.craftcosta.jailrules.rpgcraftcosta.gui;

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmor;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumEntities;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumPotions;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumWeapon;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorMaterial;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumAttackType;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.leveling.RPGLevels;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.bukkit.ChatColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sun.misc.Launcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jail
 */
public class GUI extends JFrame {

    //Weapons variables
    public static List<ImageIcon> images1;
    public static List<String> imagesNames1;
    //Items variables
    public static List<ImageIcon> images2;
    public static List<String> imagesNames2;
    //Armor variables
    public static List<ImageIcon> images3;
    public static List<String> imagesNames3;
    //Potion Variables
    public static List<ImageIcon> images4;
    public static List<String> imagesNames4;
    //Entities Variables
    public static List<ImageIcon> images5;
    public static List<String> imagesNames5;
    //Entities Behaviour type
    public static List<ImageIcon> images6;
    public static List<String> imagesNames6;
    //Tipos de ataque
    public static List<ImageIcon> images7;
    public static List<String> imagesNames7;
    //Tipos de calidad
    public static List<ImageIcon> images8;
    public static List<String> imagesNames8;
    //Tipos de material de armadura
    public static List<ImageIcon> images9;
    public static List<String> imagesNames9;

    private static void copy(FileInputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultDirectories() {
        //TODO AUTOMATIZAR EL PROCESO DE GENERACION DE LOS FICHEROS DE CONFIGURACION

        File file = new File(RPGFinals.pluginNameFolder);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Creating default config folders and files", "RPGCraftCosta-Information", 1);
            file.mkdirs();
        } else {
            JOptionPane.showMessageDialog(null, "Loading config from config files\nIf any config file or folder doesn't exists will be created by default", "RPGCraftCosta-Information", 1);
        }

        //LISTADO DE FICHEROS YAMLS PARA COPIAR POR DEFECTO
        final String path = "";
        final File jarFile = new File(GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile()) {
            try {
                // Run with JAR file
                final JarFile jar;
                jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith(path /*+ "/"*/)) {
                        System.out.println("1");
                        System.out.println(name);
                        System.out.println("2");
                    }
                }
                jar.close();
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { // Run with IDE
            final URL url = Launcher.class.getResource("/" + path);
            if (url != null) {
                try {
                    List<String> inexistentfiles = new ArrayList<>();
                    final File apps = new File(url.toURI());
                    for (File app : apps.listFiles()) {
                        if (app.getName().contains(".yml")) {

                            File configfile = new File(file.getAbsolutePath() + File.separator + app.getName());
                            if (!configfile.exists()) {
                                inexistentfiles.add(app.getName());
                                InputStream in = GUI.class.getClassLoader().getResourceAsStream(app.getName());
                                FileOutputStream fos = null;
                                try {
                                    fos = new FileOutputStream(file.getAbsolutePath() + File.separator + app.getName());
                                    byte[] buf = new byte[2048];
                                    int r = in.read(buf);
                                    while (r != -1) {
                                        fos.write(buf, 0, r);
                                        r = in.read(buf);
                                    }
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                } finally {
                                    if (fos != null) {
                                        fos.close();
                                    }
                                }
                            }

                        }
                    }
                    if (!inexistentfiles.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Los siguientes ficheros de configuracion no existen:\n"
                                + getList(inexistentfiles) + "Se añadira/n su version por defecto");
                    }

                } catch (URISyntaxException | IOException ex) {

                }
            }
        }
        //Cargar las diferentes configuraciones con sus propios gestores
    }

    private static String getList(List<String> inexistentfiles) {
        String cadena = "";
        for (String inexistentfile : inexistentfiles) {
            cadena += "- " + inexistentfile + "\n";
        }
        return cadena;
    }

    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = GUI.class.getResource(path);
        if (imgURL != null) {
            //System.err.println("Found file: " + path);
            return new ImageIcon(imgURL);

        } else {
            //System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Creates new form RPGCraftCosta
     */
    public GUI() {
        initComponents();
        for (ChatColor color : ChatColor.values()) {
            comboPrefixColorMarketChat.addItem(color.name());
            comboPrefixColorGlobalChat.addItem(color.name());
            comboPrefixColorGuildChat.addItem(color.name());
            comboPrefixColorLocalChat.addItem(color.name());
            comboPrefixColorPartyChat.addItem(color.name());
            comboPrefixColorPrivateChat.addItem(color.name());
            comboPrefixColorNewsChat.addItem(color.name());
            comboPrefixColorWarningChat.addItem(color.name());

            comboMessageColorGlobalChat.addItem(color.name());
            comboMessageColorWarningChat.addItem(color.name());
            comboMessageColorGuildChat.addItem(color.name());
            comboMessageColorLocalChat.addItem(color.name());
            comboMessageColorPrivateChat.addItem(color.name());
            comboMessageColorMarketChat.addItem(color.name());
            comboMessageColorNewsChat.addItem(color.name());
            comboMessageColorPartyChat.addItem(color.name());
        }
        String caracteresEspeciales = "#@$%&\\=?¿¡!'*^{}[],;.:-_<>";
        for (int i = 0; i < caracteresEspeciales.length(); i++) {
            comboShortcutMarketChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutGlobalChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutGuildChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutNewsChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutPartyChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutPrivateChat.addItem(caracteresEspeciales.charAt(i));
            comboShortcutWarningChat.addItem(caracteresEspeciales.charAt(i));
        }
        int count = 0;
        imagesNames1 = new ArrayList<>();
        images1 = new ArrayList<>();
        List<Integer> intArray = new ArrayList<>();
        for (EnumWeapon weapon : EnumWeapon.values()) {
            String name = weapon.name().replaceAll("_", " ").toLowerCase();
            intArray.add(count);
            imagesNames1.add(name);
            images1.add(createImageIcon(weapon.getPath()));
            count++;
        }
        //prepare item combobox
        int count2 = 0;
        imagesNames2 = new ArrayList<>();
        images2 = new ArrayList<>();
        List<Integer> intArray2 = new ArrayList<>();
        for (EnumItems items : EnumItems.values()) {
            String name2 = items.name().replaceAll("_", " ").toLowerCase();
            intArray2.add(count2);
            imagesNames2.add(name2);
            images2.add(createImageIcon(items.getPath()));
            count2++;
        }
        //prepare armor combobox
        int count3 = 0;
        imagesNames3 = new ArrayList<>();
        images3 = new ArrayList<>();
        List<Integer> intArray3 = new ArrayList<>();
        for (EnumArmor items : EnumArmor.values()) {
            String name3 = items.name().replaceAll("_", " ").toLowerCase();
            intArray3.add(count3);
            imagesNames3.add(name3);
            images3.add(createImageIcon(items.getPath()));
            count3++;
        }
        //prepare potion combobox
        int count4 = 0;
        imagesNames4 = new ArrayList<>();
        images4 = new ArrayList<>();
        List<Integer> intArray4 = new ArrayList<>();
        for (EnumPotions items : EnumPotions.values()) {
            String name4 = items.name().replaceAll("_", " ").toLowerCase();
            intArray4.add(count4);
            imagesNames4.add(name4);
            images4.add(createImageIcon(items.getPath()));
            count4++;
        }
        //prepare entities combobox
        int count5 = 0;
        imagesNames5 = new ArrayList<>();
        images5 = new ArrayList<>();
        List<Integer> intArray5 = new ArrayList<>();
        for (EnumEntities items : EnumEntities.values()) {
            String name5 = items.name().replaceAll("_", " ").toLowerCase();
            intArray5.add(count5);
            imagesNames5.add(name5);
            images5.add(createImageIcon(items.getPath()));
            count5++;
        }
        //prepare behaviours
        int count6 = 0;
        imagesNames6 = new ArrayList<>();
        images6 = new ArrayList<>();
        List<Integer> intArray6 = new ArrayList<>();
        for (EnumBehaviour items : EnumBehaviour.values()) {
            String name6 = items.name().replaceAll("_", " ").toLowerCase();
            intArray6.add(count6);
            imagesNames6.add(name6);
            images6.add(createImageIcon(items.getPath()));
            count6++;
        }
        //prepare attack types
        int count7 = 0;
        imagesNames7 = new ArrayList<>();
        images7 = new ArrayList<>();
        List<Integer> intArray7 = new ArrayList<>();
        for (EnumAttackType items : EnumAttackType.values()) {
            String name7 = items.name().replaceAll("_", " ").toLowerCase();
            intArray7.add(count7);
            imagesNames7.add(name7);
            images7.add(createImageIcon(items.getPath()));
            count7++;
        }
        //prepare quality types
        int count8 = 0;
        imagesNames8 = new ArrayList<>();
        images8 = new ArrayList<>();
        List<Integer> intArray8 = new ArrayList<>();
        for (EnumQuality items : EnumQuality.values()) {
            String name8 = items.name().replaceAll("_", " ").toLowerCase();
            intArray8.add(count8);
            imagesNames8.add(name8);
            images8.add(createImageIcon(items.getPath()));
            count8++;
        }
        //prepare armor material
        int count9 = 0;
        imagesNames9 = new ArrayList<>();
        images9 = new ArrayList<>();
        List<Integer> intArray9 = new ArrayList<>();
        for (EnumArmorMaterial items : EnumArmorMaterial.values()) {
            String name9 = items.name().replaceAll("_", " ").toLowerCase();
            intArray9.add(count9);
            imagesNames9.add(name9);
            images9.add(createImageIcon(items.getPath()));
            count9++;
        }

        this.comboTipoArma.removeAllItems();
        this.comboCalidadArma.removeAllItems();

        this.comboTipoObjeto.removeAllItems();
        this.comboCalidadObjeto.removeAllItems();

        this.comboSelectorTipoMob.removeAllItems();
        this.comboComportamiento.removeAllItems();
        this.comboTipoAtaqueMob.removeAllItems();

        this.comboCalidad.removeAllItems();
        this.comboMaterial.removeAllItems();

        this.comboObjetoJoya.removeAllItems();
        this.comboCalidadJoya.removeAllItems();

        ComboBoxRendererWeapons renderer = new ComboBoxRendererWeapons();
        renderer.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererQualityType renderer2 = new ComboBoxRendererQualityType();
        renderer2.setPreferredSize(new Dimension(225, 50));

        ComboBoxRendererItems renderer3 = new ComboBoxRendererItems();
        renderer3.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererQualityType renderer4 = new ComboBoxRendererQualityType();
        renderer4.setPreferredSize(new Dimension(225, 50));

        ComboBoxRendererEntities renderer5 = new ComboBoxRendererEntities();
        renderer5.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererBehaviour renderer6 = new ComboBoxRendererBehaviour();
        renderer6.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererAttackType renderer7 = new ComboBoxRendererAttackType();
        renderer7.setPreferredSize(new Dimension(225, 50));

        ComboBoxRendererQualityType renderer8 = new ComboBoxRendererQualityType();
        renderer8.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererArmorMaterial renderer9 = new ComboBoxRendererArmorMaterial();
        renderer9.setPreferredSize(new Dimension(225, 50));

        ComboBoxRendererQualityType renderer10 = new ComboBoxRendererQualityType();
        renderer10.setPreferredSize(new Dimension(225, 50));
        ComboBoxRendererItems renderer11 = new ComboBoxRendererItems();
        renderer11.setPreferredSize(new Dimension(225, 50));

        //ARMAS
        JComboBox tipoarma = (JComboBox) panelEditorArma.getComponent(panelEditorArma.getComponentZOrder(this.comboTipoArma));
        tipoarma.setRenderer(renderer);
        tipoarma.setMaximumRowCount(3);
        for (Integer i : intArray) {
            tipoarma.addItem(i);
        }
        JComboBox calidadarma = (JComboBox) panelEditorArma.getComponent(panelEditorArma.getComponentZOrder(this.comboCalidadArma));
        calidadarma.setRenderer(renderer2);
        calidadarma.setMaximumRowCount(3);
        for (Integer i : intArray8) {
            calidadarma.addItem(i);
        }
        //OBJETOS
        JComboBox tipoobjeto = (JComboBox) panelEditorObjetos.getComponent(panelEditorObjetos.getComponentZOrder(this.comboTipoObjeto));
        tipoobjeto.setRenderer(renderer3);
        tipoobjeto.setMaximumRowCount(3);
        for (Integer i : intArray2) {
            tipoobjeto.addItem(i);
        }
        JComboBox calidadobjeto = (JComboBox) panelEditorObjetos.getComponent(panelEditorObjetos.getComponentZOrder(this.comboCalidadObjeto));
        calidadobjeto.setRenderer(renderer2);
        calidadobjeto.setMaximumRowCount(3);
        for (Integer i : intArray8) {
            calidadobjeto.addItem(i);
        }
        //MONSTRUOS
        JComboBox tipomob = (JComboBox) panelEditorMobs.getComponent(panelEditorMobs.getComponentZOrder(this.comboSelectorTipoMob));
        tipomob.setRenderer(renderer5);
        tipomob.setMaximumRowCount(3);
        for (Integer i : intArray5) {
            tipomob.addItem(i);
        }
        JComboBox mobcomp = (JComboBox) panelComportamientoMob.getComponent(panelComportamientoMob.getComponentZOrder(this.comboComportamiento));
        mobcomp.setRenderer(renderer6);
        mobcomp.setMaximumRowCount(3);
        for (Integer i : intArray6) {
            mobcomp.addItem(i);
        }
        JComboBox mobatac = (JComboBox) panelAtaqueMobs.getComponent(panelAtaqueMobs.getComponentZOrder(this.comboTipoAtaqueMob));
        mobatac.setRenderer(renderer7);
        mobatac.setMaximumRowCount(3);
        for (Integer i : intArray7) {
            mobatac.addItem(i);
        }
        //ARMADURAS
        JComboBox tipoarm = (JComboBox) panelEditarSet.getComponent(panelEditarSet.getComponentZOrder(this.comboMaterial));
        tipoarm.setRenderer(renderer9);
        tipoarm.setMaximumRowCount(3);
        for (Integer i : intArray9) {
            tipoarm.addItem(i);
        }
        JComboBox calidadarm = (JComboBox) panelEditarSet.getComponent(panelEditarSet.getComponentZOrder(this.comboCalidad));
        calidadarm.setRenderer(renderer8);
        calidadarm.setMaximumRowCount(3);
        for (Integer i : intArray8) {
            calidadarm.addItem(i);
        }
        //JOYAS
        JComboBox tipojoy = (JComboBox) panelEditorJoya.getComponent(panelEditorJoya.getComponentZOrder(this.comboObjetoJoya));
        tipojoy.setRenderer(renderer3);
        tipojoy.setMaximumRowCount(3);
        for (Integer i : intArray2) {
            tipojoy.addItem(i);
        }
        JComboBox calidadjoy = (JComboBox) panelEditorJoya.getComponent(panelEditorJoya.getComponentZOrder(this.comboCalidadJoya));
        calidadjoy.setRenderer(renderer8);
        calidadjoy.setMaximumRowCount(3);
        for (Integer i : intArray8) {
            calidadjoy.addItem(i);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngroupNumJugGrupo = new javax.swing.ButtonGroup();
        btngroupPvpGrupo = new javax.swing.ButtonGroup();
        btngroupRepartoGrupo = new javax.swing.ButtonGroup();
        btnGroupOpcionRepartoGrupo = new javax.swing.ButtonGroup();
        btngroupOpcionRepartoPropGrupo = new javax.swing.ButtonGroup();
        btngroupNumJugClan = new javax.swing.ButtonGroup();
        btngroupOpcLimNivelClan = new javax.swing.ButtonGroup();
        btngroupSistContrClan = new javax.swing.ButtonGroup();
        panelConfig = new javax.swing.JTabbedPane();
        panelInicio = new javax.swing.JPanel();
        btnprueba = new javax.swing.JButton();
        panelConfigGeneral = new javax.swing.JPanel();
        panelConfigGeneralGen = new javax.swing.JPanel();
        panelConfigDanioGeneral = new javax.swing.JPanel();
        checkSistHambre = new javax.swing.JCheckBox();
        checkDanioCaida = new javax.swing.JCheckBox();
        checkDanioAhogo = new javax.swing.JCheckBox();
        checkDanioPvp = new javax.swing.JCheckBox();
        panelEnableModulosGeneral = new javax.swing.JPanel();
        checkClanes = new javax.swing.JCheckBox();
        checkGrupos = new javax.swing.JCheckBox();
        panelConfigInicio = new javax.swing.JPanel();
        lblMundoInicio = new javax.swing.JLabel();
        txtMundoInicio = new javax.swing.JTextField();
        lblXInicio = new javax.swing.JLabel();
        lblYInicio = new javax.swing.JLabel();
        lblZInicio = new javax.swing.JLabel();
        spinnerXInicio = new javax.swing.JSpinner();
        spinnerYInicio = new javax.swing.JSpinner();
        spinnerZInicio = new javax.swing.JSpinner();
        panelConfigPlayers = new javax.swing.JPanel();
        checkColocarBloques = new javax.swing.JCheckBox();
        checkDestruirBloques = new javax.swing.JCheckBox();
        btnGuardarConfigGlobal = new javax.swing.JButton();
        panelConfigDayCycle = new javax.swing.JPanel();
        checkDayCycle = new javax.swing.JCheckBox();
        panelConfigLevel = new javax.swing.JPanel();
        lblx3Nivel = new javax.swing.JLabel();
        lblx2Nivel = new javax.swing.JLabel();
        lblxNivel = new javax.swing.JLabel();
        lblFdeXNivel = new javax.swing.JLabel();
        btnValidarFormulaNiveles = new javax.swing.JButton();
        btnGuardarConfigNiveles = new javax.swing.JButton();
        lblInfoNivel = new javax.swing.JLabel();
        lblInfoNivel2 = new javax.swing.JLabel();
        btnDibujarGraficoNiveles = new javax.swing.JButton();
        lblInfoNivelMaxNivel = new javax.swing.JLabel();
        txtX3Nivel = new javax.swing.JSpinner();
        txtX2Nivel = new javax.swing.JSpinner();
        txtXNivel = new javax.swing.JSpinner();
        lblMaxNivelNivel = new javax.swing.JSpinner();
        panelGraficosNivel = new javax.swing.JPanel();
        panelNivelExpNivel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaExpNivel = new javax.swing.JTable();
        lblPHxNivel = new javax.swing.JLabel();
        spinnerPHxNivel = new javax.swing.JSpinner();
        panelConfigClases = new javax.swing.JPanel();
        comboSelectorClases = new javax.swing.JComboBox();
        btnCrearNuevaClase = new javax.swing.JButton();
        btnEditarClase = new javax.swing.JButton();
        btnEliminarClase = new javax.swing.JButton();
        panelEditorClase = new javax.swing.JPanel();
        lblNombreClase = new javax.swing.JLabel();
        panelAtribBasicoClase = new javax.swing.JPanel();
        lblVidaBase = new javax.swing.JLabel();
        lblManaBase = new javax.swing.JLabel();
        lblAtaFisBase = new javax.swing.JLabel();
        lblDefFisBase = new javax.swing.JLabel();
        lblHRFisBase = new javax.swing.JLabel();
        lblEvaFisBase = new javax.swing.JLabel();
        lblAtaMagBase = new javax.swing.JLabel();
        lblDefMagBase = new javax.swing.JLabel();
        lblHRMagBase = new javax.swing.JLabel();
        lblEvaMagBase = new javax.swing.JLabel();
        lblCritBase = new javax.swing.JLabel();
        lblMortalBase = new javax.swing.JLabel();
        spinnerMaxVidaBase = new javax.swing.JSpinner();
        spinnerMaxManaBase = new javax.swing.JSpinner();
        spinnerDefFisBase = new javax.swing.JSpinner();
        spinnerAtaFisBase = new javax.swing.JSpinner();
        spinnerHRFisBase = new javax.swing.JSpinner();
        spinnerEvaBase = new javax.swing.JSpinner();
        spinnerAtaMagBase = new javax.swing.JSpinner();
        spinnerDefMagBase = new javax.swing.JSpinner();
        spinnerHRMagBase = new javax.swing.JSpinner();
        spinnerEvaMagBase = new javax.swing.JSpinner();
        spinnerCritBase = new javax.swing.JSpinner();
        spinnerMortalBase = new javax.swing.JSpinner();
        txtNombreClase = new javax.swing.JTextField();
        checkEnableClase = new javax.swing.JCheckBox();
        btnGuardarClase = new javax.swing.JButton();
        btnResetClase = new javax.swing.JButton();
        panelSubirNivel = new javax.swing.JPanel();
        lblVidaNivel = new javax.swing.JLabel();
        lblManaNivel = new javax.swing.JLabel();
        lblAtaFisNivel = new javax.swing.JLabel();
        lblDefFisNivel = new javax.swing.JLabel();
        lblHRFisNivel = new javax.swing.JLabel();
        lblEvaFisNivel = new javax.swing.JLabel();
        lblAtaMagNivel = new javax.swing.JLabel();
        lblDefMagNivel = new javax.swing.JLabel();
        lblHRMagNivel = new javax.swing.JLabel();
        lblEvaMagNivel = new javax.swing.JLabel();
        lblCritNivel = new javax.swing.JLabel();
        lblMortalNivel = new javax.swing.JLabel();
        spinnerVidaNivel = new javax.swing.JSpinner();
        spinnerManaNivel = new javax.swing.JSpinner();
        spinnerAtaFisNivel = new javax.swing.JSpinner();
        spinnerDefFisNivel = new javax.swing.JSpinner();
        spinnerHRFisNivel = new javax.swing.JSpinner();
        spinnerEvaFisNivel = new javax.swing.JSpinner();
        spinnerAtaMagNivel = new javax.swing.JSpinner();
        spinnerDefMagNivel = new javax.swing.JSpinner();
        spinnerHRMagNivel = new javax.swing.JSpinner();
        spinnerEvaMagNivel = new javax.swing.JSpinner();
        spinnerCritNivel = new javax.swing.JSpinner();
        spinnerMortalNivel = new javax.swing.JSpinner();
        panelPuntoHabilidad = new javax.swing.JPanel();
        panelHabFuerza = new javax.swing.JPanel();
        lblVidaAP = new javax.swing.JLabel();
        lblAtaFisAP = new javax.swing.JLabel();
        spinnerVidaAP = new javax.swing.JSpinner();
        spinnerAtaFisAP = new javax.swing.JSpinner();
        panelHabConst = new javax.swing.JPanel();
        lblVidaAP2 = new javax.swing.JLabel();
        lblManaAP = new javax.swing.JLabel();
        spinnerVidaAP2 = new javax.swing.JSpinner();
        spinnerManaAP = new javax.swing.JSpinner();
        panelHabInt = new javax.swing.JPanel();
        lblManaAP2 = new javax.swing.JLabel();
        lblAtaMagAP = new javax.swing.JLabel();
        spinnerManaAP2 = new javax.swing.JSpinner();
        spinnerAtaMagAP = new javax.swing.JSpinner();
        panelHabDest = new javax.swing.JPanel();
        lblCritAP = new javax.swing.JLabel();
        lblMortalAP = new javax.swing.JLabel();
        spinnerCrtitAP = new javax.swing.JSpinner();
        spinnerMortalAP = new javax.swing.JSpinner();
        panelConfigObjetos = new javax.swing.JPanel();
        btnCrearNuevoObjeto = new javax.swing.JButton();
        panelEditorObjetos = new javax.swing.JPanel();
        lblNombreObjeto = new javax.swing.JLabel();
        txtNombreObjeto = new javax.swing.JTextField();
        lblTipoObjeto = new javax.swing.JLabel();
        comboTipoObjeto = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        listDescripcionObjeto = new javax.swing.JList();
        lblDescripObjeto = new javax.swing.JLabel();
        txtDescripObjeto = new javax.swing.JTextField();
        btnAnadirDesObjeto = new javax.swing.JButton();
        btnEditarDesObjeto = new javax.swing.JButton();
        btnEliminarDesObjeto = new javax.swing.JButton();
        btnGuardarObjeto = new javax.swing.JButton();
        lblCalidadObjeto = new javax.swing.JLabel();
        comboCalidadObjeto = new javax.swing.JComboBox();
        comboListaObjetos = new javax.swing.JComboBox();
        btnEditarObjeto = new javax.swing.JButton();
        btnEliminarObjeto = new javax.swing.JButton();
        panelConfigWeapon = new javax.swing.JPanel();
        panelMejoraArma = new javax.swing.JPanel();
        btnGuardarConfigArma = new javax.swing.JButton();
        lblTipoMejoradorArma = new javax.swing.JLabel();
        lblNomMejoraArma = new javax.swing.JLabel();
        txtNombreObjetoMejArma = new javax.swing.JTextField();
        comboMejoradorArma = new javax.swing.JComboBox();
        txtDescripcionArma = new javax.swing.JTextField();
        lblDescripcionArma = new javax.swing.JLabel();
        btnAnadirDescArma = new javax.swing.JButton();
        btnEditarDEscArma = new javax.swing.JButton();
        btnEliminarDescArma = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListDescArma = new javax.swing.JList();
        lblProbRotArma = new javax.swing.JLabel();
        lblProbDetArma = new javax.swing.JLabel();
        lblProbSEArma = new javax.swing.JLabel();
        lblProbExitoArma = new javax.swing.JLabel();
        spinnerProbRotArma = new javax.swing.JSpinner();
        spinnerProbDetArma = new javax.swing.JSpinner();
        spinnerProSEArma = new javax.swing.JSpinner();
        spinerProbExitoArma = new javax.swing.JSpinner();
        panelSelectorArma = new javax.swing.JPanel();
        btnCrearNuevaArma = new javax.swing.JButton();
        panelEditorArma = new javax.swing.JPanel();
        lblTipoArma = new javax.swing.JLabel();
        comboTipoArma = new javax.swing.JComboBox();
        lblNombreArma = new javax.swing.JLabel();
        txtNombreArma = new javax.swing.JTextField();
        lblNivelMinArma = new javax.swing.JLabel();
        spinnerNivelMinArma = new javax.swing.JSpinner();
        lblNivelIniArma = new javax.swing.JLabel();
        spinnerNivelIniArma = new javax.swing.JSpinner();
        lblCalidadArma = new javax.swing.JLabel();
        comboCalidadArma = new javax.swing.JComboBox();
        checkComerciableArma = new javax.swing.JCheckBox();
        checkMejorableArma = new javax.swing.JCheckBox();
        lblPrecioCArma = new javax.swing.JLabel();
        spinnerPrecioCArma = new javax.swing.JSpinner();
        lblPrecioVArma = new javax.swing.JLabel();
        spinnerPrecioVArma = new javax.swing.JSpinner();
        lblDineroExtraArma = new javax.swing.JLabel();
        spinnerDineroExtraArma = new javax.swing.JSpinner();
        lblExpExtraArma = new javax.swing.JLabel();
        spinnerExpExtraArma = new javax.swing.JSpinner();
        lblAtaFisArma = new javax.swing.JLabel();
        spinnerAtaFisArma = new javax.swing.JSpinner();
        lblMejAtaFisArma = new javax.swing.JLabel();
        spinnerMejAtaFisArma = new javax.swing.JSpinner();
        lblAtaMagArma = new javax.swing.JLabel();
        spinnerAtaMagArma = new javax.swing.JSpinner();
        lblMejAtaMagArma = new javax.swing.JLabel();
        spinnerMejAtaMagArma = new javax.swing.JSpinner();
        lblHRFisArma = new javax.swing.JLabel();
        lblHRMagArma = new javax.swing.JLabel();
        lblMejHRFisArma = new javax.swing.JLabel();
        lblMejHRMagArma = new javax.swing.JLabel();
        spinnerHRFisArma = new javax.swing.JSpinner();
        spinnerMejHRFisArma = new javax.swing.JSpinner();
        spinnerHRMagArma = new javax.swing.JSpinner();
        spinnerMejHRMagArma = new javax.swing.JSpinner();
        lblCritArma = new javax.swing.JLabel();
        lblMejCritArma = new javax.swing.JLabel();
        spinnerCritArma = new javax.swing.JSpinner();
        spinnerMejCritArma = new javax.swing.JSpinner();
        lvlRoboVArma = new javax.swing.JLabel();
        lvlMejRoboVArma = new javax.swing.JLabel();
        spinnerRoboVArma = new javax.swing.JSpinner();
        spinnerMejRoboVArma = new javax.swing.JSpinner();
        lblRoboMArma = new javax.swing.JLabel();
        lblMejRoboMArma = new javax.swing.JLabel();
        spinnerRoboMArma = new javax.swing.JSpinner();
        spinnerMejRoboMArma = new javax.swing.JSpinner();
        btnGuardarArma = new javax.swing.JButton();
        comboListaArmas = new javax.swing.JComboBox();
        btnEditarArma = new javax.swing.JButton();
        btnEliminarArma = new javax.swing.JButton();
        panelConfigJewels = new javax.swing.JPanel();
        panelConfigJoyas = new javax.swing.JPanel();
        lblProbRotJoya = new javax.swing.JLabel();
        spinnerProbRotJoya = new javax.swing.JSpinner();
        spinnerProbDetJoya = new javax.swing.JSpinner();
        lblProbDetJoya = new javax.swing.JLabel();
        lblProbSEJoya = new javax.swing.JLabel();
        spinnerProbSEJoya = new javax.swing.JSpinner();
        spinnerProbExitoJoya = new javax.swing.JSpinner();
        lblProbExitoJoya = new javax.swing.JLabel();
        btnGuardarConfigJoyas = new javax.swing.JButton();
        panelSelectorJoyas = new javax.swing.JPanel();
        btnCrearNuevaJoya = new javax.swing.JButton();
        panelEditorJoya = new javax.swing.JPanel();
        lblNombreJoya = new javax.swing.JLabel();
        txtNombreJoya = new javax.swing.JTextField();
        lblObjetoJoya = new javax.swing.JLabel();
        comboObjetoJoya = new javax.swing.JComboBox();
        lblCalidadJoya = new javax.swing.JLabel();
        comboCalidadJoya = new javax.swing.JComboBox();
        lblPrecioVJoya = new javax.swing.JLabel();
        lblPrecioCJoya = new javax.swing.JLabel();
        checkComerciable = new javax.swing.JCheckBox();
        checkCombinable = new javax.swing.JCheckBox();
        spinnerPrecioVJoya = new javax.swing.JSpinner();
        spinnerPrecioCJoya = new javax.swing.JSpinner();
        lblRoboMJoya = new javax.swing.JLabel();
        lblDefFisJoya = new javax.swing.JLabel();
        lblDefMagJoya = new javax.swing.JLabel();
        lblHRFisJoya = new javax.swing.JLabel();
        lblEvaFisJoya = new javax.swing.JLabel();
        lblAtaFisJoya = new javax.swing.JLabel();
        lblManaJoya = new javax.swing.JLabel();
        lblManaVidaJoya = new javax.swing.JLabel();
        lblAtaMagJoya = new javax.swing.JLabel();
        lvlEvaMagJoya = new javax.swing.JLabel();
        lblDineroExtraJoya = new javax.swing.JLabel();
        lblExpExtraJoya = new javax.swing.JLabel();
        lblHRMagJoya = new javax.swing.JLabel();
        lblRoboVJoya = new javax.swing.JLabel();
        spinnerDineroExtraJoya = new javax.swing.JSpinner();
        spinnerExpExtraJoya = new javax.swing.JSpinner();
        spinnerRoboVJoya = new javax.swing.JSpinner();
        spinnerRoboMJoya = new javax.swing.JSpinner();
        spinnerVidaJoya = new javax.swing.JSpinner();
        spinnerManaJoya = new javax.swing.JSpinner();
        spinnerAtaFisJoya = new javax.swing.JSpinner();
        spinnerEvaFisJoya = new javax.swing.JSpinner();
        spinnerHRFisJoya = new javax.swing.JSpinner();
        spinnerDefFisJoya = new javax.swing.JSpinner();
        spinnerAtaMagJoya = new javax.swing.JSpinner();
        spinnerEvaMagJoya = new javax.swing.JSpinner();
        spinnerHRMagJoya = new javax.swing.JSpinner();
        spinnerDefMagJoya = new javax.swing.JSpinner();
        btnGuardarJoya = new javax.swing.JButton();
        comboSelectorJoyas = new javax.swing.JComboBox();
        btnEditarJoya = new javax.swing.JButton();
        btnEliminarJoya = new javax.swing.JButton();
        panelConfigSpawners = new javax.swing.JPanel();
        comboSelectorGenerador = new javax.swing.JComboBox();
        btnEditarSpawner = new javax.swing.JButton();
        btnEliminarSpawner = new javax.swing.JButton();
        panelEditorSpawner = new javax.swing.JPanel();
        panelLocSpawner = new javax.swing.JPanel();
        lblMundoSpawnLoc = new javax.swing.JLabel();
        lblXSpawnLoc = new javax.swing.JLabel();
        lblYSpawnLoc = new javax.swing.JLabel();
        lblZSpawnLoc = new javax.swing.JLabel();
        txtMundoSpawnLoc = new javax.swing.JTextField();
        spinnerXSpawnLoc = new javax.swing.JSpinner();
        spinnerYSpawnLoc = new javax.swing.JSpinner();
        spinnerZSpawnLoc = new javax.swing.JSpinner();
        panelAtribSpawner = new javax.swing.JPanel();
        lblMobSpawner = new javax.swing.JLabel();
        comboMobSpawner = new javax.swing.JComboBox();
        lblRefrescoSpawner = new javax.swing.JLabel();
        lblIdSpawner = new javax.swing.JLabel();
        spinnerRefrescoSpawner = new javax.swing.JSpinner();
        lblRadioSpawner = new javax.swing.JLabel();
        spinnerRadioSpawner = new javax.swing.JSpinner();
        lblNumMaxMobSpawner = new javax.swing.JLabel();
        spinnerNumMaxMobSpawner = new javax.swing.JSpinner();
        checkEnableSpawner = new javax.swing.JCheckBox();
        comboSelectorSpawner = new javax.swing.JTextField();
        btnGuardarSpawner = new javax.swing.JButton();
        btnResetSpawner = new javax.swing.JButton();
        btnCrearSpawner = new javax.swing.JButton();
        panelConfigGuilds = new javax.swing.JPanel();
        btnResetConfigClan = new javax.swing.JButton();
        btnGuardarConfigClan = new javax.swing.JButton();
        panelConfigClanes = new javax.swing.JPanel();
        panelNumJugClan = new javax.swing.JPanel();
        rdBtnNumIlimitadosClan = new javax.swing.JRadioButton();
        rdBtnNumFijosClan = new javax.swing.JRadioButton();
        rdBtnLimitNivClan = new javax.swing.JRadioButton();
        spinnerNumFijosClan = new javax.swing.JSpinner();
        lblJugadoresClan = new javax.swing.JLabel();
        panelSisContNivelClan = new javax.swing.JPanel();
        rdBtnContriSoloDon = new javax.swing.JRadioButton();
        rdBtnContriSoloContri = new javax.swing.JRadioButton();
        rdBtnContriAmbos = new javax.swing.JRadioButton();
        spinnerContriPorcentajeMuertes = new javax.swing.JSpinner();
        lblporcMuertesClan = new javax.swing.JLabel();
        lblnivMinJugCrearClan = new javax.swing.JLabel();
        spinnerNivelMinJugClan = new javax.swing.JSpinner();
        panelOpcLimNivelClan = new javax.swing.JPanel();
        rdBtnOpcDinFijo = new javax.swing.JRadioButton();
        rdBtnOpcDinForm = new javax.swing.JRadioButton();
        panelFormulaClan = new javax.swing.JPanel();
        lblfdex = new javax.swing.JLabel();
        spinnerformulax2clan = new javax.swing.JSpinner();
        lblx2clan = new javax.swing.JLabel();
        spinnerformulaxclan = new javax.swing.JSpinner();
        lblxclan = new javax.swing.JLabel();
        lblMaxNivelClan = new javax.swing.JLabel();
        spinnerNivelMaxClan = new javax.swing.JSpinner();
        btnValidarFormulaClan = new javax.swing.JButton();
        btnResetearFormulaClan = new javax.swing.JButton();
        lblFormJugXNivelClan = new javax.swing.JLabel();
        spinnerFormJugXNivelClan = new javax.swing.JSpinner();
        lblFormJugIniClan = new javax.swing.JLabel();
        spinnerFormJugIniClan = new javax.swing.JSpinner();
        panelEditNivelesClan = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaClanes = new javax.swing.JTable();
        btnAnadirNivel = new javax.swing.JButton();
        btnEditarNivel = new javax.swing.JButton();
        btnEliminarNivel = new javax.swing.JButton();
        spinnerDineroEditClan = new javax.swing.JSpinner();
        lblDineroEditNivelClan = new javax.swing.JLabel();
        lblNumJugEditNivelClan = new javax.swing.JLabel();
        spinnerNumJugEditClan = new javax.swing.JSpinner();
        panelConfigParties = new javax.swing.JPanel();
        btnGuardarConfigGrupos = new javax.swing.JButton();
        btnResetConfigGrupos = new javax.swing.JButton();
        panelConfigGrupos = new javax.swing.JPanel();
        panelNumJugGrupos = new javax.swing.JPanel();
        spinnerNumeroFijoJugGrupo = new javax.swing.JSpinner();
        lblJugadoresGrupo = new javax.swing.JLabel();
        rdBtnIlimitadosGrupo = new javax.swing.JRadioButton();
        rdBtnFijosGrupo = new javax.swing.JRadioButton();
        panelPvpGrupos = new javax.swing.JPanel();
        rdBtnPermitidoPvpGrupo = new javax.swing.JRadioButton();
        rdBtnNoPermitidoPvpGrupo = new javax.swing.JRadioButton();
        panelSistRepGrupos = new javax.swing.JPanel();
        rdBtnSinRepartoGrupo = new javax.swing.JRadioButton();
        rdBtnSoloDineroGrupo = new javax.swing.JRadioButton();
        rdBtnSoloExpGrupo = new javax.swing.JRadioButton();
        rdBtnAmbosGrupo = new javax.swing.JRadioButton();
        panelOpcRepGRupos = new javax.swing.JPanel();
        rdBtnOpcionigualitarioGrupo = new javax.swing.JRadioButton();
        rdBtnOpcionProporcionalGrupo = new javax.swing.JRadioButton();
        panelOpcRepProp = new javax.swing.JPanel();
        rdBtnProNivelGrupo = new javax.swing.JRadioButton();
        rdBtnProKillsGrupo = new javax.swing.JRadioButton();
        panelConfigArmor = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        panelConfigArmaduras = new javax.swing.JPanel();
        panelSelectorArmadura = new javax.swing.JPanel();
        lblSelectorSet = new javax.swing.JLabel();
        comboListSets = new javax.swing.JComboBox();
        btnNuevoSet = new javax.swing.JButton();
        btnEditarSet = new javax.swing.JButton();
        btnEliminarSet = new javax.swing.JButton();
        panelEditarSet = new javax.swing.JPanel();
        lblNombreSet = new javax.swing.JLabel();
        txtNombreSet = new javax.swing.JTextField();
        spinnerNivel = new javax.swing.JSpinner();
        lblNivel = new javax.swing.JLabel();
        comboMaterial = new javax.swing.JComboBox();
        lblMaterial = new javax.swing.JLabel();
        comboCalidad = new javax.swing.JComboBox();
        lblCalidad = new javax.swing.JLabel();
        checkComerciableArmadura = new javax.swing.JCheckBox();
        checkMejorableArmadura = new javax.swing.JCheckBox();
        panelPechera = new javax.swing.JPanel();
        lblDefFisPechera = new javax.swing.JLabel();
        lblMejDefFisPechera = new javax.swing.JLabel();
        lblMejEvaFisPechera = new javax.swing.JLabel();
        lblEvaFisPechera = new javax.swing.JLabel();
        spinnerDefFisPechera = new javax.swing.JSpinner();
        spinnerMejDefFisPechera = new javax.swing.JSpinner();
        spinnerEvaFisPechera = new javax.swing.JSpinner();
        spinnerMejEvaFisPechera = new javax.swing.JSpinner();
        lblPrecioVPechera = new javax.swing.JLabel();
        spinnerPrecioVPechera = new javax.swing.JSpinner();
        lblPrecioCPechera = new javax.swing.JLabel();
        spinnerPrecioCPechera = new javax.swing.JSpinner();
        lblExpExtraPechera = new javax.swing.JLabel();
        spinnerExpExtraPechera = new javax.swing.JSpinner();
        spinnerDineroExtraPechera = new javax.swing.JSpinner();
        lblDineroExtraPechera = new javax.swing.JLabel();
        panelPantalones = new javax.swing.JPanel();
        lblDefFisGrebas = new javax.swing.JLabel();
        lblMejDefFisGrebas = new javax.swing.JLabel();
        lvlMejEvaFisGrebas = new javax.swing.JLabel();
        lblEvaFisGrebas = new javax.swing.JLabel();
        spinnerDefFisGrebas = new javax.swing.JSpinner();
        spinnerMejDefFisGrebas = new javax.swing.JSpinner();
        spinnerEvaFisGrebas = new javax.swing.JSpinner();
        spinnerMejEvaFisGrebas = new javax.swing.JSpinner();
        lblPrecioVGrebas = new javax.swing.JLabel();
        spinnerPrecioVGrebas = new javax.swing.JSpinner();
        lblPrecioCGrebas = new javax.swing.JLabel();
        spinnerPrecioCGrebas = new javax.swing.JSpinner();
        lblExpExtraGrebas = new javax.swing.JLabel();
        spinnerExpExtraGrebas = new javax.swing.JSpinner();
        spinnerDineroExtraGrebas = new javax.swing.JSpinner();
        lblDineroExtraGrebas = new javax.swing.JLabel();
        panelBotas = new javax.swing.JPanel();
        lblDefFisBotas = new javax.swing.JLabel();
        lblMejDefFisBotas = new javax.swing.JLabel();
        lblMejEvaFisBotas = new javax.swing.JLabel();
        lblEvaFisBotas = new javax.swing.JLabel();
        spinnerDefFisBotas = new javax.swing.JSpinner();
        spinnerMejDefFisBotas = new javax.swing.JSpinner();
        spinnerEvaFisBotas = new javax.swing.JSpinner();
        spinnerMejEvaFisBotas = new javax.swing.JSpinner();
        lblPrecioVBotas = new javax.swing.JLabel();
        spinnerPrecioVBotas = new javax.swing.JSpinner();
        lblPrecioCBotas = new javax.swing.JLabel();
        spinnerPrecioCBotas = new javax.swing.JSpinner();
        lblExpExtraBotas = new javax.swing.JLabel();
        spinnerExpExtraBotas = new javax.swing.JSpinner();
        spinnerDineroExtraBotas = new javax.swing.JSpinner();
        lblDineroExtraBotas = new javax.swing.JLabel();
        panelCasco = new javax.swing.JPanel();
        lblDefFisCasco = new javax.swing.JLabel();
        lblMejDefFisCasco = new javax.swing.JLabel();
        lblMejEvaFisCasco = new javax.swing.JLabel();
        lblEvaFisCasco = new javax.swing.JLabel();
        spinnerDefFisCasco = new javax.swing.JSpinner();
        spinnerMejDefFisCasco = new javax.swing.JSpinner();
        spinnerEvaFisCasco = new javax.swing.JSpinner();
        spinnerMejEvaFisCasco = new javax.swing.JSpinner();
        lblPrecioVCasco = new javax.swing.JLabel();
        spinnerPrecioVCasco = new javax.swing.JSpinner();
        lblPrecioCCasco = new javax.swing.JLabel();
        spinnerPrecioCCasco = new javax.swing.JSpinner();
        lblExpExtraCasco = new javax.swing.JLabel();
        spinnerExpExtraCasco = new javax.swing.JSpinner();
        spinnerDineroExtraCasco = new javax.swing.JSpinner();
        lblDineroExtraCasco = new javax.swing.JLabel();
        btnGuardarSet = new javax.swing.JButton();
        panelMejoraArmadura = new javax.swing.JPanel();
        btnGuardarConfigArmadura = new javax.swing.JButton();
        lblMejoradorArmadura = new javax.swing.JLabel();
        lblNombreMejoradorArmadura = new javax.swing.JLabel();
        txtNombreObjetoMejArmadura = new javax.swing.JTextField();
        comboMejoradorArmadura = new javax.swing.JComboBox();
        txtDescripcionArmadura = new javax.swing.JTextField();
        lblDescripcionMejoradorArmadura = new javax.swing.JLabel();
        btnAnadirDescArmadura = new javax.swing.JButton();
        btnEditarDEscArmadura = new javax.swing.JButton();
        btnEliminarDescArmadura = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListDescArmadura = new javax.swing.JList();
        lblProbRotArmadura = new javax.swing.JLabel();
        lblProbDetArmadura = new javax.swing.JLabel();
        lblProbSEArmadura = new javax.swing.JLabel();
        lblProbExitoArmadura = new javax.swing.JLabel();
        spinnerProbRotArmadura = new javax.swing.JSpinner();
        spinnerProbDetArmadura = new javax.swing.JSpinner();
        spinnerProbSEArmadura = new javax.swing.JSpinner();
        spinnerProbExitoArmadura = new javax.swing.JSpinner();
        panelConfigMobs = new javax.swing.JPanel();
        comboSelectorMobs = new javax.swing.JComboBox();
        btnNuevoMob = new javax.swing.JButton();
        btnEditMob = new javax.swing.JButton();
        btnEliminarMob = new javax.swing.JButton();
        panelEditorMobs = new javax.swing.JPanel();
        lblNombreMob = new javax.swing.JLabel();
        txtNombreMob = new javax.swing.JTextField();
        btnGuardarMob = new javax.swing.JButton();
        btnResetearMob = new javax.swing.JButton();
        lblNivelMob = new javax.swing.JLabel();
        spinnerNivelMob = new javax.swing.JSpinner();
        panelComportamientoMob = new javax.swing.JPanel();
        comboComportamiento = new javax.swing.JComboBox();
        panelDropsMobs = new javax.swing.JPanel();
        panelDropsObjetosMobs = new javax.swing.JPanel();
        comboObjetoDropMob = new javax.swing.JComboBox();
        lblObjetoDropMob = new javax.swing.JLabel();
        lblCantidadDropMob = new javax.swing.JLabel();
        lblProbDropMob = new javax.swing.JLabel();
        spinnerCantidadDropMob = new javax.swing.JSpinner();
        spinnerProbDropMob = new javax.swing.JSpinner();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDropsMob = new javax.swing.JTable();
        lblTipoDropMob = new javax.swing.JLabel();
        comboTipoDropMob = new javax.swing.JComboBox();
        btnAnadirDropMob = new javax.swing.JButton();
        btnEditarDropMob = new javax.swing.JButton();
        btnEliminarDropMob = new javax.swing.JButton();
        panelDropsDefault = new javax.swing.JPanel();
        spinnerDineroDropMob = new javax.swing.JSpinner();
        spinnerExpDropMob = new javax.swing.JSpinner();
        lblDineroDropMob = new javax.swing.JLabel();
        lblExpDropMob = new javax.swing.JLabel();
        panelAtaqueMobs = new javax.swing.JPanel();
        comboTipoAtaqueMob = new javax.swing.JComboBox();
        panelAtributosMobs = new javax.swing.JPanel();
        lblAtaFisMob = new javax.swing.JLabel();
        lblAtaDistMob = new javax.swing.JLabel();
        lblVelocidadMob = new javax.swing.JLabel();
        lblVelocidadAtaqueMob = new javax.swing.JLabel();
        lblRetrocesoMob = new javax.swing.JLabel();
        lblRangoSeguiMob = new javax.swing.JLabel();
        spinnerAtaDistMob = new javax.swing.JSpinner();
        spinnerVelocidadMob = new javax.swing.JSpinner();
        spinnerVelocidadAtaqueMob = new javax.swing.JSpinner();
        spinnerRetrocesoMob = new javax.swing.JSpinner();
        spinnerRangoSeguiMob = new javax.swing.JSpinner();
        lblMaxVidaMob = new javax.swing.JLabel();
        spinnerMaxVidaMob = new javax.swing.JSpinner();
        lblFuerzaDistMob = new javax.swing.JLabel();
        spinnerFuerzaDistMob = new javax.swing.JSpinner();
        spinnerAtaFisMob = new javax.swing.JSpinner();
        lblTipoMob = new javax.swing.JLabel();
        comboSelectorTipoMob = new javax.swing.JComboBox();
        panelConfChats = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelConfigChats = new javax.swing.JPanel();
        panelConfigMarketChat = new javax.swing.JPanel();
        lblPrefixMarket = new javax.swing.JLabel();
        lblPrefixColorMarket = new javax.swing.JLabel();
        lblShortcutMarket = new javax.swing.JLabel();
        lblMessageColorMarket = new javax.swing.JLabel();
        textPrefixMarketChat = new javax.swing.JTextField();
        comboPrefixColorMarketChat = new javax.swing.JComboBox();
        comboShortcutMarketChat = new javax.swing.JComboBox();
        comboMessageColorMarketChat = new javax.swing.JComboBox();
        panelConfigGlobal = new javax.swing.JPanel();
        lblPrefixGlobal = new javax.swing.JLabel();
        lblPCG = new javax.swing.JLabel();
        lblSG = new javax.swing.JLabel();
        lblMCG = new javax.swing.JLabel();
        textPrefixGlobalChat = new javax.swing.JTextField();
        comboPrefixColorGlobalChat = new javax.swing.JComboBox();
        comboShortcutGlobalChat = new javax.swing.JComboBox();
        comboMessageColorGlobalChat = new javax.swing.JComboBox();
        panelConfigParty = new javax.swing.JPanel();
        lblPP = new javax.swing.JLabel();
        lblPCP = new javax.swing.JLabel();
        lblSP = new javax.swing.JLabel();
        lblMCP = new javax.swing.JLabel();
        textPrefixPartyChat = new javax.swing.JTextField();
        comboPrefixColorPartyChat = new javax.swing.JComboBox();
        comboShortcutPartyChat = new javax.swing.JComboBox();
        comboMessageColorPartyChat = new javax.swing.JComboBox();
        panelConfigGuild = new javax.swing.JPanel();
        lblPGuild = new javax.swing.JLabel();
        lblPCGuild = new javax.swing.JLabel();
        lblSGuild = new javax.swing.JLabel();
        lblMCGuild = new javax.swing.JLabel();
        textPrefixGuildChat = new javax.swing.JTextField();
        comboPrefixColorGuildChat = new javax.swing.JComboBox();
        comboShortcutGuildChat = new javax.swing.JComboBox();
        comboMessageColorGuildChat = new javax.swing.JComboBox();
        panelConfiglPrivate = new javax.swing.JPanel();
        lblPPriv = new javax.swing.JLabel();
        lblPCPriv = new javax.swing.JLabel();
        lblSPriv = new javax.swing.JLabel();
        lblMCPriv = new javax.swing.JLabel();
        textPrefixPrivateChat = new javax.swing.JTextField();
        comboPrefixColorPrivateChat = new javax.swing.JComboBox();
        comboShortcutPrivateChat = new javax.swing.JComboBox();
        comboMessageColorPrivateChat = new javax.swing.JComboBox();
        panelConfigNews = new javax.swing.JPanel();
        lblPN = new javax.swing.JLabel();
        lblPCN = new javax.swing.JLabel();
        lblSN = new javax.swing.JLabel();
        lblMCN = new javax.swing.JLabel();
        textPrefixNewsChat = new javax.swing.JTextField();
        comboPrefixColorNewsChat = new javax.swing.JComboBox();
        comboShortcutNewsChat = new javax.swing.JComboBox();
        comboMessageColorNewsChat = new javax.swing.JComboBox();
        panelConfigWarning = new javax.swing.JPanel();
        lblPW = new javax.swing.JLabel();
        lblPCW = new javax.swing.JLabel();
        lblSW = new javax.swing.JLabel();
        lblMCW = new javax.swing.JLabel();
        textPrefixWarningChat = new javax.swing.JTextField();
        comboPrefixColorWarningChat = new javax.swing.JComboBox();
        comboShortcutWarningChat = new javax.swing.JComboBox();
        comboMessageColorWarningChat = new javax.swing.JComboBox();
        panelConfigLocal = new javax.swing.JPanel();
        lblPL = new javax.swing.JLabel();
        lblPCL = new javax.swing.JLabel();
        lblMCL = new javax.swing.JLabel();
        textPrefixLocalChat = new javax.swing.JTextField();
        lblDL = new javax.swing.JLabel();
        spinnerDistanceLocalChat = new javax.swing.JSpinner();
        comboPrefixColorLocalChat = new javax.swing.JComboBox();
        comboMessageColorLocalChat = new javax.swing.JComboBox();
        btnGuardarConfigChats = new javax.swing.JButton();
        panelConfigEnableChats = new javax.swing.JPanel();
        checkEnableGlobalChat = new javax.swing.JCheckBox();
        checkEnableMarketChat = new javax.swing.JCheckBox();
        checkGuildChat = new javax.swing.JCheckBox();
        checkPartyChat = new javax.swing.JCheckBox();
        checkPrivateChat = new javax.swing.JCheckBox();

        btngroupNumJugGrupo.add(rdBtnIlimitadosGrupo);
        btngroupNumJugGrupo.add(rdBtnFijosGrupo);

        btngroupPvpGrupo.add(rdBtnPermitidoPvpGrupo);
        btngroupPvpGrupo.add(rdBtnNoPermitidoPvpGrupo);

        btngroupRepartoGrupo.add(rdBtnSinRepartoGrupo);
        btngroupRepartoGrupo.add(rdBtnSoloDineroGrupo);
        btngroupRepartoGrupo.add(rdBtnSoloExpGrupo);
        btngroupRepartoGrupo.add(rdBtnAmbosGrupo);

        btnGroupOpcionRepartoGrupo.add(rdBtnOpcionigualitarioGrupo);
        btnGroupOpcionRepartoGrupo.add(rdBtnOpcionProporcionalGrupo);

        btngroupOpcionRepartoPropGrupo.add(rdBtnProNivelGrupo);
        btngroupOpcionRepartoPropGrupo.add(rdBtnProKillsGrupo);

        btngroupNumJugClan.add(rdBtnNumIlimitadosClan);
        btngroupNumJugClan.add(rdBtnNumFijosClan);
        btngroupNumJugClan.add(rdBtnLimitNivClan);

        btngroupOpcLimNivelClan.add(rdBtnOpcDinFijo);
        btngroupOpcLimNivelClan.add(rdBtnOpcDinForm);

        btngroupSistContrClan.add(rdBtnContriAmbos);
        btngroupSistContrClan.add(rdBtnContriSoloContri);
        btngroupSistContrClan.add(rdBtnContriSoloDon);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 800));
        setMinimumSize(new java.awt.Dimension(600, 400));

        panelConfig.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        panelConfig.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        panelConfig.setToolTipText("");
        panelConfig.setAutoscrolls(true);
        panelConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelConfig.setMaximumSize(new java.awt.Dimension(1000, 600));
        panelConfig.setMinimumSize(new java.awt.Dimension(800, 500));
        panelConfig.setPreferredSize(new java.awt.Dimension(1000, 600));

        panelInicio.setPreferredSize(new java.awt.Dimension(800, 600));

        btnprueba.setText("jButton2");
        btnprueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpruebaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(btnprueba)
                .addContainerGap(588, Short.MAX_VALUE))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnprueba)
                .addContainerGap(533, Short.MAX_VALUE))
        );

        panelConfig.addTab("Inicio", panelInicio);

        panelConfigGeneral.setPreferredSize(new java.awt.Dimension(800, 600));

        panelConfigGeneralGen.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración general"));

        panelConfigDanioGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion de daño"));

        checkSistHambre.setText("Sistema original de hambre");

        checkDanioCaida.setText("Daño por caida");

        checkDanioAhogo.setText("Daño por ahogamiento");

        checkDanioPvp.setText("Habilitar/Deshabilitar PvP");

        javax.swing.GroupLayout panelConfigDanioGeneralLayout = new javax.swing.GroupLayout(panelConfigDanioGeneral);
        panelConfigDanioGeneral.setLayout(panelConfigDanioGeneralLayout);
        panelConfigDanioGeneralLayout.setHorizontalGroup(
            panelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigDanioGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkSistHambre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioCaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioAhogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioPvp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelConfigDanioGeneralLayout.setVerticalGroup(
            panelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigDanioGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkSistHambre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDanioCaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDanioAhogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDanioPvp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEnableModulosGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de módulos"));

        checkClanes.setSelected(true);
        checkClanes.setText("Habilitar/Deshabilitar clanes");
        checkClanes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkClanesActionPerformed(evt);
            }
        });

        checkGrupos.setSelected(true);
        checkGrupos.setText("Habilitar/Deshabilitar grupos");
        checkGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGruposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEnableModulosGeneralLayout = new javax.swing.GroupLayout(panelEnableModulosGeneral);
        panelEnableModulosGeneral.setLayout(panelEnableModulosGeneralLayout);
        panelEnableModulosGeneralLayout.setHorizontalGroup(
            panelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnableModulosGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkClanes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        panelEnableModulosGeneralLayout.setVerticalGroup(
            panelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnableModulosGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkClanes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkGrupos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConfigInicio.setBorder(javax.swing.BorderFactory.createTitledBorder("Localización de inicio"));

        lblMundoInicio.setText("Mundo");

        lblXInicio.setText("X");

        lblYInicio.setText("Y");

        lblZInicio.setText("Z");

        javax.swing.GroupLayout panelConfigInicioLayout = new javax.swing.GroupLayout(panelConfigInicio);
        panelConfigInicio.setLayout(panelConfigInicioLayout);
        panelConfigInicioLayout.setHorizontalGroup(
            panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblXInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblYInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblZInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMundoInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMundoInicio)
                    .addComponent(spinnerXInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(spinnerYInicio)
                    .addComponent(spinnerZInicio))
                .addContainerGap())
        );
        panelConfigInicioLayout.setVerticalGroup(
            panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMundoInicio)
                    .addComponent(txtMundoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXInicio)
                    .addComponent(spinnerXInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYInicio)
                    .addComponent(spinnerYInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZInicio)
                    .addComponent(spinnerZInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConfigPlayers.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración jugadores"));

        checkColocarBloques.setText("Permitir colocar bloques");

        checkDestruirBloques.setText("Permitir destruir bloques");

        javax.swing.GroupLayout panelConfigPlayersLayout = new javax.swing.GroupLayout(panelConfigPlayers);
        panelConfigPlayers.setLayout(panelConfigPlayersLayout);
        panelConfigPlayersLayout.setHorizontalGroup(
            panelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPlayersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkColocarBloques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDestruirBloques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigPlayersLayout.setVerticalGroup(
            panelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPlayersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkColocarBloques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDestruirBloques))
        );

        btnGuardarConfigGlobal.setText("Guardar");
        btnGuardarConfigGlobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarConfigGlobalActionPerformed(evt);
            }
        });

        panelConfigDayCycle.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración global"));

        checkDayCycle.setSelected(true);
        checkDayCycle.setText("Ciclo de dia");

        javax.swing.GroupLayout panelConfigDayCycleLayout = new javax.swing.GroupLayout(panelConfigDayCycle);
        panelConfigDayCycle.setLayout(panelConfigDayCycleLayout);
        panelConfigDayCycleLayout.setHorizontalGroup(
            panelConfigDayCycleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigDayCycleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkDayCycle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConfigDayCycleLayout.setVerticalGroup(
            panelConfigDayCycleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigDayCycleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkDayCycle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelConfigGeneralGenLayout = new javax.swing.GroupLayout(panelConfigGeneralGen);
        panelConfigGeneralGen.setLayout(panelConfigGeneralGenLayout);
        panelConfigGeneralGenLayout.setHorizontalGroup(
            panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGeneralGenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarConfigGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfigGeneralGenLayout.createSequentialGroup()
                        .addGroup(panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelConfigInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelConfigDanioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelConfigDayCycle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelEnableModulosGeneral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelConfigPlayers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        panelConfigGeneralGenLayout.setVerticalGroup(
            panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGeneralGenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelConfigDanioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEnableModulosGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelConfigInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelConfigGeneralGenLayout.createSequentialGroup()
                        .addComponent(panelConfigPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelConfigDayCycle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarConfigGlobal))
        );

        javax.swing.GroupLayout panelConfigGeneralLayout = new javax.swing.GroupLayout(panelConfigGeneral);
        panelConfigGeneral.setLayout(panelConfigGeneralLayout);
        panelConfigGeneralLayout.setHorizontalGroup(
            panelConfigGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelConfigGeneralGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
        panelConfigGeneralLayout.setVerticalGroup(
            panelConfigGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelConfigGeneralGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        panelConfig.addTab("General", panelConfigGeneral);

        lblx3Nivel.setText("x³+");

        lblx2Nivel.setText("x²+");

        lblxNivel.setText("x");

        lblFdeXNivel.setText("f(x)=");

        btnValidarFormulaNiveles.setText("Validar");
        btnValidarFormulaNiveles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarFormulaNivelesActionPerformed(evt);
            }
        });

        btnGuardarConfigNiveles.setText("Guardar");
        btnGuardarConfigNiveles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarConfigNivelesActionPerformed(evt);
            }
        });

        lblInfoNivel.setText("Para crear una formula de niveles rellenar los huecos con números, acepta decimales");

        lblInfoNivel2.setText("El resultado de la formula representa el total de experiencia que necesita el jugador para alcanzar el nivel X");

        btnDibujarGraficoNiveles.setText("Dibujar gráfico");
        btnDibujarGraficoNiveles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDibujarGraficoNivelesActionPerformed(evt);
            }
        });

        lblInfoNivelMaxNivel.setText("Selecciona el nivel máximo que el jugador alcanzará");

        txtX3Nivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));
        txtX3Nivel.setAutoscrolls(true);

        txtX2Nivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        txtXNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        lblMaxNivelNivel.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        lblMaxNivelNivel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblMaxNivelNivelPropertyChange(evt);
            }
        });

        panelGraficosNivel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gráfico"));

        javax.swing.GroupLayout panelGraficosNivelLayout = new javax.swing.GroupLayout(panelGraficosNivel);
        panelGraficosNivel.setLayout(panelGraficosNivelLayout);
        panelGraficosNivelLayout.setHorizontalGroup(
            panelGraficosNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
        );
        panelGraficosNivelLayout.setVerticalGroup(
            panelGraficosNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        panelNivelExpNivel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de experiencia"));

        tablaExpNivel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaExpNivel.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tablaExpNivel);

        javax.swing.GroupLayout panelNivelExpNivelLayout = new javax.swing.GroupLayout(panelNivelExpNivel);
        panelNivelExpNivel.setLayout(panelNivelExpNivelLayout);
        panelNivelExpNivelLayout.setHorizontalGroup(
            panelNivelExpNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelNivelExpNivelLayout.setVerticalGroup(
            panelNivelExpNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblPHxNivel.setText("Puntos de habilidad por nivel alcanzado");

        javax.swing.GroupLayout panelConfigLevelLayout = new javax.swing.GroupLayout(panelConfigLevel);
        panelConfigLevel.setLayout(panelConfigLevelLayout);
        panelConfigLevelLayout.setHorizontalGroup(
            panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLevelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                        .addComponent(lblInfoNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(177, 177, 177))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigLevelLayout.createSequentialGroup()
                        .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelConfigLevelLayout.createSequentialGroup()
                                .addComponent(panelGraficosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelNivelExpNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelConfigLevelLayout.createSequentialGroup()
                                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                                        .addComponent(lblFdeXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtX3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblx3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtX2Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblInfoNivelMaxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                                        .addComponent(lblx2Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnValidarFormulaNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDibujarGraficoNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGuardarConfigNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                                        .addComponent(lblMaxNivelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblPHxNivel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spinnerPHxNivel)))
                                .addGap(184, 184, 184)))
                        .addGap(18, 18, 18))
                    .addComponent(lblInfoNivel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelConfigLevelLayout.setVerticalGroup(
            panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLevelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoNivel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoNivel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFdeXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtX3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblx3Nivel)
                        .addComponent(txtX2Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblx2Nivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblxNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnValidarFormulaNiveles)
                        .addComponent(btnDibujarGraficoNiveles)
                        .addComponent(btnGuardarConfigNiveles)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInfoNivelMaxNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaxNivelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPHxNivel)
                    .addComponent(spinnerPHxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                        .addComponent(panelGraficosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(panelConfigLevelLayout.createSequentialGroup()
                        .addComponent(panelNivelExpNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        panelConfig.addTab("Niveles", panelConfigLevel);

        panelConfigClases.setAutoscrolls(true);
        panelConfigClases.setName(""); // NOI18N
        panelConfigClases.setPreferredSize(new java.awt.Dimension(800, 600));

        btnCrearNuevaClase.setText("Nueva clase");

        btnEditarClase.setText("Editar");
        btnEditarClase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClaseActionPerformed(evt);
            }
        });

        btnEliminarClase.setText("Eliminar");

        panelEditorClase.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de clases"));
        panelEditorClase.setName("Class Editor"); // NOI18N

        lblNombreClase.setText("Nombre");

        panelAtribBasicoClase.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos base"));

        lblVidaBase.setText("Vida máxima");

        lblManaBase.setText("Mana");

        lblAtaFisBase.setText("Ataque físico");

        lblDefFisBase.setText("Defensa física");

        lblHRFisBase.setText("Tasa de acierto físico");

        lblEvaFisBase.setText("Evasión física");

        lblAtaMagBase.setText("Ataque mágico");

        lblDefMagBase.setText("Defensa mágica");

        lblHRMagBase.setText("Tasa de acierto mágico");

        lblEvaMagBase.setText("Evasión mágica");

        lblCritBase.setText("Crítico");

        lblMortalBase.setText("Mortal");

        spinnerMaxVidaBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerMaxManaBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerDefFisBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaFisBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerHRFisBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerEvaBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaMagBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerDefMagBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerHRMagBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerEvaMagBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerCritBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerMortalBase.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelAtribBasicoClaseLayout = new javax.swing.GroupLayout(panelAtribBasicoClase);
        panelAtribBasicoClase.setLayout(panelAtribBasicoClaseLayout);
        panelAtribBasicoClaseLayout.setHorizontalGroup(
            panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblVidaBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMaxVidaBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblManaBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMaxManaBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblDefFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefFisBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblAtaFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblHRFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRFisBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblEvaFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblAtaMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblDefMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefMagBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblHRMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRMagBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblEvaMagBase)
                        .addGap(12, 12, 12)
                        .addComponent(spinnerEvaMagBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblMortalBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMortalBase))
                    .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblCritBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerCritBase)))
                .addContainerGap())
        );
        panelAtribBasicoClaseLayout.setVerticalGroup(
            panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtribBasicoClaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaBase)
                    .addComponent(spinnerMaxVidaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaBase)
                    .addComponent(spinnerMaxManaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisBase)
                    .addComponent(spinnerAtaFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisBase)
                    .addComponent(spinnerDefFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRFisBase)
                    .addComponent(spinnerHRFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisBase)
                    .addComponent(spinnerEvaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagBase)
                    .addComponent(spinnerAtaMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefMagBase)
                    .addComponent(spinnerDefMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRMagBase)
                    .addComponent(spinnerHRMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaMagBase)
                    .addComponent(spinnerEvaMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritBase)
                    .addComponent(spinnerCritBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMortalBase)
                    .addComponent(spinnerMortalBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checkEnableClase.setText("Habilitado/Deshabilitado");

        btnGuardarClase.setText("Guardar");
        btnGuardarClase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClaseActionPerformed(evt);
            }
        });

        btnResetClase.setText("Resetear");

        panelSubirNivel.setBorder(javax.swing.BorderFactory.createTitledBorder(" Incremento de atributos por nivel"));

        lblVidaNivel.setText("Vida");

        lblManaNivel.setText("Mana");

        lblAtaFisNivel.setText("Ataque físico");

        lblDefFisNivel.setText("defensa física");

        lblHRFisNivel.setText("Tasa de acierto físico");

        lblEvaFisNivel.setText("Evasión física");

        lblAtaMagNivel.setText("Ataque mágico");

        lblDefMagNivel.setText("Defensa mágica");

        lblHRMagNivel.setText("Tasa de acierto mágico");

        lblEvaMagNivel.setText("Evasión mágica");

        lblCritNivel.setText("Crítico");

        lblMortalNivel.setText("Mortal");

        spinnerVidaNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerManaNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaFisNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerDefFisNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerHRFisNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerEvaFisNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaMagNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerDefMagNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerHRMagNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerEvaMagNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerCritNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerMortalNivel.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelSubirNivelLayout = new javax.swing.GroupLayout(panelSubirNivel);
        panelSubirNivel.setLayout(panelSubirNivelLayout);
        panelSubirNivelLayout.setHorizontalGroup(
            panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubirNivelLayout.createSequentialGroup()
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblAtaFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblDefFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefFisNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblHRFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRFisNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblEvaFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaFisNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblAtaMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblEvaMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaMagNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblCritNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerCritNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblMortalNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMortalNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblDefMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefMagNivel))
                    .addGroup(panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblHRMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRMagNivel)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSubirNivelLayout.createSequentialGroup()
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblManaNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerManaNivel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblVidaNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerVidaNivel)))
                .addGap(10, 10, 10))
        );
        panelSubirNivelLayout.setVerticalGroup(
            panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubirNivelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaNivel)
                    .addComponent(spinnerVidaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaNivel)
                    .addComponent(spinnerManaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisNivel)
                    .addComponent(spinnerAtaFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisNivel)
                    .addComponent(spinnerDefFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRFisNivel)
                    .addComponent(spinnerHRFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisNivel)
                    .addComponent(spinnerEvaFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagNivel)
                    .addComponent(spinnerAtaMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefMagNivel)
                    .addComponent(spinnerDefMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRMagNivel)
                    .addComponent(spinnerHRMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEvaMagNivel)
                    .addComponent(spinnerEvaMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritNivel)
                    .addComponent(spinnerCritNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMortalNivel)
                    .addComponent(spinnerMortalNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        panelPuntoHabilidad.setBorder(javax.swing.BorderFactory.createTitledBorder("Incremento de atributos por punto de habilidad"));

        panelHabFuerza.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de fuerza"));

        lblVidaAP.setText("Vida");

        lblAtaFisAP.setText("Ataque físico");

        spinnerVidaAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaFisAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelHabFuerzaLayout = new javax.swing.GroupLayout(panelHabFuerza);
        panelHabFuerza.setLayout(panelHabFuerzaLayout);
        panelHabFuerzaLayout.setHorizontalGroup(
            panelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabFuerzaLayout.createSequentialGroup()
                .addGroup(panelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHabFuerzaLayout.createSequentialGroup()
                        .addComponent(lblVidaAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerVidaAP))
                    .addGroup(panelHabFuerzaLayout.createSequentialGroup()
                        .addComponent(lblAtaFisAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisAP)))
                .addContainerGap())
        );
        panelHabFuerzaLayout.setVerticalGroup(
            panelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabFuerzaLayout.createSequentialGroup()
                .addGroup(panelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaAP)
                    .addComponent(spinnerVidaAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisAP)
                    .addComponent(spinnerAtaFisAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelHabConst.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de constitución"));

        lblVidaAP2.setText("Vida");

        lblManaAP.setText("Mana");

        spinnerVidaAP2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerManaAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelHabConstLayout = new javax.swing.GroupLayout(panelHabConst);
        panelHabConst.setLayout(panelHabConstLayout);
        panelHabConstLayout.setHorizontalGroup(
            panelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabConstLayout.createSequentialGroup()
                .addGroup(panelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHabConstLayout.createSequentialGroup()
                        .addComponent(lblVidaAP2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerVidaAP2))
                    .addGroup(panelHabConstLayout.createSequentialGroup()
                        .addComponent(lblManaAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerManaAP)))
                .addContainerGap())
        );
        panelHabConstLayout.setVerticalGroup(
            panelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabConstLayout.createSequentialGroup()
                .addGroup(panelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaAP2)
                    .addComponent(spinnerVidaAP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaAP)
                    .addComponent(spinnerManaAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelHabInt.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de inteligencia"));

        lblManaAP2.setText("Mana");

        lblAtaMagAP.setText("Ataque mágico");

        spinnerManaAP2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaMagAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelHabIntLayout = new javax.swing.GroupLayout(panelHabInt);
        panelHabInt.setLayout(panelHabIntLayout);
        panelHabIntLayout.setHorizontalGroup(
            panelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabIntLayout.createSequentialGroup()
                .addGroup(panelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHabIntLayout.createSequentialGroup()
                        .addComponent(lblManaAP2)
                        .addGap(10, 10, 10)
                        .addComponent(spinnerManaAP2))
                    .addGroup(panelHabIntLayout.createSequentialGroup()
                        .addComponent(lblAtaMagAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagAP)))
                .addContainerGap())
        );
        panelHabIntLayout.setVerticalGroup(
            panelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabIntLayout.createSequentialGroup()
                .addGroup(panelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaAP2)
                    .addComponent(spinnerManaAP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagAP)
                    .addComponent(spinnerAtaMagAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelHabDest.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de destreza"));

        lblCritAP.setText("Crítico");

        lblMortalAP.setText("Mortal");

        spinnerCrtitAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerMortalAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout panelHabDestLayout = new javax.swing.GroupLayout(panelHabDest);
        panelHabDest.setLayout(panelHabDestLayout);
        panelHabDestLayout.setHorizontalGroup(
            panelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabDestLayout.createSequentialGroup()
                .addComponent(lblCritAP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerCrtitAP)
                .addGap(17, 17, 17))
            .addGroup(panelHabDestLayout.createSequentialGroup()
                .addComponent(lblMortalAP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerMortalAP)
                .addGap(16, 16, 16))
        );
        panelHabDestLayout.setVerticalGroup(
            panelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabDestLayout.createSequentialGroup()
                .addGroup(panelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritAP)
                    .addComponent(spinnerCrtitAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMortalAP)
                    .addComponent(spinnerMortalAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout panelPuntoHabilidadLayout = new javax.swing.GroupLayout(panelPuntoHabilidad);
        panelPuntoHabilidad.setLayout(panelPuntoHabilidadLayout);
        panelPuntoHabilidadLayout.setHorizontalGroup(
            panelPuntoHabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHabFuerza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelHabConst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelHabInt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelHabDest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPuntoHabilidadLayout.setVerticalGroup(
            panelPuntoHabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPuntoHabilidadLayout.createSequentialGroup()
                .addComponent(panelHabFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelHabConst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelHabInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelHabDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEditorClaseLayout = new javax.swing.GroupLayout(panelEditorClase);
        panelEditorClase.setLayout(panelEditorClaseLayout);
        panelEditorClaseLayout.setHorizontalGroup(
            panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorClaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorClaseLayout.createSequentialGroup()
                        .addComponent(panelAtribBasicoClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(panelSubirNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelEditorClaseLayout.createSequentialGroup()
                        .addComponent(lblNombreClase, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreClase)
                        .addGap(53, 53, 53)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorClaseLayout.createSequentialGroup()
                        .addComponent(checkEnableClase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetClase, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addComponent(panelPuntoHabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelEditorClaseLayout.setVerticalGroup(
            panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorClaseLayout.createSequentialGroup()
                .addGroup(panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombreClase)
                    .addComponent(checkEnableClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResetClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPuntoHabilidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditorClaseLayout.createSequentialGroup()
                        .addGroup(panelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelAtribBasicoClase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelSubirNivel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 5, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout panelConfigClasesLayout = new javax.swing.GroupLayout(panelConfigClases);
        panelConfigClases.setLayout(panelConfigClasesLayout);
        panelConfigClasesLayout.setHorizontalGroup(
            panelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigClasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditorClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigClasesLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevaClase, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboSelectorClases, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelConfigClasesLayout.setVerticalGroup(
            panelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigClasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSelectorClases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarClase)
                    .addComponent(btnEliminarClase)
                    .addComponent(btnCrearNuevaClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        panelConfig.addTab("Clases", panelConfigClases);

        panelConfigObjetos.setPreferredSize(new java.awt.Dimension(800, 600));

        btnCrearNuevoObjeto.setText("Nuevo");

        panelEditorObjetos.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de objetos"));

        lblNombreObjeto.setText("Nombre");

        lblTipoObjeto.setText("Tipo de objeto");

        comboTipoObjeto.setName("tipoobjeto"); // NOI18N

        listDescripcionObjeto.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción objeto"));
        jScrollPane2.setViewportView(listDescripcionObjeto);

        lblDescripObjeto.setText("Descripción");

        btnAnadirDesObjeto.setText("Añadir");

        btnEditarDesObjeto.setText("Editar");

        btnEliminarDesObjeto.setText("Eliminar descripción");

        btnGuardarObjeto.setText("Guardar");
        btnGuardarObjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarObjetoActionPerformed(evt);
            }
        });

        lblCalidadObjeto.setText("Calidad objeto");

        comboCalidadObjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCalidadObjeto.setName("calidadobjeto"); // NOI18N

        javax.swing.GroupLayout panelEditorObjetosLayout = new javax.swing.GroupLayout(panelEditorObjetos);
        panelEditorObjetos.setLayout(panelEditorObjetosLayout);
        panelEditorObjetosLayout.setHorizontalGroup(
            panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                                .addComponent(btnAnadirDesObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditarDesObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                            .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                                .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDescripObjeto)
                                    .addComponent(lblNombreObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTipoObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(lblCalidadObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboTipoObjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNombreObjeto)
                                    .addComponent(txtDescripObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(comboCalidadObjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnEliminarDesObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addComponent(btnGuardarObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEditorObjetosLayout.setVerticalGroup(
            panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorObjetosLayout.createSequentialGroup()
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreObjeto)
                            .addComponent(txtNombreObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoObjeto)
                            .addComponent(comboTipoObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCalidadObjeto)
                            .addComponent(comboCalidadObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescripObjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAnadirDesObjeto)
                            .addComponent(btnEditarDesObjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarDesObjeto))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarObjeto)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        btnEditarObjeto.setText("Editar");

        btnEliminarObjeto.setText("Eliminar");

        javax.swing.GroupLayout panelConfigObjetosLayout = new javax.swing.GroupLayout(panelConfigObjetos);
        panelConfigObjetos.setLayout(panelConfigObjetosLayout);
        panelConfigObjetosLayout.setHorizontalGroup(
            panelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelEditorObjetos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigObjetosLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevoObjeto)
                        .addGap(130, 130, 130)
                        .addComponent(comboListaObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarObjeto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarObjeto)))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        panelConfigObjetosLayout.setVerticalGroup(
            panelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevoObjeto)
                    .addComponent(comboListaObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarObjeto)
                    .addComponent(btnEliminarObjeto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        panelConfig.addTab("Objetos", panelConfigObjetos);

        panelConfigWeapon.setPreferredSize(new java.awt.Dimension(800, 600));

        panelMejoraArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Mejora de armas"));

        btnGuardarConfigArma.setText("Guardar");

        lblTipoMejoradorArma.setText("Objeto mejorador");

        lblNomMejoraArma.setText("Nombre del objeto");

        lblDescripcionArma.setText("Descripción");

        btnAnadirDescArma.setText("Añadir");
        btnAnadirDescArma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirDescArmaActionPerformed(evt);
            }
        });

        btnEditarDEscArma.setText("Editar");
        btnEditarDEscArma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDEscArmaActionPerformed(evt);
            }
        });

        btnEliminarDescArma.setText("Eliminar");
        btnEliminarDescArma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDescArmaActionPerformed(evt);
            }
        });

        jListDescArma.setBorder(javax.swing.BorderFactory.createTitledBorder("lista de descripción"));
        jListDescArma.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(jListDescArma);

        lblProbRotArma.setText("Probabilidad de rotura");

        lblProbDetArma.setText("Probabilidad de deterioro");

        lblProbSEArma.setText("Probabilidad sin efecto");

        lblProbExitoArma.setText("Probabilidad de exito");

        javax.swing.GroupLayout panelMejoraArmaLayout = new javax.swing.GroupLayout(panelMejoraArma);
        panelMejoraArma.setLayout(panelMejoraArmaLayout);
        panelMejoraArmaLayout.setHorizontalGroup(
            panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMejoraArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNomMejoraArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTipoMejoradorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboMejoradorArma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreObjetoMejArma)
                            .addComponent(txtDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                        .addComponent(btnAnadirDescArma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarDEscArma, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarDescArma, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblProbSEArma, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(lblProbExitoArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinerProbExitoArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerProSEArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarConfigArma, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelMejoraArmaLayout.setVerticalGroup(
            panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotArma)
                                .addComponent(spinnerProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNomMejoraArma)
                                .addComponent(txtNombreObjetoMejArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboMejoradorArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTipoMejoradorArma))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDescripcionArma)
                                .addComponent(txtDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAnadirDescArma)
                                .addComponent(btnEditarDEscArma)
                                .addComponent(btnEliminarDescArma)))
                        .addGroup(panelMejoraArmaLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEArma)
                                .addComponent(spinnerProSEArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinerProbExitoArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoArma))))))
            .addComponent(btnGuardarConfigArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelSelectorArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de armas"));

        btnCrearNuevaArma.setText("Nueva");

        panelEditorArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de armas"));

        lblTipoArma.setText("Tipo de arma");

        comboTipoArma.setName("tipoarma"); // NOI18N

        lblNombreArma.setText("Nombre");

        lblNivelMinArma.setText("Nivel minimo para uso");

        lblNivelIniArma.setText("Nivel arma");

        lblCalidadArma.setText("Calidad");

        comboCalidadArma.setName("calidadarma"); // NOI18N

        checkComerciableArma.setText("Comerciable");

        checkMejorableArma.setText("Mejorable");

        lblPrecioCArma.setText("Precio compra");

        lblPrecioVArma.setText("Precio de venta");

        lblDineroExtraArma.setText("Dinero extra");

        lblExpExtraArma.setText("Experiencia extra");

        lblAtaFisArma.setText("Daño físico base");

        lblMejAtaFisArma.setText("Daño físico mejora");

        lblAtaMagArma.setText("Daño mágico base");

        lblMejAtaMagArma.setText("Daño mágico mejora");

        lblHRFisArma.setText("Tasa acierto físico");

        lblHRMagArma.setText("Tasa acierto mágico");

        lblMejHRFisArma.setText("Mejora tasa física");

        lblMejHRMagArma.setText("Mejora tasa mágica");

        lblCritArma.setText("Crítico");

        lblMejCritArma.setText("Mejora de crítico");

        lvlRoboVArma.setText("Robo de vida");

        lvlMejRoboVArma.setText("Mejora robo de vida");

        lblRoboMArma.setText("Robo de maná");

        lblMejRoboMArma.setText("Mejora robo de maná");

        btnGuardarArma.setText("Guardar");

        javax.swing.GroupLayout panelEditorArmaLayout = new javax.swing.GroupLayout(panelEditorArma);
        panelEditorArma.setLayout(panelEditorArmaLayout);
        panelEditorArmaLayout.setHorizontalGroup(
            panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEditorArmaLayout.createSequentialGroup()
                        .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lvlRoboVArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lvlMejRoboVArma, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerRoboVArma)
                                    .addComponent(spinnerMejRoboVArma)))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(checkComerciableArma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkMejorableArma))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblCalidadArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNivelIniArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNivelMinArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNombreArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTipoArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboTipoArma, 0, 150, Short.MAX_VALUE)
                                    .addComponent(txtNombreArma)
                                    .addComponent(spinnerNivelMinArma)
                                    .addComponent(spinnerNivelIniArma)
                                    .addComponent(comboCalidadArma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblPrecioVArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerPrecioVArma))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblDineroExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerDineroExtraArma))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblExpExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerExpExtraArma)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMejAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAtaMagArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejAtaMagArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerMejAtaMagArma)
                                    .addComponent(spinnerMejAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spinnerAtaMagArma)
                                    .addComponent(spinnerAtaFisArma)))
                            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblMejRoboMArma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerMejRoboMArma))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorArmaLayout.createSequentialGroup()
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblRoboMArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejCritArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCritArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejHRMagArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHRMagArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejHRFisArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHRFisArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spinnerHRFisArma, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(spinnerMejHRFisArma)
                                    .addComponent(spinnerHRMagArma)
                                    .addComponent(spinnerMejHRMagArma)
                                    .addComponent(spinnerCritArma)
                                    .addComponent(spinnerMejCritArma)
                                    .addComponent(spinnerRoboMArma))))))
                .addContainerGap())
        );
        panelEditorArmaLayout.setVerticalGroup(
            panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoArma)
                    .addComponent(comboTipoArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaFisArma)
                    .addComponent(spinnerAtaFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreArma)
                    .addComponent(txtNombreArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejAtaFisArma)
                    .addComponent(spinnerMejAtaFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivelMinArma)
                    .addComponent(spinnerNivelMinArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaMagArma)
                    .addComponent(spinnerAtaMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivelIniArma)
                    .addComponent(spinnerNivelIniArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejAtaMagArma)
                    .addComponent(spinnerMejAtaMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalidadArma)
                    .addComponent(comboCalidadArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRFisArma)
                    .addComponent(spinnerHRFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciableArma)
                    .addComponent(checkMejorableArma)
                    .addComponent(lblMejHRFisArma)
                    .addComponent(spinnerMejHRFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCArma)
                    .addComponent(spinnerPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRMagArma)
                    .addComponent(spinnerHRMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVArma)
                    .addComponent(spinnerPrecioVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejHRMagArma)
                    .addComponent(spinnerMejHRMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraArma)
                    .addComponent(spinnerDineroExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCritArma)
                    .addComponent(spinnerCritArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraArma)
                    .addComponent(spinnerExpExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejCritArma)
                    .addComponent(spinnerMejCritArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlRoboVArma)
                    .addComponent(spinnerRoboVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRoboMArma)
                    .addComponent(spinnerRoboMArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlMejRoboVArma)
                    .addComponent(spinnerMejRoboVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejRoboMArma)
                    .addComponent(spinnerMejRoboMArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardarArma))
        );

        btnEditarArma.setText("Editar");

        btnEliminarArma.setText("Eliminar");

        javax.swing.GroupLayout panelSelectorArmaLayout = new javax.swing.GroupLayout(panelSelectorArma);
        panelSelectorArma.setLayout(panelSelectorArmaLayout);
        panelSelectorArmaLayout.setHorizontalGroup(
            panelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSelectorArmaLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevaArma)
                        .addGap(130, 130, 130)
                        .addComponent(comboListaArmas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnEditarArma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarArma)))
                .addContainerGap())
        );
        panelSelectorArmaLayout.setVerticalGroup(
            panelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorArmaLayout.createSequentialGroup()
                .addGroup(panelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevaArma)
                    .addComponent(comboListaArmas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarArma)
                    .addComponent(btnEliminarArma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelConfigWeaponLayout = new javax.swing.GroupLayout(panelConfigWeapon);
        panelConfigWeapon.setLayout(panelConfigWeaponLayout);
        panelConfigWeaponLayout.setHorizontalGroup(
            panelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigWeaponLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMejoraArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigWeaponLayout.createSequentialGroup()
                        .addComponent(panelSelectorArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConfigWeaponLayout.setVerticalGroup(
            panelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigWeaponLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMejoraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSelectorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelConfig.addTab("Armas", panelConfigWeapon);

        panelConfigJoyas.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de joyas"));

        lblProbRotJoya.setText("Probabilidad de rotura");

        lblProbDetJoya.setText("Probabilidad de deterioro");

        lblProbSEJoya.setText("Probabilidad sin efecto");

        lblProbExitoJoya.setText("Probabilidad de exito");

        btnGuardarConfigJoyas.setText("Guardar");

        javax.swing.GroupLayout panelConfigJoyasLayout = new javax.swing.GroupLayout(panelConfigJoyas);
        panelConfigJoyas.setLayout(panelConfigJoyasLayout);
        panelConfigJoyasLayout.setHorizontalGroup(
            panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblProbSEJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                .addComponent(lblProbExitoJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spinnerProbExitoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerProbSEJoya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(spinnerProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnGuardarConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelConfigJoyasLayout.setVerticalGroup(
            panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotJoya)
                                .addComponent(spinnerProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelConfigJoyasLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEJoya)
                                .addComponent(spinnerProbSEJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbExitoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoJoya)))
                        .addComponent(btnGuardarConfigJoyas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        panelSelectorJoyas.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de joyas"));

        btnCrearNuevaJoya.setText("Nueva");

        panelEditorJoya.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de joyas"));

        lblNombreJoya.setText("Nombre");

        lblObjetoJoya.setText("Objeto");

        comboObjetoJoya.setName("tipojoya"); // NOI18N

        lblCalidadJoya.setText("Calidad");

        comboCalidadJoya.setName("calidadjoya"); // NOI18N

        lblPrecioVJoya.setText("Precio de venta");

        lblPrecioCJoya.setText("Precio de compra");

        checkComerciable.setText("Comerciable");

        checkCombinable.setText("Combinable");

        lblRoboMJoya.setText("Robo de maná");

        lblDefFisJoya.setText("Defensa física");

        lblDefMagJoya.setText("Defensa mágica");

        lblHRFisJoya.setText("Tasa acierto físico");

        lblEvaFisJoya.setText("Evasión física");

        lblAtaFisJoya.setText("Daño físico");

        lblManaJoya.setText("Maná");

        lblManaVidaJoya.setText("Vida");

        lblAtaMagJoya.setText("Daño mágico");

        lvlEvaMagJoya.setText("Evasión mágica");

        lblDineroExtraJoya.setText("Dinero extra");

        lblExpExtraJoya.setText("Experiencia extra");

        lblHRMagJoya.setText("Tasa acierto mágico");

        lblRoboVJoya.setText("Robo de vida");

        btnGuardarJoya.setText("Guardar");

        javax.swing.GroupLayout panelEditorJoyaLayout = new javax.swing.GroupLayout(panelEditorJoya);
        panelEditorJoya.setLayout(panelEditorJoyaLayout);
        panelEditorJoyaLayout.setHorizontalGroup(
            panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorJoyaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEditorJoyaLayout.createSequentialGroup()
                        .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditorJoyaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblPrecioCJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                    .addComponent(lblNombreJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblObjetoJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCalidadJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPrecioVJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblRoboVJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDineroExtraJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(lblExpExtraJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(checkComerciable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRoboMJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboObjetoJoya, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboCalidadJoya, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinnerPrecioVJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerPrecioCJoya)
                                .addComponent(txtNombreJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(spinnerDineroExtraJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkCombinable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(spinnerRoboMJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerExpExtraJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerRoboVJoya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblAtaFisJoya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEvaFisJoya)
                                .addComponent(lblManaJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblHRFisJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDefFisJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHRMagJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lvlEvaMagJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAtaMagJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDefMagJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblManaVidaJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerVidaJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(spinnerManaJoya)
                            .addComponent(spinnerAtaFisJoya)
                            .addComponent(spinnerEvaFisJoya)
                            .addComponent(spinnerHRFisJoya)
                            .addComponent(spinnerDefFisJoya)
                            .addComponent(spinnerAtaMagJoya)
                            .addComponent(spinnerEvaMagJoya)
                            .addComponent(spinnerHRMagJoya)
                            .addComponent(spinnerDefMagJoya))))
                .addContainerGap())
        );
        panelEditorJoyaLayout.setVerticalGroup(
            panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorJoyaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreJoya)
                    .addComponent(txtNombreJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblManaVidaJoya)
                    .addComponent(spinnerVidaJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjetoJoya)
                    .addComponent(comboObjetoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblManaJoya)
                    .addComponent(spinnerManaJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalidadJoya)
                    .addComponent(comboCalidadJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaFisJoya)
                    .addComponent(spinnerAtaFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVJoya)
                    .addComponent(spinnerPrecioVJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEvaFisJoya)
                    .addComponent(spinnerEvaFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCJoya)
                    .addComponent(spinnerPrecioCJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRFisJoya)
                    .addComponent(spinnerHRFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciable)
                    .addComponent(checkCombinable)
                    .addComponent(lblDefFisJoya)
                    .addComponent(spinnerDefFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraJoya)
                    .addComponent(lblAtaMagJoya)
                    .addComponent(spinnerDineroExtraJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerAtaMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraJoya)
                    .addComponent(lvlEvaMagJoya)
                    .addComponent(spinnerExpExtraJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerEvaMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoboVJoya)
                    .addComponent(lblHRMagJoya)
                    .addComponent(spinnerRoboVJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerHRMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoboMJoya)
                    .addComponent(spinnerRoboMJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDefMagJoya)
                    .addComponent(spinnerDefMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarJoya)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        btnEditarJoya.setText("Editar");

        btnEliminarJoya.setText("Eliminar");

        javax.swing.GroupLayout panelSelectorJoyasLayout = new javax.swing.GroupLayout(panelSelectorJoyas);
        panelSelectorJoyas.setLayout(panelSelectorJoyasLayout);
        panelSelectorJoyasLayout.setHorizontalGroup(
            panelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorJoyasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditorJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSelectorJoyasLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevaJoya)
                        .addGap(44, 44, 44)
                        .addComponent(comboSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelSelectorJoyasLayout.setVerticalGroup(
            panelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorJoyasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevaJoya)
                    .addComponent(comboSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarJoya)
                    .addComponent(btnEliminarJoya))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelConfigJewelsLayout = new javax.swing.GroupLayout(panelConfigJewels);
        panelConfigJewels.setLayout(panelConfigJewelsLayout);
        panelConfigJewelsLayout.setHorizontalGroup(
            panelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigJewelsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(239, Short.MAX_VALUE))
        );
        panelConfigJewelsLayout.setVerticalGroup(
            panelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigJewelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(333, 333, 333))
        );

        panelConfig.addTab("Joyas", panelConfigJewels);

        btnEditarSpawner.setText("Editar");
        btnEditarSpawner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSpawnerActionPerformed(evt);
            }
        });

        btnEliminarSpawner.setText("Eliminar");
        btnEliminarSpawner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSpawnerActionPerformed(evt);
            }
        });

        panelEditorSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de Spawner"));

        panelLocSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Localización"), "Localización"));

        lblMundoSpawnLoc.setText("Mundo");

        lblXSpawnLoc.setText("X");

        lblYSpawnLoc.setText("Y");

        lblZSpawnLoc.setText("Z");

        txtMundoSpawnLoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMundoSpawnLoc.setText("world");
        txtMundoSpawnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMundoSpawnLocActionPerformed(evt);
            }
        });

        spinnerXSpawnLoc.setModel(new javax.swing.SpinnerNumberModel());

        spinnerYSpawnLoc.setModel(new javax.swing.SpinnerNumberModel());

        spinnerZSpawnLoc.setModel(new javax.swing.SpinnerNumberModel());

        javax.swing.GroupLayout panelLocSpawnerLayout = new javax.swing.GroupLayout(panelLocSpawner);
        panelLocSpawner.setLayout(panelLocSpawnerLayout);
        panelLocSpawnerLayout.setHorizontalGroup(
            panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLocSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMundoSpawnLoc)
                    .addComponent(lblXSpawnLoc)
                    .addComponent(lblYSpawnLoc)
                    .addComponent(lblZSpawnLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerXSpawnLoc)
                    .addComponent(txtMundoSpawnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(spinnerYSpawnLoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spinnerZSpawnLoc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panelLocSpawnerLayout.setVerticalGroup(
            panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLocSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMundoSpawnLoc)
                    .addComponent(txtMundoSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXSpawnLoc)
                    .addComponent(spinnerXSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYSpawnLoc)
                    .addComponent(spinnerYSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZSpawnLoc)
                    .addComponent(spinnerZSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAtribSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades del generador"));

        lblMobSpawner.setText("Monstruo");

        comboMobSpawner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMobSpawnerActionPerformed(evt);
            }
        });

        lblRefrescoSpawner.setText("Cooldown");

        lblIdSpawner.setText("Identificador");

        spinnerRefrescoSpawner.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(0L), null, null, Long.valueOf(1L)));

        lblRadioSpawner.setText("Radio de generación");

        spinnerRadioSpawner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblNumMaxMobSpawner.setText("Núm maximo de monstruos");

        spinnerNumMaxMobSpawner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        checkEnableSpawner.setText("Habilitado/Deshabilitado");
        checkEnableSpawner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnableSpawnerActionPerformed(evt);
            }
        });

        comboSelectorSpawner.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout panelAtribSpawnerLayout = new javax.swing.GroupLayout(panelAtribSpawner);
        panelAtribSpawner.setLayout(panelAtribSpawnerLayout);
        panelAtribSpawnerLayout.setHorizontalGroup(
            panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtribSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtribSpawnerLayout.createSequentialGroup()
                        .addComponent(checkEnableSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(164, 164, 164))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtribSpawnerLayout.createSequentialGroup()
                        .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdSpawner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMobSpawner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRefrescoSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRadioSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumMaxMobSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerNumMaxMobSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(spinnerRadioSpawner)
                            .addComponent(spinnerRefrescoSpawner)
                            .addComponent(comboMobSpawner, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboSelectorSpawner))))
                .addContainerGap())
        );
        panelAtribSpawnerLayout.setVerticalGroup(
            panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtribSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdSpawner)
                    .addComponent(comboSelectorSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMobSpawner)
                    .addComponent(comboMobSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRefrescoSpawner)
                    .addComponent(spinnerRefrescoSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRadioSpawner)
                    .addComponent(spinnerRadioSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumMaxMobSpawner)
                    .addComponent(spinnerNumMaxMobSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(checkEnableSpawner)
                .addContainerGap())
        );

        btnGuardarSpawner.setText("Guardar");

        btnResetSpawner.setText("Resetear");
        btnResetSpawner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSpawnerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEditorSpawnerLayout = new javax.swing.GroupLayout(panelEditorSpawner);
        panelEditorSpawner.setLayout(panelEditorSpawnerLayout);
        panelEditorSpawnerLayout.setHorizontalGroup(
            panelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAtribSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLocSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResetSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEditorSpawnerLayout.setVerticalGroup(
            panelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelLocSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelAtribSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelEditorSpawnerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardarSpawner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetSpawner)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCrearSpawner.setText("Nuevo");

        javax.swing.GroupLayout panelConfigSpawnersLayout = new javax.swing.GroupLayout(panelConfigSpawners);
        panelConfigSpawners.setLayout(panelConfigSpawnersLayout);
        panelConfigSpawnersLayout.setHorizontalGroup(
            panelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigSpawnersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelEditorSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigSpawnersLayout.createSequentialGroup()
                        .addComponent(btnCrearSpawner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboSelectorGenerador, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(245, 245, 245))
        );
        panelConfigSpawnersLayout.setVerticalGroup(
            panelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigSpawnersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearSpawner)
                    .addComponent(comboSelectorGenerador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarSpawner)
                    .addComponent(btnEliminarSpawner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        panelConfig.addTab("Spawners", panelConfigSpawners);

        panelConfigGuilds.setPreferredSize(new java.awt.Dimension(800, 600));

        btnResetConfigClan.setText("Resetear configuración");

        btnGuardarConfigClan.setText("Guardar");

        panelConfigClanes.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración clanes"));

        panelNumJugClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de jugadores"));

        rdBtnNumIlimitadosClan.setSelected(true);
        rdBtnNumIlimitadosClan.setText("Ilimitados");

        rdBtnNumFijosClan.setText("Fijos");

        rdBtnLimitNivClan.setText("Limitado por nivel del clan");

        lblJugadoresClan.setText("jugadores");

        javax.swing.GroupLayout panelNumJugClanLayout = new javax.swing.GroupLayout(panelNumJugClan);
        panelNumJugClan.setLayout(panelNumJugClanLayout);
        panelNumJugClanLayout.setHorizontalGroup(
            panelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumJugClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnNumIlimitadosClan)
                    .addGroup(panelNumJugClanLayout.createSequentialGroup()
                        .addComponent(rdBtnNumFijosClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumFijosClan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJugadoresClan))
                    .addComponent(rdBtnLimitNivClan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelNumJugClanLayout.setVerticalGroup(
            panelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumJugClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnNumIlimitadosClan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnNumFijosClan)
                    .addComponent(spinnerNumFijosClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJugadoresClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnLimitNivClan))
        );

        panelSisContNivelClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Sistema de contribución clan limitado por nivel"));

        rdBtnContriSoloDon.setSelected(true);
        rdBtnContriSoloDon.setText("Sólo donación");

        rdBtnContriSoloContri.setText("Sólo contribución");

        rdBtnContriAmbos.setText("Ambos");

        lblporcMuertesClan.setText("%muertes");

        javax.swing.GroupLayout panelSisContNivelClanLayout = new javax.swing.GroupLayout(panelSisContNivelClan);
        panelSisContNivelClan.setLayout(panelSisContNivelClanLayout);
        panelSisContNivelClanLayout.setHorizontalGroup(
            panelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSisContNivelClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnContriSoloDon)
                    .addGroup(panelSisContNivelClanLayout.createSequentialGroup()
                        .addComponent(rdBtnContriSoloContri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerContriPorcentajeMuertes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblporcMuertesClan))
                    .addComponent(rdBtnContriAmbos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSisContNivelClanLayout.setVerticalGroup(
            panelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSisContNivelClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnContriSoloDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnContriSoloContri)
                    .addComponent(spinnerContriPorcentajeMuertes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblporcMuertesClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnContriAmbos))
        );

        lblnivMinJugCrearClan.setText("Nivel minimo del jugador para crear un clan");

        panelOpcLimNivelClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de clan limitado por nivel"));

        rdBtnOpcDinFijo.setSelected(true);
        rdBtnOpcDinFijo.setText("Dinero Fijo");

        rdBtnOpcDinForm.setText("Formula de dinero");

        panelFormulaClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Formula"));

        lblfdex.setText("f(x)=");

        spinnerformulax2clan.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        lblx2clan.setText("x^2+");

        spinnerformulaxclan.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        lblxclan.setText("x");

        lblMaxNivelClan.setText("Nivel máximo del clan");

        spinnerNivelMaxClan.setModel(new javax.swing.SpinnerNumberModel());

        btnValidarFormulaClan.setText("Validar");
        btnValidarFormulaClan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarFormulaClanActionPerformed(evt);
            }
        });

        btnResetearFormulaClan.setText("Resetear");
        btnResetearFormulaClan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetearFormulaClanActionPerformed(evt);
            }
        });

        lblFormJugXNivelClan.setText("Jugadores por nivel");

        spinnerFormJugXNivelClan.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        lblFormJugIniClan.setText("Jugadores inicial");

        spinnerFormJugIniClan.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        javax.swing.GroupLayout panelFormulaClanLayout = new javax.swing.GroupLayout(panelFormulaClan);
        panelFormulaClan.setLayout(panelFormulaClanLayout);
        panelFormulaClanLayout.setHorizontalGroup(
            panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnValidarFormulaClan)
                .addGap(26, 26, 26)
                .addComponent(btnResetearFormulaClan)
                .addGap(10, 10, 10))
            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormulaClanLayout.createSequentialGroup()
                        .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblfdex)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerformulax2clan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblx2clan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerformulaxclan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblMaxNivelClan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerNivelMaxClan))
                            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblFormJugXNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(spinnerFormJugXNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblxclan))
                    .addGroup(panelFormulaClanLayout.createSequentialGroup()
                        .addComponent(lblFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormulaClanLayout.setVerticalGroup(
            panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormulaClanLayout.createSequentialGroup()
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblfdex)
                    .addComponent(spinnerformulax2clan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblx2clan)
                    .addComponent(spinnerformulaxclan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblxclan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxNivelClan)
                    .addComponent(spinnerNivelMaxClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormJugXNivelClan)
                    .addComponent(spinnerFormJugXNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormJugIniClan)
                    .addComponent(spinnerFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnValidarFormulaClan)
                    .addComponent(btnResetearFormulaClan)))
        );

        panelEditNivelesClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor/Visor de niveles"));

        tablaClanes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nivel", "Dinero", "Núm. Jug."
            }
        ));
        tablaClanes.getTableHeader().setReorderingAllowed(false);
        tablaClanes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClanesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaClanesMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tablaClanes);

        btnAnadirNivel.setText("Añadir nivel");
        btnAnadirNivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirNivelActionPerformed(evt);
            }
        });

        btnEditarNivel.setText("Editar nivel");
        btnEditarNivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarNivelActionPerformed(evt);
            }
        });

        btnEliminarNivel.setText("Eliminar nivel");
        btnEliminarNivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarNivelActionPerformed(evt);
            }
        });

        lblDineroEditNivelClan.setText("Dinero");

        lblNumJugEditNivelClan.setText("Número Jugadores");

        javax.swing.GroupLayout panelEditNivelesClanLayout = new javax.swing.GroupLayout(panelEditNivelesClan);
        panelEditNivelesClan.setLayout(panelEditNivelesClanLayout);
        panelEditNivelesClanLayout.setHorizontalGroup(
            panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                        .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(btnAnadirNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditarNivel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(lblDineroEditNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerDineroEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                                .addComponent(lblNumJugEditNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spinnerNumJugEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelEditNivelesClanLayout.setVerticalGroup(
            panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditNivelesClanLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarNivel)
                    .addComponent(btnEliminarNivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerDineroEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDineroEditNivelClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumJugEditNivelClan)
                    .addComponent(spinnerNumJugEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAnadirNivel)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelOpcLimNivelClanLayout = new javax.swing.GroupLayout(panelOpcLimNivelClan);
        panelOpcLimNivelClan.setLayout(panelOpcLimNivelClanLayout);
        panelOpcLimNivelClanLayout.setHorizontalGroup(
            panelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcLimNivelClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnOpcDinFijo)
                    .addComponent(rdBtnOpcDinForm)
                    .addComponent(panelFormulaClan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditNivelesClan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelOpcLimNivelClanLayout.setVerticalGroup(
            panelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcLimNivelClanLayout.createSequentialGroup()
                .addGroup(panelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOpcLimNivelClanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rdBtnOpcDinFijo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdBtnOpcDinForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelFormulaClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelEditNivelesClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelConfigClanesLayout = new javax.swing.GroupLayout(panelConfigClanes);
        panelConfigClanes.setLayout(panelConfigClanesLayout);
        panelConfigClanesLayout.setHorizontalGroup(
            panelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigClanesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpcLimNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigClanesLayout.createSequentialGroup()
                        .addComponent(panelNumJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelSisContNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelConfigClanesLayout.createSequentialGroup()
                        .addComponent(lblnivMinJugCrearClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNivelMinJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConfigClanesLayout.setVerticalGroup(
            panelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigClanesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnivMinJugCrearClan)
                    .addComponent(spinnerNivelMinJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(panelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelNumJugClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSisContNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcLimNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelConfigGuildsLayout = new javax.swing.GroupLayout(panelConfigGuilds);
        panelConfigGuilds.setLayout(panelConfigGuildsLayout);
        panelConfigGuildsLayout.setHorizontalGroup(
            panelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGuildsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigGuildsLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(btnResetConfigClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarConfigClan))
                    .addComponent(panelConfigClanes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(681, 681, 681))
        );
        panelConfigGuildsLayout.setVerticalGroup(
            panelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGuildsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetConfigClan)
                    .addComponent(btnGuardarConfigClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConfigClanes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        panelConfig.addTab("Clanes", panelConfigGuilds);
        if(checkClanes.isSelected()){
            lblnivMinJugCrearClan.setEnabled(checkClanes.isSelected());
            spinnerNivelMinJugClan.setEnabled(checkClanes.isSelected());
            panelNumJugClan.setEnabled(checkClanes.isSelected());
            rdBtnNumIlimitadosClan.setEnabled(checkClanes.isSelected());
            rdBtnNumFijosClan.setEnabled(checkClanes.isSelected());
            rdBtnLimitNivClan.setEnabled(checkClanes.isSelected());
            if(rdBtnNumIlimitadosClan.isSelected()){
                spinnerNumFijosClan.setEnabled(false);
                lblJugadoresClan.setEnabled(false);
                panelOpcLimNivelClan.setEnabled(false);
                rdBtnOpcDinFijo.setEnabled(false);
                rdBtnOpcDinForm.setEnabled(false);
                panelFormulaClan.setEnabled(false);
                lblfdex.setEnabled(false);
                spinnerformulax2clan.setEnabled(false);
                lblx2clan.setEnabled(false);
                spinnerformulaxclan.setEnabled(false);
                lblxclan.setEnabled(false);
                lblMaxNivelClan.setEnabled(false);
                lblFormJugIniClan.setEnabled(false);
                spinnerFormJugIniClan.setEnabled(false);
                lblFormJugXNivelClan.setEnabled(false);
                spinnerFormJugXNivelClan.setEnabled(false);
                btnValidarFormulaClan.setEnabled(false);
                panelSisContNivelClan.setEnabled(false);
                rdBtnContriAmbos.setEnabled(false);
                rdBtnContriSoloContri.setEnabled(false);
                rdBtnContriSoloDon.setEnabled(false);
                spinnerContriPorcentajeMuertes.setEnabled(false);
                lblporcMuertesClan.setEnabled(false);
                panelEditNivelesClan.setEnabled(false);
                tablaClanes.setEnabled(false);
                btnEditarNivel.setEnabled(false);
                btnEliminarNivel.setEnabled(false);
                lblDineroEditNivelClan.setEnabled(false);
                spinnerDineroEditClan.setEnabled(false);
                lblNumJugEditNivelClan.setEnabled(false);
                spinnerNumJugEditClan.setEnabled(false);
                btnAnadirNivel.setEnabled(false);
            }
            if(rdBtnNumFijosClan.isSelected()){
                spinnerNumFijosClan.setEnabled(true);
                lblJugadoresClan.setEnabled(true);

                panelOpcLimNivelClan.setEnabled(false);
                rdBtnOpcDinFijo.setEnabled(false);
                rdBtnOpcDinForm.setEnabled(false);
                panelFormulaClan.setEnabled(false);
                lblfdex.setEnabled(false);
                spinnerformulax2clan.setEnabled(false);
                lblx2clan.setEnabled(false);
                spinnerformulaxclan.setEnabled(false);
                lblxclan.setEnabled(false);
                lblMaxNivelClan.setEnabled(false);
                lblFormJugIniClan.setEnabled(false);
                spinnerFormJugIniClan.setEnabled(false);
                lblFormJugXNivelClan.setEnabled(false);
                spinnerFormJugXNivelClan.setEnabled(false);
                btnValidarFormulaClan.setEnabled(false);
                panelSisContNivelClan.setEnabled(false);
                rdBtnContriAmbos.setEnabled(false);
                rdBtnContriSoloContri.setEnabled(false);
                rdBtnContriSoloDon.setEnabled(false);
                spinnerContriPorcentajeMuertes.setEnabled(false);
                lblporcMuertesClan.setEnabled(false);
                panelEditNivelesClan.setEnabled(false);
                tablaClanes.setEnabled(false);
                btnEditarNivel.setEnabled(false);
                btnEliminarNivel.setEnabled(false);
                lblDineroEditNivelClan.setEnabled(false);
                spinnerDineroEditClan.setEnabled(false);
                lblNumJugEditNivelClan.setEnabled(false);
                spinnerNumJugEditClan.setEnabled(false);
                btnAnadirNivel.setEnabled(false);
            }
            if(rdBtnLimitNivClan.isSelected()){
                spinnerNumFijosClan.setEnabled(false);
                lblJugadoresClan.setEnabled(false);
                panelSisContNivelClan.setEnabled(true);
                rdBtnContriSoloContri.setEnabled(true);
                rdBtnContriSoloDon.setEnabled(true);
                rdBtnContriAmbos.setEnabled(true);
                if(rdBtnContriSoloDon.isSelected()){
                    spinnerContriPorcentajeMuertes.setEnabled(false);
                    lblporcMuertesClan.setEnabled(false);
                }
                if(rdBtnContriSoloContri.isSelected() || rdBtnContriAmbos.isSelected()){
                    spinnerContriPorcentajeMuertes.setEnabled(true);
                    lblporcMuertesClan.setEnabled(true);
                }
                panelOpcLimNivelClan.setEnabled(true);
                rdBtnOpcDinFijo.setEnabled(true);
                rdBtnOpcDinForm.setEnabled(true);
                panelEditNivelesClan.setEnabled(true);
                tablaClanes.setEnabled(true);
                if(rdBtnOpcDinFijo.isSelected()){
                    panelFormulaClan.setEnabled(false);
                    lblfdex.setEnabled(false);
                    spinnerformulax2clan.setEnabled(false);
                    lblx2clan.setEnabled(false);
                    spinnerformulaxclan.setEnabled(false);
                    lblxclan.setEnabled(false);
                    lblMaxNivelClan.setEnabled(false);
                    spinnerNivelMaxClan.setEnabled(false);
                    lblFormJugIniClan.setEnabled(false);
                    spinnerFormJugIniClan.setEnabled(false);
                    lblFormJugXNivelClan.setEnabled(false);
                    spinnerFormJugXNivelClan.setEnabled(false);
                    btnValidarFormulaClan.setEnabled(false);
                    btnResetearFormulaClan.setEnabled(false);

                    btnEditarNivel.setEnabled(true);
                    btnEliminarNivel.setEnabled(true);
                    btnAnadirNivel.setEnabled(true);
                    lblDineroEditNivelClan.setEnabled(true);
                    spinnerDineroEditClan.setEnabled(true);
                    lblNumJugEditNivelClan.setEnabled(true);
                    spinnerNumJugEditClan.setEnabled(true);
                }
                if(rdBtnOpcDinForm.isSelected()){
                    panelFormulaClan.setEnabled(true);
                    lblfdex.setEnabled(true);
                    spinnerformulax2clan.setEnabled(true);
                    lblx2clan.setEnabled(true);
                    spinnerformulaxclan.setEnabled(true);
                    lblxclan.setEnabled(true);
                    lblMaxNivelClan.setEnabled(true);
                    spinnerNivelMaxClan.setEnabled(true);
                    lblFormJugIniClan.setEnabled(true);
                    spinnerFormJugIniClan.setEnabled(true);
                    lblFormJugXNivelClan.setEnabled(true);
                    spinnerFormJugXNivelClan.setEnabled(true);
                    btnValidarFormulaClan.setEnabled(true);
                    btnResetearFormulaClan.setEnabled(true);

                    btnEditarNivel.setEnabled(false);
                    btnEliminarNivel.setEnabled(false);
                    btnAnadirNivel.setEnabled(false);
                    lblDineroEditNivelClan.setEnabled(false);
                    spinnerDineroEditClan.setEnabled(false);
                    lblNumJugEditNivelClan.setEnabled(false);
                    spinnerNumJugEditClan.setEnabled(false);
                }
            }
        }

        btnGuardarConfigGrupos.setText("Guardar");

        btnResetConfigGrupos.setText("Resetear configuracion");

        panelConfigGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de grupos"));

        panelNumJugGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de jugadores"));

        lblJugadoresGrupo.setText("jugadores");

        rdBtnIlimitadosGrupo.setSelected(true);
        rdBtnIlimitadosGrupo.setText("Ilimitados");

        rdBtnFijosGrupo.setText("Fijos");
        rdBtnFijosGrupo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdBtnFijosGrupoStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelNumJugGruposLayout = new javax.swing.GroupLayout(panelNumJugGrupos);
        panelNumJugGrupos.setLayout(panelNumJugGruposLayout);
        panelNumJugGruposLayout.setHorizontalGroup(
            panelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumJugGruposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNumJugGruposLayout.createSequentialGroup()
                        .addComponent(rdBtnFijosGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumeroFijoJugGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJugadoresGrupo))
                    .addComponent(rdBtnIlimitadosGrupo)))
        );
        panelNumJugGruposLayout.setVerticalGroup(
            panelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumJugGruposLayout.createSequentialGroup()
                .addComponent(rdBtnIlimitadosGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnFijosGrupo)
                    .addComponent(spinnerNumeroFijoJugGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJugadoresGrupo)))
        );

        panelPvpGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("PvP"));

        rdBtnPermitidoPvpGrupo.setSelected(true);
        rdBtnPermitidoPvpGrupo.setText("Permitido");

        rdBtnNoPermitidoPvpGrupo.setText("No permitido");

        javax.swing.GroupLayout panelPvpGruposLayout = new javax.swing.GroupLayout(panelPvpGrupos);
        panelPvpGrupos.setLayout(panelPvpGruposLayout);
        panelPvpGruposLayout.setHorizontalGroup(
            panelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPvpGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnPermitidoPvpGrupo)
                    .addComponent(rdBtnNoPermitidoPvpGrupo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPvpGruposLayout.setVerticalGroup(
            panelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPvpGruposLayout.createSequentialGroup()
                .addComponent(rdBtnPermitidoPvpGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnNoPermitidoPvpGrupo))
        );

        panelSistRepGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Sistema de reparto"));

        rdBtnSinRepartoGrupo.setSelected(true);
        rdBtnSinRepartoGrupo.setText("Sin reparto");

        rdBtnSoloDineroGrupo.setText("Sólo dinero");

        rdBtnSoloExpGrupo.setText("Sólo experiencia");

        rdBtnAmbosGrupo.setText("Dinero y experiencia");

        javax.swing.GroupLayout panelSistRepGruposLayout = new javax.swing.GroupLayout(panelSistRepGrupos);
        panelSistRepGrupos.setLayout(panelSistRepGruposLayout);
        panelSistRepGruposLayout.setHorizontalGroup(
            panelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSistRepGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnSinRepartoGrupo)
                    .addComponent(rdBtnSoloDineroGrupo)
                    .addComponent(rdBtnSoloExpGrupo)
                    .addComponent(rdBtnAmbosGrupo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSistRepGruposLayout.setVerticalGroup(
            panelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSistRepGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdBtnSinRepartoGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnSoloDineroGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnSoloExpGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnAmbosGrupo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOpcRepGRupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de reparto"));

        rdBtnOpcionigualitarioGrupo.setSelected(true);
        rdBtnOpcionigualitarioGrupo.setText("Reparto igualitario");

        rdBtnOpcionProporcionalGrupo.setText("Reparto proporcional");

        panelOpcRepProp.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones reparto proporcional"));

        rdBtnProNivelGrupo.setSelected(true);
        rdBtnProNivelGrupo.setText("Por nivel del jugador");

        rdBtnProKillsGrupo.setText("Por muertes del jugador");
        rdBtnProKillsGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnProKillsGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcRepPropLayout = new javax.swing.GroupLayout(panelOpcRepProp);
        panelOpcRepProp.setLayout(panelOpcRepPropLayout);
        panelOpcRepPropLayout.setHorizontalGroup(
            panelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcRepPropLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnProNivelGrupo)
                    .addComponent(rdBtnProKillsGrupo))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        panelOpcRepPropLayout.setVerticalGroup(
            panelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcRepPropLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnProNivelGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnProKillsGrupo))
        );

        javax.swing.GroupLayout panelOpcRepGRuposLayout = new javax.swing.GroupLayout(panelOpcRepGRupos);
        panelOpcRepGRupos.setLayout(panelOpcRepGRuposLayout);
        panelOpcRepGRuposLayout.setHorizontalGroup(
            panelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcRepGRuposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpcRepProp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelOpcRepGRuposLayout.createSequentialGroup()
                        .addGroup(panelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdBtnOpcionigualitarioGrupo)
                            .addComponent(rdBtnOpcionProporcionalGrupo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelOpcRepGRuposLayout.setVerticalGroup(
            panelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcRepGRuposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnOpcionigualitarioGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnOpcionProporcionalGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcRepProp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelConfigGruposLayout = new javax.swing.GroupLayout(panelConfigGrupos);
        panelConfigGrupos.setLayout(panelConfigGruposLayout);
        panelConfigGruposLayout.setHorizontalGroup(
            panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelSistRepGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNumJugGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPvpGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpcRepGRupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigGruposLayout.setVerticalGroup(
            panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGruposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelNumJugGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPvpGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOpcRepGRupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSistRepGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panelConfigPartiesLayout = new javax.swing.GroupLayout(panelConfigParties);
        panelConfigParties.setLayout(panelConfigPartiesLayout);
        panelConfigPartiesLayout.setHorizontalGroup(
            panelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPartiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelConfigGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigPartiesLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(btnResetConfigGrupos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarConfigGrupos)))
                .addContainerGap(323, Short.MAX_VALUE))
        );
        panelConfigPartiesLayout.setVerticalGroup(
            panelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPartiesLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetConfigGrupos)
                    .addComponent(btnGuardarConfigGrupos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConfigGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );

        panelConfig.addTab("Grupos", panelConfigParties);

        panelConfigArmor.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración armaduras"));
        panelConfigArmor.setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane6.setMaximumSize(new java.awt.Dimension(1000, 600));
        jScrollPane6.setMinimumSize(new java.awt.Dimension(800, 600));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(1000, 600));

        panelSelectorArmadura.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de sets de armadura"));

        lblSelectorSet.setText("Seleccion de set");

        btnNuevoSet.setText("Nuevo set");

        btnEditarSet.setText("Editar set");

        btnEliminarSet.setText("Eliminar set");

        panelEditarSet.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de sets de armadura"));

        lblNombreSet.setText("Nombre de la armadura");

        lblNivel.setText("Nivel");

        comboMaterial.setName("material"); // NOI18N

        lblMaterial.setText("Material base del set");

        comboCalidad.setName("calidadarmor"); // NOI18N

        lblCalidad.setText("Calidad del set");

        checkComerciableArmadura.setText("Comerciable");

        checkMejorableArmadura.setText("Mejorable");

        panelPechera.setBorder(javax.swing.BorderFactory.createTitledBorder("Pechera"));

        lblDefFisPechera.setText("Defensa física");

        lblMejDefFisPechera.setText("Inc. Defensa física");

        lblMejEvaFisPechera.setText("Inc. Evasión física");

        lblEvaFisPechera.setText("Evasión física");

        lblPrecioVPechera.setText("Precio venta");

        lblPrecioCPechera.setText("Precio compra");

        lblExpExtraPechera.setText("Exp. extra");

        lblDineroExtraPechera.setText("Dinero extra");

        javax.swing.GroupLayout panelPecheraLayout = new javax.swing.GroupLayout(panelPechera);
        panelPechera.setLayout(panelPecheraLayout);
        panelPecheraLayout.setHorizontalGroup(
            panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPecheraLayout.createSequentialGroup()
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPecheraLayout.createSequentialGroup()
                        .addComponent(spinnerDefFisPechera, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPecheraLayout.createSequentialGroup()
                        .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerPrecioVPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisPechera))
                        .addGap(10, 10, 10))
                    .addGroup(panelPecheraLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioCPechera)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPecheraLayout.createSequentialGroup()
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerDineroExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(spinnerExpExtraPechera))
                .addContainerGap())
        );
        panelPecheraLayout.setVerticalGroup(
            panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPecheraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisPechera)
                    .addComponent(spinnerDefFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisPechera)
                    .addComponent(spinnerMejDefFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisPechera)
                    .addComponent(spinnerEvaFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisPechera)
                    .addComponent(spinnerMejEvaFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVPechera)
                    .addComponent(spinnerPrecioVPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCPechera)
                    .addComponent(spinnerPrecioCPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraPechera)
                    .addComponent(spinnerExpExtraPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraPechera)
                    .addComponent(spinnerDineroExtraPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPantalones.setBorder(javax.swing.BorderFactory.createTitledBorder("Grebas"));

        lblDefFisGrebas.setText("Defensa física");

        lblMejDefFisGrebas.setText("Inc. Defensa física");

        lvlMejEvaFisGrebas.setText("Inc. Evasión física");

        lblEvaFisGrebas.setText("Evasión física");

        lblPrecioVGrebas.setText("Precio venta");

        lblPrecioCGrebas.setText("Precio compra");

        lblExpExtraGrebas.setText("Exp. extra");

        lblDineroExtraGrebas.setText("Dinero extra");

        javax.swing.GroupLayout panelPantalonesLayout = new javax.swing.GroupLayout(panelPantalones);
        panelPantalones.setLayout(panelPantalonesLayout);
        panelPantalonesLayout.setHorizontalGroup(
            panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPantalonesLayout.createSequentialGroup()
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblExpExtraGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioVGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDefFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lvlMejEvaFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCGrebas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraGrebas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerDineroExtraGrebas)
                    .addComponent(spinnerPrecioVGrebas)
                    .addComponent(spinnerPrecioCGrebas)
                    .addComponent(spinnerExpExtraGrebas)
                    .addComponent(spinnerDefFisGrebas)
                    .addComponent(spinnerMejDefFisGrebas)
                    .addComponent(spinnerEvaFisGrebas)
                    .addComponent(spinnerMejEvaFisGrebas))
                .addContainerGap())
        );
        panelPantalonesLayout.setVerticalGroup(
            panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPantalonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisGrebas)
                    .addComponent(spinnerDefFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisGrebas)
                    .addComponent(spinnerMejDefFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisGrebas)
                    .addComponent(spinnerEvaFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlMejEvaFisGrebas)
                    .addComponent(spinnerMejEvaFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVGrebas)
                    .addComponent(spinnerPrecioVGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCGrebas)
                    .addComponent(spinnerPrecioCGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraGrebas)
                    .addComponent(spinnerExpExtraGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraGrebas)
                    .addComponent(spinnerDineroExtraGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelBotas.setBorder(javax.swing.BorderFactory.createTitledBorder("Botas"));

        lblDefFisBotas.setText("Defensa física");

        lblMejDefFisBotas.setText("Inc. Defensa física");

        lblMejEvaFisBotas.setText("Inc. Evasión física");

        lblEvaFisBotas.setText("Evasión física");

        lblPrecioVBotas.setText("Precio venta");

        lblPrecioCBotas.setText("Precio compra");

        lblExpExtraBotas.setText("Exp. extra");

        lblDineroExtraBotas.setText("Dinero extra");

        javax.swing.GroupLayout panelBotasLayout = new javax.swing.GroupLayout(panelBotas);
        panelBotas.setLayout(panelBotasLayout);
        panelBotasLayout.setHorizontalGroup(
            panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotasLayout.createSequentialGroup()
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotasLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioVBotas)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotasLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioCBotas)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotasLayout.createSequentialGroup()
                        .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisBotas)
                            .addComponent(spinnerMejDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(10, 10, 10))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotasLayout.createSequentialGroup()
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerDineroExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(spinnerExpExtraBotas))
                .addContainerGap())
        );
        panelBotasLayout.setVerticalGroup(
            panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisBotas)
                    .addComponent(spinnerDefFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisBotas)
                    .addComponent(spinnerMejDefFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisBotas)
                    .addComponent(spinnerEvaFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisBotas)
                    .addComponent(spinnerMejEvaFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVBotas)
                    .addComponent(spinnerPrecioVBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCBotas)
                    .addComponent(spinnerPrecioCBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraBotas)
                    .addComponent(spinnerExpExtraBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraBotas)
                    .addComponent(spinnerDineroExtraBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCasco.setBorder(javax.swing.BorderFactory.createTitledBorder("Yelmo"));

        lblDefFisCasco.setText("Defensa física");

        lblMejDefFisCasco.setText("Inc. Defensa física");

        lblMejEvaFisCasco.setText("Inc. Evasión física");

        lblEvaFisCasco.setText("Evasión física");

        lblPrecioVCasco.setText("Precio venta");

        lblPrecioCCasco.setText("Precio compra");

        lblExpExtraCasco.setText("Exp. extra");

        lblDineroExtraCasco.setText("Dinero extra");

        javax.swing.GroupLayout panelCascoLayout = new javax.swing.GroupLayout(panelCasco);
        panelCasco.setLayout(panelCascoLayout);
        panelCascoLayout.setHorizontalGroup(
            panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCascoLayout.createSequentialGroup()
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCascoLayout.createSequentialGroup()
                        .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerMejDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(spinnerEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisCasco))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCascoLayout.createSequentialGroup()
                        .addComponent(spinnerDefFisCasco)
                        .addContainerGap())
                    .addGroup(panelCascoLayout.createSequentialGroup()
                        .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerPrecioVCasco)
                            .addComponent(spinnerPrecioCCasco))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCascoLayout.createSequentialGroup()
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerExpExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(spinnerDineroExtraCasco))
                .addContainerGap())
        );
        panelCascoLayout.setVerticalGroup(
            panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCascoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisCasco)
                    .addComponent(spinnerDefFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisCasco)
                    .addComponent(spinnerMejDefFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisCasco)
                    .addComponent(spinnerEvaFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisCasco)
                    .addComponent(spinnerMejEvaFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVCasco)
                    .addComponent(spinnerPrecioVCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCCasco)
                    .addComponent(spinnerPrecioCCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraCasco)
                    .addComponent(spinnerExpExtraCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraCasco)
                    .addComponent(spinnerDineroExtraCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarSet.setText("Guardar");

        javax.swing.GroupLayout panelEditarSetLayout = new javax.swing.GroupLayout(panelEditarSet);
        panelEditarSet.setLayout(panelEditarSetLayout);
        panelEditarSetLayout.setHorizontalGroup(
            panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarSetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardarSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEditarSetLayout.createSequentialGroup()
                        .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBotas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkComerciableArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelEditarSetLayout.createSequentialGroup()
                                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombreSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboMaterial, 0, 165, Short.MAX_VALUE)
                                    .addComponent(txtNombreSet))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelEditarSetLayout.createSequentialGroup()
                                .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEditarSetLayout.createSequentialGroup()
                                .addComponent(lblCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(checkMejorableArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelPantalones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelEditarSetLayout.setVerticalGroup(
            panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarSetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreSet)
                    .addComponent(txtNombreSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivel)
                    .addComponent(spinnerNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaterial)
                    .addComponent(comboMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCalidad)
                    .addComponent(comboCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciableArmadura)
                    .addComponent(checkMejorableArmadura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPantalones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarSet)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout panelSelectorArmaduraLayout = new javax.swing.GroupLayout(panelSelectorArmadura);
        panelSelectorArmadura.setLayout(panelSelectorArmaduraLayout);
        panelSelectorArmaduraLayout.setHorizontalGroup(
            panelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelEditarSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelSelectorArmaduraLayout.createSequentialGroup()
                        .addComponent(lblSelectorSet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboListSets, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarSet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarSet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoSet)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSelectorArmaduraLayout.setVerticalGroup(
            panelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectorSet)
                    .addComponent(comboListSets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoSet)
                    .addComponent(btnEditarSet)
                    .addComponent(btnEliminarSet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditarSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelMejoraArmadura.setBorder(javax.swing.BorderFactory.createTitledBorder("Mejora de armaduras"));

        btnGuardarConfigArmadura.setText("Guardar");

        lblMejoradorArmadura.setText("objeto mejorador");

        lblNombreMejoradorArmadura.setText("Nombre del objeto");

        lblDescripcionMejoradorArmadura.setText("descripción");

        btnAnadirDescArmadura.setText("Añadir");
        btnAnadirDescArmadura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirDescArmaduraActionPerformed(evt);
            }
        });

        btnEditarDEscArmadura.setText("Editar");
        btnEditarDEscArmadura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDEscArmaduraActionPerformed(evt);
            }
        });

        btnEliminarDescArmadura.setText("Eliminar");
        btnEliminarDescArmadura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDescArmaduraActionPerformed(evt);
            }
        });

        jListDescArmadura.setBorder(javax.swing.BorderFactory.createTitledBorder("lista de descripción"));
        jListDescArmadura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jListDescArmadura);

        lblProbRotArmadura.setText("Probabilidad de rotura");

        lblProbDetArmadura.setText("Probabilidad de deterioro");

        lblProbSEArmadura.setText("Probabilidad sin efecto");

        lblProbExitoArmadura.setText("Probabilidad de exito");

        javax.swing.GroupLayout panelMejoraArmaduraLayout = new javax.swing.GroupLayout(panelMejoraArmadura);
        panelMejoraArmadura.setLayout(panelMejoraArmaduraLayout);
        panelMejoraArmaduraLayout.setHorizontalGroup(
            panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                        .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombreMejoradorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejoradorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDescripcionMejoradorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboMejoradorArmadura, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNombreObjetoMejArmadura)
                                    .addComponent(txtDescripcionArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                                .addComponent(btnAnadirDescArmadura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditarDEscArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminarDescArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spinnerProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblProbSEArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(lblProbExitoArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerProbExitoArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerProbSEArmadura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(btnGuardarConfigArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelMejoraArmaduraLayout.setVerticalGroup(
            panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotArmadura)
                                .addComponent(spinnerProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombreMejoradorArmadura)
                                .addComponent(txtNombreObjetoMejArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboMejoradorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMejoradorArmadura))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDescripcionMejoradorArmadura)
                                .addComponent(txtDescripcionArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAnadirDescArmadura)
                                .addComponent(btnEditarDEscArmadura)
                                .addComponent(btnEliminarDescArmadura)))
                        .addGroup(panelMejoraArmaduraLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEArmadura)
                                .addComponent(spinnerProbSEArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbExitoArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoArmadura)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnGuardarConfigArmadura)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelConfigArmadurasLayout = new javax.swing.GroupLayout(panelConfigArmaduras);
        panelConfigArmaduras.setLayout(panelConfigArmadurasLayout);
        panelConfigArmadurasLayout.setHorizontalGroup(
            panelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigArmadurasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMejoraArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSelectorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigArmadurasLayout.setVerticalGroup(
            panelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigArmadurasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMejoraArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSelectorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(panelConfigArmaduras);

        javax.swing.GroupLayout panelConfigArmorLayout = new javax.swing.GroupLayout(panelConfigArmor);
        panelConfigArmor.setLayout(panelConfigArmorLayout);
        panelConfigArmorLayout.setHorizontalGroup(
            panelConfigArmorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigArmorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConfigArmorLayout.setVerticalGroup(
            panelConfigArmorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigArmorLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelConfig.addTab("Armaduras", panelConfigArmor);

        panelConfigMobs.setPreferredSize(new java.awt.Dimension(800, 600));

        btnNuevoMob.setText("Nuevo");
        btnNuevoMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMobActionPerformed(evt);
            }
        });

        btnEditMob.setText("Editar");
        btnEditMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMobActionPerformed(evt);
            }
        });

        btnEliminarMob.setText("Eliminar");
        btnEliminarMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMobActionPerformed(evt);
            }
        });

        panelEditorMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de monstruos"));
        panelEditorMobs.setName("Class Editor"); // NOI18N

        lblNombreMob.setText("Nombre");

        btnGuardarMob.setText("Guardar");
        btnGuardarMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMobActionPerformed(evt);
            }
        });

        btnResetearMob.setText("Resetear");

        lblNivelMob.setText("Nivel");

        panelComportamientoMob.setBorder(javax.swing.BorderFactory.createTitledBorder("Comportamiento"));

        javax.swing.GroupLayout panelComportamientoMobLayout = new javax.swing.GroupLayout(panelComportamientoMob);
        panelComportamientoMob.setLayout(panelComportamientoMobLayout);
        panelComportamientoMobLayout.setHorizontalGroup(
            panelComportamientoMobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComportamientoMobLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboComportamiento, 0, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelComportamientoMobLayout.setVerticalGroup(
            panelComportamientoMobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComportamientoMobLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboComportamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelDropsMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Drops del monstruo"));

        panelDropsObjetosMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Drop de objetos"));

        lblObjetoDropMob.setText("Objeto");

        lblCantidadDropMob.setText("Cantidad");

        lblProbDropMob.setText("Probabilidad");

        spinnerCantidadDropMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        spinnerProbDropMob.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));

        tablaDropsMob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de objeto", "Objeto", "Cantidad", "Probabilidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tablaDropsMob);

        lblTipoDropMob.setText("Tipo de objeto");

        btnAnadirDropMob.setText("Añadir");

        btnEditarDropMob.setText("Editar");

        btnEliminarDropMob.setText("Eliminar");

        javax.swing.GroupLayout panelDropsObjetosMobsLayout = new javax.swing.GroupLayout(panelDropsObjetosMobs);
        panelDropsObjetosMobs.setLayout(panelDropsObjetosMobsLayout);
        panelDropsObjetosMobsLayout.setHorizontalGroup(
            panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsObjetosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelDropsObjetosMobsLayout.createSequentialGroup()
                        .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTipoDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(lblObjetoDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCantidadDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProbDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(spinnerCantidadDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(spinnerProbDropMob)
                            .addComponent(comboObjetoDropMob, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboTipoDropMob, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelDropsObjetosMobsLayout.createSequentialGroup()
                        .addComponent(btnAnadirDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDropsObjetosMobsLayout.setVerticalGroup(
            panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsObjetosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoDropMob)
                    .addComponent(comboTipoDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjetoDropMob)
                    .addComponent(comboObjetoDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidadDropMob)
                    .addComponent(spinnerCantidadDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProbDropMob)
                    .addComponent(spinnerProbDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnadirDropMob)
                    .addComponent(btnEditarDropMob)
                    .addComponent(btnEliminarDropMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDropsDefault.setBorder(javax.swing.BorderFactory.createTitledBorder("Drops básicos"));

        spinnerDineroDropMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spinnerExpDropMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblDineroDropMob.setText("Dinero");

        lblExpDropMob.setText("Experiencia");

        javax.swing.GroupLayout panelDropsDefaultLayout = new javax.swing.GroupLayout(panelDropsDefault);
        panelDropsDefault.setLayout(panelDropsDefaultLayout);
        panelDropsDefaultLayout.setHorizontalGroup(
            panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsDefaultLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDineroDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblExpDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerDineroDropMob)
                    .addComponent(spinnerExpDropMob))
                .addContainerGap())
        );
        panelDropsDefaultLayout.setVerticalGroup(
            panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsDefaultLayout.createSequentialGroup()
                .addGroup(panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroDropMob)
                    .addComponent(spinnerDineroDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpDropMob)
                    .addComponent(spinnerExpDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDropsMobsLayout = new javax.swing.GroupLayout(panelDropsMobs);
        panelDropsMobs.setLayout(panelDropsMobsLayout);
        panelDropsMobsLayout.setHorizontalGroup(
            panelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDropsObjetosMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDropsDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDropsMobsLayout.setVerticalGroup(
            panelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDropsMobsLayout.createSequentialGroup()
                .addComponent(panelDropsDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDropsObjetosMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelAtaqueMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de ataque"));

        javax.swing.GroupLayout panelAtaqueMobsLayout = new javax.swing.GroupLayout(panelAtaqueMobs);
        panelAtaqueMobs.setLayout(panelAtaqueMobsLayout);
        panelAtaqueMobsLayout.setHorizontalGroup(
            panelAtaqueMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtaqueMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTipoAtaqueMob, 0, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelAtaqueMobsLayout.setVerticalGroup(
            panelAtaqueMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtaqueMobsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboTipoAtaqueMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelAtributosMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos básicos del monstruo"));

        lblAtaFisMob.setText("Daño físico");

        lblAtaDistMob.setText("Daño a distancia");

        lblVelocidadMob.setText("Velocidad");

        lblVelocidadAtaqueMob.setText("Velocidad de ataque");

        lblRetrocesoMob.setText("Retroceso");

        lblRangoSeguiMob.setText("Rango de persecución");

        spinnerAtaDistMob.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(2.0d), Double.valueOf(1.0d), null, Double.valueOf(1.0d)));

        spinnerVelocidadMob.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.23f), Float.valueOf(0.01f), Float.valueOf(0.5f), Float.valueOf(0.01f)));

        spinnerVelocidadAtaqueMob.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(4.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));

        spinnerRetrocesoMob.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));

        spinnerRangoSeguiMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(23), Integer.valueOf(1), null, Integer.valueOf(1)));

        lblMaxVidaMob.setText("Vida máxima");

        spinnerMaxVidaMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(20), Integer.valueOf(1), null, Integer.valueOf(1)));

        lblFuerzaDistMob.setText("Fuerza a distancia");

        spinnerAtaFisMob.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(2.0d), Double.valueOf(1.0d), null, Double.valueOf(1.0d)));

        javax.swing.GroupLayout panelAtributosMobsLayout = new javax.swing.GroupLayout(panelAtributosMobs);
        panelAtributosMobs.setLayout(panelAtributosMobsLayout);
        panelAtributosMobsLayout.setHorizontalGroup(
            panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFuerzaDistMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaxVidaMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRangoSeguiMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRetrocesoMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVelocidadAtaqueMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVelocidadMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAtaFisMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAtaDistMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerVelocidadMob, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(spinnerAtaFisMob)
                    .addComponent(spinnerRangoSeguiMob)
                    .addComponent(spinnerRetrocesoMob)
                    .addComponent(spinnerVelocidadAtaqueMob)
                    .addComponent(spinnerAtaDistMob)
                    .addComponent(spinnerMaxVidaMob)
                    .addComponent(spinnerFuerzaDistMob))
                .addContainerGap())
        );
        panelAtributosMobsLayout.setVerticalGroup(
            panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributosMobsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisMob)
                    .addComponent(spinnerAtaFisMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaDistMob)
                    .addComponent(spinnerAtaDistMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVelocidadMob)
                    .addComponent(spinnerVelocidadMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVelocidadAtaqueMob)
                    .addComponent(spinnerVelocidadAtaqueMob, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRetrocesoMob)
                    .addComponent(spinnerRetrocesoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRangoSeguiMob)
                    .addComponent(spinnerRangoSeguiMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxVidaMob)
                    .addComponent(spinnerMaxVidaMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFuerzaDistMob)
                    .addComponent(spinnerFuerzaDistMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lblTipoMob.setText("Tipo de monstruo");

        comboSelectorTipoMob.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelEditorMobsLayout = new javax.swing.GroupLayout(panelEditorMobs);
        panelEditorMobs.setLayout(panelEditorMobsLayout);
        panelEditorMobsLayout.setHorizontalGroup(
            panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorMobsLayout.createSequentialGroup()
                .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorMobsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNombreMob)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreMob, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarMob, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetearMob, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelEditorMobsLayout.createSequentialGroup()
                            .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panelAtributosMobs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelAtaqueMobs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelComportamientoMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelDropsMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelEditorMobsLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblTipoMob)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(comboSelectorTipoMob, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        panelEditorMobsLayout.setVerticalGroup(
            panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivelMob)
                    .addComponent(spinnerNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarMob)
                    .addComponent(btnResetearMob)
                    .addComponent(lblNombreMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoMob)
                    .addComponent(comboSelectorTipoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorMobsLayout.createSequentialGroup()
                        .addComponent(panelAtributosMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelAtaqueMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelComportamientoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(94, Short.MAX_VALUE))
                    .addComponent(panelDropsMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panelConfigMobsLayout = new javax.swing.GroupLayout(panelConfigMobs);
        panelConfigMobs.setLayout(panelConfigMobsLayout);
        panelConfigMobsLayout.setHorizontalGroup(
            panelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelConfigMobsLayout.createSequentialGroup()
                        .addComponent(btnNuevoMob, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(226, 226, 226)
                        .addComponent(comboSelectorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditMob, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarMob, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        panelConfigMobsLayout.setVerticalGroup(
            panelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSelectorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoMob)
                    .addComponent(btnEditMob)
                    .addComponent(btnEliminarMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditorMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelConfig.addTab("Monstruos", panelConfigMobs);

        panelConfigChats.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de chats"));

        panelConfigMarketChat.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de comercio"));

        lblPrefixMarket.setText("Prefijo");

        lblPrefixColorMarket.setText("Color del prefijo");

        lblShortcutMarket.setText("atajo en el chat");

        lblMessageColorMarket.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigMarketChatLayout = new javax.swing.GroupLayout(panelConfigMarketChat);
        panelConfigMarketChat.setLayout(panelConfigMarketChatLayout);
        panelConfigMarketChatLayout.setHorizontalGroup(
            panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigMarketChatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMessageColorMarket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrefixMarket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrefixColorMarket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblShortcutMarket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPrefixColorMarketChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(textPrefixMarketChat)
                    .addComponent(comboShortcutMarketChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorMarketChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigMarketChatLayout.setVerticalGroup(
            panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigMarketChatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrefixMarket)
                    .addComponent(textPrefixMarketChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrefixColorMarket)
                    .addComponent(comboPrefixColorMarketChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShortcutMarket)
                    .addComponent(comboShortcutMarketChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigMarketChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMessageColorMarket)
                    .addComponent(comboMessageColorMarketChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelConfigGlobal.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat global"));

        lblPrefixGlobal.setText("Prefijo");

        lblPCG.setText("Color del prefijo");

        lblSG.setText("atajo en el chat");

        lblMCG.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigGlobalLayout = new javax.swing.GroupLayout(panelConfigGlobal);
        panelConfigGlobal.setLayout(panelConfigGlobalLayout);
        panelConfigGlobalLayout.setHorizontalGroup(
            panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrefixGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixGlobalChat)
                    .addComponent(comboPrefixColorGlobalChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboShortcutGlobalChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorGlobalChat, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigGlobalLayout.setVerticalGroup(
            panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGlobalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrefixGlobal)
                    .addComponent(textPrefixGlobalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCG)
                    .addComponent(comboPrefixColorGlobalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSG)
                    .addComponent(comboShortcutGlobalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCG)
                    .addComponent(comboMessageColorGlobalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelConfigParty.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de grupos"));

        lblPP.setText("Prefijo");

        lblPCP.setText("Color del prefijo");

        lblSP.setText("atajo en el chat");

        lblMCP.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigPartyLayout = new javax.swing.GroupLayout(panelConfigParty);
        panelConfigParty.setLayout(panelConfigPartyLayout);
        panelConfigPartyLayout.setHorizontalGroup(
            panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPartyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixPartyChat, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(comboPrefixColorPartyChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(comboShortcutPartyChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorPartyChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigPartyLayout.setVerticalGroup(
            panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigPartyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPP)
                    .addComponent(textPrefixPartyChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCP)
                    .addComponent(comboPrefixColorPartyChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSP)
                    .addComponent(comboShortcutPartyChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCP)
                    .addComponent(comboMessageColorPartyChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        panelConfigGuild.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de clanes"));

        lblPGuild.setText("Prefijo");

        lblPCGuild.setText("Color del prefijo");

        lblSGuild.setText("atajo en el chat");

        lblMCGuild.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigGuildLayout = new javax.swing.GroupLayout(panelConfigGuild);
        panelConfigGuild.setLayout(panelConfigGuildLayout);
        panelConfigGuildLayout.setHorizontalGroup(
            panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGuildLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCGuild, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPGuild, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCGuild, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSGuild, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixGuildChat, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(comboPrefixColorGuildChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(comboShortcutGuildChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorGuildChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigGuildLayout.setVerticalGroup(
            panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGuildLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPGuild)
                    .addComponent(textPrefixGuildChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCGuild)
                    .addComponent(comboPrefixColorGuildChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSGuild)
                    .addComponent(comboShortcutGuildChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigGuildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCGuild)
                    .addComponent(comboMessageColorGuildChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        panelConfiglPrivate.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat privado"));

        lblPPriv.setText("Prefijo");

        lblPCPriv.setText("Color del prefijo");

        lblSPriv.setText("atajo en el chat");

        lblMCPriv.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfiglPrivateLayout = new javax.swing.GroupLayout(panelConfiglPrivate);
        panelConfiglPrivate.setLayout(panelConfiglPrivateLayout);
        panelConfiglPrivateLayout.setHorizontalGroup(
            panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiglPrivateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCPriv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPPriv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCPriv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSPriv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixPrivateChat, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(comboPrefixColorPrivateChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(comboShortcutPrivateChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorPrivateChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfiglPrivateLayout.setVerticalGroup(
            panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiglPrivateLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPPriv)
                    .addComponent(textPrefixPrivateChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCPriv)
                    .addComponent(comboPrefixColorPrivateChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSPriv)
                    .addComponent(comboShortcutPrivateChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfiglPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCPriv)
                    .addComponent(comboMessageColorPrivateChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        panelConfigNews.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat noticias"));

        lblPN.setText("Prefijo");

        lblPCN.setText("Color del prefijo");

        lblSN.setText("atajo en el chat");

        lblMCN.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigNewsLayout = new javax.swing.GroupLayout(panelConfigNews);
        panelConfigNews.setLayout(panelConfigNewsLayout);
        panelConfigNewsLayout.setHorizontalGroup(
            panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigNewsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixNewsChat, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(comboPrefixColorNewsChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(comboShortcutNewsChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorNewsChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigNewsLayout.setVerticalGroup(
            panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigNewsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPN)
                    .addComponent(textPrefixNewsChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCN)
                    .addComponent(comboPrefixColorNewsChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSN)
                    .addComponent(comboShortcutNewsChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMCN)
                    .addComponent(comboMessageColorNewsChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelConfigWarning.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat incidencias"));

        lblPW.setText("Prefijo");

        lblPCW.setText("Color del prefijo");

        lblSW.setText("atajo en el chat");

        lblMCW.setText("Color del mensaje");

        javax.swing.GroupLayout panelConfigWarningLayout = new javax.swing.GroupLayout(panelConfigWarning);
        panelConfigWarning.setLayout(panelConfigWarningLayout);
        panelConfigWarningLayout.setHorizontalGroup(
            panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigWarningLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMCW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPCW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrefixWarningChat, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(comboPrefixColorWarningChat, 0, 154, Short.MAX_VALUE)
                    .addComponent(comboShortcutWarningChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMessageColorWarningChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigWarningLayout.setVerticalGroup(
            panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigWarningLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPW)
                    .addComponent(textPrefixWarningChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCW)
                    .addComponent(comboPrefixColorWarningChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSW)
                    .addComponent(comboShortcutWarningChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCW)
                    .addComponent(comboMessageColorWarningChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        panelConfigLocal.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat local"));

        lblPL.setText("Prefijo");

        lblPCL.setText("Color del prefijo");

        lblMCL.setText("Color del mensaje");

        lblDL.setText("Distancia de recepción");

        javax.swing.GroupLayout panelConfigLocalLayout = new javax.swing.GroupLayout(panelConfigLocal);
        panelConfigLocal.setLayout(panelConfigLocalLayout);
        panelConfigLocalLayout.setHorizontalGroup(
            panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigLocalLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigLocalLayout.createSequentialGroup()
                        .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelConfigLocalLayout.createSequentialGroup()
                                .addComponent(lblPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(panelConfigLocalLayout.createSequentialGroup()
                                .addComponent(lblPCL, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addGap(4, 4, 4)))
                        .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textPrefixLocalChat)
                            .addComponent(comboPrefixColorLocalChat, 0, 120, Short.MAX_VALUE)))
                    .addGroup(panelConfigLocalLayout.createSequentialGroup()
                        .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMCL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerDistanceLocalChat, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(comboMessageColorLocalChat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelConfigLocalLayout.setVerticalGroup(
            panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigLocalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPL)
                    .addComponent(textPrefixLocalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPCL)
                    .addComponent(comboPrefixColorLocalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDL)
                    .addComponent(spinnerDistanceLocalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMCL)
                    .addComponent(comboMessageColorLocalChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarConfigChats.setText("Guardar configuración");

        panelConfigEnableChats.setBorder(javax.swing.BorderFactory.createTitledBorder("Habilitar/Deshabilitar chats"));

        checkEnableGlobalChat.setSelected(true);
        checkEnableGlobalChat.setText("Habilitar/Deshabilitar chat global");
        checkEnableGlobalChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnableGlobalChatActionPerformed(evt);
            }
        });

        checkEnableMarketChat.setSelected(true);
        checkEnableMarketChat.setText("Habilitar/Deshabilitar chat comercio");
        checkEnableMarketChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnableMarketChatActionPerformed(evt);
            }
        });

        checkGuildChat.setSelected(true);
        checkGuildChat.setText("Habilitar/Deshabilitar chat clan");
        checkGuildChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkGuildChatActionPerformed(evt);
            }
        });

        checkPartyChat.setSelected(true);
        checkPartyChat.setText("Habilitar/Deshabilitar chat grupo");
        checkPartyChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPartyChatActionPerformed(evt);
            }
        });

        checkPrivateChat.setSelected(true);
        checkPrivateChat.setText("Habilitar/Deshabilitar chat privado");
        checkPrivateChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPrivateChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConfigEnableChatsLayout = new javax.swing.GroupLayout(panelConfigEnableChats);
        panelConfigEnableChats.setLayout(panelConfigEnableChatsLayout);
        panelConfigEnableChatsLayout.setHorizontalGroup(
            panelConfigEnableChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigEnableChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigEnableChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkPrivateChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkPartyChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkGuildChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkEnableMarketChat, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(checkEnableGlobalChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigEnableChatsLayout.setVerticalGroup(
            panelConfigEnableChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigEnableChatsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkEnableGlobalChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkEnableMarketChat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkGuildChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkPartyChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkPrivateChat))
        );

        javax.swing.GroupLayout panelConfigChatsLayout = new javax.swing.GroupLayout(panelConfigChats);
        panelConfigChats.setLayout(panelConfigChatsLayout);
        panelConfigChatsLayout.setHorizontalGroup(
            panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarConfigChats, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigChatsLayout.createSequentialGroup()
                        .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                .addComponent(panelConfigEnableChats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
                            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                .addComponent(panelConfigGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                .addComponent(panelConfigLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelConfigNews, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelConfigMarketChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelConfigGuild, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                        .addComponent(panelConfigParty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(347, 347, 347))
                                    .addComponent(panelConfiglPrivate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(panelConfigWarning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelConfigChatsLayout.setVerticalGroup(
            panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigChatsLayout.createSequentialGroup()
                .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConfigMarketChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConfigParty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConfigEnableChats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(panelConfigGuild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelConfiglPrivate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelConfigGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelConfigLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConfigNews, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelConfigWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarConfigChats)
                .addGap(504, 504, 504))
        );

        jScrollPane4.setViewportView(panelConfigChats);

        javax.swing.GroupLayout panelConfChatsLayout = new javax.swing.GroupLayout(panelConfChats);
        panelConfChats.setLayout(panelConfChatsLayout);
        panelConfChatsLayout.setHorizontalGroup(
            panelConfChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConfChatsLayout.setVerticalGroup(
            panelConfChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelConfig.addTab("Chat", panelConfChats);

        getContentPane().add(panelConfig, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMobActionPerformed

    private void btnEliminarMobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarMobActionPerformed

    private void btnEditMobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMobActionPerformed

    private void btnNuevoMobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoMobActionPerformed

    private void btnEliminarDescArmaduraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDescArmaduraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarDescArmaduraActionPerformed

    private void btnEditarDEscArmaduraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDEscArmaduraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarDEscArmaduraActionPerformed

    private void btnAnadirDescArmaduraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirDescArmaduraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnadirDescArmaduraActionPerformed

    private void rdBtnProKillsGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnProKillsGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdBtnProKillsGrupoActionPerformed

    private void rdBtnFijosGrupoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdBtnFijosGrupoStateChanged
        spinnerNumeroFijoJugGrupo.setEnabled(rdBtnFijosGrupo.isSelected());
    }//GEN-LAST:event_rdBtnFijosGrupoStateChanged

    private void btnEliminarNivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNivelActionPerformed
        // TODO add your handling code here:
        int row = tablaClanes.getRowCount();
        try {
            if (row == 0) {
                throw new Exception("No existen niveles para eliminar");
            } else {
                DefaultTableModel tdm = (DefaultTableModel) tablaClanes.getModel();
                tdm.removeRow(row - 1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarNivelActionPerformed

    private void btnEditarNivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarNivelActionPerformed
        // TODO add your handling code here:
        int row = tablaClanes.getSelectedRow();
        int dinero = (Integer) spinnerDineroEditClan.getValue();
        int jugadores = (Integer) spinnerNumJugEditClan.getValue();
        try {
            if (dinero <= 0) {
                spinnerDineroEditClan.setBackground(Color.red);
                throw new Exception("El dinero debe ser superior a 0");
            }
            if (jugadores <= 0) {
                spinnerNumJugEditClan.setBackground(Color.red);
                throw new Exception("Los jugadores deben ser superiores a 0");
            }
            if (row == -1) {
                tablaClanes.setBackground(Color.red);
                throw new Exception("Debes seleccionar primero una celda o fila de la tabla para editar.");
            } else {
                spinnerDineroEditClan.setBackground(Color.white);
                spinnerNumJugEditClan.setBackground(Color.white);
                tablaClanes.setBackground(Color.white);
                tablaClanes.setValueAt(dinero, row, 1);
                tablaClanes.setValueAt(jugadores, row, 2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error en la validación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarNivelActionPerformed

    private void btnAnadirNivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirNivelActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int row = tablaClanes.getRowCount();
        int dinero = (Integer) spinnerDineroEditClan.getValue();
        int jugadores = (Integer) spinnerNumJugEditClan.getValue();
        try {
            if (dinero <= 0) {
                spinnerDineroEditClan.setBackground(Color.red);
                throw new Exception("El dinero debe ser superior a 0");
            }
            if (jugadores <= 0) {
                spinnerNumJugEditClan.setBackground(Color.red);
                throw new Exception("Los jugadores deben ser superiores a 0");
            }

            spinnerDineroEditClan.setBackground(Color.white);
            spinnerNumJugEditClan.setBackground(Color.white);
            tablaClanes.setBackground(Color.white);
            Object[] data = new Object[3];
            data[0] = row + 1;
            data[1] = dinero;
            data[2] = jugadores;
            DefaultTableModel tdm = (DefaultTableModel) tablaClanes.getModel();
            tdm.addRow(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error en la validación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAnadirNivelActionPerformed

    private void tablaClanesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClanesMouseReleased
        // TODO add your handling code here:
        Object data[] = new Object[3];
        data[0] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 0);
        data[1] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 1);
        data[2] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 2);
        spinnerDineroEditClan.setValue(data[1]);
        spinnerNumJugEditClan.setValue(data[2]);
    }//GEN-LAST:event_tablaClanesMouseReleased

    private void tablaClanesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClanesMouseClicked
        // TODO add your handling code here
        Object data[] = new Object[3];
        data[0] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 0);
        data[1] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 1);
        data[2] = tablaClanes.getValueAt(tablaClanes.getSelectedRow(), 2);
        spinnerDineroEditClan.setValue(data[1]);
        spinnerNumJugEditClan.setValue(data[2]);
    }//GEN-LAST:event_tablaClanesMouseClicked

    private void btnResetearFormulaClanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetearFormulaClanActionPerformed
        // TODO add your handling code here:
        spinnerformulax2clan.setValue(1);
        spinnerformulaxclan.setValue(1);
        spinnerNivelMaxClan.setValue(1);
        spinnerFormJugXNivelClan.setValue(1);
        spinnerFormJugIniClan.setValue(1);
    }//GEN-LAST:event_btnResetearFormulaClanActionPerformed

    private void btnValidarFormulaClanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarFormulaClanActionPerformed
        // TODO add your handling code here:
        double a;
        double b;
        int jugini;
        int jugxnivel;
        int nivel;
        boolean error = false;
        boolean error_c = false;
        boolean error_jugini = false;
        boolean error_level = false;
        boolean error_jugxnivel = false;
        try {
            a = (Double) spinnerformulax2clan.getValue();
            b = (Double) spinnerformulaxclan.getValue();
            nivel = (Integer) spinnerNivelMaxClan.getValue();
            jugini = (Integer) spinnerFormJugIniClan.getValue();
            jugxnivel = (Integer) spinnerFormJugXNivelClan.getValue();
            if (nivel <= 0) {
                spinnerNivelMaxClan.setBackground(Color.red);
                error_level = true;
            }
            if (jugxnivel < 1) {
                spinnerFormJugXNivelClan.setBackground(Color.red);
                error_jugxnivel = true;
            }
            if (jugini < 1) {
                spinnerFormJugIniClan.setBackground(Color.red);
                error_jugini = true;
            }
            if (a < 0) {
                spinnerformulax2clan.setBackground(Color.red);
                error = true;
            }
            if (b <= 0) {
                spinnerformulaxclan.setBackground(Color.red);
                error_c = true;
            }
            if (a > 0 && b == 0) {
                error_c = true;
            }
            if (error) {
                throw new Exception("Los valores no pueden ser negativos");
            } else if (error_c) {
                throw new Exception("Al menos el último componente debe ser positivo y mayor que cero");
            } else if (error_level) {
                throw new Exception("El nivel maximo tiene que ser positivo y mayor que cero");
            } else if (error_jugxnivel) {
                throw new Exception("El número de jugadores por nivel debes ser igual o superior a 1");
            } else if (error_jugini) {
                throw new Exception("El numero minimo de jugadores por nivel debe ser igual o superior a 1");
            } else {
                spinnerformulax2clan.setBackground(Color.white);
                spinnerformulaxclan.setBackground(Color.white);
                spinnerNivelMaxClan.setBackground(Color.white);
                spinnerFormJugXNivelClan.setBackground(Color.white);
                DefaultTableModel dtm = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                dtm.addColumn("Nivel");
                dtm.addColumn("Dinero");
                dtm.addColumn("Jugadores");
                long resdinero;
                int jugadores = 0;
                Object[] data = new Object[3];

                for (int i = 1; i <= nivel; i++) {
                    resdinero = (long) (Math.pow(i, 2) * a + b * i);
                    if (i == 1) {
                        jugadores = jugini;
                    } else {
                        jugadores += jugxnivel;
                    }
                    data[0] = i;
                    data[1] = resdinero;
                    data[2] = jugadores;
                    dtm.addRow(data);
                }
                tablaClanes.setModel(dtm);
                tablaClanes.setCellSelectionEnabled(false);
                tablaClanes.setRowSelectionAllowed(true);
                tablaClanes.setCellSelectionEnabled(false);
                tablaClanes.setEnabled(true);
                tablaClanes.setVisible(true);
                tablaClanes.validate();
                tablaClanes.repaint();

            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(rootPane, "No pueden existir campos sin dato...", "Error en la validación", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Solucione los errores en la formula para continuar...", "Error en la validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error en la validación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnValidarFormulaClanActionPerformed

    private void btnResetSpawnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSpawnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetSpawnerActionPerformed

    private void checkEnableSpawnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEnableSpawnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkEnableSpawnerActionPerformed

    private void comboMobSpawnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMobSpawnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMobSpawnerActionPerformed

    private void txtMundoSpawnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMundoSpawnLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMundoSpawnLocActionPerformed

    private void btnEliminarSpawnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSpawnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarSpawnerActionPerformed

    private void btnEditarSpawnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSpawnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarSpawnerActionPerformed

    private void btnEliminarDescArmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDescArmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarDescArmaActionPerformed

    private void btnEditarDEscArmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDEscArmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarDEscArmaActionPerformed

    private void btnAnadirDescArmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirDescArmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnadirDescArmaActionPerformed

    private void btnGuardarObjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarObjetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarObjetoActionPerformed

    private void btnGuardarClaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarClaseActionPerformed

    private void btnEditarClaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarClaseActionPerformed

    private void lblMaxNivelNivelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblMaxNivelNivelPropertyChange
        btnDibujarGraficoNiveles.setEnabled(false);
        btnGuardarConfigNiveles.setEnabled(false);
    }//GEN-LAST:event_lblMaxNivelNivelPropertyChange

    private void btnDibujarGraficoNivelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDibujarGraficoNivelesActionPerformed
        int level;
        boolean error;
        RPGLevels levels;
        //Draw graph
        try {
            panelGraficosNivel.removeAll();
            if ((Integer) lblMaxNivelNivel.getValue() == 0) {
                lblMaxNivelNivel.setBackground(Color.red);
                throw new Exception("Rellena el campo nivel máximo");
            } else {
                level = (Integer) lblMaxNivelNivel.getValue();
                levels = new RPGLevels((Double) txtX3Nivel.getValue(), (Double) txtX2Nivel.getValue(), (Double) txtXNivel.getValue(), level);
                if (level <= 0) {
                    lblMaxNivelNivel.setBackground(Color.red);
                    throw new Exception("Selecciona un nivel mayor que 0 para continuar");
                } else {
                    lblMaxNivelNivel.setBackground(Color.white);
                    //dibujar grafico
                    XYDataset dataset = createDataset(level);

                    JFreeChart chart = ChartFactory.createXYLineChart("Level Progression", "level", "experience needed", dataset);
                    DefaultTableModel dtm = new DefaultTableModel();
                    dtm.addColumn("Level");
                    dtm.addColumn("Experiencia");
                    Object[] data = new Object[2];
                    for (Iterator<Map.Entry<Long, Integer>> it = levels.getTree().entrySet().iterator(); it.hasNext();) {
                        Map.Entry<Long, Integer> entrySet = it.next();
                        data[0] = entrySet.getValue();
                        data[1] = entrySet.getKey();
                        dtm.addRow(data);
                    }
                    tablaExpNivel.setModel(dtm);
                    tablaExpNivel.setCellSelectionEnabled(false);
                    tablaExpNivel.setEnabled(false);
                    tablaExpNivel.setVisible(true);
                    tablaExpNivel.validate();
                    tablaExpNivel.repaint();

                    ChartPanel chartPanel = new ChartPanel(chart);
                    chartPanel.setPreferredSize(new Dimension(800, 500));
                    chartPanel.setMouseWheelEnabled(true);
                    panelGraficosNivel.setLayout(new java.awt.BorderLayout());
                    panelGraficosNivel.add(chartPanel, BorderLayout.CENTER);
                    panelGraficosNivel.validate();
                    panelGraficosNivel.repaint();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error en nivel maximo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error en nivel maximo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDibujarGraficoNivelesActionPerformed

    private void btnGuardarConfigNivelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarConfigNivelesActionPerformed
        //Comprobar si el fichero existe
        //Si existe modificarlo
        //Si no existe crear en la ruta que toque
    }//GEN-LAST:event_btnGuardarConfigNivelesActionPerformed

    private void btnValidarFormulaNivelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarFormulaNivelesActionPerformed
        double a;
        double b;
        double c;
        boolean error = false;
        boolean error_c = false;
        double auxres = 0.00;
        try {
            a = (Double) txtX3Nivel.getValue();
            b = (Double) txtX2Nivel.getValue();
            c = (Double) txtXNivel.getValue();
            if (a < 0) {
                txtX3Nivel.setBackground(Color.red);
                error = true;
            }
            if (b < 0) {
                txtX2Nivel.setBackground(Color.red);
                error = true;
            }
            if (c <= 0) {
                txtXNivel.setBackground(Color.red);
                error_c = true;
            }
            if ((a > 0 || b > 0) && c == 0) {
                //mirar este error
                error_c = false;
            }
            if (error) {
                throw new Exception("Los valores no pueden ser negativos");
            } else if (error_c) {
                throw new Exception("Al menos el ultimo componente debe ser positivo y mayor que cero");
            } else {
                //habilitar botones Draw y save
                txtX3Nivel.setBackground(Color.white);
                txtX2Nivel.setBackground(Color.white);
                txtXNivel.setBackground(Color.white);

                btnGuardarConfigNiveles.setEnabled(true);
                btnDibujarGraficoNiveles.setEnabled(true);
                lblMaxNivelNivel.setEnabled(true);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(rootPane, "No pueden existir campos sin dato...", "Error en la validación", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Solucione los errores en la formula para continuar...", "Error en la validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error en la validación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnValidarFormulaNivelesActionPerformed

    private void btnGuardarConfigGlobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarConfigGlobalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarConfigGlobalActionPerformed

    private void checkGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGruposActionPerformed
        if (checkGrupos.isSelected()) {
            panelConfig.setEnabledAt(9, true);
        } else {
            panelConfig.setEnabledAt(9, false);
        }
    }//GEN-LAST:event_checkGruposActionPerformed

    private void checkClanesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkClanesActionPerformed
        if (checkClanes.isSelected()) {
            panelConfig.setEnabledAt(8, true);
        } else {
            panelConfig.setEnabledAt(8, false);
        }
    }//GEN-LAST:event_checkClanesActionPerformed

    private void btnpruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpruebaActionPerformed
        JOptionPane.showMessageDialog(null, "Tamaño ventana: " + this.getBounds().width + "," + this.getBounds().height, "RPGCraftCosta-Información", 1);
    }//GEN-LAST:event_btnpruebaActionPerformed

    private void checkEnableGlobalChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEnableGlobalChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkEnableGlobalChatActionPerformed

    private void checkEnableMarketChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEnableMarketChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkEnableMarketChatActionPerformed

    private void checkGuildChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGuildChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkGuildChatActionPerformed

    private void checkPartyChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPartyChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkPartyChatActionPerformed

    private void checkPrivateChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPrivateChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkPrivateChatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
            //</editor-fold>

        /* Create and display the form */
        //Create Default config to work with
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                createDefaultDirectories();

                new GUI().setVisible(true);

            }
        });
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirDesObjeto;
    private javax.swing.JButton btnAnadirDescArma;
    private javax.swing.JButton btnAnadirDescArmadura;
    private javax.swing.JButton btnAnadirDropMob;
    private javax.swing.JButton btnAnadirNivel;
    private javax.swing.JButton btnCrearNuevaArma;
    private javax.swing.JButton btnCrearNuevaClase;
    private javax.swing.JButton btnCrearNuevaJoya;
    private javax.swing.JButton btnCrearNuevoObjeto;
    private javax.swing.JButton btnCrearSpawner;
    private javax.swing.JButton btnDibujarGraficoNiveles;
    private javax.swing.JButton btnEditMob;
    private javax.swing.JButton btnEditarArma;
    private javax.swing.JButton btnEditarClase;
    private javax.swing.JButton btnEditarDEscArma;
    private javax.swing.JButton btnEditarDEscArmadura;
    private javax.swing.JButton btnEditarDesObjeto;
    private javax.swing.JButton btnEditarDropMob;
    private javax.swing.JButton btnEditarJoya;
    private javax.swing.JButton btnEditarNivel;
    private javax.swing.JButton btnEditarObjeto;
    private javax.swing.JButton btnEditarSet;
    private javax.swing.JButton btnEditarSpawner;
    private javax.swing.JButton btnEliminarArma;
    private javax.swing.JButton btnEliminarClase;
    private javax.swing.JButton btnEliminarDesObjeto;
    private javax.swing.JButton btnEliminarDescArma;
    private javax.swing.JButton btnEliminarDescArmadura;
    private javax.swing.JButton btnEliminarDropMob;
    private javax.swing.JButton btnEliminarJoya;
    private javax.swing.JButton btnEliminarMob;
    private javax.swing.JButton btnEliminarNivel;
    private javax.swing.JButton btnEliminarObjeto;
    private javax.swing.JButton btnEliminarSet;
    private javax.swing.JButton btnEliminarSpawner;
    private javax.swing.ButtonGroup btnGroupOpcionRepartoGrupo;
    private javax.swing.JButton btnGuardarArma;
    private javax.swing.JButton btnGuardarClase;
    private javax.swing.JButton btnGuardarConfigArma;
    private javax.swing.JButton btnGuardarConfigArmadura;
    private javax.swing.JButton btnGuardarConfigChats;
    private javax.swing.JButton btnGuardarConfigClan;
    private javax.swing.JButton btnGuardarConfigGlobal;
    private javax.swing.JButton btnGuardarConfigGrupos;
    private javax.swing.JButton btnGuardarConfigJoyas;
    private javax.swing.JButton btnGuardarConfigNiveles;
    private javax.swing.JButton btnGuardarJoya;
    private javax.swing.JButton btnGuardarMob;
    private javax.swing.JButton btnGuardarObjeto;
    private javax.swing.JButton btnGuardarSet;
    private javax.swing.JButton btnGuardarSpawner;
    private javax.swing.JButton btnNuevoMob;
    private javax.swing.JButton btnNuevoSet;
    private javax.swing.JButton btnResetClase;
    private javax.swing.JButton btnResetConfigClan;
    private javax.swing.JButton btnResetConfigGrupos;
    private javax.swing.JButton btnResetSpawner;
    private javax.swing.JButton btnResetearFormulaClan;
    private javax.swing.JButton btnResetearMob;
    private javax.swing.JButton btnValidarFormulaClan;
    private javax.swing.JButton btnValidarFormulaNiveles;
    private javax.swing.ButtonGroup btngroupNumJugClan;
    private javax.swing.ButtonGroup btngroupNumJugGrupo;
    private javax.swing.ButtonGroup btngroupOpcLimNivelClan;
    private javax.swing.ButtonGroup btngroupOpcionRepartoPropGrupo;
    private javax.swing.ButtonGroup btngroupPvpGrupo;
    private javax.swing.ButtonGroup btngroupRepartoGrupo;
    private javax.swing.ButtonGroup btngroupSistContrClan;
    private javax.swing.JButton btnprueba;
    private javax.swing.JCheckBox checkClanes;
    private javax.swing.JCheckBox checkColocarBloques;
    private javax.swing.JCheckBox checkCombinable;
    private javax.swing.JCheckBox checkComerciable;
    private javax.swing.JCheckBox checkComerciableArma;
    private javax.swing.JCheckBox checkComerciableArmadura;
    private javax.swing.JCheckBox checkDanioAhogo;
    private javax.swing.JCheckBox checkDanioCaida;
    private javax.swing.JCheckBox checkDanioPvp;
    private javax.swing.JCheckBox checkDayCycle;
    private javax.swing.JCheckBox checkDestruirBloques;
    private javax.swing.JCheckBox checkEnableClase;
    private javax.swing.JCheckBox checkEnableGlobalChat;
    private javax.swing.JCheckBox checkEnableMarketChat;
    private javax.swing.JCheckBox checkEnableSpawner;
    private javax.swing.JCheckBox checkGrupos;
    private javax.swing.JCheckBox checkGuildChat;
    private javax.swing.JCheckBox checkMejorableArma;
    private javax.swing.JCheckBox checkMejorableArmadura;
    private javax.swing.JCheckBox checkPartyChat;
    private javax.swing.JCheckBox checkPrivateChat;
    private javax.swing.JCheckBox checkSistHambre;
    private javax.swing.JComboBox comboCalidad;
    private javax.swing.JComboBox comboCalidadArma;
    private javax.swing.JComboBox comboCalidadJoya;
    private javax.swing.JComboBox comboCalidadObjeto;
    private javax.swing.JComboBox comboComportamiento;
    private javax.swing.JComboBox comboListSets;
    private javax.swing.JComboBox comboListaArmas;
    private javax.swing.JComboBox comboListaObjetos;
    private javax.swing.JComboBox comboMaterial;
    private javax.swing.JComboBox comboMejoradorArma;
    private javax.swing.JComboBox comboMejoradorArmadura;
    private javax.swing.JComboBox comboMessageColorGlobalChat;
    private javax.swing.JComboBox comboMessageColorGuildChat;
    private javax.swing.JComboBox comboMessageColorLocalChat;
    private javax.swing.JComboBox comboMessageColorMarketChat;
    private javax.swing.JComboBox comboMessageColorNewsChat;
    private javax.swing.JComboBox comboMessageColorPartyChat;
    private javax.swing.JComboBox comboMessageColorPrivateChat;
    private javax.swing.JComboBox comboMessageColorWarningChat;
    private javax.swing.JComboBox comboMobSpawner;
    private javax.swing.JComboBox comboObjetoDropMob;
    private javax.swing.JComboBox comboObjetoJoya;
    private javax.swing.JComboBox comboPrefixColorGlobalChat;
    private javax.swing.JComboBox comboPrefixColorGuildChat;
    private javax.swing.JComboBox comboPrefixColorLocalChat;
    private javax.swing.JComboBox comboPrefixColorMarketChat;
    private javax.swing.JComboBox comboPrefixColorNewsChat;
    private javax.swing.JComboBox comboPrefixColorPartyChat;
    private javax.swing.JComboBox comboPrefixColorPrivateChat;
    private javax.swing.JComboBox comboPrefixColorWarningChat;
    private javax.swing.JComboBox comboSelectorClases;
    private javax.swing.JComboBox comboSelectorGenerador;
    private javax.swing.JComboBox comboSelectorJoyas;
    private javax.swing.JComboBox comboSelectorMobs;
    private javax.swing.JTextField comboSelectorSpawner;
    private javax.swing.JComboBox comboSelectorTipoMob;
    private javax.swing.JComboBox comboShortcutGlobalChat;
    private javax.swing.JComboBox comboShortcutGuildChat;
    private javax.swing.JComboBox comboShortcutMarketChat;
    private javax.swing.JComboBox comboShortcutNewsChat;
    private javax.swing.JComboBox comboShortcutPartyChat;
    private javax.swing.JComboBox comboShortcutPrivateChat;
    private javax.swing.JComboBox comboShortcutWarningChat;
    private javax.swing.JComboBox comboTipoArma;
    private javax.swing.JComboBox comboTipoAtaqueMob;
    private javax.swing.JComboBox comboTipoDropMob;
    private javax.swing.JComboBox comboTipoObjeto;
    private javax.swing.JList jListDescArma;
    private javax.swing.JList jListDescArmadura;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblAtaDistMob;
    private javax.swing.JLabel lblAtaFisAP;
    private javax.swing.JLabel lblAtaFisArma;
    private javax.swing.JLabel lblAtaFisBase;
    private javax.swing.JLabel lblAtaFisJoya;
    private javax.swing.JLabel lblAtaFisMob;
    private javax.swing.JLabel lblAtaFisNivel;
    private javax.swing.JLabel lblAtaMagAP;
    private javax.swing.JLabel lblAtaMagArma;
    private javax.swing.JLabel lblAtaMagBase;
    private javax.swing.JLabel lblAtaMagJoya;
    private javax.swing.JLabel lblAtaMagNivel;
    private javax.swing.JLabel lblCalidad;
    private javax.swing.JLabel lblCalidadArma;
    private javax.swing.JLabel lblCalidadJoya;
    private javax.swing.JLabel lblCalidadObjeto;
    private javax.swing.JLabel lblCantidadDropMob;
    private javax.swing.JLabel lblCritAP;
    private javax.swing.JLabel lblCritArma;
    private javax.swing.JLabel lblCritBase;
    private javax.swing.JLabel lblCritNivel;
    private javax.swing.JLabel lblDL;
    private javax.swing.JLabel lblDefFisBase;
    private javax.swing.JLabel lblDefFisBotas;
    private javax.swing.JLabel lblDefFisCasco;
    private javax.swing.JLabel lblDefFisGrebas;
    private javax.swing.JLabel lblDefFisJoya;
    private javax.swing.JLabel lblDefFisNivel;
    private javax.swing.JLabel lblDefFisPechera;
    private javax.swing.JLabel lblDefMagBase;
    private javax.swing.JLabel lblDefMagJoya;
    private javax.swing.JLabel lblDefMagNivel;
    private javax.swing.JLabel lblDescripObjeto;
    private javax.swing.JLabel lblDescripcionArma;
    private javax.swing.JLabel lblDescripcionMejoradorArmadura;
    private javax.swing.JLabel lblDineroDropMob;
    private javax.swing.JLabel lblDineroEditNivelClan;
    private javax.swing.JLabel lblDineroExtraArma;
    private javax.swing.JLabel lblDineroExtraBotas;
    private javax.swing.JLabel lblDineroExtraCasco;
    private javax.swing.JLabel lblDineroExtraGrebas;
    private javax.swing.JLabel lblDineroExtraJoya;
    private javax.swing.JLabel lblDineroExtraPechera;
    private javax.swing.JLabel lblEvaFisBase;
    private javax.swing.JLabel lblEvaFisBotas;
    private javax.swing.JLabel lblEvaFisCasco;
    private javax.swing.JLabel lblEvaFisGrebas;
    private javax.swing.JLabel lblEvaFisJoya;
    private javax.swing.JLabel lblEvaFisNivel;
    private javax.swing.JLabel lblEvaFisPechera;
    private javax.swing.JLabel lblEvaMagBase;
    private javax.swing.JLabel lblEvaMagNivel;
    private javax.swing.JLabel lblExpDropMob;
    private javax.swing.JLabel lblExpExtraArma;
    private javax.swing.JLabel lblExpExtraBotas;
    private javax.swing.JLabel lblExpExtraCasco;
    private javax.swing.JLabel lblExpExtraGrebas;
    private javax.swing.JLabel lblExpExtraJoya;
    private javax.swing.JLabel lblExpExtraPechera;
    private javax.swing.JLabel lblFdeXNivel;
    private javax.swing.JLabel lblFormJugIniClan;
    private javax.swing.JLabel lblFormJugXNivelClan;
    private javax.swing.JLabel lblFuerzaDistMob;
    private javax.swing.JLabel lblHRFisArma;
    private javax.swing.JLabel lblHRFisBase;
    private javax.swing.JLabel lblHRFisJoya;
    private javax.swing.JLabel lblHRFisNivel;
    private javax.swing.JLabel lblHRMagArma;
    private javax.swing.JLabel lblHRMagBase;
    private javax.swing.JLabel lblHRMagJoya;
    private javax.swing.JLabel lblHRMagNivel;
    private javax.swing.JLabel lblIdSpawner;
    private javax.swing.JLabel lblInfoNivel;
    private javax.swing.JLabel lblInfoNivel2;
    private javax.swing.JLabel lblInfoNivelMaxNivel;
    private javax.swing.JLabel lblJugadoresClan;
    private javax.swing.JLabel lblJugadoresGrupo;
    private javax.swing.JLabel lblMCG;
    private javax.swing.JLabel lblMCGuild;
    private javax.swing.JLabel lblMCL;
    private javax.swing.JLabel lblMCN;
    private javax.swing.JLabel lblMCP;
    private javax.swing.JLabel lblMCPriv;
    private javax.swing.JLabel lblMCW;
    private javax.swing.JLabel lblManaAP;
    private javax.swing.JLabel lblManaAP2;
    private javax.swing.JLabel lblManaBase;
    private javax.swing.JLabel lblManaJoya;
    private javax.swing.JLabel lblManaNivel;
    private javax.swing.JLabel lblManaVidaJoya;
    private javax.swing.JLabel lblMaterial;
    private javax.swing.JLabel lblMaxNivelClan;
    private javax.swing.JSpinner lblMaxNivelNivel;
    private javax.swing.JLabel lblMaxVidaMob;
    private javax.swing.JLabel lblMejAtaFisArma;
    private javax.swing.JLabel lblMejAtaMagArma;
    private javax.swing.JLabel lblMejCritArma;
    private javax.swing.JLabel lblMejDefFisBotas;
    private javax.swing.JLabel lblMejDefFisCasco;
    private javax.swing.JLabel lblMejDefFisGrebas;
    private javax.swing.JLabel lblMejDefFisPechera;
    private javax.swing.JLabel lblMejEvaFisBotas;
    private javax.swing.JLabel lblMejEvaFisCasco;
    private javax.swing.JLabel lblMejEvaFisPechera;
    private javax.swing.JLabel lblMejHRFisArma;
    private javax.swing.JLabel lblMejHRMagArma;
    private javax.swing.JLabel lblMejRoboMArma;
    private javax.swing.JLabel lblMejoradorArmadura;
    private javax.swing.JLabel lblMessageColorMarket;
    private javax.swing.JLabel lblMobSpawner;
    private javax.swing.JLabel lblMortalAP;
    private javax.swing.JLabel lblMortalBase;
    private javax.swing.JLabel lblMortalNivel;
    private javax.swing.JLabel lblMundoInicio;
    private javax.swing.JLabel lblMundoSpawnLoc;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNivelIniArma;
    private javax.swing.JLabel lblNivelMinArma;
    private javax.swing.JLabel lblNivelMob;
    private javax.swing.JLabel lblNomMejoraArma;
    private javax.swing.JLabel lblNombreArma;
    private javax.swing.JLabel lblNombreClase;
    private javax.swing.JLabel lblNombreJoya;
    private javax.swing.JLabel lblNombreMejoradorArmadura;
    private javax.swing.JLabel lblNombreMob;
    private javax.swing.JLabel lblNombreObjeto;
    private javax.swing.JLabel lblNombreSet;
    private javax.swing.JLabel lblNumJugEditNivelClan;
    private javax.swing.JLabel lblNumMaxMobSpawner;
    private javax.swing.JLabel lblObjetoDropMob;
    private javax.swing.JLabel lblObjetoJoya;
    private javax.swing.JLabel lblPCG;
    private javax.swing.JLabel lblPCGuild;
    private javax.swing.JLabel lblPCL;
    private javax.swing.JLabel lblPCN;
    private javax.swing.JLabel lblPCP;
    private javax.swing.JLabel lblPCPriv;
    private javax.swing.JLabel lblPCW;
    private javax.swing.JLabel lblPGuild;
    private javax.swing.JLabel lblPHxNivel;
    private javax.swing.JLabel lblPL;
    private javax.swing.JLabel lblPN;
    private javax.swing.JLabel lblPP;
    private javax.swing.JLabel lblPPriv;
    private javax.swing.JLabel lblPW;
    private javax.swing.JLabel lblPrecioCArma;
    private javax.swing.JLabel lblPrecioCBotas;
    private javax.swing.JLabel lblPrecioCCasco;
    private javax.swing.JLabel lblPrecioCGrebas;
    private javax.swing.JLabel lblPrecioCJoya;
    private javax.swing.JLabel lblPrecioCPechera;
    private javax.swing.JLabel lblPrecioVArma;
    private javax.swing.JLabel lblPrecioVBotas;
    private javax.swing.JLabel lblPrecioVCasco;
    private javax.swing.JLabel lblPrecioVGrebas;
    private javax.swing.JLabel lblPrecioVJoya;
    private javax.swing.JLabel lblPrecioVPechera;
    private javax.swing.JLabel lblPrefixColorMarket;
    private javax.swing.JLabel lblPrefixGlobal;
    private javax.swing.JLabel lblPrefixMarket;
    private javax.swing.JLabel lblProbDetArma;
    private javax.swing.JLabel lblProbDetArmadura;
    private javax.swing.JLabel lblProbDetJoya;
    private javax.swing.JLabel lblProbDropMob;
    private javax.swing.JLabel lblProbExitoArma;
    private javax.swing.JLabel lblProbExitoArmadura;
    private javax.swing.JLabel lblProbExitoJoya;
    private javax.swing.JLabel lblProbRotArma;
    private javax.swing.JLabel lblProbRotArmadura;
    private javax.swing.JLabel lblProbRotJoya;
    private javax.swing.JLabel lblProbSEArma;
    private javax.swing.JLabel lblProbSEArmadura;
    private javax.swing.JLabel lblProbSEJoya;
    private javax.swing.JLabel lblRadioSpawner;
    private javax.swing.JLabel lblRangoSeguiMob;
    private javax.swing.JLabel lblRefrescoSpawner;
    private javax.swing.JLabel lblRetrocesoMob;
    private javax.swing.JLabel lblRoboMArma;
    private javax.swing.JLabel lblRoboMJoya;
    private javax.swing.JLabel lblRoboVJoya;
    private javax.swing.JLabel lblSG;
    private javax.swing.JLabel lblSGuild;
    private javax.swing.JLabel lblSN;
    private javax.swing.JLabel lblSP;
    private javax.swing.JLabel lblSPriv;
    private javax.swing.JLabel lblSW;
    private javax.swing.JLabel lblSelectorSet;
    private javax.swing.JLabel lblShortcutMarket;
    private javax.swing.JLabel lblTipoArma;
    private javax.swing.JLabel lblTipoDropMob;
    private javax.swing.JLabel lblTipoMejoradorArma;
    private javax.swing.JLabel lblTipoMob;
    private javax.swing.JLabel lblTipoObjeto;
    private javax.swing.JLabel lblVelocidadAtaqueMob;
    private javax.swing.JLabel lblVelocidadMob;
    private javax.swing.JLabel lblVidaAP;
    private javax.swing.JLabel lblVidaAP2;
    private javax.swing.JLabel lblVidaBase;
    private javax.swing.JLabel lblVidaNivel;
    private javax.swing.JLabel lblXInicio;
    private javax.swing.JLabel lblXSpawnLoc;
    private javax.swing.JLabel lblYInicio;
    private javax.swing.JLabel lblYSpawnLoc;
    private javax.swing.JLabel lblZInicio;
    private javax.swing.JLabel lblZSpawnLoc;
    private javax.swing.JLabel lblfdex;
    private javax.swing.JLabel lblnivMinJugCrearClan;
    private javax.swing.JLabel lblporcMuertesClan;
    private javax.swing.JLabel lblx2Nivel;
    private javax.swing.JLabel lblx2clan;
    private javax.swing.JLabel lblx3Nivel;
    private javax.swing.JLabel lblxNivel;
    private javax.swing.JLabel lblxclan;
    private javax.swing.JList listDescripcionObjeto;
    private javax.swing.JLabel lvlEvaMagJoya;
    private javax.swing.JLabel lvlMejEvaFisGrebas;
    private javax.swing.JLabel lvlMejRoboVArma;
    private javax.swing.JLabel lvlRoboVArma;
    private javax.swing.JPanel panelAtaqueMobs;
    private javax.swing.JPanel panelAtribBasicoClase;
    private javax.swing.JPanel panelAtribSpawner;
    private javax.swing.JPanel panelAtributosMobs;
    private javax.swing.JPanel panelBotas;
    private javax.swing.JPanel panelCasco;
    private javax.swing.JPanel panelComportamientoMob;
    private javax.swing.JPanel panelConfChats;
    private javax.swing.JTabbedPane panelConfig;
    private javax.swing.JPanel panelConfigArmaduras;
    private javax.swing.JPanel panelConfigArmor;
    private javax.swing.JPanel panelConfigChats;
    private javax.swing.JPanel panelConfigClanes;
    private javax.swing.JPanel panelConfigClases;
    private javax.swing.JPanel panelConfigDanioGeneral;
    private javax.swing.JPanel panelConfigDayCycle;
    private javax.swing.JPanel panelConfigEnableChats;
    private javax.swing.JPanel panelConfigGeneral;
    private javax.swing.JPanel panelConfigGeneralGen;
    private javax.swing.JPanel panelConfigGlobal;
    private javax.swing.JPanel panelConfigGrupos;
    private javax.swing.JPanel panelConfigGuild;
    private javax.swing.JPanel panelConfigGuilds;
    private javax.swing.JPanel panelConfigInicio;
    private javax.swing.JPanel panelConfigJewels;
    private javax.swing.JPanel panelConfigJoyas;
    private javax.swing.JPanel panelConfigLevel;
    private javax.swing.JPanel panelConfigLocal;
    private javax.swing.JPanel panelConfigMarketChat;
    private javax.swing.JPanel panelConfigMobs;
    private javax.swing.JPanel panelConfigNews;
    private javax.swing.JPanel panelConfigObjetos;
    private javax.swing.JPanel panelConfigParties;
    private javax.swing.JPanel panelConfigParty;
    private javax.swing.JPanel panelConfigPlayers;
    private javax.swing.JPanel panelConfigSpawners;
    private javax.swing.JPanel panelConfigWarning;
    private javax.swing.JPanel panelConfigWeapon;
    private javax.swing.JPanel panelConfiglPrivate;
    private javax.swing.JPanel panelDropsDefault;
    private javax.swing.JPanel panelDropsMobs;
    private javax.swing.JPanel panelDropsObjetosMobs;
    private javax.swing.JPanel panelEditNivelesClan;
    private javax.swing.JPanel panelEditarSet;
    private javax.swing.JPanel panelEditorArma;
    private javax.swing.JPanel panelEditorClase;
    private javax.swing.JPanel panelEditorJoya;
    private javax.swing.JPanel panelEditorMobs;
    private javax.swing.JPanel panelEditorObjetos;
    private javax.swing.JPanel panelEditorSpawner;
    private javax.swing.JPanel panelEnableModulosGeneral;
    private javax.swing.JPanel panelFormulaClan;
    private javax.swing.JPanel panelGraficosNivel;
    private javax.swing.JPanel panelHabConst;
    private javax.swing.JPanel panelHabDest;
    private javax.swing.JPanel panelHabFuerza;
    private javax.swing.JPanel panelHabInt;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelLocSpawner;
    private javax.swing.JPanel panelMejoraArma;
    private javax.swing.JPanel panelMejoraArmadura;
    private javax.swing.JPanel panelNivelExpNivel;
    private javax.swing.JPanel panelNumJugClan;
    private javax.swing.JPanel panelNumJugGrupos;
    private javax.swing.JPanel panelOpcLimNivelClan;
    private javax.swing.JPanel panelOpcRepGRupos;
    private javax.swing.JPanel panelOpcRepProp;
    private javax.swing.JPanel panelPantalones;
    private javax.swing.JPanel panelPechera;
    private javax.swing.JPanel panelPuntoHabilidad;
    private javax.swing.JPanel panelPvpGrupos;
    private javax.swing.JPanel panelSelectorArma;
    private javax.swing.JPanel panelSelectorArmadura;
    private javax.swing.JPanel panelSelectorJoyas;
    private javax.swing.JPanel panelSisContNivelClan;
    private javax.swing.JPanel panelSistRepGrupos;
    private javax.swing.JPanel panelSubirNivel;
    private javax.swing.JRadioButton rdBtnAmbosGrupo;
    private javax.swing.JRadioButton rdBtnContriAmbos;
    private javax.swing.JRadioButton rdBtnContriSoloContri;
    private javax.swing.JRadioButton rdBtnContriSoloDon;
    private javax.swing.JRadioButton rdBtnFijosGrupo;
    private javax.swing.JRadioButton rdBtnIlimitadosGrupo;
    private javax.swing.JRadioButton rdBtnLimitNivClan;
    private javax.swing.JRadioButton rdBtnNoPermitidoPvpGrupo;
    private javax.swing.JRadioButton rdBtnNumFijosClan;
    private javax.swing.JRadioButton rdBtnNumIlimitadosClan;
    private javax.swing.JRadioButton rdBtnOpcDinFijo;
    private javax.swing.JRadioButton rdBtnOpcDinForm;
    private javax.swing.JRadioButton rdBtnOpcionProporcionalGrupo;
    private javax.swing.JRadioButton rdBtnOpcionigualitarioGrupo;
    private javax.swing.JRadioButton rdBtnPermitidoPvpGrupo;
    private javax.swing.JRadioButton rdBtnProKillsGrupo;
    private javax.swing.JRadioButton rdBtnProNivelGrupo;
    private javax.swing.JRadioButton rdBtnSinRepartoGrupo;
    private javax.swing.JRadioButton rdBtnSoloDineroGrupo;
    private javax.swing.JRadioButton rdBtnSoloExpGrupo;
    private javax.swing.JSpinner spinerProbExitoArma;
    private javax.swing.JSpinner spinnerAtaDistMob;
    private javax.swing.JSpinner spinnerAtaFisAP;
    private javax.swing.JSpinner spinnerAtaFisArma;
    private javax.swing.JSpinner spinnerAtaFisBase;
    private javax.swing.JSpinner spinnerAtaFisJoya;
    private javax.swing.JSpinner spinnerAtaFisMob;
    private javax.swing.JSpinner spinnerAtaFisNivel;
    private javax.swing.JSpinner spinnerAtaMagAP;
    private javax.swing.JSpinner spinnerAtaMagArma;
    private javax.swing.JSpinner spinnerAtaMagBase;
    private javax.swing.JSpinner spinnerAtaMagJoya;
    private javax.swing.JSpinner spinnerAtaMagNivel;
    private javax.swing.JSpinner spinnerCantidadDropMob;
    private javax.swing.JSpinner spinnerContriPorcentajeMuertes;
    private javax.swing.JSpinner spinnerCritArma;
    private javax.swing.JSpinner spinnerCritBase;
    private javax.swing.JSpinner spinnerCritNivel;
    private javax.swing.JSpinner spinnerCrtitAP;
    private javax.swing.JSpinner spinnerDefFisBase;
    private javax.swing.JSpinner spinnerDefFisBotas;
    private javax.swing.JSpinner spinnerDefFisCasco;
    private javax.swing.JSpinner spinnerDefFisGrebas;
    private javax.swing.JSpinner spinnerDefFisJoya;
    private javax.swing.JSpinner spinnerDefFisNivel;
    private javax.swing.JSpinner spinnerDefFisPechera;
    private javax.swing.JSpinner spinnerDefMagBase;
    private javax.swing.JSpinner spinnerDefMagJoya;
    private javax.swing.JSpinner spinnerDefMagNivel;
    private javax.swing.JSpinner spinnerDineroDropMob;
    private javax.swing.JSpinner spinnerDineroEditClan;
    private javax.swing.JSpinner spinnerDineroExtraArma;
    private javax.swing.JSpinner spinnerDineroExtraBotas;
    private javax.swing.JSpinner spinnerDineroExtraCasco;
    private javax.swing.JSpinner spinnerDineroExtraGrebas;
    private javax.swing.JSpinner spinnerDineroExtraJoya;
    private javax.swing.JSpinner spinnerDineroExtraPechera;
    private javax.swing.JSpinner spinnerDistanceLocalChat;
    private javax.swing.JSpinner spinnerEvaBase;
    private javax.swing.JSpinner spinnerEvaFisBotas;
    private javax.swing.JSpinner spinnerEvaFisCasco;
    private javax.swing.JSpinner spinnerEvaFisGrebas;
    private javax.swing.JSpinner spinnerEvaFisJoya;
    private javax.swing.JSpinner spinnerEvaFisNivel;
    private javax.swing.JSpinner spinnerEvaFisPechera;
    private javax.swing.JSpinner spinnerEvaMagBase;
    private javax.swing.JSpinner spinnerEvaMagJoya;
    private javax.swing.JSpinner spinnerEvaMagNivel;
    private javax.swing.JSpinner spinnerExpDropMob;
    private javax.swing.JSpinner spinnerExpExtraArma;
    private javax.swing.JSpinner spinnerExpExtraBotas;
    private javax.swing.JSpinner spinnerExpExtraCasco;
    private javax.swing.JSpinner spinnerExpExtraGrebas;
    private javax.swing.JSpinner spinnerExpExtraJoya;
    private javax.swing.JSpinner spinnerExpExtraPechera;
    private javax.swing.JSpinner spinnerFormJugIniClan;
    private javax.swing.JSpinner spinnerFormJugXNivelClan;
    private javax.swing.JSpinner spinnerFuerzaDistMob;
    private javax.swing.JSpinner spinnerHRFisArma;
    private javax.swing.JSpinner spinnerHRFisBase;
    private javax.swing.JSpinner spinnerHRFisJoya;
    private javax.swing.JSpinner spinnerHRFisNivel;
    private javax.swing.JSpinner spinnerHRMagArma;
    private javax.swing.JSpinner spinnerHRMagBase;
    private javax.swing.JSpinner spinnerHRMagJoya;
    private javax.swing.JSpinner spinnerHRMagNivel;
    private javax.swing.JSpinner spinnerManaAP;
    private javax.swing.JSpinner spinnerManaAP2;
    private javax.swing.JSpinner spinnerManaJoya;
    private javax.swing.JSpinner spinnerManaNivel;
    private javax.swing.JSpinner spinnerMaxManaBase;
    private javax.swing.JSpinner spinnerMaxVidaBase;
    private javax.swing.JSpinner spinnerMaxVidaMob;
    private javax.swing.JSpinner spinnerMejAtaFisArma;
    private javax.swing.JSpinner spinnerMejAtaMagArma;
    private javax.swing.JSpinner spinnerMejCritArma;
    private javax.swing.JSpinner spinnerMejDefFisBotas;
    private javax.swing.JSpinner spinnerMejDefFisCasco;
    private javax.swing.JSpinner spinnerMejDefFisGrebas;
    private javax.swing.JSpinner spinnerMejDefFisPechera;
    private javax.swing.JSpinner spinnerMejEvaFisBotas;
    private javax.swing.JSpinner spinnerMejEvaFisCasco;
    private javax.swing.JSpinner spinnerMejEvaFisGrebas;
    private javax.swing.JSpinner spinnerMejEvaFisPechera;
    private javax.swing.JSpinner spinnerMejHRFisArma;
    private javax.swing.JSpinner spinnerMejHRMagArma;
    private javax.swing.JSpinner spinnerMejRoboMArma;
    private javax.swing.JSpinner spinnerMejRoboVArma;
    private javax.swing.JSpinner spinnerMortalAP;
    private javax.swing.JSpinner spinnerMortalBase;
    private javax.swing.JSpinner spinnerMortalNivel;
    private javax.swing.JSpinner spinnerNivel;
    private javax.swing.JSpinner spinnerNivelIniArma;
    private javax.swing.JSpinner spinnerNivelMaxClan;
    private javax.swing.JSpinner spinnerNivelMinArma;
    private javax.swing.JSpinner spinnerNivelMinJugClan;
    private javax.swing.JSpinner spinnerNivelMob;
    private javax.swing.JSpinner spinnerNumFijosClan;
    private javax.swing.JSpinner spinnerNumJugEditClan;
    private javax.swing.JSpinner spinnerNumMaxMobSpawner;
    private javax.swing.JSpinner spinnerNumeroFijoJugGrupo;
    private javax.swing.JSpinner spinnerPHxNivel;
    private javax.swing.JSpinner spinnerPrecioCArma;
    private javax.swing.JSpinner spinnerPrecioCBotas;
    private javax.swing.JSpinner spinnerPrecioCCasco;
    private javax.swing.JSpinner spinnerPrecioCGrebas;
    private javax.swing.JSpinner spinnerPrecioCJoya;
    private javax.swing.JSpinner spinnerPrecioCPechera;
    private javax.swing.JSpinner spinnerPrecioVArma;
    private javax.swing.JSpinner spinnerPrecioVBotas;
    private javax.swing.JSpinner spinnerPrecioVCasco;
    private javax.swing.JSpinner spinnerPrecioVGrebas;
    private javax.swing.JSpinner spinnerPrecioVJoya;
    private javax.swing.JSpinner spinnerPrecioVPechera;
    private javax.swing.JSpinner spinnerProSEArma;
    private javax.swing.JSpinner spinnerProbDetArma;
    private javax.swing.JSpinner spinnerProbDetArmadura;
    private javax.swing.JSpinner spinnerProbDetJoya;
    private javax.swing.JSpinner spinnerProbDropMob;
    private javax.swing.JSpinner spinnerProbExitoArmadura;
    private javax.swing.JSpinner spinnerProbExitoJoya;
    private javax.swing.JSpinner spinnerProbRotArma;
    private javax.swing.JSpinner spinnerProbRotArmadura;
    private javax.swing.JSpinner spinnerProbRotJoya;
    private javax.swing.JSpinner spinnerProbSEArmadura;
    private javax.swing.JSpinner spinnerProbSEJoya;
    private javax.swing.JSpinner spinnerRadioSpawner;
    private javax.swing.JSpinner spinnerRangoSeguiMob;
    private javax.swing.JSpinner spinnerRefrescoSpawner;
    private javax.swing.JSpinner spinnerRetrocesoMob;
    private javax.swing.JSpinner spinnerRoboMArma;
    private javax.swing.JSpinner spinnerRoboMJoya;
    private javax.swing.JSpinner spinnerRoboVArma;
    private javax.swing.JSpinner spinnerRoboVJoya;
    private javax.swing.JSpinner spinnerVelocidadAtaqueMob;
    private javax.swing.JSpinner spinnerVelocidadMob;
    private javax.swing.JSpinner spinnerVidaAP;
    private javax.swing.JSpinner spinnerVidaAP2;
    private javax.swing.JSpinner spinnerVidaJoya;
    private javax.swing.JSpinner spinnerVidaNivel;
    private javax.swing.JSpinner spinnerXInicio;
    private javax.swing.JSpinner spinnerXSpawnLoc;
    private javax.swing.JSpinner spinnerYInicio;
    private javax.swing.JSpinner spinnerYSpawnLoc;
    private javax.swing.JSpinner spinnerZInicio;
    private javax.swing.JSpinner spinnerZSpawnLoc;
    private javax.swing.JSpinner spinnerformulax2clan;
    private javax.swing.JSpinner spinnerformulaxclan;
    private javax.swing.JTable tablaClanes;
    private javax.swing.JTable tablaDropsMob;
    private javax.swing.JTable tablaExpNivel;
    private javax.swing.JTextField textPrefixGlobalChat;
    private javax.swing.JTextField textPrefixGuildChat;
    private javax.swing.JTextField textPrefixLocalChat;
    private javax.swing.JTextField textPrefixMarketChat;
    private javax.swing.JTextField textPrefixNewsChat;
    private javax.swing.JTextField textPrefixPartyChat;
    private javax.swing.JTextField textPrefixPrivateChat;
    private javax.swing.JTextField textPrefixWarningChat;
    private javax.swing.JTextField txtDescripObjeto;
    private javax.swing.JTextField txtDescripcionArma;
    private javax.swing.JTextField txtDescripcionArmadura;
    private javax.swing.JTextField txtMundoInicio;
    private javax.swing.JTextField txtMundoSpawnLoc;
    private javax.swing.JTextField txtNombreArma;
    private javax.swing.JTextField txtNombreClase;
    private javax.swing.JTextField txtNombreJoya;
    private javax.swing.JTextField txtNombreMob;
    private javax.swing.JTextField txtNombreObjeto;
    private javax.swing.JTextField txtNombreObjetoMejArma;
    private javax.swing.JTextField txtNombreObjetoMejArmadura;
    private javax.swing.JTextField txtNombreSet;
    private javax.swing.JSpinner txtX2Nivel;
    private javax.swing.JSpinner txtX3Nivel;
    private javax.swing.JSpinner txtXNivel;
    // End of variables declaration//GEN-END:variables

    private XYDataset createDataset(int max_lvl) {
        // create the dataset...
        XYSeries series = new XYSeries("nivel");
        double res = 0.0;
        for (int i = 0; i <= max_lvl; i++) {
            res = (Double) txtX3Nivel.getValue() * Math.pow(i, 3)
                    + (Double) txtX2Nivel.getValue() * Math.pow(i, 2)
                    + (Double) txtXNivel.getValue() * i;
            series.add(i, res);
        }
        XYSeriesCollection data = new XYSeriesCollection(series);
        return data;

    }

    public JCheckBox getCheckClanes() {
        return checkClanes;
    }

    public void setCheckClanes(JCheckBox checkClanes) {
        this.checkClanes = checkClanes;
    }

    public JCheckBox getCheckColocarBloques() {
        return checkColocarBloques;
    }

    public void setCheckColocarBloques(JCheckBox checkColocarBloques) {
        this.checkColocarBloques = checkColocarBloques;
    }

    public JCheckBox getCheckCombinable() {
        return checkCombinable;
    }

    public void setCheckCombinable(JCheckBox checkCombinable) {
        this.checkCombinable = checkCombinable;
    }

    public JCheckBox getCheckComerciable() {
        return checkComerciable;
    }

    public void setCheckComerciable(JCheckBox checkComerciable) {
        this.checkComerciable = checkComerciable;
    }

    public JCheckBox getCheckComerciableArma() {
        return checkComerciableArma;
    }

    public void setCheckComerciableArma(JCheckBox checkComerciableArma) {
        this.checkComerciableArma = checkComerciableArma;
    }

    public JCheckBox getCheckComerciableArmadura() {
        return checkComerciableArmadura;
    }

    public void setCheckComerciableArmadura(JCheckBox checkComerciableArmadura) {
        this.checkComerciableArmadura = checkComerciableArmadura;
    }

    public JCheckBox getCheckDanioAhogo() {
        return checkDanioAhogo;
    }

    public void setCheckDanioAhogo(JCheckBox checkDanioAhogo) {
        this.checkDanioAhogo = checkDanioAhogo;
    }

    public JCheckBox getCheckDanioCaida() {
        return checkDanioCaida;
    }

    public void setCheckDanioCaida(JCheckBox checkDanioCaida) {
        this.checkDanioCaida = checkDanioCaida;
    }

    public JCheckBox getCheckDanioPvp() {
        return checkDanioPvp;
    }

    public void setCheckDanioPvp(JCheckBox checkDanioPvp) {
        this.checkDanioPvp = checkDanioPvp;
    }

    public JCheckBox getCheckDestruirBloques() {
        return checkDestruirBloques;
    }

    public void setCheckDestruirBloques(JCheckBox checkDestruirBloques) {
        this.checkDestruirBloques = checkDestruirBloques;
    }

    public JCheckBox getCheckDestruirBloques1() {
        return checkDayCycle;
    }

    public void setCheckDestruirBloques1(JCheckBox checkDestruirBloques1) {
        this.checkDayCycle = checkDestruirBloques1;
    }

    public JCheckBox getCheckEnableClase() {
        return checkEnableClase;
    }

    public void setCheckEnableClase(JCheckBox checkEnableClase) {
        this.checkEnableClase = checkEnableClase;
    }

    public JCheckBox getCheckEnableSpawner() {
        return checkEnableSpawner;
    }

    public void setCheckEnableSpawner(JCheckBox checkEnableSpawner) {
        this.checkEnableSpawner = checkEnableSpawner;
    }

    public JCheckBox getCheckGrupos() {
        return checkGrupos;
    }

    public void setCheckGrupos(JCheckBox checkGrupos) {
        this.checkGrupos = checkGrupos;
    }

    public JCheckBox getCheckMejorableArma() {
        return checkMejorableArma;
    }

    public void setCheckMejorableArma(JCheckBox checkMejorableArma) {
        this.checkMejorableArma = checkMejorableArma;
    }

    public JCheckBox getCheckMejorableArmadura() {
        return checkMejorableArmadura;
    }

    public void setCheckMejorableArmadura(JCheckBox checkMejorableArmadura) {
        this.checkMejorableArmadura = checkMejorableArmadura;
    }

    public JCheckBox getCheckSistHambre() {
        return checkSistHambre;
    }

    public void setCheckSistHambre(JCheckBox checkSistHambre) {
        this.checkSistHambre = checkSistHambre;
    }

    public JComboBox getComboCalidad() {
        return comboCalidad;
    }

    public void setComboCalidad(JComboBox comboCalidad) {
        this.comboCalidad = comboCalidad;
    }

    public JComboBox getComboCalidadArma() {
        return comboCalidadArma;
    }

    public void setComboCalidadArma(JComboBox comboCalidadArma) {
        this.comboCalidadArma = comboCalidadArma;
    }

    public JComboBox getComboCalidadJoya() {
        return comboCalidadJoya;
    }

    public void setComboCalidadJoya(JComboBox comboCalidadJoya) {
        this.comboCalidadJoya = comboCalidadJoya;
    }

    public JComboBox getComboColorPrefijoComercio() {
        return comboPrefixColorMarketChat;
    }

    public void setComboColorPrefijoComercio(JComboBox comboColorPrefijoComercio) {
        this.comboPrefixColorMarketChat = comboColorPrefijoComercio;
    }

    public JComboBox getComboComportamiento() {
        return comboComportamiento;
    }

    public void setComboComportamiento(JComboBox comboComportamiento) {
        this.comboComportamiento = comboComportamiento;
    }

    public JComboBox getComboListSets() {
        return comboListSets;
    }

    public void setComboListSets(JComboBox comboListSets) {
        this.comboListSets = comboListSets;
    }

    public JComboBox getComboListaArmas() {
        return comboListaArmas;
    }

    public void setComboListaArmas(JComboBox comboListaArmas) {
        this.comboListaArmas = comboListaArmas;
    }

    public JComboBox getComboListaObjetos() {
        return comboListaObjetos;
    }

    public void setComboListaObjetos(JComboBox comboListaObjetos) {
        this.comboListaObjetos = comboListaObjetos;
    }

    public JComboBox getComboMaterial() {
        return comboMaterial;
    }

    public void setComboMaterial(JComboBox comboMaterial) {
        this.comboMaterial = comboMaterial;
    }

    public JComboBox getComboMejoradorArma() {
        return comboMejoradorArma;
    }

    public void setComboMejoradorArma(JComboBox comboMejoradorArma) {
        this.comboMejoradorArma = comboMejoradorArma;
    }

    public JComboBox getComboMejoradorArmadura() {
        return comboMejoradorArmadura;
    }

    public void setComboMejoradorArmadura(JComboBox comboMejoradorArmadura) {
        this.comboMejoradorArmadura = comboMejoradorArmadura;
    }

    public JComboBox getComboMobSpawner() {
        return comboMobSpawner;
    }

    public void setComboMobSpawner(JComboBox comboMobSpawner) {
        this.comboMobSpawner = comboMobSpawner;
    }

    public JComboBox getComboObjetoDropMob() {
        return comboObjetoDropMob;
    }

    public void setComboObjetoDropMob(JComboBox comboObjetoDropMob) {
        this.comboObjetoDropMob = comboObjetoDropMob;
    }

    public JComboBox getComboObjetoJoya() {
        return comboObjetoJoya;
    }

    public void setComboObjetoJoya(JComboBox comboObjetoJoya) {
        this.comboObjetoJoya = comboObjetoJoya;
    }

    public JComboBox getComboPrefijoComercio() {
        return comboShortcutMarketChat;
    }

    public void setComboPrefijoComercio(JComboBox comboPrefijoComercio) {
        this.comboShortcutMarketChat = comboPrefijoComercio;
    }

    public JComboBox getComboSelectorClases() {
        return comboSelectorClases;
    }

    public void setComboSelectorClases(JComboBox comboSelectorClases) {
        this.comboSelectorClases = comboSelectorClases;
    }

    public JComboBox getComboSelectorGenerador() {
        return comboSelectorGenerador;
    }

    public void setComboSelectorGenerador(JComboBox comboSelectorGenerador) {
        this.comboSelectorGenerador = comboSelectorGenerador;
    }

    public JComboBox getComboSelectorJoyas() {
        return comboSelectorJoyas;
    }

    public void setComboSelectorJoyas(JComboBox comboSelectorJoyas) {
        this.comboSelectorJoyas = comboSelectorJoyas;
    }

    public JComboBox getComboSelectorMobs() {
        return comboSelectorMobs;
    }

    public void setComboSelectorMobs(JComboBox comboSelectorMobs) {
        this.comboSelectorMobs = comboSelectorMobs;
    }

    public JTextField getComboSelectorSpawner() {
        return comboSelectorSpawner;
    }

    public void setComboSelectorSpawner(JTextField comboSelectorSpawner) {
        this.comboSelectorSpawner = comboSelectorSpawner;
    }

    public JComboBox getComboSelectorTipoMob() {
        return comboSelectorTipoMob;
    }

    public void setComboSelectorTipoMob(JComboBox comboSelectorTipoMob) {
        this.comboSelectorTipoMob = comboSelectorTipoMob;
    }

    public JComboBox getComboTipoArma() {
        return comboTipoArma;
    }

    public void setComboTipoArma(JComboBox comboTipoArma) {
        this.comboTipoArma = comboTipoArma;
    }

    public JComboBox getComboTipoAtaqueMob() {
        return comboTipoAtaqueMob;
    }

    public void setComboTipoAtaqueMob(JComboBox comboTipoAtaqueMob) {
        this.comboTipoAtaqueMob = comboTipoAtaqueMob;
    }

    public JComboBox getComboTipoDropMob() {
        return comboTipoDropMob;
    }

    public void setComboTipoDropMob(JComboBox comboTipoDropMob) {
        this.comboTipoDropMob = comboTipoDropMob;
    }

    public JComboBox getComboTipoObjeto() {
        return comboTipoObjeto;
    }

    public void setComboTipoObjeto(JComboBox comboTipoObjeto) {
        this.comboTipoObjeto = comboTipoObjeto;
    }

    public JCheckBox getjCheckBox1() {
        return checkEnableGlobalChat;
    }

    public void setjCheckBox1(JCheckBox jCheckBox1) {
        this.checkEnableGlobalChat = jCheckBox1;
    }

    public JCheckBox getjCheckBox2() {
        return checkEnableMarketChat;
    }

    public void setjCheckBox2(JCheckBox jCheckBox2) {
        this.checkEnableMarketChat = jCheckBox2;
    }

    public JCheckBox getjCheckBox3() {
        return checkGuildChat;
    }

    public void setjCheckBox3(JCheckBox jCheckBox3) {
        this.checkGuildChat = jCheckBox3;
    }

    public JCheckBox getjCheckBox4() {
        return checkPartyChat;
    }

    public void setjCheckBox4(JCheckBox jCheckBox4) {
        this.checkPartyChat = jCheckBox4;
    }

    public JCheckBox getjCheckBox5() {
        return checkPrivateChat;
    }

    public void setjCheckBox5(JCheckBox jCheckBox5) {
        this.checkPrivateChat = jCheckBox5;
    }

    public JList getjListDescArma() {
        return jListDescArma;
    }

    public void setjListDescArma(JList jListDescArma) {
        this.jListDescArma = jListDescArma;
    }

    public JList getjListDescArmadura() {
        return jListDescArmadura;
    }

    public void setjListDescArmadura(JList jListDescArmadura) {
        this.jListDescArmadura = jListDescArmadura;
    }

    public JSpinner getjSpinner1() {
        return spinnerDistanceLocalChat;
    }

    public void setjSpinner1(JSpinner jSpinner1) {
        this.spinnerDistanceLocalChat = jSpinner1;
    }

    public JTextField getjTextField17() {
        return textPrefixPartyChat;
    }

    public void setjTextField17(JTextField jTextField17) {
        this.textPrefixPartyChat = jTextField17;
    }

    public JTextField getjTextField21() {
        return textPrefixGuildChat;
    }

    public void setjTextField21(JTextField jTextField21) {
        this.textPrefixGuildChat = jTextField21;
    }

    public JTextField getjTextField25() {
        return textPrefixPrivateChat;
    }

    public void setjTextField25(JTextField jTextField25) {
        this.textPrefixPrivateChat = jTextField25;
    }

    public JTextField getjTextField29() {
        return textPrefixNewsChat;
    }

    public void setjTextField29(JTextField jTextField29) {
        this.textPrefixNewsChat = jTextField29;
    }

    public JTextField getjTextField3() {
        return textPrefixMarketChat;
    }

    public void setjTextField3(JTextField jTextField3) {
        this.textPrefixMarketChat = jTextField3;
    }

    public JTextField getjTextField37() {
        return textPrefixWarningChat;
    }

    public void setjTextField37(JTextField jTextField37) {
        this.textPrefixWarningChat = jTextField37;
    }

    public JTextField getjTextField45() {
        return textPrefixLocalChat;
    }

    public void setjTextField45(JTextField jTextField45) {
        this.textPrefixLocalChat = jTextField45;
    }

    public JTextField getjTextField9() {
        return textPrefixGlobalChat;
    }

    public void setjTextField9(JTextField jTextField9) {
        this.textPrefixGlobalChat = jTextField9;
    }

    public JList getListDescripcionObjeto() {
        return listDescripcionObjeto;
    }

    public void setListDescripcionObjeto(JList listDescripcionObjeto) {
        this.listDescripcionObjeto = listDescripcionObjeto;
    }

    public JRadioButton getRdBtnAmbosGrupo() {
        return rdBtnAmbosGrupo;
    }

    public void setRdBtnAmbosGrupo(JRadioButton rdBtnAmbosGrupo) {
        this.rdBtnAmbosGrupo = rdBtnAmbosGrupo;
    }

    public JRadioButton getRdBtnContriAmbos() {
        return rdBtnContriAmbos;
    }

    public void setRdBtnContriAmbos(JRadioButton rdBtnContriAmbos) {
        this.rdBtnContriAmbos = rdBtnContriAmbos;
    }

    public JRadioButton getRdBtnContriSoloContri() {
        return rdBtnContriSoloContri;
    }

    public void setRdBtnContriSoloContri(JRadioButton rdBtnContriSoloContri) {
        this.rdBtnContriSoloContri = rdBtnContriSoloContri;
    }

    public JRadioButton getRdBtnContriSoloDon() {
        return rdBtnContriSoloDon;
    }

    public void setRdBtnContriSoloDon(JRadioButton rdBtnContriSoloDon) {
        this.rdBtnContriSoloDon = rdBtnContriSoloDon;
    }

    public JRadioButton getRdBtnFijosGrupo() {
        return rdBtnFijosGrupo;
    }

    public void setRdBtnFijosGrupo(JRadioButton rdBtnFijosGrupo) {
        this.rdBtnFijosGrupo = rdBtnFijosGrupo;
    }

    public JRadioButton getRdBtnIlimitadosGrupo() {
        return rdBtnIlimitadosGrupo;
    }

    public void setRdBtnIlimitadosGrupo(JRadioButton rdBtnIlimitadosGrupo) {
        this.rdBtnIlimitadosGrupo = rdBtnIlimitadosGrupo;
    }

    public JRadioButton getRdBtnLimitNivClan() {
        return rdBtnLimitNivClan;
    }

    public void setRdBtnLimitNivClan(JRadioButton rdBtnLimitNivClan) {
        this.rdBtnLimitNivClan = rdBtnLimitNivClan;
    }

    public JRadioButton getRdBtnNoPermitidoPvpGrupo() {
        return rdBtnNoPermitidoPvpGrupo;
    }

    public void setRdBtnNoPermitidoPvpGrupo(JRadioButton rdBtnNoPermitidoPvpGrupo) {
        this.rdBtnNoPermitidoPvpGrupo = rdBtnNoPermitidoPvpGrupo;
    }

    public JRadioButton getRdBtnNumFijosClan() {
        return rdBtnNumFijosClan;
    }

    public void setRdBtnNumFijosClan(JRadioButton rdBtnNumFijosClan) {
        this.rdBtnNumFijosClan = rdBtnNumFijosClan;
    }

    public JRadioButton getRdBtnNumIlimitadosClan() {
        return rdBtnNumIlimitadosClan;
    }

    public void setRdBtnNumIlimitadosClan(JRadioButton rdBtnNumIlimitadosClan) {
        this.rdBtnNumIlimitadosClan = rdBtnNumIlimitadosClan;
    }

    public JRadioButton getRdBtnOpcDinFijo() {
        return rdBtnOpcDinFijo;
    }

    public void setRdBtnOpcDinFijo(JRadioButton rdBtnOpcDinFijo) {
        this.rdBtnOpcDinFijo = rdBtnOpcDinFijo;
    }

    public JRadioButton getRdBtnOpcDinForm() {
        return rdBtnOpcDinForm;
    }

    public void setRdBtnOpcDinForm(JRadioButton rdBtnOpcDinForm) {
        this.rdBtnOpcDinForm = rdBtnOpcDinForm;
    }

    public JRadioButton getRdBtnOpcionProporcionalGrupo() {
        return rdBtnOpcionProporcionalGrupo;
    }

    public void setRdBtnOpcionProporcionalGrupo(JRadioButton rdBtnOpcionProporcionalGrupo) {
        this.rdBtnOpcionProporcionalGrupo = rdBtnOpcionProporcionalGrupo;
    }

    public JRadioButton getRdBtnOpcionigualitarioGrupo() {
        return rdBtnOpcionigualitarioGrupo;
    }

    public void setRdBtnOpcionigualitarioGrupo(JRadioButton rdBtnOpcionigualitarioGrupo) {
        this.rdBtnOpcionigualitarioGrupo = rdBtnOpcionigualitarioGrupo;
    }

    public JRadioButton getRdBtnPermitidoPvpGrupo() {
        return rdBtnPermitidoPvpGrupo;
    }

    public void setRdBtnPermitidoPvpGrupo(JRadioButton rdBtnPermitidoPvpGrupo) {
        this.rdBtnPermitidoPvpGrupo = rdBtnPermitidoPvpGrupo;
    }

    public JRadioButton getRdBtnProKillsGrupo() {
        return rdBtnProKillsGrupo;
    }

    public void setRdBtnProKillsGrupo(JRadioButton rdBtnProKillsGrupo) {
        this.rdBtnProKillsGrupo = rdBtnProKillsGrupo;
    }

    public JRadioButton getRdBtnProNivelGrupo() {
        return rdBtnProNivelGrupo;
    }

    public void setRdBtnProNivelGrupo(JRadioButton rdBtnProNivelGrupo) {
        this.rdBtnProNivelGrupo = rdBtnProNivelGrupo;
    }

    public JRadioButton getRdBtnSinRepartoGrupo() {
        return rdBtnSinRepartoGrupo;
    }

    public void setRdBtnSinRepartoGrupo(JRadioButton rdBtnSinRepartoGrupo) {
        this.rdBtnSinRepartoGrupo = rdBtnSinRepartoGrupo;
    }

    public JRadioButton getRdBtnSoloDineroGrupo() {
        return rdBtnSoloDineroGrupo;
    }

    public void setRdBtnSoloDineroGrupo(JRadioButton rdBtnSoloDineroGrupo) {
        this.rdBtnSoloDineroGrupo = rdBtnSoloDineroGrupo;
    }

    public JRadioButton getRdBtnSoloExpGrupo() {
        return rdBtnSoloExpGrupo;
    }

    public void setRdBtnSoloExpGrupo(JRadioButton rdBtnSoloExpGrupo) {
        this.rdBtnSoloExpGrupo = rdBtnSoloExpGrupo;
    }

    public JSpinner getSpinerProbExitoArma() {
        return spinerProbExitoArma;
    }

    public void setSpinerProbExitoArma(JSpinner spinerProbExitoArma) {
        this.spinerProbExitoArma = spinerProbExitoArma;
    }

    public JSpinner getSpinnerAtaDistMob() {
        return spinnerAtaDistMob;
    }

    public void setSpinnerAtaDistMob(JSpinner spinnerAtaDistMob) {
        this.spinnerAtaDistMob = spinnerAtaDistMob;
    }

    public JSpinner getSpinnerAtaFisAP() {
        return spinnerAtaFisAP;
    }

    public void setSpinnerAtaFisAP(JSpinner spinnerAtaFisAP) {
        this.spinnerAtaFisAP = spinnerAtaFisAP;
    }

    public JSpinner getSpinnerAtaFisArma() {
        return spinnerAtaFisArma;
    }

    public void setSpinnerAtaFisArma(JSpinner spinnerAtaFisArma) {
        this.spinnerAtaFisArma = spinnerAtaFisArma;
    }

    public JSpinner getSpinnerAtaFisBase() {
        return spinnerAtaFisBase;
    }

    public void setSpinnerAtaFisBase(JSpinner spinnerAtaFisBase) {
        this.spinnerAtaFisBase = spinnerAtaFisBase;
    }

    public JSpinner getSpinnerAtaFisJoya() {
        return spinnerAtaFisJoya;
    }

    public void setSpinnerAtaFisJoya(JSpinner spinnerAtaFisJoya) {
        this.spinnerAtaFisJoya = spinnerAtaFisJoya;
    }

    public JSpinner getSpinnerAtaFisMob() {
        return spinnerAtaFisMob;
    }

    public void setSpinnerAtaFisMob(JSpinner spinnerAtaFisMob) {
        this.spinnerAtaFisMob = spinnerAtaFisMob;
    }

    public JSpinner getSpinnerAtaFisNivel() {
        return spinnerAtaFisNivel;
    }

    public void setSpinnerAtaFisNivel(JSpinner spinnerAtaFisNivel) {
        this.spinnerAtaFisNivel = spinnerAtaFisNivel;
    }

    public JSpinner getSpinnerAtaMagAP() {
        return spinnerAtaMagAP;
    }

    public void setSpinnerAtaMagAP(JSpinner spinnerAtaMagAP) {
        this.spinnerAtaMagAP = spinnerAtaMagAP;
    }

    public JSpinner getSpinnerAtaMagArma() {
        return spinnerAtaMagArma;
    }

    public void setSpinnerAtaMagArma(JSpinner spinnerAtaMagArma) {
        this.spinnerAtaMagArma = spinnerAtaMagArma;
    }

    public JSpinner getSpinnerAtaMagBase() {
        return spinnerAtaMagBase;
    }

    public void setSpinnerAtaMagBase(JSpinner spinnerAtaMagBase) {
        this.spinnerAtaMagBase = spinnerAtaMagBase;
    }

    public JSpinner getSpinnerAtaMagJoya() {
        return spinnerAtaMagJoya;
    }

    public void setSpinnerAtaMagJoya(JSpinner spinnerAtaMagJoya) {
        this.spinnerAtaMagJoya = spinnerAtaMagJoya;
    }

    public JSpinner getSpinnerAtaMagNivel() {
        return spinnerAtaMagNivel;
    }

    public void setSpinnerAtaMagNivel(JSpinner spinnerAtaMagNivel) {
        this.spinnerAtaMagNivel = spinnerAtaMagNivel;
    }

    public JSpinner getSpinnerCantidadDropMob() {
        return spinnerCantidadDropMob;
    }

    public void setSpinnerCantidadDropMob(JSpinner spinnerCantidadDropMob) {
        this.spinnerCantidadDropMob = spinnerCantidadDropMob;
    }

    public JSpinner getSpinnerContriPorcentajeMuertes() {
        return spinnerContriPorcentajeMuertes;
    }

    public void setSpinnerContriPorcentajeMuertes(JSpinner spinnerContriPorcentajeMuertes) {
        this.spinnerContriPorcentajeMuertes = spinnerContriPorcentajeMuertes;
    }

    public JSpinner getSpinnerCritArma() {
        return spinnerCritArma;
    }

    public void setSpinnerCritArma(JSpinner spinnerCritArma) {
        this.spinnerCritArma = spinnerCritArma;
    }

    public JSpinner getSpinnerCritBase() {
        return spinnerCritBase;
    }

    public void setSpinnerCritBase(JSpinner spinnerCritBase) {
        this.spinnerCritBase = spinnerCritBase;
    }

    public JSpinner getSpinnerCritNivel() {
        return spinnerCritNivel;
    }

    public void setSpinnerCritNivel(JSpinner spinnerCritNivel) {
        this.spinnerCritNivel = spinnerCritNivel;
    }

    public JSpinner getSpinnerCrtitAP() {
        return spinnerCrtitAP;
    }

    public void setSpinnerCrtitAP(JSpinner spinnerCrtitAP) {
        this.spinnerCrtitAP = spinnerCrtitAP;
    }

    public JSpinner getSpinnerDefFisBase() {
        return spinnerDefFisBase;
    }

    public void setSpinnerDefFisBase(JSpinner spinnerDefFisBase) {
        this.spinnerDefFisBase = spinnerDefFisBase;
    }

    public JSpinner getSpinnerDefFisBotas() {
        return spinnerDefFisBotas;
    }

    public void setSpinnerDefFisBotas(JSpinner spinnerDefFisBotas) {
        this.spinnerDefFisBotas = spinnerDefFisBotas;
    }

    public JSpinner getSpinnerDefFisCasco() {
        return spinnerDefFisCasco;
    }

    public void setSpinnerDefFisCasco(JSpinner spinnerDefFisCasco) {
        this.spinnerDefFisCasco = spinnerDefFisCasco;
    }

    public JSpinner getSpinnerDefFisGrebas() {
        return spinnerDefFisGrebas;
    }

    public void setSpinnerDefFisGrebas(JSpinner spinnerDefFisGrebas) {
        this.spinnerDefFisGrebas = spinnerDefFisGrebas;
    }

    public JSpinner getSpinnerDefFisJoya() {
        return spinnerDefFisJoya;
    }

    public void setSpinnerDefFisJoya(JSpinner spinnerDefFisJoya) {
        this.spinnerDefFisJoya = spinnerDefFisJoya;
    }

    public JSpinner getSpinnerDefFisNivel() {
        return spinnerDefFisNivel;
    }

    public void setSpinnerDefFisNivel(JSpinner spinnerDefFisNivel) {
        this.spinnerDefFisNivel = spinnerDefFisNivel;
    }

    public JSpinner getSpinnerDefFisPechera() {
        return spinnerDefFisPechera;
    }

    public void setSpinnerDefFisPechera(JSpinner spinnerDefFisPechera) {
        this.spinnerDefFisPechera = spinnerDefFisPechera;
    }

    public JSpinner getSpinnerDefMagBase() {
        return spinnerDefMagBase;
    }

    public void setSpinnerDefMagBase(JSpinner spinnerDefMagBase) {
        this.spinnerDefMagBase = spinnerDefMagBase;
    }

    public JSpinner getSpinnerDefMagJoya() {
        return spinnerDefMagJoya;
    }

    public void setSpinnerDefMagJoya(JSpinner spinnerDefMagJoya) {
        this.spinnerDefMagJoya = spinnerDefMagJoya;
    }

    public JSpinner getSpinnerDefMagNivel() {
        return spinnerDefMagNivel;
    }

    public void setSpinnerDefMagNivel(JSpinner spinnerDefMagNivel) {
        this.spinnerDefMagNivel = spinnerDefMagNivel;
    }

    public JSpinner getSpinnerDineroDropMob() {
        return spinnerDineroDropMob;
    }

    public void setSpinnerDineroDropMob(JSpinner spinnerDineroDropMob) {
        this.spinnerDineroDropMob = spinnerDineroDropMob;
    }

    public JSpinner getSpinnerDineroEditClan() {
        return spinnerDineroEditClan;
    }

    public void setSpinnerDineroEditClan(JSpinner spinnerDineroEditClan) {
        this.spinnerDineroEditClan = spinnerDineroEditClan;
    }

    public JSpinner getSpinnerDineroExtraArma() {
        return spinnerDineroExtraArma;
    }

    public void setSpinnerDineroExtraArma(JSpinner spinnerDineroExtraArma) {
        this.spinnerDineroExtraArma = spinnerDineroExtraArma;
    }

    public JSpinner getSpinnerDineroExtraBotas() {
        return spinnerDineroExtraBotas;
    }

    public void setSpinnerDineroExtraBotas(JSpinner spinnerDineroExtraBotas) {
        this.spinnerDineroExtraBotas = spinnerDineroExtraBotas;
    }

    public JSpinner getSpinnerDineroExtraCasco() {
        return spinnerDineroExtraCasco;
    }

    public void setSpinnerDineroExtraCasco(JSpinner spinnerDineroExtraCasco) {
        this.spinnerDineroExtraCasco = spinnerDineroExtraCasco;
    }

    public JSpinner getSpinnerDineroExtraGrebas() {
        return spinnerDineroExtraGrebas;
    }

    public void setSpinnerDineroExtraGrebas(JSpinner spinnerDineroExtraGrebas) {
        this.spinnerDineroExtraGrebas = spinnerDineroExtraGrebas;
    }

    public JSpinner getSpinnerDineroExtraJoya() {
        return spinnerDineroExtraJoya;
    }

    public void setSpinnerDineroExtraJoya(JSpinner spinnerDineroExtraJoya) {
        this.spinnerDineroExtraJoya = spinnerDineroExtraJoya;
    }

    public JSpinner getSpinnerDineroExtraPechera() {
        return spinnerDineroExtraPechera;
    }

    public void setSpinnerDineroExtraPechera(JSpinner spinnerDineroExtraPechera) {
        this.spinnerDineroExtraPechera = spinnerDineroExtraPechera;
    }

    public JSpinner getSpinnerEvaBase() {
        return spinnerEvaBase;
    }

    public void setSpinnerEvaBase(JSpinner spinnerEvaBase) {
        this.spinnerEvaBase = spinnerEvaBase;
    }

    public JSpinner getSpinnerEvaFisBotas() {
        return spinnerEvaFisBotas;
    }

    public void setSpinnerEvaFisBotas(JSpinner spinnerEvaFisBotas) {
        this.spinnerEvaFisBotas = spinnerEvaFisBotas;
    }

    public JSpinner getSpinnerEvaFisCasco() {
        return spinnerEvaFisCasco;
    }

    public void setSpinnerEvaFisCasco(JSpinner spinnerEvaFisCasco) {
        this.spinnerEvaFisCasco = spinnerEvaFisCasco;
    }

    public JSpinner getSpinnerEvaFisGrebas() {
        return spinnerEvaFisGrebas;
    }

    public void setSpinnerEvaFisGrebas(JSpinner spinnerEvaFisGrebas) {
        this.spinnerEvaFisGrebas = spinnerEvaFisGrebas;
    }

    public JSpinner getSpinnerEvaFisJoya() {
        return spinnerEvaFisJoya;
    }

    public void setSpinnerEvaFisJoya(JSpinner spinnerEvaFisJoya) {
        this.spinnerEvaFisJoya = spinnerEvaFisJoya;
    }

    public JSpinner getSpinnerEvaFisNivel() {
        return spinnerEvaFisNivel;
    }

    public void setSpinnerEvaFisNivel(JSpinner spinnerEvaFisNivel) {
        this.spinnerEvaFisNivel = spinnerEvaFisNivel;
    }

    public JSpinner getSpinnerEvaFisPechera() {
        return spinnerEvaFisPechera;
    }

    public void setSpinnerEvaFisPechera(JSpinner spinnerEvaFisPechera) {
        this.spinnerEvaFisPechera = spinnerEvaFisPechera;
    }

    public JSpinner getSpinnerEvaMagBase() {
        return spinnerEvaMagBase;
    }

    public void setSpinnerEvaMagBase(JSpinner spinnerEvaMagBase) {
        this.spinnerEvaMagBase = spinnerEvaMagBase;
    }

    public JSpinner getSpinnerEvaMagJoya() {
        return spinnerEvaMagJoya;
    }

    public void setSpinnerEvaMagJoya(JSpinner spinnerEvaMagJoya) {
        this.spinnerEvaMagJoya = spinnerEvaMagJoya;
    }

    public JSpinner getSpinnerEvaMagNivel() {
        return spinnerEvaMagNivel;
    }

    public void setSpinnerEvaMagNivel(JSpinner spinnerEvaMagNivel) {
        this.spinnerEvaMagNivel = spinnerEvaMagNivel;
    }

    public JSpinner getSpinnerExpDropMob() {
        return spinnerExpDropMob;
    }

    public void setSpinnerExpDropMob(JSpinner spinnerExpDropMob) {
        this.spinnerExpDropMob = spinnerExpDropMob;
    }

    public JSpinner getSpinnerExpExtraArma() {
        return spinnerExpExtraArma;
    }

    public void setSpinnerExpExtraArma(JSpinner spinnerExpExtraArma) {
        this.spinnerExpExtraArma = spinnerExpExtraArma;
    }

    public JSpinner getSpinnerExpExtraBotas() {
        return spinnerExpExtraBotas;
    }

    public void setSpinnerExpExtraBotas(JSpinner spinnerExpExtraBotas) {
        this.spinnerExpExtraBotas = spinnerExpExtraBotas;
    }

    public JSpinner getSpinnerExpExtraCasco() {
        return spinnerExpExtraCasco;
    }

    public void setSpinnerExpExtraCasco(JSpinner spinnerExpExtraCasco) {
        this.spinnerExpExtraCasco = spinnerExpExtraCasco;
    }

    public JSpinner getSpinnerExpExtraGrebas() {
        return spinnerExpExtraGrebas;
    }

    public void setSpinnerExpExtraGrebas(JSpinner spinnerExpExtraGrebas) {
        this.spinnerExpExtraGrebas = spinnerExpExtraGrebas;
    }

    public JSpinner getSpinnerExpExtraJoya() {
        return spinnerExpExtraJoya;
    }

    public void setSpinnerExpExtraJoya(JSpinner spinnerExpExtraJoya) {
        this.spinnerExpExtraJoya = spinnerExpExtraJoya;
    }

    public JSpinner getSpinnerExpExtraPechera() {
        return spinnerExpExtraPechera;
    }

    public void setSpinnerExpExtraPechera(JSpinner spinnerExpExtraPechera) {
        this.spinnerExpExtraPechera = spinnerExpExtraPechera;
    }

    public JSpinner getSpinnerFormJugIniClan() {
        return spinnerFormJugIniClan;
    }

    public void setSpinnerFormJugIniClan(JSpinner spinnerFormJugIniClan) {
        this.spinnerFormJugIniClan = spinnerFormJugIniClan;
    }

    public JSpinner getSpinnerFormJugXNivelClan() {
        return spinnerFormJugXNivelClan;
    }

    public void setSpinnerFormJugXNivelClan(JSpinner spinnerFormJugXNivelClan) {
        this.spinnerFormJugXNivelClan = spinnerFormJugXNivelClan;
    }

    public JSpinner getSpinnerFuerzaDistMob() {
        return spinnerFuerzaDistMob;
    }

    public void setSpinnerFuerzaDistMob(JSpinner spinnerFuerzaDistMob) {
        this.spinnerFuerzaDistMob = spinnerFuerzaDistMob;
    }

    public JSpinner getSpinnerHRFisArma() {
        return spinnerHRFisArma;
    }

    public void setSpinnerHRFisArma(JSpinner spinnerHRFisArma) {
        this.spinnerHRFisArma = spinnerHRFisArma;
    }

    public JSpinner getSpinnerHRFisBase() {
        return spinnerHRFisBase;
    }

    public void setSpinnerHRFisBase(JSpinner spinnerHRFisBase) {
        this.spinnerHRFisBase = spinnerHRFisBase;
    }

    public JSpinner getSpinnerHRFisJoya() {
        return spinnerHRFisJoya;
    }

    public void setSpinnerHRFisJoya(JSpinner spinnerHRFisJoya) {
        this.spinnerHRFisJoya = spinnerHRFisJoya;
    }

    public JSpinner getSpinnerHRFisNivel() {
        return spinnerHRFisNivel;
    }

    public void setSpinnerHRFisNivel(JSpinner spinnerHRFisNivel) {
        this.spinnerHRFisNivel = spinnerHRFisNivel;
    }

    public JSpinner getSpinnerHRMagArma() {
        return spinnerHRMagArma;
    }

    public void setSpinnerHRMagArma(JSpinner spinnerHRMagArma) {
        this.spinnerHRMagArma = spinnerHRMagArma;
    }

    public JSpinner getSpinnerHRMagBase() {
        return spinnerHRMagBase;
    }

    public void setSpinnerHRMagBase(JSpinner spinnerHRMagBase) {
        this.spinnerHRMagBase = spinnerHRMagBase;
    }

    public JSpinner getSpinnerHRMagJoya() {
        return spinnerHRMagJoya;
    }

    public void setSpinnerHRMagJoya(JSpinner spinnerHRMagJoya) {
        this.spinnerHRMagJoya = spinnerHRMagJoya;
    }

    public JSpinner getSpinnerHRMagNivel() {
        return spinnerHRMagNivel;
    }

    public void setSpinnerHRMagNivel(JSpinner spinnerHRMagNivel) {
        this.spinnerHRMagNivel = spinnerHRMagNivel;
    }

    public JSpinner getSpinnerManaAP() {
        return spinnerManaAP;
    }

    public void setSpinnerManaAP(JSpinner spinnerManaAP) {
        this.spinnerManaAP = spinnerManaAP;
    }

    public JSpinner getSpinnerManaAP2() {
        return spinnerManaAP2;
    }

    public void setSpinnerManaAP2(JSpinner spinnerManaAP2) {
        this.spinnerManaAP2 = spinnerManaAP2;
    }

    public JSpinner getSpinnerManaJoya() {
        return spinnerManaJoya;
    }

    public void setSpinnerManaJoya(JSpinner spinnerManaJoya) {
        this.spinnerManaJoya = spinnerManaJoya;
    }

    public JSpinner getSpinnerManaNivel() {
        return spinnerManaNivel;
    }

    public void setSpinnerManaNivel(JSpinner spinnerManaNivel) {
        this.spinnerManaNivel = spinnerManaNivel;
    }

    public JSpinner getSpinnerMaxManaBase() {
        return spinnerMaxManaBase;
    }

    public void setSpinnerMaxManaBase(JSpinner spinnerMaxManaBase) {
        this.spinnerMaxManaBase = spinnerMaxManaBase;
    }

    public JSpinner getSpinnerMaxVidaBase() {
        return spinnerMaxVidaBase;
    }

    public void setSpinnerMaxVidaBase(JSpinner spinnerMaxVidaBase) {
        this.spinnerMaxVidaBase = spinnerMaxVidaBase;
    }

    public JSpinner getSpinnerMaxVidaMob() {
        return spinnerMaxVidaMob;
    }

    public void setSpinnerMaxVidaMob(JSpinner spinnerMaxVidaMob) {
        this.spinnerMaxVidaMob = spinnerMaxVidaMob;
    }

    public JSpinner getSpinnerMejAtaFisArma() {
        return spinnerMejAtaFisArma;
    }

    public void setSpinnerMejAtaFisArma(JSpinner spinnerMejAtaFisArma) {
        this.spinnerMejAtaFisArma = spinnerMejAtaFisArma;
    }

    public JSpinner getSpinnerMejAtaMagArma() {
        return spinnerMejAtaMagArma;
    }

    public void setSpinnerMejAtaMagArma(JSpinner spinnerMejAtaMagArma) {
        this.spinnerMejAtaMagArma = spinnerMejAtaMagArma;
    }

    public JSpinner getSpinnerMejCritArma() {
        return spinnerMejCritArma;
    }

    public void setSpinnerMejCritArma(JSpinner spinnerMejCritArma) {
        this.spinnerMejCritArma = spinnerMejCritArma;
    }

    public JSpinner getSpinnerMejDefFisBotas() {
        return spinnerMejDefFisBotas;
    }

    public void setSpinnerMejDefFisBotas(JSpinner spinnerMejDefFisBotas) {
        this.spinnerMejDefFisBotas = spinnerMejDefFisBotas;
    }

    public JSpinner getSpinnerMejDefFisCasco() {
        return spinnerMejDefFisCasco;
    }

    public void setSpinnerMejDefFisCasco(JSpinner spinnerMejDefFisCasco) {
        this.spinnerMejDefFisCasco = spinnerMejDefFisCasco;
    }

    public JSpinner getSpinnerMejDefFisGrebas() {
        return spinnerMejDefFisGrebas;
    }

    public void setSpinnerMejDefFisGrebas(JSpinner spinnerMejDefFisGrebas) {
        this.spinnerMejDefFisGrebas = spinnerMejDefFisGrebas;
    }

    public JSpinner getSpinnerMejDefFisPechera() {
        return spinnerMejDefFisPechera;
    }

    public void setSpinnerMejDefFisPechera(JSpinner spinnerMejDefFisPechera) {
        this.spinnerMejDefFisPechera = spinnerMejDefFisPechera;
    }

    public JSpinner getSpinnerMejEvaFisBotas() {
        return spinnerMejEvaFisBotas;
    }

    public void setSpinnerMejEvaFisBotas(JSpinner spinnerMejEvaFisBotas) {
        this.spinnerMejEvaFisBotas = spinnerMejEvaFisBotas;
    }

    public JSpinner getSpinnerMejEvaFisCasco() {
        return spinnerMejEvaFisCasco;
    }

    public void setSpinnerMejEvaFisCasco(JSpinner spinnerMejEvaFisCasco) {
        this.spinnerMejEvaFisCasco = spinnerMejEvaFisCasco;
    }

    public JSpinner getSpinnerMejEvaFisGrebas() {
        return spinnerMejEvaFisGrebas;
    }

    public void setSpinnerMejEvaFisGrebas(JSpinner spinnerMejEvaFisGrebas) {
        this.spinnerMejEvaFisGrebas = spinnerMejEvaFisGrebas;
    }

    public JSpinner getSpinnerMejEvaFisPechera() {
        return spinnerMejEvaFisPechera;
    }

    public void setSpinnerMejEvaFisPechera(JSpinner spinnerMejEvaFisPechera) {
        this.spinnerMejEvaFisPechera = spinnerMejEvaFisPechera;
    }

    public JSpinner getSpinnerMejHRFisArma() {
        return spinnerMejHRFisArma;
    }

    public void setSpinnerMejHRFisArma(JSpinner spinnerMejHRFisArma) {
        this.spinnerMejHRFisArma = spinnerMejHRFisArma;
    }

    public JSpinner getSpinnerMejHRMagArma() {
        return spinnerMejHRMagArma;
    }

    public void setSpinnerMejHRMagArma(JSpinner spinnerMejHRMagArma) {
        this.spinnerMejHRMagArma = spinnerMejHRMagArma;
    }

    public JSpinner getSpinnerMejRoboMArma() {
        return spinnerMejRoboMArma;
    }

    public void setSpinnerMejRoboMArma(JSpinner spinnerMejRoboMArma) {
        this.spinnerMejRoboMArma = spinnerMejRoboMArma;
    }

    public JSpinner getSpinnerMejRoboVArma() {
        return spinnerMejRoboVArma;
    }

    public void setSpinnerMejRoboVArma(JSpinner spinnerMejRoboVArma) {
        this.spinnerMejRoboVArma = spinnerMejRoboVArma;
    }

    public JSpinner getSpinnerMortalAP() {
        return spinnerMortalAP;
    }

    public void setSpinnerMortalAP(JSpinner spinnerMortalAP) {
        this.spinnerMortalAP = spinnerMortalAP;
    }

    public JSpinner getSpinnerMortalBase() {
        return spinnerMortalBase;
    }

    public void setSpinnerMortalBase(JSpinner spinnerMortalBase) {
        this.spinnerMortalBase = spinnerMortalBase;
    }

    public JSpinner getSpinnerMortalNivel() {
        return spinnerMortalNivel;
    }

    public void setSpinnerMortalNivel(JSpinner spinnerMortalNivel) {
        this.spinnerMortalNivel = spinnerMortalNivel;
    }

    public JSpinner getSpinnerNivel() {
        return spinnerNivel;
    }

    public void setSpinnerNivel(JSpinner spinnerNivel) {
        this.spinnerNivel = spinnerNivel;
    }

    public JSpinner getSpinnerNivelIniArma() {
        return spinnerNivelIniArma;
    }

    public void setSpinnerNivelIniArma(JSpinner spinnerNivelIniArma) {
        this.spinnerNivelIniArma = spinnerNivelIniArma;
    }

    public JSpinner getSpinnerNivelMaxClan() {
        return spinnerNivelMaxClan;
    }

    public void setSpinnerNivelMaxClan(JSpinner spinnerNivelMaxClan) {
        this.spinnerNivelMaxClan = spinnerNivelMaxClan;
    }

    public JSpinner getSpinnerNivelMinArma() {
        return spinnerNivelMinArma;
    }

    public void setSpinnerNivelMinArma(JSpinner spinnerNivelMinArma) {
        this.spinnerNivelMinArma = spinnerNivelMinArma;
    }

    public JSpinner getSpinnerNivelMinJugClan() {
        return spinnerNivelMinJugClan;
    }

    public void setSpinnerNivelMinJugClan(JSpinner spinnerNivelMinJugClan) {
        this.spinnerNivelMinJugClan = spinnerNivelMinJugClan;
    }

    public JSpinner getSpinnerNivelMob() {
        return spinnerNivelMob;
    }

    public void setSpinnerNivelMob(JSpinner spinnerNivelMob) {
        this.spinnerNivelMob = spinnerNivelMob;
    }

    public JSpinner getSpinnerNumFijosClan() {
        return spinnerNumFijosClan;
    }

    public void setSpinnerNumFijosClan(JSpinner spinnerNumFijosClan) {
        this.spinnerNumFijosClan = spinnerNumFijosClan;
    }

    public JSpinner getSpinnerNumJugEditClan() {
        return spinnerNumJugEditClan;
    }

    public void setSpinnerNumJugEditClan(JSpinner spinnerNumJugEditClan) {
        this.spinnerNumJugEditClan = spinnerNumJugEditClan;
    }

    public JSpinner getSpinnerNumMaxMobSpawner() {
        return spinnerNumMaxMobSpawner;
    }

    public void setSpinnerNumMaxMobSpawner(JSpinner spinnerNumMaxMobSpawner) {
        this.spinnerNumMaxMobSpawner = spinnerNumMaxMobSpawner;
    }

    public JSpinner getSpinnerNumeroFijoJugGrupo() {
        return spinnerNumeroFijoJugGrupo;
    }

    public void setSpinnerNumeroFijoJugGrupo(JSpinner spinnerNumeroFijoJugGrupo) {
        this.spinnerNumeroFijoJugGrupo = spinnerNumeroFijoJugGrupo;
    }

    public JSpinner getSpinnerPHxNivel() {
        return spinnerPHxNivel;
    }

    public void setSpinnerPHxNivel(JSpinner spinnerPHxNivel) {
        this.spinnerPHxNivel = spinnerPHxNivel;
    }

    public JSpinner getSpinnerPrecioCArma() {
        return spinnerPrecioCArma;
    }

    public void setSpinnerPrecioCArma(JSpinner spinnerPrecioCArma) {
        this.spinnerPrecioCArma = spinnerPrecioCArma;
    }

    public JSpinner getSpinnerPrecioCBotas() {
        return spinnerPrecioCBotas;
    }

    public void setSpinnerPrecioCBotas(JSpinner spinnerPrecioCBotas) {
        this.spinnerPrecioCBotas = spinnerPrecioCBotas;
    }

    public JSpinner getSpinnerPrecioCCasco() {
        return spinnerPrecioCCasco;
    }

    public void setSpinnerPrecioCCasco(JSpinner spinnerPrecioCCasco) {
        this.spinnerPrecioCCasco = spinnerPrecioCCasco;
    }

    public JSpinner getSpinnerPrecioCGrebas() {
        return spinnerPrecioCGrebas;
    }

    public void setSpinnerPrecioCGrebas(JSpinner spinnerPrecioCGrebas) {
        this.spinnerPrecioCGrebas = spinnerPrecioCGrebas;
    }

    public JSpinner getSpinnerPrecioCJoya() {
        return spinnerPrecioCJoya;
    }

    public void setSpinnerPrecioCJoya(JSpinner spinnerPrecioCJoya) {
        this.spinnerPrecioCJoya = spinnerPrecioCJoya;
    }

    public JSpinner getSpinnerPrecioCPechera() {
        return spinnerPrecioCPechera;
    }

    public void setSpinnerPrecioCPechera(JSpinner spinnerPrecioCPechera) {
        this.spinnerPrecioCPechera = spinnerPrecioCPechera;
    }

    public JSpinner getSpinnerPrecioVArma() {
        return spinnerPrecioVArma;
    }

    public void setSpinnerPrecioVArma(JSpinner spinnerPrecioVArma) {
        this.spinnerPrecioVArma = spinnerPrecioVArma;
    }

    public JSpinner getSpinnerPrecioVBotas() {
        return spinnerPrecioVBotas;
    }

    public void setSpinnerPrecioVBotas(JSpinner spinnerPrecioVBotas) {
        this.spinnerPrecioVBotas = spinnerPrecioVBotas;
    }

    public JSpinner getSpinnerPrecioVCasco() {
        return spinnerPrecioVCasco;
    }

    public void setSpinnerPrecioVCasco(JSpinner spinnerPrecioVCasco) {
        this.spinnerPrecioVCasco = spinnerPrecioVCasco;
    }

    public JSpinner getSpinnerPrecioVGrebas() {
        return spinnerPrecioVGrebas;
    }

    public void setSpinnerPrecioVGrebas(JSpinner spinnerPrecioVGrebas) {
        this.spinnerPrecioVGrebas = spinnerPrecioVGrebas;
    }

    public JSpinner getSpinnerPrecioVJoya() {
        return spinnerPrecioVJoya;
    }

    public void setSpinnerPrecioVJoya(JSpinner spinnerPrecioVJoya) {
        this.spinnerPrecioVJoya = spinnerPrecioVJoya;
    }

    public JSpinner getSpinnerPrecioVPechera() {
        return spinnerPrecioVPechera;
    }

    public void setSpinnerPrecioVPechera(JSpinner spinnerPrecioVPechera) {
        this.spinnerPrecioVPechera = spinnerPrecioVPechera;
    }

    public JSpinner getSpinnerProSEArma() {
        return spinnerProSEArma;
    }

    public void setSpinnerProSEArma(JSpinner spinnerProSEArma) {
        this.spinnerProSEArma = spinnerProSEArma;
    }

    public JSpinner getSpinnerProbDetArma() {
        return spinnerProbDetArma;
    }

    public void setSpinnerProbDetArma(JSpinner spinnerProbDetArma) {
        this.spinnerProbDetArma = spinnerProbDetArma;
    }

    public JSpinner getSpinnerProbDetArmadura() {
        return spinnerProbDetArmadura;
    }

    public void setSpinnerProbDetArmadura(JSpinner spinnerProbDetArmadura) {
        this.spinnerProbDetArmadura = spinnerProbDetArmadura;
    }

    public JSpinner getSpinnerProbDetJoya() {
        return spinnerProbDetJoya;
    }

    public void setSpinnerProbDetJoya(JSpinner spinnerProbDetJoya) {
        this.spinnerProbDetJoya = spinnerProbDetJoya;
    }

    public JSpinner getSpinnerProbDropMob() {
        return spinnerProbDropMob;
    }

    public void setSpinnerProbDropMob(JSpinner spinnerProbDropMob) {
        this.spinnerProbDropMob = spinnerProbDropMob;
    }

    public JSpinner getSpinnerProbExitoArmadura() {
        return spinnerProbExitoArmadura;
    }

    public void setSpinnerProbExitoArmadura(JSpinner spinnerProbExitoArmadura) {
        this.spinnerProbExitoArmadura = spinnerProbExitoArmadura;
    }

    public JSpinner getSpinnerProbExitoJoya() {
        return spinnerProbExitoJoya;
    }

    public void setSpinnerProbExitoJoya(JSpinner spinnerProbExitoJoya) {
        this.spinnerProbExitoJoya = spinnerProbExitoJoya;
    }

    public JSpinner getSpinnerProbRotArma() {
        return spinnerProbRotArma;
    }

    public void setSpinnerProbRotArma(JSpinner spinnerProbRotArma) {
        this.spinnerProbRotArma = spinnerProbRotArma;
    }

    public JSpinner getSpinnerProbRotArmadura() {
        return spinnerProbRotArmadura;
    }

    public void setSpinnerProbRotArmadura(JSpinner spinnerProbRotArmadura) {
        this.spinnerProbRotArmadura = spinnerProbRotArmadura;
    }

    public JSpinner getSpinnerProbRotJoya() {
        return spinnerProbRotJoya;
    }

    public void setSpinnerProbRotJoya(JSpinner spinnerProbRotJoya) {
        this.spinnerProbRotJoya = spinnerProbRotJoya;
    }

    public JSpinner getSpinnerProbSEArmadura() {
        return spinnerProbSEArmadura;
    }

    public void setSpinnerProbSEArmadura(JSpinner spinnerProbSEArmadura) {
        this.spinnerProbSEArmadura = spinnerProbSEArmadura;
    }

    public JSpinner getSpinnerProbSEJoya() {
        return spinnerProbSEJoya;
    }

    public void setSpinnerProbSEJoya(JSpinner spinnerProbSEJoya) {
        this.spinnerProbSEJoya = spinnerProbSEJoya;
    }

    public JSpinner getSpinnerRadioSpawner() {
        return spinnerRadioSpawner;
    }

    public void setSpinnerRadioSpawner(JSpinner spinnerRadioSpawner) {
        this.spinnerRadioSpawner = spinnerRadioSpawner;
    }

    public JSpinner getSpinnerRangoSeguiMob() {
        return spinnerRangoSeguiMob;
    }

    public void setSpinnerRangoSeguiMob(JSpinner spinnerRangoSeguiMob) {
        this.spinnerRangoSeguiMob = spinnerRangoSeguiMob;
    }

    public JSpinner getSpinnerRefrescoSpawner() {
        return spinnerRefrescoSpawner;
    }

    public void setSpinnerRefrescoSpawner(JSpinner spinnerRefrescoSpawner) {
        this.spinnerRefrescoSpawner = spinnerRefrescoSpawner;
    }

    public JSpinner getSpinnerRetrocesoMob() {
        return spinnerRetrocesoMob;
    }

    public void setSpinnerRetrocesoMob(JSpinner spinnerRetrocesoMob) {
        this.spinnerRetrocesoMob = spinnerRetrocesoMob;
    }

    public JSpinner getSpinnerRoboMArma() {
        return spinnerRoboMArma;
    }

    public void setSpinnerRoboMArma(JSpinner spinnerRoboMArma) {
        this.spinnerRoboMArma = spinnerRoboMArma;
    }

    public JSpinner getSpinnerRoboMJoya() {
        return spinnerRoboMJoya;
    }

    public void setSpinnerRoboMJoya(JSpinner spinnerRoboMJoya) {
        this.spinnerRoboMJoya = spinnerRoboMJoya;
    }

    public JSpinner getSpinnerRoboVArma() {
        return spinnerRoboVArma;
    }

    public void setSpinnerRoboVArma(JSpinner spinnerRoboVArma) {
        this.spinnerRoboVArma = spinnerRoboVArma;
    }

    public JSpinner getSpinnerRoboVJoya() {
        return spinnerRoboVJoya;
    }

    public void setSpinnerRoboVJoya(JSpinner spinnerRoboVJoya) {
        this.spinnerRoboVJoya = spinnerRoboVJoya;
    }

    public JSpinner getSpinnerVelocidadAtaqueMob() {
        return spinnerVelocidadAtaqueMob;
    }

    public void setSpinnerVelocidadAtaqueMob(JSpinner spinnerVelocidadAtaqueMob) {
        this.spinnerVelocidadAtaqueMob = spinnerVelocidadAtaqueMob;
    }

    public JSpinner getSpinnerVelocidadMob() {
        return spinnerVelocidadMob;
    }

    public void setSpinnerVelocidadMob(JSpinner spinnerVelocidadMob) {
        this.spinnerVelocidadMob = spinnerVelocidadMob;
    }

    public JSpinner getSpinnerVidaAP() {
        return spinnerVidaAP;
    }

    public void setSpinnerVidaAP(JSpinner spinnerVidaAP) {
        this.spinnerVidaAP = spinnerVidaAP;
    }

    public JSpinner getSpinnerVidaAP2() {
        return spinnerVidaAP2;
    }

    public void setSpinnerVidaAP2(JSpinner spinnerVidaAP2) {
        this.spinnerVidaAP2 = spinnerVidaAP2;
    }

    public JSpinner getSpinnerVidaJoya() {
        return spinnerVidaJoya;
    }

    public void setSpinnerVidaJoya(JSpinner spinnerVidaJoya) {
        this.spinnerVidaJoya = spinnerVidaJoya;
    }

    public JSpinner getSpinnerVidaNivel() {
        return spinnerVidaNivel;
    }

    public void setSpinnerVidaNivel(JSpinner spinnerVidaNivel) {
        this.spinnerVidaNivel = spinnerVidaNivel;
    }

    public JSpinner getSpinnerXInicio() {
        return spinnerXInicio;
    }

    public void setSpinnerXInicio(JSpinner spinnerXInicio) {
        this.spinnerXInicio = spinnerXInicio;
    }

    public JSpinner getSpinnerXSpawnLoc() {
        return spinnerXSpawnLoc;
    }

    public void setSpinnerXSpawnLoc(JSpinner spinnerXSpawnLoc) {
        this.spinnerXSpawnLoc = spinnerXSpawnLoc;
    }

    public JSpinner getSpinnerYInicio() {
        return spinnerYInicio;
    }

    public void setSpinnerYInicio(JSpinner spinnerYInicio) {
        this.spinnerYInicio = spinnerYInicio;
    }

    public JSpinner getSpinnerYSpawnLoc() {
        return spinnerYSpawnLoc;
    }

    public void setSpinnerYSpawnLoc(JSpinner spinnerYSpawnLoc) {
        this.spinnerYSpawnLoc = spinnerYSpawnLoc;
    }

    public JSpinner getSpinnerZInicio() {
        return spinnerZInicio;
    }

    public void setSpinnerZInicio(JSpinner spinnerZInicio) {
        this.spinnerZInicio = spinnerZInicio;
    }

    public JSpinner getSpinnerZSpawnLoc() {
        return spinnerZSpawnLoc;
    }

    public void setSpinnerZSpawnLoc(JSpinner spinnerZSpawnLoc) {
        this.spinnerZSpawnLoc = spinnerZSpawnLoc;
    }

    public JSpinner getSpinnerformulax2clan() {
        return spinnerformulax2clan;
    }

    public void setSpinnerformulax2clan(JSpinner spinnerformulax2clan) {
        this.spinnerformulax2clan = spinnerformulax2clan;
    }

    public JSpinner getSpinnerformulaxclan() {
        return spinnerformulaxclan;
    }

    public void setSpinnerformulaxclan(JSpinner spinnerformulaxclan) {
        this.spinnerformulaxclan = spinnerformulaxclan;
    }

    public JTable getTablaClanes() {
        return tablaClanes;
    }

    public void setTablaClanes(JTable tablaClanes) {
        this.tablaClanes = tablaClanes;
    }

    public JTable getTablaDropsMob() {
        return tablaDropsMob;
    }

    public void setTablaDropsMob(JTable tablaDropsMob) {
        this.tablaDropsMob = tablaDropsMob;
    }

    public JTable getTablaExpNivel() {
        return tablaExpNivel;
    }

    public void setTablaExpNivel(JTable tablaExpNivel) {
        this.tablaExpNivel = tablaExpNivel;
    }

    public JTextField getTxtDescripObjeto() {
        return txtDescripObjeto;
    }

    public void setTxtDescripObjeto(JTextField txtDescripObjeto) {
        this.txtDescripObjeto = txtDescripObjeto;
    }

    public JTextField getTxtDescripcionArma() {
        return txtDescripcionArma;
    }

    public void setTxtDescripcionArma(JTextField txtDescripcionArma) {
        this.txtDescripcionArma = txtDescripcionArma;
    }

    public JTextField getTxtDescripcionArmadura() {
        return txtDescripcionArmadura;
    }

    public void setTxtDescripcionArmadura(JTextField txtDescripcionArmadura) {
        this.txtDescripcionArmadura = txtDescripcionArmadura;
    }

    public JTextField getTxtMundoInicio() {
        return txtMundoInicio;
    }

    public void setTxtMundoInicio(JTextField txtMundoInicio) {
        this.txtMundoInicio = txtMundoInicio;
    }

    public JTextField getTxtMundoSpawnLoc() {
        return txtMundoSpawnLoc;
    }

    public void setTxtMundoSpawnLoc(JTextField txtMundoSpawnLoc) {
        this.txtMundoSpawnLoc = txtMundoSpawnLoc;
    }

    public JTextField getTxtNombreArma() {
        return txtNombreArma;
    }

    public void setTxtNombreArma(JTextField txtNombreArma) {
        this.txtNombreArma = txtNombreArma;
    }

    public JTextField getTxtNombreClase() {
        return txtNombreClase;
    }

    public void setTxtNombreClase(JTextField txtNombreClase) {
        this.txtNombreClase = txtNombreClase;
    }

    public JTextField getTxtNombreJoya() {
        return txtNombreJoya;
    }

    public void setTxtNombreJoya(JTextField txtNombreJoya) {
        this.txtNombreJoya = txtNombreJoya;
    }

    public JTextField getTxtNombreMob() {
        return txtNombreMob;
    }

    public void setTxtNombreMob(JTextField txtNombreMob) {
        this.txtNombreMob = txtNombreMob;
    }

    public JTextField getTxtNombreObjeto() {
        return txtNombreObjeto;
    }

    public void setTxtNombreObjeto(JTextField txtNombreObjeto) {
        this.txtNombreObjeto = txtNombreObjeto;
    }

    public JTextField getTxtNombreObjetoMejArma() {
        return txtNombreObjetoMejArma;
    }

    public void setTxtNombreObjetoMejArma(JTextField txtNombreObjetoMejArma) {
        this.txtNombreObjetoMejArma = txtNombreObjetoMejArma;
    }

    public JTextField getTxtNombreObjetoMejArmadura() {
        return txtNombreObjetoMejArmadura;
    }

    public void setTxtNombreObjetoMejArmadura(JTextField txtNombreObjetoMejArmadura) {
        this.txtNombreObjetoMejArmadura = txtNombreObjetoMejArmadura;
    }

    public JTextField getTxtNombreSet() {
        return txtNombreSet;
    }

    public void setTxtNombreSet(JTextField txtNombreSet) {
        this.txtNombreSet = txtNombreSet;
    }

    public JSpinner getTxtX2Nivel() {
        return txtX2Nivel;
    }

    public void setTxtX2Nivel(JSpinner txtX2Nivel) {
        this.txtX2Nivel = txtX2Nivel;
    }

    public JSpinner getTxtX3Nivel() {
        return txtX3Nivel;
    }

    public void setTxtX3Nivel(JSpinner txtX3Nivel) {
        this.txtX3Nivel = txtX3Nivel;
    }

    public JSpinner getTxtXNivel() {
        return txtXNivel;
    }

    public void setTxtXNivel(JSpinner txtXNivel) {
        this.txtXNivel = txtXNivel;
    }

    public JCheckBox getCheckDayCycle() {
        return checkDayCycle;
    }

    public void setCheckDayCycle(JCheckBox checkDayCycle) {
        this.checkDayCycle = checkDayCycle;
    }

    public JCheckBox getCheckEnableGlobalChat() {
        return checkEnableGlobalChat;
    }

    public void setCheckEnableGlobalChat(JCheckBox checkEnableGlobalChat) {
        this.checkEnableGlobalChat = checkEnableGlobalChat;
    }

    public JCheckBox getCheckEnableMarketChat() {
        return checkEnableMarketChat;
    }

    public void setCheckEnableMarketChat(JCheckBox checkEnableMarketChat) {
        this.checkEnableMarketChat = checkEnableMarketChat;
    }

    public JCheckBox getCheckGuildChat() {
        return checkGuildChat;
    }

    public void setCheckGuildChat(JCheckBox checkGuildChat) {
        this.checkGuildChat = checkGuildChat;
    }

    public JCheckBox getCheckPartyChat() {
        return checkPartyChat;
    }

    public void setCheckPartyChat(JCheckBox checkPartyChat) {
        this.checkPartyChat = checkPartyChat;
    }

    public JCheckBox getCheckPrivateChat() {
        return checkPrivateChat;
    }

    public void setCheckPrivateChat(JCheckBox checkPrivateChat) {
        this.checkPrivateChat = checkPrivateChat;
    }

    public JComboBox getComboMessageColorGlobalChat() {
        return comboMessageColorGlobalChat;
    }

    public void setComboMessageColorGlobalChat(JComboBox comboMessageColorGlobalChat) {
        this.comboMessageColorGlobalChat = comboMessageColorGlobalChat;
    }

    public JComboBox getComboMessageColorGuildChat() {
        return comboMessageColorGuildChat;
    }

    public void setComboMessageColorGuildChat(JComboBox comboMessageColorGuildChat) {
        this.comboMessageColorGuildChat = comboMessageColorGuildChat;
    }

    public JComboBox getComboMessageColorLocalChat() {
        return comboMessageColorLocalChat;
    }

    public void setComboMessageColorLocalChat(JComboBox comboMessageColorLocalChat) {
        this.comboMessageColorLocalChat = comboMessageColorLocalChat;
    }

    public JComboBox getComboMessageColorMarketChat() {
        return comboMessageColorMarketChat;
    }

    public void setComboMessageColorMarketChat(JComboBox comboMessageColorMarketChat) {
        this.comboMessageColorMarketChat = comboMessageColorMarketChat;
    }

    public JComboBox getComboMessageColorNewsChat() {
        return comboMessageColorNewsChat;
    }

    public void setComboMessageColorNewsChat(JComboBox comboMessageColorNewsChat) {
        this.comboMessageColorNewsChat = comboMessageColorNewsChat;
    }

    public JComboBox getComboMessageColorPartyChat() {
        return comboMessageColorPartyChat;
    }

    public void setComboMessageColorPartyChat(JComboBox comboMessageColorPartyChat) {
        this.comboMessageColorPartyChat = comboMessageColorPartyChat;
    }

    public JComboBox getComboMessageColorPrivateChat() {
        return comboMessageColorPrivateChat;
    }

    public void setComboMessageColorPrivateChat(JComboBox comboMessageColorPrivateChat) {
        this.comboMessageColorPrivateChat = comboMessageColorPrivateChat;
    }

    public JComboBox getComboMessageColorWarningChat() {
        return comboMessageColorWarningChat;
    }

    public void setComboMessageColorWarningChat(JComboBox comboMessageColorWarningChat) {
        this.comboMessageColorWarningChat = comboMessageColorWarningChat;
    }

    public JComboBox getComboPrefixColorGlobalChat() {
        return comboPrefixColorGlobalChat;
    }

    public void setComboPrefixColorGlobalChat(JComboBox comboPrefixColorGlobalChat) {
        this.comboPrefixColorGlobalChat = comboPrefixColorGlobalChat;
    }

    public JComboBox getComboPrefixColorGuildChat() {
        return comboPrefixColorGuildChat;
    }

    public void setComboPrefixColorGuildChat(JComboBox comboPrefixColorGuildChat) {
        this.comboPrefixColorGuildChat = comboPrefixColorGuildChat;
    }

    public JComboBox getComboPrefixColorLocalChat() {
        return comboPrefixColorLocalChat;
    }

    public void setComboPrefixColorLocalChat(JComboBox comboPrefixColorLocalChat) {
        this.comboPrefixColorLocalChat = comboPrefixColorLocalChat;
    }

    public JComboBox getComboPrefixColorMarketChat() {
        return comboPrefixColorMarketChat;
    }

    public void setComboPrefixColorMarketChat(JComboBox comboPrefixColorMarketChat) {
        this.comboPrefixColorMarketChat = comboPrefixColorMarketChat;
    }

    public JComboBox getComboPrefixColorNewsChat() {
        return comboPrefixColorNewsChat;
    }

    public void setComboPrefixColorNewsChat(JComboBox comboPrefixColorNewsChat) {
        this.comboPrefixColorNewsChat = comboPrefixColorNewsChat;
    }

    public JComboBox getComboPrefixColorPartyChat() {
        return comboPrefixColorPartyChat;
    }

    public void setComboPrefixColorPartyChat(JComboBox comboPrefixColorPartyChat) {
        this.comboPrefixColorPartyChat = comboPrefixColorPartyChat;
    }

    public JComboBox getComboPrefixColorPrivateChat() {
        return comboPrefixColorPrivateChat;
    }

    public void setComboPrefixColorPrivateChat(JComboBox comboPrefixColorPrivateChat) {
        this.comboPrefixColorPrivateChat = comboPrefixColorPrivateChat;
    }

    public JComboBox getComboPrefixColorWarningChat() {
        return comboPrefixColorWarningChat;
    }

    public void setComboPrefixColorWarningChat(JComboBox comboPrefixColorWarningChat) {
        this.comboPrefixColorWarningChat = comboPrefixColorWarningChat;
    }

    public JComboBox getComboShortcutGlobalChat() {
        return comboShortcutGlobalChat;
    }

    public void setComboShortcutGlobalChat(JComboBox comboShortcutGlobalChat) {
        this.comboShortcutGlobalChat = comboShortcutGlobalChat;
    }

    public JComboBox getComboShortcutGuildChat() {
        return comboShortcutGuildChat;
    }

    public void setComboShortcutGuildChat(JComboBox comboShortcutGuildChat) {
        this.comboShortcutGuildChat = comboShortcutGuildChat;
    }

    public JComboBox getComboShortcutMarketChat() {
        return comboShortcutMarketChat;
    }

    public void setComboShortcutMarketChat(JComboBox comboShortcutMarketChat) {
        this.comboShortcutMarketChat = comboShortcutMarketChat;
    }

    public JComboBox getComboShortcutNewsChat() {
        return comboShortcutNewsChat;
    }

    public void setComboShortcutNewsChat(JComboBox comboShortcutNewsChat) {
        this.comboShortcutNewsChat = comboShortcutNewsChat;
    }

    public JComboBox getComboShortcutPartyChat() {
        return comboShortcutPartyChat;
    }

    public void setComboShortcutPartyChat(JComboBox comboShortcutPartyChat) {
        this.comboShortcutPartyChat = comboShortcutPartyChat;
    }

    public JComboBox getComboShortcutPrivateChat() {
        return comboShortcutPrivateChat;
    }

    public void setComboShortcutPrivateChat(JComboBox comboShortcutPrivateChat) {
        this.comboShortcutPrivateChat = comboShortcutPrivateChat;
    }

    public JComboBox getComboShortcutWarningChat() {
        return comboShortcutWarningChat;
    }

    public void setComboShortcutWarningChat(JComboBox comboShortcutWarningChat) {
        this.comboShortcutWarningChat = comboShortcutWarningChat;
    }

    public JSpinner getLblMaxNivelNivel() {
        return lblMaxNivelNivel;
    }

    public void setLblMaxNivelNivel(JSpinner lblMaxNivelNivel) {
        this.lblMaxNivelNivel = lblMaxNivelNivel;
    }

    public JSpinner getSpinnerDistanceLocalChat() {
        return spinnerDistanceLocalChat;
    }

    public void setSpinnerDistanceLocalChat(JSpinner spinnerDistanceLocalChat) {
        this.spinnerDistanceLocalChat = spinnerDistanceLocalChat;
    }

    public JTextField getTextPrefixGlobalChat() {
        return textPrefixGlobalChat;
    }

    public void setTextPrefixGlobalChat(JTextField textPrefixGlobalChat) {
        this.textPrefixGlobalChat = textPrefixGlobalChat;
    }

    public JTextField getTextPrefixGuildChat() {
        return textPrefixGuildChat;
    }

    public void setTextPrefixGuildChat(JTextField textPrefixGuildChat) {
        this.textPrefixGuildChat = textPrefixGuildChat;
    }

    public JTextField getTextPrefixLocalChat() {
        return textPrefixLocalChat;
    }

    public void setTextPrefixLocalChat(JTextField textPrefixLocalChat) {
        this.textPrefixLocalChat = textPrefixLocalChat;
    }

    public JTextField getTextPrefixMarketChat() {
        return textPrefixMarketChat;
    }

    public void setTextPrefixMarketChat(JTextField textPrefixMarketChat) {
        this.textPrefixMarketChat = textPrefixMarketChat;
    }

    public JTextField getTextPrefixNewsChat() {
        return textPrefixNewsChat;
    }

    public void setTextPrefixNewsChat(JTextField textPrefixNewsChat) {
        this.textPrefixNewsChat = textPrefixNewsChat;
    }

    public JTextField getTextPrefixPartyChat() {
        return textPrefixPartyChat;
    }

    public void setTextPrefixPartyChat(JTextField textPrefixPartyChat) {
        this.textPrefixPartyChat = textPrefixPartyChat;
    }

    public JTextField getTextPrefixPrivateChat() {
        return textPrefixPrivateChat;
    }

    public void setTextPrefixPrivateChat(JTextField textPrefixPrivateChat) {
        this.textPrefixPrivateChat = textPrefixPrivateChat;
    }

    public JTextField getTextPrefixWarningChat() {
        return textPrefixWarningChat;
    }

    public void setTextPrefixWarningChat(JTextField textPrefixWarningChat) {
        this.textPrefixWarningChat = textPrefixWarningChat;
    }
}
