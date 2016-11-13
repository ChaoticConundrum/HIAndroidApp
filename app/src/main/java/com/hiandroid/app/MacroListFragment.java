package com.hiandroid.app;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MacroListFragment extends ListFragment {

    /*
     * The commented bits are to implement an interaction listener to link to an activity.
     * That route would only be necessary if we want to ditch the dedicated edit button
     * and make the entire list item selectable (which would conflict with the dedicated run button)
     */


    private OnListFragmentInteractionListener interactionListener;
    private ArrayList<Macro> macros;

    public MacroListFragment() {  }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        macros = new ArrayList();
        // Sample Population
        for (int i = 0; i < 100; i++) {
            macros.add(new Macro("Macro #" + i));
        }

        ArrayAdapter<Macro> adapter = new MacroListAdapter(getContext(), R.layout.macro_list_item, macros);
        /*ArrayAdapter<Macro> adapter = new MacroListAdapter(getContext(), R.layout.macro_list_item, macros, new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Macro item) {

            }
        }); */

        setListAdapter(adapter);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Macro item);
    }
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        macros = new ArrayList();
        macros.add(new Macro("Test1"));
        macros.add(new Macro("Test2"));
        macros.add(new Macro("Test3"));
        macros.add(new Macro("Test4"));

        View view = inflater.inflate(R.layout.fragment_macro_list, container, false);
        RecyclerView macroListView = view.findViewById(R.id.list);
        macroListView.setAdapter();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener)
            interactionListener = (OnListFragmentInteractionListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }
    */
}
