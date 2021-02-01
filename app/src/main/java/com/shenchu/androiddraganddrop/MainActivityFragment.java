package com.shenchu.androiddraganddrop;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View[] views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        views = new View[rootView.getChildCount()];
        for (int i = 0; i < rootView.getChildCount(); i++) {
            final TextView view = (TextView) rootView.getChildAt(i);
            initView(view);
            views[i] = view;
        }
        return rootView;
    }

    private void initView(final TextView view) {
        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        /** 拖拽开始时 */
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        /** 拖拽进入区域时 */
                        v.setBackgroundColor(Color.RED);
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        /** 拖拽进入区域后，仍在区域内拖动时 */
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundColor(Color.TRANSPARENT);
                        return true;

                    case DragEvent.ACTION_DROP:
                        v.setBackgroundColor(Color.TRANSPARENT);
                        int dragVal = Integer.parseInt(event.getClipData().getItemAt(0).getText().toString());
                        int viewVal = Integer.parseInt(((TextView) v).getText().toString());
                        ((TextView) v).setText("" + (dragVal + viewVal));
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("value", view.getText());
                view.startDragAndDrop(data, new View.DragShadowBuilder(v), null, 0);
                return true;
            }
        });
    }

}
