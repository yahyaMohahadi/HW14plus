package com.example.hw14.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw14.OnlineLanguege;
import com.example.hw14.R;
import com.example.hw14.database.WordRepository;
import com.example.hw14.model.Word;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    public static final int REQUEST_CODE_LOAD_FROM_ASSET = 1;
    public RecyclerView mRecyclerView;
    private WordAdapter mWordAdapter;
    private SearchView mSearchView;
    private TextView mTextViewPleaseVait;
    private TextView mTextViewPersentage;
    private WordRepository mWordRepository;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findView(view);
        mWordRepository = WordRepository.newInstance(getActivity());
        //initWaiting()
        initSearch();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateRecyclerView();
        setHasOptionsMenu(true);
        initWaiting();
        return view;
    }

    private void initWaiting() {
        if (!mWordRepository.isAssetLoad()) {

            AlertLoadWord loadWord = AlertLoadWord.newInstance();
            loadWord.setTargetFragment(this, REQUEST_CODE_LOAD_FROM_ASSET);
            loadWord.show(getFragmentManager(), "tagg");


        } else {
            mTextViewPleaseVait.setVisibility(View.GONE);
            mTextViewPersentage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else if (requestCode == REQUEST_CODE_LOAD_FROM_ASSET) {
            mWordRepository.setsBooleanPermissionForLoad(true);
            mTextViewPleaseVait.setVisibility(View.VISIBLE);
            mTextViewPersentage.setVisibility(View.VISIBLE);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            mWordRepository.addAssetWord(new WordRepository.Callbacks() {
                                public void persentOfProsses(float persent) {
                                    //todo why it crash?
                                    //mTextViewPersentage.setText(mWordRepository.getPersentAssetLoadWords()+"%");
                                }

                                @Override
                                public void assetLoadFinish() {
                                    mTextViewPersentage.setVisibility(View.GONE);
                                    mTextViewPleaseVait.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "all " + mWordRepository.getWordsCount() + " added sucsesfuly", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },
                    2000
            );

        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateRecyclerView() {
        if (mWordAdapter == null) {
            mWordAdapter = WordAdapter.newInstance(getActivity(),
                    new ArrayList<Word>()
                    , new WordAdapter.Callbacks() {
                        @Override
                        public void onCklickItemCalled(final Word word) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setTitle("Word option");

                            final String[] options = new String[2];
                            options[0] = "share";
                            options[1] = "delete";

                            builder.setItems(options, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int item) {

                                    if (options[item].equals("share")) {
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, word.toStringShare());
                                        sendIntent.setType("text/plain");
                                        startActivity(sendIntent);


                                    } else if (options[item].equals("delete")) {
                                        WordRepository.newInstance(getActivity()).deleteWord(word);
                                        mWordAdapter.setWords(WordRepository.newInstance(getActivity()).getLimitedWord(""));
                                        mWordAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void getUpdateList(String search) {
                            if (search.length() != 0) {
                                mWordAdapter.setWords(
                                        WordRepository
                                                .newInstance(getActivity())
                                                .getLimitedWord(search));
                            } else {
                                mWordAdapter.setWords(
                                        new ArrayList<Word>()
                                );
                            }
                        }
                    }
            );

            mRecyclerView.setAdapter(mWordAdapter);
        }
    }

    private void findView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_words);
        mSearchView = view.findViewById(R.id.searchView_main);
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mTextViewPersentage = view.findViewById(R.id.textView_persentage);
        mTextViewPleaseVait = view.findViewById(R.id.pleaseWait);
    }

    private void initSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mWordAdapter.getFilter().filter(query);
                return false;

            }

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        initViewMenu();
    }

    private void initViewMenu() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(
                String.valueOf(WordRepository.newInstance(getActivity()).getWordsCount())
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_add: {
                addNewWord();
                break;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void addNewWord() {
        AlertDialogAdd alertDialog = AlertDialogAdd.newInstance(new AlertDialogAdd.Callbacks() {
            @Override
            public void setWord(String stringRight, String stringLeft) {
                boolean add =
                        WordRepository.newInstance(getActivity()).addWord(
                                new Word(
                                        OnlineLanguege.newInstance().getLanguage(),
                                        stringRight,
                                        stringLeft
                                )
                        );
                if (add) {
                    Toast.makeText(getActivity(), "Add secsusfuly", Toast.LENGTH_SHORT).show();
                    initViewMenu();
                } else {
                    Toast.makeText(getActivity(), "coudent add", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show(getFragmentManager(), "TAG");

    }
}