package com.example.alex.nasapp.ui.asteroid;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.helpers.StringHelper;
import com.example.alex.nasapp.model.asteroid.Asteroid;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;


public class SMSAlertFragment extends Fragment {


    private static final String SELECTED_ASTEROID = "selected_asteroid";
    public static final String SENT = "sent";
    private static final int SEND_SMS_PERMISSION_REQUEST = 21;
    private static final int READ_CONTACTS_PERMISSION_REQUEST = 22;
    private static final int PICK_CONTACT_REQUEST = 23;

    private EditText phoneEditText;
    private EditText smsEditText;
    private Button sendSMSbutton;
    private ImageButton pickContactButton;
    private RelativeLayout rootLayout;
    BroadcastReceiver smsSentBroadcastReceiver;

    private DisposableObserver<Boolean> disposable;
    Observable<CharSequence> observablePhoneEditText;
    Observable<CharSequence> observableSmsEditText;

    public SMSAlertFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms_alert, container, false);
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
        smsEditText = (EditText) rootView.findViewById(R.id.smsEditText);
        sendSMSbutton = (Button) rootView.findViewById(R.id.sendSMSButton);
        phoneEditText = (EditText) rootView.findViewById(R.id.phoneEditText);
        pickContactButton = (ImageButton) rootView.findViewById(R.id.pickContactsButton);

        observablePhoneEditText = RxTextView.textChanges(phoneEditText);
        observableSmsEditText = RxTextView.textChanges(smsEditText);

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

        disposable = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean value) {sendSMSbutton.setEnabled(value);
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        };

        return rootView;
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Register for SMS send action
        smsSentBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String result = "";

                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        result = "SMS alert successfully sent";
                        break;

                    default:
                        result = "Some error occurred. Please check the recipient number and try again";

                }
                Snackbar.make(rootLayout, result, Snackbar.LENGTH_SHORT).show();
            }

        };
        getActivity().registerReceiver(smsSentBroadcastReceiver, new IntentFilter(SENT));

        // subscribe for editText events

        Observable.combineLatest(
                observablePhoneEditText, observableSmsEditText, new BiFunction<CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence, CharSequence charSequence2) {
                        return  (charSequence.length() > 0
                                && charSequence2.length() > 0);

                    }
                }).subscribe (disposable);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(smsSentBroadcastReceiver);

        disposable.dispose();
    }

    private void sendSMS() {
        Intent sentIntent = new Intent(SENT);

        PendingIntent sentPI = PendingIntent.getBroadcast(
                getActivity().getApplicationContext(), 0, sentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneEditText.getText().toString(), null,
                smsEditText.getText().toString(), sentPI, null);

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
