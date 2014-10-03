package com.example.cherry_zhang.androidbeaconexample.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cherry_zhang.androidbeaconexample.Models.OfficeItem;
import com.example.cherry_zhang.androidbeaconexample.R;

import java.util.ArrayList;

/**
 * Created by Cherry_Zhang on 2014-09-20.
 */
public class ListOfOfficeAdapter extends ArrayAdapter<OfficeItem> {

    private Context context;
    private int layoutId;
    private ArrayList<OfficeItem> items;

    public ListOfOfficeAdapter(Context context, ArrayList<OfficeItem> items, int layoutId)
    {
        super(context, layoutId, items);
        this.context = context;
        this.layoutId = layoutId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new Holder();
            holder.officeImage = (ImageView) row.findViewById(R.id.IV_item_image);
            holder.title = (TextView) row.findViewById(R.id.TV_item_title);
            holder.address = (TextView) row.findViewById(R.id.TV_item_address);
            holder.company = (TextView) row.findViewById(R.id.TV_item_company);
            holder.price = (TextView) row.findViewById(R.id.TV_item_price);
            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        OfficeItem item = items.get(position);

        //TODO: get picture
        ((ImageView) row.findViewById(R.id.IV_item_image)).setImageBitmap(item.getOfficeImage());
        scaleImage(((ImageView) row.findViewById(R.id.IV_item_image)));
//        ((ImageView) row.findViewById(R.id.IV_item_image)).setLayoutParams
//                (new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        scaleImage(((ImageView) row.findViewById(R.id.IV_item_image)));
        holder.title.setText(item.getTitle());
        holder.address.setText(item.getAddress());
        holder.company.setText(item.getCompany());
        holder.price.setText(item.getPrice());
        return row;
    }

    //memory leak 防ぐのため
    static class Holder
    {
        ImageView officeImage;
        TextView title, address, company, price;
    }

    private void scaleImage(ImageView imageview)
    {
        // Get the ImageView and its bitmap
        ImageView view = imageview;
        Drawable drawing = view.getDrawable();
        if (drawing == null) {
            return; // Checking for null & return, as suggested in comments
        }
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions AND the desired bounding box
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int bounding = dpToPx(250);

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
