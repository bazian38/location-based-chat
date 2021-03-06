
package com.braunster.chatsdk.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.fragments.abstracted.ChatSDKAbstractProfileFragment;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;
import com.braunster.chatsdk.object.SaveIndexDetailsTextWatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class ChatSDKProfileFragment extends ChatSDKAbstractProfileFragment {

    private static final String S_I_D_NAME = "saved_name_data";
    private static final String S_I_D_PHONE = "saved_phones_data";
    private static final String S_I_D_EMAIL = "saved_email_data";
    private static final String S_I_D_STATUS = "saved_status_data";
    private static final String S_I_D_DEPARTMENT = "saved_department_data";
    private static final String S_I_D_COURSES = "saved_courses_data";

    private EditText etName, etMail, etPhone, etStatus, etDepartment, etCourses;
    private Spinner spinner;

    private ListView courses;
    private ArrayList<String> arrayCourses;
    private ArrayAdapter<String> arrayAdapter;

    public static ChatSDKProfileFragment newInstance() {
        ChatSDKProfileFragment f = new ChatSDKProfileFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        f.setRetainInstance(true);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        initViews(inflater);

        if (savedInstanceState != null)
        {
            setDetails(getLoginType(), savedInstanceState);
        }
        else
        {
            loadData();
        }

        return mainView;
    }

    public void initViews(LayoutInflater inflater){
        if (inflater != null)
            mainView = inflater.inflate(R.layout.chat_sdk_activity_profile, null);
        else return;

        super.initViews();

        setupTouchUIToDismissKeyboard(mainView, R.id.chat_sdk_circle_ing_profile_pic);

        // Changing the weight of the view according to orientation.
        // This will make sure (hopefully) there is enough space to show the views in landscape mode.
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainView.findViewById(R.id.linear).getLayoutParams();
            layoutParams.weight = 3;
            mainView.findViewById(R.id.linear).setLayoutParams(layoutParams);
        }
        else
        {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainView.findViewById(R.id.linear).getLayoutParams();
            layoutParams.weight = 2;
            mainView.findViewById(R.id.linear).setLayoutParams(layoutParams);
        }

        etName = mainView.findViewById(R.id.chat_sdk_et_name);
        etMail = mainView.findViewById(R.id.chat_sdk_et_mail);
        etPhone = mainView.findViewById(R.id.chat_sdk_et_phone_number);
        etStatus = mainView.findViewById(R.id.chat_sdk_et_status);
        etDepartment = mainView.findViewById(R.id.chat_sdk_hidden_department);
        spinner = mainView.findViewById(R.id.departments_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.departments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        etCourses = mainView.findViewById(R.id.chat_sdk_hidden_course);
        courses = mainView.findViewById(R.id.lv_courses);
        arrayCourses = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayCourses);
        courses.setAdapter(arrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Setting a listener to text change, The listener will take cate of indexing the data.
        SaveIndexDetailsTextWatcher emailTextWatcher = new SaveIndexDetailsTextWatcher(BDefines.Keys.BEmail);
        SaveIndexDetailsTextWatcher nameTextWatcher= new SaveIndexDetailsTextWatcher(BDefines.Keys.BName);
        etMail.addTextChangedListener(emailTextWatcher);
        etName.addTextChangedListener(nameTextWatcher);


        // The number would only be index if phone index is enabled in BDefines.
        TextWatcher phoneTextWatcher = new SaveIndexDetailsTextWatcher(BDefines.Keys.BPhone);
        etPhone.addTextChangedListener(phoneTextWatcher);

        TextWatcher statusTextWatcher = new SaveIndexDetailsTextWatcher(BDefines.Keys.BStatus);
        etStatus.addTextChangedListener(statusTextWatcher);

        TextWatcher departmentTextWatcher = new SaveIndexDetailsTextWatcher(BDefines.Keys.BDepartment);
        etDepartment.addTextChangedListener(departmentTextWatcher);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    etDepartment.setText(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextWatcher coursesTextWatcher = new SaveIndexDetailsTextWatcher(BDefines.Keys.BCourses);
        etCourses.addTextChangedListener(coursesTextWatcher);

        courses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Remove Course?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Object item = adapterView.getItemAtPosition(i);
                        if (item != null) {
                            arrayAdapter.remove(item.toString());
                            if (!arrayCourses.isEmpty()) {
                                Iterator<String> iterator = arrayCourses.iterator();
                                StringBuilder s = new StringBuilder("");
                                while (iterator.hasNext()) {
                                    s.append(iterator.next());
                                    s.append(";");
                                }
                                etCourses.setText(s.toString());
                            } else {
                                etCourses.setText(";");
                            }
                        }
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismissProgDialog();
                    }
                });
                alert.show();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        super.loadData();
        setDetails((Integer) BNetworkManager.sharedManager().getNetworkAdapter().getLoginInfo().get(BDefines.Prefs.AccountTypeKey));
    }

    @Override
    public void clearData() {
        super.clearData();

        if (mainView != null)
        {
            etName.getText().clear();
            etMail.getText().clear();
            etPhone.getText().clear();
            etStatus.getText().clear();
            etDepartment.getText().clear();
            etCourses.getText().clear();
            arrayAdapter.clear();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString(S_I_D_NAME, etName.getText().toString());
        outState.putString(S_I_D_EMAIL, etMail.getText().toString());
        outState.putString(S_I_D_PHONE, etPhone.getText().toString());
        outState.putString(S_I_D_STATUS, etStatus.getText().toString());
        outState.putString(S_I_D_DEPARTMENT, etDepartment.getText().toString());
        outState.putString(S_I_D_COURSES, etCourses.getText().toString());
    }

    @Override
    public void logout() {
        // Logout and return to the login activity.

        BNetworkManager.sharedManager().getNetworkAdapter().logout();
        chatSDKUiHelper.startLoginActivity(true);
    }

    @Override
    public void addCourse() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setTitle("Write Course Number");
        alert.setMessage("should contain only numbers");
        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton) {
                final String s = input.getText().toString().trim();
                arrayAdapter.add(s);
                etCourses.setText(s + ";" + etCourses.getText());
            }
        });
        alert.show();
    }

    /*############################################*/
    /* UI*/
    /** Fetching the user details from the user's metadata.*/
    private void setDetails(int loginType){
        if (mainView == null || getActivity() == null)
        {
            return;
        }

        BUser user = BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel();
        etName.setText(user.getMetaName());
        etPhone.setText(user.metaStringForKey(BDefines.Keys.BPhone));
        etMail.setText(user.getMetaEmail());
        etStatus.setText(user.getMetaStatus());

        String department = user.getMetaDepartment();
        spinner.setSelection(getIndex(spinner, department));
        etDepartment.setText(department);

        chatSDKProfileHelper.loadProfilePic(loginType);

        String courses = user.getMetaCourses();
        if (courses != null) {
            etCourses.setText(courses);
            String[] arrCourses = courses.split(";");
            arrayCourses.clear();
            Collections.addAll(arrayCourses, arrCourses);
        }
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    private void setDetails(int loginType, Bundle bundle){
        etName.setText(bundle.getString(S_I_D_NAME));
        etPhone.setText(bundle.getString(S_I_D_PHONE));
        etMail.setText(bundle.getString(S_I_D_EMAIL));
        etStatus.setText(bundle.getString(S_I_D_STATUS));

        String department = bundle.getString(S_I_D_DEPARTMENT);
        spinner.setSelection(getIndex(spinner, department));
        etDepartment.setText(department);

        chatSDKProfileHelper.loadProfilePic(loginType);

        String courses = bundle.getString(S_I_D_COURSES, "");
        etCourses.setText(courses);
        String[] arrCourses = courses.split(";");
        arrayCourses.clear();
        Collections.addAll(arrayCourses, arrCourses);
    }

}
