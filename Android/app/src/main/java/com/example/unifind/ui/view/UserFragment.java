package com.example.unifind.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.constants.Constants;
import com.example.unifind.ui.model.Room.Specialities;
import com.example.unifind.ui.presenter.UserPresenter;
import com.example.unifind.ui.view.adapter.OnClicklistener;
import com.example.unifind.ui.view.adapter.SpecialitiesAdapter_room;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.List;
import java.util.Objects;

public class UserFragment extends Fragment implements com.example.unifind.ui.view.View.UserView {
    ImageView button;
    android.view.View root;
    UserPresenter presenter;
    SpecialitiesAdapter_room adapter;
    RecyclerView rv;
    RelativeLayout part1;
    CardView part2;
    LinearLayout part3;
    Button eng, kz, ru, sign_out;
    TextView name,text_university,text_speciality;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct;
    GoogleSignInOptions gso;

    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.user_page, container, false);
        initialise();
        presenter.loadData();
        return root;
    }

    private void signOut() {
        mGoogleSignInClient.signOut();
        Toast.makeText(root.getContext(), "Successfully signed out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), LoginPage.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void initialise() {
        presenter = new UserPresenter(this, root.getContext());
        rv = root.findViewById(R.id.recycle_view);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        part1 = root.findViewById(R.id.part1);
        part2 = root.findViewById(R.id.part2);
        part3 = root.findViewById(R.id.part3);
        eng = root.findViewById(R.id.lang_eng);
        kz = root.findViewById(R.id.lang_kz);
        ru = root.findViewById(R.id.lang_rus);
        sign_out = root.findViewById(R.id.sign_out);
        name = root.findViewById(R.id.name);
        button = root.findViewById(R.id.settings);
        text_speciality = root.findViewById(R.id.text_speciality);
        text_university = root.findViewById(R.id.text_university);
        if (Constants.LANG.equals("KZ")){
            text_university.setText("Университеттер");
            text_speciality.setText("Мамандықтар");
        }else if (Constants.LANG.equals("ENG")){
            text_university.setText("Universities");
            text_speciality.setText("Specialities");

        }
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(root.getContext(), gso);
        acct = GoogleSignIn.getLastSignedInAccount(root.getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            name.setText(personName);
        }
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                part1.setVisibility(android.view.View.GONE);
                part2.setVisibility(android.view.View.GONE);
                part3.setVisibility(android.view.View.VISIBLE);
            }
        });
        ru.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Constants.LANG = "RU";
                makeToast(Constants.LANG);
            }
        });


        kz.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Constants.LANG = "KZ";
                makeToast(Constants.LANG);

            }
        });

        eng.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Constants.LANG = "ENG";
                makeToast(Constants.LANG);

            }
        });
        sign_out.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                signOut();

            }
        });


    }

    private void makeToast(String text) {
        Toast.makeText(root.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showdata(final List<Specialities> specialities) {
        adapter = new SpecialitiesAdapter_room(specialities, new OnClicklistener() {
            @Override
            public void onItemClick(android.view.View view, int position) {
                presenter.deleteData(specialities.get(position));
                specialities.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, specialities.size());
            }
        });
        rv.setAdapter(adapter);
    }
}
