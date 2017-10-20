package fr.codevallee.formation.android_projet_sante;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUserAddFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAddFragment extends Fragment {

    private OnUserAddFragmentInteractionListener mListener;

    public UserAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAddFragment newInstance() {
        UserAddFragment fragment = new UserAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        final EditText firstnameField = (EditText) view.findViewById(R.id.add_firstname);
        final EditText lastnameField = (EditText) view.findViewById(R.id.add_lastname);
        final RadioButton genderMale = (RadioButton) view.findViewById(R.id.add_gender_male);
        final RadioButton genderFemale = (RadioButton) view.findViewById(R.id.add_gender_female);
        final RadioButton genderAgender = (RadioButton) view.findViewById(R.id.add_gender_agender);
        final RadioButton genderOther = (RadioButton) view.findViewById(R.id.add_gender_other);
        final AutoCompleteTextView jobField = (AutoCompleteTextView) view.findViewById(R.id.add_job);
        final Spinner serviceField = (Spinner) view.findViewById(R.id.add_service);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.services_list, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceField.setAdapter(spinnerAdapter);
        final EditText mailField = (EditText) view.findViewById(R.id.add_mail);
        final EditText telephoneField = (EditText) view.findViewById(R.id.add_telephone);
        final EditText cvField = (EditText) view.findViewById(R.id.add_cv);

        SharedPreferences prefs = getActivity().getSharedPreferences("JobsFile", Context.MODE_PRIVATE);
        Set<String> jobsSet = prefs.getStringSet("jobs", new HashSet<String>());
        ArrayList<String> jobsList = new ArrayList<String>(jobsSet);
        Log.d("Info jobs", jobsList.toString());
        if (jobsSet != null) {
            ArrayAdapter<String> jobsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, jobsList);
            jobField.setAdapter(jobsAdapter);
        }

        Button sendButton = (Button) view.findViewById(R.id.add_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstnameField.getText().toString();
                String lastname = lastnameField.getText().toString();
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
                String job = jobField.getText().toString();
                String service = serviceField.getSelectedItem().toString();
                String mail = mailField.getText().toString();
                String telephone = telephoneField.getText().toString();
                String cv = cvField.getText().toString();

                User user = null;
                try {
                    user = new User(
                            null,
                            firstname,
                            lastname,
                            gender,
                            job,
                            service,
                            mail,
                            telephone,
                            cv
                    );

                    UserDataSource dataSource = new UserDataSource(getContext());
                    UserDAO userDAO = dataSource.newUserDAO();
                    user = userDAO.createUser(user);

                    Log.d("[INFO]", "Add user : " + user.toString());

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
                } catch (User.UserException e) {
                    Toast.makeText(getContext(), (CharSequence) e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onUserAddFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserAddFragmentInteractionListener) {
            mListener = (OnUserAddFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserViewFragmentInteractionListener");
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
    public interface OnUserAddFragmentInteractionListener {
        void onUserAddFragmentInteraction();
    }
}
