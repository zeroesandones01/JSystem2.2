package Accounting.Commissions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import Database.pgSelect;
import Database.pgUpdate;

public class Qualify_Commission {

	public static String PBL_ID 	= "";
	public static String SeqNo 		= "";
	public static String CommType	= "";
	public static String SchemeID	= "";
	public static Integer NoOfCond	= 0;
	public static String CondID		= "";
	public static String FuncID		= "";
	public static String Co_ID		= "";

	public static String strUnitID; 
	public static String strSeqNo;
	public static String strCommType;
	public static String strCoID;

	public static void qualifyComm(String pbl_id, String seq_no, String comm_type){

		PBL_ID 		= pbl_id;
		SeqNo 		= seq_no;
		SchemeID	= getCommSchemeID();
		CommType	= comm_type;
		CondID		= getCommConditionID();		
		NoOfCond	= getNumberofCondition()-1;
		int x 		= 0;

		System.out.printf("SchemeID : " + SchemeID + "\n" );
		System.out.printf("CommType : " + CommType + "\n");
		System.out.printf("CondID 	: " + CondID+ "\n" );
		System.out.printf("NoOfCond : " + NoOfCond + "\n");

		while (x<=NoOfCond) {

			Object[][] func = getFunctionIDs();	

			System.out.printf("FuncID : " + "(String) func[0]["+x+"]" + "\n");
			FuncID = (String) func[x][0];			

			if (FuncID.equals("001")||FuncID.equals("042")) { 
				if (unit_haspaidResFee()==true) {System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid required Reservation Fee"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { 
				if (unit_haspaidXDP(2.00)==true) {System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 2nd Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("003")) { 
				if (isDocsComplete()==true) {System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet Docs Complete"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("004")) { 
				if (unit_IssuedPDCFor6DPAnd36MAIn60Days()==true) {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit has not issued PDCs for DPs & 36 MAs w/in 60 days after OR"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("005")||FuncID.equals("050")) { 
				if (unit_haspaidXDP(4.00)==true) {System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 4th Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("006")) { 
				if (unit_isfullDP()==true) {System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 006 - Full DP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet Full Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("007")) { 
				if (unit_hassubmittedCTS_and_DOR()==true) {System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "CTS (Signed) not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("008")) { 
				if (unit_prin_perc_paid(.25)==true) {System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("009")) { 
				if (unit_issuedpdcfor75percentoftcp()==true) {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet issued PDC for 75 Percent of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("010")) { 
				if (unit_issuedpdcfor36main30days()==true) {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit has not issued PDC for 36 MA in 30 days"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("011")) { 
				if (unit_hasSubmittedWaiveronTransferofTitle()==true) {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Waiver on Transfer of Title not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("014")||FuncID.equals("032")) { 
				if (unit_haspaidXDP(3.00)==true) {System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 3rd Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("015")) { 
				if (unit_haspaidXDP(5.00)==true) {System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 5th Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("016")|FuncID.equals("033")) { 
				if (unit_haspaidXDP(6.00)==true) {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 6th Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("017")||FuncID.equals("034")) { 
				if (unit_prin_perc_paid(.05)==true) {System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("018")||FuncID.equals("035")) { 
				if (unit_prin_perc_paid(.10)==true) {System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("019")) { 
				if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Signed SPA not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("020")) { 
				if (unit_prin_perc_paid(.50)==true) {System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("021")) { 
				if (unit_prin_perc_paid(.75)==true) {System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("022")) { 
				if (unit_prin_perc_paid(1.00)==true) {System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("023")) { 
				if (unit_paid_first_ma()==true) {System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid 1st MA"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("024")) { 
				if (unit_haspaidXMA(2.0)==true) {System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid 2nd MA"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("025")) { 
				if (unit_issuedpdcfor6dpand24ma()==true) {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet issued PDC for 6DP and 24MA"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("026")) { 
				if (unit_haspaidXDP(8.00)==true) {System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 026 - Paud 8th DP/CO : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet paid 8th Cash Outlay / DP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("027")) { 
				if (unit_issuedpdcfor24masin60days()==true) {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Unit not yet issued PDC for 24MAs in 60 days"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("029")) { 
				if (unit_hassubmitteditr()==true) {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "ITR/EC/CEC/AFS not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("030")) { 
				if (unit_hasSubmittedMRI()==true) {System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "MRI not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("031")) { 
				if (unit_hassubmittedmsvs()==true) {System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "MSVS not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("036")) { 
				if (unit_prin_perc_paid(.15)==true) {System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("037")) { 
				if (unit_prin_perc_paid(.20)==true) {System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet paid at least 25% of TCP"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("038")) { 
				if (unit_issuedpdcfor5dpand24ma()==true) {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet issued PDC for 5DP and 24MA"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("039")) { 
				if (unit_hassubmitted_tin()==true) {System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "TIN not yet submitted"+ "\n", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission"+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("040")) { 
				if (unit_issuedpdcfor24mas()==true) {System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n"); 
					JOptionPane.showMessageDialog(null, "Not yet issued PDC for 24MAs", "Information",JOptionPane.INFORMATION_MESSAGE); 
					JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);				
					break; }}

			else if (FuncID.equals("041")) { 
				if (isSignedSCDComplete()==true) {System.out.printf("FuncID 041 - Submitted Signed SCD : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 041 -  Submitted Signed SCD : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Signed SCD not yet submitted", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}			

			else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")||
					FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
				if (unit_isofficiallyreserved()==true) {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
				else
				{System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Account not yet Officially Reserved", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("046")) { 
				if (unit_isfullsettledorloantakeout()==true) {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Account not yet Loan Take Out / Full Settled", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("047")) { 
				if (unit_isCompleteMinorDocs()==true) {System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Account has not yet Completed Minor Docs.", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("048")) { 
				if (unit_issuedpdcfor12mas()==true) {System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Account has not yet issued PDC for 12MAs", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("051")) { 
				if (unit_hasSubmittedPayslip()==true) {System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Payslip not yet submitted.", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("052")) { 
				if (unit_haspaidpercDP(0.50)==true) {System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 50% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("053")) { 
				if (unit_haspaidpercDP(1.00)==true) {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid 100% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("055")) { 
				if (unit_issuedpdcfor9dpand36ma()==true) {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet issued PDC for 9DP and 36MA", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("056")) { 
				if (unit_haspaidXDP(9.00)==true) {System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid 9th Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("057")) { 
				if (unit_haspaidXDP(12.00)==true) {System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid 12th Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("060")) { 
				if (unit_haspaidpercDP(0.75)==true) {System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 75% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("062")) { 
				if (unit_haspaidpercDP(0.25)==true) {System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 25% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("063")) { 
				if (unit_haspaidpercDP(0.90)==true) {System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 25% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("064")) { 
				if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 30% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("065")) { 
				if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 60% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("066")) { 
				if (unit_haspaidpercDP(0.90)==true) {System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 90% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("068")) { 
				if (unit_haspaidpercDP(0.65)==true) {System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 65% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("072")) { 
				if (unit_haspaidpercDP(0.70)==true) {System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); 
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 70% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else if (FuncID.equals("074")) { 
				if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n");
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 30% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}


			else if (FuncID.equals("075")) { 
				if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 075 - Paid 60perc DP/CO: TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n");
				JOptionPane.showMessageDialog(null, "Unit not yet paid at least 60% Cash Outlay / DP", "Information",JOptionPane.INFORMATION_MESSAGE); 
				JOptionPane.showMessageDialog(null, "Account not yet qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
				break; }}

			else {System.out.printf("FuncID Not Found!"+ "\n\n"); }


			if (x==NoOfCond) {JOptionPane.showMessageDialog(null, "Account qualified for Commission", "Information", JOptionPane.INFORMATION_MESSAGE);
			break;}

			x++;	
		}
	}

	public static Boolean commQualified(String pbl_id, String seq_no, String func_id){

		PBL_ID 		= pbl_id;
		SeqNo 		= seq_no;
		FuncID		= func_id;
		Boolean commQualified = false;

		if (FuncID.equals("001")||FuncID.equals("042")) { 
			if (unit_haspaidResFee()==true) { commQualified = true; System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); }}

		else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { 
			if (unit_haspaidXDP(2.00)==true) {commQualified = true;System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); }}

		else if (FuncID.equals("003")) { 
			if (isDocsComplete()==true) {commQualified = true;System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); }}

		else if (FuncID.equals("004")) { 
			if (unit_IssuedPDCFor6DPAnd36MAIn60Days()==true) {commQualified = true;System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n");  }}

		else if (FuncID.equals("005")||FuncID.equals("050")) { 
			if (unit_haspaidXDP(4.00)==true) {commQualified = true;System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); }}

		else if (FuncID.equals("006")) { 
			if (unit_isfullDP()==true) {commQualified = true;System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 006 - Full DP : False"+ "\n"); }}

		else if (FuncID.equals("007")) { 
			if (unit_hassubmittedCTS_and_DOR()==true) {commQualified = true;System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); }}

		else if (FuncID.equals("008")) { 
			if (unit_prin_perc_paid(.25)==true) {commQualified = true;System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
			else {
				System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n");}}

		else if (FuncID.equals("009")) { 
			if (unit_issuedpdcfor75percentoftcp()==true) {commQualified = true;System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); }}

		else if (FuncID.equals("010")) { 
			if (unit_issuedpdcfor36main30days()==true) {commQualified = true;System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); }}

		else if (FuncID.equals("011")) { 
			if (unit_hasSubmittedWaiveronTransferofTitle()==true) {commQualified = true;System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n");  }}

		else if (FuncID.equals("014")||FuncID.equals("032")) { 
			if (unit_haspaidXDP(3.00)==true) {commQualified = true;System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n");  }}

		else if (FuncID.equals("015")) { 
			if (unit_haspaidXDP(5.00)==true) {commQualified = true;System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 	 }}

		else if (FuncID.equals("016")|FuncID.equals("033")) { 
			if (unit_haspaidXDP(6.00)==true) {commQualified = true;System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("017")||FuncID.equals("034")) { 
			if (unit_prin_perc_paid(.05)==true) {commQualified = true;System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("018")||FuncID.equals("035")) { 
			if (unit_prin_perc_paid(.10)==true) {commQualified = true;System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("019")) { 
			if (unit_hassubmittedsignedspa()==true) {commQualified = true;System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); }}

		else if (FuncID.equals("020")) { 
			if (unit_prin_perc_paid(.50)==true) {commQualified = true;System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("021")) { 
			if (unit_prin_perc_paid(.75)==true) {commQualified = true;System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n");  }}

		else if (FuncID.equals("022")) { 
			if (unit_prin_perc_paid(1.00)==true) {commQualified = true;System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("023")) { 
			if (unit_paid_first_ma()==true) {commQualified = true;System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); }}

		else if (FuncID.equals("024")) { 
			if (unit_haspaidXMA(2.0)==true) {commQualified = true;System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n");}}

		else if (FuncID.equals("025")) { 
			if (unit_issuedpdcfor6dpand24ma()==true) {commQualified = true;System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); }}

		else if (FuncID.equals("026")) { 
			if (unit_haspaidXDP(8.00)==true) {commQualified = true;System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 026 - Paud 8th DP/CO : False"+ "\n"); 		}}

		else if (FuncID.equals("027")) { 
			if (unit_issuedpdcfor24masin60days()==true) {commQualified = true;System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 	}}

		else if (FuncID.equals("029")) { 
			if (unit_hassubmitteditr()==true) {commQualified = true;System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); }}

		else if (FuncID.equals("030")) { 
			if (unit_hasSubmittedMRI()==true) {commQualified = true;System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); }}

		else if (FuncID.equals("031")) { 
			if (unit_hassubmittedmsvs()==true) {commQualified = true;System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); }}

		else if (FuncID.equals("036")) { 
			if (unit_prin_perc_paid(.15)==true) {commQualified = true;System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n");}}

		else if (FuncID.equals("037")) { 
			if (unit_prin_perc_paid(.20)==true) {commQualified = true;System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 	 }}

		else if (FuncID.equals("038")) { 
			if (unit_issuedpdcfor5dpand24ma()==true) {commQualified = true;System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); }}

		else if (FuncID.equals("039")) { 
			if (unit_hassubmitted_tin()==true) {commQualified = true;System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 	}}

		else if (FuncID.equals("040")) { 
			if (unit_issuedpdcfor24mas()==true) {commQualified = true;System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n");  }}

		else if (FuncID.equals("041")) { 
			if (isSignedSCDComplete()==true) {commQualified = true;System.out.printf("FuncID 041 - Has Submitted Signed SCD : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 041 - Has Submitted Signed SCD : False"+ "\n"); }}			

		else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")||
				FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
			if (unit_isofficiallyreserved()==true) {commQualified = true;System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); }}

		else if (FuncID.equals("046")) { 
			if (unit_isfullsettledorloantakeout()==true) {commQualified = true;System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n");  }}

		else if (FuncID.equals("047")) { 
			if (unit_isCompleteMinorDocs()==true) {commQualified = true;System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); }}

		else if (FuncID.equals("048")) { 
			if (unit_issuedpdcfor12mas()==true) {commQualified = true;System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n");  }}

		else if (FuncID.equals("051")) { 
			if (unit_hasSubmittedPayslip()==true) {commQualified = true;System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n");  }}

		else if (FuncID.equals("052")) { 
			if (unit_haspaidpercDP(0.50)==true) {commQualified = true;System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("053")) { 
			if (unit_haspaidpercDP(1.00)==true) {commQualified = true;System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n");  }}

		else if (FuncID.equals("055")) { 
			if (unit_issuedpdcfor9dpand36ma()==true) {commQualified = true;System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n");  }}

		else if (FuncID.equals("056")) { 
			if (unit_haspaidXDP(9.00)==true) {commQualified = true;System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("057")) { 
			if (unit_haspaidXDP(12.00)==true) {commQualified = true;System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n");  }}

		else if (FuncID.equals("060")) { 
			if (unit_haspaidpercDP(0.75)==true) {commQualified = true;System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("062")) { 
			if (unit_haspaidpercDP(0.25)==true) {commQualified = true;System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n");  }}

		else if (FuncID.equals("063")) { 
			if (unit_haspaidpercDP(0.90)==true) {commQualified = true;System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("064")) { 
			if (unit_haspaidpercDP(0.30)==true) {commQualified = true;System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("065")) { 
			if (unit_haspaidpercDP(0.60)==true) {commQualified = true;System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("066")) { 
			if (unit_haspaidpercDP(0.90)==true) {commQualified = true;System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("068")) { 
			if (unit_haspaidpercDP(0.65)==true) {commQualified = true;System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("072")) { 
			if (unit_haspaidpercDP(0.70)==true) {commQualified = true;System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("074")) { 
			if (unit_haspaidpercDP(0.30)==true) {commQualified = true;System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("075")) { 
			if (unit_haspaidpercDP(0.60)==true) {commQualified = true;System.out.printf("FuncID 075 - Paid 60perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("076")) { //last checked : 09-23-2016
			if (unit_hasBankLOG()==true) {commQualified = true; System.out.printf("FuncID 076 - Unit has Bank LOG or NOA : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 076 -  Unit has Bank LOG or NOA : False"+ "\n"); 
			}}

		else if (FuncID.equals("077")) { //last checked : 09-23-2016
			if (unit_hasSignedBankLOG()==true) {commQualified = true; System.out.printf("FuncID 077 - Unit has Signed Bank LOG or NOA : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 077 -  Unit has Signed Bank LOG or NOA : False"+ "\n"); 
			}}		  

		else if (FuncID.equals("078")) { //last checked : 09-23-2016
			if (unit_isLoanTakeout()==true) {commQualified = true; System.out.printf("FuncID 078 - Loan Take Out (Bank) / Full Settled : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 078 -  Loan Take Out (Bank) / Full Settled : False"+ "\n"); 
			}}

		else if (FuncID.equals("079")) { //last checked : 09-23-2016
			if (unit_hasDOAS()==true) {commQualified = true; System.out.printf("FuncID 079 - Unit Has Signed DOAS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 079 - Unit Has Signed DOAS : False"+ "\n"); 
			}}

		else if (FuncID.equals("080")) { //added : 04-06-2017
			if (unit_loandFiledtoBank()==true) {commQualified = true; System.out.printf("FuncID 080 - Unit Loan Filed to Bank : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : False"+ "\n"); 
			}}

		return commQualified;
	}
	
	public static Boolean commQualified_v2(String entity_id, String proj_id, String pbl_id, String seq_no, String func_id){

		FuncID		= func_id;
		Boolean commQualified = false;

		if (FuncID.equals("001")||FuncID.equals("042")) { 
			if (unit_haspaidResFee_v2(entity_id, proj_id, pbl_id, seq_no)==true) { commQualified = true; System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); }}

		else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 2.00)==true) {commQualified = true;System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); }}

		else if (FuncID.equals("003")) { 
			if (isDocsComplete_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); }}

		else if (FuncID.equals("004")) { 
			if (unit_IssuedPDCFor6DPAnd36MAIn60Days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n");  }}

		else if (FuncID.equals("005")||FuncID.equals("050")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 4.00)==true) {commQualified = true;System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); }}

		else if (FuncID.equals("006")) { 
			if (unit_isfullDP_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 006 - Full DP : False"+ "\n"); }}

		else if (FuncID.equals("007")) { 
			if (unit_hassubmittedCTS_and_DOR_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); }}

		else if (FuncID.equals("008")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .25)==true) {commQualified = true;System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
			else {
				System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n");}}

		else if (FuncID.equals("009")) { 
			if (unit_issuedpdcfor75percentoftcp_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); }}

		else if (FuncID.equals("010")) { 
			if (unit_issuedpdcfor36main30days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); }}

		else if (FuncID.equals("011")) { 
			if (unit_hasSubmittedWaiveronTransferofTitle_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n");  }}

		else if (FuncID.equals("014")||FuncID.equals("032")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 3.00)==true) {commQualified = true;System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n");  }}

		else if (FuncID.equals("015")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 5.00)==true) {commQualified = true;System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 	 }}

		else if (FuncID.equals("016")|FuncID.equals("033")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 6.00)==true) {commQualified = true;System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("017")||FuncID.equals("034")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .05)==true) {commQualified = true;System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("018")||FuncID.equals("035")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .10)==true) {commQualified = true;System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("019")) { 
			if (unit_hassubmittedsignedspa_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); }}

		else if (FuncID.equals("020")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .50)==true) {commQualified = true;System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("021")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .75)==true) {commQualified = true;System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n");  }}

		else if (FuncID.equals("022")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, 1.00)==true) {commQualified = true;System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); }}

		else if (FuncID.equals("023")) { 
			if (unit_paid_first_ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); }}

		else if (FuncID.equals("024")) { 
			if (unit_haspaidXMA_v2(entity_id, proj_id, pbl_id, seq_no, 2.0)==true) {commQualified = true;System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n");}}

		else if (FuncID.equals("025")) { 
			if (unit_issuedpdcfor6dpand24ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); }}

		else if (FuncID.equals("026")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 8.00)==true) {commQualified = true;System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 026 - Paud 8th DP/CO : False"+ "\n"); 		}}

		else if (FuncID.equals("027")) { 
			if (unit_issuedpdcfor24masin60days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 	}}

		else if (FuncID.equals("029")) { 
			if (unit_hassubmitteditr_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); }}

		else if (FuncID.equals("030")) { 
			if (unit_hasSubmittedMRI_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); }}

		else if (FuncID.equals("031")) { 
			if (unit_hassubmittedmsvs_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); }}

		else if (FuncID.equals("036")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .15)==true) {commQualified = true;System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n");}}

		else if (FuncID.equals("037")) { 
			if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .20)==true) {commQualified = true;System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 	 }}

		else if (FuncID.equals("038")) { 
			if (unit_issuedpdcfor5dpand24ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); }}

		else if (FuncID.equals("039")) { 
			if (unit_hassubmitted_tin_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 	}}

		else if (FuncID.equals("040")) { 
			if (unit_issuedpdcfor24mas_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
			else {System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n");  }}

		else if (FuncID.equals("041")) { 
			if (isSignedSCDComplete_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 041 - Has Submitted Signed SCD : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 041 - Has Submitted Signed SCD : False"+ "\n"); }}			

		else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")||
				FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
			if (unit_isofficiallyreserved_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); }}

		else if (FuncID.equals("046")) { 
			if (unit_isfullsettledorloantakeout_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n");  }}

		else if (FuncID.equals("047")) { 
			if (unit_isCompleteMinorDocs_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); }}

		else if (FuncID.equals("048")) { 
			if (unit_issuedpdcfor12mas_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n");  }}

		else if (FuncID.equals("051")) { 
			if (unit_hasSubmittedPayslip_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n");  }}

		else if (FuncID.equals("052")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, .50)==true) {commQualified = true;System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("053")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 1.00)==true) {commQualified = true;System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n");  }}

		else if (FuncID.equals("055")) { 
			if (unit_issuedpdcfor9dpand36ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true;System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n");  }}

		else if (FuncID.equals("056")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 9.00)==true) {commQualified = true;System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("057")) { 
			if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 12.00)==true) {commQualified = true;System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n");  }}

		else if (FuncID.equals("060")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, .75)==true) {commQualified = true;System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("062")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, .25)==true) {commQualified = true;System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n");  }}

		else if (FuncID.equals("063")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, .90)==true) {commQualified = true;System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("064")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, .30)==true) {commQualified = true;System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("065")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.60)==true) {commQualified = true;System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("066")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.90)==true) {commQualified = true;System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("068")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.65)==true) {commQualified = true;System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("072")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.70)==true) {commQualified = true;System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("074")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.30)==true) {commQualified = true;System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("075")) { 
			if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.60)==true) {commQualified = true;System.out.printf("FuncID 075 - Paid 60perc DP/CO : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n"); }}

		else if (FuncID.equals("076")) { //last checked : 09-23-2016
			if (unit_hasBankLOG_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true; System.out.printf("FuncID 076 - Unit has Bank LOG or NOA : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 076 -  Unit has Bank LOG or NOA : False"+ "\n"); 
			}}

		else if (FuncID.equals("077")) { //last checked : 09-23-2016
			if (unit_hasSignedBankLOG_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true; System.out.printf("FuncID 077 - Unit has Signed Bank LOG or NOA : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 077 -  Unit has Signed Bank LOG or NOA : False"+ "\n"); 
			}}		  

		else if (FuncID.equals("078")) { //last checked : 09-23-2016
			if (unit_isLoanTakeout_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true; System.out.printf("FuncID 078 - Loan Take Out (Bank) / Full Settled : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 078 -  Loan Take Out (Bank) / Full Settled : False"+ "\n"); 
			}}

		else if (FuncID.equals("079")) { //last checked : 09-23-2016
			if (unit_hasDOAS_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true; System.out.printf("FuncID 079 - Unit Has Signed DOAS : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 079 - Unit Has Signed DOAS : False"+ "\n"); 
			}}

		else if (FuncID.equals("080")) { //added : 04-06-2017
			if (unit_loandFiledtoBank_v2(entity_id, proj_id, pbl_id, seq_no)==true) {commQualified = true; System.out.printf("FuncID 080 - Unit Loan Filed to Bank : TRUE"+ "\n\n"); } 
			else {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : False"+ "\n"); 
			}}

		return commQualified;
	}


	//conditions
	public static Boolean isDocsComplete(){ //last checked : 02-10-2016

		Boolean isDocsComplete = false;

		String SQL =  "select unit_isdocs_complete('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL isDocsComplete : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isDocsComplete = true;}
		else{isDocsComplete = false;}

		return isDocsComplete;		
	}
	
	public static Boolean isDocsComplete_v2(String entity_id, String proj_id, String pbl_id, String seq_no){ //last checked : 02-10-2016

		Boolean isDocsComplete = false;

		String SQL =  "select unit_isdocs_complete_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL isDocsComplete : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isDocsComplete = true;}
		else{isDocsComplete = false;}

		return isDocsComplete;		
	}

	public static Boolean isSignedSCDComplete(){

		Boolean isSCDsubmitted = false;

		String SQL =  "select unit_submittedsignedscd('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL isSignedSCDComplete : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isSCDsubmitted = true;}
		else{isSCDsubmitted = false;}

		return isSCDsubmitted;		
	}
	
	public static Boolean isSignedSCDComplete_v2(String entity_id, String proj_id, String pbl_id, String seq_no){

		Boolean isSCDsubmitted = false;

		String SQL =  "select unit_submittedsignedscd_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL isSignedSCDComplete : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isSCDsubmitted = true;}
		else{isSCDsubmitted = false;}

		return isSCDsubmitted;		
	}

	public static Boolean unit_hassubmitteditr(){ //last checked : 02-11-2016

		Boolean isITRsubmitted = false;

		String SQL =  "select unit_hassubmitteditr('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hassubmitteditr : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isITRsubmitted = true;}
		else{isITRsubmitted = false;}

		return isITRsubmitted;		
	}
	
	public static Boolean unit_hassubmitteditr_v2(String entity_id, String proj_id, String pbl_id, String seq_no){ //last checked : 02-11-2016

		Boolean isITRsubmitted = false;

		String SQL =  "select unit_hassubmitteditr_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmitteditr : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isITRsubmitted = true;}
		else{isITRsubmitted = false;}

		return isITRsubmitted;		
	}

	public static Boolean unit_hassubmittedmsvs(){//last checked : 02-11-2016

		Boolean isMSVSsubmitted = false;

		String SQL =  "select unit_hassubmittedmsvs('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hassubmittedmsvs : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isMSVSsubmitted = true;}
		else{isMSVSsubmitted = false;}

		return isMSVSsubmitted;		
	}
	
	public static Boolean unit_hassubmittedmsvs_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean isMSVSsubmitted = false;

		String SQL =  "select unit_hassubmittedmsvs_v2('"+entity_id+"', '"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmittedmsvs : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){isMSVSsubmitted = true;}
		else{isMSVSsubmitted = false;}

		return isMSVSsubmitted;		
	}

	public static Boolean unit_isofficiallyreserved(){//last checked : 02-11-2016

		Boolean unit_isofficiallyreserved = false;

		String SQL =  "select unit_isofficiallyreserved('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isofficiallyreserved : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isofficiallyreserved = true;}
		else{unit_isofficiallyreserved = false;}

		return unit_isofficiallyreserved;		
	}
	
	public static Boolean unit_isofficiallyreserved_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_isofficiallyreserved = false;

		String SQL =  "select unit_isofficiallyreserved_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_isofficiallyreserved_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isofficiallyreserved = true;}
		else{unit_isofficiallyreserved = false;}

		return unit_isofficiallyreserved;		
	}

	public static Boolean unit_isfullDP(){//last checked : 02-11-2016

		Boolean unit_isfullDP = false;

		String SQL =  "select unit_isfullDP('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isfullDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isfullDP = true;}
		else{unit_isfullDP = false;}

		return unit_isfullDP;		
	}
	
	public static Boolean unit_isfullDP_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_isfullDP = false;

		String SQL =  "select unit_isfulldp_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_isfullDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isfullDP = true;}
		else{unit_isfullDP = false;}

		return unit_isfullDP;		
	}

	public static Boolean unit_isfullsettledorloantakeout(){//last checked : 02-11-2016

		Boolean unit_isfullsettledorloantakeout = false;

		String SQL =  "select unit_isfullsettledorloantakeout('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isfullsettledorloantakeout : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isfullsettledorloantakeout = true;}
		else{unit_isfullsettledorloantakeout = false;}

		return unit_isfullsettledorloantakeout;		
	}
	
	public static Boolean unit_isfullsettledorloantakeout_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_isfullsettledorloantakeout = false;

		String SQL =  "select unit_isfullsettledorloantakeout_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_isfullsettledorloantakeout_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isfullsettledorloantakeout = true;}
		else{unit_isfullsettledorloantakeout = false;}

		return unit_isfullsettledorloantakeout;		
	}

	public static Boolean unit_isCompleteMinorDocs(){//last checked : 02-11-2016

		Boolean unit_isCompleteMinorDocs = false;

		String SQL =  "select unit_isCompleteMinorDocs('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isCompleteMinorDocs : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isCompleteMinorDocs = true;}
		else{unit_isCompleteMinorDocs = false;}

		return unit_isCompleteMinorDocs;		
	}
	
	public static Boolean unit_isCompleteMinorDocs_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_isCompleteMinorDocs = false;

		String SQL =  "select unit_iscompleteminordocs_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_iscompleteminordocs_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isCompleteMinorDocs = true;}
		else{unit_isCompleteMinorDocs = false;}

		return unit_isCompleteMinorDocs;		
	}

	public static Boolean unit_hasSubmittedWaiveronTransferofTitle(){//last checked : 02-11-2016

		Boolean unit_hasSubmittedWaiveronTransferofTitle = false;

		String SQL =  "select unit_hasSubmittedWaiveronTransferofTitle('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasSubmittedWaiveronTransferofTitle : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedWaiveronTransferofTitle = true;}
		else{unit_hasSubmittedWaiveronTransferofTitle = false;}

		return unit_hasSubmittedWaiveronTransferofTitle;		
	}
	
	public static Boolean unit_hasSubmittedWaiveronTransferofTitle_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_hasSubmittedWaiveronTransferofTitle = false;

		String SQL =  "select unit_hassubmittedwaiverontransferoftitle_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hasSubmittedWaiveronTransferofTitle : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedWaiveronTransferofTitle = true;}
		else{unit_hasSubmittedWaiveronTransferofTitle = false;}

		return unit_hasSubmittedWaiveronTransferofTitle;		
	}

	public static Boolean unit_hasSubmittedMRI(){//last checked : 02-11-2016

		Boolean unit_hasSubmittedMRI = false;

		String SQL =  "select unit_hasSubmittedMRI('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasSubmittedMRI : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedMRI = true;}
		else{unit_hasSubmittedMRI
			= false;}

		return unit_hasSubmittedMRI;		
	}
	
	public static Boolean unit_hasSubmittedMRI_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_hasSubmittedMRI = false;

		String SQL =  "select unit_hassubmittedmri_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hasSubmittedMRI : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedMRI = true;}
		else{unit_hasSubmittedMRI
			= false;}

		return unit_hasSubmittedMRI;		
	}

	public static Boolean unit_hassubmitted_tin(){//last checked : 02-11-2016

		Boolean unit_hassubmitted_tin = false;

		String SQL =  "select unit_hassubmitted_tin('"+PBL_ID+"', "+SeqNo+", '"+getEntityID()+"') " ;

		System.out.printf("SQL unit_hassubmitted_tin : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmitted_tin = true;}
		else{unit_hassubmitted_tin = false;}

		return unit_hassubmitted_tin;		
	}
	
	public static Boolean unit_hassubmitted_tin_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_hassubmitted_tin = false;

		String SQL =  "select unit_hassubmitted_tin_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmitted_tin_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmitted_tin = true;}
		else{unit_hassubmitted_tin = false;}

		return unit_hassubmitted_tin;		
	}

	public static Boolean unit_hasSubmittedPayslip(){//last checked : 02-12-2016

		Boolean unit_hasSubmittedPayslip = false;

		String SQL =  "select unit_hasSubmittedPayslip('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasSubmittedPayslip : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedPayslip = true;}
		else{unit_hasSubmittedPayslip = false;}

		return unit_hasSubmittedPayslip;		
	}
	
	public static Boolean unit_hasSubmittedPayslip_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-12-2016

		Boolean unit_hasSubmittedPayslip = false;

		String SQL =  "select unit_hassubmittedpayslip_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmittedpayslip_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSubmittedPayslip = true;}
		else{unit_hasSubmittedPayslip = false;}

		return unit_hasSubmittedPayslip;		
	}

	public static Boolean unit_hassubmittedCTS_and_DOR(){//last checked : 02-11-2016

		Boolean unit_hassubmittedCTS = false;

		String SQL =  "select unit_hassubmittedcts_and_dor('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hassubmittedcts_and_dor : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmittedCTS = true;}
		else{unit_hassubmittedCTS = false;}

		return unit_hassubmittedCTS;		
	}
	
	public static Boolean unit_hassubmittedCTS_and_DOR_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_hassubmittedCTS = false;

		String SQL =  "select unit_hassubmittedcts_and_dor_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmittedcts_and_dor_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmittedCTS = true;}
		else{unit_hassubmittedCTS = false;}

		return unit_hassubmittedCTS;		
	}

	public static Boolean unit_hassubmittedsignedspa(){//last checked : 02-12-2016

		Boolean unit_hassubmittedsignedspa = false;

		String SQL =  "select unit_hassubmittedsignedspa('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hassubmittedsignedspa : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmittedsignedspa = true;}
		else{unit_hassubmittedsignedspa = false;}

		return unit_hassubmittedsignedspa;		
	}
	
	public static Boolean unit_hassubmittedsignedspa_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-12-2016

		Boolean unit_hassubmittedsignedspa = false;

		String SQL =  "select unit_hassubmittedsignedspa_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassubmittedsignedspa_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hassubmittedsignedspa = true;}
		else{unit_hassubmittedsignedspa = false;}

		return unit_hassubmittedsignedspa;		
	}

	public static Boolean unit_haspaidResFee(){  //last checked : 02-10-2016

		Boolean unit_haspaidResFee = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_paid_resfee ( '"+PBL_ID+"', "+SeqNo+" ),0) - " +
						"coalesce(get_unit_required_resfee ( '"+PBL_ID+"', "+SeqNo+" ),0) ) >= 0 " +
						"then true else false end  \r\n" ;

		System.out.printf("SQL unit_haspaidResFee : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidResFee = true;}
		else{unit_haspaidResFee = false;}

		return unit_haspaidResFee;		
	}
	
	public static Boolean unit_haspaidResFee_v2(String entity_id, String proj_id, String pbl_id, String seq_no){  //last checked : 02-10-2016

		Boolean unit_haspaidResFee = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_paid_resfee_v2 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+" ),0) - " +
						"coalesce(get_unit_required_resfee_v2 ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+" ),0) ) >= 0 " +
						"then true else false end  \r\n" ;

		System.out.printf("SQL unit_haspaidResFee : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidResFee = true;}
		else{unit_haspaidResFee = false;}

		return unit_haspaidResFee;		
	}

	public static Boolean unit_haspaidXDP(Double num){//last checked : 02-10-2016

		Boolean unit_haspaidDP = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_dp_amount_paid ('"+PBL_ID+"', "+SeqNo+"),0) / " +
						"coalesce(get_unit_dp_amount_per_month ('"+PBL_ID+"', "+SeqNo+"),1) ) >= "+num+" " +
						"then true else false end " ;

		System.out.printf("SQL unit_haspaidDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidDP = true;}
		else{unit_haspaidDP = false;}

		return unit_haspaidDP;		
	}
	
	public static Boolean unit_haspaidXDP_v2(String entity_id, String proj_id, String pbl_id, String seq_no, Double num){//last checked : 02-10-2016

		Boolean unit_haspaidDP = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_dp_amount_paid_v4 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+"),0) / " +
						"coalesce(get_unit_dp_amount_per_month_v2 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+"),1) ) >= "+num+" " +
						"then true else false end " ;

		System.out.printf("SQL unit_haspaidDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidDP = true;}
		else{unit_haspaidDP = false;}

		return unit_haspaidDP;		
	}

	public static Boolean unit_haspaidXMA(Double num){//last checked : 02-10-2016

		Boolean unit_haspaidXMA = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_ma_amount_paid ('"+PBL_ID+"', "+SeqNo+"),0) / " +
						"coalesce(get_unit_ma_amount_per_month ('"+PBL_ID+"', "+SeqNo+"),1) ) >= "+num+" " +
						"then true else false end " ;

		System.out.printf("SQL unit_haspaidXMA : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidXMA = true;}
		else{unit_haspaidXMA = false;}

		return unit_haspaidXMA;		
	}
	
	public static Boolean unit_haspaidXMA_v2(String entity_id, String proj_id, String pbl_id, String seq_no, Double num){//last checked : 02-10-2016

		Boolean unit_haspaidXMA = false;

		String SQL =  
				"select case when ( select coalesce(get_unit_ma_amount_paid_v2 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+"),0) / " +
						"coalesce(get_unit_ma_amount_per_month_v2 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+"),1) ) >= "+num+" " +
						"then true else false end " ;

		System.out.printf("SQL unit_haspaidXMA : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidXMA = true;}
		else{unit_haspaidXMA = false;}

		return unit_haspaidXMA;		
	}

	public static Boolean unit_haspaidpercDP(Double perc){//last checked : 02-10-2016

		Boolean unit_haspaidpercDP = false;

		String SQL =  
				"select case when ( select coalesce( get_unit_dp_amount_paid ('"+PBL_ID+"', "+SeqNo+"),0) / " +
						"coalesce(get_unit_dp_amount_total_required ('"+PBL_ID+"', "+SeqNo+"),1) ) >= "+perc+" " +
						"then true else false end  " ;

		System.out.printf("SQL unit_haspaidpercDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidpercDP = true;}
		else{unit_haspaidpercDP = false;}

		return unit_haspaidpercDP;		
	}
	
	public static Boolean unit_haspaidpercDP_v2(String entity_id, String proj_id, String pbl_id, String seq_no, Double perc){//last checked : 02-10-2016

		Boolean unit_haspaidpercDP = false;

		String SQL =  
				"select case when ( select coalesce( get_unit_dp_amount_paid_v4('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+"),0) / " +
						"coalesce(get_unit_dp_amount_total_required_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+"),1) ) >= "+perc+" " +
						"then true else false end  " ;

		System.out.printf("SQL unit_haspaidpercDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_haspaidpercDP = true;}
		else{unit_haspaidpercDP = false;}

		return unit_haspaidpercDP;		
	}

	public static Boolean unit_prin_perc_paid(Double perc){//last checked : 02-11-2016

		Boolean unit_prin_perc_paid = false;

		String SQL =  "select coalesce(get_unit_prin_perc_paid ( '"+PBL_ID+"' ,"+SeqNo+" ),0) \n" ;

		System.out.printf("SQL unit_prin_perc_paid : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(Double.parseDouble(db.getResult()[0][0].toString())>= perc){unit_prin_perc_paid = true;}
		else{unit_prin_perc_paid = false;}

		return unit_prin_perc_paid;			
	}
	
	public static Boolean unit_prin_perc_paid_v2(String entity_id, String proj_id, String pbl_id, String seq_no, Double perc){//last checked : 02-11-2016

		Boolean unit_prin_perc_paid = false;

		String SQL =  "select coalesce(get_unit_prin_perc_paid_v2 ('"+entity_id+"','"+proj_id+"','"+pbl_id+"' ,"+seq_no+" ),0) \n" ;

		System.out.printf("SQL unit_prin_perc_paid : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(Double.parseDouble(db.getResult()[0][0].toString())>= perc){unit_prin_perc_paid = true;}
		else{unit_prin_perc_paid = false;}

		return unit_prin_perc_paid;			
	}

	public static Boolean unit_paid_first_ma(){ //last checked : 02-11-2016

		Boolean unit_paid_first_ma = false;

		String SQL =  "select unit_paid_first_ma('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isfullDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_paid_first_ma = true;}
		else{unit_paid_first_ma = false;}

		return unit_paid_first_ma;		
	}
	
	public static Boolean unit_paid_first_ma_v2(String entity_id, String proj_id, String pbl_id, String seq_no){ //last checked : 02-11-2016

		Boolean unit_paid_first_ma = false;

		String SQL =  "select unit_paid_first_ma_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_isfullDP : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_paid_first_ma = true;}
		else{unit_paid_first_ma = false;}

		return unit_paid_first_ma;		
	}

	public static Boolean unit_IssuedPDCFor6DPAnd36MAIn60Days(){//last checked : 02-11-2016

		Boolean unit_IssuedPDCFor6DPAnd36MAIn60Days = false;

		//OLD SQL 		String SQL =  "select unit_issuedpdcfor6dpand36main60days('"+PBL_ID+"', "+SeqNo+") " ;

		//NEW SQL (02/11/2016)
		String SQL =  "select unit_issuedpdcfor36main30days('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor6dpand36main60days : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_IssuedPDCFor6DPAnd36MAIn60Days = true;}
		else{unit_IssuedPDCFor6DPAnd36MAIn60Days = false;}

		return unit_IssuedPDCFor6DPAnd36MAIn60Days;		
	}
	
	public static Boolean unit_IssuedPDCFor6DPAnd36MAIn60Days_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_IssuedPDCFor6DPAnd36MAIn60Days = false;

		//OLD SQL 		String SQL =  "select unit_issuedpdcfor6dpand36main60days('"+PBL_ID+"', "+SeqNo+") " ;

		//NEW SQL (02/11/2016)
		String SQL =  "select unit_issuedpdcfor36main30days_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor36main30days_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_IssuedPDCFor6DPAnd36MAIn60Days = true;}
		else{unit_IssuedPDCFor6DPAnd36MAIn60Days = false;}

		return unit_IssuedPDCFor6DPAnd36MAIn60Days;		
	}

	public static Boolean unit_issuedpdcfor36main30days(){

		Boolean unit_issuedpdcfor36main30days = false;

		String SQL =  "select unit_issuedpdcfor36main30days('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor36main30days : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor36main30days = true;}
		else{unit_issuedpdcfor36main30days = false;}

		return unit_issuedpdcfor36main30days;		
	}
	
	public static Boolean unit_issuedpdcfor36main30days_v2(String entity_id, String proj_id, String pbl_id, String seq_no){

		Boolean unit_issuedpdcfor36main30days = false;

		String SQL =  "select unit_issuedpdcfor36main30days_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor36main30days_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor36main30days = true;}
		else{unit_issuedpdcfor36main30days = false;}

		return unit_issuedpdcfor36main30days;		
	}

	public static Boolean unit_issuedpdcfor6dpand24ma(){ //last checked : 02-11-2016

		Boolean unit_issuedpdcfor6dpand24ma = false;

		String SQL =  "select unit_issuedpdcfor6dpand24ma('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor6dpand24ma : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor6dpand24ma = true;}
		else{unit_issuedpdcfor6dpand24ma = false;}

		return unit_issuedpdcfor6dpand24ma;		
	}
	
	public static Boolean unit_issuedpdcfor6dpand24ma_v2(String entity_id, String proj_id, String pbl_id, String seq_no){ //last checked : 02-11-2016

		Boolean unit_issuedpdcfor6dpand24ma = false;

		String SQL =  "select unit_issuedpdcfor6dpand24ma_v2('"+entity_id+"', '"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor6dpand24ma : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor6dpand24ma = true;}
		else{unit_issuedpdcfor6dpand24ma = false;}

		return unit_issuedpdcfor6dpand24ma;		
	}

	public static Boolean unit_issuedpdcfor24masin60days(){ //last checked : 02-11-2016

		Boolean unit_issuedpdcfor24masin60days = false;

		String SQL =  "select unit_issuedpdcfor24masin60days('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor24masin60days : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor24masin60days = true;}
		else{unit_issuedpdcfor24masin60days = false;}

		return unit_issuedpdcfor24masin60days;		
	}
	
	public static Boolean unit_issuedpdcfor24masin60days_v2(String entity_id, String proj_id, String pbl_id, String seq_no){ //last checked : 02-11-2016

		Boolean unit_issuedpdcfor24masin60days = false;

		String SQL =  "select unit_issuedpdcfor24masin60days_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor24masin60days : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor24masin60days = true;}
		else{unit_issuedpdcfor24masin60days = false;}

		return unit_issuedpdcfor24masin60days;		
	}

	public static Boolean unit_issuedpdcfor5dpand24ma(){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor5dpand24ma = false;

		String SQL =  "select unit_issuedpdcfor5dpand24ma('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor5dpand24ma : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor5dpand24ma = true;}
		else{unit_issuedpdcfor5dpand24ma = false;}

		return unit_issuedpdcfor5dpand24ma;		
	}
	
	public static Boolean unit_issuedpdcfor5dpand24ma_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor5dpand24ma = false;

		String SQL =  "select unit_issuedpdcfor5dpand24ma_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor5dpand24ma : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor5dpand24ma = true;}
		else{unit_issuedpdcfor5dpand24ma = false;}

		return unit_issuedpdcfor5dpand24ma;		
	}

	public static Boolean unit_issuedpdcfor24mas(){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor24mas = false;

		String SQL =  "select unit_issuedpdcfor24mas('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor24mas : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor24mas = true;}
		else{unit_issuedpdcfor24mas = false;}

		return unit_issuedpdcfor24mas;		
	}
	
	public static Boolean unit_issuedpdcfor24mas_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor24mas = false;

		String SQL =  "select unit_issuedpdcfor24mas_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor24mas_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor24mas = true;}
		else{unit_issuedpdcfor24mas = false;}

		return unit_issuedpdcfor24mas;		
	}

	public static Boolean unit_issuedpdcfor12mas(){//last checked : 02-12-2016

		Boolean unit_issuedpdcfor12mas = false;

		String SQL =  "select unit_issuedpdcfor12mas('"+PBL_ID+"', "+SeqNo+"),0 " ;

		System.out.printf("SQL unit_issuedpdcfor12mas : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor12mas = true;}
		else{unit_issuedpdcfor12mas = false;}

		return unit_issuedpdcfor12mas;		
	}
	
	public static Boolean unit_issuedpdcfor12mas_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-12-2016

		Boolean unit_issuedpdcfor12mas = false;

		String SQL =  "select unit_issuedpdcfor12mas_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+"),0 " ;

		System.out.printf("SQL unit_issuedpdcfor12mas_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor12mas = true;}
		else{unit_issuedpdcfor12mas = false;}

		return unit_issuedpdcfor12mas;		
	}

	public static Boolean unit_issuedpdcfor9dpand36ma(){//last checked : 02-12-2016

		Boolean unit_issuedpdcfor9dpand36ma = false;

		//DP payment is not relevant here, only the 36 MAs; that's y I replace the 9PDC and 6PDC
		String SQL =  "select unit_issuedpdcfor6dpand36main60days('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor9dpand36ma : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor9dpand36ma = true;}
		else{unit_issuedpdcfor9dpand36ma = false;}

		return unit_issuedpdcfor9dpand36ma;		
	}
	
	public static Boolean unit_issuedpdcfor9dpand36ma_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-12-2016

		Boolean unit_issuedpdcfor9dpand36ma = false;

		//DP payment is not relevant here, only the 36 MAs; that's y I replace the 9PDC and 6PDC
		String SQL =  "select unit_issuedpdcfor6dpand36main60days_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor6dpand36main60days_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor9dpand36ma = true;}
		else{unit_issuedpdcfor9dpand36ma = false;}

		return unit_issuedpdcfor9dpand36ma;		
	}

	public static Boolean unit_issuedpdcfor75percentoftcp(){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor75percentoftcp = false;

		String SQL =  "select unit_issuedpdcfor75percentoftcp('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_issuedpdcfor75percentoftcp: " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor75percentoftcp = true;}
		else{unit_issuedpdcfor75percentoftcp = false;}

		return unit_issuedpdcfor75percentoftcp;		
	}
	
	public static Boolean unit_issuedpdcfor75percentoftcp_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 02-11-2016

		Boolean unit_issuedpdcfor75percentoftcp = false;

		String SQL =  "select unit_issuedpdcfor75percentoftcp_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_issuedpdcfor75percentoftcp_v2: " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_issuedpdcfor75percentoftcp = true;}
		else{unit_issuedpdcfor75percentoftcp = false;}

		return unit_issuedpdcfor75percentoftcp;		
	}

	public static Boolean unit_isLoanTakeout(){//last checked : 09-23-2016

		Boolean unit_isLoanTakeout = false;

		String SQL =  "select unit_isLoanTakeout('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_isLoanTakeout : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isLoanTakeout = true;}
		else{unit_isLoanTakeout = false;}

		return unit_isLoanTakeout;		
	}
	
	public static Boolean unit_isLoanTakeout_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 09-23-2016

		Boolean unit_isLoanTakeout = false;

		String SQL =  "select unit_isloantakeout_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_isloantakeout_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_isLoanTakeout = true;}
		else{unit_isLoanTakeout = false;}

		return unit_isLoanTakeout;		
	}

	public static Boolean unit_hasBankLOG(){//last checked : 09-23-2016

		Boolean unit_hasBankLOG = false;

		String SQL =  "select unit_hasBankLOG('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasBankLOG : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasBankLOG = true;}
		else{unit_hasBankLOG = false;}

		return unit_hasBankLOG;		
	}
	
	public static Boolean unit_hasBankLOG_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 09-23-2016

		Boolean unit_hasBankLOG = false;

		String SQL =  "select unit_hasbanklog_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hasbanklog_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasBankLOG = true;}
		else{unit_hasBankLOG = false;}

		return unit_hasBankLOG;		
	}

	public static Boolean unit_hasSignedBankLOG(){//last checked : 09-23-2016

		Boolean unit_hasSignedBankLOG = false;

		String SQL =  "select unit_hasSignedBankLOG('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasSignedBankLOG : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSignedBankLOG = true;}
		else{unit_hasSignedBankLOG = false;}

		return unit_hasSignedBankLOG;		
	}
	
	public static Boolean unit_hasSignedBankLOG_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 09-23-2016

		Boolean unit_hasSignedBankLOG = false;

		String SQL =  "select unit_hassignedbanklog_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hassignedbanklog_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasSignedBankLOG = true;}
		else{unit_hasSignedBankLOG = false;}

		return unit_hasSignedBankLOG;		
	}

	public static Boolean unit_hasDOAS(){//last checked : 09-23-2016

		Boolean unit_hasDOAS = false;

		String SQL =  "select unit_hasDOAS('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_hasDOAS : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasDOAS = true;}
		else{unit_hasDOAS = false;}

		return unit_hasDOAS;		
	}
	
	public static Boolean unit_hasDOAS_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//last checked : 09-23-2016

		Boolean unit_hasDOAS = false;

		String SQL =  "select unit_hasdoas_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_hasdoas_v2 : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_hasDOAS = true;}
		else{unit_hasDOAS = false;}

		return unit_hasDOAS;		
	}

	public static Boolean unit_loandFiledtoBank(){//added : 04-06-2017

		Boolean unit_loandFiledtoBank = false;

		String SQL =  "select unit_filed_to_bank('"+PBL_ID+"', "+SeqNo+") " ;

		System.out.printf("SQL unit_filed_to_bank : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_loandFiledtoBank = true;}
		else{unit_loandFiledtoBank = false;}

		return unit_loandFiledtoBank;		
	}
	
	public static Boolean unit_loandFiledtoBank_v2(String entity_id, String proj_id, String pbl_id, String seq_no){//added : 04-06-2017

		Boolean unit_loandFiledtoBank = false;

		String SQL =  "select unit_filed_to_bank_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+") " ;

		System.out.printf("SQL unit_filed_to_bank : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_loandFiledtoBank = true;}
		else{unit_loandFiledtoBank = false;}

		return unit_loandFiledtoBank;		
	}


	//other details
	public static String getCommConditionID() {//used

		String strSQL = 			
				"select get_comm_condition_id ('"+SchemeID+"','"+CommType+"')";		

		System.out.printf("SQL getCommConditionID : " + strSQL + "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}	

	public static String getCommSchemeID() {//used

		String strSQL = 			
				"select distinct on (pbl_id, seq_no) scheme_code from cm_schedule_hd \n" +
						"where pbl_id = '"+PBL_ID+"' and seq_no = "+SeqNo+" and status_id = 'A' ";		

		System.out.printf("SQL getCommSchemeID : " + strSQL + "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	public static String getCommSchemeID_v2(String entity_id, String proj_id, String pbl_id, String seq_no) {
		String SQL = "select distinct on (account_code, proj_id, pbl_id, seq_no) scheme_code \n"+
			         "from cm_schedule_hd \n" +
					 "where account_code = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status_id = 'A' ";
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static String getEntityID() {//used

		String strSQL = 			
				"select distinct on (account_code) account_code from cm_schedule_hd \n" +
						"where pbl_id = '"+PBL_ID+"' and seq_no = "+SeqNo+" and status_id = 'A' ";		

		System.out.printf("SQL getEntityID : " + strSQL + "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}	

	public static Integer getNumberofCondition(){

		Integer x = 1;

		String SQL = 
				"select get_comm_numberofcondition('"+SchemeID+"','"+PBL_ID+"','"+CommType+"',"+SeqNo+") " ;

		System.out.printf("SQL getNumberofCondition : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Integer) db.getResult()[0][0];
		}else{
			x = 1;
		}

		return x;
	}

	public static Integer getNumberofCondition_v2(String entity_id, String proj_id, String pbl_id, String seq_no){

		Integer x = 1;

		String SQL = 
				"select get_comm_numberofcondition_v2('"+SchemeID+"','"+CommType+"','"+entity_id+"','"+proj_id+"','"+pbl_id+"',"+seq_no+") " ;

		System.out.printf("SQL getNumberofCondition : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Integer) db.getResult()[0][0];
		}else{
			x = 1;
		}

		return x;
	}
	
	public static Object[][] getFunctionIDs() {//used

		String strSQL = 
				"select trim(func_id) from cm_conditions_dl where cond_id = '"+CondID+"' and status_id = 'A' "  ;		

		System.out.printf("getFunctionIDs : " + strSQL + "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){			
			return db.getResult();
		}else{
			return null;
		}
	}	


	//insert / save
	public static void updateScheduleDetail(String pbl_id, String seq_no, String comm_type, pgUpdate db) {

		String sqlDetail = 
				"update cm_schedule_dl set " +
						"qualified = true, \n" +
						"date_qualified = '"+Calendar.getInstance().getTime()+"'  \n" +
						"where trim(pbl_id) = '"+pbl_id+"' " +
						"and seq_no = "+seq_no+" \n" +
						"and comm_type = '"+comm_type+"' " +
						"and status != 'R' " +
						"and status = 'A'   " +
						"and comm_amt != 0 " ;

		System.out.printf("Update SQL :", sqlDetail);
		db.executeUpdate(sqlDetail, false);			
	}
	
	public static void updateScheduleDetail_v2(String entity_id, String proj_id, String pbl_id, String seq_no, String comm_type, pgUpdate db) {

		String sqlDetail = 
				"update cm_schedule_dl set " +
						"qualified = true, \n" +
						"date_qualified = '"+Calendar.getInstance().getTime()+"'  \n" +
						"where account_code = '"+entity_id+"' \n"+
						"and projcode = '"+proj_id+"' \n"+
						"and trim(pbl_id) = '"+pbl_id+"' " +
						"and seq_no = "+seq_no+" \n" +
						"and comm_type = '"+comm_type+"' " +
						"and status != 'R' " +
						"and status = 'A'   " +
						"and comm_amt != 0 " ;

		System.out.printf("Update SQL :", sqlDetail);
		db.executeUpdate(sqlDetail, false);			
	}


	public static void qualifyCommAll(String pbl_id, String seq_no, String comm_type, String co_id) throws InterruptedException {

		PBL_ID 		= pbl_id;
		SeqNo 		= seq_no;
		SchemeID	= getCommSchemeID();
		CommType	= comm_type;
		CondID		= getCommConditionID();		
		NoOfCond	= getNumberofCondition()-1;
		Co_ID 		= co_id;
		int x 		= 0;

		System.out.printf("SchemeID : " + SchemeID + "\n" );
		System.out.printf("CommType : " + CommType + "\n");
		System.out.printf("CondID 	: " + CondID+ "\n" );
		System.out.printf("NoOfCond : " + NoOfCond + "\n");

		while (x<=NoOfCond) {

			Object[][] func = getFunctionIDs();	

			System.out.printf("FuncID : " + "(String) func[0]["+x+"]" + "\n");
			FuncID = (String) func[x][0];
			

			if (FuncID.equals("001")||FuncID.equals("042")) { //last checked 02-10-2016
				if (unit_haspaidResFee()==true) {
					System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n");
				} else {
					System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); break;
				}
			}

			else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(2.00)==true) {
					System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); 
				} else {
					System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); break;
				}
			}

			else if (FuncID.equals("003")) { //accdg to M'Rhea (02-10-2016) : docs complete (IHF) ; SCD In (PagiBIG)
				if (isDocsComplete()==true) {
					System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n");
				} else {
					System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); break; 
				}
			}

			else if (FuncID.equals("004")) {  //last checked 02-11-2016
				if (unit_IssuedPDCFor6DPAnd36MAIn60Days()==true) {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n"); break; }}

			else if (FuncID.equals("005")||FuncID.equals("050")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(4.00)==true) {System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("006")) { //last checked : 02-10-2016
				if (unit_isfullDP()==true) {System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 006 - Full DP : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("007")) { //last checked : 02-11-2016
				if (unit_hassubmittedCTS_and_DOR()==true) {System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("008")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.25)==true) {System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n"); 			
					break; }}

			else if (FuncID.equals("009")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor75percentoftcp()==true) {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("010")) {  //last checked : 02-11-2016
				if (unit_issuedpdcfor36main30days()==true) {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("011")) { //last checked : 02-11-2016
				if (unit_hasSubmittedWaiveronTransferofTitle()==true) {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("014")||FuncID.equals("032")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(3.00)==true) {System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("015")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage				
				if (unit_haspaidXDP(5.00)==true) {System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("016")|FuncID.equals("033")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(6.00)==true) {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("017")||FuncID.equals("034")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.05)==true) {System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("018")||FuncID.equals("035")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.10)==true) {System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); 			
				break; }}

			/*else if (FuncID.equals("019")||FuncID.equals("067")) { 
				if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); 				
					break; }}*/

			else if (FuncID.equals("020")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.50)==true) {System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("021")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.75)==true) {System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("022")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(1.00)==true) {System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("023")) { //last checked : 02-11-2016
				if (unit_paid_first_ma()==true) {System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("024")) { //last checked : 02-10-2016
				if (unit_haspaidXMA(2.0)==true) {System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("025")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor6dpand24ma()==true) {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("026")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(8.00)==true) {System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 026 - Paid 8th DP/CO : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("027")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor24masin60days()==true) {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("029")) { //last checked : 02-11-2016
				if (unit_hassubmitteditr()==true) {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("030")) { //last checked : 02-11-2016
				if (unit_hasSubmittedMRI()==true) {System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("031")) { //last checked : 02-11-2016
				if (unit_hassubmittedmsvs()==true) {System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
				else
				{System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("036")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.15)==true) {System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("037")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(.20)==true) {System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("038")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor5dpand24ma()==true) {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("039")) { //last checked : 02-11-2016
				if (unit_hassubmitted_tin()==true) {System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("040")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor24mas()==true) {System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("041")) { //last checked : 02-10-2016
				if (isDocsComplete()==true) {System.out.printf("FuncID 041 - Docs Complete : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 041 - Docs Complete : False"+ "\n"); 
				break; }}			

			else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")|| //last checked : 02-11-2016
					FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
				if (unit_isofficiallyreserved()==true) {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("046")) { //last checked : 02-11-2016
				if(getBuyerType(PBL_ID, SeqNo).equals("03")) {
					if (unit_isfullsettledorloantakeout()==true && isDocsComplete()) {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); 
					} else {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
						break; }
				}else {
					if (unit_isfullsettledorloantakeout()==true) {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); 
					} else {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
						break; }
				}
			}

			else if (FuncID.equals("047")) { //last checked : 02-11-2016
				if (unit_isCompleteMinorDocs()==true) {System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("048")) { //last checked : 02-12-2016
				if (unit_issuedpdcfor12mas()==true) {System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("051")) { //last checked : 02-12-2016
				if (unit_hasSubmittedPayslip()==true) {System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("052")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.50)==true) {System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("053")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(1.00)==true) {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("055")) { //last checked : 02-12-2016
				if (unit_issuedpdcfor9dpand36ma()==true) {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("056")) { //last checked : 02-12-2016
				if (unit_haspaidXDP(9.00)==true) {System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("057")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP(12.00)==true) {System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("060")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.75)==true) {System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("062")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.25)==true) {System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("063")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid(0.90)==true) {System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("064")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("065")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("066")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.90)==true) {System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("067")) { //last checked : 02-12-2016 
				if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 067 - Submitted Signed SPA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 067 - Submitted Signed SPA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("068")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.65)==true) {System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("072")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP(0.70)==true) {System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); 
				break; }}			  

			else if (FuncID.equals("074")) { 
				if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n");break; }}

			else if (FuncID.equals("075")) { 
				if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 075 - Paid 60perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n");break; }}

			else if (FuncID.equals("076")) { //last checked : 09-23-2016
				if (unit_hasBankLOG()==true) {System.out.printf("FuncID 076 - Unit has Bank LOG or NOA : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 076 -  Unit has Bank LOG or NOA : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("077")) { //last checked : 09-23-2016
				if (unit_hasSignedBankLOG()==true) {System.out.printf("FuncID 077 - Unit has Signed Bank LOG or NOA : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 077 -  Unit has Signed Bank LOG or NOA : False"+ "\n"); 
				break; }}		  

			else if (FuncID.equals("078")) { //last checked : 09-23-2016
				if (unit_isLoanTakeout()==true) {System.out.printf("FuncID 078 - Loan Take Out (Bank) / Full Settled : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 078 -  Loan Take Out (Bank) / Full Settled : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("079")) { //last checked : 09-23-2016
				if (unit_hasDOAS()==true) {System.out.printf("FuncID 079 - Unit Has Signed DOAS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 079 - Unit Has Signed DOAS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("080")) { //added : 04-06-2017
				if (unit_loandFiledtoBank()==true) {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : False"+ "\n"); 
				break; }}			

			else {System.out.printf("FuncID Not Found!"+ "\n\n"); }


			if (x==NoOfCond) {				
				pgUpdate db = new pgUpdate();	
				updateScheduleDetail(PBL_ID, SeqNo, CommType, db);
				db.commit();				
				System.out.printf("Account qualified for Commission"+ "\n\n");
				break;
			}

			x++;

		}
	}
	
	public static void qualifyCommAll_v2(String entity_id, String proj_id, String pbl_id, String seq_no, String comm_type, String co_id) throws InterruptedException {

		PBL_ID 		= pbl_id;
		SeqNo 		= seq_no;
		SchemeID	= getCommSchemeID_v2(entity_id, proj_id, pbl_id, seq_no);
		CommType	= comm_type;
		CondID		= getCommConditionID();		
		NoOfCond	= getNumberofCondition_v2(entity_id, proj_id, pbl_id, seq_no)-1;
		Co_ID 		= co_id;
		int x 		= 0;

		System.out.printf("SchemeID : " + SchemeID + "\n" );
		System.out.printf("CommType : " + CommType + "\n");
		System.out.printf("CondID 	: " + CondID+ "\n" );
		System.out.printf("NoOfCond : " + NoOfCond + "\n");

		while (x<=NoOfCond) {

			Object[][] func = getFunctionIDs();	

			System.out.printf("FuncID : " + "(String) func[0]["+x+"]" + "\n");
			FuncID = (String) func[x][0];
			System.out.printf("value of function id: %s%n", FuncID);

			if (FuncID.equals("001")||FuncID.equals("042")) { //last checked 02-10-2016
				if (unit_haspaidResFee_v2(entity_id, proj_id, pbl_id, seq_no)==true) {
					System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n");
				} else {
					System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); break;
				}
			}else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 2.00)==true) {
					System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); 
				} else {
					System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); break;
				}
			}

			else if (FuncID.equals("003")) { //accdg to M'Rhea (02-10-2016) : docs complete (IHF) ; SCD In (PagiBIG)
				if (isDocsComplete_v2(entity_id, proj_id, pbl_id, seq_no)==true) {
					System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n");
				} else {
					System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); break; 
				}
			}

			else if (FuncID.equals("004")) {  //last checked 02-11-2016
				if (unit_IssuedPDCFor6DPAnd36MAIn60Days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n"); break; }}

			else if (FuncID.equals("005")||FuncID.equals("050")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 4.00)==true) {System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("006")) { //last checked : 02-10-2016
				if (unit_isfullDP_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 006 - Full DP : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("007")) { //last checked : 02-11-2016
				if (unit_hassubmittedCTS_and_DOR_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("008")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .25)==true) {System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
				else {
					System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n"); 			
					break; }}

			else if (FuncID.equals("009")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor75percentoftcp_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("010")) {  //last checked : 02-11-2016
				if (unit_issuedpdcfor36main30days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("011")) { //last checked : 02-11-2016
				if (unit_hasSubmittedWaiveronTransferofTitle_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("014")||FuncID.equals("032")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 3.00)==true) {System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("015")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage				
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 5.00)==true) {System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("016")|FuncID.equals("033")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 6.00)==true) {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("017")||FuncID.equals("034")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .05)==true) {System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("018")||FuncID.equals("035")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no,.10)==true) {System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); 			
				break; }}

			/*else if (FuncID.equals("019")||FuncID.equals("067")) { 
				if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); 				
					break; }}*/

			else if (FuncID.equals("020")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no,.50)==true) {System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("021")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no,.75)==true) {System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("022")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, 1.00)==true) {System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("023")) { //last checked : 02-11-2016
				if (unit_paid_first_ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("024")) { //last checked : 02-10-2016
				if (unit_haspaidXMA_v2(entity_id, proj_id, pbl_id, seq_no, 2.0)==true) {System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("025")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor6dpand24ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("026")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 8.00)==true) {System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 026 - Paid 8th DP/CO : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("027")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor24masin60days_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("029")) { //last checked : 02-11-2016
				if (unit_hassubmitteditr_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("030")) { //last checked : 02-11-2016
				if (unit_hasSubmittedMRI_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("031")) { //last checked : 02-11-2016
				if (unit_hassubmittedmsvs_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
				else
				{System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("036")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .15)==true) {System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("037")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, .20)==true) {System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("038")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor5dpand24ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("039")) { //last checked : 02-11-2016
				if (unit_hassubmitted_tin_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("040")) { //last checked : 02-11-2016
				if (unit_issuedpdcfor24mas_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n"); 			
				break; }}

			else if (FuncID.equals("041")) { //last checked : 02-10-2016
				if (isDocsComplete_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 041 - Docs Complete : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 041 - Docs Complete : False"+ "\n"); 
				break; }}			

			else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")|| //last checked : 02-11-2016
					FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
				if (unit_isofficiallyreserved_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("046")) { //last checked : 02-11-2016
				if(getBuyerType_v2(entity_id, proj_id, pbl_id, seq_no).equals("03")) {
					if (unit_isfullsettledorloantakeout_v2(entity_id, proj_id, pbl_id, seq_no)==true && isDocsComplete_v2(entity_id, proj_id, pbl_id, seq_no)) {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); 
					} else {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
						break; }
				}else {
					if (unit_isfullsettledorloantakeout_v2(entity_id, proj_id, pbl_id, seq_no)==true) {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); 
					} else {
						System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
						break; }
				}
			}

			else if (FuncID.equals("047")) { //last checked : 02-11-2016
				if (unit_isCompleteMinorDocs_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("048")) { //last checked : 02-12-2016
				if (unit_issuedpdcfor12mas_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("051")) { //last checked : 02-12-2016
				if (unit_hasSubmittedPayslip_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("052")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.50)==true) {System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("053")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 1.00)==true) {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("055")) { //last checked : 02-12-2016
				if (unit_issuedpdcfor9dpand36ma_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("056")) { //last checked : 02-12-2016
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 9.00)==true) {System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("057")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
				//current commission are now based on payment percentage
				if (unit_haspaidXDP_v2(entity_id, proj_id, pbl_id, seq_no, 12.00)==true) {System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("060")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.75)==true) {System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("062")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.25)==true) {System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("063")) { //last checked : 02-11-2016
				if (unit_prin_perc_paid_v2(entity_id, proj_id, pbl_id, seq_no, 0.90)==true) {System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("064")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.30)==true) {System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("065")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.60)==true) {System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("066")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.90)==true) {System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("067")) { //last checked : 02-12-2016 
				if (unit_hassubmittedsignedspa_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 067 - Submitted Signed SPA : TRUE" + "\n\n"); } 
				else {System.out.printf("FuncID 067 - Submitted Signed SPA : False"+ "\n"); 				
				break; }}

			else if (FuncID.equals("068")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.65)==true) {System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("072")) { //last checked : 02-10-2016
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.70)==true) {System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); 
				break; }}			  

			else if (FuncID.equals("074")) { 
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.30)==true) {System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n");break; }}

			else if (FuncID.equals("075")) { 
				if (unit_haspaidpercDP_v2(entity_id, proj_id, pbl_id, seq_no, 0.60)==true) {System.out.printf("FuncID 075 - Paid 60perc DP/CO : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n");break; }}

			else if (FuncID.equals("076")) { //last checked : 09-23-2016
				if (unit_hasBankLOG_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 076 - Unit has Bank LOG or NOA : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 076 -  Unit has Bank LOG or NOA : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("077")) { //last checked : 09-23-2016
				if (unit_hasSignedBankLOG_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 077 - Unit has Signed Bank LOG or NOA : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 077 -  Unit has Signed Bank LOG or NOA : False"+ "\n"); 
				break; }}		  

			else if (FuncID.equals("078")) { //last checked : 09-23-2016
				if (unit_isLoanTakeout_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 078 - Loan Take Out (Bank) / Full Settled : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 078 -  Loan Take Out (Bank) / Full Settled : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("079")) { //last checked : 09-23-2016
				if (unit_hasDOAS_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 079 - Unit Has Signed DOAS : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 079 - Unit Has Signed DOAS : False"+ "\n"); 
				break; }}

			else if (FuncID.equals("080")) { //added : 04-06-2017
				if (unit_loandFiledtoBank_v2(entity_id, proj_id, pbl_id, seq_no)==true) {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : TRUE"+ "\n\n"); } 
				else {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : False"+ "\n"); 
				break; }}			

			else {System.out.printf("FuncID Not Found!"+ "\n\n"); }


			if (x==NoOfCond) {				
				pgUpdate db = new pgUpdate();	
				//updateScheduleDetail(PBL_ID, SeqNo, CommType, db);
				updateScheduleDetail_v2(entity_id, proj_id, pbl_id, seq_no, comm_type, db);
				db.commit();				
				System.out.printf("Account qualified for Commission"+ "\n\n");
				break;
			}

			x++;

		}
	}

	private static String getBuyerType(String pbl_id, String seq_no) {
		String buyertype = "";

		pgSelect db = new pgSelect();
		String SQL = "SELECT get_group_id(buyertype) from rf_sold_unit where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and currentstatus != '02' and status_id = 'A'";
		db.select(SQL);

		if(db.isNotNull()) {
			buyertype = (String) db.getResult()[0][0];

		}
		return buyertype;
	}
	
	private static String getBuyerType_v2(String entity_id, String proj_id, String pbl_id, String seq_no) {
		String buyertype = "";

		pgSelect db = new pgSelect();
		String SQL = "SELECT get_group_id(buyertype) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and currentstatus != '02' and status_id = 'A'";
		db.select(SQL);

		if(db.isNotNull()) {
			buyertype = (String) db.getResult()[0][0];

		}
		return buyertype;
	}


	public static void CommThread() {

		SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {

				PBL_ID 		= strUnitID;
				SeqNo 		= strSeqNo;
				CommType	= strCommType;
				Co_ID 		= strCoID;
				SchemeID	= getCommSchemeID();
				CondID		= getCommConditionID();		
				NoOfCond	= getNumberofCondition()-1;

				int x 		= 0;

				System.out.printf("SchemeID : " + SchemeID + "\n" );
				System.out.printf("CommType : " + CommType + "\n");
				System.out.printf("CondID 	: " + CondID+ "\n" );
				System.out.printf("NoOfCond : " + NoOfCond + "\n");

				while (x<=NoOfCond) {

					Object[][] func = getFunctionIDs();	

					System.out.printf("FuncID : " + "(String) func[0]["+x+"]" + "\n");
					FuncID = (String) func[x][0];			

					if (FuncID.equals("001")||FuncID.equals("042")) { //last checked 02-10-2016
						if (unit_haspaidResFee()==true) {
							System.out.printf("FuncID 001||042 - Paid Res Fee : TRUE" + "\n\n");
						} else {
							System.out.printf("FuncID 001||042 - Paid Res Fee : False" + "\n"); break;
						}
					}

					else if (FuncID.equals("002")||FuncID.equals("043")||FuncID.equals("049")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(2.00)==true) {
							System.out.printf("FuncID 002||043||049 - Paid 2nd DP : TRUE" + "\n\n"); 
						} else {
							System.out.printf("FuncID 002||043||049 - Paid 2nd DP : False"+ "\n"); break;
						}
					}

					else if (FuncID.equals("003")) { //accdg to M'Rhea (02-10-2016) : docs complete (IHF) ; SCD In (PagiBIG)
						if (isDocsComplete()==true) {
							System.out.printf("FuncID 003 - Docs Complete : TRUE" + "\n\n");
						} else {
							System.out.printf("FuncID 003 - Docs Complete : False"+ "\n"); break; 
						}
					}

					else if (FuncID.equals("004")) {  //last checked 02-11-2016
						if (unit_IssuedPDCFor6DPAnd36MAIn60Days()==true) {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 004 - Issued PDCs for DPs & 36 MAs w/in 60 days after OR : False"+ "\n"); break; }}

					else if (FuncID.equals("005")||FuncID.equals("050")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(4.00)==true) {System.out.printf("FuncID 005||050 - Paid 4th Dp : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 005||050 - Paid 4th Dp : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("006")) { //last checked : 02-10-2016
						if (unit_isfullDP()==true) {System.out.printf("FuncID 006 - Full DP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 006 - Full DP : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("007")) { //last checked : 02-11-2016
						if (unit_hassubmittedCTS_and_DOR()==true) {System.out.printf("FuncID 007 - Submitted CTS : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 007 - Submitted CTS : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("008")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.25)==true) {System.out.printf("FuncID 008 - Paid 25perc TCP : TRUE" + "\n\n"); } 
						else {
							System.out.printf("FuncID 008 - Paid 25perc TCP : False"+ "\n"); 			
							break; }}

					else if (FuncID.equals("009")) { //last checked : 02-11-2016
						if (unit_issuedpdcfor75percentoftcp()==true) {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 009 - Issued PDC for 75 Percent of TCP : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("010")) {  //last checked : 02-11-2016
						if (unit_issuedpdcfor36main30days()==true) {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 010 - IssuedPDCFor36MAIn30Days : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("011")) { //last checked : 02-11-2016
						if (unit_hasSubmittedWaiveronTransferofTitle()==true) {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 011 - Submitted Waiver on Transfer of Title : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("014")||FuncID.equals("032")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(3.00)==true) {System.out.printf("FuncID 014||032 - Paid 3rd DP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 014||032 - Paid 3rd DP : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("015")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage				
						if (unit_haspaidXDP(5.00)==true) {System.out.printf("FuncID 015 - Paid 5th DP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 015 - Paid 5th DP : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("016")|FuncID.equals("033")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(6.00)==true) {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 016||033 - Paid 6th DP/CO : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("017")||FuncID.equals("034")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.05)==true) {System.out.printf("FuncID 017||034 - Paid 5perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 017||034 - Paid 5perc TCP : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("018")||FuncID.equals("035")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.10)==true) {System.out.printf("FuncID 018||035 - Paid 10perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 018||035 - Paid 10perc TCP : False"+ "\n"); 			
						break; }}

					/*else if (FuncID.equals("019")||FuncID.equals("067")) { 
						if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 019 - Submitted Signed SPA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 019 - Submitted Signed SPA : False"+ "\n"); 				
							break; }}*/

					else if (FuncID.equals("020")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.50)==true) {System.out.printf("FuncID 020 - Paid 50perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 020 - Paid 50perc TCP : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("021")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.75)==true) {System.out.printf("FuncID 021 - Paid 75perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 021 - Paid 75perc TCP : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("022")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(1.00)==true) {System.out.printf("FuncID 022 - Paid 100perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 022 - Paid 100perc TCP : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("023")) { //last checked : 02-11-2016
						if (unit_paid_first_ma()==true) {System.out.printf("FuncID 023 - Paid 1st MA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 023 - Paid 1st MA : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("024")) { //last checked : 02-10-2016
						if (unit_haspaidXMA(2.0)==true) {System.out.printf("FuncID 024 - Paid 2nd MA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 024 - Paid 2nd MA : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("025")) { //last checked : 02-11-2016
						if (unit_issuedpdcfor6dpand24ma()==true) {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 025 - Issued PDC For 6DP And 24MA : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("026")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(8.00)==true) {System.out.printf("FuncID 026 - Paid 8th DP/CO : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 026 - Paid 8th DP/CO : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("027")) { //last checked : 02-11-2016
						if (unit_issuedpdcfor24masin60days()==true) {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 027 - Issued PDC for 24MAs in 60 days : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("029")) { //last checked : 02-11-2016
						if (unit_hassubmitteditr()==true) {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 029 - Submitted ITR/EC/CEC/AFS : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("030")) { //last checked : 02-11-2016
						if (unit_hasSubmittedMRI()==true) {System.out.printf("FuncID 030 - Submitted MRI : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 030 - Submitted MRI : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("031")) { //last checked : 02-11-2016
						if (unit_hassubmittedmsvs()==true) {System.out.printf("FuncID 031 - Submitted MSVS : TRUE"+ "\n\n"); } 
						else
						{System.out.printf("FuncID 031 - Submitted MSVS : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("036")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.15)==true) {System.out.printf("FuncID 036 - Paid 15perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 036 - Paid 15perc TCP : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("037")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(.20)==true) {System.out.printf("FuncID 037 - Paid 20perc TCP : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 037 - Paid 20perc TCP : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("038")) { //last checked : 02-11-2016
						if (unit_issuedpdcfor5dpand24ma()==true) {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 038 - Issued PDC for 5DP and 24MA : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("039")) { //last checked : 02-11-2016
						if (unit_hassubmitted_tin()==true) {System.out.printf("FuncID 039 - TIN submission : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 039 - TIN submission : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("040")) { //last checked : 02-11-2016
						if (unit_issuedpdcfor24mas()==true) {System.out.printf("FuncID 040 - Issued PDC for 24MAs : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 040 - Issued PDC for 24MAs : False"+ "\n"); 			
						break; }}

					else if (FuncID.equals("041")) { //last checked : 02-10-2016
						if (isDocsComplete()==true) {System.out.printf("FuncID 041 - Docs Complete : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 041 - Docs Complete : False"+ "\n"); 
						break; }}			

					else if (FuncID.equals("044")||FuncID.equals("045")||FuncID.equals("069")|| //last checked : 02-11-2016
							FuncID.equals("054")||FuncID.equals("058")||FuncID.equals("061")||FuncID.equals("070")||FuncID.equals("071")) { 
						if (unit_isofficiallyreserved()==true) {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 069||044||045||069||054||058||061||070||071 - Officially Reserved : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("046")) { //last checked : 02-11-2016
						if (unit_isfullsettledorloantakeout()==true) {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 046 - Full Settled or Loan Take Out : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("047")) { //last checked : 02-11-2016
						if (unit_isCompleteMinorDocs()==true) {System.out.printf("FuncID 047 - Complete Minor Docs. : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 047 - Complete Minor Docs. : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("048")) { //last checked : 02-12-2016
						if (unit_issuedpdcfor12mas()==true) {System.out.printf("FuncID 048 - Issued PDC for 12MAs : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 048 - Issued PDC for 12MAs : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("051")) { //last checked : 02-12-2016
						if (unit_hasSubmittedPayslip()==true) {System.out.printf("FuncID 051 - Submitted Payslip : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 051 - Submitted Payslip : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("052")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.50)==true) {System.out.printf("FuncID 052 - Paid 50perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 052 - Paid 50perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("053")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(1.00)==true) {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 053 - Paid 100perc DP/CO  : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("055")) { //last checked : 02-12-2016
						if (unit_issuedpdcfor9dpand36ma()==true) {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 055 - Issued PDC for 9DP and 36MA  : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("056")) { //last checked : 02-12-2016
						if (unit_haspaidXDP(9.00)==true) {System.out.printf("FuncID 056 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 056 - Paid 90perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("057")) { //accdg to M'Rhea (02-10-2016) : this does not apply anymore, 
						//current commission are now based on payment percentage
						if (unit_haspaidXDP(12.00)==true) {System.out.printf("FuncID 057 - Paid 12th DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 057 - Paid 12th DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("060")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.75)==true) {System.out.printf("FuncID 060 - Paid 75perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 060 - Paid 75perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("062")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.25)==true) {System.out.printf("FuncID 062 - Paid 25perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 062 - Paid 25perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("063")) { //last checked : 02-11-2016
						if (unit_prin_perc_paid(0.90)==true) {System.out.printf("FuncID 063 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 063 - Paid 90perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("064")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 064 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 064 - Paid 30perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("065")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 065 - Paid 60perc DP/CO  TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 065 - Paid 60perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("066")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.90)==true) {System.out.printf("FuncID 066 - Paid 90perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 066 - Paid 90perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("067")) { //last checked : 02-12-2016 
						if (unit_hassubmittedsignedspa()==true) {System.out.printf("FuncID 067 - Submitted Signed SPA : TRUE" + "\n\n"); } 
						else {System.out.printf("FuncID 067 - Submitted Signed SPA : False"+ "\n"); 				
						break; }}

					else if (FuncID.equals("068")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.65)==true) {System.out.printf("FuncID 068 - Paid 65perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 068 - Paid 65perc DP/CO : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("072")) { //last checked : 02-10-2016
						if (unit_haspaidpercDP(0.70)==true) {System.out.printf("FuncID 072 - Paid 70perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 072 - Paid 70perc DP/CO : False"+ "\n"); 
						break; }}			  

					else if (FuncID.equals("074")) { 
						if (unit_haspaidpercDP(0.30)==true) {System.out.printf("FuncID 074 - Paid 30perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 074 - Paid 30perc DP/CO : False"+ "\n");break; }}

					else if (FuncID.equals("075")) { 
						if (unit_haspaidpercDP(0.60)==true) {System.out.printf("FuncID 075 - Paid 60perc DP/CO : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 075 - Paid 60perc DP/CO : False"+ "\n");break; }}

					else if (FuncID.equals("076")) { //last checked : 09-23-2016
						if (unit_hasBankLOG()==true) {System.out.printf("FuncID 076 - Unit has Bank LOG or NOA : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 076 -  Unit has Bank LOG or NOA : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("077")) { //last checked : 09-23-2016
						if (unit_hasSignedBankLOG()==true) {System.out.printf("FuncID 077 - Unit has Signed Bank LOG or NOA : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 077 -  Unit has Signed Bank LOG or NOA : False"+ "\n"); 
						break; }}		  

					else if (FuncID.equals("078")) { //last checked : 09-23-2016
						if (unit_isLoanTakeout()==true) {System.out.printf("FuncID 078 - Loan Take Out (Bank) / Full Settled : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 078 -  Loan Take Out (Bank) / Full Settled : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("079")) { //last checked : 09-23-2016
						if (unit_hasDOAS()==true) {System.out.printf("FuncID 079 - Unit Has Signed DOAS : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 079 - Unit Has Signed DOAS : False"+ "\n"); 
						break; }}

					else if (FuncID.equals("080")) { //added : 04-06-2017
						if (unit_loandFiledtoBank()==true) {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : TRUE"+ "\n\n"); } 
						else {System.out.printf("FuncID 080 - Unit Loan Filed to Bank : False"+ "\n"); 
						break; }}			

					else {System.out.printf("FuncID Not Found!"+ "\n\n"); }


					if (x==NoOfCond) {				
						pgUpdate db = new pgUpdate();	
						updateScheduleDetail(PBL_ID, SeqNo, CommType, db);
						db.commit();				
						System.out.printf("Account qualified for Commission"+ "\n\n");
						break;}

					Thread.sleep(2000);

					x++;

				}

				return null;
			}
		}; 

		sw.execute(); 
	}

}