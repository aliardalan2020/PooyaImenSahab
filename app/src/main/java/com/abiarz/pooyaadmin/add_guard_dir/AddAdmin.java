package com.abiarz.pooyaadmin.add_guard_dir;//package com.abiarz.pooyaimensahabadmin.add_guard_dir;
//
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.List;
//
//public class AddAdmin extends Fragment {
//View view;
//    private adapterAdmin adapter;
//    private ProgressBar pb_hottopics;
//    private RecyclerView recyclerView;
//    private RequestQueue requestQueue;
//    private ImageView back_ic;
//    FloatingActionButton fab_add_currency;
//    public AddAdmin() {}
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view= inflater.inflate(R.layout.fragment_add_admin, container, false);
//
//        requestQueue = Volley.newRequestQueue(getContext());
//        recyclerView= view.findViewById(R.id.recyclerView);
//        pb_hottopics= view.findViewById(R.id.pb_hottopics);
//        fab_add_currency=view.findViewById(R.id.fab_add_currency);
//        back_ic=view.findViewById(R.id.back_ic);
//        back_ic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//
//        setUpRecyclerView();
//
//
//
//        fab_add_currency.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft_a;
//                assert getFragmentManager() != null;
//                ft_a = getFragmentManager().beginTransaction();
//                AddAdminDialog newFragment = AddAdminDialog.newInstance();
//                newFragment.show(ft_a, "add_admin");
//                newFragment.setCancelable(true);
//            }
//        });
//
//
//
//    return view;
//    }
//
//    public void setUpRecyclerView(){
//        api_admin api_serverpost = new api_admin(new api_admin.getpost_session_days() {
//            @Override
//            public void posts_session_days(List<datamodelAdmin> post) {
//                pb_hottopics.setVisibility(View.GONE);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
//                adapter = new adapterAdmin(getContext(), post);
//                recyclerView.setAdapter(adapter);
//
//
//                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                    @Override
//                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                        super.onScrolled(recyclerView, dx, dy);
//                        if (dy > 0 && fab_add_currency.getVisibility() == View.VISIBLE) {
//                            fab_add_currency.hide();
//                        } else if (dy < 0 && fab_add_currency.getVisibility() != View.VISIBLE) {
//                            fab_add_currency.show();
//                        }
//                    }
//                });
//            }
//        }, getContext(),requestQueue);
//    }
//}
