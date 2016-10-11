package com.hoan.turnercodingtest.activities.main;

import android.util.SparseArray;
import android.view.View;

@SuppressWarnings("unchecked")
public final class ViewHolder
{
	
	private ViewHolder()
	{
	}
	
	public static <T extends View> T get(View view, int id) 
    {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) 
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) 
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
    
	public static <T extends View> T get(View view, int id, int position) 
    {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) 
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        
        if (childView == null) 
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        childView.setTag(position);
        return (T) childView;
    }
	
	@SuppressWarnings("nls")
	public static <T extends View> T get(View view, int id, int groupPosition, int childPosition) 
    {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) 
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        
        if (childView == null) 
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        childView.setTag(groupPosition  + ":" + childPosition);
        return (T) childView;
    }
}
