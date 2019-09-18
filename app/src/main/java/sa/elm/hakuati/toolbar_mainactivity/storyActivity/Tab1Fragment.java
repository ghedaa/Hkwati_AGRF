package sa.elm.hakuati.toolbar_mainactivity.storyActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import sa.elm.hakuati.toolbar_mainactivity.R;


public class Tab1Fragment extends Fragment  {



            @SuppressLint("WrongViewCast")
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View v=  inflater.inflate(R.layout.story_fragment_one, container, false);




                

                return  v;
            }//end Tab1Fragment

            

        }//end class

