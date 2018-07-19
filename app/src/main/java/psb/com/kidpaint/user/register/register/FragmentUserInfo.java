package psb.com.kidpaint.user.register.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import psb.com.kidpaint.utils.CalendarTool;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;

public class FragmentUserInfo extends Fragment implements iVUserInfo {

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText editTextName, editTextLastName;
    private ImageView imageViewUserImg, camera;
    private Button buttonUserInfo;
    private PUserInfo pUserInfo;
    private TextView text_phone_number, error, title;
    private ProgressBar progressBar;

    private static final int REQUEST_SELECT_IMAGE = 20;
    private Bitmap bitmapUserImage = null;
    private Uri uriUserImage;

    private String encodedImageData;

    private ParamsRegister paramsRegister;

    private ImageView girlImage, boyImage;
    private TextView girlText, boyText, birthDay, selectBirthDay;

    private ImageView imgBack;


    public FragmentUserInfo() {
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
        pUserInfo = new PUserInfo(this);
        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        setView();
        return view;
    }

    private void setView() {

        TextView title1 = view.findViewById(R.id.text_title_1);
        TextView title2 = view.findViewById(R.id.text_title_2);
        title1.setText("ثبت نام");
        title2.setText("ثبت نام");

        editTextName = view.findViewById(R.id.edit_text_name);
        editTextLastName = view.findViewById(R.id.edit_text_last_name);

        imgBack= view.findViewById(R.id.icon_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.cancelRegister();
                }
            }
        });


        paramsRegister = new ParamsRegister();

        girlImage = view.findViewById(R.id.image_girl_normal);
        girlText = view.findViewById(R.id.text_girl);
        boyImage = view.findViewById(R.id.image_boy_normal);
        boyText = view.findViewById(R.id.text_boy);
        birthDay = view.findViewById(R.id.text_birthday);
        selectBirthDay = view.findViewById(R.id.selectBirthDay);

        imageViewUserImg = view.findViewById(R.id.image_user_info_avatar);
        camera = view.findViewById(R.id.image_user_info_edit);
        text_phone_number = view.findViewById(R.id.text_phone_number);
        progressBar = view.findViewById(R.id.frg_registerUserInfo_progressBar);
        text_phone_number.setText(Utils.getStringPreference(getContext(),
                Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, "-1"));
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
        buttonUserInfo.setText("ثبت نام");

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

    public void retryGetProfile() {
        pUserInfo.getUserInfo(Utils.getStringPreference(getContext(),
                Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, "-1"));
    }

    public void retrySetProfile() {
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
        if (!userInfo.getImageUrl().isEmpty() || userInfo.getImageUrl() != null) {
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
        encodedImageData = "data:image/.*?;base64," + getEncoded64ImageStringFromBitmap(bmap);
        mListener.getUserInfoSuccess();
    }

    @Override
    public void getUserInfoFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        mListener.getUserInfoFailed(msg);
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
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            byte[] byteArray = data.getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageViewUserImg.setImageBitmap(bmp);
            uriUserImage = getUri(bmp, "userimage");
            bitmapUserImage = bmp;
            imageViewUserImg.buildDrawingCache();
            Bitmap bmap = imageViewUserImg.getDrawingCache();
            encodedImageData = "data:image/.*?;base64," + getEncoded64ImageStringFromBitmap(bmap);
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
                        CalendarTool calendarTool=new CalendarTool();
                        calendarTool.setIranianDate(year,(monthOfYear+1),dayOfMonth);
                        paramsRegister.setBirthDay(calendarTool.getGregorianYear() + "-" + (calendarTool.getGregorianMonth()<10?("0"+calendarTool.getGregorianMonth()):calendarTool.getGregorianMonth()) + "-" + calendarTool.getGregorianDay());
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


    public interface OnFragmentInteractionListener {
        void onStartGetUserInfo();

        void getUserInfoSuccess();

        void getUserInfoFailed(String msg);

        void onStartSetUserInfo();

        void setUserInfoSuccess();

        void setUserInfoFailed(String msg);

        void cancelRegister();
    }
}
