package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView=(RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView=(TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.crime_date);
           // mSolvedImageView=(ImageView)itemView.findViewById(R.id.crime_solved);

        }

        /*
        public CrimeHolder(LayoutInflater inflater,ViewGroup parent,int viewType){
            super(inflater.inflate(R.layout.list_item_crime_police,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView=(TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView=(ImageView)itemView.findViewById(R.id.crime_solved);

        }
        */
        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            String showData=(String)DateFormat.format("EEEE,MMM dd,yyyy",mCrime.getDate());
            mDateTextView.setText(showData);
          //  mSolvedImageView.setVisibility(crime.isSolved()? View.VISIBLE:View.GONE);
        }
        @Override
        //start another activity by intent with some messages
        public void onClick(View v){
            Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
     // private static final int sCALL_POLICE= -1;

        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }


        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
//            if(viewType==sCALL_POLICE){
//                return new CrimeHolder(layoutInflater,parent,sCALL_POLICE);
//            }else{
//                return new CrimeHolder(layoutInflater ,parent);
//            }
            return new CrimeHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
     //   @Override
//        public int getItemViewType(int position){
//            if(mCrimes.get(position).isRequiresPolice()){
//                return sCALL_POLICE;
//            }else{
//                return 0;
//            }
//        }
    }

    private void updateUi(){
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        List<Crime> crimes=crimeLab.getCrimes();

        mAdapter=new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

}
