package fr.codevallee.formation.android_projet_sante;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;

    private OnUserViewFragmentInteractionListener mListener;

    public UserViewFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param User user
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_view, container, false);
        TextView testString = (TextView) view.findViewById(R.id.test_string);
        testString.setText((CharSequence) this.user.toString());
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
