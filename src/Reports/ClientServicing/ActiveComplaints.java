package Reports.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components._JInternalFrame;
import components._JXTextField;

public class ActiveComplaints extends _JInternalFrame {

          private static final long serialVersionUID = -8578733666062340293L;
          private static Dimension frameSize = new Dimension(620, 140);
          private static String title = "Contractors Credit Balance";
          
          private JCheckBox chkProject;
          private _JLookup lookupProject;
          private JTextField txtProject; 
          
          public ActiveComplaints() {
                    super(title, false, true, false, false);
                    initGUI();
          }

          public ActiveComplaints(String title) {
                    super(title);
                    initGUI();
          }

          public ActiveComplaints(String title, boolean resizable, boolean closable, boolean maximizable,
                              boolean iconifiable) {
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
                    this.setMinimumSize(frameSize);

                    JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
                    this.add(panMain, BorderLayout.CENTER);
                    panMain.setBorder(new EmptyBorder(10, 10, 10, 10));
                    {
                              {
                                        lookupProject = new _JLookup(null, "Select project", 0);
                                        panMain.add(lookupProject, BorderLayout.LINE_START);
                                        lookupProject.setPreferredSize(new Dimension(150, 0));
                                        lookupProject.setLookupSQL(lookupMethods.getProject(""));
                                        lookupProject.addLookupListener(new LookupListener() {
                                                  public void lookupPerformed(LookupEvent event) {
                                                            Object[] data = ((_JLookup)event.getSource()).getDataSet();
                                                            
                                                            if(data != null){
                                                                      lookupProject.setValue(data[0].toString());
                                                                      txtProject.setText(data[1].toString());
                                                            }
                                                  }
                                        });
                                        lookupProject.setFont(FncGlobal.sizeTextValue);
                              }
                              {
                                        txtProject = new _JXTextField("");
                                        panMain.add(txtProject, BorderLayout.CENTER);
                                        txtProject.setHorizontalAlignment(JTextField.CENTER);
                                        txtProject.setEditable(false);
                                        txtProject.setFont(FncGlobal.sizeTextValue);
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
                    ActiveComplaints sales = new ActiveComplaints(); 

                    JFrame frame = new JFrame("Tae"); 
                    frame.setContentPane(sales);
                    frame.setSize(620, 140);
                    frame.setVisible(true);

          }
          
          private void preview() {
                    Map<String, Object> mapComplaints = new HashMap<String, Object>();
                    mapComplaints.put("proj_id", lookupProject.getValue());
                    mapComplaints.put("prepared_by", UserInfo.FullName);
                    mapComplaints.put("filterproject", lookupProject.getValue()!=null);
                    
                    FncReport.generateReport("/Reports/rptClientComplaints.jasper", "List of Active Complaints", mapComplaints);
          }
}
