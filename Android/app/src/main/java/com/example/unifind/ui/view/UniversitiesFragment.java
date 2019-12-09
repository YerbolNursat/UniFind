package com.example.unifind.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Universities;
import com.example.unifind.ui.presenter.UniversitiesPresenter;
import com.example.unifind.ui.view.adapter.UniversitiesAdapter;

import java.util.List;


public class UniversitiesFragment extends Fragment implements com.example.unifind.ui.view.View.UniversitiesView {
    private RecyclerView rv;
    private View root;
    private UniversitiesPresenter presenter;
    private UniversitiesAdapter adapter;
    private ProgressBar progressBar;
    private SearchView searchView;

    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.universities_page, container, false);
        initialize();
        presenter.loadData();
         return root;
    }

    @Override
    public void ShowData(List<Universities> universities) {
        RemoveWait();
        adapter = new UniversitiesAdapter(universities);
        rv.setAdapter(adapter);
        presenter.search(adapter, searchView);
    }

    @Override
    public void ShowWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void RemoveWait() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        presenter.disposeDisposable();
        RemoveWait();
        Toast.makeText(root.getContext(), "Something wrong with internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initialize() {
        progressBar = root.findViewById(R.id.progress);
        rv = root.findViewById(R.id.universities_rv);
        presenter = new UniversitiesPresenter(this);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        searchView = root.findViewById(R.id.university_search);
    }
}
