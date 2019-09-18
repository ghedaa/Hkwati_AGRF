package sa.elm.hakuati.toolbar_mainactivity.storyActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sa.elm.hakuati.toolbar_mainactivity.R;
import sa.elm.hakuati.toolbar_mainactivity.innerStroeyActivity.InnerStoryActivity;
import sa.elm.hakuati.toolbar_mainactivity.mainActivity.MainActivity;

import static android.Manifest.permission_group.CAMERA;

public class SignUpActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText userNameED;
    EditText emailED;
    EditText passwordED;
    Button regBtn;
    TextView price;
    RadioButton childRadio, orgRadio;
    RelativeLayout rt, child;

    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;


    private File imageFile;
    DatePicker picker;
    Button btnGet;
    TextView tvw;

    final static FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference KidsCollectionRef = db.collection("kids");
    DocumentReference userDocumentRef = db.document("kids/user");
    private static final String TAG = SignUpActivity.class.getSimpleName();

    ImageView imgBtn;

    private static final int INTENT_GALLERY = 301;
    private static final int INTENT_CAMERA = 401;


    String[] textArray = {"Zebra", "Fox", "Bear", "Penguin"};
    Integer[] imageArray = {R.drawable.zebra, R.drawable.fox,
            R.drawable.zoo, R.drawable.penguin};

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.createAccount));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNameED = findViewById(R.id.nameSignUpHint);
        emailED = findViewById(R.id.emailSignUpHint);
        passwordED = findViewById(R.id.passwordSignUpHint);
        regBtn = findViewById(R.id.regBtn);
        price = findViewById(R.id.price);


        childRadio = findViewById(R.id.radioChild);
        childRadio.setOnClickListener(this);

        orgRadio = findViewById(R.id.radioOrg);
        orgRadio.setOnClickListener(this);


        imgBtn = findViewById(R.id.imgLoadPicture);


// OnClick Image
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCameraChooser();

            }
        });// OnClick Image finish


        rt = findViewById(R.id.org);
        child = findViewById(R.id.child);
        child.setVisibility(View.GONE);
        rt.setVisibility(View.GONE);



        //get the spinner from the xml.
        Spinner dropdownOrg = findViewById(R.id.spinnerOrg);

//create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.Subscription), "50-100", "100-500", getString(R.string.morethan)+" 500"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdownOrg.setAdapter(adapter);
        dropdownOrg.setOnItemSelectedListener(this);


        ImageView imageView = findViewById(R.id.spinnerImages);
        Spinner spinnerImage = findViewById(R.id.spinnerChild);

        SpinnerAdapter adapterChild = new SpinnerAdapter(this, R.layout.spinner_value_layout, textArray, imageArray);

        spinnerImage.setAdapter(adapterChild);

        spinnerImage.setOnItemSelectedListener(this);


        regBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (isValid(emailED.getText().toString()) && passwordED.getText().toString() != "") {

                    Toast.makeText(getApplicationContext(), "Success :)", Toast.LENGTH_LONG).show();

                }// end if
                else

                    Toast.makeText(getApplicationContext(), "Filled :)", Toast.LENGTH_LONG).show();


            }//end onClick


        });

        tvw= findViewById(R.id.textView1);
        picker= findViewById(R.id.datePicker1);
        btnGet= findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dob = Calendar.getInstance();
                Calendar today = Calendar.getInstance();

                int age = today.get(Calendar.YEAR) - picker.getYear();

                if (today.get(Calendar.DAY_OF_MONTH) < picker.getDayOfMonth()|| today.get(Calendar.MONTH) < picker.getMonth()){
                    age--;
                }

                Integer ageInt = new Integer(age);
                String ageS = ageInt.toString();

                tvw.setText("العمر: "+ ageS);
            }
        });

    }//end onCreate






    private void openCameraChooser() {
        //TODO run time permission
        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 100);

            return;
        }
        showPhotoOptionDialog();
    }


    private void showPhotoOptionDialog() {
        final CharSequence[] items = {getString(R.string.Camera), getString(R.string.Gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if (items[item].equals(getString(R.string.Camera))) {
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.Gallery))) {
                    galleryIntent();
                }
            }

        });
        builder.show();
    }

    private void galleryIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, INTENT_GALLERY);
    }//end galleryIntent

    private void cameraIntent() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, INTENT_CAMERA);


    }//end cameraIntent
    // for uploading a pic


    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }//end isValid


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerOrg:
                // if (view.getId() == R.id.spinnerOrg){
                // first spinner selected
                switch (i) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        price.setText(R.string.totalPrice);
                        break;

                    case 1:
                        // Whatever you want to happen when the first item gets selected
                        price.setText("100" + getString(R.string.Rs));
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        price.setText("1000" + getString(R.string.Rs));

                        break;
                    case 3:
                        // Whatever you want to happen when the thrid item gets selected
                        price.setText("10,000" + getString(R.string.Rs));
                        break;

                }
                break;
            case R.id.spinnerChild: {
                //   else if (view.getId() == R.id.spinnerChild) {
                // second spinner selected
                switch (i) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        imgBtn.setImageResource(R.drawable.zebra);
                        break;

                    case 1:
                        // Whatever you want to happen when the first item gets selected
                        imgBtn.setImageResource(R.drawable.fox);
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        imgBtn.setImageResource(R.drawable.zoo);

                        break;
                    case 3:
                        // Whatever you want to happen when the third item gets selected
                        imgBtn.setImageResource(R.drawable.penguin);
                        break;
                }

                break;
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioChild:

                child.setVisibility(View.VISIBLE);
                rt.setVisibility(View.GONE);

                break;
            case R.id.radioOrg:
                rt = findViewById(R.id.org);
                child = findViewById(R.id.child);
                rt.setVisibility(View.VISIBLE);

                child.setVisibility(View.GONE);



                break;
        }//end switch
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }//end if statement
        if (requestCode == INTENT_GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //String path = saveImage(bitmap);
                    Toast.makeText(SignUpActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imgBtn.setImageBitmap(bitmap);
                    boolean isSelectImage = true;
                    persistImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }//end if statement

        } else if (requestCode == INTENT_CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgBtn.setImageBitmap(thumbnail);
            //saveImage(thumbnail);
            Toast.makeText(SignUpActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }//end if statement1
    }//end onActivityResult()

    private void persistImage(Bitmap bitmap) {
        File fileDir = getFilesDir();

        String name = "";
        if (userNameED.getText() != null) {
            name = userNameED.getText().toString();
            imageFile = new File(fileDir, name + ".png");
            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            }//end try
            catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }//end catch
        }//end if
    }//end FromBitmapToFile()

    public void Collections(){


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", userNameED);
        user.put("DOB", 1998);
        user.put("img", R.drawable.zebra);
        user.put("email", emailED);
        user.put("type", "child");


// Add a new document with a generated ID
        db.collection("kids")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        String id = KidsCollectionRef.document("user").getId();

        passData(id);

    }

    private void passData(String id) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

}//end class
