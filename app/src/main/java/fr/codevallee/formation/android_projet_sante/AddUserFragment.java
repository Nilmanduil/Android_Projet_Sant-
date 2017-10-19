package fr.codevallee.formation.android_projet_sante;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
 * {@link AddUserFragment.OnAddUserFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAddUserFragmentInteractionListener mListener;

    public AddUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddUserFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddUserFragmentInteractionListener) {
            mListener = (OnAddUserFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnAddUserFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddUserFragmentInteraction();
    }
}
