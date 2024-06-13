package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import System.DataChangeRequest;
import interfaces._GUI;

public class User_Dcrf_Checker extends JXPanel implements _GUI,ActionListener {
          private Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);

          private static final long serialVersionUID = -6374262988407254345L;

          private JLabel[] lblUser;
          private JLabel[] lblStatus;
          private JButton[] btnPrev;
          private JButton[] btnConformed;
          private JScrollPane scrollUser;

          private JPanel pnlMain;
          private JPanel pnlScrollLbl;
          static JPanel pnlTrueMain;
          private JPanel mainCenter;
          private JPanel pnlPrev;
          private JPanel pnlConformed;

          private TimerTask tmr;
          private Timer timerExecute;

          private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
          private Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);
          
          private Color colorHeader = new Color(91, 149, 249);
          private Color color1 = new Color(207, 224, 254);
          private Color color2 = Color.WHITE; 
          
          public User_Dcrf_Checker(){
                    initGUI();
          }

          public User_Dcrf_Checker (boolean isDoubleBuffered){
                    super(isDoubleBuffered);
                    initGUI();
          } 
          public User_Dcrf_Checker(LayoutManager layout){
                    super(layout);
                    initGUI();
          }
          public User_Dcrf_Checker(LayoutManager layout, boolean isDoubleBuffered){
                    super(layout,isDoubleBuffered);
                    initGUI();
          }
          public void initGUI() {
                    this.setLayout(new BorderLayout(1, 1));
                    this.setOpaque(true);
                    this.setBorder(empty);
                    this.setBackground(new Color(0, 0, 0, 0));

                    {
                              pnlTrueMain = new JPanel(new BorderLayout());
                              this.add(pnlTrueMain);
                              pnlTrueMain.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                              pnlTrueMain.setPreferredSize(new Dimension(400, 200));
                              pnlTrueMain.setOpaque(false);
                              {
                                        {
                                                  JPanel panHeader = new JPanel(new BorderLayout(0, 0)); 
                                                  pnlTrueMain.add(panHeader, BorderLayout.PAGE_START);
                                                  panHeader.setPreferredSize(new Dimension(0, 40));
                                                  panHeader.setBackground(colorHeader);
                                                  panHeader.setOpaque(true);
                                                  {
                                                            {
                                                                      JPanel panLabels = new JPanel(new BorderLayout(0, 0)); 
                                                                      panHeader.add(panLabels, BorderLayout.CENTER);
                                                                      panLabels.setOpaque(false);
                                                                      {
                                                                                {
                                                                                          JLabel lblDCRF = new JLabel("DCRF#"); 
                                                                                          panLabels.add(lblDCRF, BorderLayout.LINE_START);
                                                                                          lblDCRF.setPreferredSize(new Dimension(60, 0));
                                                                                          lblDCRF.setOpaque(false);
                                                                                          lblDCRF.setHorizontalAlignment(JLabel.CENTER);
                                                                                          lblDCRF.setBorder(_LINE_BORDER);
                                                                                }
                                                                                {
                                                                                          JLabel lblStatus = new JLabel("Status"); 
                                                                                          panLabels.add(lblStatus, BorderLayout.CENTER);
                                                                                          lblStatus.setOpaque(false);
                                                                                          lblStatus.setHorizontalAlignment(JLabel.CENTER);
                                                                                          lblStatus.setBorder(_LINE_BORDER);
                                                                                }                                                                                
                                                                      }
                                                            }
                                                            {
                                                                      JLabel lblControls = new JLabel(""); 
                                                                      panHeader.add(lblControls, BorderLayout.LINE_END);
                                                                      lblControls.setPreferredSize(new Dimension(200, 0));
                                                                      lblControls.setOpaque(false);
                                                                      lblControls.setHorizontalAlignment(JLabel.CENTER);
                                                                      lblControls.setBorder(_LINE_BORDER);
                                                            }
                                                  }
                                        }
                                        {
                                                  pnlMain = new JPanel(new BorderLayout(0, 0));
                                                  pnlMain.setBorder(new EmptyBorder(5, 0, 5, 0));
                                                  pnlMain.setOpaque(false);
                                                  repeat();

                                                  scrollUser = new JScrollPane(pnlMain);
                                                  scrollUser.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                                  pnlTrueMain.add(scrollUser, BorderLayout.CENTER);
                                                  scrollUser.setOpaque(false);
                                        }
                              }
                              ExecuteUSB(); 
                    }


          }

          private void repeat() {
                    pgSelect db = new pgSelect();
                    db.select("select * \n" + 
                    		"from view_dcrf_monitoring_users('"+UserInfo.EmployeeCode+"'); ");

                    lblUser = new JLabel[db.getRowCount()];
                    lblStatus = new JLabel[db.getRowCount()];
                    btnPrev = new JButton[db.getRowCount()];
                    btnConformed = new JButton[db.getRowCount()];

                    int row_count = db.getRowCount();

                    if (row_count<= 6) {
                              row_count = 6;
                    } else {
                              row_count = db.getRowCount();
                    }

                    pnlMain.removeAll();
                    {
                              {
                                        pnlScrollLbl = new JPanel(new BorderLayout(0, 0)); 
                                        pnlMain.add(pnlScrollLbl, BorderLayout.CENTER);
                                        {
                                                  {
                                                            JPanel panDCRF = new JPanel(new GridLayout(row_count, 1, 0, 0)); 
                                                            pnlScrollLbl.add(panDCRF, BorderLayout.LINE_START);
                                                            panDCRF.setPreferredSize(new Dimension(60, 0));
                                                            {
                                                                      for (int x = 0; x<db.getRowCount(); x++){
                                                                                lblUser[x] = new JLabel(db.getResult()[x][0].toString());
                                                                                panDCRF.add(lblUser[x]);
                                                                                lblUser[x].setHorizontalAlignment(JLabel.CENTER);
                                                                                lblUser[x].setFont(font11bold);
                                                                                
                                                                                lblUser[x].setOpaque(true);
                                                                                lblUser[x].setBackground(alternatingColor(x));
                                                                                lblUser[x].setForeground(Color.DARK_GRAY);
                                                                      }
                                                            }
                                                  }
                                                  {
                                                            JPanel panStatus = new JPanel(new GridLayout(row_count, 1, 0, 0)); 
                                                            pnlScrollLbl.add(panStatus, BorderLayout.CENTER);
                                                            {
                                                                      for (int x = 0; x<db.getRowCount(); x++){
                                                                                lblStatus[x] = new JLabel(db.getResult()[x][1].toString());
                                                                                panStatus.add(lblStatus[x]);
                                                                                lblStatus[x].setFont(font11);
                                                                                
                                                                                lblStatus[x].setOpaque(true);
                                                                                lblStatus[x].setBackground(alternatingColor(x));
                                                                                lblStatus[x].setForeground(Color.DARK_GRAY);
                                                                      }
                                                            }
                                                  }
                                        }
                              }
                              {
                                        mainCenter = new JPanel(new GridLayout(1, 2, 0, 0));
                                        pnlMain.add(mainCenter, BorderLayout.LINE_END);
                                        mainCenter.setPreferredSize(new Dimension(160, 0));
                                        {
                                                  int row = db.getRowCount();
                                                  
                                                  {
                                                            pnlPrev = new JPanel(new GridLayout(row_count, 1, 0, 0));
                                                            mainCenter.add(pnlPrev);
                                                            {
                                                                      for(int x = 0; x<row; x++) {
                                                                                JPanel panRow = new JPanel(new BorderLayout(0, 0)); 
                                                                                pnlPrev.add(panRow);
                                                                                panRow.setOpaque(true);
                                                                                panRow.setBackground(alternatingColor(x));
                                                                                {
                                                                                          btnPrev[x] = new JButton("Preview");
                                                                                          panRow.add(btnPrev[x], BorderLayout.CENTER);
                                                                                          btnPrev[x].setActionCommand("Preview");
                                                                                          btnPrev[x].addActionListener(this);
                                                                                          btnPrev[x].setName(db.getResult()[x][0].toString());
                                                                                          btnPrev[x].setFont(font11);
                                                                                }
                                                                      }
                                                            }
                                                  }
                                                  {
                                                            pnlConformed = new JPanel(new GridLayout(row_count, 1, 0, 0));
                                                            mainCenter.add(pnlConformed);
                                                            {
                                                                      for (int x = 0; x<row; x++) {
                                                                                JPanel panRow = new JPanel(new BorderLayout(0, 0)); 
                                                                                pnlConformed.add(panRow);
                                                                                panRow.setOpaque(true);
                                                                                panRow.setBackground(alternatingColor(x));
                                                                                {
                                                                                          btnConformed[x] = new JButton("Conform");
                                                                                          panRow.add(btnConformed[x]);
                                                                                          btnConformed[x].setActionCommand("Conformed");
                                                                                          btnConformed[x].addActionListener(this);
                                                                                          btnConformed[x].setName(db.getResult()[x][0].toString());
                                                                                          btnConformed[x].setFont(font11);
                                                                                          btnConformed[x].setEnabled((db.getResult()[x][1].toString().equals("FOR CHECKING BY CREATOR")));
                                                                                }
                                                                      }
                                                            }
                                                  }
                                        }
                              }
                    }

                    pnlMain.repaint();
                    pnlMain.revalidate();
                    System.out.println("Dumaan dito");
          }

          private void preview(String dcrf){	
                    Integer dcrf_no = Integer.parseInt(dcrf);
                    String criteria = "DCRF";		
                    String reportTitle = String.format("%s (%s)", DataChangeRequest.title.replace(" Report", ""), criteria.toUpperCase());
                    Map<String, Object> mapParameters = new HashMap<String, Object>();
                    mapParameters.put("dcrf_no",dcrf_no);
                    FncReport.generateReport("/Reports/rptDCRF_preview.jasper", reportTitle,DataChangeRequest.company, mapParameters);
          }

          private void conformed(String dcrf){
                    Integer dcrf_no = Integer.parseInt(dcrf);
                    pgUpdate db = new pgUpdate();
                    String query = "update rf_dcrf_header \n" +
                                        "set date_conformed = '"+FncGlobal.getDateToday()+"', status = 'COMPLETED', conformed_by = '"+UserInfo.EmployeeCode+"' \n" +
                                        "where dcrf_no = '"+dcrf_no+"'; ";
                    db.executeUpdate(query, true);
                    db.commit();		
          }

          public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("Preview")) {
                              preview((((JButton) e.getSource()).getName()));
                    }

                    if (e.getActionCommand().equals("Conformed")) {
                              conformed((((JButton) e.getSource()).getName()));
                              JOptionPane.showMessageDialog(this, "dcrf#: \t"+ (((JButton) e.getSource()).getName())+"\t has been conformed");
                              this.removeAll();
                              initGUI();
                    }
          }

          private void ExecuteUSB() {
                    tmr = new TimerTask() {
                              public void run() {
                                        repeat(); 
                              }
                    };

                    timerExecute = new Timer();
                    long delay = 0;
                    long intevalPeriod = 20 * 1000; 

                    timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
          }
          
          private Color alternatingColor(Integer intRow) {
                    Color color = null; 

                    if (intRow%2==0) {
                              color = color1; 
                    } else {
                              color = color2; 
                    }
                              
                    return  color; 
          }
}
