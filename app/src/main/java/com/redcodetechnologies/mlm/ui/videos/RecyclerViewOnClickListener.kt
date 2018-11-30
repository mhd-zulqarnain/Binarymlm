package com.redcodetechnologies.mlm.ui.videos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerViewOnClickListener(context: Context, listener: OnItemClickListener): RecyclerView.OnItemTouchListener {
    private val mListener: OnItemClickListener
    private val mGestureDetector: GestureDetector
    interface OnItemClickListener {
        fun onItemClick(view: View, position:Int)
    }
    init{
        mListener = listener
        mGestureDetector = GestureDetector(context, object:GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent):Boolean {
                return true
            }
        })
    }
    override fun onInterceptTouchEvent(view:RecyclerView, e:MotionEvent):Boolean {
        val childView = view.findChildViewUnder(e.getX(), e.getY())
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
        {
            mListener.onItemClick(childView, view.getChildPosition(childView))
            return true
        }
        return false
    }
    override fun onTouchEvent(view:RecyclerView, motionEvent:MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept:Boolean) {}
}