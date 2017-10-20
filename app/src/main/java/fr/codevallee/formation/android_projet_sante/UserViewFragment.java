package fr.codevallee.formation.android_projet_sante;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUserViewFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserViewFragment extends Fragment {
    private User user;

    private OnUserViewFragmentInteractionListener mListener;

    public UserViewFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param context context
     * @param user user
     * @return A new instance of fragment UserViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserViewFragment newInstance(Context context, User user) {
        UserViewFragment fragment = new UserViewFragment();
        UserDataSource dataSource = new UserDataSource(context);
        UserDAO userDAO = dataSource.newUserDAO();
        user = userDAO.readUser(user);
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
        View view = inflater.inflate(R.layout.fragment_user_view, container, false);

        TextView fullnameField = (TextView) view.findViewById(R.id.view_fullname);
        TextView genderField = (TextView) view.findViewById(R.id.view_gender);
        TextView jobField = (TextView) view.findViewById(R.id.view_job);
        TextView serviceField = (TextView) view.findViewById(R.id.view_service);
        TextView mailField = (TextView) view.findViewById(R.id.view_mail);
        TextView telephoneField = (TextView) view.findViewById(R.id.view_telephone);
        TextView cvField = (TextView) view.findViewById(R.id.view_cv);

        fullnameField.setText(user.getFirstname() + " " + user.getLastname());
        genderField.setText((user.getGender() != null ? user.getGender() : getString(R.string.gender_undefined)));
        jobField.setText((user.getJob() != null ? user.getJob() : getString(R.string.job_undefined)));
        serviceField.setText((user.getService() != null ? user.getService() : getString(R.string.service_undefined)));
        mailField.setText((user.getMail() != null ? user.getMail() : getString(R.string.mail_undefined)));
        telephoneField.setText((user.getTelephone() != null ? user.getTelephone() : getString(R.string.telephone_undefined)));
        cvField.setText((user.getCv() != null ? user.getCv() : getString(R.string.cv_undefined)));

        Button callButton = (Button) view.findViewById(R.id.view_call);
        Button sendMailButton = (Button) view.findViewById(R.id.view_send_mail);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getTelephone() != null) {
                    Uri phone = Uri.parse("tel:" + user.getTelephone());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, phone);
                    startActivity(callIntent);
                }
            }
        });
        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getMail() != null) {
                    Intent mailIntent = new Intent(Intent.ACTION_SEND);
                    mailIntent.setType("text/html");
                    mailIntent.putExtra(Intent.EXTRA_EMAIL, user.getMail());
                    startActivity(mailIntent);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onUserViewFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserViewFragmentInteractionListener) {
            mListener = (OnUserViewFragmentInteractionListener) context;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public interface OnUserViewFragmentInteractionListener {
        void onUserViewFragmentInteraction();
    }
}
