package com.example.mohamed.resturantapp;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamed.resturantapp.Adapter.RecyclerItemTouchHelper;
import com.example.mohamed.resturantapp.Adapter.ResturantAdpter;
import com.example.mohamed.resturantapp.Application.MyApplication;
import com.example.mohamed.resturantapp.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Item> cartList;
    private CoordinatorLayout coordinatorLayout;
    private ResturantAdpter mAdapter;
    private static final String URL = "https://api.androidhive.info/json/menu.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cartList = new ArrayList<>();
        mAdapter = new ResturantAdpter(cartList,this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

   PrepareCart();
    }

    public void PrepareCart(){
        JsonArrayRequest request=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               if (response==null){
                    Toast.makeText(MainActivity.this, "No response", Toast.LENGTH_SHORT).show();
                   return;
               }
               List<Item> items=new Gson().fromJson(response.toString(),new TypeToken<List<Item>>(){}.getType());
                cartList.clear();
                cartList.addAll(items);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToRequestQueue(request,TAG);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
      if (viewHolder instanceof ResturantAdpter.ResturantHolder){
          String name=cartList.get(viewHolder.getAdapterPosition()).getName();

          // backup of removed item for undo purpose
          final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
          final int deletedIndex = viewHolder.getAdapterPosition();

          // remove the item from recycler view
          mAdapter.RemoveItem(viewHolder.getAdapterPosition());

          Snackbar snackbar = Snackbar
                  .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
          snackbar.setAction("UNDO", new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  // undo is selected, restore the deleted item
                  mAdapter.RestoreItem(deletedIndex, deletedItem);
              }
          });
          snackbar.setActionTextColor(Color.YELLOW);
          snackbar.show();
      }
    }
}
