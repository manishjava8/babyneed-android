package com.vertechxa.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vertechxa.babyneeds.R;
import com.vertechxa.babyneeds.data.DatabaseHandler;
import com.vertechxa.babyneeds.model.Item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;

    private AlertDialog.Builder builder;

    private  AlertDialog dialog;

    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.itemName.setText(MessageFormat.format("Item: {0}",item.getItem()));
        holder.itemColor.setText(MessageFormat.format("Color: {0}", item.getItemColor()));
        holder.quantity.setText(MessageFormat.format("Qty: {0}", String.valueOf(item.getItemQuantity())));
        holder.size.setText(MessageFormat.format("Size: {0}", String.valueOf(item.getItemSize())));
        holder.dateAdded.setText(MessageFormat.format("Added on: {0}",item.getDateItemAdded()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName, itemColor, quantity, size, dateAdded;
        public int id;
        public Button editButton, deleteButton;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemColor = itemView.findViewById(R.id.item_color);
            quantity = itemView.findViewById(R.id.item_quantity);
            size = itemView.findViewById(R.id.item_size);
            dateAdded = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position;
            switch (v.getId()) {
                case R.id.editButton:
                    // edit item
                    break;
                case R.id.deleteButton:

                    // delete item
                    position = getAdapterPosition();
                    Item item = itemList.get(position);
                    deleteItem(item.getId());
                    break;
            }
        }

        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.confirmation_pop, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
    }
}
