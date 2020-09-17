package com.stone.mynotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.stone.mynotes.R;
import com.stone.mynotes.deligate.SaveNotes;
import com.stone.mynotes.dto.Note;
import com.stone.mynotes.model.AppModel;
import com.stone.mynotes.utils.AppUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class CreateNoteActivity extends AppCompatActivity implements SaveNotes {

    @BindView(R.id.imageBack)
    ImageView imageBack;
    @BindView(R.id.imageSave)
    ImageView imageSave;
    @BindView(R.id.inputNoteTitle)
    EditText inputNoteTitle;
    @BindView(R.id.inputNote)
    EditText inputNote;
    @BindView(R.id.textDateTime)
    TextView textDateTime;
    @BindView(R.id.layoutMiscellaneous)
    LinearLayout miscellaneous;
    @BindView(R.id.imageColor1)
    ImageView imageColor1;
    @BindView(R.id.imageColor2)
    ImageView imageColor2;
    @BindView(R.id.imageColor3)
    ImageView imageColor3;
    @BindView(R.id.imageColor4)
    ImageView imageColor4;
    @BindView(R.id.imageColor5)
    ImageView imageColor5;
    @BindView(R.id.viewColor1)
    View viewColor1;
    @BindView(R.id.viewColor2)
    View viewColor2;
    @BindView(R.id.viewColor3)
    View viewColor3;
    @BindView(R.id.viewColor4)
    View viewColor4;
    @BindView(R.id.viewColor5)
    View viewColor5;
    @BindView(R.id.scrollView2)
    ScrollView layoutCreateNote;
    @BindView(R.id.imageNote)
    ImageView imageNote;
    @BindView(R.id.layoutAddImage)
    LinearLayout layoutAddImage;
    @BindView(R.id.layoutAddUrl)
    LinearLayout layoutAddUrl;
    @BindView(R.id.layoutWebURL)
    LinearLayout layoutWebURL;
    @BindView(R.id.textWebURL)
    TextView textWebURL;


    private String selectedNoteColor = "#333333";
    public static final int PICK_IMAGE = 1;
    private String picturePath = "";
    private AppModel appModel;
    private AlertDialog dialogAddURL;
    private static Note note = new Note();
    private static int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        ButterKnife.bind(this);
        textDateTime.setText(AppUtils.getDateFormat());
        appModel = AppModel.getInstance(this);
        if (position != -1) {
            Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
            doUpdate(note);
        }

    }

    private void doUpdate(Note updateNote) {
        inputNoteTitle.setText(updateNote.getTitle());
        inputNote.setText(updateNote.getNoteText());
        textDateTime.setText(updateNote.getDateTime());
        picturePath = updateNote.getImagePath();
        if (!updateNote.getImagePath().equals("")) {
            imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
            imageNote.setVisibility(View.VISIBLE);
        }
        if (!updateNote.getWebLink().equals("")) {
            textWebURL.setText(updateNote.getWebLink());
            layoutWebURL.setVisibility(View.VISIBLE);
        }
    }


    public static Intent goToCreateNoteActivity(Context context) {
        Intent intent = new Intent(context, CreateNoteActivity.class);
        return intent;

    }

    public static Intent goToCreateNoteWithData(Context context, Note no, int posi) {
        Intent intent = new Intent(context, CreateNoteActivity.class);
        note = no;
        position = posi;
        return intent;
    }

    @OnClick(R.id.layoutAddUrl)
    void clickAddURL() {
        clickMiscellaneous();
        if (dialogAddURL == null) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_add_url, findViewById(R.id.layoutAddUrlContainer));
            builder.setView(view);
            dialogAddURL = builder.create();
            if (dialogAddURL.getWindow() != null) {
                dialogAddURL.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            EditText inputURL = view.findViewById(R.id.inputURL);
            inputURL.requestFocus();

            TextView textAdd = view.findViewById(R.id.textAdd);
            TextView textCancel = view.findViewById(R.id.textCancel);

            textAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputURL.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateNoteActivity.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    } else if (!Patterns.WEB_URL.matcher(inputURL.getText().toString()).matches()) {
                        Toast.makeText(CreateNoteActivity.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                    } else {

                        textWebURL.setText(inputURL.getText().toString());
                        layoutWebURL.setVisibility(View.VISIBLE);
                        dialogAddURL.dismiss();
                    }
                }
            });
            textCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddURL.dismiss();
                }
            });
            dialogAddURL.show();

        }

    }

    @OnClick(R.id.imageBack)
    void clickImageBack() {
        onBackPressed();
    }

    @OnClick(R.id.layoutAddImage)
    void clickLayoutAddImage() {
        clickMiscellaneous();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            selectImage();
        }
    }

    @OnClick(R.id.imageSave)
    void clickImageSave() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            inputNoteTitle.setError("note title cannot be empty");
            return;
        } else if (inputNote.getText().toString().trim().isEmpty()) {
            inputNote.setError("note  cannot be empty");
            return;
        }

        String webLink = "";
        if (layoutWebURL.getVisibility() == View.VISIBLE) {
            webLink = textWebURL.getText().toString();
        }

        if (position == -1) {
            Note not1 = new Note.Builder()
                    .title(inputNoteTitle.getText().toString())
                    .noteText(inputNote.getText().toString())
                    .dateTime(textDateTime.getText().toString())
                    .colour(selectedNoteColor)
                    .imagePath(picturePath)
                    .webLink(webLink)
                    .build();
            appModel.InsertNote(not1, this);
        } else {

            Note not = new Note.Builder()
                    .id(note.getId())
                    .title(inputNoteTitle.getText().toString())
                    .noteText(inputNote.getText().toString())
                    .dateTime(textDateTime.getText().toString())
                    .colour(selectedNoteColor)
                    .imagePath(picturePath)
                    .webLink(webLink)
                    .build();
            appModel.UpdateNote(not, this);
        }
    }

    @OnLongClick({R.id.layoutWebURL, R.id.imageNote})
    void deleteURl(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false);
        switch (view.getId()) {
            case R.id.imageNote:
                builder.setTitle("Delete Image!!")
                        .setMessage("Are you sure to delete this image")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageNote.setImageResource(0);
                                imageNote.setVisibility(View.GONE);
                                picturePath = "";
                            }
                        });

                break;
            case R.id.layoutWebURL:
                builder.setTitle("Delete URL!!")
                        .setMessage("Are you sure to delete this URL")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textWebURL.setText("");
                                layoutWebURL.setVisibility(View.GONE);
                            }
                        });
                break;
        }
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .show();


    }

    @OnClick(R.id.layoutMiscellaneous)
    void clickMiscellaneous() {
        BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(miscellaneous);
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick(R.id.viewColor1)
    void clickViewColor1() {
        selectedNoteColor = "#333333";
        layoutCreateNote.setBackgroundResource(R.color.colorDefaultNoteColor);
        imageColor1.setImageResource(R.drawable.ic_done);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    @OnClick(R.id.viewColor2)
    void clickViewColor2() {
        selectedNoteColor = "#F44336";
        layoutCreateNote.setBackgroundResource(R.color.colorNote2);
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(R.drawable.ic_done);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    @OnClick(R.id.viewColor3)
    void clickViewColor3() {
        selectedNoteColor = "#9C27B0";
        layoutCreateNote.setBackgroundResource(R.color.colorNote3);
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(R.drawable.ic_done);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(0);
    }

    @OnClick(R.id.viewColor4)
    void clickViewColor4() {
        selectedNoteColor = "#FF5722";
        layoutCreateNote.setBackgroundResource(R.color.colorNote4);
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(R.drawable.ic_done);
        imageColor5.setImageResource(0);
    }

    @OnClick(R.id.viewColor5)
    void clickViewColor5() {
        selectedNoteColor = "#FF9800";
        layoutCreateNote.setBackgroundResource(R.color.colorNote5);
        imageColor1.setImageResource(0);
        imageColor2.setImageResource(0);
        imageColor3.setImageResource(0);
        imageColor4.setImageResource(0);
        imageColor5.setImageResource(R.drawable.ic_done);
    }

    @Override
    public void success(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    void selectImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageNote.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageNote.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            }
        }
    }
}