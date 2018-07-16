package psb.com.kidpaint.user.edit.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.ActivityCropImage;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;

public class FragmentEditUserInfo extends Fragment implements iVEditUserInfo {

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText editTextName, editTextLastName;
    private ImageView imageViewUserImg,camera;
    private Button buttonUserInfo;
    private TextView text_phone_number,error,title;
    private ProgressBar progressBar;
    private PEditUserInfo pUserInfo;
    private UserProfile userProfile;

    private static final int REQUEST_SELECT_IMAGE = 20;
    private Bitmap bitmapUserImage = null;
    private Uri uriUserImage;

    private String encodedImageData;

    private ParamsRegister paramsRegister;
    private ImageView girlImage, boyImage;
    private TextView girlText, boyText, birthDay, selectBirthDay;

    public FragmentEditUserInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pUserInfo = new PEditUserInfo(this);
        userProfile=new UserProfile(getContext());
        view = inflater.inflate(R.layout.fragment_get_user_info, container, false);
        setView();
        return view;
    }

    private void setView(){
        title = view.findViewById(R.id.text_toolbar_title);
        title.setText("ویرایش اطلاعات کاربری");
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextLastName = view.findViewById(R.id.edit_text_last_name);

        imageViewUserImg = view.findViewById(R.id.image_user_info_avatar);
        camera = view.findViewById(R.id.image_user_info_edit);
        text_phone_number = view.findViewById(R.id.text_phone_number);
        progressBar = view.findViewById(R.id.frg_registerUserInfo_progressBar);

        paramsRegister = new ParamsRegister();

        girlImage = view.findViewById(R.id.image_girl_normal);
        girlText = view.findViewById(R.id.text_girl);
        boyImage = view.findViewById(R.id.image_boy_normal);
        boyText = view.findViewById(R.id.text_boy);
        birthDay = view.findViewById(R.id.text_birthday);
        selectBirthDay = view.findViewById(R.id.selectBirthDay);
        if (userProfile.get_KEY_BRITH_DAY("").isEmpty()) {
            selectBirthDay.setVisibility(View.VISIBLE);
            birthDay.setVisibility(View.GONE);
            paramsRegister.setBirthDay(null);

        }else{
            selectBirthDay.setVisibility(View.GONE);
            birthDay.setVisibility(View.VISIBLE);
            birthDay.setText(userProfile.get_KEY_BRITH_DAY(""));
            paramsRegister.setBirthDay(userProfile.get_KEY_BRITH_DAY(""));
        }

        setSex(userProfile.get_KEY_SEX(true));

        paramsRegister.setMale(userProfile.get_KEY_SEX(true));

        text_phone_number.setText(userProfile.get_KEY_PHONE_NUMBER(""));

        editTextName.setText(userProfile.get_KEY_FIRST_NAME(""));
        editTextLastName.setText(userProfile.get_KEY_LAST_NAME(""));

        String imageUrl=userProfile.get_KEY_IMG_URL("avatar");
        if(!imageUrl.isEmpty() || imageUrl != null){
            Picasso.get().load(imageUrl).error(R.drawable.user_empty_gray).placeholder(R.drawable.user_empty_gray).into(imageViewUserImg, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                }
            });
        } else {
            Picasso.get().load(R.drawable.user_empty_gray).into(imageViewUserImg);
        }

        error = view.findViewById(R.id.text_error);
        imageViewUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getContext(), ActivityCropImage.class).putExtra("KEY_HAS_ASPECT", false),
                        REQUEST_SELECT_IMAGE);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getContext(), ActivityCropImage.class).putExtra("KEY_HAS_ASPECT", false),
                        REQUEST_SELECT_IMAGE);
            }
        });

        buttonUserInfo = view.findViewById(R.id.frg_registerUserInfo_sendBtn);
        buttonUserInfo.setText("ویرایش اطلاعات");

        buttonUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paramsRegister.getMale()==null) {
                    Toast.makeText(getContext(), "لطفا جنسیت خود را انتخاب کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateName()) {
                    return;
                }
                if (!validateLastName()) {
                    return;
                }
                if (paramsRegister.getBirthDay()==null) {
                    Toast.makeText(getContext(), "لطفا تاریخ تولد خود را انتخاب کنید", Toast.LENGTH_SHORT).show();
                    return;

                }

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                paramsRegister.setPhoneNumber(Utils.getStringPreference(getContext(),
                        Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, "-1"));
                paramsRegister.setEmail("");
                paramsRegister.setFirstName(editTextName.getText().toString());
                paramsRegister.setLastName(editTextLastName.getText().toString());
                paramsRegister.setImageUrl(encodedImageData);
                pUserInfo.setUserInfo(paramsRegister);
              //  Log.d("TAG", "onClickEdit: "+new Gson().toJson(paramsRegister));

            }
        });

      /*  pUserInfo.getUserInfo(Utils.getStringPreference(getContext(),Utils.KEY_REGISTER,
                Utils.KEY_PHONENUMBER,"-1"));*/


        girlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSex(false);
            }
        });
        boyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSex(true);
            }
        });

        selectBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker();
            }
        });
        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker();
            }
        });
    }

    private boolean validateName() {
        if (editTextName.getText().toString().trim().isEmpty()) {
            editTextName.setError("لطفا نام راوارد کنید");
            requestFocus(editTextName);
            return false;
        } else {
        }
        return true;
    }

    private boolean validateLastName() {
        if (editTextLastName.getText().toString().trim().isEmpty()) {
            editTextLastName.setError("لطفا نام خانوادگی راوارد کنید");
            requestFocus(editTextLastName);
            return false;
        } else {
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStartSetUserInfo() {
        //mListener.onStartSetUserInfo();
        error.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        buttonUserInfo.setEnabled(false);
    }

    @Override
    public void setUserInfoSuccess(UserInfo userInfo) {
        mListener.setUserInfoSuccess();
    }

    @Override
    public void setUserInfoFailed(String msg) {
        error.setText(msg);
        error.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        buttonUserInfo.setEnabled(true);
    }


    public void retryGetProfile(){
        pUserInfo.getUserInfo(Utils.getStringPreference(getContext(),
                Utils.KEY_REGISTER,Utils.KEY_PHONENUMBER, "-1"));
    }

    public void retrySetProfile(){
        paramsRegister = new ParamsRegister();
        paramsRegister.setPhoneNumber(Utils.getStringPreference(getContext(),
                Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, "-1"));
        paramsRegister.setEmail("");
        paramsRegister.setFirstName(editTextName.getText().toString());
        paramsRegister.setLastName(editTextLastName.getText().toString());
        paramsRegister.setImageUrl("");
        pUserInfo.setUserInfo(paramsRegister);
    }

    @Override
    public void onStartGetUserInfo() {
        mListener.onStartGetUserInfo();
    }

    @Override
    public void getUserInfoSuccess(UserInfo userInfo) {
        editTextName.setText(userInfo.getFirstName());
        editTextLastName.setText(userInfo.getLastName());
        if(!userInfo.getImageUrl().isEmpty() || userInfo.getImageUrl() != null){
            Picasso.get().load(userInfo.getImageUrl()).into(imageViewUserImg, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(R.drawable.user_empty_gray).into(imageViewUserImg);
                }
            });
        } else {
            Picasso.get().load(R.drawable.user_empty_gray).into(imageViewUserImg);
        }
        imageViewUserImg.buildDrawingCache();
        Bitmap bmap = imageViewUserImg.getDrawingCache();
        encodedImageData ="data:image/.*?;base64," + getEncoded64ImageStringFromBitmap(bmap);
        mListener.getUserInfoSuccess();
    }

    @Override
    public void getUserInfoFailed(String msg) {
        Toast.makeText(getContext(),msg, Toast.LENGTH_LONG).show();
        mListener.getUserInfoFailed(msg);
    }

    public interface OnFragmentInteractionListener {
        void onStartGetUserInfo();
        void getUserInfoSuccess();
        void getUserInfoFailed(String msg);

        void onStartSetUserInfo();
        void setUserInfoSuccess();
        void setUserInfoFailed(String msg);
    }

    private Uri getUri(Bitmap bitmap, String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File filesDir = getActivity().getFilesDir();
        File imageFile = new File(filesDir, timeStamp + "_" + name + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        return Uri.fromFile(imageFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SELECT_IMAGE && resultCode== Activity.RESULT_OK){
            byte[] byteArray = data.getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageViewUserImg.setImageBitmap(bmp);
            uriUserImage=getUri(bmp,"userimage");
            bitmapUserImage=bmp;
            imageViewUserImg.buildDrawingCache();
            Bitmap bmap = imageViewUserImg.getDrawingCache();
            encodedImageData ="data:image/.*?;base64," + getEncoded64ImageStringFromBitmap(bmap);
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    /////////

    void setSex(boolean isMale) {
        paramsRegister.setMale(isMale);
        boyImage.setImageResource(isMale ? R.drawable.icon_boy_selected : R.drawable.icon_boy_normal);
        girlImage.setImageResource(isMale ? R.drawable.icon_gir_normal : R.drawable.icon_gir_selected);
        boyText.setTextColor(isMale ? getActivity().getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.md_grey_600));
        girlText.setTextColor(isMale ? getActivity().getResources().getColor(R.color.md_grey_600) : getResources().getColor(R.color.colorPrimary));


    }

    public void dialogDatePicker() {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        birthDay.setVisibility(View.VISIBLE);
                        paramsRegister.setBirthDay(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        birthDay.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        selectBirthDay.setVisibility(View.GONE);

                    }
                },
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        dpd.setThemeDark(true);
        dpd.show(getChildFragmentManager(), "DatePickerDialog");
    }
}
