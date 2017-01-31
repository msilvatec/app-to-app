package com.example.mrdsilva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import br.com.stone.methods.CancellationResponse;
import br.com.stone.methods.PrintResponse;
import br.com.stone.methods.TransactionResponse;
import br.com.stone.xml.ReturnOfCancellationXml;
import br.com.stone.xml.ReturnOfPrintXml;
import br.com.stone.xml.ReturnOfTransactionXml;

public class MainActivity extends Activity implements OnClickListener{
	
	Button transaction;
	Button cancellation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		instanceViews();
	}

	private void instanceViews() {
		
		transaction  = (Button) findViewById(R.id.transationButton);
		cancellation = (Button) findViewById(R.id.cancellationButton);
		
		transaction.setOnClickListener(this);
		cancellation.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		
		Intent intent;

		switch (v.getId()) {
		
		case R.id.transationButton:
			intent = new Intent(getApplicationContext(), TransactionActivity.class);
			startActivity(intent);
			break;

		case R.id.cancellationButton:
			intent = new Intent(getApplicationContext(), CancellationActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	protected void onResume() {
		super.onResume();
		getExternalInformations();
	}
	
	public void getExternalInformations() {

		Bundle backActivity = getIntent().getExtras();

		if(backActivity != null) {
			
			String xmlTransaction  = backActivity.getString("xmlTransaction");
			String xmlCancellation = backActivity.getString("xmlCancellation");
			String xmlPrint        = backActivity.getString("xmlPrint");
			
			// if a transaction
			if(xmlTransaction != null && !xmlTransaction.equals("")) {
				
				ReturnOfTransactionXml mReturnOfTransactionXml = new ReturnOfTransactionXml();
				mReturnOfTransactionXml = TransactionResponse.getTransaction(this, xmlTransaction, backActivity);
				
				Log.i("sdk_stone",
						"\n\n======== Dados recebidos SDK ========"
						+ "\nValor               : " + mReturnOfTransactionXml.amount
						+ "\nARN                 : " + mReturnOfTransactionXml.arn
						+ "\nParcelas            : " + mReturnOfTransactionXml.parcel
						+ "\nBandeira            : " + mReturnOfTransactionXml.flag
						+ "\nCA                  : " + mReturnOfTransactionXml.ca
						+ "\nStatus              : " + mReturnOfTransactionXml.status
						+ "\nData                : " + mReturnOfTransactionXml.date
						+ "\nAmountOfInst.       : " + mReturnOfTransactionXml.amountOfInstallments
						+ "\nDemandId            : " + mReturnOfTransactionXml.demandId
//						+ "\nEmailSent           : " + mReturnOfTransactionXml.emailSent
						+ "\nTipo da transa��o   : " + mReturnOfTransactionXml.transactionType);
			}
			
			//if a cancellation
			if(xmlCancellation != null && !xmlCancellation.equals("")){
				
				ReturnOfCancellationXml mReturnOfCancellationXml = new ReturnOfCancellationXml();
				mReturnOfCancellationXml = CancellationResponse.getCancellation(this, xmlCancellation, backActivity);
				
				Log.i("sdk_stone",
						"\n\n======== Dados recebidos SDK ========"
						+ "\nCA      : " + mReturnOfCancellationXml.ca
						+ "\nARN     : " + mReturnOfCancellationXml.arn
						+ "\nStatus  : " + mReturnOfCancellationXml.status);
				
				
			}
			// if a print
			if (xmlPrint != null && !xmlPrint.equals("")){
				
				ReturnOfPrintXml returnOfPrintXml = new ReturnOfPrintXml();
				returnOfPrintXml = PrintResponse.getPrint(this, xmlPrint, backActivity);
				
				switch (returnOfPrintXml.printCode) {
				case 1:
					Toast.makeText(getApplicationContext(), "Impresso com sucesso!", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "Ocorreu um erro dutante a impress�o.", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "O pinpad n�o possui suporte para impress�o.", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
				
			}
			
		}
	}
}
