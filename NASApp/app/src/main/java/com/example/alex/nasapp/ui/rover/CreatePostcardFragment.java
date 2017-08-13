package com.example.alex.nasapp.ui.rover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.helpers.ImageHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CreatePostcardFragment extends Fragment {
    private static final String SELECTED_PHOTO_SRC = "selected_photo";

    public CreatePostcardFragment () {

    }

    TextView postCardTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_create_postcard, container, false);

        final String imageSrc = getArguments().getString(SELECTED_PHOTO_SRC);
        final RelativeLayout postcardLayout = (RelativeLayout) rootView.findViewById(R.id.postCardRelativeLayout);
        final ImageView postCardImageView = (ImageView) rootView.findViewById(R.id.postCardImageView);
        postCardTextView = (TextView) rootView.findViewById(R.id.postCardTextView);
        EditText postCardEditText = (EditText) rootView.findViewById(R.id.postCardEditText);
        final Button submitButton = (Button) rootView.findViewById(R.id.submitButton);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.colorSpinner);
        Picasso.with(getActivity()).load(imageSrc).into(postCardImageView);

        postCardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postCardTextView.setText(s);
                submitButton.setEnabled(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.text_colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(5);

        //handling text color change
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        setPostCardTextColor(R.color.colorBlack);
                        break;
                    case 1:
                        setPostCardTextColor(R.color.colorBlue);
                        break;
                    case 2:
                        setPostCardTextColor(R.color.colorRed);
                        break;
                    case 3:
                        setPostCardTextColor(R.color.colorGrey);
                        break;
                    case 4:
                        setPostCardTextColor(R.color.colorOrange);
                        break;
                    case 5:
                        setPostCardTextColor(R.color.colorWhite);
                        break;
                    default:
                        setPostCardTextColor(R.color.colorWhite);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File postCard = createPostCardImage (postcardLayout);
                sendPostcardByEmail (postCard);
            }
        });

        return rootView;
    }

    private void setPostCardTextColor(int colorId) {
        postCardTextView.setTextColor(ContextCompat.getColor(getContext(), colorId));
    }

    private void sendPostcardByEmail(final File postCard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter e-mail address");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userMail = input.getText().toString();
                String subject = "Postcard from Mars";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse ("mailto:" + userMail));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(postCard));
                startActivity(Intent.createChooser(emailIntent, "Choose app to send e-mail:"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private File createPostCardImage(View view) {
        Bitmap bitmap = ImageHelper.createBitmapFromView(view);
        return ImageHelper.convertBitmapIntoJPEG(getActivity(), bitmap);
    }

    public static CreatePostcardFragment newInstance (String src) {
        CreatePostcardFragment createPostcardFragment = new CreatePostcardFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_PHOTO_SRC, src);
        createPostcardFragment.setArguments(args);
        return createPostcardFragment;
    }
}
