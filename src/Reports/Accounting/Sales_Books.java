package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;

public class Sales_Books extends _JInternalFrame {

          private static final long serialVersionUID = 1162406788303915011L;

          static String title = "Sales Reports";
          static Dimension frameSize = new Dimension(620, 440);

          private static _JLookup lookupCompany;
          private static _JLookup lookupProject;
          private static _JLookup lookupPhase;

          private static JTextField txtCompany;
          private static JTextField txtProject;
          private static JTextField txtPhase;

          private static JComboBox cmbReportName;

          private static JComboBox cmbYear;
          private static JComboBox cmbMonth;

          public Sales_Books() {
                    super(title, false, true, true, true);
                    initGUI();
          }

          public Sales_Books(String title) {
                    super(title);
                    initGUI();
          }

          public Sales_Books(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
                    super(title, resizable, closable, maximizable, iconifiable);
                    initGUI();
          }

          private void initGUI() {
                    this.setSize(frameSize);
                    this.setLayout(new BorderLayout(5, 5));
                    this.setMinimumSize(frameSize);
                    this.addComponentListener(new ComponentAdapter() {
                              public void componentResized(final ComponentEvent e) {
                                        super.componentResized(e);
                              }
                    });
                    this.setMinimumSize(new Dimension(620, 440));

                    JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
                    this.add(panMain, BorderLayout.CENTER);
                    panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
                    {
                              {
                                        JXPanel panCenter = new JXPanel(new GridBagLayout());
                                        panMain.add(panCenter, BorderLayout.CENTER);
                                        {
                                                  GridBagConstraints c = new GridBagConstraints();
                                                  c.fill = GridBagConstraints.BOTH;
                                                  c.ipady = 20;

                                                  {
                                                            c.weightx = 1;
                                                            c.weighty = 0.5;

                                                            c.gridx = 0; 
                                                            c.gridy = 0; 

                                                            JPanel panParam1 = new JPanel(new GridBagLayout());
                                                            panCenter.add(panParam1, c);
                                                            panParam1.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
                                                            {
                                                                      GridBagConstraints c_com = new GridBagConstraints();
                                                                      c_com.fill = GridBagConstraints.HORIZONTAL;
                                                                      c_com.ipady = 10;
                                                                      c_com.insets = new Insets(0, 5, 0, 5);

                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 0; 
                                                                                c_com.gridy = 0; 

                                                                                JLabel lblCompany = new JLabel("Company"); 
                                                                                panParam1.add(lblCompany, c_com); 
                                                                                lblCompany.setHorizontalAlignment(JLabel.LEADING);
                                                                                lblCompany.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 1; 
                                                                                c_com.gridy = 0; 

                                                                                {
                                                                                          lookupCompany = new _JLookup(null, "Company");
                                                                                          panParam1.add(lookupCompany);
                                                                                          lookupCompany.setReturnColumn(0);
                                                                                          lookupCompany.setLookupSQL(lookupMethods.getCompany());
                                                                                          lookupCompany.addLookupListener(new LookupListener() {
                                                                                                    public void lookupPerformed(LookupEvent event) {
                                                                                                              Object[] data = ((_JLookup)event.getSource()).getDataSet();
                                                                                                              
                                                                                                              if (data!=null) {
                                                                                                                        lookupCompany.setValue(data[0].toString());
                                                                                                                        txtCompany.setText(data[1].toString());
                                                                                                                        
                                                                                                                        lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
                                                                                                              }
                                                                                                    }
                                                                                          });
                                                                                }
                                                                                lookupCompany.setFont(FncGlobal.sizeTextValue);
                                                                                panParam1.add(lookupCompany, c_com); 
                                                                      }
                                                                      {
                                                                                c_com.weightx = 1.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 2; 
                                                                                c_com.gridy = 0; 

                                                                                txtCompany = new JTextField("");
                                                                                txtCompany.setHorizontalAlignment(JLabel.CENTER);
                                                                                txtCompany.setFont(FncGlobal.sizeTextValue);
                                                                                txtCompany.setEditable(false); 
                                                                                txtCompany.setFocusable(false);
                                                                                panParam1.add(txtCompany, c_com); 
                                                                      }
                                                                      
                                                                      lookupCompany.setValue("02");
                                                                      txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
                                                            }
                                                  }
                                                  {
                                                            c.weightx = 1;
                                                            c.weighty = 0.5;

                                                            c.gridx = 0; 
                                                            c.gridy = 1; 

                                                            JPanel panParam2 = new JPanel(new GridBagLayout());
                                                            panCenter.add(panParam2, c);
                                                            panParam2.setBorder(JTBorderFactory.createTitleBorder("Report Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
                                                            {

                                                                      GridBagConstraints c_com = new GridBagConstraints();
                                                                      c_com.fill = GridBagConstraints.HORIZONTAL;
                                                                      c_com.ipady = 10;
                                                                      c_com.insets = new Insets(0, 5, 0, 5);

                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 0; 
                                                                                c_com.gridy = 0; 

                                                                                JLabel lblCompany = new JLabel("Report Type"); 
                                                                                panParam2.add(lblCompany, c_com); 
                                                                                lblCompany.setHorizontalAlignment(JLabel.LEADING);
                                                                                lblCompany.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 1.5;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 1; 
                                                                                c_com.gridy = 0;                                                                                
                                                                                c_com.gridwidth = 2;


                                                                                String status[] = 
                                                                                          {
                                                                                                              "Annual Sales (Cash/Deferred)",
                                                                                                              "Annual Sales (Installment)",
                                                                                                              "Sales Schedule (All)",
                                                                                                              "Annual Payment"
                                                                                          };

                                                                                cmbReportName = new JComboBox(status);
                                                                                cmbReportName.setSelectedItem(null);
                                                                                cmbReportName.setSelectedIndex(0);      
                                                                                cmbReportName.setFont(FncGlobal.sizeTextValue);
                                                                                panParam2.add(cmbReportName, c_com); 
                                                                      }

                                                            }
                                                  }
                                                  {
                                                            c.weightx = 1;
                                                            c.weighty = 2;

                                                            c.gridx = 0; 
                                                            c.gridy = 2; 

                                                            JPanel panParam3 = new JPanel(new GridBagLayout());
                                                            panCenter.add(panParam3, c);
                                                            panParam3.setBorder(JTBorderFactory.createTitleBorder("Search Option", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
                                                            {
                                                                      GridBagConstraints c_com = new GridBagConstraints();
                                                                      c_com.fill = GridBagConstraints.HORIZONTAL;
                                                                      c_com.ipady = 10;
                                                                      c_com.insets = new Insets(0, 5, 0, 5);

                                                                      //        LINE 1
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 0; 
                                                                                c_com.gridy = 0; 

                                                                                JLabel lblCompany = new JLabel("Project"); 
                                                                                panParam3.add(lblCompany, c_com); 
                                                                                lblCompany.setHorizontalAlignment(JLabel.LEADING);
                                                                                lblCompany.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 1; 
                                                                                c_com.gridy = 0; 

                                                                                {
                                                                                          lookupProject = new _JLookup(null, "Project", 2, 2);
                                                                                          lookupProject.setReturnColumn(0);
                                                                                          lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
                                                                                          lookupProject.addLookupListener(new LookupListener() {
                                                                                                    public void lookupPerformed(LookupEvent event) {
                                                                                                              Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
                                                                                                              
                                                                                                              if (data!=null) {
                                                                                                                        lookupProject.setValue(data[0].toString());
                                                                                                                        txtProject.setText(data[1].toString());
                                                                                                                        
                                                                                                                        lookupPhase.setLookupSQL(lookupMethods.getPhase(lookupProject.getValue()));
                                                                                                              }
                                                                                                    }
                                                                                          });
                                                                                }

                                                                                lookupProject.setFont(FncGlobal.sizeLabel);
                                                                                panParam3.add(lookupProject, c_com); 

                                                                      }
                                                                      {
                                                                                c_com.weightx = 1.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 2; 
                                                                                c_com.gridy = 0;
                                                                                c_com.gridwidth = 2; 

                                                                                txtProject = new JTextField("") ; 
                                                                                txtProject.setHorizontalAlignment(JLabel.CENTER);
                                                                                txtProject.setFont(FncGlobal.sizeTextValue);
                                                                                txtProject.setEditable(false); 
                                                                                panParam3.add(txtProject, c_com); 
                                                                      }

                                                                      //        LINE 2
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 0; 
                                                                                c_com.gridy = 1; 

                                                                                c_com.gridwidth = 1;

                                                                                JLabel lblPhase = new JLabel("Phase"); 
                                                                                panParam3.add(lblPhase, c_com); 
                                                                                lblPhase.setHorizontalAlignment(JLabel.LEADING);
                                                                                lblPhase.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 1; 
                                                                                c_com.gridy = 1; 

                                                                                lookupPhase = new _JLookup(null, "Phase", 2, 2);
                                                                                lookupPhase.setReturnColumn(3);
                                                                                lookupPhase.setLookupSQL(lookupMethods.getPhase(lookupProject.getValue()));       
                                                                                lookupPhase.setFont(FncGlobal.sizeLabel);
                                                                                lookupPhase.addLookupListener(new LookupListener() {
                                                                                          public void lookupPerformed(LookupEvent event) {
                                                                                                    Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
                                                                                                    
                                                                                                    if (data!=null) {
                                                                                                              lookupPhase.setValue(data[0].toString());
                                                                                                              txtPhase.setText(data[1].toString());
                                                                                                    }
                                                                                          }
                                                                                });
                                                                                panParam3.add(lookupPhase, c_com);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 1.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 2; 
                                                                                c_com.gridy = 1;
                                                                                c_com.gridwidth = 2; 

                                                                                txtPhase = new JTextField("") ; 
                                                                                txtPhase.setHorizontalAlignment(JLabel.CENTER);
                                                                                txtPhase.setFont(FncGlobal.sizeTextValue);
                                                                                txtPhase.setEditable(false); 
                                                                                panParam3.add(txtPhase, c_com);
                                                                      }

                                                                      //        LINE 3
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 0; 
                                                                                c_com.gridy = 2; 
                                                                                c_com.gridwidth = 1; 

                                                                                JLabel lblYear = new JLabel("Year"); 
                                                                                panParam3.add(lblYear, c_com); 
                                                                                lblYear.setHorizontalAlignment(JLabel.LEADING);
                                                                                lblYear.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.5;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 1; 
                                                                                c_com.gridy = 2; 

                                                                                String status[] = {
                                                                                                    "2021",
                                                                                                    "2020",
                                                                                                    "2019",
                                                                                                    "2018",
                                                                                                    "2017",
                                                                                                    "2016",
                                                                                                    "2015"
                                                                                };                                
                                                                                cmbYear = new JComboBox(status);
                                                                                cmbYear.setSelectedItem(null);    
                                                                                cmbYear.setSelectedIndex(0);  
                                                                                cmbYear.setFont(FncGlobal.sizeTextValue);
                                                                                panParam3.add(cmbYear, c_com); 

                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.25;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 2; 
                                                                                c_com.gridy = 2; 

                                                                                JLabel lblMonth = new JLabel("Month"); 
                                                                                panParam3.add(lblMonth, c_com); 
                                                                                lblMonth.setHorizontalAlignment(JLabel.CENTER);
                                                                                lblMonth.setFont(FncGlobal.sizeLabel);
                                                                      }
                                                                      {
                                                                                c_com.weightx = 0.5;
                                                                                c_com.weighty = 1;

                                                                                c_com.gridx = 3; 
                                                                                c_com.gridy = 2; 

                                                                                String status[] = {
                                                                                                    "All",
                                                                                                    "January",
                                                                                                    "February",
                                                                                                    "March",
                                                                                                    "April",
                                                                                                    "May",
                                                                                                    "June",
                                                                                                    "July",
                                                                                                    "August",
                                                                                                    "September",
                                                                                                    "October",
                                                                                                    "November",
                                                                                                    "December"

                                                                                };                                                
                                                                                cmbMonth = new JComboBox(status);
                                                                                cmbMonth.setSelectedItem(null);
                                                                                cmbMonth.setSelectedIndex(0); 
                                                                                cmbMonth.setFont(FncGlobal.sizeTextValue);
                                                                                panParam3.add(cmbMonth, c_com); 

                                                                      }                                                                      

                                                            }
                                                  }                                                  
                                        }
                              }
                              {
                                        JPanel panEnd = new JPanel(new GridLayout(1, 3, 5, 5)); 
                                        panMain.add(panEnd, BorderLayout.PAGE_END); 
                                        panEnd.setPreferredSize(new Dimension(0, 30));
                                        {
                                                  {
                                                            panEnd.add(new JLabel(""));                                                             
                                                            panEnd.add(new JLabel(""));
                                                  }
                                                  {
                                                            JButton btnPreview = new JButton("Preview");
                                                            panEnd.add(btnPreview);
                                                            btnPreview.setFont(FncGlobal.sizeControls);
                                                            btnPreview.addActionListener(new ActionListener() {
                                                                      public void actionPerformed(ActionEvent arg0) {
                                                                                preview();
                                                                      }
                                                            });
                                                  }
                                        }
                              }
                    }
          }

          public static void main (String args[]) {
                    Sales_Books sales = new Sales_Books(); 

                    JFrame frame = new JFrame("Tae"); 
                    frame.setContentPane(sales);
                    frame.setSize(620, 440);
                    frame.setVisible(true);

          }

          private void preview() {
                    String criteria = "";
                    String strProjID = "";
                    String strPhaseID = "";
                    String strBOI = "";

                    if (cmbReportName.getSelectedIndex()==0) {
                              criteria = "Annual Sales (Cash/Deferred)";
                    } else if (cmbReportName.getSelectedIndex()==1) {
                              criteria = "Annual Sales (Installment)";
                    } else if (cmbReportName.getSelectedIndex()==2) {
                              criteria = "Sales Schedule (All)";
                    } else if (cmbReportName.getSelectedIndex()==3) {
                              criteria = "Annual Payment";
                    }    

                    String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
                    String period_id = "";

                    try {
                              if (lookupProject.getValue().equals("")) {
                                        strProjID = "All";
                              } else {

                              }
                    } catch (NullPointerException e) {
                              strProjID = "All";
                    }

                    try {
                              if (lookupPhase.getValue().equals("")) {
                                        strPhaseID = "All";
                              } else {

                              }                              
                    } catch (NullPointerException e) {
                              strPhaseID = "All";
                    }

                    if (cmbMonth.getSelectedIndex()==0) {
                              period_id = "00";
                    }

                    if (cmbMonth.getSelectedIndex()==1) {
                              period_id = "01";
                    }

                    if (cmbMonth.getSelectedIndex()==2) {
                              period_id = "02";
                    }

                    if (cmbMonth.getSelectedIndex()==3) {
                              period_id = "03";
                    }

                    if (cmbMonth.getSelectedIndex()==4) {
                              period_id = "04";
                    }

                    if (cmbMonth.getSelectedIndex()==5) {
                              period_id = "05";
                    }

                    if (cmbMonth.getSelectedIndex()==6) {
                              period_id = "06";
                    }

                    if (cmbMonth.getSelectedIndex()==7) {
                              period_id = "07";
                    }

                    if (cmbMonth.getSelectedIndex()==8) {
                              period_id = "08";
                    }

                    if (cmbMonth.getSelectedIndex()==9) {
                              period_id = "09";
                    }

                    if (cmbMonth.getSelectedIndex()==10) {
                              period_id = "10";
                    }

                    if (cmbMonth.getSelectedIndex()==11) {
                              period_id = "11";
                    }

                    if (cmbMonth.getSelectedIndex()==12) {
                              period_id = "12";
                    }

                    strBOI = FncGlobal.GetString("select coalesce(a.boi_registration_no,'') from mf_sub_project where proj_id = '"+strProjID+"' AND status_id = 'A'; "); 

                    System.out.println("");
                    System.out.println("lookupCompany.getValue(): "+lookupCompany.getValue());
                    System.out.println("strProjID: "+strProjID);
                    System.out.println("txtCompany.getText(): "+strProjID);
                    System.out.println("Name: "+UserInfo.FirstName + " " +UserInfo.LastName);
                    System.out.println("txtPhase.getText(): "+txtPhase.getText());
                    System.out.println("strPhaseID: "+strPhaseID);
                    System.out.println("cmbMonth.getSelectedItem(): "+cmbMonth.getSelectedItem());
                    System.out.println("cmbYear.getSelectedItem(): "+cmbYear.getSelectedItem());
                    System.out.println("period_id: "+period_id);
                    System.out.println("txtProject.getText().toUpperCase(): "+txtProject.getText().toUpperCase());
                    System.out.println("strBOI: "+strBOI);
                    
                    Map<String, Object> mapParameters = new HashMap<String, Object>();
                    mapParameters.put("co_id", lookupCompany.getValue());
                    mapParameters.put("proj_id", strProjID);
                    mapParameters.put("company", txtCompany.getText());
                    mapParameters.put("prepared_by", UserInfo.FirstName + " " +UserInfo.LastName);
                    mapParameters.put("prepared_name", "");
                    mapParameters.put("phase", txtPhase.getText());      
                    mapParameters.put("phase_no", strPhaseID);      
                    mapParameters.put("month", cmbMonth.getSelectedItem());     
                    mapParameters.put("year", cmbYear.getSelectedItem()); 
                    mapParameters.put("period_id", period_id);        
                    mapParameters.put("proj_name", txtProject.getText().toUpperCase());
                    mapParameters.put("boi_reg_no", strBOI);      

                    if (cmbReportName.getSelectedIndex()==0) {
                              FncReport.generateReport("/Reports/rptAnnualSales_cash.jasper", reportTitle, "", mapParameters);
                    } else if (cmbReportName.getSelectedIndex()==1) {
                              mapParameters.put("period", cmbMonth.getSelectedIndex()+1); 
                              FncReport.generateReport("/Reports/rptAnnualSales_installment.jasper", reportTitle, "", mapParameters);
                    } else if (cmbReportName.getSelectedIndex()==2) {
                              mapParameters.put("period", cmbMonth.getSelectedIndex()+1); 
                              FncReport.generateReport("/Reports/rptSalesSchedule_all.jasper", reportTitle, "", mapParameters);
                    } else if (cmbReportName.getSelectedIndex()==3) {
                              FncReport.generateReport("/Reports/rptAnnual_Payment.jasper", reportTitle, "", mapParameters);
                    }
          }
}
