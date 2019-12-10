package com.example.unifind.ui.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.constants.Constants;
import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.model.Specialities;
import com.example.unifind.ui.presenter.PickerPresenter;
import com.example.unifind.ui.view.adapter.DirectionsAdapter;
import com.example.unifind.ui.view.adapter.OnClicklistener;
import com.example.unifind.ui.view.adapter.OnEditTextChanged;
import com.example.unifind.ui.view.adapter.ProfessionsAdapter;
import com.example.unifind.ui.view.adapter.QuestionAdapter;
import com.example.unifind.ui.view.adapter.SpecialitiesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PickerFragment extends Fragment implements com.example.unifind.ui.view.View.ChooserPage {
    private View root;
    private TextView name_of_page, text1, text2, text3, text4;
    private RecyclerView directionrv, questionrv, professionrv, specialitiesrv;
    private DirectionsAdapter directionadapter;
    private QuestionAdapter questionAdapter;
    private ProfessionsAdapter professionsAdapter;
    private SpecialitiesAdapter specialitiesAdapter;
    private Spinner subject1, subject2, city;
    private PickerPresenter presenter;
    private String data;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private ImageView next;
    private LinearLayout firstpage, secondpage, thirdpage, fourth_page, fifth_page;
    private ProgressBar progressBar;
    private Integer[] answers = new Integer[41];
    private ArrayList<String> LikedProfessions = new ArrayList<>();
    private ArrayList<Integer> checked = new ArrayList<>();
    private ArrayList<com.example.unifind.ui.model.Room.Specialities> LikedSpecialities = new ArrayList<>();
    private List<Specialities> specialities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chooser_page, container, false);
        initialize();
        data = "9,5,3,5,7,6,8,5,4,6,3,6,7,8,10,2,3,6,7,8,10,8,10,2,4,5,6,7,8,9,4,3,4,5,6,8,9,10,10,3,2";

        return root;
    }


    @Override
    public void makeToast(String text) {
        Toast.makeText(root.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void makeIntent() {
        UserFragment userFragment = new UserFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, userFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void ShowQuestions(List<String> questions) {
        questionAdapter = new QuestionAdapter(questions, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                if (!charSeq.isEmpty()) {
                    if (Integer.parseInt(charSeq) != 0 && Integer.parseInt(charSeq) < 11)
                        answers[position] = Integer.parseInt(charSeq);
                    else
                        makeToast("Числа не больше 10");
                }
            }
        });
        questionrv.setAdapter(questionAdapter);
    }

    @Override
    public void ShowDirections(final List<Directions> directions) {
        RemoveWait();
        directionadapter = new DirectionsAdapter(directions, new OnClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                thirdpage.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                fourth_page.setVisibility(View.VISIBLE);
                presenter.loadProfessions(directions.get(position).getName(), subject1.getSelectedItem().toString(), subject2.getSelectedItem().toString());
            }
        });
        directionrv.setAdapter(directionadapter);

    }


    @Override
    public void ShowProfessions(final List<Professions> professions) {
        RemoveWait();
        professionsAdapter = new ProfessionsAdapter(professions, new OnClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                if (LikedProfessions.contains(professions.get(position).getName())) {
                    LikedProfessions.remove(professions.get(position).getName());
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    LikedProfessions.add(professions.get(position).getName());
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"));

                }
            }
        });
        professionrv.setAdapter(professionsAdapter);
    }

    @Override
    public void ShowSpecialities(List<Specialities> specialities) {
        RemoveWait();
        this.specialities = specialities;
        specialitiesAdapter = new SpecialitiesAdapter(specialities, new OnClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                if (checked.contains(position)) {
                    checked.remove(checked.indexOf(position));
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    checked.add(position);
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }
            }
        });
        specialitiesrv.setAdapter(specialitiesAdapter);
    }

    @Override
    public void initialize() {
        directionrv = root.findViewById(R.id.directions_rv);
        questionrv = root.findViewById(R.id.questions_rv);
        professionrv = root.findViewById(R.id.profession_rv);
        specialitiesrv = root.findViewById(R.id.specialities_rv);
        presenter = new PickerPresenter(this, root.getContext());
        next = root.findViewById(R.id.next);
        firstpage = root.findViewById(R.id.first_page);
        secondpage = root.findViewById(R.id.second_page);
        thirdpage = root.findViewById(R.id.third_page);
        fourth_page = root.findViewById(R.id.fourth_page);
        fifth_page = root.findViewById(R.id.fifth_page);
        questionrv.setHasFixedSize(true);
        directionrv.setHasFixedSize(true);
        professionrv.setHasFixedSize(true);
        specialitiesrv.setHasFixedSize(true);
        professionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        directionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        questionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        specialitiesrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        name_of_page = root.findViewById(R.id.name_of_page);
        subject1 = root.findViewById(R.id.subject1);
        subject2 = root.findViewById(R.id.subject2);
        city = root.findViewById(R.id.cities);
        progressBar = root.findViewById(R.id.progress);
        text1 = root.findViewById(R.id.text_1);
        text2 = root.findViewById(R.id.text_2);
        text3 = root.findViewById(R.id.text_3);
        text4 = root.findViewById(R.id.text_4);
        if (Constants.LANG.equals("KZ")) {
            text1.setText("Пәндерді таңдаңыз");
            text2.setText("Пән 1");
            text3.setText("Пән 2");
            text4.setText("Қаланы таңдаңыз");
            name_of_page.setText("Мәліметтерді енгізіңіз");

            List<String> subject1_list = Arrays.asList((root.getResources().getStringArray(R.array.subject1_KZ)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, subject1_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject1.setAdapter(spinnerArrayAdapter);
            List<String> subject2_list = Arrays.asList((root.getResources().getStringArray(R.array.subject2_KZ)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, subject2_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject2.setAdapter(spinnerArrayAdapter);
            List<String> cities_list = Arrays.asList((root.getResources().getStringArray(R.array.Cities_KZ)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, cities_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(spinnerArrayAdapter);

        } else if (Constants.LANG.equals("ENG")) {
            text1.setText("Choose subjects");
            text2.setText("Subjects 1");
            text3.setText("Subjects 2");
            text4.setText("Choose city");
            name_of_page.setText("Enter your details");

            List<String> subject1_list = Arrays.asList((root.getResources().getStringArray(R.array.subject1_ENG)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, subject1_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject1.setAdapter(spinnerArrayAdapter);
            List<String> subject2_list = Arrays.asList((root.getResources().getStringArray(R.array.subject2_ENG)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, subject2_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject2.setAdapter(spinnerArrayAdapter);
            List<String> cities_list = Arrays.asList((root.getResources().getStringArray(R.array.Cities_ENG)));
            spinnerArrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, cities_list);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(spinnerArrayAdapter);

        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstpage.getVisibility() == View.VISIBLE) {
                    firstpage.setVisibility(View.GONE);
                    presenter.loadQuestions(root.getContext());
                    secondpage.setVisibility(View.VISIBLE);

                    name_of_page.setText("Ответьте на все вопросы");
                    if (Constants.LANG.equals("KZ")) {
                        name_of_page.setText("сұрақтарға жауап беріңіз");
                    } else if (Constants.LANG.equals("ENG")) {
                        name_of_page.setText("answer for all questions");
                    }
                } else if (secondpage.getVisibility() == View.VISIBLE) {
//                    if (checkSecondPage()) {
                    secondpage.setVisibility(View.GONE);
                    presenter.loadDirections(data);
                    next.setVisibility(View.GONE);
                    name_of_page.setText("Выберите направление");
                    thirdpage.setVisibility(View.VISIBLE);
                    if (Constants.LANG.equals("KZ")) {
                        name_of_page.setText("Бағытты танданыз");
                    } else if (Constants.LANG.equals("ENG")) {
                        name_of_page.setText("Choose direction");
                    }


//                    } else {
//                        if (Constants.LANG.equals("KZ")){
//                            name_of_page.setText("Университетты танданыз");
//                        }else if (Constants.LANG.equals("ENG")){
//                            name_of_page.setText("Choose a university");
//                        }else{makeToast("Заполните всё");}
//                    }

                } else if (fourth_page.getVisibility() == View.VISIBLE) {
                    if (checkThirdPage()) {
                        fourth_page.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        fifth_page.setVisibility(View.VISIBLE);
                        name_of_page.setText("Выберите университеты");
                        presenter.loadSpecialities(data, city.getSelectedItem().toString());
                        if (Constants.LANG.equals("KZ")) {
                            name_of_page.setText("Университетты танданыз");
                        } else if (Constants.LANG.equals("ENG")) {
                            name_of_page.setText("Choose a university");
                        }

                    } else {
                        if (Constants.LANG.equals("KZ")) {
                            name_of_page.setText("Кем дегенде бір мамандық таңдаңыз");
                        } else if (Constants.LANG.equals("ENG")) {
                            name_of_page.setText("Choose at least one profession");
                        } else {
                            makeToast("Выберите хотя бы одну профессию");
                        }
                    }
                } else if (fifth_page.getVisibility() == View.VISIBLE) {
                    if (checked.size() != 0) {
                        for (Integer item : checked) {
                            LikedSpecialities.add(new com.example.unifind.ui.model.Room.Specialities(specialities.get(item).getUni_name(), specialities.get(item).getSpec_name()));
                        }
                        presenter.setdata(LikedSpecialities);
                    } else {
                        if (Constants.LANG.equals("KZ")) {
                            name_of_page.setText("Кем дегенде бір мамандық таңдаңыз\n");
                        } else if (Constants.LANG.equals("ENG")) {
                            name_of_page.setText("Choose at least one speciality");
                        } else {
                            makeToast("Выберите хотя бы одну специализацию");
                        }
                    }
                }
            }
        });
    }

    private boolean checkThirdPage() {
        data = "";
        if (LikedProfessions.size() == 0)
            return false;
        for (String item : LikedProfessions) {
            data += "," + item;
        }
        data = data.replaceFirst(",", "");
        return true;
    }

    private boolean checkSecondPage() {
        data = "";
        for (Integer item : answers) {
            if (item != null && item != 0 && item < 11) {
                data += "," + item;
            } else {
                return false;
            }
        }
        data = data.replaceFirst(",", "");
        return true;
    }

    @Override
    public void ShowWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void RemoveWait() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
