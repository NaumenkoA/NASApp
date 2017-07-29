package com.example.alex.nasapp.ui.asteroid;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.helpers.StringHelper;
import com.example.alex.nasapp.model.asteroid.Asteroid;


public class SMSAlertFragment extends Fragment {


    private static final String SELECTED_ASTEROID = "selected_asteroid";
    private static final int SEND_SMS_PERMISSION_REQUEST = 21;
    private static final int READ_CONTACTS_PERMISSION_REQUEST = 22;
    private static final int PICK_CONTACT_REQUEST = 23;

    private EditText phoneEditText;
    private EditText smsEditText;
    private Button sendSMSbutton;
    private ImageButton pickContactButton;

    public SMSAlertFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms_alert, container, false);
        smsEditText = (EditText) rootView.findViewById(R.id.smsEditText);
        sendSMSbutton = (Button) rootView.findViewById(R.id.sendSMSButton);
        phoneEditText = (EditText) rootView.findViewById(R.id.phoneEditText);
        pickContactButton = (ImageButton) rootView.findViewById(R.id.pickContactsButton);

        Asteroid asteroid = getArguments().getParcelable(SELECTED_ASTEROID);
        String smsText = "";
        if (asteroid!=null) {
            smsText = getActivity().getResources().getString(R.string.sms_text
                    , StringHelper.changeNumberOfCharsAfterDot
                            (asteroid.getEstimatedDiameter().getMeters().getEstimatedDiameterMax().toString(), 0)
                    , StringHelper.changeNumberOfCharsAfterDot
                            (asteroid.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerSecond(), 1));
        }
        smsEditText.setText(smsText);

        pickContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (checkPermission(Manifest.permission.READ_CONTACTS, READ_CONTACTS_PERMISSION_REQUEST)) {
                        pickContact();
                    }
                } else {
                   pickContact();
                }
            }
        });


        sendSMSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (checkPermission(Manifest.permission.SEND_SMS, SEND_SMS_PERMISSION_REQUEST)) {
                        sendSMS();
                    }
                } else {
                    sendSMS();
                }
            }
        });

        return rootView;
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    private void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneEditText.getText().toString(), null,
                smsEditText.getText().toString(), null, null);
    }

    private boolean checkPermission (String permissionType,int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permissionType)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permissionType},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    public static SMSAlertFragment newInstance (Asteroid asteroid) {
        SMSAlertFragment smsAlertFragment = new SMSAlertFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_ASTEROID, asteroid);
        smsAlertFragment.setArguments(args);
        return smsAlertFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri result = data.getData();
                    Cursor cursor = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone._ID + "=?",
                            new String[]{result.getLastPathSegment()}, null);

                    if (cursor.getCount() >= 1 && cursor.moveToFirst()) {
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneEditText.setText(number);
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        sendSMS();
                }
            }
            break;
            case READ_CONTACTS_PERMISSION_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickContact();
                }
            }
        }
    }
}
