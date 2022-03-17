package com.example.projectrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectrestaurant.adapters.ItemsListAdapter;
import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.viewmodels.ShowItemListActivityViewModel;

import java.util.List;

public class ShowItemsListActivity extends AppCompatActivity implements ItemsListAdapter.HandleItemsClick {

    private ShowItemListActivityViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private ItemsListAdapter itemsListAdapter;
    private Item itemForEdit;
    private int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);

        ImageView addNew = findViewById(R.id.addNewItemImageView);
        noResultTextView = findViewById(R.id.noResult);
        recyclerView = findViewById(R.id.recyclerView);
        category_id = getIntent().getIntExtra("category_id", 0);
        String category_name = getIntent().getStringExtra("category_name");

        getSupportActionBar().setTitle(category_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(false);
            }
        });

        initViewModel();
        initRecyclerView();
        viewModel.getAllItemsList(category_id);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsListAdapter = new ItemsListAdapter(this, this);
        recyclerView.setAdapter(itemsListAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ShowItemListActivityViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items == null) {
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    //show in the recyclerview
                    itemsListAdapter.setCategoryList(items);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showAddItemDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.add_item_layout, null);
        EditText enterItemName = dialogView.findViewById(R.id.enterItemName);
        EditText enterItemPrice = dialogView.findViewById(R.id.enterItemPrice);
        EditText enterItemQuantity = dialogView.findViewById(R.id.enterItemQuantity);
        TextView createButton = dialogView.findViewById(R.id.createButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);

        if (isForEdit) {
            createButton.setText("Update");
            enterItemName.setText(itemForEdit.getName());
            enterItemPrice.setText(itemForEdit.getPrice() + "");
            enterItemQuantity.setText(itemForEdit.getQuantity() + "");
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterItemName.getText().toString();
                Float price = Float.parseFloat(enterItemPrice.getText().toString());
                int quantity = Integer.parseInt(enterItemQuantity.getText().toString());
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ShowItemsListActivity.this, "Enter item name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(price <= 0) {
                    Toast.makeText(ShowItemsListActivity.this, "Enter valid item price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(quantity <= 0) {
                    Toast.makeText(ShowItemsListActivity.this, "Enter valid item quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isForEdit) {
                    itemForEdit.setName(name);
                    itemForEdit.setPrice(price);
                    itemForEdit.setCategoryId(category_id);
                    itemForEdit.setQuantity(quantity);
                    viewModel.updateItem(itemForEdit);
                } else {
                    viewModel.insertItem(new Item(name, price, category_id, quantity));
                }
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

//    @Override
//    public void itemClick(Category category) {
//        Intent intent = new Intent(ShowCategoryListActivity.this, ShowItemsListActivity.class);
//        intent.putExtra("category_id", category.getId());
//        intent.putExtra("category_name", category.getName());
//
//        startActivity(intent);
//    }

    @Override
    public void removeItem(Item item) {
        viewModel.deleteItem(item);
        Toast.makeText(this, "Remove successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editItem(Item item) {
        this.itemForEdit = item;
        showAddItemDialog(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}