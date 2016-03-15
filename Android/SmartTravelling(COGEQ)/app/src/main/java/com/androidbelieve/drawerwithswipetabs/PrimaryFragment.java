package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PrimaryFragment extends android.support.v4.app.ListFragment {

    private static PrimaryFragment instance;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<CogeqActivity> m_activities = null;
    private CogeqActivityAdapter m_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        m_activities = new ArrayList<>();
        m_activities.add( new CogeqActivity( "activity1", "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. 1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar."));
        m_activities.add( new CogeqActivity( "activity2", "2Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        m_activities.add( new CogeqActivity( "activity3", "3Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        m_activities.add(new CogeqActivity("activity4", "4Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis libero mi, nec vestibulum sem tincidunt vulputate. Curabitur ex lorem, consectetur eget orci vitae, iaculis posuere tellus. Ut ut iaculis lorem. Duis pretium imperdiet lorem, id blandit nunc porttitor in. Morbi et erat molestie, tempor sapien eget, feugiat elit. Etiam vehicula est ex, sed hendrerit massa tristique at. Curabitur porttitor velit lacus, eu varius odio molestie quis. Fusce tincidunt tincidunt venenatis. Donec vestibulum pretium lectus non pulvinar. "));
        m_activities.get(0).setImageUrl("http://www.asociatiaedelvais.ro/wp-content/uploads/2014/11/goodwp.com_163111.jpg");
        m_activities.get(1).setImageUrl("http://drugfree.scdn1.secure.raxcdn.com/wp-content/uploads/2010/09/alcohol11.jpg");
        m_activities.get(2).setImageUrl("http://orig12.deviantart.net/e367/f/2010/327/4/c/sunny_beach_14475003_by_stockproject1-d33h5n6.jpg");
        m_activities.get(3).setImageUrl("http://covermaker.net/thumbnail/10/1058.jpg");
        m_activities.get(0).setPosition( new LatLng(39.9117, 32.8403));
        m_activities.get(1).setPosition( new LatLng(39.8563, 32.8403));
        m_activities.get(2).setPosition( new LatLng(39.564, 32.8403));
        m_activities.get(3).setPosition( new LatLng(39.65465, 32.8403));
        m_adapter = new CogeqActivityAdapter( getActivity(), R.layout.cogeq_activity_row, m_activities);
        setListAdapter(m_adapter);
        return inflater.inflate(R.layout.primary_layout,null);
    }
    public static PrimaryFragment getInstance(){
        return instance;
    }
    public ArrayList<CogeqActivity> getActivities(){
        return m_activities;
    }
}
