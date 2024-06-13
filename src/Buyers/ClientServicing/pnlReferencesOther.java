package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components._JTabbedPane;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferencesOther extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 5817889377568614706L;

	private _JTabbedPane tabCenterRefOther;
	//private JPanel pnlMain;
	private pnlReferences_BankAccounts pnlBankAccnt;
	private pnlReferences_CreditsCardsOwned pnlCardsOwned;
	private pnlReferences_RealEstateOwned pnlRealEstateOwned;
	private pnlReferences_LoanCredits pnlLoanCredits;
	private pnlReferences_LoanCreditsRef pnlLoanCreditsRef;
	private pnlReferences_TradeReference pnlTradeRef;
	private pnlReferences_CharacterReference pnlCharacterRef;
	private ClientInformation ci;

	public pnlReferencesOther(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferencesOther(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferencesOther(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferencesOther(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	@Override
	public void initGUI() {
		this.setPreferredSize(new Dimension(707, 317));
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			tabCenterRefOther = new _JTabbedPane();
			this.add(tabCenterRefOther, BorderLayout.CENTER);
			tabCenterRefOther.setPreferredSize(new Dimension(644, 299));
			{
				pnlBankAccnt = new pnlReferences_BankAccounts(ci);
				tabCenterRefOther.addTab("Bank Accounts", pnlBankAccnt);
				pnlBankAccnt.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlCardsOwned = new pnlReferences_CreditsCardsOwned(ci);
				tabCenterRefOther.addTab("Credit Cards Owned", pnlCardsOwned);
				pnlCardsOwned.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlRealEstateOwned = new pnlReferences_RealEstateOwned(ci);
				tabCenterRefOther.addTab("Real Estate Owned", pnlRealEstateOwned);
				pnlRealEstateOwned.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlLoanCredits = new pnlReferences_LoanCredits(ci);
				tabCenterRefOther.addTab("Loan/Credits", pnlLoanCredits);
				pnlLoanCredits.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlLoanCreditsRef = new pnlReferences_LoanCreditsRef(ci);
				tabCenterRefOther.addTab("Loan/Credit Reference", pnlLoanCreditsRef);
				pnlLoanCreditsRef.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlTradeRef = new pnlReferences_TradeReference(ci);
				tabCenterRefOther.addTab("Trade Reference", pnlTradeRef);
				pnlTradeRef.setPreferredSize(new Dimension(128, 192));
			}
			{
				pnlCharacterRef = new pnlReferences_CharacterReference(ci);
				tabCenterRefOther.addTab("Character Reference", pnlCharacterRef);
				pnlCharacterRef.setPreferredSize(new Dimension(128, 192));
			}
			tabCenterRefOther.addChangeListener(new ChangeListener() {
				
				public void stateChanged(ChangeEvent e) {
					int selectedTab = ((_JTabbedPane)e.getSource()).getSelectedIndex();
					if(selectedTab == 0){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 1){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 2){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 3){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 4){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 5){
						ci.btnState(true, false, false, false, false);
					}
					if(selectedTab == 6){
						ci.btnState(true, false, false, false, false);
					}
				}
			});
		}
		pnlStateRefOther(true, true, true, true, true, true, true);
	}//END OF INIT GUI

	public void pnlStateRefOther(Boolean ref_bankaccnt, Boolean cards_owned, Boolean real_estate_owned, Boolean loan_credits, Boolean loan_credits_ref, Boolean trade_ref, Boolean char_ref){
		tabCenterRefOther.setEnabledAt(0, ref_bankaccnt);
		tabCenterRefOther.setEnabledAt(1, cards_owned);
		tabCenterRefOther.setEnabledAt(2, real_estate_owned);
		tabCenterRefOther.setEnabledAt(3, loan_credits);
		tabCenterRefOther.setEnabledAt(4, loan_credits_ref);
		tabCenterRefOther.setEnabledAt(5, trade_ref);
		tabCenterRefOther.setEnabledAt(6, char_ref);
	}
	
	public void newRefother(){
		if (tabCenterRefOther.getSelectedIndex()== 0){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(true, false, false, false, false, false, false);
			pnlBankAccnt.clearFields();
			pnlBankAccnt.newBankAccnt();
		}
		if (tabCenterRefOther.getSelectedIndex()== 1){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, true, false, false, false, false, false);
			pnlCardsOwned.clearFields();
			pnlCardsOwned.newCreditCardsOwned();
		}
		if (tabCenterRefOther.getSelectedIndex()== 2){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, false, true, false, false, false, false);
			pnlRealEstateOwned.clearFields();
			pnlRealEstateOwned.newRealEstate();
		}
		if (tabCenterRefOther.getSelectedIndex()== 3){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, false, false, true, false, false, false);
			pnlLoanCredits.clearFields();
			pnlLoanCredits.newLoanCredits();
		}
		if (tabCenterRefOther.getSelectedIndex() == 4){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, false, false, false, true, false, false);
			pnlLoanCreditsRef.clearFields();
			pnlLoanCreditsRef.newLoanCreditsRef();
		}
		if (tabCenterRefOther.getSelectedIndex() == 5){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, false, false, false, false, true, false);
			pnlTradeRef.clearFields();
			pnlTradeRef.newTradeRef();
		}
		if (tabCenterRefOther.getSelectedIndex() == 6){
			ci.btnState(false, false, false, true, true);
			pnlStateRefOther(false, false, false, false, false, false, true);
			pnlCharacterRef.clearFields();
			pnlCharacterRef.newCharRef();
		}
	}

	public void edit(String entity_id){//EDITING OF DATA
		if(tabCenterRefOther.getSelectedIndex() == 0){
			pnlBankAccnt.editBankList();
			pnlStateRefOther(true, false, false, false, false, false, false);
		}
		if(tabCenterRefOther.getSelectedIndex() == 1){
			pnlCardsOwned.editCreditCardsOwned();
			pnlStateRefOther(false, true, false, false, false, false, false);
		}
		if(tabCenterRefOther.getSelectedIndex() ==2){
			pnlRealEstateOwned.editRealEstate();
			pnlStateRefOther(false, false, true, false, false, false, false);
		}
		if(tabCenterRefOther.getSelectedIndex() == 3){
			pnlLoanCredits.editLoanCredits();
			pnlStateRefOther(false, false, false, true, false, false, false);
		}
		
		if(tabCenterRefOther.getSelectedIndex() ==4){
			pnlLoanCreditsRef.editLoanCreditsRef();
			pnlStateRefOther(false, false, false, false, true, false, false);
		}
		if(tabCenterRefOther.getSelectedIndex() == 5){
			pnlTradeRef.editTradeRef();
			pnlStateRefOther(false, false, false, false, false, true, false);
		}
		if(tabCenterRefOther.getSelectedIndex() == 6){
			pnlCharacterRef.editCharRef();
			pnlStateRefOther(false, false, false, false, false, false, true);
		}
	}

	public boolean save(String entity_id) {//SAVING OF DATA IN THE REFERENCES OTHER PANEL
		if (tabCenterRefOther.getSelectedIndex() == 0){
			if(pnlBankAccnt.toSave()){
				if (pnlBankAccnt.saveBankAccnts(entity_id)){
					pnlBankAccnt.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if (tabCenterRefOther.getSelectedIndex()== 1){
			if (pnlCardsOwned.toSave()){
				if (pnlCardsOwned.saveCreditCardsOwned(entity_id)){
					pnlCardsOwned.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if(tabCenterRefOther.getSelectedIndex()== 2){
			if(pnlRealEstateOwned.toSave()){
				if (pnlRealEstateOwned.saveRealEstateOwned(entity_id)){
					pnlRealEstateOwned.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if (tabCenterRefOther.getSelectedIndex() == 3){
			if(pnlLoanCredits.toSave()){
				if (pnlLoanCredits.saveLoanCredits(entity_id)){
					pnlLoanCredits.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if (tabCenterRefOther.getSelectedIndex() == 4){
			if(pnlLoanCreditsRef.toSave()){
				if (pnlLoanCreditsRef.saveLoanCreditsRef(entity_id)){
					pnlLoanCreditsRef.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if (tabCenterRefOther.getSelectedIndex() == 5){
			if(pnlTradeRef.toSave()){
				if(pnlTradeRef.saveTradeRef(entity_id)){
					pnlTradeRef.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		if (tabCenterRefOther.getSelectedIndex() == 6){
			if(pnlCharacterRef.toSave()){
				if(pnlCharacterRef.saveCharRef(entity_id)){
					pnlCharacterRef.clearFields();
					ci.btnState(true, false, false, false, false);
					pnlStateRefOther(true, true, true, true, true, true, true);
				}
			}
		}
		return true;
	}
	
	public void cancelRefOther(){//CANCELATION FOR THE TABS IN THE REFERENCES OTHER PANEL 
		if (tabCenterRefOther.getSelectedIndex()== 0){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlBankAccnt.cancelBankAccnt();
		}
		if (tabCenterRefOther.getSelectedIndex()== 1){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlCardsOwned.cancelCardsOwned();
		}
		if (tabCenterRefOther.getSelectedIndex()== 2){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlRealEstateOwned.cancelRealEstate();
		}
		if (tabCenterRefOther.getSelectedIndex()== 3){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlLoanCredits.cancelLoanCredits();
		}
		if (tabCenterRefOther.getSelectedIndex() == 4){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlLoanCreditsRef.cancelLoanCreditsRef();
		}
		if (tabCenterRefOther.getSelectedIndex() == 5){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlTradeRef.cancelTradRef();
		}
		if (tabCenterRefOther.getSelectedIndex() == 6){
			ci.btnState(true, false, false, false, false);
			pnlStateRefOther(true, true, true, true, true, true, true);
			ci.pnlState(true, true, true, true, true, true, true, true);
			pnlCharacterRef.cancelCharRef();
		}
	}
	
	public void displayRefOther(String entity_id){//DISPLAYS THE DETAILS FOR ALL TABS IN THE REFERENCES OTHER PANEL
		pnlBankAccnt.displayBankAccnts(entity_id);
		pnlLoanCreditsRef.displayLoanCreditsRef(entity_id);
		pnlCharacterRef.displayCharacterRef(entity_id);
		pnlTradeRef.displayReferences_TradeRef(entity_id);
		pnlLoanCredits.displayLoanCredits(entity_id);
		pnlRealEstateOwned.displayRealEstateOwned(entity_id);
		pnlCardsOwned.displayCreditCardsOwned(entity_id);
	}
	
	public void clearRefOther(){//CLEARS THE FIELD FOR ALL TABS IN THE REFERENCES OTHER PANEL
		pnlBankAccnt.clearFields();
		pnlLoanCreditsRef.clearFields();
		pnlCharacterRef.clearFields();
		pnlTradeRef.clearFields();
		pnlLoanCredits.clearFields();
		pnlRealEstateOwned.clearFields();
		pnlCardsOwned.clearFields();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
