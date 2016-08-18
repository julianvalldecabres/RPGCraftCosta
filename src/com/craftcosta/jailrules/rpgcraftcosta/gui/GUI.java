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

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.leveling.RPGLevels;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.RPGItemManagerUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
public class GUI extends javax.swing.JFrame {

    private static RPGItemManagerUI rpgIManUI;

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

    private static void createDefaultDirectories() throws IOException {
        //TODO AUTOMATIZAR EL PROCESO DE GENERACION DE LOS FICHEROS DE CONFIGURACION

        File file = new File(RPGFinals.dataFolder);
        System.out.println(Material.values());
        for (Material mat : Material.values()) {
            System.out.println(mat.name() + "(\"\"),");
        }
        if (!file.exists()) {

            JOptionPane.showMessageDialog(null, "Creating default config folders and files", "RPGCraftCosta-Information", 1);
            file.mkdirs();
        } else {
            JOptionPane.showMessageDialog(null, "Loading config from config files\nIf any config file or folder doesn't exists will be created by default", "RPGCraftCosta-Information", 1);
        }

        //LISTADO DE FICHEROS YAMLS PARA COPIAR POR DEFECTO
        final String path = "";
        final File jarFile = new File(GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile()) {  // Run with JAR file
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(path /*+ "/"*/)) { //filter according to the path
                    System.out.println("1");
                    System.out.println(name);
                    System.out.println("2");
                }
            }
            jar.close();
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

                } catch (URISyntaxException ex) {
                    // never happens
                }
            }
        }
        //Cargar las diferentes configuraciones con sus propios gestores
        rpgIManUI = new RPGItemManagerUI();
    }

    private static String getList(List<String> inexistentfiles) {
        String cadena = "";
        for (String inexistentfile : inexistentfiles) {
            cadena += "- " + inexistentfile + "\n";
        }
        return cadena;
    }

    /**
     * Creates new form RPGCraftCosta
     */
    public GUI() {
        initComponents();
        loadComboprueba();
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
        PanelConfig = new javax.swing.JTabbedPane();
        PanelInicio = new javax.swing.JPanel();
        btnprueba = new javax.swing.JButton();
        PanelConfigGeneral = new javax.swing.JPanel();
        PanelConfigGeneralGen = new javax.swing.JPanel();
        PanelConfigDanioGeneral = new javax.swing.JPanel();
        checkSistHambre = new javax.swing.JCheckBox();
        checkDanioCaida = new javax.swing.JCheckBox();
        checkDanioAhogo = new javax.swing.JCheckBox();
        checkDanioPvp = new javax.swing.JCheckBox();
        PanelEnableModulosGeneral = new javax.swing.JPanel();
        checkClanes = new javax.swing.JCheckBox();
        checkGrupos = new javax.swing.JCheckBox();
        PanelConfigInicio = new javax.swing.JPanel();
        lblMundoInicio = new javax.swing.JLabel();
        txtMundoInicio = new javax.swing.JTextField();
        lblXInicio = new javax.swing.JLabel();
        lblYInicio = new javax.swing.JLabel();
        lblZInicio = new javax.swing.JLabel();
        spinnerXInicio = new javax.swing.JSpinner();
        spinnerYInicio = new javax.swing.JSpinner();
        spinnerZInicio = new javax.swing.JSpinner();
        PanelConfigPlayers = new javax.swing.JPanel();
        checkColocarBloques = new javax.swing.JCheckBox();
        checkDestruirBloques = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        PanelConfigLevel = new javax.swing.JPanel();
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
        PanelGraficosNivel = new javax.swing.JPanel();
        PanelNivelExpNivel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaExpNivel = new javax.swing.JTable();
        lblPHxNivel = new javax.swing.JLabel();
        spinnerPHxNivel = new javax.swing.JSpinner();
        PanelConfigClases = new javax.swing.JPanel();
        comboSelectorClases = new javax.swing.JComboBox();
        btnCrearNuevaClase = new javax.swing.JButton();
        btnEditarClase = new javax.swing.JButton();
        btnEliminarClase = new javax.swing.JButton();
        PanelEditorClase = new javax.swing.JPanel();
        lblNombreClase = new javax.swing.JLabel();
        PanelAtribBasicoClase = new javax.swing.JPanel();
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
        PanelSubirNivel = new javax.swing.JPanel();
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
        PanelPuntoHabilidad = new javax.swing.JPanel();
        PanelHabFuerza = new javax.swing.JPanel();
        lblVidaAP = new javax.swing.JLabel();
        lblAtaFisAP = new javax.swing.JLabel();
        spinnerVidaAP = new javax.swing.JSpinner();
        spinnerAtaFisAP = new javax.swing.JSpinner();
        PanelHabConst = new javax.swing.JPanel();
        lblVidaAP2 = new javax.swing.JLabel();
        lblManaAP = new javax.swing.JLabel();
        spinnerVidaAP2 = new javax.swing.JSpinner();
        spinnerManaAP = new javax.swing.JSpinner();
        PanelHabInt = new javax.swing.JPanel();
        lblManaAP2 = new javax.swing.JLabel();
        lblAtaMagAP = new javax.swing.JLabel();
        spinnerManaAP2 = new javax.swing.JSpinner();
        spinnerAtaMagAP = new javax.swing.JSpinner();
        PanelHabDest = new javax.swing.JPanel();
        lblCritAP = new javax.swing.JLabel();
        lblMortalAP = new javax.swing.JLabel();
        spinnerCrtitAP = new javax.swing.JSpinner();
        spinnerMortalAP = new javax.swing.JSpinner();
        PanelConfigObjetos = new javax.swing.JPanel();
        btnCrearNuevoObjeto = new javax.swing.JButton();
        PanelEditorObjetos = new javax.swing.JPanel();
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
        comboListaObjetos = new javax.swing.JComboBox();
        btnEditarObjeto = new javax.swing.JButton();
        btnEliminarObjeto = new javax.swing.JButton();
        PanelConfigWeapon = new javax.swing.JPanel();
        PanelMejoraArma = new javax.swing.JPanel();
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
        PanelSelectorArma = new javax.swing.JPanel();
        btnCrearNuevaArma = new javax.swing.JButton();
        PanelEditorArma = new javax.swing.JPanel();
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
        PanelConfigJewels = new javax.swing.JPanel();
        PanelConfigJoyas = new javax.swing.JPanel();
        lblProbRotJoya = new javax.swing.JLabel();
        spinnerProbRotJoya = new javax.swing.JSpinner();
        spinnerProbDetJoya = new javax.swing.JSpinner();
        lblProbDetJoya = new javax.swing.JLabel();
        lblProbSEJoya = new javax.swing.JLabel();
        spinnerProbSEJoya = new javax.swing.JSpinner();
        spinnerProbExitoJoya = new javax.swing.JSpinner();
        lblProbExitoJoya = new javax.swing.JLabel();
        btnGuardarConfigJoyas = new javax.swing.JButton();
        PanelSelectorJoyas = new javax.swing.JPanel();
        btnCrearNuevaJoya = new javax.swing.JButton();
        PanelEditorJoya = new javax.swing.JPanel();
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
        PanelConfigSpawners = new javax.swing.JPanel();
        comboSelectorGenerador = new javax.swing.JComboBox();
        btnEditarSpawner = new javax.swing.JButton();
        btnEliminarSpawner = new javax.swing.JButton();
        PanelEditorSpawner = new javax.swing.JPanel();
        PanelLocSpawner = new javax.swing.JPanel();
        lblMundoSpawnLoc = new javax.swing.JLabel();
        lblXSpawnLoc = new javax.swing.JLabel();
        lblYSpawnLoc = new javax.swing.JLabel();
        lblZSpawnLoc = new javax.swing.JLabel();
        txtMundoSpawnLoc = new javax.swing.JTextField();
        spinnerXSpawnLoc = new javax.swing.JSpinner();
        spinnerYSpawnLoc = new javax.swing.JSpinner();
        spinnerZSpawnLoc = new javax.swing.JSpinner();
        PanelAtribSpawner = new javax.swing.JPanel();
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
        PanelConfigGuilds = new javax.swing.JPanel();
        btnResetConfigClan = new javax.swing.JButton();
        btnGuardarConfigClan = new javax.swing.JButton();
        PanelConfigClanes = new javax.swing.JPanel();
        PanelNumJugClan = new javax.swing.JPanel();
        rdBtnNumIlimitadosClan = new javax.swing.JRadioButton();
        rdBtnNumFijosClan = new javax.swing.JRadioButton();
        rdBtnLimitNivClan = new javax.swing.JRadioButton();
        spinnerNumFijosClan = new javax.swing.JSpinner();
        lblJugadoresClan = new javax.swing.JLabel();
        PanelSisContNivelClan = new javax.swing.JPanel();
        rdBtnContriSoloDon = new javax.swing.JRadioButton();
        rdBtnContriSoloContri = new javax.swing.JRadioButton();
        rdBtnContriAmbos = new javax.swing.JRadioButton();
        spinnerContriPorcentajeMuertes = new javax.swing.JSpinner();
        lblporcMuertesClan = new javax.swing.JLabel();
        lblnivMinJugCrearClan = new javax.swing.JLabel();
        spinnerNivelMinJugClan = new javax.swing.JSpinner();
        PanelOpcLimNivelClan = new javax.swing.JPanel();
        rdBtnOpcDinFijo = new javax.swing.JRadioButton();
        rdBtnOpcDinForm = new javax.swing.JRadioButton();
        PanelFormulaClan = new javax.swing.JPanel();
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
        PanelEditNivelesClan = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaClanes = new javax.swing.JTable();
        btnAnadirNivel = new javax.swing.JButton();
        btnEditarNivel = new javax.swing.JButton();
        btnEliminarNivel = new javax.swing.JButton();
        spinnerDineroEditClan = new javax.swing.JSpinner();
        lblDineroEditNivelClan = new javax.swing.JLabel();
        lblNumJugEditNivelClan = new javax.swing.JLabel();
        spinnerNumJugEditClan = new javax.swing.JSpinner();
        PanelConfigParties = new javax.swing.JPanel();
        btnGuardarConfigGrupos = new javax.swing.JButton();
        btnResetConfigGrupos = new javax.swing.JButton();
        PanelConfigGrupos = new javax.swing.JPanel();
        PanelNumJugGrupos = new javax.swing.JPanel();
        spinnerNumeroFijoJugGrupo = new javax.swing.JSpinner();
        lblJugadoresGrupo = new javax.swing.JLabel();
        rdBtnIlimitadosGrupo = new javax.swing.JRadioButton();
        rdBtnFijosGrupo = new javax.swing.JRadioButton();
        PanelPvpGrupos = new javax.swing.JPanel();
        rdBtnPermitidoPvpGrupo = new javax.swing.JRadioButton();
        rdBtnNoPermitidoPvpGrupo = new javax.swing.JRadioButton();
        PanelSistRepGrupos = new javax.swing.JPanel();
        rdBtnSinRepartoGrupo = new javax.swing.JRadioButton();
        rdBtnSoloDineroGrupo = new javax.swing.JRadioButton();
        rdBtnSoloExpGrupo = new javax.swing.JRadioButton();
        rdBtnAmbosGrupo = new javax.swing.JRadioButton();
        PanelOpcRepGRupos = new javax.swing.JPanel();
        rdBtnOpcionigualitarioGrupo = new javax.swing.JRadioButton();
        rdBtnOpcionProporcionalGrupo = new javax.swing.JRadioButton();
        PanelOpcRepProp = new javax.swing.JPanel();
        rdBtnProNivelGrupo = new javax.swing.JRadioButton();
        rdBtnProKillsGrupo = new javax.swing.JRadioButton();
        PanelConfigArmor = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        PanelConfigArmaduras = new javax.swing.JPanel();
        PanelSelectorArmadura = new javax.swing.JPanel();
        lblSelectorSet = new javax.swing.JLabel();
        comboListSets = new javax.swing.JComboBox();
        btnNuevoSet = new javax.swing.JButton();
        btnEditarSet = new javax.swing.JButton();
        btnEliminarSet = new javax.swing.JButton();
        PanelEditarSet = new javax.swing.JPanel();
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
        PanelPechera = new javax.swing.JPanel();
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
        PanelPantalones = new javax.swing.JPanel();
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
        PanelBotas = new javax.swing.JPanel();
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
        PanelCasco = new javax.swing.JPanel();
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
        PanelMejoraArmadura = new javax.swing.JPanel();
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
        PanelConfigMobs = new javax.swing.JPanel();
        comboSelectorMobs = new javax.swing.JComboBox();
        btnNuevoMob = new javax.swing.JButton();
        btnEditMob = new javax.swing.JButton();
        btnEliminarMob = new javax.swing.JButton();
        PanelEditorMobs = new javax.swing.JPanel();
        lblNombreMob = new javax.swing.JLabel();
        txtNombreMob = new javax.swing.JTextField();
        btnGuardarMob = new javax.swing.JButton();
        btnResetearMob = new javax.swing.JButton();
        lblNivelMob = new javax.swing.JLabel();
        lblTipoMob = new javax.swing.JLabel();
        comboSelectorTipoMob = new javax.swing.JComboBox();
        spinnerNivelMob = new javax.swing.JSpinner();
        PanelComportamientoMob = new javax.swing.JPanel();
        comboComportamiento = new javax.swing.JComboBox();
        PanelDropsMobs = new javax.swing.JPanel();
        PanelDropsObjetosMobs = new javax.swing.JPanel();
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
        PanelDropsDefault = new javax.swing.JPanel();
        spinnerDineroDropMob = new javax.swing.JSpinner();
        spinnerExpDropMob = new javax.swing.JSpinner();
        lblDineroDropMob = new javax.swing.JLabel();
        lblExpDropMob = new javax.swing.JLabel();
        PanelAtaqueMobs = new javax.swing.JPanel();
        comboTipoAtaqueMob = new javax.swing.JComboBox();
        PanelAtributosMobs = new javax.swing.JPanel();
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
        PanelConfChats = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        comboColorPrefijoComercio = new javax.swing.JComboBox();
        comboPrefijoComercio = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();

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

        PanelConfig.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        PanelConfig.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        PanelConfig.setToolTipText("");
        PanelConfig.setAutoscrolls(true);
        PanelConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PanelConfig.setMaximumSize(new java.awt.Dimension(1000, 600));
        PanelConfig.setMinimumSize(new java.awt.Dimension(800, 500));
        PanelConfig.setPreferredSize(new java.awt.Dimension(1000, 600));

        PanelInicio.setPreferredSize(new java.awt.Dimension(800, 600));

        btnprueba.setText("jButton2");
        btnprueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpruebaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelInicioLayout = new javax.swing.GroupLayout(PanelInicio);
        PanelInicio.setLayout(PanelInicioLayout);
        PanelInicioLayout.setHorizontalGroup(
            PanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInicioLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(btnprueba)
                .addContainerGap(732, Short.MAX_VALUE))
        );
        PanelInicioLayout.setVerticalGroup(
            PanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInicioLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnprueba)
                .addContainerGap(533, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Inicio", PanelInicio);

        PanelConfigGeneral.setPreferredSize(new java.awt.Dimension(800, 600));

        PanelConfigGeneralGen.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración general"));

        PanelConfigDanioGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion de daño"));

        checkSistHambre.setText("Sistema original de hambre");

        checkDanioCaida.setText("Daño por caida");

        checkDanioAhogo.setText("Daño por ahogamiento");

        checkDanioPvp.setText("Habilitar/Deshabilitar PvP");

        javax.swing.GroupLayout PanelConfigDanioGeneralLayout = new javax.swing.GroupLayout(PanelConfigDanioGeneral);
        PanelConfigDanioGeneral.setLayout(PanelConfigDanioGeneralLayout);
        PanelConfigDanioGeneralLayout.setHorizontalGroup(
            PanelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigDanioGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkSistHambre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioCaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioAhogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkDanioPvp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        PanelConfigDanioGeneralLayout.setVerticalGroup(
            PanelConfigDanioGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigDanioGeneralLayout.createSequentialGroup()
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

        PanelEnableModulosGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de módulos"));

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

        javax.swing.GroupLayout PanelEnableModulosGeneralLayout = new javax.swing.GroupLayout(PanelEnableModulosGeneral);
        PanelEnableModulosGeneral.setLayout(PanelEnableModulosGeneralLayout);
        PanelEnableModulosGeneralLayout.setHorizontalGroup(
            PanelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEnableModulosGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkClanes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        PanelEnableModulosGeneralLayout.setVerticalGroup(
            PanelEnableModulosGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEnableModulosGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkClanes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkGrupos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelConfigInicio.setBorder(javax.swing.BorderFactory.createTitledBorder("Localización de inicio"));

        lblMundoInicio.setText("Mundo");

        lblXInicio.setText("X");

        lblYInicio.setText("Y");

        lblZInicio.setText("Z");

        javax.swing.GroupLayout PanelConfigInicioLayout = new javax.swing.GroupLayout(PanelConfigInicio);
        PanelConfigInicio.setLayout(PanelConfigInicioLayout);
        PanelConfigInicioLayout.setHorizontalGroup(
            PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblXInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblYInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblZInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMundoInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMundoInicio)
                    .addComponent(spinnerXInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(spinnerYInicio)
                    .addComponent(spinnerZInicio))
                .addContainerGap())
        );
        PanelConfigInicioLayout.setVerticalGroup(
            PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMundoInicio)
                    .addComponent(txtMundoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXInicio)
                    .addComponent(spinnerXInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYInicio)
                    .addComponent(spinnerYInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZInicio)
                    .addComponent(spinnerZInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelConfigPlayers.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración jugadores"));

        checkColocarBloques.setText("Permitir colocar bloques");

        checkDestruirBloques.setText("Permitir destruir bloques");

        javax.swing.GroupLayout PanelConfigPlayersLayout = new javax.swing.GroupLayout(PanelConfigPlayers);
        PanelConfigPlayers.setLayout(PanelConfigPlayersLayout);
        PanelConfigPlayersLayout.setHorizontalGroup(
            PanelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigPlayersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkColocarBloques)
                    .addComponent(checkDestruirBloques))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelConfigPlayersLayout.setVerticalGroup(
            PanelConfigPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigPlayersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkColocarBloques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDestruirBloques)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelConfigGeneralGenLayout = new javax.swing.GroupLayout(PanelConfigGeneralGen);
        PanelConfigGeneralGen.setLayout(PanelConfigGeneralGenLayout);
        PanelConfigGeneralGenLayout.setHorizontalGroup(
            PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGeneralGenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelConfigGeneralGenLayout.createSequentialGroup()
                        .addGroup(PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelConfigInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelConfigDanioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelEnableModulosGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelConfigPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        PanelConfigGeneralGenLayout.setVerticalGroup(
            PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGeneralGenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelConfigDanioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelEnableModulosGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigGeneralGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelConfigInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelConfigPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelConfigGeneralLayout = new javax.swing.GroupLayout(PanelConfigGeneral);
        PanelConfigGeneral.setLayout(PanelConfigGeneralLayout);
        PanelConfigGeneralLayout.setHorizontalGroup(
            PanelConfigGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelConfigGeneralGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(419, Short.MAX_VALUE))
        );
        PanelConfigGeneralLayout.setVerticalGroup(
            PanelConfigGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelConfigGeneralGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        PanelConfig.addTab("General", PanelConfigGeneral);

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

        PanelGraficosNivel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gráfico"));

        javax.swing.GroupLayout PanelGraficosNivelLayout = new javax.swing.GroupLayout(PanelGraficosNivel);
        PanelGraficosNivel.setLayout(PanelGraficosNivelLayout);
        PanelGraficosNivelLayout.setHorizontalGroup(
            PanelGraficosNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
        );
        PanelGraficosNivelLayout.setVerticalGroup(
            PanelGraficosNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        PanelNivelExpNivel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de experiencia"));

        tablaExpNivel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaExpNivel.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tablaExpNivel);

        javax.swing.GroupLayout PanelNivelExpNivelLayout = new javax.swing.GroupLayout(PanelNivelExpNivel);
        PanelNivelExpNivel.setLayout(PanelNivelExpNivelLayout);
        PanelNivelExpNivelLayout.setHorizontalGroup(
            PanelNivelExpNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        PanelNivelExpNivelLayout.setVerticalGroup(
            PanelNivelExpNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblPHxNivel.setText("Puntos de habilidad por nivel alcanzado");

        javax.swing.GroupLayout PanelConfigLevelLayout = new javax.swing.GroupLayout(PanelConfigLevel);
        PanelConfigLevel.setLayout(PanelConfigLevelLayout);
        PanelConfigLevelLayout.setHorizontalGroup(
            PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                        .addComponent(lblInfoNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(177, 177, 177))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelConfigLevelLayout.createSequentialGroup()
                        .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                                .addComponent(PanelGraficosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PanelNivelExpNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                                        .addComponent(lblFdeXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtX3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblx3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtX2Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblInfoNivelMaxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
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
                                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                                        .addComponent(lblMaxNivelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblPHxNivel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spinnerPHxNivel)))
                                .addGap(184, 184, 184)))
                        .addGap(18, 18, 18))
                    .addComponent(lblInfoNivel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelConfigLevelLayout.setVerticalGroup(
            PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoNivel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoNivel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFdeXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtX3Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblx3Nivel)
                        .addComponent(txtX2Nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblx2Nivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtXNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblxNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnValidarFormulaNiveles)
                        .addComponent(btnDibujarGraficoNiveles)
                        .addComponent(btnGuardarConfigNiveles)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInfoNivelMaxNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaxNivelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPHxNivel)
                    .addComponent(spinnerPHxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                        .addComponent(PanelGraficosNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(PanelConfigLevelLayout.createSequentialGroup()
                        .addComponent(PanelNivelExpNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        PanelConfig.addTab("Niveles", PanelConfigLevel);

        PanelConfigClases.setAutoscrolls(true);
        PanelConfigClases.setName(""); // NOI18N
        PanelConfigClases.setPreferredSize(new java.awt.Dimension(800, 600));

        btnCrearNuevaClase.setText("Nueva clase");

        btnEditarClase.setText("Editar");
        btnEditarClase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClaseActionPerformed(evt);
            }
        });

        btnEliminarClase.setText("Eliminar");

        PanelEditorClase.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de clases"));
        PanelEditorClase.setName("Class Editor"); // NOI18N

        lblNombreClase.setText("Nombre");

        PanelAtribBasicoClase.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos base"));

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

        javax.swing.GroupLayout PanelAtribBasicoClaseLayout = new javax.swing.GroupLayout(PanelAtribBasicoClase);
        PanelAtribBasicoClase.setLayout(PanelAtribBasicoClaseLayout);
        PanelAtribBasicoClaseLayout.setHorizontalGroup(
            PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblVidaBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMaxVidaBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblManaBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMaxManaBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblDefFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefFisBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblAtaFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblHRFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRFisBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblEvaFisBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblAtaMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblDefMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefMagBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblHRMagBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRMagBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblEvaMagBase)
                        .addGap(12, 12, 12)
                        .addComponent(spinnerEvaMagBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblMortalBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMortalBase))
                    .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                        .addComponent(lblCritBase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerCritBase)))
                .addContainerGap())
        );
        PanelAtribBasicoClaseLayout.setVerticalGroup(
            PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtribBasicoClaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaBase)
                    .addComponent(spinnerMaxVidaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaBase)
                    .addComponent(spinnerMaxManaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisBase)
                    .addComponent(spinnerAtaFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisBase)
                    .addComponent(spinnerDefFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRFisBase)
                    .addComponent(spinnerHRFisBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisBase)
                    .addComponent(spinnerEvaBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagBase)
                    .addComponent(spinnerAtaMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefMagBase)
                    .addComponent(spinnerDefMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRMagBase)
                    .addComponent(spinnerHRMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaMagBase)
                    .addComponent(spinnerEvaMagBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritBase)
                    .addComponent(spinnerCritBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribBasicoClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        PanelSubirNivel.setBorder(javax.swing.BorderFactory.createTitledBorder(" Incremento de atributos por nivel"));

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

        javax.swing.GroupLayout PanelSubirNivelLayout = new javax.swing.GroupLayout(PanelSubirNivel);
        PanelSubirNivel.setLayout(PanelSubirNivelLayout);
        PanelSubirNivelLayout.setHorizontalGroup(
            PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblAtaFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblDefFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefFisNivel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblHRFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRFisNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblEvaFisNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaFisNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblAtaMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblEvaMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerEvaMagNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblCritNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerCritNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblMortalNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerMortalNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblDefMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerDefMagNivel))
                    .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblHRMagNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerHRMagNivel)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSubirNivelLayout.createSequentialGroup()
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblManaNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerManaNivel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelSubirNivelLayout.createSequentialGroup()
                        .addComponent(lblVidaNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerVidaNivel)))
                .addGap(10, 10, 10))
        );
        PanelSubirNivelLayout.setVerticalGroup(
            PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSubirNivelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaNivel)
                    .addComponent(spinnerVidaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaNivel)
                    .addComponent(spinnerManaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisNivel)
                    .addComponent(spinnerAtaFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisNivel)
                    .addComponent(spinnerDefFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRFisNivel)
                    .addComponent(spinnerHRFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisNivel)
                    .addComponent(spinnerEvaFisNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagNivel)
                    .addComponent(spinnerAtaMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefMagNivel)
                    .addComponent(spinnerDefMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHRMagNivel)
                    .addComponent(spinnerHRMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEvaMagNivel)
                    .addComponent(spinnerEvaMagNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritNivel)
                    .addComponent(spinnerCritNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSubirNivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMortalNivel)
                    .addComponent(spinnerMortalNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        PanelPuntoHabilidad.setBorder(javax.swing.BorderFactory.createTitledBorder("Incremento de atributos por punto de habilidad"));

        PanelHabFuerza.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de fuerza"));

        lblVidaAP.setText("Vida");

        lblAtaFisAP.setText("Ataque físico");

        spinnerVidaAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaFisAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout PanelHabFuerzaLayout = new javax.swing.GroupLayout(PanelHabFuerza);
        PanelHabFuerza.setLayout(PanelHabFuerzaLayout);
        PanelHabFuerzaLayout.setHorizontalGroup(
            PanelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabFuerzaLayout.createSequentialGroup()
                .addGroup(PanelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHabFuerzaLayout.createSequentialGroup()
                        .addComponent(lblVidaAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerVidaAP))
                    .addGroup(PanelHabFuerzaLayout.createSequentialGroup()
                        .addComponent(lblAtaFisAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaFisAP)))
                .addContainerGap())
        );
        PanelHabFuerzaLayout.setVerticalGroup(
            PanelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabFuerzaLayout.createSequentialGroup()
                .addGroup(PanelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaAP)
                    .addComponent(spinnerVidaAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelHabFuerzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisAP)
                    .addComponent(spinnerAtaFisAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        PanelHabConst.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de constitución"));

        lblVidaAP2.setText("Vida");

        lblManaAP.setText("Mana");

        spinnerVidaAP2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerManaAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout PanelHabConstLayout = new javax.swing.GroupLayout(PanelHabConst);
        PanelHabConst.setLayout(PanelHabConstLayout);
        PanelHabConstLayout.setHorizontalGroup(
            PanelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabConstLayout.createSequentialGroup()
                .addGroup(PanelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHabConstLayout.createSequentialGroup()
                        .addComponent(lblVidaAP2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerVidaAP2))
                    .addGroup(PanelHabConstLayout.createSequentialGroup()
                        .addComponent(lblManaAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerManaAP)))
                .addContainerGap())
        );
        PanelHabConstLayout.setVerticalGroup(
            PanelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabConstLayout.createSequentialGroup()
                .addGroup(PanelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaAP2)
                    .addComponent(spinnerVidaAP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelHabConstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaAP)
                    .addComponent(spinnerManaAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        PanelHabInt.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de inteligencia"));

        lblManaAP2.setText("Mana");

        lblAtaMagAP.setText("Ataque mágico");

        spinnerManaAP2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerAtaMagAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout PanelHabIntLayout = new javax.swing.GroupLayout(PanelHabInt);
        PanelHabInt.setLayout(PanelHabIntLayout);
        PanelHabIntLayout.setHorizontalGroup(
            PanelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabIntLayout.createSequentialGroup()
                .addGroup(PanelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHabIntLayout.createSequentialGroup()
                        .addComponent(lblManaAP2)
                        .addGap(10, 10, 10)
                        .addComponent(spinnerManaAP2))
                    .addGroup(PanelHabIntLayout.createSequentialGroup()
                        .addComponent(lblAtaMagAP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAtaMagAP)))
                .addContainerGap())
        );
        PanelHabIntLayout.setVerticalGroup(
            PanelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabIntLayout.createSequentialGroup()
                .addGroup(PanelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManaAP2)
                    .addComponent(spinnerManaAP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelHabIntLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaMagAP)
                    .addComponent(spinnerAtaMagAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        PanelHabDest.setBorder(javax.swing.BorderFactory.createTitledBorder("Punto de habilidad de destreza"));

        lblCritAP.setText("Crítico");

        lblMortalAP.setText("Mortal");

        spinnerCrtitAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        spinnerMortalAP.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));

        javax.swing.GroupLayout PanelHabDestLayout = new javax.swing.GroupLayout(PanelHabDest);
        PanelHabDest.setLayout(PanelHabDestLayout);
        PanelHabDestLayout.setHorizontalGroup(
            PanelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabDestLayout.createSequentialGroup()
                .addComponent(lblCritAP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerCrtitAP)
                .addGap(17, 17, 17))
            .addGroup(PanelHabDestLayout.createSequentialGroup()
                .addComponent(lblMortalAP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerMortalAP)
                .addGap(16, 16, 16))
        );
        PanelHabDestLayout.setVerticalGroup(
            PanelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHabDestLayout.createSequentialGroup()
                .addGroup(PanelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCritAP)
                    .addComponent(spinnerCrtitAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelHabDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMortalAP)
                    .addComponent(spinnerMortalAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout PanelPuntoHabilidadLayout = new javax.swing.GroupLayout(PanelPuntoHabilidad);
        PanelPuntoHabilidad.setLayout(PanelPuntoHabilidadLayout);
        PanelPuntoHabilidadLayout.setHorizontalGroup(
            PanelPuntoHabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelHabFuerza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelHabConst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelHabInt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelHabDest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelPuntoHabilidadLayout.setVerticalGroup(
            PanelPuntoHabilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPuntoHabilidadLayout.createSequentialGroup()
                .addComponent(PanelHabFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelHabConst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelHabInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelHabDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelEditorClaseLayout = new javax.swing.GroupLayout(PanelEditorClase);
        PanelEditorClase.setLayout(PanelEditorClaseLayout);
        PanelEditorClaseLayout.setHorizontalGroup(
            PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorClaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEditorClaseLayout.createSequentialGroup()
                        .addComponent(PanelAtribBasicoClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(PanelSubirNivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelEditorClaseLayout.createSequentialGroup()
                        .addComponent(lblNombreClase, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreClase)
                        .addGap(53, 53, 53)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEditorClaseLayout.createSequentialGroup()
                        .addComponent(checkEnableClase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetClase, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addComponent(PanelPuntoHabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelEditorClaseLayout.setVerticalGroup(
            PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorClaseLayout.createSequentialGroup()
                .addGroup(PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombreClase)
                    .addComponent(checkEnableClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResetClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PanelPuntoHabilidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelEditorClaseLayout.createSequentialGroup()
                        .addGroup(PanelEditorClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PanelAtribBasicoClase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelSubirNivel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 5, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout PanelConfigClasesLayout = new javax.swing.GroupLayout(PanelConfigClases);
        PanelConfigClases.setLayout(PanelConfigClasesLayout);
        PanelConfigClasesLayout.setHorizontalGroup(
            PanelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigClasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelEditorClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigClasesLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevaClase, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboSelectorClases, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarClase, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelConfigClasesLayout.setVerticalGroup(
            PanelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigClasesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigClasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSelectorClases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarClase)
                    .addComponent(btnEliminarClase)
                    .addComponent(btnCrearNuevaClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Clases", PanelConfigClases);

        PanelConfigObjetos.setPreferredSize(new java.awt.Dimension(800, 600));

        btnCrearNuevoObjeto.setText("Nuevo");

        PanelEditorObjetos.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de objetos"));

        lblNombreObjeto.setText("Nombre");

        lblTipoObjeto.setText("Tipo de objeto");

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

        javax.swing.GroupLayout PanelEditorObjetosLayout = new javax.swing.GroupLayout(PanelEditorObjetos);
        PanelEditorObjetos.setLayout(PanelEditorObjetosLayout);
        PanelEditorObjetosLayout.setHorizontalGroup(
            PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                        .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                                    .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblDescripObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNombreObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTipoObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(comboTipoObjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtNombreObjeto)
                                        .addComponent(txtDescripObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                                .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                                    .addComponent(btnAnadirDesObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnEditarDesObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                            .addComponent(btnEliminarDesObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addComponent(btnGuardarObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelEditorObjetosLayout.setVerticalGroup(
            PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelEditorObjetosLayout.createSequentialGroup()
                        .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreObjeto)
                            .addComponent(txtNombreObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoObjeto)
                            .addComponent(comboTipoObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripObjeto)
                            .addComponent(txtDescripObjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEditorObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAnadirDesObjeto)
                            .addComponent(btnEditarDesObjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarDesObjeto)))
                .addGap(7, 7, 7)
                .addComponent(btnGuardarObjeto)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        btnEditarObjeto.setText("Editar");

        btnEliminarObjeto.setText("Eliminar");

        javax.swing.GroupLayout PanelConfigObjetosLayout = new javax.swing.GroupLayout(PanelConfigObjetos);
        PanelConfigObjetos.setLayout(PanelConfigObjetosLayout);
        PanelConfigObjetosLayout.setHorizontalGroup(
            PanelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelEditorObjetos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigObjetosLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevoObjeto)
                        .addGap(130, 130, 130)
                        .addComponent(comboListaObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarObjeto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarObjeto)))
                .addContainerGap(414, Short.MAX_VALUE))
        );
        PanelConfigObjetosLayout.setVerticalGroup(
            PanelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigObjetosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevoObjeto)
                    .addComponent(comboListaObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarObjeto)
                    .addComponent(btnEliminarObjeto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Objetos", PanelConfigObjetos);

        PanelConfigWeapon.setPreferredSize(new java.awt.Dimension(800, 600));

        PanelMejoraArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Mejora de armas"));

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

        javax.swing.GroupLayout PanelMejoraArmaLayout = new javax.swing.GroupLayout(PanelMejoraArma);
        PanelMejoraArma.setLayout(PanelMejoraArmaLayout);
        PanelMejoraArmaLayout.setHorizontalGroup(
            PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMejoraArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNomMejoraArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTipoMejoradorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboMejoradorArma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreObjetoMejArma)
                            .addComponent(txtDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                        .addComponent(btnAnadirDescArma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarDEscArma, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarDescArma, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblProbSEArma, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(lblProbExitoArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinerProbExitoArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerProSEArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarConfigArma, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelMejoraArmaLayout.setVerticalGroup(
            PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotArma)
                                .addComponent(spinnerProbRotArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetArma, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNomMejoraArma)
                                .addComponent(txtNombreObjetoMejArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboMejoradorArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTipoMejoradorArma))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDescripcionArma)
                                .addComponent(txtDescripcionArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAnadirDescArma)
                                .addComponent(btnEditarDEscArma)
                                .addComponent(btnEliminarDescArma)))
                        .addGroup(PanelMejoraArmaLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEArma)
                                .addComponent(spinnerProSEArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinerProbExitoArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoArma))))))
            .addComponent(btnGuardarConfigArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PanelSelectorArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de armas"));

        btnCrearNuevaArma.setText("Nueva");

        PanelEditorArma.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de armas"));

        lblTipoArma.setText("Tipo de arma");

        lblNombreArma.setText("Nombre");

        lblNivelMinArma.setText("Nivel minimo para uso");

        lblNivelIniArma.setText("Nivel arma");

        lblCalidadArma.setText("Calidad");

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

        javax.swing.GroupLayout PanelEditorArmaLayout = new javax.swing.GroupLayout(PanelEditorArma);
        PanelEditorArma.setLayout(PanelEditorArmaLayout);
        PanelEditorArmaLayout.setHorizontalGroup(
            PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                        .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lvlRoboVArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lvlMejRoboVArma, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerRoboVArma)
                                    .addComponent(spinnerMejRoboVArma)))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(checkComerciableArma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkMejorableArma))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblCalidadArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNivelIniArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNivelMinArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNombreArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTipoArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboTipoArma, 0, 150, Short.MAX_VALUE)
                                    .addComponent(txtNombreArma)
                                    .addComponent(spinnerNivelMinArma)
                                    .addComponent(spinnerNivelIniArma)
                                    .addComponent(comboCalidadArma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblPrecioVArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerPrecioVArma))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblDineroExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerDineroExtraArma))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblExpExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerExpExtraArma)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMejAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAtaMagArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejAtaMagArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerMejAtaMagArma)
                                    .addComponent(spinnerMejAtaFisArma, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spinnerAtaMagArma)
                                    .addComponent(spinnerAtaFisArma)))
                            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                                .addComponent(lblMejRoboMArma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerMejRoboMArma))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditorArmaLayout.createSequentialGroup()
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblRoboMArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejCritArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCritArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejHRMagArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHRMagArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejHRFisArma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHRFisArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spinnerHRFisArma, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(spinnerMejHRFisArma)
                                    .addComponent(spinnerHRMagArma)
                                    .addComponent(spinnerMejHRMagArma)
                                    .addComponent(spinnerCritArma)
                                    .addComponent(spinnerMejCritArma)
                                    .addComponent(spinnerRoboMArma))))))
                .addContainerGap())
        );
        PanelEditorArmaLayout.setVerticalGroup(
            PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoArma)
                    .addComponent(comboTipoArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaFisArma)
                    .addComponent(spinnerAtaFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreArma)
                    .addComponent(txtNombreArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejAtaFisArma)
                    .addComponent(spinnerMejAtaFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivelMinArma)
                    .addComponent(spinnerNivelMinArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaMagArma)
                    .addComponent(spinnerAtaMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivelIniArma)
                    .addComponent(spinnerNivelIniArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejAtaMagArma)
                    .addComponent(spinnerMejAtaMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalidadArma)
                    .addComponent(comboCalidadArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRFisArma)
                    .addComponent(spinnerHRFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciableArma)
                    .addComponent(checkMejorableArma)
                    .addComponent(lblMejHRFisArma)
                    .addComponent(spinnerMejHRFisArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCArma)
                    .addComponent(spinnerPrecioCArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRMagArma)
                    .addComponent(spinnerHRMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVArma)
                    .addComponent(spinnerPrecioVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejHRMagArma)
                    .addComponent(spinnerMejHRMagArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraArma)
                    .addComponent(spinnerDineroExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCritArma)
                    .addComponent(spinnerCritArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraArma)
                    .addComponent(spinnerExpExtraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejCritArma)
                    .addComponent(spinnerMejCritArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlRoboVArma)
                    .addComponent(spinnerRoboVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRoboMArma)
                    .addComponent(spinnerRoboMArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlMejRoboVArma)
                    .addComponent(spinnerMejRoboVArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMejRoboMArma)
                    .addComponent(spinnerMejRoboMArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardarArma))
        );

        btnEditarArma.setText("Editar");

        btnEliminarArma.setText("Eliminar");

        javax.swing.GroupLayout PanelSelectorArmaLayout = new javax.swing.GroupLayout(PanelSelectorArma);
        PanelSelectorArma.setLayout(PanelSelectorArmaLayout);
        PanelSelectorArmaLayout.setHorizontalGroup(
            PanelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorArmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelEditorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelSelectorArmaLayout.createSequentialGroup()
                        .addComponent(btnCrearNuevaArma)
                        .addGap(130, 130, 130)
                        .addComponent(comboListaArmas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnEditarArma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarArma)))
                .addContainerGap())
        );
        PanelSelectorArmaLayout.setVerticalGroup(
            PanelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorArmaLayout.createSequentialGroup()
                .addGroup(PanelSelectorArmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevaArma)
                    .addComponent(comboListaArmas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarArma)
                    .addComponent(btnEliminarArma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelConfigWeaponLayout = new javax.swing.GroupLayout(PanelConfigWeapon);
        PanelConfigWeapon.setLayout(PanelConfigWeaponLayout);
        PanelConfigWeaponLayout.setHorizontalGroup(
            PanelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigWeaponLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelMejoraArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigWeaponLayout.createSequentialGroup()
                        .addComponent(PanelSelectorArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelConfigWeaponLayout.setVerticalGroup(
            PanelConfigWeaponLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigWeaponLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelMejoraArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSelectorArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelConfig.addTab("Armas", PanelConfigWeapon);

        PanelConfigJoyas.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de joyas"));

        lblProbRotJoya.setText("Probabilidad de rotura");

        lblProbDetJoya.setText("Probabilidad de deterioro");

        lblProbSEJoya.setText("Probabilidad sin efecto");

        lblProbExitoJoya.setText("Probabilidad de exito");

        btnGuardarConfigJoyas.setText("Guardar");

        javax.swing.GroupLayout PanelConfigJoyasLayout = new javax.swing.GroupLayout(PanelConfigJoyas);
        PanelConfigJoyas.setLayout(PanelConfigJoyasLayout);
        PanelConfigJoyasLayout.setHorizontalGroup(
            PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblProbSEJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                .addComponent(lblProbExitoJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spinnerProbExitoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerProbSEJoya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(spinnerProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnGuardarConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelConfigJoyasLayout.setVerticalGroup(
            PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotJoya)
                                .addComponent(spinnerProbRotJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(PanelConfigJoyasLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEJoya)
                                .addComponent(spinnerProbSEJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelConfigJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbExitoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoJoya)))
                        .addComponent(btnGuardarConfigJoyas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        PanelSelectorJoyas.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de joyas"));

        btnCrearNuevaJoya.setText("Nueva");

        PanelEditorJoya.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de joyas"));

        lblNombreJoya.setText("Nombre");

        lblObjetoJoya.setText("Objeto");

        lblCalidadJoya.setText("Calidad");

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

        javax.swing.GroupLayout PanelEditorJoyaLayout = new javax.swing.GroupLayout(PanelEditorJoya);
        PanelEditorJoya.setLayout(PanelEditorJoyaLayout);
        PanelEditorJoyaLayout.setHorizontalGroup(
            PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorJoyaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelEditorJoyaLayout.createSequentialGroup()
                        .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEditorJoyaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblPrecioCJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                    .addComponent(lblNombreJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblObjetoJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCalidadJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPrecioVJoya, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblRoboVJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDineroExtraJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(lblExpExtraJoya, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(checkComerciable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRoboMJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboObjetoJoya, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboCalidadJoya, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinnerPrecioVJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerPrecioCJoya)
                                .addComponent(txtNombreJoya, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(spinnerDineroExtraJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkCombinable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(spinnerRoboMJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerExpExtraJoya, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerRoboVJoya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                        .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
        PanelEditorJoyaLayout.setVerticalGroup(
            PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorJoyaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreJoya)
                    .addComponent(txtNombreJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblManaVidaJoya)
                    .addComponent(spinnerVidaJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjetoJoya)
                    .addComponent(comboObjetoJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblManaJoya)
                    .addComponent(spinnerManaJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalidadJoya)
                    .addComponent(comboCalidadJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtaFisJoya)
                    .addComponent(spinnerAtaFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVJoya)
                    .addComponent(spinnerPrecioVJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEvaFisJoya)
                    .addComponent(spinnerEvaFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCJoya)
                    .addComponent(spinnerPrecioCJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHRFisJoya)
                    .addComponent(spinnerHRFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciable)
                    .addComponent(checkCombinable)
                    .addComponent(lblDefFisJoya)
                    .addComponent(spinnerDefFisJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraJoya)
                    .addComponent(lblAtaMagJoya)
                    .addComponent(spinnerDineroExtraJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerAtaMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraJoya)
                    .addComponent(lvlEvaMagJoya)
                    .addComponent(spinnerExpExtraJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerEvaMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoboVJoya)
                    .addComponent(lblHRMagJoya)
                    .addComponent(spinnerRoboVJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerHRMagJoya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(PanelEditorJoyaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        javax.swing.GroupLayout PanelSelectorJoyasLayout = new javax.swing.GroupLayout(PanelSelectorJoyas);
        PanelSelectorJoyas.setLayout(PanelSelectorJoyasLayout);
        PanelSelectorJoyasLayout.setHorizontalGroup(
            PanelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorJoyasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelEditorJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelSelectorJoyasLayout.createSequentialGroup()
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
        PanelSelectorJoyasLayout.setVerticalGroup(
            PanelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorJoyasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSelectorJoyasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearNuevaJoya)
                    .addComponent(comboSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarJoya)
                    .addComponent(btnEliminarJoya))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorJoya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelConfigJewelsLayout = new javax.swing.GroupLayout(PanelConfigJewels);
        PanelConfigJewels.setLayout(PanelConfigJewelsLayout);
        PanelConfigJewelsLayout.setHorizontalGroup(
            PanelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigJewelsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(383, Short.MAX_VALUE))
        );
        PanelConfigJewelsLayout.setVerticalGroup(
            PanelConfigJewelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigJewelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelConfigJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelSelectorJoyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(333, 333, 333))
        );

        PanelConfig.addTab("Joyas", PanelConfigJewels);

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

        PanelEditorSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de Spawner"));

        PanelLocSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Localización"), "Localización"));

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

        javax.swing.GroupLayout PanelLocSpawnerLayout = new javax.swing.GroupLayout(PanelLocSpawner);
        PanelLocSpawner.setLayout(PanelLocSpawnerLayout);
        PanelLocSpawnerLayout.setHorizontalGroup(
            PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLocSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMundoSpawnLoc)
                    .addComponent(lblXSpawnLoc)
                    .addComponent(lblYSpawnLoc)
                    .addComponent(lblZSpawnLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerXSpawnLoc)
                    .addComponent(txtMundoSpawnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(spinnerYSpawnLoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spinnerZSpawnLoc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        PanelLocSpawnerLayout.setVerticalGroup(
            PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLocSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMundoSpawnLoc)
                    .addComponent(txtMundoSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXSpawnLoc)
                    .addComponent(spinnerXSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYSpawnLoc)
                    .addComponent(spinnerYSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLocSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZSpawnLoc)
                    .addComponent(spinnerZSpawnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelAtribSpawner.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades del generador"));

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

        javax.swing.GroupLayout PanelAtribSpawnerLayout = new javax.swing.GroupLayout(PanelAtribSpawner);
        PanelAtribSpawner.setLayout(PanelAtribSpawnerLayout);
        PanelAtribSpawnerLayout.setHorizontalGroup(
            PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtribSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAtribSpawnerLayout.createSequentialGroup()
                        .addComponent(checkEnableSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(164, 164, 164))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAtribSpawnerLayout.createSequentialGroup()
                        .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdSpawner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMobSpawner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRefrescoSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRadioSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumMaxMobSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spinnerNumMaxMobSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(spinnerRadioSpawner)
                            .addComponent(spinnerRefrescoSpawner)
                            .addComponent(comboMobSpawner, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboSelectorSpawner))))
                .addContainerGap())
        );
        PanelAtribSpawnerLayout.setVerticalGroup(
            PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtribSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdSpawner)
                    .addComponent(comboSelectorSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMobSpawner)
                    .addComponent(comboMobSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRefrescoSpawner)
                    .addComponent(spinnerRefrescoSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRadioSpawner)
                    .addComponent(spinnerRadioSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtribSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        javax.swing.GroupLayout PanelEditorSpawnerLayout = new javax.swing.GroupLayout(PanelEditorSpawner);
        PanelEditorSpawner.setLayout(PanelEditorSpawnerLayout);
        PanelEditorSpawnerLayout.setHorizontalGroup(
            PanelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelAtribSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelLocSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResetSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelEditorSpawnerLayout.setVerticalGroup(
            PanelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorSpawnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEditorSpawnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(PanelLocSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelAtribSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelEditorSpawnerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardarSpawner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetSpawner)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCrearSpawner.setText("Nuevo");

        javax.swing.GroupLayout PanelConfigSpawnersLayout = new javax.swing.GroupLayout(PanelConfigSpawners);
        PanelConfigSpawners.setLayout(PanelConfigSpawnersLayout);
        PanelConfigSpawnersLayout.setHorizontalGroup(
            PanelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelConfigSpawnersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PanelEditorSpawner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigSpawnersLayout.createSequentialGroup()
                        .addComponent(btnCrearSpawner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboSelectorGenerador, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(245, 245, 245))
        );
        PanelConfigSpawnersLayout.setVerticalGroup(
            PanelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigSpawnersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigSpawnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearSpawner)
                    .addComponent(comboSelectorGenerador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarSpawner)
                    .addComponent(btnEliminarSpawner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorSpawner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Spawners", PanelConfigSpawners);

        PanelConfigGuilds.setPreferredSize(new java.awt.Dimension(800, 600));

        btnResetConfigClan.setText("Resetear configuración");

        btnGuardarConfigClan.setText("Guardar");

        PanelConfigClanes.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración clanes"));

        PanelNumJugClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de jugadores"));

        rdBtnNumIlimitadosClan.setSelected(true);
        rdBtnNumIlimitadosClan.setText("Ilimitados");

        rdBtnNumFijosClan.setText("Fijos");

        rdBtnLimitNivClan.setText("Limitado por nivel del clan");

        lblJugadoresClan.setText("jugadores");

        javax.swing.GroupLayout PanelNumJugClanLayout = new javax.swing.GroupLayout(PanelNumJugClan);
        PanelNumJugClan.setLayout(PanelNumJugClanLayout);
        PanelNumJugClanLayout.setHorizontalGroup(
            PanelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNumJugClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnNumIlimitadosClan)
                    .addGroup(PanelNumJugClanLayout.createSequentialGroup()
                        .addComponent(rdBtnNumFijosClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumFijosClan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJugadoresClan))
                    .addComponent(rdBtnLimitNivClan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelNumJugClanLayout.setVerticalGroup(
            PanelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNumJugClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnNumIlimitadosClan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNumJugClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnNumFijosClan)
                    .addComponent(spinnerNumFijosClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJugadoresClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnLimitNivClan))
        );

        PanelSisContNivelClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Sistema de contribución clan limitado por nivel"));

        rdBtnContriSoloDon.setSelected(true);
        rdBtnContriSoloDon.setText("Sólo donación");

        rdBtnContriSoloContri.setText("Sólo contribución");

        rdBtnContriAmbos.setText("Ambos");

        lblporcMuertesClan.setText("%muertes");

        javax.swing.GroupLayout PanelSisContNivelClanLayout = new javax.swing.GroupLayout(PanelSisContNivelClan);
        PanelSisContNivelClan.setLayout(PanelSisContNivelClanLayout);
        PanelSisContNivelClanLayout.setHorizontalGroup(
            PanelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSisContNivelClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnContriSoloDon)
                    .addGroup(PanelSisContNivelClanLayout.createSequentialGroup()
                        .addComponent(rdBtnContriSoloContri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerContriPorcentajeMuertes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblporcMuertesClan))
                    .addComponent(rdBtnContriAmbos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelSisContNivelClanLayout.setVerticalGroup(
            PanelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSisContNivelClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnContriSoloDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSisContNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnContriSoloContri)
                    .addComponent(spinnerContriPorcentajeMuertes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblporcMuertesClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnContriAmbos))
        );

        lblnivMinJugCrearClan.setText("Nivel minimo del jugador para crear un clan");

        PanelOpcLimNivelClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de clan limitado por nivel"));

        rdBtnOpcDinFijo.setSelected(true);
        rdBtnOpcDinFijo.setText("Dinero Fijo");

        rdBtnOpcDinForm.setText("Formula de dinero");

        PanelFormulaClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Formula"));

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

        javax.swing.GroupLayout PanelFormulaClanLayout = new javax.swing.GroupLayout(PanelFormulaClan);
        PanelFormulaClan.setLayout(PanelFormulaClanLayout);
        PanelFormulaClanLayout.setHorizontalGroup(
            PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnValidarFormulaClan)
                .addGap(26, 26, 26)
                .addComponent(btnResetearFormulaClan)
                .addGap(10, 10, 10))
            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                        .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblfdex)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerformulax2clan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblx2clan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerformulaxclan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblMaxNivelClan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerNivelMaxClan))
                            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                                .addComponent(lblFormJugXNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(spinnerFormJugXNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblxclan))
                    .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                        .addComponent(lblFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFormulaClanLayout.setVerticalGroup(
            PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFormulaClanLayout.createSequentialGroup()
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblfdex)
                    .addComponent(spinnerformulax2clan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblx2clan)
                    .addComponent(spinnerformulaxclan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblxclan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxNivelClan)
                    .addComponent(spinnerNivelMaxClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormJugXNivelClan)
                    .addComponent(spinnerFormJugXNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormJugIniClan)
                    .addComponent(spinnerFormJugIniClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelFormulaClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnValidarFormulaClan)
                    .addComponent(btnResetearFormulaClan)))
        );

        PanelEditNivelesClan.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor/Visor de niveles"));

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

        javax.swing.GroupLayout PanelEditNivelesClanLayout = new javax.swing.GroupLayout(PanelEditNivelesClan);
        PanelEditNivelesClan.setLayout(PanelEditNivelesClanLayout);
        PanelEditNivelesClanLayout.setHorizontalGroup(
            PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                        .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(btnAnadirNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditarNivel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(lblDineroEditNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerDineroEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                                .addComponent(lblNumJugEditNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spinnerNumJugEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PanelEditNivelesClanLayout.setVerticalGroup(
            PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditNivelesClanLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarNivel)
                    .addComponent(btnEliminarNivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerDineroEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDineroEditNivelClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditNivelesClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumJugEditNivelClan)
                    .addComponent(spinnerNumJugEditClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAnadirNivel)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelOpcLimNivelClanLayout = new javax.swing.GroupLayout(PanelOpcLimNivelClan);
        PanelOpcLimNivelClan.setLayout(PanelOpcLimNivelClanLayout);
        PanelOpcLimNivelClanLayout.setHorizontalGroup(
            PanelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcLimNivelClanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnOpcDinFijo)
                    .addComponent(rdBtnOpcDinForm)
                    .addComponent(PanelFormulaClan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditNivelesClan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelOpcLimNivelClanLayout.setVerticalGroup(
            PanelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcLimNivelClanLayout.createSequentialGroup()
                .addGroup(PanelOpcLimNivelClanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelOpcLimNivelClanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rdBtnOpcDinFijo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdBtnOpcDinForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelFormulaClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(PanelEditNivelesClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelConfigClanesLayout = new javax.swing.GroupLayout(PanelConfigClanes);
        PanelConfigClanes.setLayout(PanelConfigClanesLayout);
        PanelConfigClanesLayout.setHorizontalGroup(
            PanelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigClanesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelOpcLimNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigClanesLayout.createSequentialGroup()
                        .addComponent(PanelNumJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelSisContNivelClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelConfigClanesLayout.createSequentialGroup()
                        .addComponent(lblnivMinJugCrearClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNivelMinJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelConfigClanesLayout.setVerticalGroup(
            PanelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigClanesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnivMinJugCrearClan)
                    .addComponent(spinnerNivelMinJugClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(PanelConfigClanesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelNumJugClan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelSisContNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelOpcLimNivelClan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelConfigGuildsLayout = new javax.swing.GroupLayout(PanelConfigGuilds);
        PanelConfigGuilds.setLayout(PanelConfigGuildsLayout);
        PanelConfigGuildsLayout.setHorizontalGroup(
            PanelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGuildsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelConfigGuildsLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(btnResetConfigClan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarConfigClan))
                    .addComponent(PanelConfigClanes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(681, 681, 681))
        );
        PanelConfigGuildsLayout.setVerticalGroup(
            PanelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGuildsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigGuildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetConfigClan)
                    .addComponent(btnGuardarConfigClan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelConfigClanes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Clanes", PanelConfigGuilds);
        if(checkClanes.isSelected()){
            lblnivMinJugCrearClan.setEnabled(checkClanes.isSelected());
            spinnerNivelMinJugClan.setEnabled(checkClanes.isSelected());
            PanelNumJugClan.setEnabled(checkClanes.isSelected());
            rdBtnNumIlimitadosClan.setEnabled(checkClanes.isSelected());
            rdBtnNumFijosClan.setEnabled(checkClanes.isSelected());
            rdBtnLimitNivClan.setEnabled(checkClanes.isSelected());
            if(rdBtnNumIlimitadosClan.isSelected()){
                spinnerNumFijosClan.setEnabled(false);
                lblJugadoresClan.setEnabled(false);
                PanelOpcLimNivelClan.setEnabled(false);
                rdBtnOpcDinFijo.setEnabled(false);
                rdBtnOpcDinForm.setEnabled(false);
                PanelFormulaClan.setEnabled(false);
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
                PanelSisContNivelClan.setEnabled(false);
                rdBtnContriAmbos.setEnabled(false);
                rdBtnContriSoloContri.setEnabled(false);
                rdBtnContriSoloDon.setEnabled(false);
                spinnerContriPorcentajeMuertes.setEnabled(false);
                lblporcMuertesClan.setEnabled(false);
                PanelEditNivelesClan.setEnabled(false);
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

                PanelOpcLimNivelClan.setEnabled(false);
                rdBtnOpcDinFijo.setEnabled(false);
                rdBtnOpcDinForm.setEnabled(false);
                PanelFormulaClan.setEnabled(false);
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
                PanelSisContNivelClan.setEnabled(false);
                rdBtnContriAmbos.setEnabled(false);
                rdBtnContriSoloContri.setEnabled(false);
                rdBtnContriSoloDon.setEnabled(false);
                spinnerContriPorcentajeMuertes.setEnabled(false);
                lblporcMuertesClan.setEnabled(false);
                PanelEditNivelesClan.setEnabled(false);
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
                PanelSisContNivelClan.setEnabled(true);
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
                PanelOpcLimNivelClan.setEnabled(true);
                rdBtnOpcDinFijo.setEnabled(true);
                rdBtnOpcDinForm.setEnabled(true);
                PanelEditNivelesClan.setEnabled(true);
                tablaClanes.setEnabled(true);
                if(rdBtnOpcDinFijo.isSelected()){
                    PanelFormulaClan.setEnabled(false);
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
                    PanelFormulaClan.setEnabled(true);
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

        PanelConfigGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de grupos"));

        PanelNumJugGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de jugadores"));

        lblJugadoresGrupo.setText("jugadores");

        rdBtnIlimitadosGrupo.setSelected(true);
        rdBtnIlimitadosGrupo.setText("Ilimitados");

        rdBtnFijosGrupo.setText("Fijos");
        rdBtnFijosGrupo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdBtnFijosGrupoStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PanelNumJugGruposLayout = new javax.swing.GroupLayout(PanelNumJugGrupos);
        PanelNumJugGrupos.setLayout(PanelNumJugGruposLayout);
        PanelNumJugGruposLayout.setHorizontalGroup(
            PanelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNumJugGruposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNumJugGruposLayout.createSequentialGroup()
                        .addComponent(rdBtnFijosGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumeroFijoJugGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJugadoresGrupo))
                    .addComponent(rdBtnIlimitadosGrupo)))
        );
        PanelNumJugGruposLayout.setVerticalGroup(
            PanelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNumJugGruposLayout.createSequentialGroup()
                .addComponent(rdBtnIlimitadosGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNumJugGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnFijosGrupo)
                    .addComponent(spinnerNumeroFijoJugGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJugadoresGrupo)))
        );

        PanelPvpGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("PvP"));

        rdBtnPermitidoPvpGrupo.setSelected(true);
        rdBtnPermitidoPvpGrupo.setText("Permitido");

        rdBtnNoPermitidoPvpGrupo.setText("No permitido");

        javax.swing.GroupLayout PanelPvpGruposLayout = new javax.swing.GroupLayout(PanelPvpGrupos);
        PanelPvpGrupos.setLayout(PanelPvpGruposLayout);
        PanelPvpGruposLayout.setHorizontalGroup(
            PanelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPvpGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnPermitidoPvpGrupo)
                    .addComponent(rdBtnNoPermitidoPvpGrupo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelPvpGruposLayout.setVerticalGroup(
            PanelPvpGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPvpGruposLayout.createSequentialGroup()
                .addComponent(rdBtnPermitidoPvpGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnNoPermitidoPvpGrupo))
        );

        PanelSistRepGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Sistema de reparto"));

        rdBtnSinRepartoGrupo.setSelected(true);
        rdBtnSinRepartoGrupo.setText("Sin reparto");

        rdBtnSoloDineroGrupo.setText("Sólo dinero");

        rdBtnSoloExpGrupo.setText("Sólo experiencia");

        rdBtnAmbosGrupo.setText("Dinero y experiencia");

        javax.swing.GroupLayout PanelSistRepGruposLayout = new javax.swing.GroupLayout(PanelSistRepGrupos);
        PanelSistRepGrupos.setLayout(PanelSistRepGruposLayout);
        PanelSistRepGruposLayout.setHorizontalGroup(
            PanelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSistRepGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnSinRepartoGrupo)
                    .addComponent(rdBtnSoloDineroGrupo)
                    .addComponent(rdBtnSoloExpGrupo)
                    .addComponent(rdBtnAmbosGrupo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelSistRepGruposLayout.setVerticalGroup(
            PanelSistRepGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSistRepGruposLayout.createSequentialGroup()
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

        PanelOpcRepGRupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de reparto"));

        rdBtnOpcionigualitarioGrupo.setSelected(true);
        rdBtnOpcionigualitarioGrupo.setText("Reparto igualitario");

        rdBtnOpcionProporcionalGrupo.setText("Reparto proporcional");

        PanelOpcRepProp.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones reparto proporcional"));

        rdBtnProNivelGrupo.setSelected(true);
        rdBtnProNivelGrupo.setText("Por nivel del jugador");

        rdBtnProKillsGrupo.setText("Por muertes del jugador");
        rdBtnProKillsGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnProKillsGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelOpcRepPropLayout = new javax.swing.GroupLayout(PanelOpcRepProp);
        PanelOpcRepProp.setLayout(PanelOpcRepPropLayout);
        PanelOpcRepPropLayout.setHorizontalGroup(
            PanelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcRepPropLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdBtnProNivelGrupo)
                    .addComponent(rdBtnProKillsGrupo))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        PanelOpcRepPropLayout.setVerticalGroup(
            PanelOpcRepPropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcRepPropLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnProNivelGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnProKillsGrupo))
        );

        javax.swing.GroupLayout PanelOpcRepGRuposLayout = new javax.swing.GroupLayout(PanelOpcRepGRupos);
        PanelOpcRepGRupos.setLayout(PanelOpcRepGRuposLayout);
        PanelOpcRepGRuposLayout.setHorizontalGroup(
            PanelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcRepGRuposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelOpcRepProp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelOpcRepGRuposLayout.createSequentialGroup()
                        .addGroup(PanelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdBtnOpcionigualitarioGrupo)
                            .addComponent(rdBtnOpcionProporcionalGrupo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelOpcRepGRuposLayout.setVerticalGroup(
            PanelOpcRepGRuposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOpcRepGRuposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdBtnOpcionigualitarioGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdBtnOpcionProporcionalGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelOpcRepProp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PanelConfigGruposLayout = new javax.swing.GroupLayout(PanelConfigGrupos);
        PanelConfigGrupos.setLayout(PanelConfigGruposLayout);
        PanelConfigGruposLayout.setHorizontalGroup(
            PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(PanelSistRepGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelNumJugGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelPvpGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelOpcRepGRupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelConfigGruposLayout.setVerticalGroup(
            PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigGruposLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelNumJugGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelPvpGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelConfigGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelOpcRepGRupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelSistRepGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout PanelConfigPartiesLayout = new javax.swing.GroupLayout(PanelConfigParties);
        PanelConfigParties.setLayout(PanelConfigPartiesLayout);
        PanelConfigPartiesLayout.setHorizontalGroup(
            PanelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigPartiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelConfigGrupos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelConfigPartiesLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(btnResetConfigGrupos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarConfigGrupos)))
                .addContainerGap(467, Short.MAX_VALUE))
        );
        PanelConfigPartiesLayout.setVerticalGroup(
            PanelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigPartiesLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(PanelConfigPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetConfigGrupos)
                    .addComponent(btnGuardarConfigGrupos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelConfigGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Grupos", PanelConfigParties);

        PanelConfigArmor.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración armaduras"));
        PanelConfigArmor.setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane6.setMaximumSize(new java.awt.Dimension(1000, 600));
        jScrollPane6.setMinimumSize(new java.awt.Dimension(800, 600));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(1000, 600));

        PanelSelectorArmadura.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector de sets de armadura"));

        lblSelectorSet.setText("Seleccion de set");

        btnNuevoSet.setText("Nuevo set");

        btnEditarSet.setText("Editar set");

        btnEliminarSet.setText("Eliminar set");

        PanelEditarSet.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de sets de armadura"));

        lblNombreSet.setText("Nombre de la armadura");

        lblNivel.setText("Nivel");

        lblMaterial.setText("Material base del set");

        lblCalidad.setText("Calidad del set");

        checkComerciableArmadura.setText("Comerciable");

        checkMejorableArmadura.setText("Mejorable");

        PanelPechera.setBorder(javax.swing.BorderFactory.createTitledBorder("Pechera"));

        lblDefFisPechera.setText("Defensa física");

        lblMejDefFisPechera.setText("Inc. Defensa física");

        lblMejEvaFisPechera.setText("Inc. Evasión física");

        lblEvaFisPechera.setText("Evasión física");

        lblPrecioVPechera.setText("Precio venta");

        lblPrecioCPechera.setText("Precio compra");

        lblExpExtraPechera.setText("Exp. extra");

        lblDineroExtraPechera.setText("Dinero extra");

        javax.swing.GroupLayout PanelPecheraLayout = new javax.swing.GroupLayout(PanelPechera);
        PanelPechera.setLayout(PanelPecheraLayout);
        PanelPecheraLayout.setHorizontalGroup(
            PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPecheraLayout.createSequentialGroup()
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPecheraLayout.createSequentialGroup()
                        .addComponent(spinnerDefFisPechera, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPecheraLayout.createSequentialGroup()
                        .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerPrecioVPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejDefFisPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerEvaFisPechera, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisPechera))
                        .addGap(10, 10, 10))
                    .addGroup(PanelPecheraLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioCPechera)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPecheraLayout.createSequentialGroup()
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerDineroExtraPechera, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(spinnerExpExtraPechera))
                .addContainerGap())
        );
        PanelPecheraLayout.setVerticalGroup(
            PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPecheraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisPechera)
                    .addComponent(spinnerDefFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisPechera)
                    .addComponent(spinnerMejDefFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisPechera)
                    .addComponent(spinnerEvaFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisPechera)
                    .addComponent(spinnerMejEvaFisPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVPechera)
                    .addComponent(spinnerPrecioVPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCPechera)
                    .addComponent(spinnerPrecioCPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraPechera)
                    .addComponent(spinnerExpExtraPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecheraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraPechera)
                    .addComponent(spinnerDineroExtraPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelPantalones.setBorder(javax.swing.BorderFactory.createTitledBorder("Grebas"));

        lblDefFisGrebas.setText("Defensa física");

        lblMejDefFisGrebas.setText("Inc. Defensa física");

        lvlMejEvaFisGrebas.setText("Inc. Evasión física");

        lblEvaFisGrebas.setText("Evasión física");

        lblPrecioVGrebas.setText("Precio venta");

        lblPrecioCGrebas.setText("Precio compra");

        lblExpExtraGrebas.setText("Exp. extra");

        lblDineroExtraGrebas.setText("Dinero extra");

        javax.swing.GroupLayout PanelPantalonesLayout = new javax.swing.GroupLayout(PanelPantalones);
        PanelPantalones.setLayout(PanelPantalonesLayout);
        PanelPantalonesLayout.setHorizontalGroup(
            PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantalonesLayout.createSequentialGroup()
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblExpExtraGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioVGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDefFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lvlMejEvaFisGrebas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCGrebas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraGrebas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        PanelPantalonesLayout.setVerticalGroup(
            PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPantalonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisGrebas)
                    .addComponent(spinnerDefFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisGrebas)
                    .addComponent(spinnerMejDefFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisGrebas)
                    .addComponent(spinnerEvaFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lvlMejEvaFisGrebas)
                    .addComponent(spinnerMejEvaFisGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVGrebas)
                    .addComponent(spinnerPrecioVGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCGrebas)
                    .addComponent(spinnerPrecioCGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraGrebas)
                    .addComponent(spinnerExpExtraGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPantalonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraGrebas)
                    .addComponent(spinnerDineroExtraGrebas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        PanelBotas.setBorder(javax.swing.BorderFactory.createTitledBorder("Botas"));

        lblDefFisBotas.setText("Defensa física");

        lblMejDefFisBotas.setText("Inc. Defensa física");

        lblMejEvaFisBotas.setText("Inc. Evasión física");

        lblEvaFisBotas.setText("Evasión física");

        lblPrecioVBotas.setText("Precio venta");

        lblPrecioCBotas.setText("Precio compra");

        lblExpExtraBotas.setText("Exp. extra");

        lblDineroExtraBotas.setText("Dinero extra");

        javax.swing.GroupLayout PanelBotasLayout = new javax.swing.GroupLayout(PanelBotas);
        PanelBotas.setLayout(PanelBotasLayout);
        PanelBotasLayout.setHorizontalGroup(
            PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotasLayout.createSequentialGroup()
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotasLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioVBotas)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotasLayout.createSequentialGroup()
                        .addComponent(spinnerPrecioCBotas)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotasLayout.createSequentialGroup()
                        .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerEvaFisBotas, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisBotas)
                            .addComponent(spinnerMejDefFisBotas, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(10, 10, 10))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotasLayout.createSequentialGroup()
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerDineroExtraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(spinnerExpExtraBotas))
                .addContainerGap())
        );
        PanelBotasLayout.setVerticalGroup(
            PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisBotas)
                    .addComponent(spinnerDefFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisBotas)
                    .addComponent(spinnerMejDefFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisBotas)
                    .addComponent(spinnerEvaFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisBotas)
                    .addComponent(spinnerMejEvaFisBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVBotas)
                    .addComponent(spinnerPrecioVBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCBotas)
                    .addComponent(spinnerPrecioCBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraBotas)
                    .addComponent(spinnerExpExtraBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelBotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraBotas)
                    .addComponent(spinnerDineroExtraBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelCasco.setBorder(javax.swing.BorderFactory.createTitledBorder("Yelmo"));

        lblDefFisCasco.setText("Defensa física");

        lblMejDefFisCasco.setText("Inc. Defensa física");

        lblMejEvaFisCasco.setText("Inc. Evasión física");

        lblEvaFisCasco.setText("Evasión física");

        lblPrecioVCasco.setText("Precio venta");

        lblPrecioCCasco.setText("Precio compra");

        lblExpExtraCasco.setText("Exp. extra");

        lblDineroExtraCasco.setText("Dinero extra");

        javax.swing.GroupLayout PanelCascoLayout = new javax.swing.GroupLayout(PanelCasco);
        PanelCasco.setLayout(PanelCascoLayout);
        PanelCascoLayout.setHorizontalGroup(
            PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCascoLayout.createSequentialGroup()
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPrecioVCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lblDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMejEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPrecioCCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCascoLayout.createSequentialGroup()
                        .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerMejDefFisCasco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(spinnerEvaFisCasco, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerMejEvaFisCasco))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCascoLayout.createSequentialGroup()
                        .addComponent(spinnerDefFisCasco)
                        .addContainerGap())
                    .addGroup(PanelCascoLayout.createSequentialGroup()
                        .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerPrecioVCasco)
                            .addComponent(spinnerPrecioCCasco))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCascoLayout.createSequentialGroup()
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExpExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDineroExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinnerExpExtraCasco, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(spinnerDineroExtraCasco))
                .addContainerGap())
        );
        PanelCascoLayout.setVerticalGroup(
            PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCascoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefFisCasco)
                    .addComponent(spinnerDefFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejDefFisCasco)
                    .addComponent(spinnerMejDefFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaFisCasco)
                    .addComponent(spinnerEvaFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMejEvaFisCasco)
                    .addComponent(spinnerMejEvaFisCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioVCasco)
                    .addComponent(spinnerPrecioVCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioCCasco)
                    .addComponent(spinnerPrecioCCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpExtraCasco)
                    .addComponent(spinnerExpExtraCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelCascoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroExtraCasco)
                    .addComponent(spinnerDineroExtraCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarSet.setText("Guardar");

        javax.swing.GroupLayout PanelEditarSetLayout = new javax.swing.GroupLayout(PanelEditarSet);
        PanelEditarSet.setLayout(PanelEditarSetLayout);
        PanelEditarSetLayout.setHorizontalGroup(
            PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditarSetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardarSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelEditarSetLayout.createSequentialGroup()
                        .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelPechera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelBotas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkComerciableArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PanelEditarSetLayout.createSequentialGroup()
                                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombreSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboMaterial, 0, 165, Short.MAX_VALUE)
                                    .addComponent(txtNombreSet))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelEditarSetLayout.createSequentialGroup()
                                .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelEditarSetLayout.createSequentialGroup()
                                .addComponent(lblCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(checkMejorableArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelCasco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelPantalones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PanelEditarSetLayout.setVerticalGroup(
            PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditarSetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreSet)
                    .addComponent(txtNombreSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivel)
                    .addComponent(spinnerNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaterial)
                    .addComponent(comboMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCalidad)
                    .addComponent(comboCalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkComerciableArmadura)
                    .addComponent(checkMejorableArmadura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelPechera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelPantalones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelEditarSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelBotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelCasco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarSet)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout PanelSelectorArmaduraLayout = new javax.swing.GroupLayout(PanelSelectorArmadura);
        PanelSelectorArmadura.setLayout(PanelSelectorArmaduraLayout);
        PanelSelectorArmaduraLayout.setHorizontalGroup(
            PanelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelEditarSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelSelectorArmaduraLayout.createSequentialGroup()
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
        PanelSelectorArmaduraLayout.setVerticalGroup(
            PanelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSelectorArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSelectorArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectorSet)
                    .addComponent(comboListSets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoSet)
                    .addComponent(btnEditarSet)
                    .addComponent(btnEliminarSet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditarSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PanelMejoraArmadura.setBorder(javax.swing.BorderFactory.createTitledBorder("Mejora de armaduras"));

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

        javax.swing.GroupLayout PanelMejoraArmaduraLayout = new javax.swing.GroupLayout(PanelMejoraArmadura);
        PanelMejoraArmadura.setLayout(PanelMejoraArmaduraLayout);
        PanelMejoraArmaduraLayout.setHorizontalGroup(
            PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                        .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombreMejoradorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMejoradorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDescripcionMejoradorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboMejoradorArmadura, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNombreObjetoMejArmadura)
                                    .addComponent(txtDescripcionArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                                .addComponent(btnAnadirDescArmadura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditarDEscArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminarDescArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spinnerProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblProbSEArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(lblProbExitoArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerProbExitoArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerProbSEArmadura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(btnGuardarConfigArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        PanelMejoraArmaduraLayout.setVerticalGroup(
            PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbRotArmadura)
                                .addComponent(spinnerProbRotArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbDetArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombreMejoradorArmadura)
                                .addComponent(txtNombreObjetoMejArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboMejoradorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMejoradorArmadura))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDescripcionMejoradorArmadura)
                                .addComponent(txtDescripcionArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAnadirDescArmadura)
                                .addComponent(btnEditarDEscArmadura)
                                .addComponent(btnEliminarDescArmadura)))
                        .addGroup(PanelMejoraArmaduraLayout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblProbSEArmadura)
                                .addComponent(spinnerProbSEArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(PanelMejoraArmaduraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinnerProbExitoArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProbExitoArmadura)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnGuardarConfigArmadura)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelConfigArmadurasLayout = new javax.swing.GroupLayout(PanelConfigArmaduras);
        PanelConfigArmaduras.setLayout(PanelConfigArmadurasLayout);
        PanelConfigArmadurasLayout.setHorizontalGroup(
            PanelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigArmadurasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelMejoraArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelSelectorArmadura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelConfigArmadurasLayout.setVerticalGroup(
            PanelConfigArmadurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigArmadurasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelMejoraArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSelectorArmadura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(PanelConfigArmaduras);

        javax.swing.GroupLayout PanelConfigArmorLayout = new javax.swing.GroupLayout(PanelConfigArmor);
        PanelConfigArmor.setLayout(PanelConfigArmorLayout);
        PanelConfigArmorLayout.setHorizontalGroup(
            PanelConfigArmorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelConfigArmorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelConfigArmorLayout.setVerticalGroup(
            PanelConfigArmorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigArmorLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Armaduras", PanelConfigArmor);

        PanelConfigMobs.setPreferredSize(new java.awt.Dimension(800, 600));

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

        PanelEditorMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de monstruos"));
        PanelEditorMobs.setName("Class Editor"); // NOI18N

        lblNombreMob.setText("Nombre");

        btnGuardarMob.setText("Guardar");
        btnGuardarMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMobActionPerformed(evt);
            }
        });

        btnResetearMob.setText("Resetear");

        lblNivelMob.setText("Nivel");

        lblTipoMob.setText("Tipo de monstruo");

        PanelComportamientoMob.setBorder(javax.swing.BorderFactory.createTitledBorder("Comportamiento"));

        javax.swing.GroupLayout PanelComportamientoMobLayout = new javax.swing.GroupLayout(PanelComportamientoMob);
        PanelComportamientoMob.setLayout(PanelComportamientoMobLayout);
        PanelComportamientoMobLayout.setHorizontalGroup(
            PanelComportamientoMobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelComportamientoMobLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboComportamiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelComportamientoMobLayout.setVerticalGroup(
            PanelComportamientoMobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelComportamientoMobLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboComportamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PanelDropsMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Drops del monstruo"));

        PanelDropsObjetosMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Drop de objetos"));

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

        javax.swing.GroupLayout PanelDropsObjetosMobsLayout = new javax.swing.GroupLayout(PanelDropsObjetosMobs);
        PanelDropsObjetosMobs.setLayout(PanelDropsObjetosMobsLayout);
        PanelDropsObjetosMobsLayout.setHorizontalGroup(
            PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsObjetosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PanelDropsObjetosMobsLayout.createSequentialGroup()
                        .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTipoDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblObjetoDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCantidadDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProbDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(spinnerCantidadDropMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(spinnerProbDropMob)
                            .addComponent(comboObjetoDropMob, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboTipoDropMob, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(PanelDropsObjetosMobsLayout.createSequentialGroup()
                        .addComponent(btnAnadirDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelDropsObjetosMobsLayout.setVerticalGroup(
            PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsObjetosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoDropMob)
                    .addComponent(comboTipoDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjetoDropMob)
                    .addComponent(comboObjetoDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidadDropMob)
                    .addComponent(spinnerCantidadDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProbDropMob)
                    .addComponent(spinnerProbDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsObjetosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnadirDropMob)
                    .addComponent(btnEditarDropMob)
                    .addComponent(btnEliminarDropMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelDropsDefault.setBorder(javax.swing.BorderFactory.createTitledBorder("Drops básicos"));

        spinnerDineroDropMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spinnerExpDropMob.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        lblDineroDropMob.setText("Dinero");

        lblExpDropMob.setText("Experiencia");

        javax.swing.GroupLayout PanelDropsDefaultLayout = new javax.swing.GroupLayout(PanelDropsDefault);
        PanelDropsDefault.setLayout(PanelDropsDefaultLayout);
        PanelDropsDefaultLayout.setHorizontalGroup(
            PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsDefaultLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDineroDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblExpDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerDineroDropMob, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(spinnerExpDropMob))
                .addContainerGap())
        );
        PanelDropsDefaultLayout.setVerticalGroup(
            PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsDefaultLayout.createSequentialGroup()
                .addGroup(PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDineroDropMob)
                    .addComponent(spinnerDineroDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDropsDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpDropMob)
                    .addComponent(spinnerExpDropMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelDropsMobsLayout = new javax.swing.GroupLayout(PanelDropsMobs);
        PanelDropsMobs.setLayout(PanelDropsMobsLayout);
        PanelDropsMobsLayout.setHorizontalGroup(
            PanelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelDropsDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelDropsObjetosMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDropsMobsLayout.setVerticalGroup(
            PanelDropsMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDropsMobsLayout.createSequentialGroup()
                .addComponent(PanelDropsDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDropsObjetosMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelAtaqueMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de ataque"));

        javax.swing.GroupLayout PanelAtaqueMobsLayout = new javax.swing.GroupLayout(PanelAtaqueMobs);
        PanelAtaqueMobs.setLayout(PanelAtaqueMobsLayout);
        PanelAtaqueMobsLayout.setHorizontalGroup(
            PanelAtaqueMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtaqueMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTipoAtaqueMob, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelAtaqueMobsLayout.setVerticalGroup(
            PanelAtaqueMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtaqueMobsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboTipoAtaqueMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PanelAtributosMobs.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos básicos del monstruo"));

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

        javax.swing.GroupLayout PanelAtributosMobsLayout = new javax.swing.GroupLayout(PanelAtributosMobs);
        PanelAtributosMobs.setLayout(PanelAtributosMobsLayout);
        PanelAtributosMobsLayout.setHorizontalGroup(
            PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtributosMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFuerzaDistMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaxVidaMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRangoSeguiMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRetrocesoMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVelocidadAtaqueMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVelocidadMob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAtaFisMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAtaDistMob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        PanelAtributosMobsLayout.setVerticalGroup(
            PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAtributosMobsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaFisMob)
                    .addComponent(spinnerAtaFisMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAtaDistMob)
                    .addComponent(spinnerAtaDistMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVelocidadMob)
                    .addComponent(spinnerVelocidadMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVelocidadAtaqueMob)
                    .addComponent(spinnerVelocidadAtaqueMob, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRetrocesoMob)
                    .addComponent(spinnerRetrocesoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRangoSeguiMob)
                    .addComponent(spinnerRangoSeguiMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxVidaMob)
                    .addComponent(spinnerMaxVidaMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAtributosMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFuerzaDistMob)
                    .addComponent(spinnerFuerzaDistMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout PanelEditorMobsLayout = new javax.swing.GroupLayout(PanelEditorMobs);
        PanelEditorMobs.setLayout(PanelEditorMobsLayout);
        PanelEditorMobsLayout.setHorizontalGroup(
            PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditorMobsLayout.createSequentialGroup()
                .addGroup(PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelEditorMobsLayout.createSequentialGroup()
                        .addGroup(PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PanelAtributosMobs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelAtaqueMobs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelComportamientoMob, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelDropsMobs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelEditorMobsLayout.createSequentialGroup()
                        .addComponent(lblNombreMob)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreMob, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTipoMob)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboSelectorTipoMob, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarMob, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetearMob, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelEditorMobsLayout.setVerticalGroup(
            PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditorMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNivelMob)
                    .addComponent(spinnerNivelMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoMob)
                    .addComponent(comboSelectorTipoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarMob)
                    .addComponent(btnResetearMob)
                    .addComponent(lblNombreMob))
                .addGap(2, 2, 2)
                .addGroup(PanelEditorMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEditorMobsLayout.createSequentialGroup()
                        .addComponent(PanelAtributosMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelAtaqueMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelComportamientoMob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PanelDropsMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout PanelConfigMobsLayout = new javax.swing.GroupLayout(PanelConfigMobs);
        PanelConfigMobs.setLayout(PanelConfigMobsLayout);
        PanelConfigMobsLayout.setHorizontalGroup(
            PanelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelEditorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelConfigMobsLayout.createSequentialGroup()
                        .addComponent(btnNuevoMob, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboSelectorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditMob, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarMob, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(237, Short.MAX_VALUE))
        );
        PanelConfigMobsLayout.setVerticalGroup(
            PanelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigMobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigMobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSelectorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoMob)
                    .addComponent(btnEditMob)
                    .addComponent(btnEliminarMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelEditorMobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        PanelConfig.addTab("Monstruos", PanelConfigMobs);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de chats"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de comercio"));

        jLabel1.setText("Prefijo");

        jLabel2.setText("Color del prefijo");

        jLabel3.setText("atajo en el chat");

        jLabel4.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboColorPrefijoComercio, 0, 154, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jTextField3)
                    .addComponent(comboPrefijoComercio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboColorPrefijoComercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboPrefijoComercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat global"));

        jLabel9.setText("Prefijo");

        jLabel10.setText("Color del prefijo");

        jLabel11.setText("atajo en el chat");

        jLabel12.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField9)
                    .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField12))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de grupos"));

        jLabel17.setText("Prefijo");

        jLabel18.setText("Color del prefijo");

        jLabel19.setText("atajo en el chat");

        jLabel20.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField20))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat de clanes"));

        jLabel21.setText("Prefijo");

        jLabel22.setText("Color del prefijo");

        jLabel23.setText("atajo en el chat");

        jLabel24.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField21)
                    .addComponent(jTextField22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField24))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat privado"));

        jLabel25.setText("Prefijo");

        jLabel26.setText("Color del prefijo");

        jLabel27.setText("atajo en el chat");

        jLabel28.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField25, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField28))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat noticias"));

        jLabel29.setText("Prefijo");

        jLabel30.setText("Color del prefijo");

        jLabel31.setText("atajo en el chat");

        jLabel32.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField29)
                    .addComponent(jTextField30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField31, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField32))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(146, 146, 146))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat incidencias"));

        jLabel37.setText("Prefijo");

        jLabel38.setText("Color del prefijo");

        jLabel39.setText("atajo en el chat");

        jLabel40.setText("Color del mensaje");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField37, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jTextField38, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField39, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField40))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración chat local"));

        jLabel45.setText("Prefijo");

        jLabel46.setText("Color del prefijo");

        jLabel48.setText("Color del mensaje");

        jLabel49.setText("Distancia de recepción");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField45, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField46)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField47)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jButton3.setText("Guardar configuración");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Habilitar/Deshabilitar chats"));

        jCheckBox1.setText("Habilitar/Deshabilitar chat global");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Habilitar/Deshabilitar chat comercio");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Habilitar/Deshabilitar chat clan");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Habilitar/Deshabilitar chat grupo");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("Habilitar/Deshabilitar chat privado");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(4, 4, 4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(347, 347, 347))
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(504, 504, 504))
        );

        jScrollPane4.setViewportView(jPanel1);

        javax.swing.GroupLayout PanelConfChatsLayout = new javax.swing.GroupLayout(PanelConfChats);
        PanelConfChats.setLayout(PanelConfChatsLayout);
        PanelConfChatsLayout.setHorizontalGroup(
            PanelConfChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelConfChatsLayout.setVerticalGroup(
            PanelConfChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelConfig.addTab("Chat", PanelConfChats);

        getContentPane().add(PanelConfig, java.awt.BorderLayout.CENTER);

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
            PanelGraficosNivel.removeAll();
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
                    PanelGraficosNivel.setLayout(new java.awt.BorderLayout());
                    PanelGraficosNivel.add(chartPanel, BorderLayout.CENTER);
                    PanelGraficosNivel.validate();
                    PanelGraficosNivel.repaint();
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void checkGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkGruposActionPerformed
        if(checkGrupos.isSelected()){
            PanelConfig.setEnabledAt(9, true);
        }else{
            PanelConfig.setEnabledAt(9, false);
        }
    }//GEN-LAST:event_checkGruposActionPerformed

    private void checkClanesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkClanesActionPerformed
        if(checkClanes.isSelected()){
            PanelConfig.setEnabledAt(8, true);
        }else{
            PanelConfig.setEnabledAt(8, false);
        }
    }//GEN-LAST:event_checkClanesActionPerformed

    private void btnpruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpruebaActionPerformed
        JOptionPane.showMessageDialog(null, "Tamaño ventana: " + this.getBounds().width + "," + this.getBounds().height, "RPGCraftCosta-Información", 1);
    }//GEN-LAST:event_btnpruebaActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws FileNotFoundException {

        try {
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
                java.util.logging.Logger.getLogger(RPGCraftCosta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(RPGCraftCosta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(RPGCraftCosta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(RPGCraftCosta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            //Create Default config to work with
            createDefaultDirectories();
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GUI().setVisible(true);

                }
            });
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAtaqueMobs;
    private javax.swing.JPanel PanelAtribBasicoClase;
    private javax.swing.JPanel PanelAtribSpawner;
    private javax.swing.JPanel PanelAtributosMobs;
    private javax.swing.JPanel PanelBotas;
    private javax.swing.JPanel PanelCasco;
    private javax.swing.JPanel PanelComportamientoMob;
    private javax.swing.JPanel PanelConfChats;
    private javax.swing.JTabbedPane PanelConfig;
    private javax.swing.JPanel PanelConfigArmaduras;
    private javax.swing.JPanel PanelConfigArmor;
    private javax.swing.JPanel PanelConfigClanes;
    private javax.swing.JPanel PanelConfigClases;
    private javax.swing.JPanel PanelConfigDanioGeneral;
    private javax.swing.JPanel PanelConfigGeneral;
    private javax.swing.JPanel PanelConfigGeneralGen;
    private javax.swing.JPanel PanelConfigGrupos;
    private javax.swing.JPanel PanelConfigGuilds;
    private javax.swing.JPanel PanelConfigInicio;
    private javax.swing.JPanel PanelConfigJewels;
    private javax.swing.JPanel PanelConfigJoyas;
    private javax.swing.JPanel PanelConfigLevel;
    private javax.swing.JPanel PanelConfigMobs;
    private javax.swing.JPanel PanelConfigObjetos;
    private javax.swing.JPanel PanelConfigParties;
    private javax.swing.JPanel PanelConfigPlayers;
    private javax.swing.JPanel PanelConfigSpawners;
    private javax.swing.JPanel PanelConfigWeapon;
    private javax.swing.JPanel PanelDropsDefault;
    private javax.swing.JPanel PanelDropsMobs;
    private javax.swing.JPanel PanelDropsObjetosMobs;
    private javax.swing.JPanel PanelEditNivelesClan;
    private javax.swing.JPanel PanelEditarSet;
    private javax.swing.JPanel PanelEditorArma;
    private javax.swing.JPanel PanelEditorClase;
    private javax.swing.JPanel PanelEditorJoya;
    private javax.swing.JPanel PanelEditorMobs;
    private javax.swing.JPanel PanelEditorObjetos;
    private javax.swing.JPanel PanelEditorSpawner;
    private javax.swing.JPanel PanelEnableModulosGeneral;
    private javax.swing.JPanel PanelFormulaClan;
    private javax.swing.JPanel PanelGraficosNivel;
    private javax.swing.JPanel PanelHabConst;
    private javax.swing.JPanel PanelHabDest;
    private javax.swing.JPanel PanelHabFuerza;
    private javax.swing.JPanel PanelHabInt;
    private javax.swing.JPanel PanelInicio;
    private javax.swing.JPanel PanelLocSpawner;
    private javax.swing.JPanel PanelMejoraArma;
    private javax.swing.JPanel PanelMejoraArmadura;
    private javax.swing.JPanel PanelNivelExpNivel;
    private javax.swing.JPanel PanelNumJugClan;
    private javax.swing.JPanel PanelNumJugGrupos;
    private javax.swing.JPanel PanelOpcLimNivelClan;
    private javax.swing.JPanel PanelOpcRepGRupos;
    private javax.swing.JPanel PanelOpcRepProp;
    private javax.swing.JPanel PanelPantalones;
    private javax.swing.JPanel PanelPechera;
    private javax.swing.JPanel PanelPuntoHabilidad;
    private javax.swing.JPanel PanelPvpGrupos;
    private javax.swing.JPanel PanelSelectorArma;
    private javax.swing.JPanel PanelSelectorArmadura;
    private javax.swing.JPanel PanelSelectorJoyas;
    private javax.swing.JPanel PanelSisContNivelClan;
    private javax.swing.JPanel PanelSistRepGrupos;
    private javax.swing.JPanel PanelSubirNivel;
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
    private javax.swing.JButton btnGuardarConfigClan;
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
    private javax.swing.JCheckBox checkDestruirBloques;
    private javax.swing.JCheckBox checkEnableClase;
    private javax.swing.JCheckBox checkEnableSpawner;
    private javax.swing.JCheckBox checkGrupos;
    private javax.swing.JCheckBox checkMejorableArma;
    private javax.swing.JCheckBox checkMejorableArmadura;
    private javax.swing.JCheckBox checkSistHambre;
    private javax.swing.JComboBox comboCalidad;
    private javax.swing.JComboBox comboCalidadArma;
    private javax.swing.JComboBox comboCalidadJoya;
    private javax.swing.JComboBox comboColorPrefijoComercio;
    private javax.swing.JComboBox comboComportamiento;
    private javax.swing.JComboBox comboListSets;
    private javax.swing.JComboBox comboListaArmas;
    private javax.swing.JComboBox comboListaObjetos;
    private javax.swing.JComboBox comboMaterial;
    private javax.swing.JComboBox comboMejoradorArma;
    private javax.swing.JComboBox comboMejoradorArmadura;
    private javax.swing.JComboBox comboMobSpawner;
    private javax.swing.JComboBox comboObjetoDropMob;
    private javax.swing.JComboBox comboObjetoJoya;
    private javax.swing.JComboBox comboPrefijoComercio;
    private javax.swing.JComboBox comboSelectorClases;
    private javax.swing.JComboBox comboSelectorGenerador;
    private javax.swing.JComboBox comboSelectorJoyas;
    private javax.swing.JComboBox comboSelectorMobs;
    private javax.swing.JTextField comboSelectorSpawner;
    private javax.swing.JComboBox comboSelectorTipoMob;
    private javax.swing.JComboBox comboTipoArma;
    private javax.swing.JComboBox comboTipoAtaqueMob;
    private javax.swing.JComboBox comboTipoDropMob;
    private javax.swing.JComboBox comboTipoObjeto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListDescArma;
    private javax.swing.JList jListDescArmadura;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField9;
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
    private javax.swing.JLabel lblCantidadDropMob;
    private javax.swing.JLabel lblCritAP;
    private javax.swing.JLabel lblCritArma;
    private javax.swing.JLabel lblCritBase;
    private javax.swing.JLabel lblCritNivel;
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
    private javax.swing.JLabel lblPHxNivel;
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
    private javax.swing.JLabel lblSelectorSet;
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

    private void loadComboprueba() {
        //prueba de combo colores
        for(ChatColor color: ChatColor.values()){
            comboColorPrefijoComercio.addItem(color.name());
        }
        String caracteresEspeciales="#@$%&\\=?¿¡!'*^{}[],;.:-_<>";
        for(int i=0;i<caracteresEspeciales.length();i++){
            comboPrefijoComercio.addItem(caracteresEspeciales.charAt(i));
        }
    }

}
