package com.example.fragmenteventlistener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BlankFragment extends Fragment {
    // getString が失敗した時のためのデフォルト値
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // フラグメントが初期化されたときに設定したいパラメータ
    private String mParam1;
    private String mParam2;

    private Button buttonGetMessageByListener;
    private Button buttonGetMessageFromActivity;
    private TextView textView;

    // 参考 http://developer.android.com/training/basics/fragments/communicating.html
    // アクティビティ側の情報を使わなければならない処理など
    // フラグメント側の一存で処理できないことについては
    // インターフェイスだけを定義しておいてアクティビティ側に任せる。
    // Fragment#getActivity でアクティビティを取得して対応することもできるが、
    // そのためには Activity が public メンバを公開しなければならない。
    public interface OnGetMessageListener {
        String onGetMessage();
    }//OnGetMessageListener

    //  何らかの方法で外部から OnGetMessageListener のインスタンスをもらう。
    //  通常はアクティビティが OnGetMessageListener インターフェイスを実装し
    //  そのアクティビティを Fragment#onAttach で受け取り listener で参照する。
    private OnGetMessageListener listener;

    //　フラグメントのコンストラクタは引数を持つことができない
    public BlankFragment() {
    }//BlankFragment()

    //　フラグメントのコンストラクタは引数を持つことができないため、
    //  パラメータは生成後に Fragment#setArguments で渡すしかない。
    // そこで new と setArguments を同時に行う静的メソッドを用意する。
    // このような静的メソッドをファクトリメソッドと呼ぶ。
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

    //  Fragment#setArguments により渡されたパラメータは
    //  Fragment#onCreate の中で Fragment#getArguments により取得する。
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }//if
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        this.buttonGetMessageByListener = view.findViewById(R.id.buttonGetMessageByListener);
        this.buttonGetMessageFromActivity = view.findViewById(R.id.buttonGetMessageFromActivity);
        this.textView = view.findViewById(R.id.textView);

        this.buttonGetMessageByListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フラグメント側は自分で定義したリスナのメソッドを呼ぶ。
                // どのアクティビティから使われるのか知る必要は無い。
                final String message = listener.onGetMessage();
                textView.setText(message);
            }//onClick
        });//setOnClickListener

        this.buttonGetMessageFromActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フラグメント側がどのアクティビティで
                // 使われるのか知っていなければならない
                final String message = ((MainActivity) getActivity()).getMessage();
                textView.setText(message);
            }//onClick
        });//setOnClickListener

        return view;
    }//onCreateView

    // フラグメントがアクティビティに関連付けられたとき Fragment#onAttach が呼び出される。
    // このアクティビティは OnGetMessageListener インターフェスを実装している。
    // Context で受けているので OnGetMessageListener へのキャストが必要。
    // もしアクティビティが OnGetMessageListener を実装していなければ例外を投げる。
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGetMessageListener) {
            listener = (OnGetMessageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGetMessageListener");
        }//if
    }//onAttach

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }//onDetach
}//class BlankFragment
