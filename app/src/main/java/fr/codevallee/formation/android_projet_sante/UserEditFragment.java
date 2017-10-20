package fr.codevallee.formation.android_projet_sante;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserEditFragment.OnUserEditFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEditFragment extends Fragment {

    private User user;
    private OnUserEditFragmentInteractionListener mListener;

    public UserEditFragment() {
        // Required empty public constructor
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment UserEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEditFragment newInstance(User user) {
        Log.d("[INFO]", user.toString());
        UserEditFragment fragment = new UserEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setUser(user);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        final EditText firstnameField = (EditText) view.findViewById(R.id.edit_firstname);
        final EditText lastnameField = (EditText) view.findViewById(R.id.edit_lastname);
        final RadioButton genderMale = (RadioButton) view.findViewById(R.id.edit_gender_male);
        final RadioButton genderFemale = (RadioButton) view.findViewById(R.id.edit_gender_female);
        final RadioButton genderAgender = (RadioButton) view.findViewById(R.id.edit_gender_agender);
        final RadioButton genderOther = (RadioButton) view.findViewById(R.id.edit_gender_other);
        final AutoCompleteTextView jobField = (AutoCompleteTextView) view.findViewById(R.id.edit_job);
        final Spinner serviceField = (Spinner) view.findViewById(R.id.edit_service);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.services_list, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceField.setAdapter(spinnerAdapter);
        final EditText mailField = (EditText) view.findViewById(R.id.edit_mail);
        final EditText telephoneField = (EditText) view.findViewById(R.id.edit_telephone);
        final EditText cvField = (EditText) view.findViewById(R.id.edit_cv);

        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        Log.d("[INFO]", "Gender : " + user.getGender());
        if (user.getGender() != null) {
            switch (user.getGender()) {
                case User.GENDER_MALE:
                    genderMale.setChecked(true);
                    break;
                case User.GENDER_FEMALE:
                    genderFemale.setChecked(true);
                    break;
                case User.GENDER_AGENDER:
                    genderAgender.setChecked(true);
                    break;
                case User.GENDER_OTHER:
                    genderOther.setChecked(true);
                    break;
            }
        }
        jobField.setText(user.getJob());
        serviceField.setSelection(spinnerAdapter.getPosition((CharSequence) user.getService()));
        mailField.setText(user.getMail());
        telephoneField.setText(user.getTelephone());
        cvField.setText(user.getCv());

        Button sendButton = (Button) view.findViewById(R.id.edit_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    user.setFirstname(firstnameField.getText().toString());
                    user.setLastname(lastnameField.getText().toString());
                    String gender;
                    if (genderMale.isChecked()) {
                        gender = User.GENDER_MALE;
                    } else if (genderFemale.isChecked()) {
                        gender = User.GENDER_FEMALE;
                    } else if (genderAgender.isChecked()) {
                        gender = User.GENDER_AGENDER;
                    } else if (genderOther.isChecked()) {
                        gender = User.GENDER_OTHER;
                    } else {
                        gender = null;
                    }
                    user.setGender(gender);
                    Log.d("[INFO]", "Gender : " + gender);
                    user.setJob(jobField.getText().toString());
                    user.setService(serviceField.getSelectedItem().toString());
                    user.setMail(mailField.getText().toString());
                    user.setTelephone(telephoneField.getText().toString());
                    user.setCv(cvField.getText().toString());
                } catch (User.UserException e) {
                    e.printStackTrace();
                }

                Log.d("[INFO]", "Before edit user : " + user.toString());

                UserDataSource dataSource = new UserDataSource(getContext());
                UserDAO userDAO = dataSource.newUserDAO();
                user = userDAO.updateUser(user);

                Log.d("[INFO]", "After edit user : " + user.toString());

                // Intent toListIntent = new Intent(getContext(), MainActivity.class);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // if (manager.findFragmentById(R.id.secondary_fragment) != null) {
                //     transaction.detach(R.id.secondary_fragment);
                //     transaction.commit();
                // } else {
                    UserListFragment listFragment = new UserListFragment();
                    transaction.replace(R.id.main_fragment_container, listFragment);
                    transaction.commit();
                // }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onUserEditFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserEditFragmentInteractionListener) {
            mListener = (OnUserEditFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserEditFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUserEditFragmentInteractionListener {
        void onUserEditFragmentInteraction();
    }
}
