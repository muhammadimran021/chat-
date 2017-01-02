package com.example.babarmustafa.chatapplication;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.example.babarmustafa.chatapplication.Chat_Work.ConversationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuestureFrag extends Fragment implements ConversationActivity.AnimationForPullerBottomListener {
    public static boolean groupMembersFragmentDown;
    float pullerBottomActualPosition;
    private ImageButton pullerBottom;
    GestureDetector gestureDetector;

    public GuestureFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_guesture, container, false);
        pullerBottom = (ImageButton) view1.findViewById(R.id.puller_bottom);

        return view1;
    }

    public void moveUp() {

        ObjectAnimator animationForUpperView = ObjectAnimator.ofFloat(ConversationActivity.upperView, "y", 0);
        animationForUpperView.setDuration(400);

        ObjectAnimator slideUpAnimation = ObjectAnimator.ofFloat(ConversationActivity.groupMemberFragment, "y", ConversationActivity.groupMemberFragmentTop);
        slideUpAnimation.setDuration(400);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                pullerBottom.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.play(slideUpAnimation).with(animationForUpperView);

        animatorSet.start();
    }

    @Override
    public void animatePullerBottom() {


        GuestureFrag.groupMembersFragmentDown = true;
        Log.d("AnimationExp", "in GroupMembersFragment animatePullerBottom()");

        if (pullerBottomActualPosition == 0)
            pullerBottomActualPosition = pullerBottom.getY();

        gestureDetector = new GestureDetector(getActivity(), new MyGestureDetector());
        pullerBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        final ObjectAnimator animationForPullerBottom = ObjectAnimator.ofFloat(pullerBottom, "y", 2000, pullerBottomActualPosition);
        animationForPullerBottom.setDuration(4000);
        animationForPullerBottom.setInterpolator(new DecelerateInterpolator(12));
        animationForPullerBottom.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                pullerBottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationForPullerBottom.start();
    }

    public interface OnGroupMembersFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGroupMembersFragmentInteraction(String id);

        public void changeMenuItemsForGroupMembersFragmentToHomeFragment();
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getY() < e2.getY()) {

                Log.d("AnimationExp", "in GroupMembersFragment onFling()");

                moveUp();
            }
            return true;
        }
    }


}
